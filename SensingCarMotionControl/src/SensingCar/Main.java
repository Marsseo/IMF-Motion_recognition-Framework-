package SensingCar;

import Motion.GyroMotions;
import Motion.MotionCheck;
import Motion.TriggerMotionInterface;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static Motion.Main main;

	public static void main(String[] args) throws Exception {
		
		main=new Motion.Main(new Action());
		MotionCheck.triggerOnMotionList.add(new Controller());
		System.out.print("시작스");
		
		main.start();
		System.out.println("input command(Press q to quit)");
		Scanner scanner=new Scanner(System.in);
		String command=scanner.nextLine();
		if(command.equals("q")){
			main.stop();
		}
		
		
		
		
		
	}
}
