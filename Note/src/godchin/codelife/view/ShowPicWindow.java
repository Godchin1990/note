package godchin.codelife.view;

import godchin.codelife.control.FrameUtil;
import godchin.codelife.control.JarPath;

import java.awt.Frame;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ShowPicWindow extends Frame {
	private File f;
	private FileImageOutputStream out;
	private FileImageInputStream in;
	private ImageIcon icon;
	private Image image;
	private Frame frame;
	private JLabel jLabel;

	public ShowPicWindow(final String t, int w, int h, byte[] p) {
		this.setVisible(false);

		try {
			f = new File(JarPath.getJarPath(), t + ".jpg");
			out = new FileImageOutputStream(f);
			out.write(p);
			frame = new Frame();

			in = new FileImageInputStream(f);
			image = ImageIO.read(in);

			// image=getToolkit().getImage(t+".jpg");//no

			// image =ImageIO.read(getClass().getResource(t+".jpg"));//no

			/*
			 * URL url=getClass().getResource(t+".jpg"); ImageIcon imageIcon=new
			 * ImageIcon(url); image=imageIcon.getImage();
			 */// no
			icon = new ImageIcon(image);
			jLabel = new JLabel(icon);
			frame.setTitle(t);
			frame.setSize(w, h);
			frame.setResizable(true);
			FrameUtil.setCenter(frame);
			frame.add(jLabel);
			frame.setVisible(true);
			out.close();
			frame.addFocusListener(new FocusListener() {

				@Override
				public void focusLost(FocusEvent e) {

				}

				@Override
				public void focusGained(FocusEvent e) {
					// TODO Auto-generated method stub

				}
			});

			frame.addWindowListener(new WindowListener() {

				@Override
				public void windowOpened(WindowEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void windowIconified(WindowEvent e) {

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
					// 修改大小

					f.delete();
					// win8连接mdb数据库

					frame.dispose();

				}

				@Override
				public void windowClosed(WindowEvent e) {
					int width = frame.getWidth();
					int height = frame.getHeight();
					// System.out.println("closed:"+width);
					try {
						Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
						String uri = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="
								+ JarPath.getJarPath()
								+ File.separator
								+ "Demo.mdb";
						Connection conn = DriverManager.getConnection(uri, "",
								"");
						String sql = "update tb_code set Width=" + width
								+ ",Height=" + height + " where stitle ='" + t
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
				public void windowActivated(WindowEvent e) {
					// TODO Auto-generated method stub

				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	};

}
