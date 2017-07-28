package Motion;

import java.util.List;

public class GyroMotionImpl_UpDown implements GyroMotionInterface {

	@Override
	public void gyroMotion(List<List> differenceResultList) {
		for (int i = 0; i < differenceResultList.size(); i++) {
			List<double[]> factorsInRange = differenceResultList.get(i);
			for (int j = 0; j < factorsInRange.size(); j++) {

				double[] count = factorsInRange.get(j);
				if (j == 0) {

					if (count[1] >= 0) {
						System.out.println(count[1]);
						System.out.println("left");
					} else {
						System.out.println("right");
					}
				} else if (j == 1) {
					if (count[2] >= 0) {
						System.out.println(count[2]);
						System.out.println("down");
					} else {
						System.out.println("up");
					}
				}
			}

		}
	}

}
