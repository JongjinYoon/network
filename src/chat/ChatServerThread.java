package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

public class ChatServerThread extends Thread {
	private Socket socket;
	private String nickname;
	private List<Writer> listWriters;

	public ChatServerThread(Socket socket, List<Writer> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {

		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
			

			while (true) {

				
				String data = br.readLine();
				if (data == null) {
					ChatServer.log(" 클라이언트로 부터 연결 끊김");
					break;
				}

				String[] tokens = data.split(":");

//				if ("join".equals(tokens[0])) {
//					doJoin(tokens[1], pw);
//				} else if ("message".equals(tokens[0])) {
//					doMessage(tokens[1]);
//				} else if ("quit".equals(tokens[0])) {
//					doQuit(pw);
//				} else {
//					ChatServer.log("에러 : 알 수 없는 요청(" + tokens[0] + ")");
//				}

				switch (tokens[0]) {
				
				case "join":
					doJoin(tokens[1], pw);
					break;
				case "message":
					doMessage(tokens[1]);
					break;
				case "quit":
					doQuit(pw);
					break;
				default:
					ChatServer.log("에러 : 알 수 없는 요청(" + tokens[0] + ")");
				}
			}
		} catch (SocketException e) {
			ChatServer.log("비정상 종료");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed() == false) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void doMessage(String message) {
		broadcast(this.nickname + ":" + message);
		System.out.println(this.nickname + ":" + message);
	}

	private void doQuit(Writer writer) {
		removeWriter(writer);
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}

	private void removeWriter(Writer writer) {
		synchronized (listWriters) {
			listWriters.remove(writer);
		}

	}

	private void doJoin(String nickname, Writer writer) {
		this.nickname = nickname;
		String data = nickname + "님이 참여하였습니다.";
		broadcast(data);
		addWriter(writer);

		PrintWriter printWriter = (PrintWriter) writer;
		printWriter.println("join:ok");
		printWriter.flush();
	}

	private void addWriter(Writer pw) {
		synchronized (listWriters) {
			listWriters.add(pw);
		}
	}

	private void broadcast(String data) {
		synchronized (listWriters) {
			for (Writer writer : listWriters) {
				PrintWriter printWriter = (PrintWriter) writer;
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}
}
