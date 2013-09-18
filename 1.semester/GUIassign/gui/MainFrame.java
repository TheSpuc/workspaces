package gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import service.Service;

import model.Person;


public class MainFrame extends JFrame {

	private JTextField txfName, txfTitel;
	private JButton addButton;
	private JLabel lblName, lblTitel;
	private JList<Person> jlList;
	private JScrollPane scrPane;
	private JCheckBox chbRetir;

	private Controller controller;

	public MainFrame(){
		controller = new Controller();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(400, 600);
		this.setLayout(null);
		this.setLocation(200, 300);
		this.setTitle("GUI Test");

		lblName = new JLabel("Name:");
		lblName.setBounds(10, 10, 100, 25);
		this.add(lblName);

		txfName = new JTextField();
		txfName.setBounds(5, 35, 200, 25);
		this.add(txfName);

		lblTitel = new JLabel("Titel:");
		lblTitel.setBounds(10, 75, 100, 25);
		this.add(lblTitel);

		txfTitel = new JTextField();
		txfTitel.setBounds(5, 100, 200, 25);
		this.add(txfTitel);

		chbRetir = new JCheckBox("retired");
		chbRetir.setBounds(10, 130, 100, 25);
		this.add(chbRetir);

		addButton = new JButton("Add Name");
		addButton.setBounds(5, 170, 100, 25);
		addButton.addActionListener(controller);
		this.add(addButton);

		jlList = new JList<Person>();

		scrPane = new JScrollPane(jlList);
		scrPane.setBounds(10, 200, 250, 250);
		this.add(scrPane);
	}

	private class Controller implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			if(ae.getSource().equals(addButton)){
				String name = txfName.getText().trim();
				String title = txfTitel.getText().trim();
				boolean retired = chbRetir.isSelected();
				
				Service.createPerson(title, name, retired);
				jlList.setListData(Service.getPersons().toArray(new Person[0]));
			}
		}
	}
}
