package Motion;

import java.util.List;
import java.util.Map;

public class GyroMotionImpl_UpDown implements GyroMotionInterface {

	@Override
	public void gyroMotion(List<List> differenceResultList,Map<String,Integer> motionMap) {
		int leftCount=0;
		int rightCount=0;
		int upCount=0;
		int downCount=0;
		//System.out.println("differenceResultList.size() :"+differenceResultList.size());
		for (int i = 0; i < differenceResultList.size(); i++) {
			List<double[]> factorsInRange = differenceResultList.get(i);
			for (int j = 0; j < factorsInRange.size(); j++) {

				double[] count = factorsInRange.get(j);
				if (i== 0) {

					if (count[1] >= 0) {
						System.out.println(count[1]);
						System.out.println("left");
						leftCount++;
					} else {
						System.out.println("right");
						rightCount++;
					}
				} else if (i == 1) {
					
					if (count[2] >= 0) {
						System.out.println(count[2]);
						System.out.println("down");
						downCount++;
					} else {
						System.out.println("up");
						upCount++;
					}
				}
			}

		}
		motionMap.put("left", leftCount);
		motionMap.put("right", rightCount);
		motionMap.put("up", upCount);
		motionMap.put("down", downCount);
	}

}
