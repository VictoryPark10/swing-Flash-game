package ddong;

import java.awt.event.*;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;

public class Start extends JFrame implements ActionListener, MouseListener {

	public JPanel jpStart; // �ʱ�ȭ�� �г� ����
	
	ImageIcon imgMain = new ImageIcon("./images/MainBG.png");
	ImageIcon imgSelect = new ImageIcon("./images/SelectBG.png");
	
	JButton btnStart = new JButton(new ImageIcon("./images/StartButton.png")); // �����ϱ� ��ư
	JButton btnInfo = new JButton(new ImageIcon("./images/InfoButton.png")); // ������������ ��ư
	JButton btnJinho = new JButton(new ImageIcon("./images/SelectStudent1.png")); // ��ȣ ���ù�ư
	JButton btnDaeyoung = new JButton(new ImageIcon("./images/SelectStudent2.png")); // �뿵 ���ù�ư
	JButton btnSeungri = new JButton(new ImageIcon("./images/SelectStudent3.png")); // �¸� ���ù�ư
	public JButton btnBack = new JButton(new ImageIcon("./images/BackButton.png")); // �ڷΰ��� ��ư
	JButton btnBack2 = new JButton(new ImageIcon("./images/BackButton.png")); // �ڷΰ��� ��ư
	
	JLabel lbTitle = new JLabel(); // ���� �����г� Ÿ��Ʋ
	JLabel lbJ = new JLabel(); // ��ȣ �̸�
	JLabel lbD = new JLabel(); // �뿵 �̸�
	JLabel lbS = new JLabel(); // �¸� �̸�
	JLabel Jinfo = new JLabel(); // ��ȣ ����
	JLabel Dinfo = new JLabel(); // �뿵 ����
	JLabel Sinfo = new JLabel(); // �¸� ����
	JLabel Info = new JLabel(); // ���� ����
	
	Sound sound = new Sound();
	
	Thread th;

	JPanel jpSelect = new JPanel() { // ĳ���� ���� �г�
		public void paintComponent(Graphics g) {
			g.drawImage(imgSelect.getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};
	
	JPanel jpInfo = new JPanel() { // �������� �г�
		public void paintComponent(Graphics g) {
			g.drawImage(imgSelect.getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	};

	public Start() { // ������
		sound.sound("mainbgm.wav", true); // �ʱ� �������(ĳ���� ���� �� �����)
		
		jpStart = new JPanel() { // �ʱ�ȭ�� �г� �׸���
			public void paintComponent(Graphics g) {
				g.drawImage(imgMain.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		
		// jpStart ���� �ʱ�ȭ��
		jpStart.setLayout(null);

		btnInfo.setBounds(230, 550, 150, 50);
		btnInfo.addActionListener(this);
		btnStart.setBounds(230, 480, 150, 50);
		btnStart.addActionListener(this);
		
		// jpStart ���� �ʱ�ȭ�鿡 �߰�
		jpStart.add(btnInfo);
		jpStart.add(btnStart);
		
		// jpSelect ĳ���� ���� �г�
		jpSelect.setLayout(null);

		lbJ.setText("����ȣ");
		lbJ.setBounds(130, 250, 100, 100);
		lbD.setText("���뿵");
		lbD.setBounds(280, 250, 100, 100);
		lbS.setText("�ڽ¸�");
		lbS.setBounds(430, 250, 100, 100);
		Jinfo.setText("<html>" + "<div style=\"text-align:center\">" + "�� ĳ���ʹ� ���� �� �Ե��� �մϴ�." + "<br>" + "�� ���� +1!"
				+ "</div>" + "</html>");
		Jinfo.setBounds(200, 70, 300, 100);
		Jinfo.setVisible(false);
		Dinfo.setText("<html>" + "<div style=\"text-align:center\">" + "�� ĳ���ʹ� �����ۿ� �𸣴� �ٺ��Դϴ�." + "<br>" + "å ���� +3!"
				+ "</div>" + "</html>");
		Dinfo.setBounds(195, 70, 300, 100);
		Dinfo.setVisible(false);
		Sinfo.setText("<html>" + "<div style=\"text-align:center\">" + "�� ĳ���ʹ� �谡 ���Ŵϴ�." + "<br>" + "�� ���� +4!"
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

		// jpSelect ĳ���� ���� �гο� �߰�
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
		
		// jpInfo �������� �г�
		jpInfo.setLayout(null);
		
		Info.setText("<html>" + "<div style=\"text-align:center\">" + " ���б��� �Դ�...." + "<br>"
				+ "�ƹ��͵� �𸣴�ä�� ���б��� �� 3���� �Ϳ��� �������!!" + "<br>" + "���л�Ȱ�� ������ �˷��־�����" + "<br>"
				+ "F�� �ٸ� ������ ������� �ƹ��� �˷����� �ʾҴ�..." + "<br>" + "������ ������ �賭�� ���л�Ȱ�� ������ ���ĺ���!!" + "<br>" + "<br>"
				+ "�¿� Ű�� �������� �������� F�� ���غ�����." + "<br>" + "<br>" + "F�� -3��" + "<br>" + "å��  +2��" + "<br>" + "���� +2��"
				+ "<br>" + "���� -1��");
		Info.setBounds(150, 200, 300, 300);
		Info.setVisible(true);
		
		btnBack2.setBounds(230, 550, 150, 50);
		btnBack2.addActionListener(this);
		
		// jpInfo �������� �гο� �߰�
		jpInfo.add(Info);
		jpInfo.add(btnBack2);

		// JFrame ����
		add(jpStart);
		setTitle("Dodge Fail");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setSize(600, 740);
		setVisible(true);
	}

	// ��ư ������
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(btnInfo)) {
			this.change(jpInfo); // jpStart >> ����������ư >> jpInfo
		}
		if (e.getSource().equals(btnStart)) {
			this.change2(jpSelect); // jpStart >> ���ӽ��۹�ư >> jpSelect
		}
		if (e.getSource().equals(btnBack)) {
			this.change3(jpStart); // jpSelect >> �ڷΰ����ư >> jpStart
		}
		if (e.getSource().equals(btnBack2)) {
			this.change4(jpStart); // jpInfo >> �ڷΰ����ư >> jpStart
		}
		if (e.getSource().equals(btnJinho)) { // ��ȣ��ư Ŭ�� ��
			sound.sound_stop();
			Game1 t = new Game1();
			th = new Thread(t);
			th.start();
			dispose();
		} else if (e.getSource().equals(btnDaeyoung)) { // �뿵��ư Ŭ�� ��
			dispose();
			sound.sound_stop();
			Game2 t = new Game2();
			th = new Thread(t);
			th.start();
		} else if (e.getSource().equals(btnSeungri)) { // �¸���ư Ŭ�� ��
			dispose();
			sound.sound_stop();
			Game3 t = new Game3();
			th = new Thread(t);
			th.start();
		}
	}

	private void change(JPanel jp) { // ȭ����ȯ �޼ҵ� ����
		// TODO Auto-generated method stub
		if (jpStart.equals(jpStart)) {
			getContentPane().removeAll();
			getContentPane().add(jp);
			revalidate();
			repaint();
		}
	}

	private void change2(JPanel jp2) { // ȭ����ȯ �޼ҵ� ����
		// TODO Auto-generated method stub
		if (jpStart.equals(jpStart)) {
			getContentPane().removeAll();
			getContentPane().add(jp2);
			revalidate();
			repaint();
		}
	}

	private void change3(JPanel jp3) { // ȭ����ȯ �޼ҵ� ����
		// TODO Auto-generated method stub
		if (jpInfo.equals(jpInfo)) {
			getContentPane().removeAll();
			getContentPane().add(jp3);
			revalidate();
			repaint();
		}
	}

	private void change4(JPanel jp4) { // ȭ����ȯ �޼ҵ� ����
		// TODO Auto-generated method stub
		if (jpSelect.equals(jpSelect)) {
			getContentPane().removeAll();
			getContentPane().add(jp4);
			revalidate();
			repaint();
		}
	}

	public static void main(String[] args) { // ����
		Start st = new Start();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) { // �� ĳ���� ��ư�� ���콺 �÷����� �� �� ĳ���ͺ� ���� ���
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
	public void mouseExited(MouseEvent arg0) { // �� ĳ���� ��ư�� ���콺 �÷��� ���� ���� �� (�⺻����)
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
	
	public void th_stop() { // ������ ���� �޼ҵ� ����
		th.interrupt();
	}
}