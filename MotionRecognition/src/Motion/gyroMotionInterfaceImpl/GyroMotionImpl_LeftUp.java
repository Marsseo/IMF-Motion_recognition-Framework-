
package Motion.gyroMotionInterfaceImpl;

import Motion.Interfaces.GyroMotionInterface;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author CheolMin Kim
 */

public class GyroMotionImpl_LeftUp implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int leftUpCount=0;
		List<double[]> factorsInRange = differenceResultList.get(4);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (count[1] > 0&&count[2]<0) {
					//	System.out.println(count[1]);
					//	System.out.println("left");
						leftUpCount++;
			}

		}
		motionMap.put("leftup", leftUpCount);
	}

}
