package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import service.Service;

import model.Contact;

public class ContactsPanel extends JPanel {
	private MainFrame frame;
	private JList<Contact> contacts;
	private JScrollPane scrContacts;
	private JTextField txfSearch,txfFirstName, txfLastName, txfAddress, txfNumber;
	private JLabel lblSearch, lblError, lblError2,lblFirstName, lblLastName, lblAddress, lblNumber;;
	private JButton btnSearch, btnCreate, btnCall, btnText, btnEdit, btnDelete,btnCreate1, btnUpdate;
	private static Contact currentContact;

	private Controller controller = new Controller();

	public ContactsPanel(MainFrame frame) {

		this.frame = frame;
		this.setSize(300, 380);
		this.setLocation(10, 10);

		lblSearch = new JLabel("Search");
		this.add(lblSearch);
		lblSearch.setSize(40, 15);
		lblSearch.setLocation(15, 15);

		txfSearch = new JTextField();
		this.add(txfSearch);
		txfSearch.setSize(180, 20);
		txfSearch.setLocation(15, 30);

		contacts = new JList<Contact>(Service.getContacts().toArray(new Contact[0]));
		contacts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrContacts = new JScrollPane(contacts);
		scrContacts.setSize(260, 250);
		scrContacts.setLocation(15, 60);
		this.add(scrContacts);

		btnSearch = new JButton("Search");
		this.add(btnSearch);
		btnSearch.setSize(70, 20);
		btnSearch.setLocation(200, 30);
		btnSearch.addActionListener(controller);

		btnCreate = new JButton("Create");
		this.add(btnCreate);
		btnCreate.setSize(65, 20);
		btnCreate.setLocation(10, 320);
		btnCreate.addActionListener(controller);

		btnCall = new JButton("Call");
		this.add(btnCall);
		btnCall.setSize(65, 20);
		btnCall.setLocation(80, 320);
		btnCall.addActionListener(controller);

		btnText = new JButton("Text");
		this.add(btnText);
		btnText.setSize(65, 20);
		btnText.setLocation(150, 320);
		btnText.addActionListener(controller);

		btnEdit = new JButton("Edit");
		this.add(btnEdit);
		btnEdit.setSize(65, 20);
		btnEdit.setLocation(220, 320);
		btnEdit.addActionListener(controller);

		btnDelete = new JButton("Delete");
		this.add(btnDelete);
		btnDelete.setSize(65, 20);
		btnDelete.setLocation(10, 345);
		btnDelete.addActionListener(controller);

		lblError = new JLabel("Please select a contact");
		lblError.setSize(180, 20);
		lblError.setLocation(80, 345);
		this.add(lblError);
		lblError.setVisible(false);

		lblError2 = new JLabel("Please enter searchObject");
		lblError2.setSize(180, 20);
		lblError2.setLocation(80, 345);
		this.add(lblError2);
		lblError2.setVisible(false);
		
		lblFirstName = new JLabel("Firstname");
		this.add(lblFirstName);
		lblFirstName.setSize(80, 20);
		lblFirstName.setLocation(15, 15);
		lblFirstName.setVisible(false);

		txfFirstName = new JTextField();
		this.add(txfFirstName);
		txfFirstName.setSize(180, 20);
		txfFirstName.setLocation(15, 40);
		txfFirstName.setVisible(false);

		lblLastName = new JLabel("Lastname");
		this.add(lblLastName);
		lblLastName.setSize(80, 20);
		lblLastName.setLocation(15, 65);
		lblLastName.setVisible(false);

		txfLastName = new JTextField();
		this.add(txfLastName);
		txfLastName.setSize(180, 20);
		txfLastName.setLocation(15, 90);
		txfLastName.setVisible(false);

		lblAddress = new JLabel("Address");
		this.add(lblAddress);
		lblAddress.setSize(80, 20);
		lblAddress.setLocation(15, 115);
		lblAddress.setVisible(false);
		
		txfAddress = new JTextField();
		this.add(txfAddress);
		txfAddress.setSize(180, 20);
		txfAddress.setLocation(15, 140);
		txfAddress.setVisible(false);

		lblNumber = new JLabel("Number");
		this.add(lblNumber);
		lblNumber.setSize(80, 20);
		lblNumber.setLocation(15, 165);
		lblNumber.setVisible(false);

		txfNumber = new JTextField();
		this.add(txfNumber);
		txfNumber.setSize(180, 20);
		txfNumber.setLocation(15, 190);
		txfNumber.setVisible(false);

		btnCreate1 = new JButton("Create");
		this.add(btnCreate1);
		btnCreate1.setSize(70, 25);
		btnCreate1.setLocation(15, 320);
		btnCreate1.addActionListener(controller);
		btnCreate1.setVisible(false);

		btnUpdate = new JButton("Update");
		this.add(btnUpdate);
		btnUpdate.setSize(70, 25);
		btnUpdate.setLocation(90, 320);
		btnUpdate.addActionListener(controller);
		btnUpdate.setVisible(false);


	}
	
	public void setCurrentContact(){
		currentContact=contacts.getSelectedValue();
	}
	public static Contact getCurrentContact(){
		return currentContact;
	}
	private void setContact(Contact contact) {
		
		if (!contact.equals(null)) {
			txfFirstName.setText(contact.getFirstName());
			txfLastName.setText(contact.getLastName());
			txfAddress.setText(contact.getAddress());
			txfNumber.setText(contact.getNumber());
			btnCreate1.setVisible(false);
			btnUpdate.setVisible(true);
		}
	}

	private void updatePanel() {
		contacts.setListData(Service.getContacts().toArray(new Contact[0]));
	}

	private class Controller implements ActionListener {
		
		private void showCreate(){
			lblSearch.setVisible(false);
			txfSearch.setVisible(false);
			scrContacts.setVisible(false);
			btnSearch.setVisible(false);
			btnCreate.setVisible(false);
			btnCall.setVisible(false);
			btnText.setVisible(false);
			btnEdit.setVisible(false);
			btnDelete.setVisible(false);
			lblFirstName.setVisible(true);
			txfFirstName.setVisible(true);
			lblLastName.setVisible(true);
			txfLastName.setVisible(true);
			lblAddress.setVisible(true);
			txfAddress.setVisible(true);
			lblNumber.setVisible(true);
			txfNumber.setVisible(true);
			btnCreate1.setVisible(true);
		}
		private void showEdit(){
			lblSearch.setVisible(false);
			txfSearch.setVisible(false);
			scrContacts.setVisible(false);
			btnSearch.setVisible(false);
			btnCreate.setVisible(false);
			btnCall.setVisible(false);
			btnText.setVisible(false);
			btnEdit.setVisible(false);
			btnDelete.setVisible(false);
			lblFirstName.setVisible(true);
			txfFirstName.setVisible(true);
			lblLastName.setVisible(true);
			txfLastName.setVisible(true);
			lblAddress.setVisible(true);
			txfAddress.setVisible(true);
			lblNumber.setVisible(true);
			txfNumber.setVisible(true);
			btnCreate1.setVisible(false);
			btnUpdate.setVisible(true);
		}
		private void showContacts(){
			lblSearch.setVisible(true);
			txfSearch.setVisible(true);
			scrContacts.setVisible(true);
			btnSearch.setVisible(true);
			btnCreate.setVisible(true);
			btnCall.setVisible(true);
			btnText.setVisible(true);
			btnEdit.setVisible(true);
			btnDelete.setVisible(true);
			lblFirstName.setVisible(false);
			txfFirstName.setVisible(false);
			lblLastName.setVisible(false);
			txfLastName.setVisible(false);
			lblAddress.setVisible(false);
			txfAddress.setVisible(false);
			lblNumber.setVisible(false);
			txfNumber.setVisible(false);
			btnCreate1.setVisible(false);
			btnUpdate.setVisible(false);
		}
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(btnCreate)) {
				showCreate();
				

			} else if (e.getSource().equals(btnEdit)) {
				if (contacts.getSelectedValue().equals("")) {
					lblError.setVisible(true);

				} else {
					setContact(contacts.getSelectedValue());
					showEdit();
				}
				

			} else if (e.getSource().equals(btnDelete)) {
				if (contacts.getSelectedValue().equals("")) {
					lblError.setVisible(true);

				} else {
					Service.deleteContact(contacts.getSelectedValue());
					updatePanel();
				}

			} else if (e.getSource().equals(btnSearch)) {
				if (txfSearch.getText().equals("")) {
					lblError2.setVisible(true);
				} else {
					contacts.setListData(Service.getContacts(txfSearch.getText()).toArray(new Contact[0]));
				}

			} else if (e.getSource().equals(btnCall)) {
				if (contacts.getSelectedIndex() == -1) {
					lblError.setVisible(true);
				}else{
					contacts.getSelectedValue();
					setCurrentContact();
					CallsPanel.setCall(true);
					frame.showPanel("Calls");
				}

			} else if (e.getSource().equals(btnText)) {
				if (contacts.getSelectedValue().equals("")) {
					lblError.setVisible(true);

				} else {
					contacts.getSelectedValue();
					frame.showPanel("Texts");
				}
			}else if (e.getSource().equals(btnCreate1)) {
				String first = txfFirstName.getText().trim();
				String last = txfLastName.getText().trim();
				String address = txfAddress.getText().trim();
				String number = txfNumber.getText().trim();
				if (!first.equals("") && !last.equals("")
						&& !address.equals("") && !number.equals("")) {
					Service.createContact(first, last, address, number);
				}
				txfFirstName.setText("");
				txfLastName.setText("");
				txfAddress.setText("");
				txfNumber.setText("");
				showContacts();
				updatePanel();

			} else if (e.getSource().equals(btnUpdate)) {
				String first = txfFirstName.getText().trim();
				String last = txfLastName.getText().trim();
				String address = txfAddress.getText().trim();
				String number = txfNumber.getText().trim();
				if (!first.equals("") && !last.equals("") && !address.equals("") && !number.equals("")) {
					Service.updateContact(contacts.getSelectedValue(), first, last, address, number);
				}
				txfFirstName.setText("");
				txfLastName.setText("");
				txfAddress.setText("");
				txfNumber.setText("");
				showContacts();
				updatePanel();
			}
		}
	}
}