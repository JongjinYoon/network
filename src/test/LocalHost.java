package test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			String hostName = ia.getHostName();
			String hostAddress = ia.getHostAddress();
			byte[] ipAddress = ia.getAddress();

			System.out.println(hostName);
			System.out.println(hostAddress);
			for (byte ipaddress : ipAddress) {
				System.out.print(((int) ipaddress & 0x000000ff) + ".");
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
