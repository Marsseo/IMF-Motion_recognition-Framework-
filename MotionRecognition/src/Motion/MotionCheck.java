
package Motion;

import java.util.ArrayList;
import java.util.List;

public class MotionCheck {
	public static Thread gyroCheckThread;
	public static Thread ultraCheckThread;
	public static Thread irCheckThread;
	public static Thread buttonCheckThread;
	private static List<String> list = new ArrayList<>();

	public MotionCheck(){
		gyroCheckThreadStart();
		ultraCheckThreadStart();
		irCheckThreadStart();
		buttonCheckThreadStart();
	}
	
	
	public static void addData(double y, double p, double r) {
		//list.add(y + " " + p + " " + r);
		String strData = y + " " + p + " " + r;
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
