
package Motion;

import java.util.List;
import java.util.Map;

public interface TriggerMotionInterface {
	
	public void triggerMotion(int value);
	public void triggerButton(int value,String status);
	public void triggerIR(int value,double distance);
}
