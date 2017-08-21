
package Motion.gyroMotionInterfaceImpl;

import Motion.Interfaces.GyroMotionInterface;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author CheolMin Kim
 */

public class GyroMotionImpl_Right implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int rightCount=0;
		int totalRightDifference=0;
		List<double[]> factorsInRange = differenceResultList.get(1);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (count[1] <0&&count[2]!=0) {
						//System.out.println(count[1]);
						//System.out.println("right   "+count[1]);
						rightCount++;
						totalRightDifference+=count[1];
					} 
		}
		System.out.println("totalRightDifference :  "+totalRightDifference);
		motionMap.put("right", rightCount);
	}

}
