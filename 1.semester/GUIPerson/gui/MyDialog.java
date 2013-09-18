package gui;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import service.Service;

public class MyDialog extends JDialog {

	
	private JButton okButton, cancelButton;
	private JLabel lblName, lblTitle;
	private JTextField txfName, txfTitle;
	private JCheckBox ckbRetire;
	private Controller controller;
	private boolean modalResult;
	
	public MyDialog(String title){
		controller = new Controller();
		
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setTitle(title);
		this.setModalityType(JDialog.DEFAULT_MODALITY_TYPE);
		this.setLayout(null);
		this.setSize(300, 300);
		this.setLocation(300, 200);
		
		okButton = new JButton("Add");
		okButton.setBounds(100, 250, 80, 25);
		okButton.addActionListener(controller);
		this.add(okButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(180, 250, 80, 25);
		cancelButton.addActionListener(controller);
		this.add(cancelButton);
		
		lblName = new JLabel("Name:");
		lblName.setBounds(10, 10, 100, 25);
		this.add(lblName);
		
		txfName = new JTextField();
		txfName.setBounds(5, 35, 200, 25);
		this.add(txfName);
		
		lblTitle = new JLabel("Title:");
		lblTitle.setBounds(10, 70, 100, 25);
		this.add(lblTitle);
		
		txfTitle = new JTextField();
		txfTitle.setBounds(5, 95, 200, 25);
		this.add(txfTitle);
		
		ckbRetire = new JCheckBox("retired");
		ckbRetire.setBounds(10, 150, 100, 25);
		this.add(ckbRetire);
	}
	
	private class Controller implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			if(ae.getSource().equals(okButton)){
				String name = txfName.getText().trim();
				String title = txfTitle.getText().trim();
				boolean retired = ckbRetire.isSelected();
				
				Service.createPerson(name, title, retired);
				modalResult = true;
				MyDialog.this.setVisible(false);
				
			}else if(ae.getSource().equals(cancelButton)){
				modalResult = false;
				MyDialog.this.setVisible(false);
			}
		}
	}
	
	public boolean personCreated(){
		return modalResult;
	}
	
}
