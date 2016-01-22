package godchin.codelife.view;

import godchin.codelife.control.JarPath;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateTextWindow extends javax.swing.JFrame {
	private java.awt.Button save;
	private java.awt.TextArea scontent;
	private java.awt.TextField stitle;

	public CreateTextWindow() {
		initComponents();
		this.setLocation(100, 50);
		this.setAlwaysOnTop(true);
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
				// TODO Auto-generated method stub

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
		setMinimumSize(new java.awt.Dimension(250, 200));
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void initComponents() {

		stitle = new java.awt.TextField();
		scontent = new java.awt.TextArea();

		save = new java.awt.Button();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();

			}

			protected void save() {
				String t = stitle.getText().trim();
				String c = scontent.getText().trim();
				int w = CreateTextWindow.this.getWidth();
				int h = CreateTextWindow.this.getHeight();
				if (t != null & t.length() != 0) {
					try {
						String uri = (new StringBuilder(
								"jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="))
								.append(JarPath.getJarPath())
								.append(File.separator).append("Demo.mdb")
								.toString();
						Connection conn = DriverManager.getConnection(uri, "",
								"");
						String sql = "insert into tb_code(stitle,scontent,Width,Height) values(?,?,?,?)";
						PreparedStatement pstmt = conn.prepareStatement(sql);

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
		});
		save.setLabel("save");
		stitle.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

			}

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:

					String t = stitle.getText().trim();
					String c = scontent.getText().trim();
					int w = CreateTextWindow.this.getWidth();
					int h = CreateTextWindow.this.getHeight();
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
					break;

				default:
					break;
				}
			}
		});

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
														scontent,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														416, Short.MAX_VALUE)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		stitle,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Integer.MAX_VALUE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		save,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.PREFERRED_SIZE)))));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(
														stitle,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE)
												.addComponent(
														save,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(scontent,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										162, Short.MAX_VALUE).addContainerGap()));

		pack();
	}

}
