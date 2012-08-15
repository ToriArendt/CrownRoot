import java.awt.Color;
import javax.vecmath.*;
import java.util.ArrayList;
import java.util.Collections;
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
public class Root implements Comparable<Root>{
	ArrayList<Coord> voxels = new ArrayList<Coord>();
	
	Color color;
	ArrayList<Coord> lastLevel = new ArrayList<Coord>();
	int firstZ;
	boolean isSeed;
	boolean touchseed;

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

	@Override
	public int compareTo(Root r) {
		return voxels.size() - r.voxels.size();
	}
	/* Working code
	 * below here
	 */
	public double area(){
		ArrayList<Coorddoub> rep = getAverageVoxels();
		Collections.sort(rep);
		int quarter = (rep.size()/4)-1;
		if (quarter<0) {
			quarter = 1;
		}
		int count = 0;
		for (Coord c:voxels) {
			if (c.z == quarter) {
				count++;
			}
		}
		return (count*Math.sin(angle()));
	}
	
	public int volume(){
		return voxels.size();
	}
	
	public double curvature(){
		ArrayList<Coorddoub> rep = getAverageVoxels();
		double arc = 0;
		for(int i=0; i<rep.size()-1;i++) {
			arc += distance(rep.get(i), rep.get(i+1));
		}
		double straight = distance(rep.get(0), rep.get(rep.size()-1));
		return arc/straight;			
	}
	
	private ArrayList<Coorddoub> getAverageVoxels() {
		int num = voxels.size();
		Collections.sort(voxels);
		
		int minZ = voxels.get(0).z;
		
		int prev = minZ;
		ArrayList<Coord> level = new ArrayList<Coord>();
		ArrayList<Coorddoub> rep = new ArrayList<Coorddoub>();
		for(int i = 0; i<num; i++) {
			if (voxels.get(i).z == prev) {
				level.add(voxels.get(i));
			}
			else{
				if(level.size()>0){
					rep.add(center(level));
				}
				prev++;
				level.clear();
			}
		}
		if(level.size()>0) {
		rep.add(center(level));
		}
		return rep;
	}
	
	private Coorddoub center(ArrayList<Coord> level) {
		int sumx=0;
		int sumy=0;
		for(int i = 0; i<level.size(); i++) {
			sumx += level.get(i).x;
			sumy += level.get(i).y;
		}
		double x = sumx/level.size();
		double y = sumy/level.size();
		
		Coorddoub point = new Coorddoub(x,y,level.get(0).z);
		return point;
	}

	private double distance(Coorddoub coord, Coorddoub coord2) {
		double sum = Math.pow((coord.x-coord2.x),2)+ Math.pow((coord.y-coord2.y),2)+Math.pow((coord.z-coord2.z),2);
		double dist = Math.sqrt(sum);
		return dist;
	}

	public double angle(){
		ArrayList<Coorddoub> rep = getAverageVoxels();
		Collections.sort(rep);
		int size = rep.size();
		int quarter = (size/4)-1;
		if (size == quarter || quarter < 0) {
			quarter = size-1;
		}
		Coorddoub first = rep.get(0);
		Coorddoub next = rep.get(quarter);
		Coorddoub vector = new Coorddoub(next.x-first.x, next.y-first.y, next.z-first.z);
		Coorddoub vectorhor = new Coorddoub(next.x-first.x, next.y-first.y, 0);
		double numerator = (vector.x*vectorhor.x) + (vector.y*vectorhor.y);
		double denominator = vector.size() * vectorhor.size();
		return Math.acos(numerator/denominator);
		
	}
	
	public boolean attachedToSeed(){
		return isSeed;
	}
	

	public boolean isCrown() {
		double logres = 1; //Input real logistic regression here
		if (logres > 0.5) {
			return true;
		}
		else {
			return false;
		}
	}
	
}

