
package Motion.Interfaces;
/**
 * 
 * @author CheolMin Kim
 */
public interface TriggerMotionInterface {
	
	public void triggerMotion(int value);
	public void triggerButton(int value,String status);
	public void triggerIR(int value,double distance);
}
