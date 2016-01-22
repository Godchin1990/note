package godchin.codelife.start;

import godchin.codelife.control.FrameUtil;
import godchin.codelife.control.HotKeyControl;
import godchin.codelife.control.JarPath;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.io.File;

import javax.swing.JOptionPane;

public class CodeLib {
	public static void main(String[] args) throws Exception {
		File flagFile = new File(JarPath.getJarPath(), "flag");
		if (false == flagFile.createNewFile()) {
			JOptionPane.showMessageDialog(new Component() {
			}, "A previous instance is already running....", "isrunning",
					JOptionPane.PLAIN_MESSAGE);
			System.exit(1);
		}

		flagFile.deleteOnExit();
		Frame codeWindow = new CodeWindow();
		// codeWindow.setAlwaysOnTop(true);
		codeWindow.setTitle("Code");
		FrameUtil.setCenter(codeWindow);
		codeWindow.setBackground(Color.WHITE);
		codeWindow.setVisible(true);
		HotKeyControl m = new HotKeyControl();
		m.init();
	}
}
