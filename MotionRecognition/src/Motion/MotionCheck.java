package Motion;

import java.util.ArrayList;
import java.util.List;

public class MotionCheck {

	public static Thread gyroCheckThread;
	public static Thread ultraCheckThread;
	public static Thread irCheckThread;
	public static Thread buttonCheckThread;
	public static String buttonStatus = "off";
	public static double irDistance;
	public static double ultrasonicDistance;
	public static boolean motionOn=false;
	
	public static List yawRollPitchRangeList = new ArrayList<>();
	public static List differenceResultList = new ArrayList<>();
	//[yaw min, yaw max, roll min , roll max,pitch min,pitch max] , 고려하지 않을 경우 max와 min에 각각 0을 넣어줌

	private GyroMotionList gyroMotionList;

	public MotionCheck() {
		
		gyroCheckThreadStart();
		ultraCheckThreadStart();
		irCheckThreadStart();
		buttonCheckThreadStart();
		
		gyroMotionList=new GyroMotionList();
	}

	public static void buttonAddData(String status) {
		buttonStatus = status;
	}

	public static void irAddData(double distance) {
		irDistance = distance;
	}

	public static void ultrasonicAddData(double distance) {
		ultrasonicDistance = distance;
	}
	
	public static void MotionRecognitionStatus(boolean status){
		motionOn=status;
	}

	
	private void gyroCheckThreadStart() {
		
		gyroCheckThread = new Thread() {
			@Override
			public void run() {
              	
				while (true) {
					if(motionOn==false){
					gyroMotionList.pitchCircle();
					System.out.println("pitchCircle 실행 while문");
					try {
						Thread.sleep(200);
					} catch (Exception e) {
				}
					}else{
						System.out.println("yaw roll 실행 while문");
						try {
						Thread.sleep(1000);
					} catch (Exception e) {
					}
						if(!yawRollPitchRangeList.isEmpty()){
					differenceResultList=gyroMotionList.Range(yawRollPitchRangeList);
					
					if(!differenceResultList.isEmpty()){
						
					}
							}
						}
					}
				
			}
		};
		gyroCheckThread.start();
		
	}

	private void ultraCheckThreadStart() {
		ultraCheckThread = new Thread() {
			

		};
		ultraCheckThread.start();
	}

	private void irCheckThreadStart() {
		irCheckThread = new Thread() {

		};
		irCheckThread.start();
	}

	private void buttonCheckThreadStart() {
		buttonCheckThread = new Thread() {

		};
		buttonCheckThread.start();
	}
}
