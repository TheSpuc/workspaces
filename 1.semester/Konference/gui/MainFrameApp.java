package gui;

import javax.swing.UIManager;

/**
 * Class for running the program
 * @author Mark Medum Bundgaard
 *
 */
public class MainFrameApp {

	public static void main(String[] args){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Error setting look and feel: " + e.getMessage());
		}
		MainFrame frame = new MainFrame();

		frame.setVisible(true);
	}
}
