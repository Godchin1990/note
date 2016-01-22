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
	 * ȫ���ȼ��ĳ�ʼ��ע��
	 */
	public void init() {
		// �赱ǰ����Ŀ¼Ϊ"e:\\java\\JIntellitype"
		// Ҫ���ص�dll�ļ���Ϊ"JIntellitype64.dll",λ�ڵ�ǰĿ¼��".\\com"Ŀ¼��
		// System.load("e:\\java\\JIntellitype\\com\\JIntellitype64.dll");
		// JIntellitype.setLibraryLocation("com\\JIntellitype64.dll");

		if (!JIntellitype.isJIntellitypeSupported()) {// �����ȷ������ϵͳ��windows�����ҵ�32λ��64λ��dll֧��,����dll֧��
			System.exit(1);
		}

		jinstance = JIntellitype.getInstance();// ������jni���λ�ú󣬼���jni�⣬��ȡ������jintellitype����ʵ��

		// �����ȼ�������
		jinstance.addHotKeyListener(new HotkeyListener() {
			public void onHotKey(int identifier) {
				// System.out.println("�������ȼ���Ψһ��ʾID:" + identifier + "��������һ");
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

		// ����ý���������
		jinstance.addIntellitypeListener(new IntellitypeListener() {
			@Override
			public void onIntellitype(int command) {
				// System.out.println("������ý�����Ψһ��ʾID:" + command);
			}
		});

		// ����ȼ�
		// jinstance.registerHotKey(1, 0, (int) 'Z');// ����Ϊintǿ��ת���Ĵ�д��ĸ
		// jinstance.registerHotKey(2, Event.CTRL_MASK + Event.SHIFT_MASK,(int)
		// 'Z');
		// jinstance.registerHotKey(3,JIntellitype.MOD_WIN +
		// JIntellitype.MOD_ALT, (int) 'A');

		// jinstance.registerHotKey(4, "a");
		jinstance.registerHotKey(5, "shift+ctrl+p");
		jinstance.registerHotKey(6, "ctrl+h");
		jinstance.registerHotKey(7, "shift+ctrl+i");

		// System.out.println("jintellitype��ʼ����ɣ�����ȫ���ȼ����ܿ�����һ����Ч����");
	}

	/**
	 * ע��ȫ���ȼ���������Դ
	 */
	public void exit() {
		JIntellitype.getInstance().cleanUp();
		System.exit(0);
	}

}
