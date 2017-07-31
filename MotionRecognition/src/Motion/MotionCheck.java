package Motion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MotionCheck {

	public static Thread gyroCheckThread;
	public static Thread ultraCheckThread;
	public static Thread irCheckThread;
	public static Thread buttonCheckThread;
	public static String buttonStatus = "off";
	public static double irDistance;
	public static double ultrasonicDistance;
	public static boolean motionOn = false;

	public static List yawRollPitchRangeList = new ArrayList<>();
	public static List<List> differenceResultList = new ArrayList<>();
	public List<GyroMotionInterface> gyroMotionList = new ArrayList<>();
	public Map<String, Integer> motionMap = new HashMap<String, Integer>();

	//[yaw min, yaw max, roll min , roll max,pitch min,pitch max,yaw Gap,roll Gap,pitch Gap] , 고려하지 않을 경우 max와 min에 각각 0을 넣어줌,
	//해당각의 Gap을 고려하지 않을경우 0값을 넣어줌

	private GyroMotions gyroMotions;

	public MotionCheck() {

		gyroCheckThreadStart();
		ultraCheckThreadStart();
		irCheckThreadStart();
		buttonCheckThreadStart();

		gyroMotions= new GyroMotions();
		double[] yawLine = {90, 280, 160, 220, 0, 0, 3, 0, 0};
		double[] rollLine = {170, 190, 90, 270, 0, 0, 0, 1, 0};
		yawRollPitchRangeList.add(yawLine);
		yawRollPitchRangeList.add(rollLine);
		gyroMotionList.add(new GyroMotionImpl_UpDown());
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

	public static void MotionRecognitionStatus(boolean status) {
		motionOn = status;
	}

	private void gyroCheckThreadStart() {

		gyroCheckThread = new Thread() {
			@Override
			public void run() {

				while (true) {
					if (motionOn == false) {
						gyroMotions.pitchCircle();
						System.out.println("pitchCircle 실행 while문");
						try {
							Thread.sleep(200);
						} catch (Exception e) {
						}
					} else {
						System.out.println("yaw roll 실행 while문");
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
						}
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
