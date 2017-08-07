
package Motion;

import Motion.*;
import java.util.List;
import java.util.Map;


public class GyroMotionImpl_N implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int zigzagCount=0;
		boolean step1=true;
		boolean step2=false;
		boolean step3=false;
		List<double[]> factorsInRange = differenceResultList.get(4);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if(step1==true){
			if (count[2] < 0&&count[1]!=0) {
						step1=false;
						step2=true;
						System.out.println("Step1");
					}
			
			}else if(step2==true){
				if (count[1] < 0&&count[2]>0) {
					step2=false;	
					step3=true;    
					System.out.println("Step2");
					}
				
			}else if(step3==true){
				if (count[2] < 0&&count[1]!=0) {
					zigzagCount=10000;
					System.out.println("Step3");
				}
			}
			
		}
		motionMap.put("n", zigzagCount);
	}

}
