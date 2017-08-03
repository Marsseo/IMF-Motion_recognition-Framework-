
package Motion;

import java.util.List;
import java.util.Map;


public class GyroMotionImpl_LeftDown implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int leftDownCount=0;
		List<double[]> factorsInRange = differenceResultList.get(4);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (count[1] > 0&&count[2]>0) {
						System.out.println("yaw값    "+count[1]);
						System.out.println("roll값    "+count[2]);
						//System.out.println("left");
						leftDownCount++;
						
					} 
		}
		motionMap.put("leftdown", leftDownCount);
	}

}
