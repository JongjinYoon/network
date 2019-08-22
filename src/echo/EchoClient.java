package echo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
	private static String SERVER_IP = "192.168.1.9";
	private static int SERVER_PORT = 8000;

	public static void main(String[] args) {
		Socket socket = null;
		try {
			// 1. 소켓 생성
			socket = new Socket();

			// 2. 서버연결
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);

			socket.connect(inetSocketAddress);

			System.out.println("[TCPClient] connected");

			// 3. IOStream 받아오기
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			// 5. 읽기
			while (true) {

				// 4. 쓰기
				Scanner s = new Scanner(System.in);
				String data = s.nextLine();
				os.write(data.getBytes("UTF-8"));
				System.out.print(">>");
				// 5. 데이터 읽기
				byte[] buffer = new byte[256];
				int readByteCount = is.read(buffer);// blocking
				if (readByteCount == -1) {
					// 정상종료 : remote socket이 close() 메소드를 통해서 정상적으로 소켓을 닫은 경우
					System.out.println("[TCPClient] closed by client");
					return;
				}

				data = new String(buffer, 0, readByteCount, "UTF-8");
				System.out.println("<<" + data);

				if (data.equals("exit")) {
					break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
