import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;


public class MainFrame extends JFrame {

	private PersonAdministration persadmin;
	private JPanel contentPane;
	private Controller controller;
	private JPanel west;
	private JList<Person> persons;
	private JScrollPane jspScroll;
	private DefaultListModel<Person> listModel;
	private JPanel north;
	private JLabel lblName;
	private JTextField txtName;
	private JButton btnAddPerson;
	private JButton btnRemovePerson;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame(PersonAdministration.getInstance());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame(PersonAdministration persadmin) {
		this.persadmin = persadmin;
		controller = new Controller();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		west = new JPanel();
		contentPane.add(west, BorderLayout.CENTER);
		west.setLayout(new BorderLayout(0, 0));
		
		listModel = new DefaultListModel<>();
		persons = new JList<Person>(listModel);
		jspScroll = new JScrollPane(persons);
		west.add(jspScroll);
		
		north = new JPanel();
		contentPane.add(north, BorderLayout.NORTH);
		
		lblName = new JLabel("Name");
		north.add(lblName);
		
		txtName = new JTextField();
		north.add(txtName);
		txtName.setColumns(10);
		
		btnAddPerson = new JButton("Add Person");
		btnAddPerson.addActionListener(controller);
		north.add(btnAddPerson);
		
		btnRemovePerson = new JButton("Remove Person");
		btnRemovePerson.addActionListener(controller);
		north.add(btnRemovePerson);
	}
	
	private void update(){
		persons.setListData(persadmin.getPersons().toArray(new Person[0]));
		System.out.println(persadmin.getPersons());
	}
	
	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(btnAddPerson)){
				Person temp = new Person(txtName.getText().trim());
				persadmin.addPerson(temp);
				update();
				txtName.setText("");
			}else if(e.getSource().equals(btnRemovePerson)){
				Person temp = persons.getSelectedValue();
				persadmin.removePerson(temp);
				update();
			}
		}
	}
}
