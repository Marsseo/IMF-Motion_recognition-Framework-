package Motion;



public class DistanceMotionList {
	
	private boolean eventState;
	private boolean holdState;
	
	public DistanceMotionList(){
		
	}

	public boolean eventHandle(double distance, double fixPoint) {
		
		if(distance<=fixPoint){
			eventState = true;
		}else eventState = false;
		
		return eventState;
	}

	public boolean holdOn(double distance, double min, double max) {
		
		if(distance<min && distance>max){
			holdState = true;
		}else holdState = false;
		
		return holdState;		
	}	
	
	
}
