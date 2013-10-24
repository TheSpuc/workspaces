package recursivesorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private TegnePanel pnlTegning;

	public MainFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("TegneApp");
		this.setLocation(200, 100);
		this.setSize(512, 512);

		pnlTegning = new TegnePanel();
		this.add(pnlTegning);
		pnlTegning.setLocation(5, 5);
		pnlTegning.setSize(512, 512);
		
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
		
		//fun
		for(String s : sierpinski(10)){
			System.out.println(s);
		}
	}
	
	public static List<String> sierpinski(int n)
    {
        List<String> down = Arrays.asList("*");
        String space = " ";
        for (int i = 0; i < n; i++) {
            List<String> newDown = new ArrayList<String>();
            for (String x : down)
                newDown.add(space + x + space);
            for (String x : down)
                newDown.add(x + " " + x);
 
            down = newDown;
            space += space;
        }
        return down;
    }
}
