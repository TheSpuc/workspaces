package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.TruckRegister;

import service.Service;
import service.ServiceGUI;

/**
 * 
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
public class NotifyChauffeurDialog extends JDialog {

	private Service service;
	private ServiceGUI serviceGui;

	private TruckRegister tr;

	private Controller controller;
	
	private JPanel pnlSouth, pnlCenter;

	//Center
	private JTextArea txaMessage;
	private JPanel pnlWeight;
	private JLabel lblWeight;
	private JTextField txfWeight;
	private JScrollPane jspScroll;

	//South
	private JButton btnRegisterDeparture;
	private JButton btnCancel;
	
	private boolean modalResult;
	
	/**
	 * Create the dialog.
	 */
	public NotifyChauffeurDialog(TruckRegister tr) {
		this.setBounds(100, 100, 450, 300);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		setTitle("Notify chauffeur");
		setModal(true);
		modalResult = false;
		
		service = Service.getInstance();
		serviceGui = ServiceGUI.getInstance();
		
		this.tr = tr;
		
		controller = new Controller();
		
		// Panel Center
		pnlCenter = new JPanel();
		pnlCenter.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		this.add(pnlCenter, BorderLayout.CENTER);
		
		
		txaMessage = new JTextArea();
		txaMessage.setEditable(false);
		txaMessage.setLineWrap(true);
		txaMessage.setWrapStyleWord(true);
		jspScroll = new JScrollPane(txaMessage);
		jspScroll.setPreferredSize(new Dimension(400, 150));
		pnlCenter.add(jspScroll);
		
		txaMessage.setText(serviceGui.printNotify(tr));
		
		pnlWeight = new JPanel();
		pnlWeight.setPreferredSize(new Dimension(400, 150));
		pnlWeight.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlCenter.add(pnlWeight);
		
		lblWeight = new JLabel("Weight (kg): ");
		lblWeight.setPreferredSize(new Dimension(75, 25));
		pnlWeight.add(lblWeight);
		
		txfWeight = new JTextField();
		txfWeight.setPreferredSize(new Dimension(150, 25));
		pnlWeight.add(txfWeight);
		
		// Panel South
		pnlSouth = new JPanel();
		pnlSouth.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		this.add(pnlSouth, BorderLayout.SOUTH);

		btnRegisterDeparture = new JButton("Register departure");
		pnlSouth.add(btnRegisterDeparture);
		btnRegisterDeparture.addActionListener(controller);
		
		btnCancel = new JButton("Cancel");
		pnlSouth.add(btnCancel);
		btnCancel.addActionListener(controller);
	}
	
	public boolean isModalResult(){
		return modalResult;
	}
	
	public void setModalResult(boolean modalResult){
		this.modalResult = modalResult;
	}
	
	private class Controller implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(btnRegisterDeparture)) {
				try {
					int weight = Integer.parseInt(txfWeight.getText());
					service.setDepartureWeight(tr, weight);
					setModalResult(true);
					setVisible(false);
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Weight must be a number", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}else if(ae.getSource().equals(btnCancel)){
				setModal(false);
				setVisible(false);
			}
		}
	}
}
