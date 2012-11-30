import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Create, modify, and output root information.
 * 
 * @author Victoria Arendt
 * @param voxels: all voxels in the root
 * @param lastLevel: all voxels in last level of root (used to build root)
 * @param firstZ: z value where root is first detected
 * @param isSeed: is this a seed?
 * @param touchSeed: does the root touch the seed?
 * @param isCrown: is it a crown root (used for training)
 * @param NUMBER_ATTRIBUTES: number of features (including crown vs. non-crown). 
 * 
 */
public class Root implements Comparable<Root>{
	ArrayList<Coord> voxels = new ArrayList<Coord>(); 
	ArrayList<Coord> lastLevel = new ArrayList<Coord>(); //used by Separation.java
	int firstZ;
	boolean isSeed;
	boolean touchseed;
	boolean isCrown = false;
	
	int NUMBER_ATTRIBUTES = 6;

	/**
	 * Constructor
	 * @param vox: first voxels of root.
	 */
	public Root(ArrayList<Coord> vox) {
		firstZ = vox.get(0).z;
		voxels.addAll(vox);
		lastLevel = vox;
		isSeed=false;
	}
	
	/**
	 * Adds all coordinates in adds to voxels. 
	 * @param adds: all coordinates to be added.
	 */
	public void addCoords(ArrayList<Coord> adds) {
		voxels.addAll(adds);
		lastLevel = adds;
	}
	


	/**
	 * Compare root to another root (based on size).
	 */
	@Override
	public int compareTo(Root r) {
		return voxels.size() - r.voxels.size();
	}

	/**
	 * Compute the cross-sectional area of the root at 1/4 depth (standardized by angle of growth).
	 * @return cross-sectional area of root at 1/4 depth
	 */
	public double area(){
		ArrayList<Coorddoub> rep = getAverageVoxels(); // get representation at each level
		Collections.sort(rep); //sort by z-coordinate
		int quarter = (rep.size()/4)-1; // get 1/4 depth z-value
		if (quarter<=0) { //account for roots with small depth
			quarter = 0;
		}
		
		//count number of voxels with same z value as 1/4 depth z-value
		int count = 0; 
		for (Coord c:voxels) {
			if (c.z == rep.get(quarter).z) {
				count++;
			}
		}
		
		// standardize by sine(angle)
		return (count*Math.sin(angle()));
	}
	
	/**
	 * Return volume (number of voxels) in root
	 * 
	 * @return volume of root
	 */
	public double volume(){
		return voxels.size();
	}
	
	/**
	 * Compute ratio of point to point arc length vs. straight (beginning to end) line distance 
	 * 
	 * @return ratio modeling curvature 
	 */
	public double curvature(){
		ArrayList<Coorddoub> rep = getAverageVoxels(); // get representative voxels
		
		// compute arc length (point to point distance)
		double arc = 0;
		for(int i=0; i<rep.size()-1;i++) {
			arc += distance(rep.get(i), rep.get(i+1));
		}
		// straight line length
		double straight = distance(rep.get(0), rep.get(rep.size()-1));
		
		// return ratio
		return arc/straight;			
	}
	
	/**
	 * Get representative voxels (one voxel per z-value with average x,y values)
	 * 
	 * @return ArrayList with one (average) voxel per z value
	 */
	private ArrayList<Coorddoub> getAverageVoxels() {
		int num = voxels.size(); // total number of voxels
		Collections.sort(voxels); // sort by z-value
		int prev = voxels.get(0).z; // get minimum z-value
		ArrayList<Coord> level = new ArrayList<Coord>(); //array representing given level
		ArrayList<Coorddoub> rep = new ArrayList<Coorddoub>(); //array to be returned
		
		for(int i = 0; i<num; i++) {
			//if z of voxel i matches z of voxel i-1
			if (voxels.get(i).z == prev) { 
				level.add(voxels.get(i)); 
			}
			
			// if z of voxel i is different than z of voxel i+1, 
			else{ 
				if(level.size()>0){ 
					rep.add(center(level)); 
				}
				prev++;
				level.clear();
			}
		}
		// Add final level to rep list
		if(level.size()>0) { 
		rep.add(center(level));
		}
		return rep;
	}
	
	/**
	 * Find center point in a given z-cross section
	 * 
	 * @param level - collection of voxels from given level
	 * @return voxel with average x, y coordinates
	 */
	private Coorddoub center(ArrayList<Coord> level) {
		
		//Calculate average x,y values
		int sumx=0;
		int sumy=0;
		for(int i = 0; i<level.size(); i++) {
			sumx += level.get(i).x;
			sumy += level.get(i).y;
		}
		double x = sumx/level.size();
		double y = sumy/level.size();
		
		// create average coordinate
		Coorddoub point = new Coorddoub(x,y,level.get(0).z);
		return point;
	}

	/**
	 * Find distance between two voxels
	 * 
	 * @param coord - first voxel
	 * @param coord2 - second voxel
	 * @return distance between two voxels
	 */
	private double distance(Coorddoub coord, Coorddoub coord2) {
		double sum = Math.pow((coord.x-coord2.x),2)+ Math.pow((coord.y-coord2.y),2)+Math.pow((coord.z-coord2.z),2);
		double dist = Math.sqrt(sum);
		return dist;
	}

	/**
	 * Find angle of root growth from initiation to 1/4 depth
	 * 
	 * @return angle of root growth
	 */
	public double angle(){
		
		ArrayList<Coorddoub> rep = getAverageVoxels();
		Collections.sort(rep); //sort by z
		
		// set 1/4 depth
		int size = rep.size();
		int quarter = (size/4)-1;
		if ( quarter <= 0) { //if root is small
			quarter = size-1;
		}
		
		// get vector from 0 to 1/4 depth
		Coorddoub first = rep.get(0);
		Coorddoub next = rep.get(quarter);
		Coorddoub vector = new Coorddoub(next.x-first.x, next.y-first.y, next.z-first.z);
		
		// projection to z=0
		Coorddoub vectorhor = new Coorddoub(next.x-first.x, next.y-first.y, 0);
		
		// calculate angle between vectors
		double numerator = (vector.x*vectorhor.x) + (vector.y*vectorhor.y);
		double denominator = vector.size() * vectorhor.size();
		return Math.acos(numerator/denominator);
		
	}
	/**
	 * Feature describing if root touches seed
	 * 
	 * @return 1 if touches seed, 0 if does not touch seed
	 */
	public double attachedToSeed(){
		if (touchseed) {
			return 1;
		}
		return 0;
	}
	/**
	 * Returns isSeed parameter
	 * 
	 * @return isSeed
	 */
	public boolean isSeed() {
		return isSeed;
	}
	

	/**
	 * Is the root a crown root?
	 * 
	 * @return 1 if crown, 0 if not crown
	 */
	public double isCrown() {
		if (isCrown) {
			return 1;
		}
		return 0;
	}


	/**
	 * Print root to given file
	 * 
	 * @param out - writer for a file
	 * @throws IOException - error if file not available
	 */
	public void printTo(BufferedWriter out) throws IOException {
		//print each voxel
		for (Coord c: voxels) {
			out.write("voxel\t" + c.x + "\t" + c.y + "\t" + c.z);
			out.newLine();
		}
		
		//print features
		out.write("area\t" + area());
		out.newLine();
		out.write("angle\t" + angle());
		out.newLine();
		out.write("volume\t" + volume());
		out.newLine();
		out.write("curve\t" + curvature());
		out.newLine();
		out.write("touch\t" + attachedToSeed());
		out.newLine();
		out.newLine();
	}

	/**
	 * Return values used for logistic regression
	 * 
	 * @return double array of values for logistic regression
	 */
	public double[] getValues() {
		double[] atts = new double[NUMBER_ATTRIBUTES];
		atts[0] = area();
		atts[1] = volume();
		atts[2] = curvature();
		atts[3] = angle();
		atts[4] = attachedToSeed();
		atts[5] = isCrown();
		return atts;
	}
	
}

