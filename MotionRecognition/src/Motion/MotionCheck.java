package Motion;

import java.util.ArrayList;
import java.util.List;

public class MotionCheck {

	public static Thread gyroCheckThread;
	public static Thread ultraCheckThread;
	public static Thread irCheckThread;
	public static Thread buttonCheckThread;
	public static String buttonStatus = "";
	public static double irDistance;
	public static double ultrasonicDistance;

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



	private void gyroCheckThreadStart() {
		gyroCheckThread = new Thread() {
			@Override
			public void run() {

				while (true) {
					gyroMotionList.yawCircle();
					gyroMotionList.pitchCircle();
					gyroMotionList.rollCircle();
					

					try {
						Thread.sleep(1000);
					} catch (Exception e) {
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
