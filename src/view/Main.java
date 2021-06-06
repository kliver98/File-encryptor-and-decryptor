package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import control.Controller;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	private PanelFileSelector pFileSelector;
	private PanelOptions pOptions;
	private PanelInformation pInformation;
	private Controller controller;
	
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
	
	public void addPanels() {
		
		pFileSelector = new PanelFileSelector();
		pOptions = new PanelOptions();
		pInformation = new PanelInformation(controller);
		
		JPanel aux = new JPanel(new BorderLayout());
		int width = Constants.WIDTH;
		int height = Constants.HEIGHT;
		aux.setBorder(BorderFactory.createEmptyBorder(height/8,width/10,height/15,width/10));
		aux.add(pFileSelector,BorderLayout.NORTH);
		aux.add(pOptions,BorderLayout.CENTER);
		aux.add(pInformation,BorderLayout.SOUTH);
		
		add(aux,BorderLayout.CENTER);
		
		pack();
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Main main = new Main();
	}

}
