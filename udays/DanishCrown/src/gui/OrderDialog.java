package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import model.PackageType;
import service.Service;

/**
 * 
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
public class OrderDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	private JTextField txtNumber, txtWeight, txtMargin;

	private JButton cancelButton, okButton;

	private Controller controller;

	private boolean modalResult;

	private JComboBox<PackageType> comboBoxPackageType;

	private JSpinner dateSpinner;

	private Service service;

	/**
	 * Create the dialog.
	 */
	public OrderDialog() {
		setBounds(100, 100, 350, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		setTitle("Create order");
		setModal(true);
		setResizable(false);
		controller = new Controller();
		modalResult = false;
		service = Service.getInstance();

		{
			Box verticalBox = Box.createVerticalBox();
			contentPanel.add(verticalBox);
			{
				JPanel pnlOrderNumber = new JPanel();
				verticalBox.add(pnlOrderNumber);
				pnlOrderNumber.setLayout(new GridLayout(0, 2, 0, 0));
				{
					JLabel lblOrdernumber = new JLabel("Order number: ");
					pnlOrderNumber.add(lblOrdernumber);
				}
				{
					txtNumber = new JTextField();
					pnlOrderNumber.add(txtNumber);
					txtNumber.setColumns(10);
				}
			}
			{
				JPanel pnlWeight = new JPanel();
				verticalBox.add(pnlWeight);
				pnlWeight.setLayout(new GridLayout(0, 2, 0, 0));
				{
					JLabel lblWeight = new JLabel("Weight (kg): ");
					pnlWeight.add(lblWeight);
				}
				{
					txtWeight = new JTextField();
					pnlWeight.add(txtWeight);
					txtWeight.setColumns(10);
				}
			}
			JPanel pnlDate = new JPanel();
			verticalBox.add(pnlDate);
			pnlDate.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JLabel lblDate = new JLabel("Date: ");
				pnlDate.add(lblDate);
			}
			SimpleDateFormat shortf = new SimpleDateFormat("MM-dd-yyyy");
			dateSpinner = new JSpinner();
			dateSpinner.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
			dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, shortf .toPattern()));
			pnlDate.add(dateSpinner);
			{
				JPanel pnlMargin = new JPanel();
				verticalBox.add(pnlMargin);
				pnlMargin.setLayout(new GridLayout(0, 2, 0, 0));
				{
					JLabel lblMargin = new JLabel("Margin (kg): ");
					pnlMargin.add(lblMargin);
				}
				{
					txtMargin = new JTextField();
					pnlMargin.add(txtMargin);
					txtMargin.setColumns(10);
				}
			}
			{
				JPanel pnlPackageType = new JPanel();
				verticalBox.add(pnlPackageType);
				pnlPackageType.setLayout(new GridLayout(0, 2, 0, 0));
				{
					JLabel lblPackagetype = new JLabel("PackageType: ");
					pnlPackageType.add(lblPackagetype);
				}
				{
					comboBoxPackageType = new JComboBox<PackageType>(service.getPackageTypes().toArray(new PackageType[0]));
					pnlPackageType.add(comboBoxPackageType);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(controller);
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(controller);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JPanel pnlCreateOrder = new JPanel();
			getContentPane().add(pnlCreateOrder, BorderLayout.NORTH);
			{
				JLabel lblCreateOrder = new JLabel("Create order");
				lblCreateOrder.setFont(new Font("Euphemia", Font.PLAIN, 19));
				pnlCreateOrder.add(lblCreateOrder);
			}
		}
	}

	public boolean isModalResult() {
		return modalResult;
	}

	public void setModalResult(boolean modalResult) {
		this.modalResult = modalResult;
	}

	private class Controller implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(okButton)){
				try {
					int weight = Integer.parseInt(txtWeight.getText());
					int margin = Integer.parseInt(txtMargin.getText());
					if (margin < weight){
						int number = Integer.parseInt(txtNumber.getText());
						if (service.checkOrderNumber(number)){
							Calendar date = new GregorianCalendar();
							date.setTime((Date) dateSpinner.getValue());
							PackageType packageType = (PackageType) comboBoxPackageType.getSelectedItem();
							service.createOrder(number, weight, date, margin, packageType);
							setModalResult(true);
							setVisible(false);
						}
						else {
							JOptionPane.showMessageDialog(null, "Order number must be a unique number \nWeight must be a number \nMargin must be a number", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
				} 
				catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Order number must be a unique number \nWeight must be a number \nMargin must be a number", "Error", JOptionPane.ERROR_MESSAGE);
				}

			}
			else if (ae.getSource().equals(cancelButton)){
				setVisible(false);
			}
		}

	}


}
