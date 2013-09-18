package javaBean22August1;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JLabel;
import java.awt.GridLayout;

public class Test extends JFrame {

	private JPanel contentPane;
	/**
	 * @wbp.nonvisual location=169,341
	 */
	private final Person person = new Person();
	private JPanel panel;
	private JLabel lblName;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JButton btnNewButton;
	private JButton btnSubmit;
	private JButton btnNewButton_1;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		person.addAgeErrorListener(new AgeErrorListener() {
			public void ageOutOfRange(AgeErrorEvent event) {
				textArea.setText("");
				textArea.append("The age is out range");
			}
		});
		person.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				textArea.append(person.getName() + ", " + person.getAdr() + ", " + person.getAge()+"\n");
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		panel.setLayout(new GridLayout(3, 3, 0, 0));
		
		lblName = new JLabel("Name:");
		panel.add(lblName);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				person.setName(textField.getText());
			}
		});
		panel.add(btnNewButton);
		
		lblNewLabel = new JLabel("Adr:");
		panel.add(lblNewLabel);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				person.setAdr(textField_1.getText());
			}
		});
		panel.add(btnSubmit);
		
		lblNewLabel_1 = new JLabel("Age:");
		panel.add(lblNewLabel_1);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		btnNewButton_1 = new JButton("Submit");
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				person.setAge(Integer.parseInt(textField_2.getText()));
			}
		});
		panel.add(btnNewButton_1);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		contentPane.add(textArea, BorderLayout.CENTER);
	}

}
