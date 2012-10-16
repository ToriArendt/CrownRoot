import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

import java.awt.event.ActionListener;
import java.io.File;


public class JFileBrowser extends JFrame {

	File file;
	private JTextField textField;
	String fileContent = ""; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFileBrowser window = new JFileBrowser();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public JFileBrowser() {
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		textField = new JTextField();
		textField.setBounds(23, 122, 313, 31);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.setBounds(334, 123, 89, 30);
		getContentPane().add(btnBrowse);
		
		  btnBrowse.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
		        JFileChooser fileChooser = new JFileChooser();

		        // For File
		        
	
		        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		        fileChooser.setAcceptAllFileFilterUsed(false);

		        int rVal = fileChooser.showOpenDialog(null);
		        file = fileChooser.getSelectedFile();
		        if (rVal == JFileChooser.APPROVE_OPTION) {
		          textField.setText(file.toString());
		        }
		      }
		    });
		  

		
		JLabel lblNewLabel = new JLabel("Select the 3D root model to classify.");
		lblNewLabel.setBounds(101, 41, 235, 70);
		getContentPane().add(lblNewLabel);
		
		JButton btnNext = new JButton("Next >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JChooseParam paramPage=new JChooseParam(file);
				paramPage.setVisible(true);
				setVisible(false);
			}
		});
		btnNext.setBounds(359, 243, 85, 29);
		getContentPane().add(btnNext);
	}
}
