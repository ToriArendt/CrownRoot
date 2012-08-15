/**
 * Coordinate (voxel) objects
 * 
 * @author ToriArendt
 *
 */
public class Coorddoub implements Comparable<Coorddoub>{
	public double x;
	public double y;
	public double z;

	/**
	 * Constructor
	 * @param xCoord: x
	 * @param yCoord: y
	 * @param zCoord: z
	 */
	public Coorddoub(double xCoord, double yCoord, double zCoord){
		x = xCoord;
		y = yCoord;
		z = zCoord;
	}

	/**
	 * Determines if each coordinate is equal to another
	 * @param other: other coordinate compared to
	 * @return
	 */
	public boolean equals(Coorddoub other) {
		if (x == other.x && y == other.y && z == other.z) {
			return true;
		}
		return false;
	}

	/**
	 * Compares coordinate to another coordinate
	 */

	@Override
	public int compareTo(Coorddoub o) {
		if (o.z != z){
			if (z-o.z>0){
				return 1;
			}
			else {
				return -1;
			}
		}
		if (o.y != y) {
			if (y-o.y>0){
				return 1;
			}
			else {
				return -1;
			}
		}
		if (o.y != y) {
			if (x-o.x>0){
				return 1;
			}
			else {
				return -1;
			}
		}
		return 0;
	}
	
	public double size() {
		double sum = Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2);
		return Math.sqrt(sum);
	}
}
