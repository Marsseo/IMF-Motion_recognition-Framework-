package Motion;

import java.util.ArrayList;
import java.util.List;

public class GyroMotionList {

	public static List<Double> listYawAngle = new ArrayList<>();
	public static List<Double> listRollAngle = new ArrayList<>();
	public static List<Double> listPitchAngle = new ArrayList<>();

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

	public static void yawLeftRight() {
		System.out.println("yawLeftRight실행");//삭제각
		int leftCount = 0;
		int rightCount = 0;
		if (listYawAngle.size() >= 10) {
			for (int i = 0; i < listYawAngle.size() - 1; i++) {
				double rollPrevalue = listRollAngle.get(i + 1);
				double yawPrevalue = listYawAngle.get(i);
				double yawCurrvalue = listYawAngle.get(i + 1);
				double yawGap = Math.abs(yawCurrvalue - yawPrevalue);
				if (160 < rollPrevalue && rollPrevalue < 220) {
					if (yawGap > 0.3) {

						if (yawPrevalue < yawCurrvalue) {
							leftCount++;
						}

						if (yawPrevalue > yawCurrvalue) {
							rightCount++;
						}
					}
				}

			}
			if (leftCount > 6) {
				System.out.println("yaw Dirention :  <---");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
			} else if (rightCount > 6) {
				System.out.println("yaw Dirention :  --->");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
			}
		};
	}
	
	public static void rollUpDown() {
		System.out.println("rollUpDown실행");//삭제각
		int upCount = 0;
		int downCount = 0;
		
		if (listRollAngle.size() >= 10) {
			for (int i = 0; i < listRollAngle.size() - 1; i++) {
				double yawValue = listYawAngle.get(i + 1);
				double rollPrevalue = listRollAngle.get(i);
				double rollCurrvalue = listRollAngle.get(i + 1);
				double rollGap = Math.abs(rollCurrvalue - rollPrevalue);
				if (300 < yawValue || yawValue < 40) {
					if (rollGap > 0.3) {

						if (rollPrevalue < rollCurrvalue) {
							downCount++;
						}

						if (rollPrevalue > rollCurrvalue) {
							upCount++;
						}
					}
				}

			}
			if (downCount > 6) {
				System.out.println("roll Dirention :  Down↓");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
			} else if (upCount > 6) {
				System.out.println("roll Dirention :  Up ↑");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
			}
		};
	
	}

	public static void rollCircle() {
		int count = 0;
		if (listRollAngle.size() >= 8) {
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

	public synchronized static void pitchCircle() {
		boolean step1 = false;
		boolean step2 = false;
		int count = 0;
		if (listPitchAngle.size() >= 8) {
			for (int i = 0; i < listPitchAngle.size(); i++) {
				double prevalue = listPitchAngle.get(i);
				if (MotionCheck.motionOn == false) {

					if (prevalue < 130 && step1 == false) {
						step1 = true;
						System.out.println("Step1 On");
					}
					if (step1 == true) {
						if (prevalue > 170) {
							step2 = true;
							System.out.println("Step2 On");
						}
					}

					if (step1 == true && step2 == true) {
						System.out.println("Motion On");
						MotionCheck.MotionRecognitionStatus(true);
						i = listPitchAngle.size();
					}

				}

			}
		}
	}

}
