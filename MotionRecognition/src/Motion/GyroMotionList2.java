
package Motion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GyroMotionList2 {

	private static List<Double> listYawAngle=GyroMotions.listYawAngle;
	private static List<Double> listRollAngle=GyroMotions.listRollAngle;
	private static List<Double> listPitchAngle=GyroMotions.listPitchAngle;
	
	
	
	public static void pitchCircle() {
		boolean step1 = false;
		boolean step2 = false;
		int count = 0;
		if (listPitchAngle.size() >= 8) {
			for (int i = 0; i < listPitchAngle.size(); i++) {
				double prevalue = listPitchAngle.get(i);
				if (MotionCheck.motionOn == 0) {

				if (prevalue < 90) {
					step1 = true;
				}
				if (step1 == true) {
					if (prevalue > 170) {
						step2 = true;
					}
				}

				if (step1 == true && step2 == true) {
					System.out.println("Motion On");
					MotionCheck.MotionRecognitionStatus(1);
				}
					
				}

			}
		}
	}
	public static void leftright() {
		System.out.println("yawCircle실행");//삭제각
		int leftCount = 0;
		int rightCount = 0;
		if (listYawAngle.size() >= 10) {
			for (int i = 0; i < listYawAngle.size() - 1; i++) {
				double rollPrevalue = listRollAngle.get(i + 1);
				double yawPrevalue = listYawAngle.get(i);
				double yawCurrvalue = listYawAngle.get(i + 1);
				double yawGap = Math.abs(yawCurrvalue - yawPrevalue);
				if (170 < rollPrevalue && rollPrevalue < 220) {
					if (yawGap > 0.5) {

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
				MotionCheck.MotionRecognitionStatus(0);
				System.out.println("Motion Off");
			} else if (rightCount > 6) {
				System.out.println("yaw Dirention :  --->");
				MotionCheck.MotionRecognitionStatus(0);
				System.out.println("Motion Off");
			}
		};
		
		
	}
	
	public static void updown() {
		
		int upCount = 0;
		int downCount = 0;
		if (listRollAngle.size() >= 10) {
			for (int i = 0; i < listRollAngle.size() - 1; i++) {
				//double rollPrevalue = listRollAngle.get(i + 1);
				double rollPrevalue = listRollAngle.get(i);
				double rollCurrvalue = listRollAngle.get(i + 1);
				double rollGap = Math.abs(rollCurrvalue - rollPrevalue);
				if (170 < rollPrevalue && rollPrevalue < 220) {
					if (rollGap > 0.5) {

						if (rollPrevalue < rollCurrvalue) {
							upCount++;
						}

						if (rollPrevalue > rollCurrvalue) {
							downCount++;
						}
					}
				}
				
			}
			if (upCount > 6) {
				System.out.println("Roll Dirention :  up");
				MotionCheck.MotionRecognitionStatus(0);
				System.out.println("Motion Off");
			} else if (downCount > 6) {
				System.out.println("Roll Dirention :  down");
				MotionCheck.MotionRecognitionStatus(0);
				System.out.println("Motion Off");
			}
		};
		
		
	}
	
	public static void circleCheck(){
		
		double circle=0;
		
		if(listRollAngle.size()>99){
			
			for(int j=0;j<100;j++){
				
				circle = Math.hypot(Math.sin(listRollAngle.get(j)), Math.cos(listYawAngle.get(j)));
				
				System.out.println("is it a circle? : "+circle);
			}
			MotionCheck.MotionRecognitionStatus(0);
			
		
		}
		
	}
	
	public static Map<String, String> roundline(){
		
				
		Map<String, String> infoMap = new HashMap<>();
		
		double preYaw, curYaw;
		double preRoll, curRoll;
		double prePitch, curPitch;
		
		double yawGap, rollGap, pitchGap;
		
		int countYaw=0, countRoll=0, countPitch=0;
		
		int directionYaw=0, directionRoll=0, directionPitch=0;
		
		String direction[] = {"left","up","right","down"};		
		
				
		if(listYawAngle.size()>29){
			
			for(int i=0;i<listYawAngle.size()-2;i++){
				preYaw = listYawAngle.get(i);
				curYaw = listYawAngle.get(i+1);
				preRoll = listRollAngle.get(i);
				curRoll = listRollAngle.get(i+1);
				prePitch = listPitchAngle.get(i);
				curPitch = listPitchAngle.get(i+1);

				yawGap = curYaw-preYaw;
				rollGap = curRoll-preRoll;
				pitchGap = curPitch-prePitch;

				if(yawGap>rollGap){
					countYaw++;
					if(yawGap>0) directionYaw++;
					else directionYaw--;
				}
				else if(yawGap<rollGap){
					countRoll++;
					if(rollGap>0) directionRoll++;
					else directionRoll--;
				}

				if(pitchGap>0) countPitch++;
				else countPitch--;
				
				
			}		


			double probalityYaw = countYaw/30.0;
			double probalityRoll = countRoll/30.0;

			if(probalityYaw > probalityRoll){

				if(directionYaw>0) infoMap.put("direction", direction[0]);
				else infoMap.put("direction", direction[2]);

			}else if(probalityYaw < probalityRoll){

				if(directionRoll>0) infoMap.put("direction", direction[1]);
				else infoMap.put("direction", direction[3]);

			}

			
		}
		MotionCheck.MotionRecognitionStatus(0);
		return infoMap;
			
	}
	
	private static void putMap(Map<String, String>map, String key, String value){
		value += value+"&";
		map.put(key, value);
	}
}
