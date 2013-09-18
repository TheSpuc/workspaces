package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import model.Person;
import service.Service;

public class MainFrame extends JFrame {
	
	private JList<Person> jlList;
	private JScrollPane jsrPane;
	private JButton addButton, updateButton;
	private Controller controller;
	private MyDialog myDialog;
	
	public MainFrame(){
		controller = new Controller();

		this.setTitle("Person Frame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setLocation(300, 200);
		this.setLayout(null);
		
		jlList = new JList<>();
		jlList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		jsrPane = new JScrollPane(jlList);
		jsrPane.setBounds(10, 10, 400, 500);
		this.add(jsrPane);
		
		addButton = new JButton("Add Person");
		addButton.setBounds(420, 110, 100, 25);
		addButton.addActionListener(controller);
		this.add(addButton);
		
		updateButton = new JButton("Update");
		updateButton.setBounds(420, 150, 100, 25);
		updateButton.addActionListener(controller);
		this.add(updateButton);
	}
	
	private class Controller implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			if(ae.getSource().equals(addButton)){
				myDialog = new MyDialog("Add Person");
				myDialog.setVisible(true);
		
				if(myDialog.personCreated()){
					jlList.setListData(Service.getPersons().toArray(new Person[0]));
				}

			}else if(ae.getSource().equals(updateButton)){
				Person ps = jlList.getSelectedValue();
				myDialog = new MyDialog("Update Person");
				myDialog.setVisible(true);
				if(myDialog.isModal()){
					Service.removePerson(ps);
					jlList.setListData(Service.getPersons().toArray(new Person[0]));
				}
			}
		}
	}
}
