package godchin.codelife.view;

import godchin.codelife.control.JarPath;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowTextWindow extends javax.swing.JFrame {
	private java.awt.TextArea scontent;
	private java.awt.TextField stitle;
	private String item;

	public ShowTextWindow(String t, String s, int w, int h) {
		initComponents();
		item = t;
		stitle.setText(t);
		// stitle.setSelectionEnd(t.length());
		stitle.setCaretPosition(t.length());
		Font f = new Font("微软雅黑", Font.PLAIN, 12);
		scontent.setFont(f);
		scontent.setText(s);
		this.setSize(w, h);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub

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
				int width = ShowTextWindow.this.getWidth();
				int height = ShowTextWindow.this.getHeight();
				// System.out.println("closed:"+width);
				try {
					Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
					String uri = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="
							+ JarPath.getJarPath()
							+ File.separator
							+ "Demo.mdb";
					Connection conn = DriverManager.getConnection(uri, "", "");
					String sql = "update tb_code set Width=" + width
							+ ",Height=" + height + " where stitle ='" + item
							+ "'";
					Statement statement = conn.createStatement();
					statement.executeUpdate(sql);
					statement.close();
					conn.close();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screenSize.width;
		int screenHeight = screenSize.height;
		this.setLocation((screenWidth - w) / 2, (screenHeight - h) / 2);

	}

	private void initComponents() {

		stitle = new java.awt.TextField();
		scontent = new java.awt.TextArea();
		stitle.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					String t = stitle.getText().trim();
					String c = scontent.getText().trim();
					int w = ShowTextWindow.this.getWidth();
					int h = ShowTextWindow.this.getHeight();
					if (t != null & t.length() != 0) {
						try {
							String uri = (new StringBuilder(
									"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="))
									.append(JarPath.getJarPath())
									.append(File.separator).append("Demo.mdb")
									.toString();
							Connection conn = DriverManager.getConnection(uri,
									"", "");
							String sql = "insert into tb_code(stitle,scontent,Width,Height) values(?,?,?,?)";
							PreparedStatement pstmt = conn
									.prepareStatement(sql);
							pstmt.setString(1, t);
							pstmt.setString(2, c);
							pstmt.setInt(3, w);
							pstmt.setInt(4, h);
							pstmt.execute();

							pstmt.close();
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						dispose();
					}

				}

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					try {
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

						}
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					break;

				default:
					break;
				}

			}
		});
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
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
												.addComponent(
														stitle,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addComponent(
														scontent,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														380, Short.MAX_VALUE))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addComponent(stitle,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(scontent,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										166, Short.MAX_VALUE).addContainerGap()));

		pack();
	}

}
