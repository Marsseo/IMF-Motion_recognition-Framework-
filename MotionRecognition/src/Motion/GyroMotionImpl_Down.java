
package Motion;

import java.util.List;
import java.util.Map;


public class GyroMotionImpl_Down implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int downCount=0;
		List<double[]> factorsInRange = differenceResultList.get(3);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (count[2] > 0) {
						System.out.println(count[2]);
						System.out.println("down");
						downCount++;
					} 
		}
		motionMap.put("down", downCount);
	}

}
