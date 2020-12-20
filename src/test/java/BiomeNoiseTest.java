import blueduck.outerend.mixin_code.MixinHelpers;
import net.minecraft.world.gen.SimplexNoiseGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class BiomeNoiseTest extends JComponent {
	private static final JFrame frame = new JFrame("Biome Gen Debug");

	public static void main(String[] args) throws InterruptedException, IOException {
		frame.setSize(300, 300);
		Random rng = new Random();
		MixinHelpers.resetGenerator(rng.nextLong());
		MixinHelpers.generator1 = new SimplexNoiseGenerator(new Random(rng.nextLong()));
		frame.add(new BiomeNoiseTest());
//		frame.setVisible(true);
		while (frame.isVisible()) {
			frame.repaint();
			Thread.sleep(100);
		}
		float scale = 4;
		int res = 1000;
		int offX = 10000;
		int offZ = 0;
		BufferedImage image = new BufferedImage((int) (res * scale), (int) (res * scale), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = ((Graphics2D)image.getGraphics());
		Graphics2D g2d = ((Graphics2D)image.getGraphics());
		int imageOffX = (int)((image.getWidth()/2)/scale);
		int imageOffY = (int)((image.getHeight()/2)/scale);
		for (int x1 = 0; x1 < image.getWidth()/scale; x1++) {
			for (int z1 = 0; z1 < image.getWidth()/scale; z1++) {
				int x = x1-imageOffX+offX;
				int z = z1-imageOffY+offZ;
				float genX = MixinHelpers.floatBitShift(x, 2);
				float genZ = MixinHelpers.floatBitShift(z, 2);
				float rgb =
						(float) MixinHelpers.get(genX,genZ);
				int i = x >> 2;
				int j = z >> 2;
				if ((long) i * (long) i + (long) j * (long) j <= 4096L) {
					rgb = 0;
				} else {
					float f = MixinHelpers.getRandomNoise(MixinHelpers.generator1, genX * 2 + 1, genZ * 2 + 1);
					if (f > 40.0F) {
					} else if (f >= 0.0F) {
					} else {
						rgb = 0;
					}
				}
				if (rgb == 0) {
					g.setColor(new Color(0));
				} else {
					float weight = 60;
					float range = 60;
					if (rgb >= weight - range / 2 && rgb <= weight + range / 2) {
						rgb = Math.max(0, (rgb + 100)) / 180f;
						rgb = clampToColor(rgb);
						g.setColor(new Color(0, rgb, rgb));
					} else {
						rgb = Math.max(0, (rgb + 100)) / 180f;
						rgb = clampToColor(rgb);
						g.setColor(new Color(rgb, rgb, rgb));
					}
				}
				g.fillRect((int)(x1*scale), (int)(z1*scale), (int)scale, (int)scale);
				g.setColor(new Color(255,0,0));
				if (x%16 == 0) {
					AffineTransform transform = g2d.getTransform();
					g.fillRect((int)(x1*scale),(int)(z1*scale),1,(int)scale);
					g.fillRect((int)(x1*scale),(int)(z1*scale)+((int)(scale-1)),1,(int)scale);
					g2d.setTransform(transform);
				}
				if (z%16 == 0) {
					AffineTransform transform = g2d.getTransform();
					g.fillRect((int)(x1*scale),(int)(z1*scale),(int)scale,1);
					g.fillRect((int)(x1*scale)+((int)(scale-1)),(int)(z1*scale),(int)scale,1);
					g2d.setTransform(transform);
				}
			}
		}
		if (!new File("debug_out.png").exists()) new File("debug_out.png").createNewFile();
		ImageIO.write(image,"png",new File("debug_out.png"));
		Runtime.getRuntime().exit(0);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		float scale = 4;
		g2d.translate(frame.getWidth()/2f,frame.getHeight()/2f);
		g2d.scale(scale,scale);
		int res = 100;
		int offX = 1000;
		int offZ = 0;
		for (int x=-res+offX;x<=res+offX;x++) {
			for (int z = -res + offZ; z <= res + offZ; z++) {
				float genX = MixinHelpers.floatBitShift(x, 2);
				float genZ = MixinHelpers.floatBitShift(z, 2);
				float rgb =
						(float) MixinHelpers.get(genX,genZ);
				int i = x >> 2;
				int j = z >> 2;
				if ((long) i * (long) i + (long) j * (long) j <= 4096L) {
					rgb = 0;
				} else {
					float f = MixinHelpers.getRandomNoise(MixinHelpers.generator1, i * 2 + 1, j * 2 + 1);
					if (f > 40.0F) {
					} else if (f >= 0.0F) {
					} else {
						rgb = 0;
					}
				}
				if (rgb == 0) {
					g.setColor(new Color(0));
				} else {
					float weight = 60;
					float range = 60;
					if (rgb >= weight - range / 2 && rgb <= weight + range / 2) {
						rgb = Math.max(0, (rgb + 100)) / 180f;
						rgb = clampToColor(rgb);
						g.setColor(new Color(0, rgb, rgb));
					} else {
						rgb = Math.max(0, (rgb + 100)) / 180f;
						rgb = clampToColor(rgb);
						g.setColor(new Color(rgb, rgb, rgb));
					}
				}
				g.fillRect(x - offX, z - offZ, 1, 1);
				g.setColor(new Color(255, 0, 0,128));
				if ((x % 16) == 0) {
					AffineTransform source = g2d.getTransform();
					((Graphics2D) g).translate(x - offX,z-offZ);
					g2d.scale(0.25f,0.25f);
					g.fillRect(0, 0, 1, 4);
					g.fillRect(3, 0, 1, 4);
					g2d.setTransform(source);
				}
				if ((z % 16) == 0) {
					AffineTransform source = g2d.getTransform();
					((Graphics2D) g).translate(x - offX,z-offZ);
					g2d.scale(0.25f,0.25f);
					g.fillRect(0, 0, 4, 1);
					g.fillRect(0, 3, 4, 1);
					g2d.setTransform(source);
				}
			}
		}
		g2d.scale(1f/scale,1f/scale);
	}
	
	public static float clampToColor(float num) {
		return Math.max(0,Math.min(1,num));
	}
}
