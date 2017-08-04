
package Motion;

import Motion.server.CoapResourceServer;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
private static final Logger logger=LoggerFactory.getLogger(Main.class);
	public static CoapResourceServer coapResourceServer;
	public MotionCheck motionCheck;
	
	
	public Main(ActionInterface I) throws Exception {
		coapResourceServer=new CoapResourceServer();
		motionCheck=new MotionCheck(I);
	}
					
	public static void start(){
		logger.info("실행");
		coapResourceServer.start();
		System.out.println("start.....");
	}
	public static void stop(){
		logger.info("실행");
		coapResourceServer.stop();
		System.out.println("stop");
	}
	public static void main(String[] args) throws Exception {
		
		Main main=new Main(new Action()); // 사용자가 action 인터페이스를 구현해서 넣어줌
		main.start();
		System.out.println("input command(Press q to quit)");
		Scanner scanner=new Scanner(System.in);
		String command=scanner.nextLine();
		if(command.equals("q")){
			main.stop();
		}
	}
	
}
