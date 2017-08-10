
package sensor;


public class Quaternion {

	private float w, x, y, z;
	
	public Quaternion(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void set(float w, float x, float y, float z) {
		this.w = w;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Quaternion getProduct(Quaternion q){
		return new Quaternion(
			w*q.w - x*q.x - y*q.y - z*q.z,    // new w
			w*q.x + x*q.w + y*q.z - z*q.y,  // new x
			w*q.y - x*q.z + y*q.w + z*q.x,  // new y
			w*q.z + x*q.y - y*q.x + z*q.w  // new z
		);
	}
}
