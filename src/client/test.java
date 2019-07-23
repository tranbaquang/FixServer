package client;

import java.util.Scanner;

public class test {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Bo li in put hia: ");
		String a = scan.nextLine();
		String b = "I don't like saying: Hello, world";
		byte[] test = a.getBytes();
		byte[] test2 = b.getBytes();
		System.out.println("Chuyen qua byte: " + a.getBytes());
		System.out.println("Chuyen qua byte: " + a.getBytes());
		System.out.println("Chuyen nguoc ve: " + new String (test) + new String (test2) + "muahahaha");
		String msg = scan.nextLine();
		String msgName = (a + ": " + msg);
		byte[] msgByte = msgName.getBytes();
		System.out.println("Chuyen qua byte: " + msgName.getBytes());
		System.out.println("Chuyen sang String: " + new String (msgByte));
		
	}
}
