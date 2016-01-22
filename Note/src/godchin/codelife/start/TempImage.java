package godchin.codelife.start;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TempImage extends JPanel implements MouseListener,
		MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TempImage() {
	}

	public TempImage(JFrame jFrame, BufferedImage bufferedImage, int width,
			int height) {
		this.jFrame = jFrame;
		this.bufferedImage = bufferedImage;
		this.width = width;
		this.height = height;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		// �������
		java.net.URL path = this.getClass().getResource("/mouse.gif");
		Image cursor = Toolkit.getDefaultToolkit().createImage(path);
		cs = Toolkit.getDefaultToolkit().createCustomCursor(cursor,
				new Point(0, 0), "cursor");
		this.setCursor(cs);

		inition();
	}

	public void inition() {// ��ʼ��
		controlPoint = new Rectangle[8];
		for (int i = 0; i < controlPoint.length; i++) {
			controlPoint[i] = new Rectangle();
		}
	}

	private JFrame jFrame = null;
	private BufferedImage bufferedImage = null;
	private int width = 0;
	private int height = 0;
	private int startX, startY, endX, endY, tempX, tempY;
	private Status currentStatus = Status.DEFAULT;// ���״̬
	private Rectangle select = null; // ��ǰѡ��
	private Rectangle[] controlPoint;// 8�����Ƶ�
	private Cursor cs;

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		// ����ͼ��
		g.drawImage(bufferedImage, 0, 0, width, height, this);
		// ����ѡ��
		g.setColor(Color.RED);
		g.drawLine(startX, startY, endX, startY);// ��
		g.drawLine(startX, endY, endX, endY); // ��
		g.drawLine(startX, startY, startX, endY);// ��
		g.drawLine(endX, startY, endX, endY); // ��
		// ����ѡ��
		select = new Rectangle(startX, startY, Math.abs(endX - startX),
				Math.abs(endY - startY));
		// ���ƿ��Ƶ�
		int mid_x = (startX + endX) / 2;
		int mid_y = (startY + endY) / 2;
		g.fillRect(startX - 2, startY - 2, 5, 5);// ����
		g.fillRect(mid_x - 2, startY - 2, 5, 5); // ����
		g.fillRect(endX - 2, startY - 2, 5, 5); // ����

		g.fillRect(startX - 2, endY - 2, 5, 5); // ����
		g.fillRect(mid_x - 2, endY - 2, 5, 5); // ����
		g.fillRect(endX - 2, endY - 2, 5, 5); // ����

		g.fillRect(startX - 2, mid_y - 2, 5, 5); // ��
		g.fillRect(endX - 2, mid_y - 2, 5, 5); // ��
		// ������Ƶ���Ϣ
		controlPoint[0] = new Rectangle(endX - 5, mid_y - 5, 10, 10); // ��
		controlPoint[1] = new Rectangle(mid_x - 5, endY - 5, 10, 10); // ��
		controlPoint[2] = new Rectangle(startX - 5, mid_y - 5, 10, 10); // ��
		controlPoint[3] = new Rectangle(mid_x - 5, startY - 5, 10, 10); // ��

		controlPoint[4] = new Rectangle(endX - 5, startY - 5, 10, 10); // ����
		controlPoint[5] = new Rectangle(endX - 5, endY - 5, 10, 10); // ����
		controlPoint[6] = new Rectangle(startX - 5, endY - 5, 10, 10); // ����
		controlPoint[7] = new Rectangle(startX - 5, startY - 5, 10, 10);// ����

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		tempX = e.getX();
		tempY = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.isPopupTrigger()) {// ����Ҽ�
			if (Status.MOVE == this.currentStatus) {
				// ����ѡ����֮�ڵ����Ҽ� -> ���ѡ�� �������ͼ��
				startX = 0;
				startY = 0;
				endX = 0;
				endY = 0;
				repaint();// �ػ�
			} else {
				// ����ѡ����֮�ⵯ���Ҽ� -> ���ͼ��
				this.bufferedImage = null;
				this.jFrame.dispose();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		// ��������
		int temp = 0;
		if (startX > endX) {
			temp = startX;
			startX = endX;
			endX = temp;
		}
		if (startY > endY) {
			temp = startY;
			startY = endY;
			endY = temp;
		}

		int x = e.getX();
		int y = e.getY();
		if (Status.MOVE == this.currentStatus) {// �ƶ�ѡ��
			startX += (x - tempX);
			startY += (y - tempY);
			endX += (x - tempX);
			endY += (y - tempY);
			tempX = x;
			tempY = y;
		} else if (Status.EAST == this.currentStatus) {// ��
			// startX
			// startY
			endX += (x - tempX);
			// endY
			tempX = x;
		} else if (Status.SOUTH == this.currentStatus) {// ��
			// startX
			// startY
			// endX
			endY += (y - tempY);
			tempY = y;
		} else if (Status.WEST == this.currentStatus) {// ��
			startX += (x - tempX);
			// startY
			// endX
			// endY
			tempX = x;
		} else if (Status.NORTH == this.currentStatus) {// ��
			// startX
			startY += (y - tempY);
			// endX
			// endY
			tempY = y;
		} else if (Status.NORTH_EAST == this.currentStatus) {// ����
			// startX
			startY += (y - tempY);
			endX += (x - tempX);
			// endY
			tempX = x;
			tempY = y;
		} else if (Status.SOUTH_EAST == this.currentStatus) {// ����
			// startX
			// startY
			endX += (x - tempX);
			endY += (y - tempY);
			tempX = x;
			tempY = y;
		} else if (Status.SOUTH_WEST == this.currentStatus) {// ����
			startX += (x - tempX);
			// startY
			// endX
			endY += (y - tempY);
			tempX = x;
			tempY = y;
		} else if (Status.NORTH_WEST == this.currentStatus) {// ����
			startX += (x - tempX);
			startY += (y - tempY);
			// endX
			// endY
			tempX = x;
			tempY = y;
		} else {// ������ѡ��
			startX = tempX;
			startY = tempY;
			endX = x;
			endY = y;
		}
		this.repaint();// �ػ�ѡ��
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (select == null)
			return;

		if (select.contains(e.getPoint())) {
			this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			this.currentStatus = Status.MOVE;
		} else {
			Status[] st = Status.values();
			for (int i = 0; i < controlPoint.length; i++) {
				if (controlPoint[i].contains(e.getPoint())) {
					this.setCursor(st[i].getCursor());
					this.currentStatus = st[i];
					return;
				}
			}
			this.setCursor(cs);
			this.currentStatus = Status.DEFAULT;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2) {// ˫�����
			if (select.contains(e.getPoint())) {
				BufferedImage img;
				if (select.x + select.width < this.getWidth()
						&& select.y + select.height < this.getHeight()) {
					img = bufferedImage.getSubimage(select.x, select.y,
							select.width, select.height);

				} else {
					int w = select.width;
					int h = select.height;
					if (select.x + select.width >= this.getWidth()) {
						w = this.getWidth() - select.x;
					}
					if (select.y + select.height >= this.getHeight()) {
						h = this.getHeight() - select.y;
					}

					img = bufferedImage.getSubimage(select.x, select.y, w, h);
				}
				this.jFrame.dispose();
				// SaveImage.doSave(img);
				try {
					SaveImage.insertImage(img);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	enum Status {// ���״̬
		EAST(new Cursor(Cursor.E_RESIZE_CURSOR)), // ��
		SOUTH(new Cursor(Cursor.S_RESIZE_CURSOR)), // ��
		WEST(new Cursor(Cursor.W_RESIZE_CURSOR)), // ��
		NORTH(new Cursor(Cursor.N_RESIZE_CURSOR)), // ��
		NORTH_EAST(new Cursor(Cursor.NE_RESIZE_CURSOR)), // ����
		SOUTH_EAST(new Cursor(Cursor.SE_RESIZE_CURSOR)), // ����
		SOUTH_WEST(new Cursor(Cursor.SW_RESIZE_CURSOR)), // ����
		NORTH_WEST(new Cursor(Cursor.NW_RESIZE_CURSOR)), // ����
		DEFAULT(new Cursor(Cursor.DEFAULT_CURSOR)), // Ĭ��
		MOVE(new Cursor(Cursor.MOVE_CURSOR)); // �ƶ�
		private Cursor cs;

		Status(Cursor cs) {
			this.cs = cs;
		}

		public Cursor getCursor() {
			return this.cs;
		}
	}
}
