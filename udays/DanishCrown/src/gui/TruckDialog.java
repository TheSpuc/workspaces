package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.CardboardBox;
import model.PackageType;
import model.PlasticBox;
import model.XmasTree;
import service.Service;


/**
 * 
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
public class TruckDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JPanel pnlOpretTruck, pnlChauffeurInfo, pnlCheckBox, pnlPackageType;
	
	private JLabel lblCreateTruck, lblRegistrationNumber, lblChauffeurName, lblPhoneNumber, lblPackagetype;
	
	private JTextField txtRegno, txtChauffeurname, txtPhoneNumber;
	
	private JCheckBox chckbxXmastree, chckbxPlasticbox, chckbxCardboardbox;
	
	private Box verticalBox;
	
	private Controller controller;
	
	private JButton cancelButton, okButton;

	private boolean modalResult;

	/**
	 * Create the dialog.
	 */
	public TruckDialog() {
		setAlwaysOnTop(true);
		setBounds(100, 100, 350, 250);
		getContentPane().setLayout(new BorderLayout(0, 0));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(new BorderLayout(0, 0));
		setTitle("Create truck");
		setResizable(false);
		setModal(true);
		controller = new Controller();
		modalResult = false;
		

		pnlOpretTruck = new JPanel();
		contentPanel.add(pnlOpretTruck, BorderLayout.NORTH);
		FlowLayout fl_pnlOpretTruck = new FlowLayout(FlowLayout.CENTER, 5, 5);
		pnlOpretTruck.setLayout(fl_pnlOpretTruck);
		{
			lblCreateTruck = new JLabel("Create truck");
			pnlOpretTruck.add(lblCreateTruck);
			lblCreateTruck.setFont(new Font("Euphemia", Font.PLAIN, 19));
			lblCreateTruck.setHorizontalAlignment(SwingConstants.CENTER);
		}
		{
			JPanel centerPanel = new JPanel();
			centerPanel.setPreferredSize(new Dimension(600, 200));
			contentPanel.add(centerPanel, BorderLayout.CENTER);
			centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			verticalBox = Box.createVerticalBox();
			centerPanel.add(verticalBox);
			{
				pnlChauffeurInfo = new JPanel();
				verticalBox.add(pnlChauffeurInfo);
				pnlChauffeurInfo.setLayout(new GridLayout(0, 2, 0, 0));
				{
					lblRegistrationNumber = new JLabel("Registration number: ");
					pnlChauffeurInfo.add(lblRegistrationNumber);
				}
				{
					txtRegno = new JTextField();
					pnlChauffeurInfo.add(txtRegno);
				}

				lblChauffeurName = new JLabel("Chauffeur name: ");
				lblChauffeurName.setHorizontalAlignment(SwingConstants.LEFT);
				pnlChauffeurInfo.add(lblChauffeurName);

				txtChauffeurname = new JTextField();
				txtChauffeurname.setHorizontalAlignment(SwingConstants.LEFT);
				pnlChauffeurInfo.add(txtChauffeurname);
			}

			lblPhoneNumber = new JLabel("Phone number: ");
			pnlChauffeurInfo.add(lblPhoneNumber);

			txtPhoneNumber = new JTextField();
			pnlChauffeurInfo.add(txtPhoneNumber);

			pnlPackageType = new JPanel();
			verticalBox.add(pnlPackageType);

			lblPackagetype = new JLabel("Packagetype");
			pnlPackageType.add(lblPackagetype);

			pnlCheckBox = new JPanel();
			verticalBox.add(pnlCheckBox);

			chckbxXmastree = new JCheckBox("XmasTree");
			pnlCheckBox.add(chckbxXmastree);

			chckbxPlasticbox = new JCheckBox("PlasticBox");
			pnlCheckBox.add(chckbxPlasticbox);

			chckbxCardboardbox = new JCheckBox("CardboardBox");
			pnlCheckBox.add(chckbxCardboardbox);
		}
		{
			JPanel buttonPane = new JPanel();
			contentPanel.add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			{
				okButton = new JButton("OK");
				okButton.addActionListener(controller);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(controller);
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public boolean isModalResult(){
		return modalResult;
	}
	
	public void setModalResult(boolean modalResult){
		this.modalResult = modalResult;
	}
	
	private class Controller implements ActionListener {
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(okButton)){
				if (txtChauffeurname.getText().matches("^[a-zA-Z]+([ ][a-zA-Z]+)+$") && txtPhoneNumber.getText().matches("^\\d+$") && !txtRegno.getText().equals("") && (chckbxCardboardbox.isSelected() || chckbxPlasticbox.isSelected() || chckbxXmastree.isSelected())){
					Service service = Service.getInstance();
					int count = 0;
					if (chckbxXmastree.isSelected()) count++;
					if (chckbxCardboardbox.isSelected()) count++;
					if (chckbxPlasticbox.isSelected()) count++;
					PackageType[] pt = new PackageType[count];
					int index = 0;
					if (chckbxXmastree.isSelected()){
						pt[index] = XmasTree.getInstance();
						index++;
					}
					if (chckbxCardboardbox.isSelected()){
						pt[index] = CardboardBox.getInstance();
						index++;
					}
					if (chckbxPlasticbox.isSelected())	pt[index] = PlasticBox.getInstance();
					service.createTruck(txtRegno.getText(), txtChauffeurname.getText(), txtPhoneNumber.getText(), pt);
					setModalResult(true);
					setVisible(false);
				}
				else {
					JOptionPane.showMessageDialog(null, "Registration number must be input \nChauffeur name must be a full name \nPhone number must be a number \nPackagetype(s) must be selected", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (ae.getSource().equals(cancelButton)){
				setVisible(false);
			}
		}
	}
}
