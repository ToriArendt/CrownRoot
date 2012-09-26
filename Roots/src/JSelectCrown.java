import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class JSelectCrown extends JFrame {

	private File rFile;
	private File gFile;
	private DefaultListModel list;
	private ArrayList<Root> rSoFar;
	
	/**
	 * Create the application.
	 * @param listModel 
	 * @param f 
	 * @param f2 
	 */
	public JSelectCrown(File rootFile, File fileToGraph, DefaultListModel listModel, ArrayList<Root> roots) {
		rFile = rootFile;
		gFile = fileToGraph;
		list = listModel;
		rSoFar = roots;
		
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		
		//TODO: Plot gFile and make selectable.
		
		JButton btnNext = new JButton("Next >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (list.getSize() == 0) {
					JSaveLogRes saveLogRes = new JSaveLogRes(rFile, rSoFar);
					saveLogRes.setVisible(true);
					setVisible(false);
				}
				else {
					//TODO: Add roots from this file to ArrayList with characteristics
					File nextFile = new File( (String) list.remove(0));
					JSelectCrown s = new JSelectCrown(rFile, nextFile, list, rSoFar);
					s.setVisible(true);
					setVisible(false);
				}
				
			}
		});
		btnNext.setBounds(359, 243, 85, 29);
		getContentPane().add(btnNext);
		
		
		
	}

	

}
