package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import control.Controller;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	/**
	 * Relation with panel to choose a file to cipher or decipher
	 */
	private PanelFileSelector pFileSelector;
	/**
	 * Relation with panel to show cipher or decipher options
	 */
	private PanelOptions pOptions;
	/**
	 * Relation with panel to show information about current status of application and SHA1 hash loaded when decipher file
	 */
	private PanelInformation pInformation;
	/**
	 * Relation with controller class to handle connection between model and view
	 */
	private Controller controller;
	
	/**
	 * Constructor that initialize application and panels
	 */
	public Main() {
		
		controller = new Controller();
		
		this.setLayout(new BorderLayout());
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		this.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
		this.setLocation((int)(dm.width/(3)),dm.height/5);
		this.setTitle(Constants.APP_TITLE);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(true);
		addPanels();
		this.setVisible(true);
	}
	
	/**
	 * Method that initialize panels and shows them
	 */
	public void addPanels() {
		
		pFileSelector = new PanelFileSelector();
		pOptions = new PanelOptions(this);
		pInformation = new PanelInformation();
		
		JPanel aux = new JPanel(new BorderLayout());
		int width = Constants.WIDTH;
		int height = Constants.HEIGHT;
		aux.setBorder(BorderFactory.createEmptyBorder(height/8,width/10,height/15,width/10));
		aux.add(pFileSelector,BorderLayout.NORTH);
		aux.add(pOptions,BorderLayout.SOUTH);
		aux.add(pInformation,BorderLayout.CENTER);
		
		add(aux,BorderLayout.CENTER);
		
		pack();
	}
	
	/**
	 * Method that call to controller for cipher file with path to file provided or null
	 * @return string with status returned by model
	 */
	public void cipherFile() {
		String path = pFileSelector.getPathToFileChoosed();
		if (path==null) {
			showMessage("Seleccione un archivo válido.", Status.ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
		String password = showInputDialog(Constants.INFORMATION_PASSWORD);
		if (password==null) {
			showMessage("Digite contraseña.", Status.ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
		callMethodAsynchronously(1, new String[] {path, password});
	}
	
	/**
	 * Method that call to controller for decipher file with [path to file provided or null] and [password to decipher file]
	 * @return string with status returned by model
	 */
	public void decipherFile() {
		String path = pFileSelector.getPathToFileChoosed();
		if (path==null) {
			showMessage("Seleccione un archivo válido.", Status.ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
		String sha1 = pInformation.getSHA1();
		if (sha1==null) {
			showMessage("Carge archivo con el hash SHA1 original o digitelo.", Status.ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
		String password = showInputDialog(Constants.INFORMATION_PASSWORD);
		if (password==null) {
			showMessage("Digite contraseña.", Status.ERROR, JOptionPane.ERROR_MESSAGE);
			return;
		}
		callMethodAsynchronously(2, new String[] {path,password, pInformation.getSHA1()});
	}
	
	/**
	 * Method that call cipher or decipher with information provided
	 * @param int method to know which method to call
	 * @param String array info to pass data on calling methods - 0:path of file, 1:password of file, 2:sha1 provided
	 */
	private void callMethodAsynchronously(int method, String[] info) {
		Runnable runnable = null;
		if (method == 1) {
			runnable = () -> { 
				String status = "";
	        	try {
	        		status = controller.cipherFile(info[0], info[1]);
	        		showMessage(status, Status.DONE, JOptionPane.INFORMATION_MESSAGE);
				} catch(Exception err) {
					showMessage(status, Status.ERROR, JOptionPane.ERROR_MESSAGE);
				}
	        };
		} else if (method == 2) {
			runnable = () -> { 
				String status = "";
	        	try {
	        		status = controller.decipherFile(info[0], info[1], info[2]);
	        		showMessage(status, Status.DONE, JOptionPane.INFORMATION_MESSAGE);
				} catch(Exception err) {
					showMessage(status, Status.ERROR, JOptionPane.ERROR_MESSAGE);
				}
	        };
		}
		
		 Thread thread = new Thread(runnable);
		 thread.start();
	}
	
	/**
	 * Method to show a input dialog and user type information
	 * @param String message to be shown
	 * @return String with what user types
	 */
	private String showInputDialog(String message) {
		Object response = JOptionPane.showInputDialog(null, message);
		return response!=null ? response.toString():null;
	}
	
	/**
	 * Method to show personalized message window
	 * @param string message to show
	 * @param Status title to put in head window
	 * @param int typeMessage to put image
	 */
	private void showMessage(String message, Status title, int typeMessage) {
		JOptionPane.showMessageDialog(this, message, title.toString(), typeMessage);
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Main main = new Main();
	}

}
