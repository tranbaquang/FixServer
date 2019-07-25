package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

	private int port;
	public static ArrayList<Socket> listSocket;

	public Server(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws IOException {
		Server.listSocket = new ArrayList<Socket>();
		Server server = new Server(9999);
		server.execute();
	}

	private void execute() throws IOException {
		ServerSocket server = new ServerSocket(port);
		System.out.println("[#] Server đã hoạt động mượt mà ^^\nNgồi hóng coi có đứa nào kết nối ...");
		writeServer write = new writeServer();
		write.start();
		while (true) {
			Socket client = server.accept();
			System.out.println("[#] Có 1 đối tượng giống như con người kết nối");
			Server.listSocket.add(client);
			readServer read = new readServer(client);
			read.start();

		}

	}

}

class readServer extends Thread {
	private Socket server;

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

				// System.out.println(real[i]);
				// System.out.println("# "+data[i]);
			}
			String name = new String(data).trim();
			System.out.println("[#] " + name + " đã zô nhóm chat ;) ");

			for (Socket item : Server.listSocket) {
				if ((item.getPort() != server.getPort()) && (item.getLocalAddress() != server.getLocalAddress())) {
					dout = new DataOutputStream(item.getOutputStream());
					String nameString = ("# " + "[" + name + "]" + " hiện hình =)) !!!");
					byte[] nameByte = nameString.getBytes();
					dout.write(nameByte);
				}
			}
			while (true) {
				// String msg = din.readUTF();
				// String msg = new String(data).trim();
				int count1 = 0;
				din = new DataInputStream(server.getInputStream());
				// String name = din.readUTF();
				byte[] data1 = new byte[1024];
				count1 = din.read(data1);
				System.out.println("Count 1 = " + count1);
				byte[] real1 = new byte[count1 + 1];
				for (int i = 0; i <= count1 - 1; i++) {
					real1[i] = data1[i];
					// System.out.println(real[i]);
					// System.out.println("# "+data[i]);
				}
				String msg = new String(data1).trim();
				if (msg.equals("exit")) {
					Server.listSocket.remove(server);
					for (Socket item : Server.listSocket) {
						if ((item.getPort() != server.getPort())
								&& (item.getLocalAddress() != server.getLocalAddress())) {
							dout = new DataOutputStream(item.getOutputStream());
							String msgName = ("[#] " + "[" + name + "]" + " Đã ra đi T.T");
							byte[] msgByte = msgName.getBytes();
							dout.write(msgByte);
						}
					}
					System.out.println("[#] " + "[" + name + "]" + " Đã ra đi T.T");
					din.close();
					server.close();
					continue;
				}
				for (Socket item : Server.listSocket) {
					if ((item.getPort() != server.getPort()) && (item.getLocalAddress() != server.getLocalAddress())) {
						dout = new DataOutputStream(item.getOutputStream());
						String msgName = ("[" + name + "]" + ": " + msg);
						byte[] msgByte = msgName.getBytes();
						dout.write(msgByte);
					}
				}
				System.out.println("[" + name + "]" + ": " + msg);
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

class writeServer extends Thread {

	@Override
	public void run() {
		DataOutputStream dout = null;
		DataInputStream din = null;
		Scanner scan = new Scanner(System.in);
		while (true) {
			try {
				String msg = scan.nextLine();
//				if (msg.equals("exit")) {
//					for (Socket item : Server.listSocket) {
//						Server.listSocket.remove(item);
//						dout = new DataOutputStream(item.getOutputStream());
//						din = new DataInputStream(item.getInputStream());
//						String msgName = ("[#] Đã ngắt kết nối với Server");
//						byte[] msgByte = msgName.getBytes();
//						dout.write(msgByte);
//						din.close();
//						item.close();
//					}
//					System.out.println("[#] Ngắt kết nối với tất cả các client");
//					continue;
//				}
				for (Socket item : Server.listSocket) {
					dout = new DataOutputStream(item.getOutputStream());
					String msgName = ("[Server]" + ": " + msg);
					byte[] msgByte = msgName.getBytes();
					dout.write(msgByte);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}