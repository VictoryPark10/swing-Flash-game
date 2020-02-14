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

// 진호 게임화면 (JFrame상태, 스레드, 키 리스너 상속)
public class Game3 extends JFrame implements Runnable, ActionListener, KeyListener {
	Image img = null; // 캐릭터 이미지
	Image fimg = null; // F 이미지
	Image bookimg = null; // 책 이미지
	Image sojuimg = null; // 소주 이미지
	Image bobimg = null; // 밥 이미지
	Image buffImage = null; // 버퍼이미지
	Image BGimg = null; // 배경 이미지
	Image eimg = null; // 엔딩 드롭 아이템 이미지
	Image Endimg = null; // 엔딩 배경 이미지
	ImageIcon Endcimg = new ImageIcon("./images/SelectStudent3.png"); // 엔딩 캐릭터 이미지
	JLabel elbl = new JLabel(Endcimg); // 엔딩 캐릭터 레이블
	Graphics buffg;
	JPanel jpEnd; // 엔딩 패널
	JButton btnBack = new JButton(new ImageIcon("./images/GotoStart.png"));
	int img_x = 275, img_y = 570; // 캐릭터 초기 위치 설정
	int countF, countBook, countSoju, countBob, countEnd; // 각 아이템 떨어지는 빈도 수 조절 변수
	boolean KeyLeft = false; // ← 버튼 인식
	boolean KeyRight = false; // → 버튼 인식
	DropF df; // DropF 클래스 참조 변수
	ArrayList F_List = new ArrayList(); // F
	ArrayList Book_List = new ArrayList(); // 책
	ArrayList Soju_List = new ArrayList(); // 소주
	ArrayList Bob_List = new ArrayList(); // 밥
	ArrayList End_List = new ArrayList(); // 엔딩
	int delay = 0; // 엔딩 딜레이
	int score = 30; // 초기 점수
	int gamestate = 1; // 게임 단계별로 배경화면 변경하기 위한 변수
	Sound sound = new Sound(); // 배경음
	Sound soundItem = new Sound(); // 아이템, 캐릭터 충돌 시 효과음
	Start s; // Start 클래스 참조 변수
	TimerTask timertask = new TimerTask() { // 타이머가 할 일 설정
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

	public Game3() { // 생성자
		Timer timer = new Timer(); // 타이머 생성
		timer.scheduleAtFixedRate(timertask, 32500, 32500); // 타이머 시간 설정
		try {
			img = new ImageIcon("./images/student3.png").getImage(); // 캐릭터 이미지
			fimg = new ImageIcon("./images/F.png").getImage(); // F아이템 이미지
			bookimg = new ImageIcon("./images/book.png").getImage(); // 책 아이템 이미지
			sojuimg = new ImageIcon("./images/soju.png").getImage(); // 소주 이미지
			bobimg = new ImageIcon("./images/bob.png").getImage(); // 밥 이미지
			eimg = new ImageIcon("./images/end.png").getImage(); // 끝 이미지
			BGimg = ImageIO.read(new File("./images/GameBG1.png")); // 배경 이미지
			Endimg = ImageIO.read(new File("./images/SelectBG.png")); // 엔딩 배경 이미지
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("no image");
			System.exit(1);
		}
		sound.sound("gamebgm.wav", true); // 게임 배경음

		// jpEnd 배경 그리기
		jpEnd = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(Endimg, 0, 0, null);
				g.setFont(new Font("Default", Font.BOLD, 30));
				g.drawString("점수 : " + score, 240, 300);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		// jpEnd 엔딩 패널 설정
		jpEnd.setLayout(null);
		jpEnd.setSize(100, 100);
		elbl.setBounds(230, 330, 130, 200);
		btnBack.setBounds(230, 550, 150, 50);
		btnBack.addActionListener(this);

		// jpEnd 엔딩패널에 추가
		jpEnd.add(btnBack);
		jpEnd.add(elbl);

		// JFrame 설정
		setTitle("Dodge Fail");
		addKeyListener(this);
		setSize(600, 700);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(!false);
	}

	// 스레드 run 메소드
	public void run() {
		try {
			while (true) {
				KeyProcess();
				switch (gamestate) {
				case 1: // 1스테이지, 봄 (F, 책)
					countF++;
					countBook++;
					if (countF % 30 == 0)
						FProcess();
					if (countBook % 50 == 0)
						BookProcess();
					break;
				case 2: // 2스테이지, 여름 (F, 책, 소주)
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
				case 3: // 3스테이지, 가을(F, 책, 소주, 밥)
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
				case 4: // 4스테이지, 겨울 (F 더 많이, 책, 소주, 밥)
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
				case 5: // 엔딩스테이지. 엔딩아이텝 드랍 후 패널전환
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

	// F 아이템 드랍 프로세스
	public void FProcess() {
		df = new DropF((int) (Math.random() * 550), 0);
		F_List.add(df);
	}

	// 책 아이템 드랍 프로세스
	public void BookProcess() {
		df = new DropF((int) (Math.random() * 550), 0);
		Book_List.add(df);
	}

	// 소주 아이텝 드랍 프로세스
	public void SojuProcess() {
		df = new DropF((int) (Math.random() * 550), 0);
		Soju_List.add(df);
	}

	// 밥 아이텝 드랍 프로세스
	public void BobProcess() {
		df = new DropF((int) (Math.random() * 550), 0);
		Bob_List.add(df);
	}

	// 엔딩 아이텝 드랍 프로세스
	public void EndingProcess() {
		df = new DropF((int) 140, 0);
		End_List.add(df);
	}

	// F 아이템 그리기
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

	// 책 아이템 그리기
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

	// 소주 아이템 그리기
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

	// 밥 아이템 그리기
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

	// 엔딩 아이템 그리기
	public void Draw_End() {
		for (int i = 0; i < End_List.size(); ++i) {
			df = (DropF) (End_List.get(i));
			buffg.drawImage(eimg, df.x, df.y, this);
			df.drop1();
		}
	}

	// 좌, 우 키 누르고 있을 시 변수 값 true로 설정
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

	// 좌, 우 키 뗴고 있을 시 변수 값 false로 설정
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

	// 좌, 우 값 true 일 시 캐릭터 x좌표 이동
	public void KeyProcess() {
		if (KeyLeft == true && img_x > 0)
			img_x -= 5;
		if (KeyRight == true && img_x < 550)
			img_x += 5;
	}

	// paint 메소드
	public void paint(Graphics g) {
		buffImage = createImage(600, 700);
		buffg = buffImage.getGraphics();
		buffg.drawImage(BGimg, 0, 0, this);
		buffg.drawImage(img, img_x, img_y, this);
		buffg.setFont(new Font("Default", Font.BOLD, 30));
		buffg.setColor(Color.RED);
		buffg.drawString("점수 : " + score, 450, 60);
		buffg.setColor(Color.BLACK);
		if (gamestate == 1) {
			buffg.drawString("봄", 20, 60);
		} else if (gamestate == 2) {
			buffg.drawString("여름", 20, 60);
		} else if (gamestate == 3) {
			buffg.drawString("가을", 20, 60);
		} else if (gamestate == 4) {
			buffg.drawString("겨울", 20, 60);
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

	// 충돌방정식
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

	// btnBack 클릭 시 메소드
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.dispose();
		sound.sound_stop();
		Start st = new Start();
	}

}