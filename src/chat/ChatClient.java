package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
	private static String SERVER_IP = "127.0.0.1";
	private static int SERVER_PORT = 7000;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner s = new Scanner(System.in);
		try {

			socket = new Socket();

			socket.connect(new InetSocketAddress(SERVER_IP, SERVER_PORT));

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);

			System.out.print("닉네임>>");
			String nickname = s.nextLine();
			pw.println("join:" + nickname);
			pw.flush();
			String ack = br.readLine();
			System.out.println(ack);

			new ChatClientThread(socket).start();
			System.out.print(">>");
			while (true) {
				
				String line = s.nextLine();

				if ("quit".equals(line)) {
					pw.println("quit:");
					pw.flush();
					break;
				} else {
					pw.println("message:" + line);
					pw.flush();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (s != null) {
					s.close();
				}
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
