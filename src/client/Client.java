package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private InetAddress host;
	private int port;

	public Client(InetAddress host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws IOException {
		Client client = new Client(InetAddress.getLocalHost(), 9999);
		client.execute();
	}

	private void execute() throws IOException {
		Scanner scan = new Scanner(System.in);

		System.out.print("Nhập tên của Bạn: ");
		String name = scan.nextLine();

		Socket client = new Socket(host, port);
		System.out.println("đã kết nối tới Server");
		ReadClient read = new ReadClient(client);
		read.start();

		WriteClient write = new WriteClient(client, name);
		write.start();
	}
}

class ReadClient extends Thread {
	private Socket client;

	public ReadClient(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		DataInputStream din = null;
		try {
			din = new DataInputStream(client.getInputStream());
			while (true) {
				/*
				 * 	Nhan tin nhan dang String
				 * 		String msg = din.readUTF();
				 * 		System.out.println(msg);
				 */

				// Nhan tin nhan dang Byte
				int count = 0;
				byte[] msgByte = new byte[1024];
				count = din.read(msgByte);
				byte[] real = new byte[count + 1];
				for (int i = 0; i <= count - 1; i++) {
					real[i] = msgByte[i];
					// System.out.println(real[i]);
					// System.out.println("# "+data[i]);
				}
				// Chuyen tin nhan tu Byte qua String de hien thi
				String msg = new String(msgByte).trim();
				System.out.println(msg);
			}

		} catch (IOException e) {
			try {
				din.close();
				client.close();
			} catch (IOException ex) {
				System.err.println("Lỗi cơm mẹ nấu rồi, ahihi :3");
				//ex.printStackTrace();
			}
		}
	}

}

class WriteClient extends Thread {
	private Socket client;
	private String name;

	public WriteClient(Socket client, String name) {
		this.client = client;
		this.name = name;
	}

	@Override
	public void run() {
		DataOutputStream dout = null;
		Scanner scan = null;
		try {
			dout = new DataOutputStream(client.getOutputStream());
			scan = new Scanner(System.in);
			// Chuyen name(String) --> nameByte(byte)
			byte[] nameByte = name.getBytes();
			dout.write(nameByte);
			/*
			 * Gui ten dang String
			 * 		dout.writeUTF(name);
			 */

			while (true) {
				// Nhap tin nhan
				String msg = scan.nextLine();
				// gui ten + tin nhan
				// Chuyen qua dang byte
				byte[] msgByte = msg.getBytes();
				// gui tin nhan voi dang byte
				dout.write(msgByte);
			}
			
		} catch (IOException e) {
			try {
				dout.close();
				client.close();
			} catch (IOException ex) {
				System.err.println("Lỗi cơm mẹ nấu rồi, ahihi :3");
			}
		}
	}

}