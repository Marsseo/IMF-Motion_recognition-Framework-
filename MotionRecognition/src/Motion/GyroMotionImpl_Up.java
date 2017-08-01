package Motion;

import java.util.List;
import java.util.Map;

public class GyroMotionImpl_Up implements GyroMotionInterface {

	@Override
	public void gyroMotion(List<List> differenceResultList,Map<String,Integer> motionMap) {
		int upCount=0;
		List<double[]> factorsInRange = differenceResultList.get(2);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (count[2] < 0) {
						System.out.println(count[2]);
						System.out.println("up");
						upCount++;
					} 
		}
		motionMap.put("up", upCount);
	}

}
