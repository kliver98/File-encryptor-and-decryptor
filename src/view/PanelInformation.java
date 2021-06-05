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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PanelInformation extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton btnLoadSHA1;
	private JLabel lblPathSHA1;
	private JLabel lblCurrentStatus;
	private JTextField txtFieldSHA1;
	private JFileChooser chooser;
	
	public PanelInformation() {
		init();
	}
	
	public void init() {
		setLayout(new BorderLayout());
		int[] dims = {20,10,10,10};
		setBorder(BorderFactory.createEmptyBorder(dims[0],dims[1],dims[2],dims[3]));
		loadPanel();
	}
	
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
	
	private File chooseFile() {
		chooser = new JFileChooser("./");
		chooser.setDialogTitle(Constants.LOAD_SHA1);
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}

		throw new RuntimeException("No file selected");
	}
	
	//TODO: Method that reads file and gets SHA-1 hash, then call down on first try-catch

	@Override
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if (c.equals(Constants.LOAD_SHA1)) {
			File file = null;
			try {				
				file = chooseFile();
				String path = file.getAbsolutePath();
				txtFieldSHA1.setText(path);
				StringSelection stringSelection = new StringSelection (path);
				Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
				clpbrd.setContents (stringSelection, null);
				Runnable runnable =
				        () -> { 
				        	try {
								lblPathSHA1.setForeground(new Color(50, 106, 16));
								lblPathSHA1.setText("Copied to clipboard");
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
				
			} catch(Exception err) {
				System.out.println(Constants.EXCEPTION+err.getMessage());
			}
		}
	}

}
