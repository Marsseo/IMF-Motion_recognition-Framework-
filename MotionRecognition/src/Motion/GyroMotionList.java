
package Motion;

import java.util.ArrayList;
import java.util.List;

public class GyroMotionList {
	
		private static List<Double> listYawAngle = new ArrayList<>();
	private static List<Double> listRollAngle = new ArrayList<>();
	private static List<Double> listPitchAngle = new ArrayList<>();
	
	//Gyro3축 값을 받음
		public static void gyroAddData(double yaw, double pitch, double roll) {
		//list.add(y + " " + p + " " + r);
		//String strData = y + " " + p + " " + r;
		//processFile(strData);
		
		
		if (listYawAngle.size() < 10) {
			listYawAngle.add(yaw);
			listRollAngle.add(roll);
			listPitchAngle.add(pitch);
		} else if (listYawAngle.size() >= 10) {
			listYawAngle.remove(0);
			listPitchAngle.remove(0);
			listRollAngle.remove(0);
			listYawAngle.add(yaw);
			listRollAngle.add(roll);
			listPitchAngle.add(pitch);
		}
		

	}

		
	public static void yawCircle(){
		int count=0;
		if(listYawAngle.size()>=2){
		for(int i=0;i<listYawAngle.size()-1;i++){
			double prevalue =listYawAngle.get(i);
			double currvalue=listYawAngle.get(i+1);
		    
		    if(prevalue<currvalue){
					count++;
				}
				
				
			
		}
		if(count==9){
			System.out.println("o");
		}
	};
	}
	
	public static void rollCircle(){
		int count=0;
		if(listYawAngle.size()>=2){
		for(int i=0;i<listYawAngle.size()-1;i++){
			double prevalue =listYawAngle.get(i);
			double currvalue=listYawAngle.get(i+1);
		    
		    if(prevalue<currvalue){
					count++;
				}
				
				
			
		}
		if(count==9){
			System.out.println("o");
		}
	};
	}
	
	public static void pitchCircle(){
		int count=0;
		if(listYawAngle.size()>=2){
		for(int i=0;i<listYawAngle.size()-1;i++){
			double prevalue =listYawAngle.get(i);
			double currvalue=listYawAngle.get(i+1);
		    
		    if(prevalue<currvalue){
					count++;
				}
				
				
			
		}
		if(count==9){
			System.out.println("o");
		}
	};
	}
	
}
