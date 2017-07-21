
package coap;

import coap.server.CoapResourceServer;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
	
	private static final Logger logger=LoggerFactory.getLogger(Main.class);
	public CoapResourceServer coapResourceServer;
	
	public Main() throws Exception {
		coapResourceServer=new CoapResourceServer();
	}
					
	public void start(){
		logger.info("실행");
		coapResourceServer.start();
		System.out.println("SensingCar start.....");
	}
	public void stop(){
		logger.info("실행");
		coapResourceServer.stop();
		System.out.println("SensingCar stop");
	}
	public static void main(String[] args) throws Exception {
		Main sensingCar=new Main();
		sensingCar.start();
		System.out.println("input command(Press q to quit)");
		Scanner scanner=new Scanner(System.in);
		String command=scanner.nextLine();
		if(command.equals("q")){
			sensingCar.stop();
		}
	}

}
