package Motion;

import Motion.TriggerMotionInterface;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotionCheck {

	public static Thread gyroCheckThread;
	public static Thread ultraCheckThread;
	public static Thread irCheckThread;
	public static Thread buttonCheckThread;
	public static String buttonStatus = "ready";
	public static double irDistance;
	public static double ultrasonicDistance;
	public static int motionOn = 0;

	public static List yawRollPitchRangeList = new ArrayList<>();
	public static List<List> differenceResultList = new ArrayList<>();
	public List<GyroMotionInterface> gyroMotionList = new ArrayList<>();
	public Map<String, Integer> motionMap = new HashMap<String, Integer>();
	public List<TriggerMotionInterface> trigerMotionList = new ArrayList<>();

	//[yaw min(0), yaw max(1), roll min(2) , roll max(3),pitch min(4),pitch max(5),
	// yaw Gap Min(6),yaw Gap Max(7),roll GapMin (8),roll Gap Max (9),pitch Gap Min (10), pitch Gap Max(11)] , 고려하지 않을 경우 max와 min에 각각 0을 넣어줌,
	//해당각의 Gap을 고려하지 않을경우 0값을 넣어줌

	private GyroMotions gyroMotions;

	public MotionCheck() {

		gyroCheckThreadStart();
		ultraCheckThreadStart();
		irCheckThreadStart();
		buttonCheckThreadStart();

		gyroMotions= new GyroMotions();
		
		double[] left={-360,360,90,270,0,0,1,80,0,1,0,0};
		double[] right={-360,360,90,270,0,0,1,80,0,1,0,0};
		double[] up={-360,360,90,270,0,0,0,1,1,80,0,0};
		double[] down={-360,360,90,270,0,0,0,1,1,80,0,0};
		double[] zigzag={-360,360,90,270,0,0,1,80,1,80,0,0};
		double[] plusYawMinusRoll={180,360,90,180,0,0,1,80,1,80,0,0};
		
		yawRollPitchRangeList.add(left); //0번
		yawRollPitchRangeList.add(right);//1번
		yawRollPitchRangeList.add(up); //2번
		yawRollPitchRangeList.add(down); //3번
		yawRollPitchRangeList.add(zigzag); //4번
		gyroMotionList.add(new GyroMotionImpl_Up());
		gyroMotionList.add(new GyroMotionImpl_Left());
		gyroMotionList.add(new GyroMotionImpl_Right());
		gyroMotionList.add(new GyroMotionImpl_Down());
		gyroMotionList.add(new GyroMotionImpl_ZigZag());
		gyroMotionList.add(new GyroMotionImpl_Circle());
		
		trigerMotionList.add(gyroMotions);
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

	public static void MotionRecognitionStatus(int status) {
		motionOn = status;
	}

	private void gyroCheckThreadStart() {

		gyroCheckThread = new Thread() {
			@Override
			public void run() {

				while (true) {
					if (motionOn == 0) {
						System.out.println("모션준비 1단계"); //나중에 삭제각
						for(TriggerMotionInterface trigger: trigerMotionList){
							trigger.triggerMotion(0);
							trigger.triggerButton(0,buttonStatus);
						}
						
						try {
							Thread.sleep(500);
						} catch (Exception e) {
						}
					}
					
					else if(motionOn == 1){
						try {
							Thread.sleep(500);
						} catch (Exception e) {
						}
					System.out.println("모션을 취하는중 2단계"); //나중에 삭제각
						for(TriggerMotionInterface trigger: trigerMotionList){
							trigger.triggerMotion(1);
							trigger.triggerButton(1,buttonStatus);
						}
						
						
					}else {
						System.out.println("모션분석중........."); //나중에 삭제각
						motionMap.clear();
						
						//범위안의 변화요소 뽑아내는 부분
						if (!yawRollPitchRangeList.isEmpty()) {
							differenceResultList = gyroMotions.Range(yawRollPitchRangeList);

							//해당 모션체크부분
							if (!differenceResultList.isEmpty()) {
								for (GyroMotionInterface motion : gyroMotionList) {
									motion.gyroMotion(differenceResultList,motionMap);
								}
								if(!motionMap.isEmpty()){
									String finalMotion=gyroMotions.motionDecision(motionMap);
									gyroMotions.action(finalMotion);
								}
								
							}
							
							motionOn=0;
						}else{
							motionOn=0;
							System.out.println("Please, Add your Motion in List");
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
