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
		System.out.println("[#] Server đã hoạt động\nĐang chờ kết nối ...");
		while (true) {
			Socket client = server.accept();		
			System.out.println("[#] Co client Da ket noi vao room chat");
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
			String name = din.readUTF();
			System.out.println("[#]ahihihi " + name + " da ket noi vao room");
			for (Socket item : Server.listSocket) {
				if ((item.getPort() != server.getPort()) 
						&& (item.getLocalAddress() != server.getLocalAddress())) {
					dout = new DataOutputStream(item.getOutputStream());
					dout.writeUTF("[#] " + name + " da ket noi voi anh em!!!");
				}
			}
			while (true) {
				String msg = din.readUTF();
				for (Socket item : Server.listSocket) {
					if ((item.getPort() != server.getPort()) 
							&& (item.getLocalAddress() != server.getLocalAddress())) {
						dout = new DataOutputStream(item.getOutputStream());
						dout.writeUTF(msg);
					}
				}
				System.out.println(msg);
			}
		} catch (IOException e) {
			try {
				din.close();
				server.close();
			} catch (IOException ex) {
				System.err.println("[#] U tat duoc server @!!");
			}
		}
	}
}
