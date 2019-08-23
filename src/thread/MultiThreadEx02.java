package thread;

public class MultiThreadEx02 {

	public static void main(String[] args) {
		Thread thread1 = new DigitThread();//스레드 생성
		Thread thread2 = new AlphabetThread();
		
		thread1.start();//스레드 실행
		thread2.start();
	}

}
