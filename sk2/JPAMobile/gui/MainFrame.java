package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;

import layout.WrapLayout;
import model.Design;
import model.Mobile;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;

import service.Service;

public class MainFrame extends JFrame {

	private Controller controller;
	private JPanel contentPane;
	private JPanel panelCenter;
	private JList<Mobile> list;
	private JScrollPane jspSroll;
	private JPanel panelSouth;
	private JButton btnSave;
	private JButton btnDelete;
	private JPanel panelNorth;
	private JTextField textNumber;
	private JLabel lblNumber;
	private JLabel lblManufactor;
	private JTextField textManufactor;
	private JLabel lblModel;
	private JTextField textModel;
	private JLabel lblPrice;
	private JTextField textPrice;
	private JLabel lblDesign;
	private JTextField textDesign;
	private DefaultComboBoxModel<Mobile> model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		controller = new Controller();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panelCenter = new JPanel();
		contentPane.add(panelCenter, BorderLayout.CENTER);

		model = new DefaultComboBoxModel<>();
		list = new JList<>(model);

		jspSroll = new JScrollPane(list);
		panelCenter.add(jspSroll);

		panelSouth = new JPanel();
		contentPane.add(panelSouth, BorderLayout.SOUTH);

		btnSave = new JButton("Save");
		btnSave.addActionListener(controller);
		panelSouth.add(btnSave);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(controller);
		panelSouth.add(btnDelete);

		panelNorth = new JPanel(new WrapLayout());
		contentPane.add(panelNorth, BorderLayout.NORTH);

		lblNumber = new JLabel("Number:");

		textNumber = new JTextField();
		textNumber.setColumns(10);

		lblManufactor = new JLabel("Manufactor:");

		textManufactor = new JTextField();
		textManufactor.setColumns(10);

		lblModel = new JLabel("Model:");

		textModel = new JTextField();
		textModel.setColumns(10);


		panelNorth.add(lblNumber);
		panelNorth.add(textNumber);
		panelNorth.add(lblManufactor);
		panelNorth.add(textManufactor);
		panelNorth.add(lblModel);
		panelNorth.add(textModel);

		lblPrice = new JLabel("Price");
		panelNorth.add(lblPrice);

		textPrice = new JTextField();
		panelNorth.add(textPrice);
		textPrice.setColumns(10);

		lblDesign = new JLabel("Design:");
		panelNorth.add(lblDesign);

		textDesign = new JTextField();
		panelNorth.add(textDesign);
		textDesign.setColumns(10);
	}


	private class Controller implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(btnSave)){
				int number = Integer.parseInt(textNumber.getText());
				String manufactor = textManufactor.getText();
				String modelphone = textModel.getText();
				String price = textPrice.getText();
				String design = textDesign.getText();
				Mobile temp = new Mobile(number, manufactor, modelphone, price, Enum.valueOf(Design.class, design));
				Service.store(temp);
				model.addElement(temp);
			}else if(e.getSource().equals(btnDelete)){
				Mobile temp = (Mobile) model.getSelectedItem();
				model.removeElement(temp);
				Service.remove(temp);
			}
		}
	}
}
