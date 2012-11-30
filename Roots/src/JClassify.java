import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Classify root system based on given parameters
 * 
 * @author Victoria Arendt
 *
 */
public class JClassify extends JFrame{

	private static final long serialVersionUID = 7629730918957666240L;
	private LogisticRegression logres;
	private File rootFile;
	private File paramFile;
	private File saveLocation;
	private JTextField textField;
	private JTextField textField_1;
	
	/**
	 * Construct from param file
	 * @param rFile root file
	 * @param pFile parameter file
	 * @throws Exception
	 */
	public JClassify(File rFile, File pFile) throws Exception {
		setTitle("Choose Save Location for Classification Files");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		paramFile = pFile;
		rootFile = rFile;
		logres = new LogisticRegression(pFile);
		
		initialize();
	}
	
	/**
	 * Construct from logistic regression 
	 * 
	 * @param rFile root file
	 * @param l logistic regression object
	 */
	public JClassify(File rFile, LogisticRegression l) {
		setTitle("Choose Save Location for Classification Files");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		paramFile = l.getFile();
		rootFile = rFile;
		logres = l;
		
		initialize();
	}
		
	/**
	 * Initialize frame
	 */
	public void initialize() {		
		
		//Text field for save directory
		textField = new JTextField();
		textField.setBounds(23, 84, 313, 31);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		//Browse for directory
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(335, 85, 89, 30);
		getContentPane().add(btnBrowse);
		
		  btnBrowse.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();

		        // For File
		        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		        fileChooser.setAcceptAllFileFilterUsed(false);

		        int rVal = fileChooser.showSaveDialog(null);
		        saveLocation = fileChooser.getSelectedFile();
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          textField.setText(saveLocation.toString());
		        }
		      }
		    });
		
		  JLabel lblNewLabel = new JLabel("Save classification files in...");
			lblNewLabel.setBounds(135, 49, 182, 23);
			getContentPane().add(lblNewLabel);
			
			JLabel label = new JLabel("Save files with prefix...");
			label.setBounds(147, 127, 150, 23);
			getContentPane().add(label);
			
			// Text field for file prefix
			textField_1 = new JTextField();
			textField_1.setColumns(10);
			textField_1.setBounds(96, 162, 247, 31);
			getContentPane().add(textField_1);
			
			// Next Button
			JButton btnPerformClassification = new JButton("Next >");
			btnPerformClassification.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						Classify c = new Classify(rootFile, logres, saveLocation, textField_1.getText());
						JConfirmClassify classify = new JConfirmClassify(c);
						classify.setVisible(true);
						setVisible(false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			btnPerformClassification.setBounds(359, 243, 85, 29);
			getContentPane().add(btnPerformClassification);
			
			// Back Button
			JButton button = new JButton("< Prev");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					JChooseParam paramPage=new JChooseParam(rootFile, paramFile);
					paramPage.setVisible(true);
					setVisible(false);
				}
			});
			button.setBounds(6, 243, 85, 29);
			getContentPane().add(button);
			
			
	}

	
}
