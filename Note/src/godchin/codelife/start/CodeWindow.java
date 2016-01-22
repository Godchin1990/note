package godchin.codelife.start;

import godchin.codelife.control.JarPath;
import godchin.codelife.view.CreateTextWindow;
import godchin.codelife.view.ShowPicWindow;
import godchin.codelife.view.ShowTextWindow;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Frame;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class CodeWindow extends javax.swing.JFrame {
	static {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e3) {
			e3.printStackTrace();
		}
	}

	private java.awt.List content;
	private java.awt.Button delete;
	private java.awt.Button insert;
	private java.awt.Button search;
	private java.awt.TextField title;
	private java.awt.Button upload;
	protected Frame codeWindow;

	public CodeWindow() {

		initComponents();
		initQuery();

		SystemTray systemTray = SystemTray.getSystemTray();
		TrayIcon trayIcon = null;

		try {
			// 创建弹出菜单
			PopupMenu popupMenu = new PopupMenu();
			MenuItem mainFrameItem = new MenuItem("about");
			mainFrameItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane
							.showMessageDialog(
									new Component() {
									},
									"           This Just for personal coding !\n                     CodeLife version_1.0 \n                             by godchin ",
									"codelib", JOptionPane.PLAIN_MESSAGE);

				}
			});

			// 截图
			MenuItem captrueItem = new MenuItem("capture");
			ActionListener captureListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					try {
						Thread.currentThread().sleep(1000);
						ScreenShotWindow shotWindow = new ScreenShotWindow();
						shotWindow.setVisible(true);
						// DoCapture.doStartAnyS();

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			};
			captrueItem.addActionListener(captureListener);
			MenuItem exitItem = new MenuItem("exit");

			exitItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					System.exit(0);
				}

			});
			popupMenu.add(captrueItem);
			popupMenu.add(mainFrameItem);
			// popupMenu.addSeparator();
			popupMenu.add(exitItem);

			try {
				trayIcon = new TrayIcon(ImageIO.read(this.getClass()
						.getResource("/tray.png")), "codelib", popupMenu);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			systemTray.add(trayIcon);
			trayIcon.setImageAutoSize(true);
		} catch (AWTException e2) {
			e2.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				dispose();

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				if (JOptionPane.showConfirmDialog(null, "             exist?",
						"exist", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {
					setExtendedState(Frame.ICONIFIED);
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		trayIcon.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					setExtendedState(Frame.NORMAL);
				setVisible(true);
			}
		});
		// ---------
		upload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String command = "java -jar upload.jar";
				try {
					Runtime.getRuntime().exec(command, null,
							new File(JarPath.getJarPath()));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		insert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateTextWindow createTextWindow = new CreateTextWindow();
				createTextWindow.setVisible(true);
			}
		});
		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String t = title.getText().toString().trim();
				List<String> list = null;
				content.removeAll();
				if (t != null & t.length() >= 1) {
					try {
						// win8连接mdb数据库
						String uri = (new StringBuilder(
								"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="))
								.append(JarPath.getJarPath())
								.append(File.separator).append("Demo.mdb")
								.toString();
						Connection conn = DriverManager.getConnection(uri);
						String sql = "select id,stitle,scontent from tb_code WHERE stitle like '%"
								+ t
								+ "%' or scontent like '%"
								+ t
								+ "%' order by id desc";

						Statement st = conn.createStatement();
						ResultSet rs = st.executeQuery(sql);
						list = new ArrayList<String>();

						while (rs.next()) {
							String tt = rs.getString("stitle");
							list.add(tt);
						}
						for (String l : list) {
							content.add(l);
						}

						rs.close();
						st.close();
						conn.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else if (t.length() == 0) {
					initQuery();

				}
			}
		});
		delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String item = "";
				try {
					item = content.getSelectedItem();
					// win8连接mdb数据库
					if (item != null & item.length() > 0) {
						String uri = (new StringBuilder(
								"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="))
								.append(JarPath.getJarPath())
								.append(File.separator).append("Demo.mdb")
								.toString();
						Connection conn = DriverManager.getConnection(uri);

						PreparedStatement updateSales = conn
								.prepareStatement("delete from tb_code WHERE stitle =? ");
						updateSales.setString(1, item);
						updateSales.executeUpdate();
						updateSales.close();
						conn.close();
						content.remove(item);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		title.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {
				String t = title.getText().toString().trim();
				List<String> list = null;
				content.removeAll();
				if (t != null & t.length() >= 2) {
					try {
						// win8连接mdb数据库
						String uri = (new StringBuilder(
								"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="))
								.append(JarPath.getJarPath())
								.append(File.separator).append("Demo.mdb")
								.toString();
						Connection conn = DriverManager.getConnection(uri);
						String sql = "select * from tb_code WHERE stitle like '%"
								+ t + "%'  order by id desc";

						Statement st = conn.createStatement();
						ResultSet rs = st.executeQuery(sql);
						list = new ArrayList<String>();

						while (rs.next()) {
							String tt = rs.getString("stitle");
							list.add(tt);
						}
						for (String l : list) {
							content.add(l);
						}

						rs.close();
						st.close();
						conn.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});

		content.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				switch (e.getClickCount()) {
				case 2:
					String item = content.getSelectedItem();
					try {
						String uri = (new StringBuilder(
								"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="))
								.append(JarPath.getJarPath())
								.append(File.separator).append("Demo.mdb")
								.toString();
						Connection conn = DriverManager.getConnection(uri, "",
								"");
						String sql = "select stitle,scontent,Width,Height,pic_date from tb_code where stitle = '"
								+ item + "'";
						Statement st = conn.createStatement();
						ResultSet rs = st.executeQuery(sql);
						while (rs.next()) {
							String stitle = rs.getString("stitle");
							String scontent = rs.getString("scontent");
							int Width = rs.getInt("Width");
							int Height = rs.getInt("Height");
							// System.out.println(Width+"双击显示");
							byte[] pic_date = rs.getBytes("pic_date");
							if (pic_date == null
									|| "".equals(pic_date.toString())) {
								ShowTextWindow showTextWindow = new ShowTextWindow(
										stitle, scontent, Width, Height);
								showTextWindow.setVisible(true);
							} else {
								new ShowPicWindow(stitle, Width, Height,
										pic_date);
							}
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					break;
				default:
					break;
				}

			}
		});

	}

	private void initQuery() {
		try {
			// win8连接mdb数据库
			String uri = (new StringBuilder(
					"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="))
					.append(JarPath.getJarPath()).append(File.separator)
					.append("Demo.mdb").toString();
			Connection conn = DriverManager.getConnection(uri);
			String sql = "select id ,stitle from tb_code order by stitle asc";

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			List<String> cc = new ArrayList<String>();

			while (rs.next()) {
				String tt = rs.getString("stitle");
				cc.add(tt);
			}
			for (String c : cc) {
				content.add(c);
			}

			rs.close();
			st.close();
			conn.close();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private void initComponents() {
		title = new java.awt.TextField();
		content = new java.awt.List();
		search = new java.awt.Button();
		delete = new java.awt.Button();
		upload = new java.awt.Button();
		insert = new java.awt.Button();
		// setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(300, 300));
		search.setLabel("search");
		delete.setLabel("delete");
		upload.setLabel("upload");
		insert.setLabel("insert");
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		content,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addContainerGap())
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		title,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)

																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		search,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE))))
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(delete,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(insert,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(upload,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap()));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														search,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														title,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(content,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														delete,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														upload,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														insert,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));

		pack();

	}

}
