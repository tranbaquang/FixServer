package client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import java.awt.Cursor;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import java.awt.Component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.border.MatteBorder;


public class ServerGui extends JFrame {

	private JPanel contentPane;
	static JTextArea terminal = new JTextArea();
	public static ArrayList<Socket> listSocket;
	/**
	 * Launch the application.
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		new ServerGui().go();
	}

	/**
	 * Create the frame.
	 */
	int i, j, x = 0;


	public ServerGui() {
		super("SERVER FII CHAT");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage("/home/iot/eclipse-workspac/JavaSocket_ChatRomGui/src/image/chat.png"));
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		setSize(572, 439);
		Gui();
		setVisible(true);
	
	}
	
	private void Gui() {
		setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 572, 439);
		// setBounds(100, 100, 572, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(60, 60, 60));
		panel.setBounds(0, 0, 572, 39);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton button = new JButton("");
		button.setBorderPainted(false);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (i % 2 == 0) {
					button.setIcon(new ImageIcon("/home/iot/eclipse-workspac/ServerOnly/src/image/night.png"));
					i++;
				} else {
					button.setIcon(new ImageIcon("/home/iot/eclipse-workspac/ServerOnly/src/image/sun.png"));
					i++;
				}

			}
		});
		button.setIcon(new ImageIcon("/home/iot/eclipse-workspac/ServerOnly/src/image/sun.png"));
		button.setBackground(new Color(60, 60, 60));
		button.setBounds(0, 0, 38, 38);
		panel.add(button);

		JLabel lblNewLabel = new JLabel("Server FII Chat");
		lblNewLabel.setFont(new Font("Dyuthi", Font.BOLD, 26));
		lblNewLabel.setForeground(new Color(60, 60, 60));
		lblNewLabel.setBackground(new Color(255, 255, 255));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(39, 12, 191, 26);
		panel.add(lblNewLabel);

		JButton btnThoat = new JButton("");
		btnThoat.setBounds(532, 0, 40, 39);
		btnThoat.setBorderPainted(false);
		panel.add(btnThoat);
		btnThoat.setIcon(
				new ImageIcon("/home/iot/eclipse-workspac/ServerOnly/src/image/red-icon-power-off-vector-7129971.png"));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btnThoat.setBackground(new Color(60, 60, 60));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(60, 60, 60));
		panel_1.setBounds(532, 35, 40, 369);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JButton button_4 = new JButton("");
		button_4.setIcon(new ImageIcon("/home/iot/eclipse-workspac/FixServer/src/image/icons8-load-balancer-25.png"));
		button_4.setBorderPainted(false);
		button_4.setBounds(0, 116, 40, 40);
		panel_1.add(button_4);
		button_4.setBackground(new Color(60, 60, 60));

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(378, 36, 165, 368);
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblListOnline = new JLabel("List Online");
		lblListOnline.setBounds(0, 3, 149, 29);
		panel_2.add(lblListOnline);
		lblListOnline.setHorizontalAlignment(SwingConstants.CENTER);
		lblListOnline.setForeground(new Color(255, 250, 250));
		lblListOnline.setVisible(false);

		JTextArea txtrListOnline = new JTextArea();
		txtrListOnline.setBounds(0, 3, 155, 26);
		panel_2.add(txtrListOnline);
		txtrListOnline.setBackground(new Color(90, 90, 90));
		txtrListOnline.setVisible(false);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 29, 155, 339);
		panel_2.add(scrollPane_1);

		JList listOnline = new JList();
		listOnline.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(255, 0, 0)));
		listOnline.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 14));
		scrollPane_1.setViewportView(listOnline);
		listOnline.setModel(new AbstractListModel() {
			String[] values = new String[] { "Quang Tran", "Manh Tuan", "Tri Dung", "Duong Duong", "A Phu", "A Su",
					"Chi Mi", "Cu ti", "Ronaldol", "Mesi 10", "beckham", "Van persi", "ro bet to", "sac lo",
					"ra nal nhi no", "le cong vinh" };

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		listOnline.setBackground(new Color(220, 220, 220));
		scrollPane_1.setVisible(false);

		JButton button_6 = new JButton("");
		JButton btnList = new JButton("");
		btnList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (j % 2 == 0) {
					scrollPane_1.setVisible(true);
					txtrListOnline.setVisible(true);
					lblListOnline.setVisible(true);
					btnList.setIcon(new ImageIcon("/home/iot/eclipse-workspac/FixServer/src/image/listBlack.png"));
					btnList.setBackground(new Color(211, 211, 211, 60));
					button_6.setBackground(new Color(60, 60, 60));
					j++;
				} else {
					scrollPane_1.setVisible(false);
					txtrListOnline.setVisible(false);
					lblListOnline.setVisible(false);
					btnList.setIcon(new ImageIcon("/home/iot/eclipse-workspac/FixServer/src/image/listWhite.png"));
					btnList.setBackground(new Color(60, 60, 60));
					button_6.setBackground(new Color(255, 255, 255, 60));
					j++;
				}

			}
		});

		button_6.setBounds(0, 220, 40, 40);
		panel_1.add(button_6);
		button_6.setBackground(new Color(60, 60, 60));

		btnList.setBorderPainted(false);
		btnList.setIcon(new ImageIcon("/home/iot/eclipse-workspac/FixServer/src/image/listWhite.png"));
		btnList.setBackground(new Color(60, 60, 60));
		btnList.setBounds(0, 272, 40, 40);
		panel_1.add(btnList);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 40, 379, 363);
		contentPane.add(scrollPane);

//		JTextArea terminal = new JTextArea();
		terminal.setFont(new Font("DejaVu Sans Mono", Font.PLAIN, 12));
		scrollPane.setViewportView(terminal);
		terminal.setEditable(false);
		terminal.setLineWrap(true);
		terminal.setRows(20);
		terminal.setColumns(20);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(60, 60, 60));
		scrollPane.setRowHeaderView(panel_4);
		panel_4.setLayout(null);

		JButton button_5 = new JButton("");
		button_5.setIcon(new ImageIcon("/home/iot/eclipse-workspac/FixServer/src/image/icons8-clock-25.png"));
		button_5.setBorderPainted(false);
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date timeNow = new Date();
				DateFormat df = new SimpleDateFormat("hh:mm:ss a");
				terminal.append("[#] Now is " + df.format(timeNow) + "\n");
				JOptionPane.showMessageDialog(button_5, "Now is " + df.format(timeNow));

			}
		});
		button_5.setBounds(0, 168, 40, 40);
		panel_1.add(button_5);
		button_5.setBackground(new Color(60, 60, 60));

		JButton btnPort = new JButton("");
		btnPort.setBorderPainted(false);
		btnPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				terminal.append("[#] Server operates at PORT Address: 9999\n");
				JOptionPane.showMessageDialog(btnPort, "Server operates at PORT Address: 9999");
			}
		});
		btnPort.setIcon(new ImageIcon("/home/iot/eclipse-workspac/ServerOnly/src/image/rj45White.png"));
		btnPort.setBounds(0, 64, 40, 40);
		panel_1.add(btnPort);
		btnPort.setBackground(new Color(60, 60, 60));

		JButton btnIP = new JButton("");
		btnIP.setBorderPainted(false);
		btnIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				terminal.append("[#] Server operates at IP Address: 127.0.0.1\n");
				JOptionPane.showMessageDialog(btnIP, "Server operates at IP Address: 127.0.0.1");
			}
		});
		btnIP.setIcon(new ImageIcon("/home/iot/eclipse-workspac/ServerOnly/src/image/server.png"));
		btnIP.setBounds(0, 0, 40, 52);
		panel_1.add(btnIP);
		btnIP.setBackground(new Color(60, 60, 60));

		// JButton button_6 = new JButton("");
		button_6.setIcon(new ImageIcon("/home/iot/eclipse-workspac/FixServer/src/image/icons8-run-command-25.png"));
		button_6.setBorderPainted(false);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(60, 60, 60));
		panel_3.setBounds(0, 404, 572, 10);
		contentPane.add(panel_3);
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (x % 2 == 0) {
					button_6.setBackground(new Color(255, 255, 255, 60));
					scrollPane_1.setVisible(false);
					txtrListOnline.setVisible(false);
					lblListOnline.setVisible(false);
					btnList.setIcon(new ImageIcon("/home/iot/eclipse-workspac/FixServer/src/image/listWhite.png"));
					btnList.setBackground(new Color(60, 60, 60));
					terminal.setText("");
					terminal.append("[!] Terminal is active !!!\n");
					x++;
				} else {
					button_6.setBackground(new Color(60, 60, 60));
					x++;
				}
			}
		});
	}
	
	private void go() throws IOException {
		ServerGui.listSocket = new ArrayList<Socket>();
		ServerGui server = new ServerGui();
		server.execute();
	}

	private void execute() throws IOException {
		ServerSocket server = new ServerSocket(9999);
		//System.out.println("[#] Server đã hoạt động mượt mà ^^\nNgồi hóng coi có đứa nào kết nối ...");
		terminal.append("[#] The Server is running !\nWaiting for the Client to connect ...");
		System.out.println("[#] Server đã hoạt động mượt mà ^^\nNgồi hóng coi có đứa nào kết nối ...\n");
		while (true) {
			Socket client = server.accept();
			//System.out.println("[#] Có 1 đối tượng giống như con người kết nối");
			ServerGui.listSocket.add(client);
			readServer read = new readServer(client);
			read.start();
		}

	}

}

class readServer extends Thread {
	private Socket server;
	//private JTextArea terminal;

	public readServer(Socket server) {
		this.server = server;
	}

	@Override
	public void run() {
		DataInputStream din = null;
		DataOutputStream dout = null;
		try {
			int count = 0;
			din = new DataInputStream(server.getInputStream());
			// String name = din.readUTF();
			byte[] data = new byte[1024];
			count = din.read(data);
			System.out.println("Count = " + count);
			byte[] real = new byte[count + 1];
			for (int i = 0; i <= count - 1; i++) {
				real[i] = data[i];
				
				//System.out.println(real[i]);
				//System.out.println("# "+data[i]);
			}
			String name = new String(data).trim();
			System.out.println("[#] " + name + " đã zô nhóm chat ;) ");
			ServerGui.terminal.append("       < " + "[" + name + "]" + " joined the chat room !!!>\n");
			
			for (Socket item : ServerGui.listSocket) {
				if ((item.getPort() != server.getPort()) && (item.getLocalAddress() != server.getLocalAddress())) {
					dout = new DataOutputStream(item.getOutputStream());
					String nameString = ("# " + "[" + name + "]" + " hiện hình =)) !!!");
					byte[] nameByte = nameString.getBytes();
					dout.write(nameByte);
				}
			}
			while (true) {
				//String msg = din.readUTF();
				//String msg = new String(data).trim();
				int count1 = 0;
				din = new DataInputStream(server.getInputStream());
				// String name = din.readUTF();
				byte[] data1 = new byte[1024];
				count1 = din.read(data1);
				System.out.println("Count 1 = " + count1);
				byte[] real1 = new byte[count1 + 1];
				for (int i = 0; i <= count1 - 1; i++) {
					real1[i] = data1[i];	
					//System.out.println(real[i]);
					//System.out.println("# "+data[i]);
				}
				String msg = new String(data1).trim();
				if (msg.equals("exit")) {
					ServerGui.listSocket.remove(server);
					for (Socket item : ServerGui.listSocket) {
						if ((item.getPort() != server.getPort())
								&& (item.getLocalAddress() != server.getLocalAddress())) {
							dout = new DataOutputStream(item.getOutputStream());
							String msgName = ("       < " + "[" + name + "]" + " Disconnect !!!>\n");
							byte[] msgByte = msgName.getBytes();
							dout.write(msgByte);
						}
					}
					System.out.println("< " + "[" + name + "]" + " Đã ra đi T.T");
					ServerGui.terminal.append("       < " + "[" + name + "]" + " Disconnect !!!>\n");
					
					din.close();
					server.close();
					continue;
				}
				for (Socket item : ServerGui.listSocket) {
					if ((item.getPort() != server.getPort()) && (item.getLocalAddress() != server.getLocalAddress())) {
						dout = new DataOutputStream(item.getOutputStream());
						String msgName = ("[" + name + "]" + ": " + msg);
						byte[] msgByte = msgName.getBytes();	
						dout.write(msgByte);
					}
				}
				System.out.println("[" + name + "]" + ": " + msg + "\n");
				ServerGui.terminal.append("[" + name + "]" + ": " + msg + "\n");
//				din.close();
//				dout.close();
			}
		} catch (IOException e) {
			try {
				din.close();
				server.close();
			} catch (IOException ex) {
				System.err.println(" ");
			}
		}
	}
	
	
}
