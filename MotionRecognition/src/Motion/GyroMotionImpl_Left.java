
package Motion;

import java.util.List;
import java.util.Map;


public class GyroMotionImpl_Left implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int leftCount=0;
		List<double[]> factorsInRange = differenceResultList.get(0);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			System.out.println(count[1]);
						System.out.println(count[2]);
						System.out.println("left");
			if (count[1] > 0&&count[2]!=0) {
						
						leftCount++;
					} 
		}
		motionMap.put("left", leftCount);
	}

}
