package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.EmailEvaluator;
import model.FieldEvaluator;
import model.NumberEvaluator;

public class MainFrame extends JFrame {
		
	private JTextField txtNumber;
	private JTextField txtEmail;
	private JLabel lblNumber;
	private JLabel lblEmail;
	
		private Controller controller;
	public MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("GUIApp");
		this.setLocation(300, 200);
		this.setSize(400, 150);
		
		
		
		lblNumber = new JLabel("Tal");
		lblNumber.setLocation(20,25);
		lblNumber.setSize(100, 25);
		this.add(lblNumber);
		
		lblEmail = new JLabel("Email");
		lblEmail.setLocation(20,50);
		lblEmail.setSize(100, 25);
		this.add(lblEmail);
		
		txtNumber = new JTextField();
		txtNumber.setLocation(130,25);
		txtNumber.setSize(200, 25);
		this.add(txtNumber);
		
		txtEmail = new JTextField();
		txtEmail.setLocation(130,50);
		txtEmail.setSize(200, 25);
		this.add(txtEmail);
		
		controller = new Controller();
		txtNumber.addActionListener(controller);
		txtEmail.addActionListener(controller);
		
		
	}
	
	private class Controller implements ActionListener{
		private FieldEvaluator evalEmail = new FieldEvaluator(new EmailEvaluator());
		private FieldEvaluator evalNumb = new FieldEvaluator(new NumberEvaluator());

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(txtNumber)){
				if(evalNumb.evaluate(txtNumber.getText()))
					txtNumber.setBackground(Color.GREEN);
				else
					txtNumber.setBackground(Color.RED);
			}
			else if (e.getSource().equals(txtEmail)){
				if(evalEmail.evaluate(txtEmail.getText()))
					txtEmail.setBackground(Color.GREEN);
				else
					txtEmail.setBackground(Color.RED);
			}
			
		}

		
		
		
	}
}
