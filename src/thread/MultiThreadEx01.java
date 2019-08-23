package thread;

public class MultiThreadEx01 {

	public static void main(String[] args) {
//		for (int i = 0; i < 10; i++) {
//			System.out.print(i);
//		}
		
		Thread digitThread = new DigitThread();
		digitThread.start();
		for (char c = 'a'; c <= 'z'; c++) {
			System.out.print(c);
			try {
				Thread.sleep(1000);//메인자체도 하나의 스레드이기 때문에 digitThread와 동일한 우선순위
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
