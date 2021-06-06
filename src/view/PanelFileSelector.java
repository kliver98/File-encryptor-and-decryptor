package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
public class PanelFileSelector extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton btnSearch;
	private JFileChooser chooser;
	private JLabel lblRoute;
	
	public PanelFileSelector() {
		init();
	}
	
	public void init() {
		setLayout(new BorderLayout());
		int WIDTH = Constants.WIDTH;
		int HEIGHT = Constants.HEIGHT/3;
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		loadPanel();
	}
	
	private void loadPanel() {
		btnSearch = new JButton(Constants.SEARCH);
		btnSearch.setFont(new Font("Arial",Font.BOLD,14));
		btnSearch.addActionListener(this);
		
		lblRoute = new JLabel(Constants.PATH_TO_FILE);
		lblRoute.setFont(new Font("Arial",Font.PLAIN,12));
		lblRoute.setHorizontalAlignment(SwingConstants.CENTER);
	    JPanel aux = new JPanel(new GridLayout(2,1));
	    aux.add(btnSearch);
	    aux.add(lblRoute);
	    add(aux);
	}
	
	private File chooseFile() {
		chooser = new JFileChooser("./");
		chooser.setDialogTitle(Constants.SEARCH);
		int option = chooser.showOpenDialog(this);
		if (option == JFileChooser.APPROVE_OPTION) {
			return chooser.getSelectedFile();
		}

		throw new RuntimeException("No file selected");
	}
	
	private String getShorterPath(String path, int length) {
		char chars[] = path.toCharArray();
		if (chars.length<length) return path;
		StringBuffer newString = new StringBuffer();
		for(int i=0; i<length/2; i++) {
			newString.append(chars[i]);
		}
		newString.append("...");
		String tmp = newString.toString();
		newString = new StringBuffer();
		for(int i=chars.length-1; i>chars.length-(length/2); i--) {
			newString.append(chars[i]);
		}
		return tmp+newString.reverse().toString();
	}
	
	public String getPathToFileChoosed() {
		return chooser!=null ? chooser.getSelectedFile().getAbsolutePath():null;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String c = e.getActionCommand();
		if (c.equals(Constants.SEARCH)) {
			File file = null;
			try {				
				file = chooseFile();
				String path = file.getAbsolutePath();
				if (path.toCharArray().length>Constants.MAX_PATH_LENGTH) path = getShorterPath(path, Constants.MAX_PATH_LENGTH);
				lblRoute.setText(Constants.PATH_TO_FILE+path);
			} catch(Exception err) {
				System.out.println(Constants.EXCEPTION+err.getMessage());
			}
		}
	}

}
