package godchin.codelife.start;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class DoCapture {
	public static void doStartAnyS() {
		JFrame.setDefaultLookAndFeelDecorated(false);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension ds = tk.getScreenSize();
		Rectangle rect = new Rectangle(0, 0, ds.width, ds.height);
		Robot robot;
		try {
			robot = new Robot();
			BufferedImage image = robot.createScreenCapture(rect);
			imageFrame = new JFrame();
			imageFrame.setUndecorated(true);// È¥µô×°ÊÎ
			imageFrame.getContentPane().add(
					new TempImage(imageFrame, image, ds.width, ds.height));
			imageFrame.setSize(ds);
			imageFrame.setVisible(true);
			imageFrame.setAlwaysOnTop(true);
		} catch (Exception e) {
		}
	}

	private static JFrame imageFrame; // Í¼Ïñ´°¿Ú
}
