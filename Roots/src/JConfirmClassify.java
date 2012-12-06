
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.DualModeMouseSelector;
import org.jzy3d.maths.Coord3d;


/**
 * Graph Classification output (and allow changes)
 * 
 * @author Victoria Arendt
 *
 */
public class JConfirmClassify extends JFrame {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Root> crown;
	private ArrayList<Root> other;
	private Classify classification;
	private int cutoff;

	/**
	 * Create the application.
	 * @param listModel 
	 * @param f 
	 * @param f2 
	 * @throws FileNotFoundException 
	 */
	public JConfirmClassify(Classify cl) throws FileNotFoundException {
		
		classification = cl;
		other = cl.otherRoots;
		crown = cl.crownRoots;
		cutoff = crown.size(); // boundary between crown roots and other roots
		System.out.println(cutoff);
		
		setTitle("Confirm Classification");
		setBounds(100, 100, 1175, 563);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);



		JPanel panel = new JPanel();

		// Next button
		JButton btnNext = new JButton("Save");

		// Chart for jzy3d (see JSelectCrown for more info)
		Chart c = new Chart("swing");
		ArrayList<Integer> rootnum = new ArrayList<Integer>();
		for (int ro = 0; ro<crown.size(); ro++) {
			Root r = crown.get(ro);
			for(int i = 0; i<r.voxels.size(); i++) {
				rootnum.add(ro);
			}
		}
		for (int ro = cutoff; ro<other.size() + cutoff; ro++) {
			Root r = other.get(ro-cutoff);
			for (int i = 0; i<r.voxels.size(); i++) {
				rootnum.add(ro);
			}
		}
		org.jzy3d.colors.Color[] colors = new org.jzy3d.colors.Color[rootnum.size()];
		Coord3d[] coords = new Coord3d[rootnum.size()];

		int next = 0;
		for (Root r: crown) {
			for(int i = 0; i<r.voxels.size(); i++) {
				Coord vox =  r.voxels.get(i);
				coords[next+i] = new Coord3d(vox.x, vox.y, vox.z);
				colors[next+i] = new org.jzy3d.colors.Color(255,255,255);
			}
			next += r.voxels.size();
		}
		for (Root r: other) {
			for(int i = 0; i<r.voxels.size(); i++) {
				Coord vox =  r.voxels.get(i);
				coords[next+i] = new Coord3d(vox.x, vox.y, vox.z);
				colors[next+i] = new org.jzy3d.colors.Color(255,255,255);
			}
			next += r.voxels.size();
		}

		// Create Scatter Plot
		final MySelectableScatter scat = new MySelectableScatter(coords, colors, rootnum);
		scat.setHighlightColor(new org.jzy3d.colors.Color(255,0,0));
		DualModeMouseSelector selector = new DualModeMouseSelector(c,new MyScatterSelector(scat));
		c.getAxeLayout().setMainColor(org.jzy3d.colors.Color.WHITE);
		c.getView().setBackgroundColor(org.jzy3d.colors.Color.BLACK);
		
		// Highlight crown roots
		for (int i = 0; i<scat.getData().length; i++){
			if (scat.getGroup(i) < cutoff) {
				scat.setHighlighted(i,true);
			}
		}
		c.getScene().add(scat);
		c.render();

		panel.setLayout(new java.awt.BorderLayout());
		panel.add((JComponent)c.getCanvas());
		panel.setBounds(6, 6, 1163, 496);
		getContentPane().add(panel);

		// Next button saves files
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<Root> cr = new ArrayList<Root>();
				ArrayList<Root> ot = new ArrayList<Root>();
				ArrayList<Integer> already = new ArrayList<Integer>(); 
				for (int i = 0; i<scat.getData().length; i++ ) {
					int num = scat.getGroup(i);
					
					if (!already.contains(num)) {
						if (scat.getHighlighted(i)) {
							if (num >= cutoff) {
								num = num - cutoff;
								cr.add(other.get(num));
							}
							else{
								cr.add(crown.get(num));
							}
						}
						else {
							if (num >= cutoff) {
								num = num - cutoff;
								ot.add(other.get(num));
							}
							else {
								ot.add(crown.get(num));
							}
						}
						already.add(num);
					}
				}
				
				classification.crownRoots = cr;
				classification.otherRoots = ot;
				try {
					classification.outputRoots();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				
				setVisible(false);
				}
				

		});
		btnNext.setBounds(1084, 506, 85, 29);
		getContentPane().add(btnNext);



	}
}
