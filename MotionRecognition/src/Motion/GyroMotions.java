package Motion;

import static Motion.MotionCheck.buttonAddData;
import Motion.TriggerMotionInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;




public class GyroMotions implements TriggerMotionInterface {

	public static boolean MotionListCollecting = false;
	public static boolean adjustingYawAxis = false;
	public static double YawAxisValueForAdjusting = 0.0;

	public static List<Double> listYawAngles = new Vector<>();
	public static List<Double> listRollAngles = new Vector<>();
	public static List<Double> listPitchAngles = new Vector<>();
	public static List<Double> listYawDifferences = new Vector<>();
	public static List<Double> listRollDifferences = new Vector<>();
	public static List<Double> listPitchDifferences = new Vector<>();

	public static List<Double> listYawAngle = new Vector<>();
	public static List<Double> listRollAngle = new Vector<>();
	public static List<Double> listPitchAngle = new Vector<>();
	public static List<Double> listYawDifference = new Vector<>();
	public static List<Double> listRollDifference = new Vector<>();
	public static List<Double> listPitchDifference = new Vector<>();

	public static int listLength = 10;
	public static double initialValue = 0.0;

	public static boolean isMotionListCollecting() {
		return MotionListCollecting;
	}

	public static List<Double> getListYawAngles() {
		return listYawAngles;
	}

	public static List<Double> getListRollAngles() {
		return listRollAngles;
	}

	public static List<Double> getListPitchAngles() {
		return listPitchAngles;
	}

	public static List<Double> getListYawDifferences() {
		return listYawDifferences;
	}

	public static List<Double> getListRollDifferences() {
		return listRollDifferences;
	}

	public static List<Double> getListPitchDifferences() {
		return listPitchDifferences;
	}

	public static List<Double> getListYawAngle() {
		return listYawAngle;
	}

	public static List<Double> getListRollAngle() {
		return listRollAngle;
	}

	public static List<Double> getListPitchAngle() {
		return listPitchAngle;
	}

	public static List<Double> getListYawDifference() {
		return listYawDifference;
	}

	public static List<Double> getListRollDifference() {
		return listRollDifference;
	}

	public static List<Double> getListPitchDifference() {
		return listPitchDifference;
	}

	public static int getListLength() {
		return listLength;
	}

	public static double getInitialValue() {
		return initialValue;
	}

	public GyroMotions() {

		listYawDifferences.add(initialValue);
		listRollDifferences.add(initialValue);
		listPitchDifferences.add(initialValue);
	}

	//Gyro3축 값을 받음 , listLength 만큼의 길이의 리스트에 값을 넣는다.
	public static void gyroAddData(double yaw, double pitch, double roll) {
		if (listYawAngles.size() < listLength) {
			listYawAngles.add(yaw);
			listRollAngles.add(roll);
			listPitchAngles.add(pitch);
		} else if (listYawAngles.size() >= listLength) {
			listYawAngles.remove(0);
			listPitchAngles.remove(0);
			listRollAngles.remove(0);
			listYawAngles.add(yaw);
			listRollAngles.add(roll);
			listPitchAngles.add(pitch);
		}
		if (listYawAngles.size() >= 2) {
			double nextValue = listYawAngles.get(listYawAngles.size() - 1);
			double preValue = listYawAngles.get(listYawAngles.size() - 2);
			listYawDifferences.add(nextValue - preValue);
			nextValue = listRollAngles.get(listRollAngles.size() - 1);
			preValue = listRollAngles.get(listRollAngles.size() - 2);
			listRollDifferences.add(nextValue - preValue);
			nextValue = listPitchAngles.get(listPitchAngles.size() - 1);
			preValue = listPitchAngles.get(listPitchAngles.size() - 2);
			listPitchDifferences.add(nextValue - preValue);
			if (listYawDifferences.size() >= listLength + 1) {
				listYawDifferences.remove(0);
				listPitchDifferences.remove(0);
				listRollDifferences.remove(0);
			}
		}

		if (MotionListCollecting == true) {

			if (adjustingYawAxis == false) {
				YawAxisValueForAdjusting = 180 - yaw;
				adjustingYawAxis = true;
			} else {

				listRollAngle.add(roll);
				listPitchAngle.add(pitch);

				if (YawAxisValueForAdjusting < 0) {
				//	System.out.println("1번 1번 1번");  ///////////////////////삭제각
					if (yaw <= Math.abs(YawAxisValueForAdjusting)) {
						listYawAngle.add(360 + YawAxisValueForAdjusting + yaw);

					} else {
						listYawAngle.add(yaw + YawAxisValueForAdjusting);
					}

				} else if (YawAxisValueForAdjusting > 0) {
				//	System.out.println("2번 2번 2번");  ///////////////////////삭제각
					if (yaw> 360-YawAxisValueForAdjusting) {
						listYawAngle.add(YawAxisValueForAdjusting + yaw-360);

					} else {
						listYawAngle.add(yaw + YawAxisValueForAdjusting);
					}

				}
			//	System.out.println(listYawAngle.get(listYawAngle.size()-1));  ///////////////////////삭제각

				if (listYawAngle.size() >= 2) {
					double nextValue = listYawAngle.get(listYawAngle.size() - 1);
					double preValue = listYawAngle.get(listYawAngle.size() - 2);
					listYawDifference.add(nextValue - preValue);
					nextValue = listRollAngle.get(listRollAngle.size() - 1);
					preValue = listRollAngle.get(listRollAngle.size() - 2);
					listRollDifference.add(nextValue - preValue);
					nextValue = listPitchAngle.get(listPitchAngle.size() - 1);
					preValue = listPitchAngle.get(listPitchAngle.size() - 2);
					listPitchDifference.add(nextValue - preValue);

				} else {
					listYawDifference.add(initialValue);
					listRollDifference.add(initialValue);
					listPitchDifference.add(initialValue);
				}
			}
		} else {
			adjustingYawAxis = false;
		}
	}

	private void emptingContinuedList() {
		if (!listYawAngles.isEmpty()) {
			listYawAngles.clear();
		}
		if (!listRollAngles.isEmpty()) {
			listRollAngles.clear();
		}
		if (!listPitchAngles.isEmpty()) {
			listPitchAngles.clear();
		}
		if (!listYawDifferences.isEmpty()) {
			listYawDifferences.clear();
		}
		if (!listRollDifferences.isEmpty()) {
			listRollDifferences.clear();
		}
		if (!listPitchDifferences.isEmpty()) {
			listPitchDifferences.clear();
		}
		listYawDifferences.add(initialValue);
		listRollDifferences.add(initialValue);
		listPitchDifferences.add(initialValue);
	}

	private void emptingCollectedList() {
		if (!listYawAngle.isEmpty()) {
			listYawAngle.clear();
		}
		if (!listRollAngle.isEmpty()) {
			listRollAngle.clear();
		}
		if (!listPitchAngle.isEmpty()) {
			listPitchAngle.clear();
		}
		if (!listYawDifference.isEmpty()) {
			listYawDifference.clear();
		}
		if (!listRollDifference.isEmpty()) {
			listRollDifference.clear();
		}
		if (!listPitchDifference.isEmpty()) {
			listPitchDifference.clear();
		}
	}

	public static List Range(List<double[]> yawRollPitchRangeList) {


		//메소드에서  리턴해줄  리스트 생성
		List<List> differenceInRangeResultList = new ArrayList<>();

		//구간을 설정해놓은 갯수만큼 리스트 생성해놓기
		for (int k = 0; k < yawRollPitchRangeList.size(); k++) {
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
					double[] difference = {0, 0, 0, 0}; //해당 범위지정번호: 인덱스값 ,{값이 들어온 순서,yaw,roll,pitch}
					boolean yawEnable = true;
					boolean rollEnable = true;
					boolean pitchEnable = true;
					boolean yawSatisfaction = false;
					boolean rollSatisfaction = false;
					boolean pitchSatisfaction = false;
					double[] range = yawRollPitchRangeList.get(j);
					double yawMinRange = range[0];
					double yawMaxRange = range[1];
					double rollMinRange = range[2];
					double rollMaxRange = range[3];
					double pitchMinRange = range[4];
					double pitchMaxRange = range[5];
					double yawGapMin = range[6];
					double yawGapMax = range[7];
					double rollGapMin = range[8];
					double rollGapMax = range[9];
					double pitchGapMin = range[10];
					double pitchGapMax = range[11];
					if (yawMinRange == yawMaxRange) {
						yawEnable = false;
					}
					if (rollMinRange == rollMaxRange) {
						rollEnable = false;
					}
					if (pitchMinRange == pitchMaxRange) {
						pitchEnable = false;
					}

					if (yawEnable == true) {
						if (yawMinRange < yawAngle && yawAngle < yawMaxRange) {
							yawSatisfaction = true;
						}
					} else {
						yawSatisfaction = true;
					}

					if (rollEnable == true) {
						if (rollMinRange < rollAngle && rollAngle < rollMaxRange) {
							rollSatisfaction = true;
						}
					} else {
						rollSatisfaction = true;
					}

					if (pitchEnable == true) {
						if (pitchMinRange < pitchAngle && pitchAngle < pitchMaxRange) {
							pitchSatisfaction = true;
						}
					} else {
						pitchSatisfaction = true;
					}

					if (yawSatisfaction && rollSatisfaction && pitchSatisfaction) {
						difference[0] = i; //추후 step에 사용, 해당 값의 순서
						if (yawGapMin <= Math.abs(yawDifference) && Math.abs(yawDifference) <= yawGapMax) {
							difference[1] = yawDifference;
						}
						if (rollGapMin <= Math.abs(rollDifference) && Math.abs(rollDifference) <= rollGapMax) {
							difference[2] = rollDifference;
						}
						if (pitchGapMin <= Math.abs(pitchDifference) && Math.abs(pitchDifference) <= pitchGapMax) {
							difference[3] = pitchDifference;
						}
						List<double[]> temp = differenceInRangeResultList.get(j);
						temp.add(difference);
					}
				}
			}
		}
		return differenceInRangeResultList;
	}

	public static String motionDecision(Map<String, Integer> motionMap) {

		Set<String> keySet = motionMap.keySet();

		int prevalue = 0;
		boolean sameCount = false;
		String finalMotion = "";
		Iterator<String> keys = keySet.iterator();

		while (keys.hasNext()) {
			String key = keys.next(); // Set의 key 값을 하나씩 key에 대입
			int count = motionMap.get(key); // 해당 key에 해당하는 value 대입 / 오토 언박싱
			System.out.println(key + " : " + count);

			if (prevalue < count) {
				prevalue = count;
				finalMotion = key;
				sameCount = false;
			} else if (prevalue == count) {
				sameCount = true;
			}
			//motionMap.remove(key);
		}

		if (sameCount) {
			return "두개이상의 모션이 인식됩니다.( 인식 실패 )";
		} else {
			return finalMotion;
		}
	}

	@Override
	public void triggerMotion(int status) {
		boolean step1 = false;
		boolean step2 = false;
		int count = 0;
		if (listPitchAngles.size() >= 5) {
			for (int i = 0; i < listPitchAngles.size(); i++) {
				double prevalue = listPitchAngles.get(i);
				if (status == 0) {

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
						emptingContinuedList();
						System.out.println("Motion On");
						MotionCheck.MotionRecognitionStatus(1);
						emptingCollectedList();
						MotionListCollecting = true;
						i = listPitchAngles.size();

					}

				} else if (status == 1) {
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
						emptingContinuedList();
						System.out.println("Motion Recognition");
						MotionCheck.MotionRecognitionStatus(2);
						MotionListCollecting = false;
						i = listPitchAngles.size();
						buttonAddData("ready");  //다른 인터페이스도 이건꼭 해줘야함
					}
				}

			}
		}
	}

	@Override
	public void triggerButton(int step, String buttonStatus) {
		/*
if(!listYawAngles.isEmpty()){
			System.out.println("YawAngle:  "+listYawAngles.get(listYawAngles.size()-1));
	
			System.out.println("PitchAngle:  "+listPitchAngles.get(listPitchAngles.size()-1));
		
			System.out.println("RollAngle:  "+listRollAngles.get(listRollAngles.size()-1));
}
		*/		
				
		//모션이 취해지지 않았을경우
		if (step == 0) {
			if (buttonStatus.equals("on")) {
				emptingContinuedList();
				System.out.println("Motion On");
				MotionCheck.MotionRecognitionStatus(1);
				emptingCollectedList();
				MotionListCollecting = true;
			} 
		} else if (step == 1) {
			if(!listYawDifference.isEmpty()){
			System.out.println("YawAngle:  "+listYawDifference.get(listYawDifference.size()-1));
	
			System.out.println("PitchAngle:  "+listPitchDifference.get(listPitchDifference.size()-1));
		
			System.out.println("RollAngle:  "+listRollDifference.get(listRollDifference.size()-1));
}
			
			if (buttonStatus.equals("off")) {
				emptingContinuedList();
				System.out.println("Motion Recognition");
				MotionCheck.MotionRecognitionStatus(2);
				MotionListCollecting = false;
				buttonAddData("ready");  // 버튼 상태는 ready , on , off 세가지

			}
		}

	}

	@Override
	public void triggerIR(int step, double distance) {
		//System.out.println(distance); //삭제가가가가가각
		if (step == 0) {
			if (distance < 10) {
				emptingCollectedList();
				emptingContinuedList();
				System.out.println("Motion On");
				MotionCheck.MotionRecognitionStatus(1);
				MotionListCollecting = true;

			}
		} else if (step == 1) {
			if (distance < 10) {
				emptingContinuedList();
				System.out.println("Motion Recognition");
				MotionCheck.MotionRecognitionStatus(2);
				MotionListCollecting = false;
			}
		}

	}

	/*
	
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
									System.out.println(count[1]);
									System.out.println("right");
								}
							}else if(j==1){
								if(count[2]>=0){
									//System.out.println(count[2]);
									//System.out.println("down");
							}else{
									//System.out.println("up");
								}
							}
							}
							
							
					}
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


	 */
}
