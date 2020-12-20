import blueduck.outerend.mixin_code.MixinHelpers;
import net.minecraft.world.gen.SimplexNoiseGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BiomeNoiseTest extends JComponent {
	private static final JFrame frame = new JFrame("Biome Gen Debug");
	
	public static long nanosPerFrame = 1000000000L;
	public static long frameTime = 0;
	
	public static void main(String[] args) throws InterruptedException, IOException {
		frame.setSize(300, 300);
		System.out.println(nanosPerFrame);
		Random rng = new Random();
		MixinHelpers.resetGenerator(rng.nextLong());
		MixinHelpers.generator1 = new SimplexNoiseGenerator(new Random(rng.nextLong()));
		frame.add(new BiomeNoiseTest());
		frame.setVisible(true);
		while (frame.isVisible()) {
			long time = System.nanoTime();
			frame.repaint();
			frameTime = ((System.nanoTime()-time));
			printFrameTime();
			Thread.sleep(10);
		}
		float scale = 1;
		int res = 4000;
		int offX = 0;
		int offZ = 0;
		BufferedImage image = new BufferedImage((int) (res * scale), (int) (res * scale), BufferedImage.TYPE_INT_RGB);
		{
			Graphics2D g = ((Graphics2D)image.getGraphics());
			g.setColor(new Color(0));
			g.fillRect(0,0,image.getWidth(),image.getHeight());
		}
		int imageOffX = (int)((image.getWidth()/2)/scale);
		int imageOffY = (int)((image.getHeight()/2)/scale);
		float weight = 60;
		float range = 60;
		ArrayList<Thread> threads = new ArrayList<>();
		long time = System.currentTimeMillis();
		for (int x1 = 0; x1 < image.getWidth()/scale; x1++) {
			while (threads.size()>=10) {
				ArrayList<Integer> toRemove = new ArrayList<>();
				int num = 0;
				for (Thread td : threads) {
					if (!td.isAlive()) {
						toRemove.add(num);
					} else {
						num++;
					}
				}
				for (int i : toRemove) {
					threads.remove(i);
				}
				try {
					Thread.sleep(1);
				} catch (Throwable ignored) {
				}
			}
			int finalX = x1;
			Thread td = new Thread(()->{
				Graphics g = image.getGraphics();
				for (int z1 = 0; z1 < image.getWidth()/scale; z1++) {
					int x = finalX -imageOffX+offX;
					int z = z1-imageOffY+offZ;
					float rgb;
					int i = x >> 2;
					int j = z >> 2;
					if ((long) i * (long) i + (long) j * (long) j <= 4096L) {
						rgb = 0;
					} else {
						float f = MixinHelpers.getRandomNoise(MixinHelpers.generator1, i * 2 + 1, j * 2 + 1);
						if (f > 40.0F) {
							float genX = MixinHelpers.floatBitShift(x, 2);
							float genZ = MixinHelpers.floatBitShift(z, 2);
							rgb = (float) MixinHelpers.get(genX + range,genZ + weight);
						} else if (f >= 0.0F) {
							float genX = MixinHelpers.floatBitShift(x, 2);
							float genZ = MixinHelpers.floatBitShift(z, 2);
							rgb = (float) MixinHelpers.get(genX + range,genZ + weight);
						} else {
							rgb = 0;
						}
					}
					if (rgb != 0) {
						if (rgb >= weight - range / 2 && rgb <= weight + range / 2) {
							rgb = Math.max(0, (rgb + 100)) / 180f;
							rgb = clampToColor(rgb);
							g.setColor(new Color(0, rgb, rgb));
						} else {
							rgb = Math.max(0, (rgb + 100)) / 180f;
							rgb = clampToColor(rgb);
							g.setColor(new Color(rgb, rgb, rgb));
						}
						g.fillRect((int)(finalX *scale), (int)(z1*scale), (int)scale, (int)scale);
					}
					g.setColor(new Color(255,0,0));
					if (x%16 == 0) {
						g.fillRect((int)(finalX *scale),(int)(z1*scale),1,(int)scale);
						g.fillRect((int)(finalX *scale),(int)(z1*scale)+((int)(scale-1)),1,(int)scale);
					}
					if (z%16 == 0) {
						g.fillRect((int)(finalX *scale),(int)(z1*scale),(int)scale,1);
						g.fillRect((int)(finalX *scale)+((int)(scale-1)),(int)(z1*scale),(int)scale,1);
					}
				}
			});
			threads.add(td);
			td.start();
		}
		while (threads.size()>=1) {
			ArrayList<Integer> toRemove = new ArrayList<>();
			int num = 0;
			for (Thread td : threads) {
				if (!td.isAlive()) {
					toRemove.add(num);
				} else {
					num++;
				}
			}
			for (int i : toRemove) {
				threads.remove(i);
			}
			try {
				Thread.sleep(1);
			} catch (Throwable ignored) {
			}
		}
		long drawTime = ((System.currentTimeMillis()-time));
		System.out.println(drawTime);
		if (!new File("debug_out.png").exists()) new File("debug_out.png").createNewFile();
		ImageIO.write(image,"png",new File("debug_out.png"));
		Runtime.getRuntime().exit(0);
	}
	
	private static void printFrameTime() {
//		System.out.println();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		float scale = 2;
		g2d.translate(frame.getWidth()/2f,frame.getHeight()/2f);
		g2d.scale(scale,scale);
		int res = 100;
		int offX = 1000;
		int offZ = 0;
		
		float weight = 60;
		float weightRange = 60;
		
		g.setColor(new Color(0));
		g.fillRect(-res,-res,res*2+1,res*2+1);
		
		long time = System.currentTimeMillis();
		ArrayList<Thread> threads = new ArrayList<>();
		for (int x=-res+offX;x<=res+offX;x++) {
			while (threads.size()>=4) {
				ArrayList<Integer> toRemove = new ArrayList<>();
				int num = 0;
				for (Thread td : threads) {
					if (!td.isAlive()) {
						toRemove.add(num);
					} else {
						num++;
					}
				}
				for (int i : toRemove) {
					threads.remove(i);
				}
				try {
					Thread.sleep(1);
				} catch (Throwable ignored) {
				}
			}
			int finalX1 = x;
			Thread td = new Thread(()-> {
				Graphics g1 = g.create();
				Graphics2D g2d1 = (Graphics2D) g1;
				for (int z = -res + offZ; z <= res + offZ; z++) {
					float rgb;
					int i = finalX1 >> 2;
					int j = z >> 2;
					if ((long) i * (long) i + (long) j * (long) j <= 4096L) {
						rgb = 0;
					} else {
						float f = MixinHelpers.getRandomNoise(MixinHelpers.generator1, i * 2 + 1, j * 2 + 1);
						if (f > 40.0F) {
							float genX = MixinHelpers.floatBitShift(finalX1, 2);
							float genZ = MixinHelpers.floatBitShift(z, 2);
							rgb = (float) MixinHelpers.get(genX + weightRange, genZ + weight);
						} else if (f >= 0.0F) {
							float genX = MixinHelpers.floatBitShift(finalX1, 2);
							float genZ = MixinHelpers.floatBitShift(z, 2);
							rgb = (float) MixinHelpers.get(genX + weightRange, genZ + weight);
						} else {
							rgb = 0;
						}
					}
					if (rgb != 0) {
						float red;
						float greenblue;
						if (rgb >= weight - weightRange / 2 && rgb <= weight + weightRange / 2) {
							rgb = Math.max(0, (rgb + 100)) / 180f;
							rgb = clampToColor(rgb);
							red = 0;
							greenblue = rgb;
						} else {
							rgb = Math.max(0, (rgb + 100)) / 180f;
							rgb = clampToColor(rgb);
							red = rgb;
							greenblue = rgb;
						}
						g1.setColor(new Color(red, greenblue, greenblue));
						g1.fillRect(finalX1 - offX, z - offZ, 1, 1);
					}
					g1.setColor(new Color(255, 0, 0, 128));
					if ((finalX1 % 16) == 0) {
						AffineTransform source = g2d1.getTransform();
						g1.translate(finalX1 - offX, z - offZ);
						g2d1.scale(0.25f, 0.25f);
						g1.fillRect(0, 0, 1, 4);
						g1.fillRect(3, 0, 1, 4);
						g2d1.setTransform(source);
					}
					if ((z % 16) == 0) {
						AffineTransform source = g2d1.getTransform();
						g1.translate(finalX1 - offX, z - offZ);
						g2d1.scale(0.25f, 0.25f);
						g1.fillRect(0, 0, 4, 1);
						g1.fillRect(0, 3, 4, 1);
						g2d1.setTransform(source);
					}
				}
			});
			threads.add(td);
			td.start();
		}
		while (threads.size()>=1) {
			ArrayList<Integer> toRemove = new ArrayList<>();
			int num = 0;
			for (Thread td : threads) {
				if (!td.isAlive()) {
					toRemove.add(num);
				} else {
					num++;
				}
			}
			for (int i : toRemove) {
				threads.remove(i);
			}
			try {
				Thread.sleep(1);
			} catch (Throwable ignored) {
			}
		}
		g2d.scale(1f/scale,1f/scale);
		long frameTime = ((System.currentTimeMillis()-time));
		g2d.translate(-frame.getWidth()/2f,-frame.getHeight()/2f);
		g.setColor(new Color(0));
		g.drawString((frameTime)+"",0,15);
	}
	
	@Override
	public boolean isOptimizedDrawingEnabled() {
		return true;
	}
	
	public static float clampToColor(float num) {
		return Math.max(0,Math.min(1,num));
	}
}
