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
//				String msg = din.readUTF();
//				System.out.println(msg);
				byte[] msgByte = new byte[1024];
				int count = din.read(msgByte);
				byte[] real = new byte[count + 1];
				for (int i = 0; i <= count - 1; i++) {
					real[i] = msgByte[i];
					// System.out.println(real[i]);
					// System.out.println("# "+data[i]);
				}
				String msg = new String(msgByte).trim();
				System.out.println(msg);
			}

		} catch (IOException e) {
			try {
				din.close();
				client.close();
			} catch (IOException ex) {
				System.err.println("Lỗi cơm mẹ nấu rồi, ahihi :3");
				ex.printStackTrace();
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
			// dout.writeUTF(name);
			while (true) {
				// Nhap tin nhan
				String msg = scan.nextLine();
				// gui ten + tin nhan
				String msgName = (name + ": " + msg);
				// Chuyen qua dang byte
				byte[] msgByte = msgName.getBytes();
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