/**
 * Coordinate (voxel) objects
 * 
 * @author ToriArendt
 *
 */
public class Coord implements Comparable<Coord>{
	public int x;
	public int y;
	public int z;

	/**
	 * Constructor
	 * @param xCoord: x
	 * @param yCoord: y
	 * @param zCoord: z
	 */
	public Coord(int xCoord, int yCoord, int zCoord){
		x = xCoord;
		y = yCoord;
		z = zCoord;
	}
	
	/**
	 * Determines if each coordinate is equal to another
	 * @param other: other coordinate compared to
	 * @return
	 */
	public boolean equals(Coord other) {
		if (x == other.x && y == other.y && z == other.z) {
			return true;
		}
		return false;
	}

	/**
	 * Compares coordinate to another coordinate
	 */
	public int compareTo(Coord o) {
		if (o.z != z)
			return z - o.z;
		if (o.y != y) 
			return y - o.y;
		return x - o.x;
	}
}
