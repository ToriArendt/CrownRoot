import java.awt.event.MouseEvent;

import org.jzy3d.chart.controllers.mouse.selection.ScatterMouseSelector;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.IntegerCoord2d;
import org.jzy3d.plot3d.rendering.scene.Scene;
import org.jzy3d.plot3d.rendering.view.View;

/**
 * 
 * Scatter Mouse Selector for clicking
 * 
 * @author Victoria Arendt
 *
 */
public class MyScatterSelector extends ScatterMouseSelector{
	/**
	 * Constructor 
	 * @param scatter - scatter plot
	 */
	public MyScatterSelector(MySelectableScatter scatter) {
		super(scatter);
		this.scatter = scatter;
	}

	/**
	 * Process area selected by mouse
	 */
	@Override
	protected void processSelection(Scene scene, View view, int width,
			int height) {
		view.project();
		int selected = -1;
		Coord3d[] projection = scatter.getProjection();
		for (int i = 0; i < projection.length; i++)
			if (matchRectangleSelection(in, out, projection[i], width, height)){
				selected = scatter.getGroup(i);
			}
		if (selected >-1){
			for (int i = 0; i<projection.length; i++){
				if (scatter.getGroup(i) == selected){
					scatter.setHighlighted(i, !scatter.getHighlighted(i));
				}
			}
		}
	}

	/**
	 * Start (and end) selection when mouse clicks
	 */
	@Override
	protected void startSelection(MouseEvent e){
		in = new IntegerCoord2d(e.getX(), e.getY());
		last = new IntegerCoord2d(e.getX(), e.getY());
		out = new IntegerCoord2d(e.getX()+1, e.getY()+1); // i.e. a dot

		processSelection(chart.getScene(), chart.getView(), chart.getCanvas().getRendererWidth(), chart.getCanvas().getRendererHeight());
		chart.render();
		dragging = false;
	}

	@Override
	protected void dragSelection(MouseEvent e){	}
	@Override
	protected void releaseSelection(MouseEvent e) {	}

	protected MySelectableScatter scatter;
	/*****************************************/




}