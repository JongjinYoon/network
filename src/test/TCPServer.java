package test;

import java.io.IOException;
import java.io.*;
import java.net.*;

public class TCPServer {
	private static final int PORT = 5003;

	public static void main(String[] args) {

		ServerSocket serverSocket = null;

		try {
			// 1. 서버 소켓 생성
			serverSocket = new ServerSocket();
			
			//1-1. Time-Wait 상태에서 서버 소켓을 즉시 사용하기 위해서...
			serverSocket.setReuseAddress(true);

			// 2. Binding : Socket에 SocketAddress(IPAddress + Port) 바인딩
			InetAddress inetAddress = InetAddress.getLocalHost();
			// InetAddress 객체에 의해 표현되는 IP 주소에 해당하는 호스트네임을 포함한 String을 반환한다.

			// 로컬호스트 : 이 프로그램이 돌고있는 호스트
			InetSocketAddress inetSocketAddress = new InetSocketAddress(inetAddress, PORT);
			// 호스트 번호와 포트 번호로 부터 소켓 주소를 작성
			
			serverSocket.bind(inetSocketAddress);
			//포트와 연결
			
			System.out.println("[TCPServer] binding " + inetAddress.getHostAddress() + ":" + PORT);

			// 3. accept : 클라이언트로 부터 연결요청(Connect)을 기다린다.
			Socket socket = serverSocket.accept();// blocking 클라이언트가 연결신청할때까지 대기
			
			InetSocketAddress inetRemoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = inetRemoteSocketAddress.getAddress().getHostAddress();
			int remoteHostPort = inetRemoteSocketAddress.getPort();
			System.out.println("[TCPServer] connected from client[" + remoteHostAddress + ":" + remoteHostPort + "]");
			//클라이언트의 정보(ip,포트)를 표시
			try {
				// 4. IOStream 받아오기
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				while (true) {

					// 5. 데이터 읽기
					byte[] buffer = new byte[256];
					int readByteCount = is.read(buffer);// blocking
					if (readByteCount == -1) {
						// 정상종료 : remote socket이 close() 메소드를 통해서 정상적으로 소켓을 닫은 경우
						System.out.println("[TCPServer] closed by client");
						break;
					}

					String data = new String(buffer, 0, readByteCount, "UTF-8");
					System.out.println("[TCPServer] received : " + data);

					// 6. 데이터 쓰기
					os.write(data.getBytes("UTF-8"));
				}
			} catch (SocketException e) {
				System.out.println("[TCPServer] abnormal closed by client");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				// 7. Socket 자원정리
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 8. ServerSocket 자원정리
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
