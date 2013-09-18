package menueks;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MainFrameNew extends JFrame{
	
	private JMenuBar menuBar;
	private JMenu textColor, backgroundColor;
	private JMenuItem textRed, textWhite, textBlue, textBlack;
	private JMenuItem backWhite, backBlack, backRed;
	private Controller controller;
	
	public MainFrameNew(){
		controller = new Controller();
		this.setLayout(new BorderLayout());
		this.setTitle("Vicki");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 500, 400);
		
		//JMenuBar setup
		menuBar = new JMenuBar();
		
		
		//Text Color JMenu
		textColor = new JMenu("TextColor");
		menuBar.add(textColor);
		textColor.setMnemonic(KeyEvent.VK_ALT);
		
		textRed = new JMenuItem("Red", KeyEvent.VK_R);
		textRed.addActionListener(controller);
		textColor.add(textRed);
		
		textWhite = new JMenuItem("White", KeyEvent.VK_W);
		textWhite.addActionListener(controller);
		textColor.add(textWhite);
		
		textBlue = new JMenuItem("Blue", KeyEvent.VK_B);
		textBlue.addActionListener(controller);
		textColor.add(textBlue);
		
		textBlack = new JMenuItem("Black", KeyEvent.VK_K);
		textBlack.addActionListener(controller);
		textColor.add(textBlack);
		
		
		//Background Color JMenu
		backgroundColor = new JMenu("BackgroundColor");
		menuBar.add(backgroundColor);
		backgroundColor.setMnemonic(KeyEvent.VK_SPACE);
		
		backWhite = new JMenuItem("White", KeyEvent.VK_T);
		backWhite.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, KeyEvent.VK_P));
		backWhite.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Roking");
			}
		});
		backgroundColor.add(backWhite);
		
		backBlack = new JMenuItem("Black", KeyEvent.VK_C);
		backBlack.addActionListener(controller);
		backgroundColor.add(backBlack);
		
		backRed = new JMenuItem("Red", KeyEvent.VK_D);
		backRed.addActionListener(controller);
		backgroundColor.add(backRed);
		
		this.setJMenuBar(menuBar);
		
	}
	
	private class Controller implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrameNew mf = new MainFrameNew();
		mf.setVisible(true);
	}

}
