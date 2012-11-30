import java.util.ArrayList;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.selectable.SelectableScatter;

/**
 * Selectable scatter plot with group numbers for each root
 * 
 * @author Victoria Arendt
 *
 */
public class MySelectableScatter extends SelectableScatter{

	/**
	 * Constructor
	 * @param coordinates
	 * @param colors
	 * @param rootnum - group numbers
	 */
	public MySelectableScatter(Coord3d[] coordinates, Color[] colors, ArrayList<Integer> rootnum) {
		super(coordinates, colors);
		groups = rootnum;
	}
	/**
	 * Get group number of index i.
	 * @param i
	 * @return group number
	 */
	public int getGroup(int i) {
		return groups.get(i);
	}
	
	

	 protected ArrayList<Integer> groups;
}
