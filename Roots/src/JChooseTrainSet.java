import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class JChooseTrainSet extends JFrame {

	private JList list;
	private DefaultListModel listModel;
	private File rFile;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 * @param rootFile 
	 */
	public JChooseTrainSet(File rootFile) {
		rFile = rootFile;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		listModel = new DefaultListModel();
		list = new JList(listModel);
		list.setBorder(new LineBorder(new Color(0, 0, 0)));
		list.setBounds(32, 35, 277, 203);
		getContentPane().add(list);
		
		JButton btnAdd = new JButton("Add Files");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

		        // For File
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        fileChooser.setMultiSelectionEnabled(true);
		        fileChooser.setAcceptAllFileFilterUsed(false);

		        int rVal = fileChooser.showOpenDialog(null);
		        File[] files = fileChooser.getSelectedFiles();
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          for (File f:files) {
		        	  listModel.addElement(f.toString());
		          }
		        }
			}
		});
		btnAdd.setBounds(321, 87, 105, 29);
		getContentPane().add(btnAdd);
		
		JButton btnDeleteFiles = new JButton("Delete Files");
		btnDeleteFiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] objects = list.getSelectedValues();
				for (Object o : objects) {
					listModel.removeElement(o);
				}
			}
		});
		btnDeleteFiles.setBounds(321, 152, 105, 29);
		getContentPane().add(btnDeleteFiles);
		
		JButton button = new JButton("Next >");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (listModel.getSize() == 0) {
					//TODO: Throw exception (find out what to do)
				}
				File f = new File((String) listModel.remove(0));
				JSelectCrown crownSelection = new JSelectCrown(rFile, f, listModel, new ArrayList<Root>());
				crownSelection.setVisible(true);
				setVisible(false);
			}
		});
		button.setBounds(359, 243, 85, 29);
		getContentPane().add(button);
		
		
	}


}
