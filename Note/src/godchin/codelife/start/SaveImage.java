package godchin.codelife.start;

import godchin.codelife.control.JarPath;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

public class SaveImage {

	public static void doSave(BufferedImage img) {
		try {
			JFileChooser chooser = new JFileChooser();
			// 添加过滤器
			FileNameExtensionFilter jpgFilter = new FileNameExtensionFilter(
					"JPG/JPEG - JPG文件", "jpg");
			FileNameExtensionFilter gifFilter = new FileNameExtensionFilter(
					"GIF - Compuserve GIF", "gif");
			chooser.addChoosableFileFilter(gifFilter);
			chooser.addChoosableFileFilter(jpgFilter);
			// 打开保存对话框
			int result = chooser.showSaveDialog(null);
			if (result == JFileChooser.APPROVE_OPTION) {// 保存
				File file = chooser.getSelectedFile();
				String about = "jpg";// 文件类型
				String pathname = file.toString().toLowerCase();
				FileNameExtensionFilter filter = (FileNameExtensionFilter) chooser
						.getFileFilter();
				String ext = filter.getExtensions()[0];// 文件后缀名
				if ("jpg".equals(ext)) {
					if (!pathname.endsWith(".jpg")) {
						pathname += ".jpg";
						file = new File(pathname);
						about = "jpg";
					}
				} else if ("gif".equals(ext)) {
					if (!pathname.endsWith(".gif")) {
						pathname += ".gif";
						file = new File(pathname);
						about = "gif";
					}
				}

				if (ImageIO.write(img, about, file)) {
				} else {
					JOptionPane.showMessageDialog(null, "保存失败!");
				}
			}
		} catch (Exception e) {
		}
	}

	// ----------------
	public static void insertImage(BufferedImage img) throws Exception {
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

		int flag = jfc.showSaveDialog(null);
		if (flag == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			String path = file.getPath();
			if (!(path.endsWith(".jpg") || path.endsWith(".JPG"))) {
				path += ".jpg";
			}
			File newfile = new File(JarPath.getJarPath(), file.getName());
			ImageIO.write(img, "jpg", newfile);
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
		}

	}

}
