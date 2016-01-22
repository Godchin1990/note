package godchin.codelife.control;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;

public class FrameUtil {
	public static void setCenter(Frame frame) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		int frameWidth = frame.getWidth();
		int frameHeight = frame.getHeight();
		frame.setLocation((screenWidth - frameWidth) / 2,
				(screenHeight - frameHeight) / 2);

	}
}
