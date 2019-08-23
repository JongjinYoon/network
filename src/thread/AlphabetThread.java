package thread;

public class AlphabetThread extends Thread {

	@Override
	public void run() {
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
