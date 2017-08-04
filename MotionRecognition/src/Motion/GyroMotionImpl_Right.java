
package Motion;

import java.util.List;
import java.util.Map;


public class GyroMotionImpl_Right implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int rightCount=0;
		List<double[]> factorsInRange = differenceResultList.get(1);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (count[1] <0&&count[2]!=0) {
						//System.out.println(count[1]);
						//System.out.println("right");
						rightCount++;
					} 
		}
		motionMap.put("right", rightCount);
	}

}
