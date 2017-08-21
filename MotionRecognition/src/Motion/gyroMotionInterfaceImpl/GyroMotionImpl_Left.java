
package Motion.gyroMotionInterfaceImpl;

import Motion.Interfaces.GyroMotionInterface;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author CheolMin Kim
 */

public class GyroMotionImpl_Left implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int leftCount=0;
		int totalLeftDifference=0;
		List<double[]> factorsInRange = differenceResultList.get(0);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			System.out.println(count[1]);
						//System.out.println("left   :"+count[2]);    // 삭제가가가가가가각
			if (count[1] > 0&&count[2]!=0) {
						totalLeftDifference+=count[1];
						leftCount++;
					} 
		}
		System.out.println("totalLeftDifference :  "+totalLeftDifference);
		motionMap.put("left", leftCount);
	}

}
