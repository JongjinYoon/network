package util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {
		try {
			while (true) {
				Scanner s = new Scanner(System.in);
				String data = s.nextLine();
				
				if (data.equals("exit")) {
					break;
				}
				InetAddress[] ia = InetAddress.getAllByName(data);
				for (int i = 0; i < ia.length; i++) {
					System.out.println(ia[i]);
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

	}

}
