package farvevalg;

import javax.swing.*;

public class GUIApp {

	public static MainFrame mainFrame;
	public static SubFrame1 frame1;
	public static SubFrame2 frame2;
	
	public static void main(String[] args) {
        mainFrame = new MainFrame();
        mainFrame.setVisible(true);

        frame1 = new SubFrame1();
        frame1.setVisible(true);

        frame2 = new SubFrame2();
        frame2.setVisible(true);
	}
}
