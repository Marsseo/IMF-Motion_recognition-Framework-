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
	public static List<double[]> differenceResultList = new ArrayList<>();
	//[yaw min, yaw max, roll min , roll max,pitch min,pitch max] , 고려하지 않을 경우 max와 min에 각각 0을 넣어줌

	private GyroMotionList gyroMotionList;

	public MotionCheck() {
		
		gyroCheckThreadStart();
		ultraCheckThreadStart();
		irCheckThreadStart();
		buttonCheckThreadStart();
		
		gyroMotionList=new GyroMotionList();
		double[] yawLine={90,280,160,220,0,0};
		double[] rollLine={170,190,90,270,0,0};
		yawRollPitchRangeList.add(yawLine);
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
						//범위안의 변화요소 뽑아내는 부분
					if(!yawRollPitchRangeList.isEmpty()){
					differenceResultList=gyroMotionList.Range(yawRollPitchRangeList);
					
					//해당 모션체크부분
					if(!differenceResultList.isEmpty()){
						GyroMotionList.yawLine(differenceResultList);
					}
							}
						}
					}
				
			}
		};
//		gyroCheckThread.start();
		
	}

	private void ultraCheckThreadStart() {
		ultraCheckThread = new Thread() {
			@Override
			public void run() {
				while(true){
					System.out.println("ultra-ditance : "+ultrasonicDistance);
				}
			}

		};
		ultraCheckThread.start();
	}

	private void irCheckThreadStart() {
		irCheckThread = new Thread() {
			
			@Override
			public void run() {
				while(true){
					System.out.println("ir-ditance : "+irDistance);
				}
			}
			
		};
		irCheckThread.start();
	}

	private void buttonCheckThreadStart() {
		buttonCheckThread = new Thread() {
			@Override
			public void run() {
				while(true){
					System.out.println("button-status : "+buttonStatus);
				}				
			}
			
		};
		//buttonCheckThread.start();
	}
}
