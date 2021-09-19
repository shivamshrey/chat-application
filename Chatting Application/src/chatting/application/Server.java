package chatting.application;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ScrollBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Server implements ActionListener {
	JPanel p1;
	JTextField t1;
	JButton b1;
	static JPanel a1;
	static JFrame f1 = new JFrame();
	
	static Box vertical = Box.createVerticalBox();
	
	static ServerSocket skt;
	static Socket s;
	static DataInputStream din;
	static DataOutputStream dout;
	
	Boolean typing;
	
	Server() {
		f1.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		p1 = new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(7, 94, 84));
		p1.setBounds(0, 0, 450, 70);
		f1.add(p1);
		
		ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3.png"));
		Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i3 = new ImageIcon(i2);
		
		JLabel l1 = new JLabel(i3);
		l1.setBounds(5, 17, 30, 30);
		p1.add(l1);
		
		l1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ae) {
				System.exit(0);
			}
		});
		
		ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/1.png"));
		Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT);
		ImageIcon i6 = new ImageIcon(i5);
		
		JLabel l2 = new JLabel(i6);
		l2.setBounds(40, 5, 60, 60);
		p1.add(l2);
		
		ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/video.png"));
		Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon i9 = new ImageIcon(i8);
		
		JLabel l5 = new JLabel(i9);
		l5.setBounds(290, 20, 30, 30);
		p1.add(l5);
		
		ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/phone.png"));
		Image i12 = i11.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
		ImageIcon i13 = new ImageIcon(i12);
		
		JLabel l6 = new JLabel(i13);
		l6.setBounds(350, 20, 35, 30);
		p1.add(l6);
		
		ImageIcon i14 = new ImageIcon(ClassLoader.getSystemResource("chatting/application/icons/3icon.png"));
		Image i15 = i14.getImage().getScaledInstance(13, 25, Image.SCALE_DEFAULT);
		ImageIcon i16 = new ImageIcon(i15);
		
		JLabel l7 = new JLabel(i16);
		l7.setBounds(410, 20, 13, 25);
		p1.add(l7);
		
		JLabel l3 = new JLabel("Gaitonde");
		l3.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
		l3.setForeground(Color.WHITE);
		l3.setBounds(110, 15, 100, 18);
		p1.add(l3);
		
		JLabel l4 = new JLabel("Online");
		l4.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
		l4.setForeground(Color.WHITE);
		l4.setBounds(110, 15, 100, 20);
		p1.add(l4);
		
		Timer t = new Timer(1, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				if (!typing) {
					l4.setText("Online");
				}
				
			}
			
		});
		t.setInitialDelay(2000);
		
		a1 = new JPanel();
		a1.setBackground(Color.WHITE);
		a1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		
		JScrollPane sp = new JScrollPane(a1);
		sp.setBounds(5, 75, 440, 570);
		sp.setBorder(BorderFactory.createEmptyBorder());
		
		ScrollBarUI ui = new BasicScrollBarUI() {
			
			@Override
			protected JButton createDecreaseButton(int orientation) {
				JButton button = super.createDecreaseButton(orientation);
				button.setBackground(new Color(7, 94,84));
				button.setForeground(Color.WHITE);
				this.thumbColor = new Color(7, 94, 84);
				return button;
			}
			
			@Override
			protected JButton createIncreaseButton(int orientation) {
				JButton button = super.createIncreaseButton(orientation);
				button.setBackground(new Color(7, 94,84));
				button.setForeground(Color.WHITE);
				this.thumbColor = new Color(7, 94, 84);
				return button;
			}
		};
		sp.getVerticalScrollBar().setUI(ui);
		f1.add(sp);
		
		t1 = new JTextField();
		t1.setBounds(5, 655, 310, 40);
		t1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		f1.add(t1);
		t1.addKeyListener(new KeyAdapter() {
			
			public void keyPressed(KeyEvent ke) {
				l4.setText("typing...");
				t.stop();
				typing = true;
			}
			
			public void keyReleased(KeyEvent ke) {
				typing = false;
				if (!t.isRunning()) {
					t.start();
				}
			}
		});
		
		b1 = new JButton("Send");
		b1.setBounds(320, 655, 123, 40);
		b1.setBackground(new Color(7, 94, 84));
		b1.setForeground(Color.WHITE);
		b1.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
		b1.addActionListener(this);
		f1.add(b1);
		
		f1.setLayout(null);
		f1.setSize(450, 700);	// set frame size
		f1.setLocation(400, 200);	// set location relative to origin
//		f1.setUndecorated(true);	// remove header/Title bar
		f1.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {	// called on click of 'Send' button
		try {
			String out = t1.getText();
			writeTextToFile(out);
			JPanel p2 = formatLabel(out);
			
			a1.setLayout(new BorderLayout());
			
			JPanel right = new JPanel(new BorderLayout());
			right.add(p2, BorderLayout.LINE_END);
			vertical.add(right);
			vertical.add(Box.createVerticalStrut(15));	// Add vertical space between messages
			a1.add(vertical, BorderLayout.PAGE_START);
			
			dout.writeUTF(out);
			t1.setText("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void writeTextToFile(String message) throws FileNotFoundException {
		try (FileWriter f = new FileWriter("chat.txt");
				PrintWriter p = new PrintWriter(new BufferedWriter(f));) {
			p.println("Gaitonde: " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JPanel formatLabel(String out) {
		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		
		JLabel l1 = new JLabel("<html><p style = \"width : 150px\">" + out + "</p></html>");	// hack for line breaks for long text
		l1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		l1.setBackground(new Color(37, 211, 102));
		l1.setOpaque(true);
		l1.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		
		JLabel l2 = new JLabel();
		l2.setText(sdf.format(cal.getTime()));
		
		p3.add(l1);
		p3.add(l2);
		return p3;
	}
	
	public static void main(String[] args) {
		new Server().f1.setVisible(true);
		String msgInput = "";
		try {
			skt = new ServerSocket(6001);
			
			while (true) {
				s = skt.accept();
				din = new DataInputStream(s.getInputStream());
				dout = new DataOutputStream(s.getOutputStream());
				
				while (true) {
					msgInput = din.readUTF();
					JPanel p2 = formatLabel(msgInput);
					
					JPanel left = new JPanel(new BorderLayout());
					left.add(p2, BorderLayout.LINE_START);
					vertical.add(left);
				}
			
			}
		} catch (Exception e) {
			
		}
	}
	
}
