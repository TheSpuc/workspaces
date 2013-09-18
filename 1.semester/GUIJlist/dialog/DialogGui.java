package dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;

public class DialogGui extends JDialog {
	private JButton butOK;
	private JButton butCancel;
	
	public boolean modalResult;
	
	private Controller controller;
	
	public DialogGui(String title){
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		this.setTitle(title);
		this.setLayout(null);
		this.setSize(250, 150);
		this.setLocation(300, 300);
		
		controller = new Controller();
		
		butOK = new JButton("OK");
		butOK.setLocation(150, 80);
		butOK.setSize(80, 25);
		this.add(butOK);
		butOK.addActionListener(controller);

		butCancel = new JButton("Cancel");
		butCancel.setLocation(50, 80);
		butCancel.setSize(80, 25);
		this.add(butCancel);
		butCancel.addActionListener(controller);

		
		
		
	}
	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("er her");
			if (e.getSource().equals(butOK)){
				DialogGui.this.setVisible(false);
				modalResult= true;
			}
			else if (e.getSource().equals(butCancel)){
				DialogGui.this.setVisible(false);				
				modalResult= false;
				
			}
			
		}
		
	}

}
