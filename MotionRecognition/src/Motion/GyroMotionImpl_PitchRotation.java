

package Motion;

import java.util.List;
import java.util.Map;

public class GyroMotionImpl_PitchRotation implements GyroMotionInterface{

	@Override
	public void gyroMotion(List<List> differenceResultList, Map<String, Integer> motionMap) {
		int pitchRightRotationCount=0;
		int pitchLeftRotationCount=0;
		List<double[]> factorsInRange = differenceResultList.get(5);
		for (int j = 0; j < factorsInRange.size(); j++) {

			double[] count = factorsInRange.get(j);
			if (count[3] > 0&&count[2]!=0&&count[1]!=0) {
						//System.out.println(count[2]);
						//System.out.println("down");
						pitchLeftRotationCount++;
					} 
				if (count[3] < 0&&count[2]!=0&&count[1]!=0) {
						//System.out.println(count[2]);
						//System.out.println("down");
						pitchRightRotationCount++;
					} 
		}
		motionMap.put("pitchLeftRotation", pitchLeftRotationCount);
		motionMap.put("pitchRightRotation", pitchRightRotationCount);
	}

}
