package singletonCalculator;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
	
	private Controller controller;
	private JButton calculator, traficlight;

	public MainFrame(){
		controller = new Controller();
		this.setTitle("FrameFun");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, 400, 60);
		this.setLayout(new FlowLayout());
		
		calculator = new JButton("Calculator");
		calculator.setName("calculator");
		calculator.addActionListener(controller);
		this.add(calculator);
		
		
		traficlight = new JButton("Traficlight");
		traficlight.setName("traficlight");
		traficlight.addActionListener(controller);
		this.add(traficlight);
	}
	
	private class Controller implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}

}
