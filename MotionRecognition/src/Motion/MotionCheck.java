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
	public static int motionOn = 0;

	public static List yawRollPitchRangeList = new ArrayList<>();
	public static List<List> differenceResultList = new ArrayList<>();
	public List<GyroMotionInterface> gyroMotionList = new ArrayList<>();
	public Map<String, Integer> motionMap = new HashMap<String, Integer>();
	public List<TrigerMotionInterface> trigerMotionList = new ArrayList<>();

	//[yaw min, yaw max, roll min , roll max,pitch min,pitch max,yaw Gap,roll Gap,pitch Gap] , 고려하지 않을 경우 max와 min에 각각 0을 넣어줌,
	//해당각의 Gap을 고려하지 않을경우 0값을 넣어줌

	private GyroMotions gyroMotions;

	public MotionCheck() {

		gyroCheckThreadStart();
		ultraCheckThreadStart();
		irCheckThreadStart();
		buttonCheckThreadStart();

		gyroMotions= new GyroMotions();
		double[] leftLine={190, 270, 160, 220, 0, 0, 1, 50, 0};
		double[] rightLine={90, 170, 160, 220, 0, 0, 1, 50, 0};
		double[] upLine={170, 190, 90, 160, 0, 0,1 , 50, 0};
		double[] downLine={170, 190, 220, 270, 0, 0, 1, 50, 0};
		
		//double[] yawLine = {90, 270, 160, 220, 0, 0, 3, 0, 0};
		//double[] rollLine = {170, 190, 90, 270, 0, 0, 0, 3, 0};
		//double[] diagonalLine={0, 360, 90, 270, 0, 0, 3, 3, 0};
		yawRollPitchRangeList.add(leftLine); //0번
		yawRollPitchRangeList.add(rightLine);//1번
		yawRollPitchRangeList.add(upLine); //2번
		yawRollPitchRangeList.add(downLine); //3번
		gyroMotionList.add(new GyroMotionImpl_Up());
		gyroMotionList.add(new GyroMotionImpl_Left());
		gyroMotionList.add(new GyroMotionImpl_Right());
		gyroMotionList.add(new GyroMotionImpl_Down());
		
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
						for(TrigerMotionInterface triger: trigerMotionList){
							triger.trigerMotion(0);
						}
						System.out.println("pitchCircle 1단계"); //나중에 삭제각
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
					System.out.println("pitchCircle 2단계"); //나중에 삭제각
						for(TrigerMotionInterface triger: trigerMotionList){
							triger.trigerMotion(1);
						}
						
						
					}else {
						System.out.println("yaw roll 실행 while문"); //나중에 삭제각
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
