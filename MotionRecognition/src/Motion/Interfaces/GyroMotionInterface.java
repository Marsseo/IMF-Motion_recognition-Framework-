
package Motion.Interfaces;

import java.util.List;
import java.util.Map;
/**
 * 
 * @author CheolMin Kim
 */
public interface GyroMotionInterface {
	
	public void gyroMotion(List<List> differenceResultList,Map<String,Integer> motionMap);
}
