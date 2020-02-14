package ddong;

import java.awt.event.*;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class Start extends JFrame implements ActionListener, MouseListener {

	public JPanel jpStart; // 초기화면 패널 생성
	
	ImageIcon imgMain = new ImageIcon("./images/MainBG.png");
	ImageIcon imgSelect = new ImageIcon("./images/SelectBG.png");
	
	JButton btnStart = new JButton(new ImageIcon("./images/StartButton.png")); // 시작하기 버튼
	JButton btnInfo = new JButton(new ImageIcon("./images/InfoButton.png")); // 게임정보보기 버튼
	JButton btnJinho = new JButton(new ImageIcon("./images/SelectStudent1.png")); // 진호 선택버튼
	JButton btnDaeyoung = new JButton(new ImageIcon("./images/SelectStudent2.png")); // 대영 선택버튼
	JButton btnSeungri = new JButton(new ImageIcon("./images/SelectStudent3.png")); // 승리 선택버튼
	public JButton btnBack = new JButton(new ImageIcon("./images/BackButton.png")); // 뒤로가기 버튼
	JButton btnBack2 = new JButton(new ImageIcon("./images/BackButton.png")); // 뒤로가기 버튼
	
	JLabel lbTitle = new JLabel(); // 게임 설명패널 타이틀
	JLabel lbJ = new JLabel(); // 진호 이름
	JLabel lbD = new JLabel(); // 대영 이름
	JLabel lbS = new JLabel(); // 승리 이름
	JLabel Jinfo = new JLabel(); // 진호 설명
	JLabel Dinfo = new JLabel(); // 대영 설명
	JLabel Sinfo = new JLabel(); // 승리 설명
	JLabel Info = new JLabel(); // 게임 설명
	
	Sound sound = new Sound();
	
	Thread th;

	JPanel jpSelect = new JPanel() { // 캐릭터 선택 패널
		public void paintComponent(Graphics g) {
			g.drawImage(imgSelect.getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};
	
	JPanel jpInfo = new JPanel() { // 게임정보 패널
		public void paintComponent(Graphics g) {
			g.drawImage(imgSelect.getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};

	public Start() { // 생성자
		sound.sound("mainbgm.wav", true); // 초기 배경음악(캐릭터 선택 시 종료됨)
		
		jpStart = new JPanel() { // 초기화면 패널 그리기
			public void paintComponent(Graphics g) {
				g.drawImage(imgMain.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		// jpStart 게임 초기화면
		jpStart.setLayout(null);

		btnInfo.setBounds(230, 550, 150, 50);
		btnInfo.addActionListener(this);
		btnStart.setBounds(230, 480, 150, 50);
		btnStart.addActionListener(this);
		
		// jpStart 게임 초기화면에 추가
		jpStart.add(btnInfo);
		jpStart.add(btnStart);
		
		// jpSelect 캐릭터 선택 패널
		jpSelect.setLayout(null);

		lbJ.setText("김진호");
		lbJ.setBounds(130, 250, 100, 100);
		lbD.setText("정대영");
		lbD.setBounds(280, 250, 100, 100);
		lbS.setText("박승리");
		lbS.setBounds(430, 250, 100, 100);
		Jinfo.setText("<html>" + "<div style=\"text-align:center\">" + "이 캐릭터는 술을 밥 먹듯이 합니다." + "<br>" + "술 점수 +1!"
				+ "</div>" + "</html>");
		Jinfo.setBounds(200, 70, 300, 100);
		Jinfo.setVisible(false);
		Dinfo.setText("<html>" + "<div style=\"text-align:center\">" + "이 캐릭터는 학점밖에 모르는 바보입니다." + "<br>" + "책 점수 +3!"
				+ "</div>" + "</html>");
		Dinfo.setBounds(195, 70, 300, 100);
		Dinfo.setVisible(false);
		Sinfo.setText("<html>" + "<div style=\"text-align:center\">" + "이 캐릭터는 배가 고픕니다." + "<br>" + "밥 점수 +4!"
				+ "</div>" + "</html>");
		Sinfo.setBounds(220, 70, 300, 100);
		Sinfo.setVisible(false);

		btnJinho.setBounds(80, 330, 130, 200);
		btnJinho.addActionListener(this);
		btnJinho.addMouseListener(this);
		
		btnDaeyoung.setBounds(230, 330, 130, 200);
		btnDaeyoung.addActionListener(this);
		btnDaeyoung.addMouseListener(this);
		
		btnSeungri.setBounds(380, 330, 130, 200);
		btnSeungri.addActionListener(this);
		btnSeungri.addMouseListener(this);

		btnBack.setBounds(230, 550, 150, 50);
		btnBack.addActionListener(this);

		// jpSelect 캐릭터 선택 패널에 추가
		jpSelect.add(lbTitle);
		jpSelect.add(lbJ);
		jpSelect.add(lbD);
		jpSelect.add(lbS);
		jpSelect.add(Jinfo);
		jpSelect.add(Dinfo);
		jpSelect.add(Sinfo);
		jpSelect.add(btnJinho);
		jpSelect.add(btnDaeyoung);
		jpSelect.add(btnSeungri);
		jpSelect.add(btnBack);
		
		// jpInfo 게임정보 패널
		jpInfo.setLayout(null);
		
		Info.setText("<html>" + "<div style=\"text-align:center\">" + " 대학교에 왔다...." + "<br>"
				+ "아무것도 모르는채로 대학교에 간 3명의 귀여운 새내기들!!" + "<br>" + "대학생활의 낭만은 알려주었지만" + "<br>"
				+ "F와 다른 위험한 존재들은 아무도 알려주지 않았다..." + "<br>" + "열심히 움직여 험난한 대학생활을 무사히 마쳐보자!!" + "<br>" + "<br>"
				+ "좌우 키를 움직여서 떨어지는 F를 피해보세요." + "<br>" + "<br>" + "F는 -3점" + "<br>" + "책은  +2점" + "<br>" + "밥은 +2점"
				+ "<br>" + "술은 -1점");
		Info.setBounds(150, 200, 300, 300);
		Info.setVisible(true);
		
		btnBack2.setBounds(230, 550, 150, 50);
		btnBack2.addActionListener(this);
		
		// jpInfo 게임정보 패널에 추가
		jpInfo.add(Info);
		jpInfo.add(btnBack2);

		// JFrame 설정
		add(jpStart);
		setTitle("Dodge Fail");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(600, 740);
		setVisible(true);
	}

	// 버튼 리스너
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnInfo)) {
			this.change(jpInfo); // jpStart >> 게임정보버튼 >> jpInfo
		}
		if (e.getSource().equals(btnStart)) {
			this.change2(jpSelect); // jpStart >> 게임시작버튼 >> jpSelect
		}
		if (e.getSource().equals(btnBack)) {
			this.change3(jpStart); // jpSelect >> 뒤로가기버튼 >> jpStart
		}
		if (e.getSource().equals(btnBack2)) {
			this.change4(jpStart); // jpInfo >> 뒤로가기버튼 >> jpStart
		}
		if (e.getSource().equals(btnJinho)) { // 진호버튼 클릭 시
			sound.sound_stop();
			Game1 t = new Game1();
			th = new Thread(t);
			th.start();
			dispose();
		} else if (e.getSource().equals(btnDaeyoung)) { // 대영버튼 클릭 시
			dispose();
			sound.sound_stop();
			Game2 t = new Game2();
			th = new Thread(t);
			th.start();
		} else if (e.getSource().equals(btnSeungri)) { // 승리버튼 클릭 시
			dispose();
			sound.sound_stop();
			Game3 t = new Game3();
			th = new Thread(t);
			th.start();
		}
	}

	private void change(JPanel jp) { // 화면전환 메소드 정의
		// TODO Auto-generated method stub
		if (jpStart.equals(jpStart)) {
			getContentPane().removeAll();
			getContentPane().add(jp);
			revalidate();
			repaint();
		}
	}

	private void change2(JPanel jp2) { // 화면전환 메소드 정의
		// TODO Auto-generated method stub
		if (jpStart.equals(jpStart)) {
			getContentPane().removeAll();
			getContentPane().add(jp2);
			revalidate();
			repaint();
		}
	}

	private void change3(JPanel jp3) { // 화면전환 메소드 정의
		// TODO Auto-generated method stub
		if (jpInfo.equals(jpInfo)) {
			getContentPane().removeAll();
			getContentPane().add(jp3);
			revalidate();
			repaint();
		}
	}

	private void change4(JPanel jp4) { // 화면전환 메소드 정의
		// TODO Auto-generated method stub
		if (jpSelect.equals(jpSelect)) {
			getContentPane().removeAll();
			getContentPane().add(jp4);
			revalidate();
			repaint();
		}
	}

	public static void main(String[] args) { // 메인
		Start st = new Start();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) { // 각 캐릭터 버튼에 마우스 올려놓을 시 각 캐릭터별 설명 출력
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnJinho)) {
			Jinfo.setVisible(true);
		} else if (e.getSource().equals(btnDaeyoung)) {
			Dinfo.setVisible(true);
		} else if (e.getSource().equals(btnSeungri)) {
			Sinfo.setVisible(true);
		}
	}

	@Override
	public void mouseExited(MouseEvent arg0) { // 각 캐릭터 버튼에 마우스 올려져 있지 않을 시 (기본상태)
		// TODO Auto-generated method stub
		Jinfo.setVisible(false);
		Dinfo.setVisible(false);
		Sinfo.setVisible(false);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	public void th_stop() { // 스레드 정지 메소드 생성
		th.interrupt();
	}
}