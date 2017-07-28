package Motion;

import java.util.ArrayList;
import java.util.List;

public class GyroMotionList {

	public static List<Double> listYawAngle = new ArrayList<>();
	public static List<Double> listRollAngle = new ArrayList<>();
	public static List<Double> listPitchAngle = new ArrayList<>();
	public static List<Double> listYawDifference = new ArrayList<>();
	public static List<Double> listRollDifference = new ArrayList<>();
	public static List<Double> listPitchDifference = new ArrayList<>();
	public static int listLength = 10;

	public GyroMotionList() {
		double initialValue = 0.0;
		listYawDifference.add(initialValue);
		listRollDifference.add(initialValue);
		listPitchDifference.add(initialValue);
	}

	//Gyro3축 값을 받음 , listLength 만큼의 길이의 리스트에 값을 넣는다.
	public static void gyroAddData(double yaw, double pitch, double roll) {
		if (listYawAngle.size() < listLength) {
			listYawAngle.add(yaw);
			listRollAngle.add(roll);
			listPitchAngle.add(pitch);
		} else if (listYawAngle.size() >= listLength) {
			listYawAngle.remove(0);
			listPitchAngle.remove(0);
			listRollAngle.remove(0);
			listYawAngle.add(yaw);
			listRollAngle.add(roll);
			listPitchAngle.add(pitch);
		}
		if (listYawAngle.size() >= 2) {
			double nextValue = listYawAngle.get(listYawAngle.size()-1);
			double preValue = listYawAngle.get(listYawAngle.size() - 2);
			listYawDifference.add(nextValue - preValue);
			nextValue = listRollAngle.get(listRollAngle.size()-1);
			preValue = listRollAngle.get(listRollAngle.size() - 2);
			listRollDifference.add(nextValue - preValue);
			nextValue = listPitchAngle.get(listPitchAngle.size()-1);
			preValue = listPitchAngle.get(listPitchAngle.size() - 2);
			listPitchDifference.add(nextValue - preValue);
			if(listYawDifference.size()>=listLength+1){
				listYawDifference.remove(0);
			listPitchDifference.remove(0);
			listRollDifference.remove(0);
		}	
		}
	}
	
	
	public static void yawLine(List<List> differenceResultList){
		System.out.println(differenceResultList.size());
		
						
						for(int i=0;i<differenceResultList.size();i++){
							List<double[]> factorsInRange =differenceResultList.get(i);
							for(int j=0;j<factorsInRange.size();j++){
							
							double[] count=factorsInRange.get(j);
							if(j==0){
							
								if(count[1]>=0){
									System.out.println(count[1]);
									System.out.println("left");
							}else{
									System.out.println("right");
								}
							}else if(j==1){
								if(count[2]>=0){
									System.out.println(count[2]);
									System.out.println("down");
							}else{
									System.out.println("up");
								}
							}
							}
							
							
					}
	}

	public static List Range(List<double[]> yawRollPitchRangeList) {
		double[] difference = {0,0,0,0}; //해당 범위지정번호: 인덱스값 ,{값이 들어온 순서,yaw,roll,pitch}
		List<List> differenceInRangeResultList = new ArrayList<>();
		for(int k=0;k<yawRollPitchRangeList.size();k++){
			List<double[]> differenceInRangeList = new ArrayList<>();
			differenceInRangeResultList.add(differenceInRangeList);
		}
		if (listYawAngle.size() >= listLength) {
			for (int i = 0; i < listYawAngle.size(); i++) {
				double yawAngle = listYawAngle.get(i);
				double rollAngle = listRollAngle.get(i);
				double pitchAngle = listPitchAngle.get(i);
				double yawDifference = listYawDifference.get(i);
				double rollDifference = listRollDifference.get(i);
				double pitchDifference = listPitchDifference.get(i);
				for (int j = 0; j < yawRollPitchRangeList.size(); j++) {
					boolean yawEnable=true;
					boolean rollEnable=true;
					boolean pitchEnable=true;
					boolean yawSatisfaction=false;
					boolean rollSatisfaction=false;
					boolean pitchSatisfaction=false;
					double[] range = yawRollPitchRangeList.get(j);
					double yawMinRange = range[0];
					double yawMaxRange = range[1];
					double rollMinRange = range[2];
					double rollMaxRange = range[3];
					double pitchMinRange = range[4];
					double pitchMaxRange = range[5];
					double yawGap=range[6];
					double rollGap=range[7];
					double pitchGap=range[8];
					if(yawMinRange== yawMaxRange)yawEnable=false;
					if(rollMinRange==rollMaxRange)rollEnable=false;
					if(pitchMinRange==pitchMaxRange)pitchEnable=false;
					
					if(yawEnable==true){
						if(yawMinRange < yawAngle && yawAngle < yawMaxRange ){
							yawSatisfaction=true;
						}
					}else{
						yawSatisfaction=true;
					}
					
					if(rollEnable==true){
						if(rollMinRange < rollAngle && rollAngle < rollMaxRange ){
							rollSatisfaction=true;
						}
					}else{
						rollSatisfaction=true;
					}
					
					if(pitchEnable==true){
						if(pitchMinRange < pitchAngle && pitchAngle < pitchMaxRange ){
							pitchSatisfaction=true;
						}
					}else{
						pitchSatisfaction=true;
					}
					
					if (yawSatisfaction==true&&rollSatisfaction==true&&pitchSatisfaction==true) {
						difference[0]=i; //추후 step에 사용, 해당 값의 순서
						if(yawDifference<=yawGap||yawGap==0){
							difference[1] = yawDifference;
						}
					    if(rollDifference<=rollGap||rollGap==0){
								difference[2] = rollDifference;
							}
						if(pitchDifference<=pitchGap||pitchGap==0){
								difference[3]= pitchDifference;
							}
						List<double[]> temp=differenceInRangeResultList.get(j);
						temp.add(difference);
					}
				}
			}
		}
		return differenceInRangeResultList;
	}

	public static int yawLeftRight() {
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
				return leftCount;
			} else if (rightCount > 6) {
				System.out.println("yaw Dirention :  --->");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
				return rightCount;
			}
		};
		return 0;
	}

	public static int rollUpDown() {
		System.out.println("rollUpDown실행");//삭제각
		int upCount = 0;
		int downCount = 0;
		//int[] upCount={0,0};
		//int[] downCount={1,0};

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
				return downCount;

			} else if (upCount > 6) {
				System.out.println("roll Dirention :  Up ↑");
				MotionCheck.MotionRecognitionStatus(false);
				System.out.println("Motion Off");
				return upCount;
			}
		};
		return 0;
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
