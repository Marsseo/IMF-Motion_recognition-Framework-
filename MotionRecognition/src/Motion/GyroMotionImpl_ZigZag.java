package Motion;

import java.util.List;
import java.util.Map;

public class GyroMotionImpl_ZigZag implements GyroMotionInterface {

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int zigzagCount = 0;
		boolean step1 = true;
		boolean step2 = false;
		boolean step3 = false;

		boolean rightCheck = false;
		List<double[]> factorsInRange = differenceResultList.get(4);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (step1 == true) {
				//오른쪽직선일때
				if (count[1] < 0 && count[2] == 0) {
					
					rightCheck = false;

					zigzagCount++;
				} else if (count[1] > 0 && count[2] > 0) {
					if (rightCheck) {
						step1 = false;
						step2 = true;
						System.out.println("zigzag - Step1");
						zigzagCount++;
						zigzagCount++;
						
					}else{
					rightCheck = true;
					}
				} else {
					rightCheck = false;
				}
			}else if (step2 == true) {
				if (count[1] > 0 && count[2] > 0) {
					step2 = false;
					step3 = true;
					System.out.println("zigzag - Step2");
					zigzagCount++;
				}

			} else if (step3 == true) {
				if (count[1] < 0 && count[2] == 0) {
					zigzagCount++;
					System.out.println("zigzag - Step3");
				}
			}

		}
		if(step3==true){
		motionMap.put("zigzag", zigzagCount);
		}else{
			motionMap.put("zigzag", 0);
		}
	}

}
