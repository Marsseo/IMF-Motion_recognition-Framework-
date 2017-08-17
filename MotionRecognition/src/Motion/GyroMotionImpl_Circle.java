
package Motion;

import java.util.List;
import java.util.Map;


public class GyroMotionImpl_Circle implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
	
		int circleCount=0;
		boolean step1=true;
		boolean step2=false;
		boolean step3=false;
		boolean step4=false;
		List<double[]> factorsInRange = differenceResultList.get(4);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if(step1==true){
			if (count[1] <0&&count[2]<0) {
						step1=false;
						step2=true;
						System.out.println("Step1");
						//circleCount++;
					}
			
			}else if(step2==true){
				if (count[1] < 0&&count[2]>0) {
					step2=false;	
					step3=true;    
					System.out.println("Step2");
					//circleCount++;
					}
				
			}else if(step3==true){
				if (count[1] >0&&count[2]>0) {
					step3=false;
					step4=true;
					System.out.println("Step3");
					//circleCount++;
				}
			}else if(step4==true){
				if (count[1] >0&&count[2]<0) {
					System.out.println("Step4");
				//	circleCount++;
				circleCount=10000;
				}
			}
			
		}
		motionMap.put("circle", circleCount);
	}

}
