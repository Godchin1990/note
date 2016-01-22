package godchin.codelife.start;

import godchin.codelife.control.JarPath;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.JWindow;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class ScreenShotWindow extends JWindow {
	private int orgx, orgy, endx, endy;
	private BufferedImage image = null;
	private BufferedImage tempImage = null;
	private BufferedImage saveImage = null;

	private ToolsWindow tools = null;

	public ScreenShotWindow() throws AWTException {
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setBounds(0, 0, d.width, d.height);

		Robot robot = new Robot();
		image = robot
				.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				orgx = e.getX();
				orgy = e.getY();

				if (tools != null) {
					tools.setVisible(false);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (tools == null) {
					tools = new ToolsWindow(ScreenShotWindow.this, e.getX(), e
							.getY());
				} else {
					tools.setLocation(e.getX(), e.getY());
				}
				tools.setVisible(true);
				tools.toFront();
			}
		});

		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				endx = e.getX();
				endy = e.getY();

				Image tempImage2 = createImage(
						ScreenShotWindow.this.getWidth(),
						ScreenShotWindow.this.getHeight());
				Graphics g = tempImage2.getGraphics();
				g.drawImage(tempImage, 0, 0, null);
				int x = Math.min(orgx, endx);
				int y = Math.min(orgy, endy);
				int width = Math.abs(endx - orgx) + 1;
				int height = Math.abs(endy - orgy) + 1;
				g.setColor(Color.BLUE);
				g.drawRect(x - 1, y - 1, width + 1, height + 1);
				saveImage = image.getSubimage(x, y, width, height);
				g.drawImage(saveImage, x, y, null);

				ScreenShotWindow.this.getGraphics().drawImage(tempImage2, 0, 0,
						ScreenShotWindow.this);
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		RescaleOp ro = new RescaleOp(0.8f, 0, null);
		tempImage = ro.filter(image, null);
		g.drawImage(tempImage, 0, 0, this);
	}

	public void saveImage() throws IOException {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("save");

		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG",
				"jpg");
		jfc.setFileFilter(filter);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
		String fileName = sdf.format(new Date());
		File filePath = FileSystemView.getFileSystemView().getHomeDirectory();
		File defaultFile = new File(filePath + File.separator + fileName
				+ ".jpg");
		jfc.setSelectedFile(defaultFile);

		int flag = jfc.showSaveDialog(this);
		if (flag == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			String path = file.getPath();
			if (!(path.endsWith(".jpg") || path.endsWith(".JPG"))) {
				path += ".jpg";
			}
			ImageIO.write(saveImage, "jpg", new File(path));
			getParent().setVisible(false);
		}
	}

	// --------------
	public void insertImage() throws Exception {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("save");

		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG",
				"jpg");
		jfc.setFileFilter(filter);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
		// String fileName = sdf.format(new Date());
		String fileName = sdf.format(new Date(System.currentTimeMillis()));
		File filePath = FileSystemView.getFileSystemView().getHomeDirectory();
		File defaultFile = new File(filePath + File.separator + fileName
				+ ".jpg");
		jfc.setSelectedFile(defaultFile);

		int flag = jfc.showSaveDialog(this);
		if (flag == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			String path = file.getPath();
			if (!(path.endsWith(".jpg") || path.endsWith(".JPG"))) {
				path += ".jpg";
			}
			File newfile = new File(JarPath.getJarPath(), file.getName());
			ImageIO.write(saveImage, "jpg", newfile);
			InputStream is = new FileInputStream(newfile);
			String t = newfile.getName();
			byte[] b = new byte[is.available()];
			is.read(b);
			// win8连接mdb数据库
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String uri = "jdbc:odbc:DRIVER=Microsoft Access Driver (*.mdb, *.accdb);DBQ="
					+ JarPath.getJarPath() + File.separator + "Demo.mdb";
			Connection conn = DriverManager.getConnection(uri, "", "");
			String sql = "insert into tb_code(stitle,pic_size,pic_date,Width,Height) 								values(?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			String title = t.substring(0, t.lastIndexOf("."));

			pstmt.setString(1, title);
			pstmt.setString(2, newfile.length() + "");
			pstmt.setBytes(3, b);
			pstmt.setString(4, 500 + "");
			pstmt.setString(5, 400 + "");
			pstmt.execute();
			is.close();
			pstmt.close();
			conn.close();
			getParent().setVisible(false);
		}

	}
}

class ToolsWindow extends JWindow {
	private ScreenShotWindow parent;

	public ToolsWindow(ScreenShotWindow parent, int x, int y) {
		this.parent = parent;

		this.init();

		this.setLocation(x, y);
		this.pack();
		this.setVisible(true);

	}

	public static ImageIcon getImageIcon(String name) {
		return new ImageIcon(ClassLoader.getSystemResource(name));
	}

	private void init() {

		this.setLayout(new BorderLayout());
		JToolBar toolBar = new JToolBar("capture");

		JButton saveButton = null;
		try {
			saveButton = new JButton(new ImageIcon(ImageIO.read(this.getClass()
					.getResource("/save.jpg"))));
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		saveButton.setBackground(Color.GREEN);

		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					parent.saveImage();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		toolBar.add(saveButton);
		JButton insertButton = null;
		try {
			insertButton = new JButton(new ImageIcon(ImageIO.read(this
					.getClass().getResource("/insert.jpg"))));
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		insertButton.setBackground(Color.BLUE);

		insertButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					parent.insertImage();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		toolBar.add(insertButton);

		JButton closeButton = null;
		try {
			closeButton = new JButton(new ImageIcon(ImageIO.read(this
					.getClass().getResource("/close.jpg"))));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		closeButton.setBackground(Color.RED);

		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getParent().setVisible(false);
			}
		});
		toolBar.add(closeButton);

		this.add(toolBar, BorderLayout.NORTH);
	}
}
