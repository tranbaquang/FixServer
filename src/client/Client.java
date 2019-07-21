package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
		System.out.println("Nhập tên của Bạn: ");
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
				String msg = din.readUTF();
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
			while (true) {
				String msg = scan.nextLine();
				dout.writeUTF(name + ": " + msg);
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