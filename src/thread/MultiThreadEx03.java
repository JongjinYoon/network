package thread;

public class MultiThreadEx03 {

	public static void main(String[] args) {
		Thread thread1 = new DigitThread();// 스레드 생성
		Thread thread2 = new AlphabetThread();
		Thread thread3 = new Thread(new UppercaseAlphabetRunnableImpl());
		
		thread1.start();// 스레드 실행
		thread2.start();
		thread3.start();
	}

}
