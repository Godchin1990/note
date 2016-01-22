package godchin.codelife.control;

import godchin.codelife.start.CodeWindow;
import godchin.codelife.start.DoCapture;
import godchin.codelife.start.ScreenShotWindow;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.Window;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.IntellitypeListener;
import com.melloware.jintellitype.JIntellitype;

/**
 * 
 */
public class HotKeyControl {

	private JIntellitype jinstance;

	/**
	 * 全局热键的初始化注册
	 */
	public void init() {
		// 设当前工作目录为"e:\\java\\JIntellitype"
		// 要加载的dll文件名为"JIntellitype64.dll",位于当前目录的".\\com"目录下
		// System.load("e:\\java\\JIntellitype\\com\\JIntellitype64.dll");
		// JIntellitype.setLibraryLocation("com\\JIntellitype64.dll");

		if (!JIntellitype.isJIntellitypeSupported()) {// 检查以确保操作系统是windows且能找到32位和64位的dll支持,加载dll支持
			System.exit(1);
		}

		jinstance = JIntellitype.getInstance();// 在设置jni库的位置后，加载jni库，获取单例的jintellitype对象实例

		// 设置热键处理方法
		jinstance.addHotKeyListener(new HotkeyListener() {
			public void onHotKey(int identifier) {
				// System.out.println("触发的热键的唯一标示ID:" + identifier + "，处理方法一");
				switch (identifier) {
				case 5:
					try {
						Window[] windows = Window.getWindows();
						for (Window window : windows) {
							String name = window.getName();
							if (window instanceof CodeWindow) {
								window.dispose();
								try {
									Thread.currentThread().sleep(500);
									new ScreenShotWindow().setVisible(true);

									//
									//
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					} catch (AWTException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				case 6:
					Window[] windows = Window.getWindows();
					for (Window window : windows) {
						String name = window.getName();
						if (window instanceof CodeWindow) {
							if (((CodeWindow) window).getState() != Frame.ICONIFIED) {
								((CodeWindow) window)
										.setExtendedState(Frame.ICONIFIED);
							} else {
								((CodeWindow) window).setVisible(true);

								((CodeWindow) window)
										.setExtendedState(Frame.NORMAL);

							}
						} else {
							// ((Frame)
							// window).setExtendedState(Frame.ICONIFIED);
							// ((Frame) window).setVisible(true);
							// ((Frame)
							// window).setExtendedState(Frame.ICONIFIED);
						}

						// System.out.println(name);
					}

					break;
				case 7:
					try {
						Window[] windows2 = Window.getWindows();
						for (Window window : windows2) {
							String name = window.getName();
							if (window instanceof CodeWindow) {
								window.dispose();
								try {
									Thread.currentThread().sleep(500);
									DoCapture.doStartAnyS();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					break;

				default:
					break;
				}

			}
		});

		// 设置媒体键处理方法
		jinstance.addIntellitypeListener(new IntellitypeListener() {
			@Override
			public void onIntellitype(int command) {
				// System.out.println("触发的媒体键的唯一标示ID:" + command);
			}
		});

		// 添加热键
		// jinstance.registerHotKey(1, 0, (int) 'Z');// 参数为int强制转换的大写字母
		// jinstance.registerHotKey(2, Event.CTRL_MASK + Event.SHIFT_MASK,(int)
		// 'Z');
		// jinstance.registerHotKey(3,JIntellitype.MOD_WIN +
		// JIntellitype.MOD_ALT, (int) 'A');

		// jinstance.registerHotKey(4, "a");
		jinstance.registerHotKey(5, "shift+ctrl+p");
		jinstance.registerHotKey(6, "ctrl+h");
		jinstance.registerHotKey(7, "shift+ctrl+i");

		// System.out.println("jintellitype初始化完成，触发全局热键就能看到不一样的效果！");
	}

	/**
	 * 注销全局热键、清理资源
	 */
	public void exit() {
		JIntellitype.getInstance().cleanUp();
		System.exit(0);
	}

}
