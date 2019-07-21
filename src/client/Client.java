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
	
	private void execute () {
		
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
				System.out.println("Server: " + msg);
			}
			
		} catch (IOException e) {
			try {
				din.close();
				client.close();
			} catch (IOException e) {
				System.err.println("Lỗi cơm mẹ nấu rồi, ahihi :3");
				e.printStackTrace();
			}
		}
	}
	
	
	
	
}

class WriteClient extends Thread {
	private Socket client;

	public WriteClient(Socket client) {
		this.client = client;
	}
	
	@Override
	public void run() {
		DataOutputStream dout = null;
		Scanner scan = null;
		try {
			dout = new DataOutputStream(client.getOutputStream());
			scan = new Scanner(System.in);
			while(true) {
				String msg = scan.nextLine();
				dout.writeUTF(msg);
				System.out.println("Đã gửi: \"" + "\"Cho Server!" );
			}
		} catch (IOException e) {
			try {
				dout.close();
				client.close();
			} catch (IOException e) {
				System.err.println("Lỗi cơm mẹ nấu rồi, ahihi :3");
			}
		}
	}
	
	
}