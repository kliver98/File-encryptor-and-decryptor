package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import control.Controller;

@SuppressWarnings("serial")
public class PanelInformation extends JPanel implements ActionListener {
	
	/**
	 * Relation to button for load sha1 file
	 */
	private JButton btnLoadSHA1;
	/**
	 * Relation to label that shows text for sha1 found
	 */
	private JLabel lblPathSHA1;
	/**
	 * Relation to label for current status of application
	 */
	private JLabel lblCurrentStatus;
	/**
	 * Relation to text field that puts sha1 found in file charged
	 */
	private JTextField txtFieldSHA1;
	/**
	 * Relation to file chooser and get information of file selected
	 */
	private JFileChooser chooser;
	
	/**
	 * Constructor of panel that initialize and set relation with controller
	 * @param Controller controller
	 */
	public PanelInformation() {
		init();
	}
	
	/**
	 * Method that initialize panel and load sub panels
	 */
	public void init() {
		setLayout(new BorderLayout());
		int[] dims = {20,10,10,10};
		setBorder(BorderFactory.createEmptyBorder(dims[0],dims[1],dims[2],dims[3]));
		loadPanel();
	}
	
	/**
	 * Method that load sub panels
	 */
	private void loadPanel() {
		btnLoadSHA1 = new JButton(Constants.LOAD_SHA1);
		btnLoadSHA1.setFont(new Font("Arial",Font.BOLD,14));
		btnLoadSHA1.addActionListener(this);
		
		lblPathSHA1 = new JLabel(Constants.ORIGINAL_SHA1);
		lblPathSHA1.setFont(new Font("Arial",Font.BOLD,13));
		lblPathSHA1.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblCurrentStatus = new JLabel(Constants.STATUS);
		lblCurrentStatus.setFont(new Font("Arial",Font.PLAIN,13));
		lblCurrentStatus.setHorizontalAlignment(SwingConstants.LEFT);
		
	    JPanel aux = new JPanel(new GridLayout(2,1));
	    JPanel aux2 = new JPanel(new GridLayout(1,3));
	    JPanel aux3 = new JPanel(new GridLayout(1,1));
	    
	    txtFieldSHA1 = new JTextField();
	    txtFieldSHA1.setEditable(false);
	    
	    aux2.add(btnLoadSHA1);
	    aux2.add(lblPathSHA1);
	    aux2.add(txtFieldSHA1);
	    aux3.add(lblCurrentStatus);
	    aux.add(aux2);
	    aux.add(aux3);
	    add(aux);
	}
	
	/**
	 * Method that allows open window to choose file and return file choosed
	 * @return File file choosed or RuntimeException if no file were selected
	 */
	private File chooseFile() {
		chooser = new JFileChooser("./");
		chooser.setDialogTitle(Constants.LOAD_SHA1);
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}

		throw new RuntimeException("No file selected");
	}
	
	/**
	 * Method to get first line of file selected
	 * @param File file to read
	 * @return String line readed
	 */
	private String getFirstLine(File file) {
		String line = Constants.EXCEPTION+Constants.FILE_ERROR;
		Scanner scanner = null;
		try {			
			scanner = new Scanner(file);
			if (scanner.hasNextLine())
				line = scanner.nextLine();
		} catch(Exception err) {
			return line;
		} finally {			
			scanner.close();
		}
		return line;
	}
	
	/**
	 * Method that set information of file sha1 founded
	 * @param String text to put on
	 */
	private void changeLblPathSHA1(String text) {
		Runnable runnable =
		        () -> { 
		        	try {
						lblPathSHA1.setForeground(new Color(50, 106, 16));
						lblPathSHA1.setText(text);
						this.update(this.getGraphics());
						Thread.sleep(3L * 1000L);
					} catch(Exception err) {
						System.out.println(err);
					} finally {
						lblPathSHA1.setForeground(Color.BLACK);
						lblPathSHA1.setText(Constants.ORIGINAL_SHA1);
					}
		        };
		 Thread thread = new Thread(runnable);
		 thread.start();
	}
	
	/**
	 * Method to return path of file choosed
	 * @return String path of file choosed
	 */
	public String getPathToFileChoosed() {
		return chooser!=null ? chooser.getSelectedFile().getAbsolutePath():null;
	}
	
	/**
	 * Method to get password when decipher
	 * @return String password for decipher file
	 */
	public String getPasswordToDecipher() {
		return txtFieldSHA1.getText();
	}

	/**
	 * Method that its called when any button its pressed and calls its respective methods
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if (c.equals(Constants.LOAD_SHA1)) {
			File file = null;
			try {				
				file = chooseFile();
				String hash = getFirstLine(file);
				if (Controller.isValidSHA1(hash))
					txtFieldSHA1.setText(hash);
				else {
					txtFieldSHA1.setText(Constants.FILE_ERROR);
					return;
				}
				StringSelection stringSelection = new StringSelection (hash);
				Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
				clpbrd.setContents (stringSelection, null);
				changeLblPathSHA1("Copied to clipboard");
				
			} catch(Exception err) {
				System.out.println(Constants.EXCEPTION+err.getMessage());
			}
		}
	}

}
