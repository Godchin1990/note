package godchin.codelife.control;

import java.net.ServerSocket;
import java.net.Socket;

public class InstanceControl extends Thread {
	public void run() {
		try {
			Socket sock = new Socket("127.0.0.1", 22222); 
			System.exit(0); 
		} catch (Exception e) {
		}
		try {
			ServerSocket server = new ServerSocket(22222); 
			while (true) {
				server.accept(); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}