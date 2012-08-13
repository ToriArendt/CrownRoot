import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * Root objects
 * 
 * @author ToriArendt
 * @param voxels: all voxels in the root
 * @param color: randomly generated color code of the root
 * @param lastLevel: all voxels in last level of root (used to build root)
 * @param firstZ: z value where root is first detected
 * 
 */
public class Root {
	ArrayList<Coord> voxels = new ArrayList<Coord>();
	Color color;
	ArrayList<Coord> lastLevel = new ArrayList<Coord>();
	int firstZ;

	/**
	 * Constructor
	 * @param vox: first voxels of root.
	 */
	public Root(ArrayList<Coord> vox) {
		firstZ = vox.get(0).z;
		voxels.addAll(vox);
		Random gen = new Random();
		color = new Color(gen.nextInt(256), gen.nextInt(256), gen.nextInt(256));
		lastLevel = vox;
	}
	
	/**
	 * Adds all coordinates in adds to voxels. 
	 * @param adds: all coordinates to be added.
	 */
	public void addCoords(ArrayList<Coord> adds) {
		voxels.addAll(adds);
		lastLevel = adds;
	}
	
}

