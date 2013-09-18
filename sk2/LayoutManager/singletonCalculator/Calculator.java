package singletonCalculator;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Calculator extends JDialog {
	
	private static Calculator calculator;
	
	private JMenuBar menu;
	
	private Calculator(){
		this.setLayout(new FlowLayout());
		this.setTitle("Calculator");
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setBounds(100, 180, 400, 300);
		
		menu = new JMenuBar();
		this.setJMenuBar(menu);
		
		JMenuItem operation = new JMenuItem("operation");
		menu.add(operation);
		operation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("THIS IS A CHEAT BUTTON! <<<42!>>>");
			}
		});
		
		JMenuItem exit = new JMenuItem("exit");
		menu.add(exit);
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Calculator.this.dispose();
			}
		});
	}
	
	public static Calculator getInstance(){
		if(calculator == null){
			calculator = new Calculator();
		}
		return calculator;
	}
}
