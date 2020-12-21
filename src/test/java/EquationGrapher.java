import net.minecraft.util.math.MathHelper;

import javax.swing.*;
import java.awt.*;

public class EquationGrapher extends JComponent {
	public static JFrame frame = new JFrame();
	
	public static void main(String[] args) throws InterruptedException {
		frame.setSize(300,300);
		frame.add(new EquationGrapher());
		frame.setVisible(true);
		
		while (frame.isVisible()) {
			long time = System.nanoTime();
			frame.repaint();
			Thread.sleep(10);
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(0,frame.getHeight()/2f);
		g2d.translate(frame.getWidth()/2,0);
		g2d.scale(5,-5);
		
		for (int i=-frame.getWidth()/2;i<frame.getWidth()/2;i++) {
			g.setColor(new Color(255));
			g.drawLine(i,out(i),i+1,out(i+1));
			g.setColor(new Color(0));
			g.drawRect(i,out(i),1,1);
		}
	}
	
	public int out(int in) {
		if (true) {
			int scl = 10;
			return ((int) (in / 10f)) * (scl / 2) != ((int) ((in + 1) / 10f)) * (scl / 2) ? (int) MathHelper.lerp(0.5f, ((int) (in / 10f)) * (scl / 2), ((int) (in / 10f)) * (scl / 2) + scl) : ((int) (in / 10f)) * (scl / 2) != ((int) ((in + 2) / 10f)) * (scl / 2) ? (int) MathHelper.lerp(0.35f, ((int) (in / 10f)) * (scl / 2), ((int) (in / 10f)) * (scl / 2) + scl) : ((int) (in / 10f)) * (scl / 2) != ((int) ((in + 3) / 10f)) * (scl / 2) ? (int) MathHelper.lerp(0.2f, ((int) (in / 10f)) * (scl / 2), ((int) (in / 10f)) * (scl / 2) + scl) : ((int) (in / 10f)) * (scl / 2) + 2;
		}
		int scl = 10;
		int val = ((int)(in/10f))*(scl/2);
		if (((int)((in+1)/10f))*(scl/2) != val) {
			return (int)MathHelper.lerp(0.75f,val,val+scl);
		} else if ((int)((in+2)/10f) != val) {
			return (int)MathHelper.lerp(0.5f,val,val+scl);
		} else if ((int)((in+3)/10f) != val) {
			return (int)MathHelper.lerp(0.25f,val,val+scl);
		}
		return val;
	}
}