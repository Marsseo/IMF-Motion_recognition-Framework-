
package Motion;

import java.util.ArrayList;
import java.util.List;

public class MotionCheck {
	public static Thread gyroCheckThread;
	public static Thread ultraCheckThread;
	public static Thread irCheckThread;
	public static Thread buttonCheckThread;
	public static String buttonStatus="";
	public static double irDistance;
	public static double ultrasonicDistance;
	private static List<Double> listYawAngle = new ArrayList<>();
	private static List<Double> listRollAngle = new ArrayList<>();
	private static List<Double> listPitchAngle = new ArrayList<>();

	public MotionCheck(){
		gyroCheckThreadStart();
		ultraCheckThreadStart();
		irCheckThreadStart();
		buttonCheckThreadStart();
	}
	public static void buttonAddData(String status){
		buttonStatus=status;
	}
	public static void irAddData(double distance){
		irDistance=distance;
	}
	public static void ultrasonicAddData(double distance){
		ultrasonicDistance=distance;
	}
	
	public static void gyroAddData(double yaw, double pitch, double roll) {
		//list.add(y + " " + p + " " + r);
		//String strData = y + " " + p + " " + r;
		//processFile(strData);
		
		
	}
	
	private void gyroCheckThreadStart(){
		gyroCheckThread = new Thread() {
            @Override
			public void run() {
				
				while(true){
					
					try {Thread.sleep(1000);} catch (Exception e) {}
				}
			}
        };
        gyroCheckThread.start();
	}
	
	private void ultraCheckThreadStart(){
		ultraCheckThread= new Thread() {
            
        };
        ultraCheckThread.start();
	}
	private void irCheckThreadStart(){
		irCheckThread= new Thread() {
            
        };
        ultraCheckThread.start();
	}
	private void buttonCheckThreadStart(){
	     buttonCheckThread= new Thread() {
            
        };
        ultraCheckThread.start();
	}
}
