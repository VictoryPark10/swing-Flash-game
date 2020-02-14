package ddong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

// ��ȣ ����ȭ�� (JFrame����, ������, Ű ������ ���)
public class Game3 extends JFrame implements Runnable, ActionListener, KeyListener {
	Image img = null; // ĳ���� �̹���
	Image fimg = null; // F �̹���
	Image bookimg = null; // å �̹���
	Image sojuimg = null; // ���� �̹���
	Image bobimg = null; // �� �̹���
	Image buffImage = null; // �����̹���
	Image BGimg = null; // ��� �̹���
	Image eimg = null; // ���� ��� ������ �̹���
	Image Endimg = null; // ���� ��� �̹���
	ImageIcon Endcimg = new ImageIcon("./images/SelectStudent3.png"); // ���� ĳ���� �̹���
	JLabel elbl = new JLabel(Endcimg); // ���� ĳ���� ���̺�
	Graphics buffg;
	JPanel jpEnd; // ���� �г�
	JButton btnBack = new JButton(new ImageIcon("./images/GotoStart.png"));
	int img_x = 275, img_y = 570; // ĳ���� �ʱ� ��ġ ����
	int countF, countBook, countSoju, countBob, countEnd; // �� ������ �������� �� �� ���� ����
	boolean KeyLeft = false; // �� ��ư �ν�
	boolean KeyRight = false; // �� ��ư �ν�
	DropF df; // DropF Ŭ���� ���� ����
	ArrayList F_List = new ArrayList(); // F
	ArrayList Book_List = new ArrayList(); // å
	ArrayList Soju_List = new ArrayList(); // ����
	ArrayList Bob_List = new ArrayList(); // ��
	ArrayList End_List = new ArrayList(); // ����
	int delay = 0; // ���� ������
	int score = 30; // �ʱ� ����
	int gamestate = 1; // ���� �ܰ躰�� ���ȭ�� �����ϱ� ���� ����
	Sound sound = new Sound(); // �����
	Sound soundItem = new Sound(); // ������, ĳ���� �浹 �� ȿ����
	Start s; // Start Ŭ���� ���� ����
	TimerTask timertask = new TimerTask() { // Ÿ�̸Ӱ� �� �� ����
		@Override
		public void run() {
			gamestate++;
			try {
				BGimg = ImageIO.read(new File("./images/gameBG" + gamestate + ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	public Game3() { // ������
		Timer timer = new Timer(); // Ÿ�̸� ����
		timer.scheduleAtFixedRate(timertask, 32500, 32500); // Ÿ�̸� �ð� ����
		try {
			img = new ImageIcon("./images/student3.png").getImage(); // ĳ���� �̹���
			fimg = new ImageIcon("./images/F.png").getImage(); // F������ �̹���
			bookimg = new ImageIcon("./images/book.png").getImage(); // å ������ �̹���
			sojuimg = new ImageIcon("./images/soju.png").getImage(); // ���� �̹���
			bobimg = new ImageIcon("./images/bob.png").getImage(); // �� �̹���
			eimg = new ImageIcon("./images/end.png").getImage(); // �� �̹���
			BGimg = ImageIO.read(new File("./images/GameBG1.png")); // ��� �̹���
			Endimg = ImageIO.read(new File("./images/SelectBG.png")); // ���� ��� �̹���
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("no image");
			System.exit(1);
		}
		sound.sound("gamebgm.wav", true); // ���� �����

		// jpEnd ��� �׸���
		jpEnd = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(Endimg, 0, 0, null);
				g.setFont(new Font("Default", Font.BOLD, 30));
				g.drawString("���� : " + score, 240, 300);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		// jpEnd ���� �г� ����
		jpEnd.setLayout(null);
		jpEnd.setSize(100, 100);
		elbl.setBounds(230, 330, 130, 200);
		btnBack.setBounds(230, 550, 150, 50);
		btnBack.addActionListener(this);

		// jpEnd �����гο� �߰�
		jpEnd.add(btnBack);
		jpEnd.add(elbl);

		// JFrame ����
		setTitle("Dodge Fail");
		addKeyListener(this);
		setSize(600, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(!false);
	}

	// ������ run �޼ҵ�
	public void run() {
		try {
			while (true) {
				KeyProcess();
				switch (gamestate) {
				case 1: // 1��������, �� (F, å)
					countF++;
					countBook++;
					if (countF % 30 == 0)
						FProcess();
					if (countBook % 50 == 0)
						BookProcess();
					break;
				case 2: // 2��������, ���� (F, å, ����)
					countF++;
					countBook++;
					countSoju++;
					if (countF % 30 == 0)
						FProcess();
					if (countBook % 50 == 0)
						BookProcess();
					if (countSoju % 50 == 0)
						SojuProcess();
					break;
				case 3: // 3��������, ����(F, å, ����, ��)
					countF++;
					countBook++;
					countSoju++;
					countBob++;
					if (countF % 30 == 0)
						FProcess();
					if (countBook % 50 == 0)
						BookProcess();
					if (countSoju % 50 == 0)
						SojuProcess();
					if (countBob % 50 == 0)
						BobProcess();
					break;
				case 4: // 4��������, �ܿ� (F �� ����, å, ����, ��)
					countF++;
					countBook++;
					countSoju++;
					countBob++;
					if (countF % 20 == 0)
						FProcess();
					if (countBook % 50 == 0)
						BookProcess();
					if (countSoju % 50 == 0)
						SojuProcess();
					if (countBob % 50 == 0)
						BobProcess();
					break;
				case 5: // ������������. ���������� ��� �� �г���ȯ
					delay++;
					countEnd++;
					if (countEnd % 100 == 0) {
						EndingProcess();
					}
					if (delay == 200) {
						getContentPane().removeAll();
						getContentPane().add(jpEnd);
						jpEnd.repaint();
						revalidate();
						s.th_stop();
					}
					break;
				}
				repaint();
				Thread.sleep(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// F ������ ��� ���μ���
	public void FProcess() {
		df = new DropF((int) (Math.random() * 550), 0);
		F_List.add(df);
	}

	// å ������ ��� ���μ���
	public void BookProcess() {
		df = new DropF((int) (Math.random() * 550), 0);
		Book_List.add(df);
	}

	// ���� ������ ��� ���μ���
	public void SojuProcess() {
		df = new DropF((int) (Math.random() * 550), 0);
		Soju_List.add(df);
	}

	// �� ������ ��� ���μ���
	public void BobProcess() {
		df = new DropF((int) (Math.random() * 550), 0);
		Bob_List.add(df);
	}

	// ���� ������ ��� ���μ���
	public void EndingProcess() {
		df = new DropF((int) 140, 0);
		End_List.add(df);
	}

	// F ������ �׸���
	public void Draw_F() {
		for (int i = 0; i < F_List.size(); ++i) {
			df = (DropF) (F_List.get(i));
			buffg.drawImage(fimg, df.x, df.y, this);
			df.drop();

			if (df.y > 700) {
				F_List.remove(i);
			} else if (Crash(img_x, img_y, df.x, df.y, img, fimg)) {
				soundItem.sound("crash.wav", false);
				F_List.remove(i);
				score -= 3;
			}
		}
	}

	// å ������ �׸���
	public void Draw_Book() {
		for (int i = 0; i < Book_List.size(); ++i) {
			df = (DropF) (Book_List.get(i));
			buffg.drawImage(bookimg, df.x, df.y, this);
			df.drop();

			if (df.y > 700) {
				Book_List.remove(i);
			} else if (Crash(img_x, img_y, df.x, df.y, img, bookimg)) {
				soundItem.sound("crash.wav", false);
				Book_List.remove(i);
				score += 2;
			}
		}
	}

	// ���� ������ �׸���
	public void Draw_Soju() {
		for (int i = 0; i < Soju_List.size(); ++i) {
			df = (DropF) (Soju_List.get(i));
			buffg.drawImage(sojuimg, df.x, df.y, this);
			df.drop();

			if (df.y > 700) {
				Soju_List.remove(i);
			} else if (Crash(img_x, img_y, df.x, df.y, img, sojuimg)) {
				soundItem.sound("crash.wav", false);
				Soju_List.remove(i);
				score -= 1;
			}
		}
	}

	// �� ������ �׸���
	public void Draw_Bob() {
		for (int i = 0; i < Bob_List.size(); ++i) {
			df = (DropF) (Bob_List.get(i));
			buffg.drawImage(bobimg, df.x, df.y, this);
			df.drop();

			if (df.y > 700) {
				Bob_List.remove(i);
			} else if (Crash(img_x, img_y, df.x, df.y, img, bobimg)) {
				soundItem.sound("crash.wav", false);
				Bob_List.remove(i);
				score += 4;
			}
		}
	}

	// ���� ������ �׸���
	public void Draw_End() {
		for (int i = 0; i < End_List.size(); ++i) {
			df = (DropF) (End_List.get(i));
			buffg.drawImage(eimg, df.x, df.y, this);
			df.drop1();
		}
	}

	// ��, �� Ű ������ ���� �� ���� �� true�� ����
	@Override
	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_LEFT:
			KeyLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			KeyRight = true;
			break;
		}
	}

	// ��, �� Ű ��� ���� �� ���� �� false�� ����
	@Override
	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		switch (keycode) {
		case KeyEvent.VK_LEFT:
			KeyLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			KeyRight = false;
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	// ��, �� �� true �� �� ĳ���� x��ǥ �̵�
	public void KeyProcess() {
		if (KeyLeft == true && img_x > 0)
			img_x -= 5;
		if (KeyRight == true && img_x < 550)
			img_x += 5;
	}

	// paint �޼ҵ�
	public void paint(Graphics g) {
		buffImage = createImage(600, 700);
		buffg = buffImage.getGraphics();
		buffg.drawImage(BGimg, 0, 0, this);
		buffg.drawImage(img, img_x, img_y, this);
		buffg.setFont(new Font("Default", Font.BOLD, 30));
		buffg.setColor(Color.RED);
		buffg.drawString("���� : " + score, 450, 60);
		buffg.setColor(Color.BLACK);
		if (gamestate == 1) {
			buffg.drawString("��", 20, 60);
		} else if (gamestate == 2) {
			buffg.drawString("����", 20, 60);
		} else if (gamestate == 3) {
			buffg.drawString("����", 20, 60);
		} else if (gamestate == 4) {
			buffg.drawString("�ܿ�", 20, 60);
		}
		Draw_F();
		Draw_Book();
		Draw_Soju();
		Draw_Bob();
		Draw_End();
		g.drawImage(buffImage, 0, 0, this);
	}

	public void update(Graphics g) {
		paint(g);
	}

	// �浹������
	public boolean Crash(int x1, int y1, int x2, int y2, Image img1, Image img2) {
		boolean check = false;
		if (Math.abs((x1 + img1.getWidth(null) / 2) - (x2 + img2.getWidth(null) / 2)) < (img2.getWidth(null) / 2
				+ img1.getWidth(null) / 2)
				&& Math.abs((y1 + img1.getHeight(null) / 2)
						- (y2 + img2.getHeight(null) / 2)) < (img2.getHeight(null) / 2 + img1.getHeight(null) / 2)) {
			check = true;
		} else {
			check = false;
		}
		return check;
	}

	// btnBack Ŭ�� �� �޼ҵ�
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.dispose();
		sound.sound_stop();
		Start st = new Start();
	}

}