package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
			din = new DataInputStream(server.getInputStream());
			// String name = din.readUTF();
			byte[] data = new byte[1024];
			int count = din.read(data);
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
					dout.writeUTF("[#] " + name + " hiện hình =)) !!!");
				}
			}
			while (true) {
				String msg = din.readUTF();
				if (msg.contains("exit")) {
					Server.listSocket.remove(server);
					for (Socket item : Server.listSocket) {
						if ((item.getPort() != server.getPort())
								&& (item.getLocalAddress() != server.getLocalAddress())) {
							dout = new DataOutputStream(item.getOutputStream());
							dout.writeUTF("[#]" + name + " Đã ra đi T.T");
						}
					}
					System.out.println("[#]" + name + " Đã ra đi T.T");
					din.close();
					server.close();
					continue;
				}
				for (Socket item : Server.listSocket) {
					if ((item.getPort() != server.getPort()) && (item.getLocalAddress() != server.getLocalAddress())) {
						dout = new DataOutputStream(item.getOutputStream());
						dout.writeUTF(msg);
					}
				}
				// System.out.println(msg);
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
