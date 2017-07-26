package Motion;

import java.util.ArrayList;
import java.util.List;

public class GyroMotionList {

	private static List<Double> listYawAngle = new ArrayList<>();
	private static List<Double> listRollAngle = new ArrayList<>();
	private static List<Double> listPitchAngle = new ArrayList<>();

	//Gyro3축 값을 받음
	public static void gyroAddData(double yaw, double pitch, double roll) {
		//list.add(y + " " + p + " " + r);
		//String strData = y + " " + p + " " + r;
		//processFile(strData);

		if (listYawAngle.size() < 10) {
			listYawAngle.add(yaw);
			listRollAngle.add(roll);
			listPitchAngle.add(pitch);
		} else if (listYawAngle.size() >= 10) {
			listYawAngle.remove(0);
			listPitchAngle.remove(0);
			listRollAngle.remove(0);
			listYawAngle.add(yaw);
			listRollAngle.add(roll);
			listPitchAngle.add(pitch);
		}

	}

	public static void yawCircle() {
		int leftCount = 0;
		int rightCount = 0;
		if (listYawAngle.size() >= 10) {
			for (int i = 0; i < listYawAngle.size() - 1; i++) {
				double prevalue = listYawAngle.get(i);
				double currvalue = listYawAngle.get(i + 1);

				if (prevalue < currvalue) {
					leftCount++;
				}

				if (prevalue > currvalue) {
					rightCount++;
				}

			}
			if (leftCount > 5) {
				System.out.println("yaw Dirention :  <---");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
			} else if (rightCount > 5) {
				System.out.println("yaw Dirention :  --->");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
			}
		};
	}

	public static void rollCircle() {
		int count = 0;
		if (listRollAngle.size() >= 2) {
			for (int i = 0; i < listRollAngle.size() - 1; i++) {
				double prevalue = listRollAngle.get(i);
				double currvalue = listRollAngle.get(i + 1);

				if (prevalue < currvalue) {
					count++;
				}

			}
			if (count == 9) {
				System.out.println("o");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
			}
		};
	}

	public static void pitchCircle() {
		boolean step1 = false;
		boolean step2 = false;
		int count = 0;
		if (listPitchAngle.size() >= 8) {
			for (int i = 0; i < listPitchAngle.size(); i++) {
				double prevalue = listPitchAngle.get(i);
				if (MotionCheck.motionOn == false) {

				if (prevalue < 130) {
					step1 = true;
				}
				if (step1 == true) {
					if (prevalue > 170) {
						step2 = true;
					}
				}

				if (step1 == true && step2 == true) {
					System.out.println("Motion On");
					MotionCheck.MotionRecognitionStatus(true);
				}
					
				}

			}
		}
	}

}
