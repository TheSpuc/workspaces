package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.PackageType;
import model.PartialOrder;
import model.Truck;
import service.Service;
import service.ServiceGUI;
import java.awt.Font;

/**
 * 
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
public class TruckRegisterDialog extends JDialog {

	private Service service;
	private ServiceGUI serviceGui;

	private Controller controller;

	private JPanel pnlWest, pnlEast, pnlSouth;
	
	private JPanel pnlTruck, pnlRest, pnlWeight;
	private JLabel lblTruck;
	private JComboBox<Truck> cbbTruck;
	private JLabel lblRest;
	private JTextField txfRest;
	private JLabel lblWeight;
	private JTextField txfWeight;
	
	private DefaultComboBoxModel<Truck> truckModel;
	
	private JLabel lblPartialOrder;
	private JList<PartialOrder> lstPartialOrders;
	private JScrollPane scpPartialOrders;
	
	private DefaultComboBoxModel<PartialOrder> partialOrderModel;
	
	private JButton btnOk;
	private JButton btnCancel;
	
	private boolean modalResult;
	private JPanel pnlPartialOrders;
	private JComboBox<PackageType> cbbPackageType;

	private DefaultComboBoxModel<PackageType> packageTypeModel;
	private JPanel pnlNorth;
	private JLabel lblRegisterArrival;

	/**
	 * Create the dialog.
	 */
	public TruckRegisterDialog() {
		this.setTitle("TruckRegister");
		this.setModal(true);
		this.setResizable(false);
		this.setBounds(100, 100, 620, 450);
		getContentPane().setLayout(new BorderLayout());

		service = Service.getInstance();
		serviceGui = ServiceGUI.getInstance();
		
		controller = new Controller();
		
		truckModel = new DefaultComboBoxModel<>();
		partialOrderModel = new DefaultComboBoxModel<>();
		packageTypeModel = new DefaultComboBoxModel<>();
		inputData();
		modalResult = false;
		
		
		//Label dimension
		Dimension dimensionLabel = new Dimension(140, 25);
		Dimension dimensionComponents = new Dimension(150, 25);
		Dimension dimPanel = new Dimension(300, 30);
		
		//Panel West
		pnlWest = new JPanel();
		pnlWest.setLayout(new VerticalFlowLayout(FlowLayout.LEFT, 15, 15));
		getContentPane().add(pnlWest, BorderLayout.WEST);
		
		pnlTruck = new JPanel();
		pnlTruck.setPreferredSize(dimPanel);
		pnlTruck.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlWest.add(pnlTruck);
		
		lblTruck = new JLabel("Truck: ");
		lblTruck.setPreferredSize(dimensionLabel);
		pnlTruck.add(lblTruck);
		
		cbbTruck = new JComboBox<Truck>();
		cbbTruck.setPreferredSize(dimensionComponents);
		pnlTruck.add(cbbTruck);
		cbbTruck.addItemListener(controller);
		cbbTruck.setModel(truckModel);
		
		pnlRest = new JPanel();
		pnlRest.setPreferredSize(dimPanel);
		pnlRest.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlWest.add(pnlRest);
		
		lblRest = new JLabel("Rest needed (minutes): ");
		lblRest.setPreferredSize(dimensionLabel);
		pnlRest.add(lblRest);
		
		txfRest = new JTextField("0");
		txfRest.setPreferredSize(dimensionComponents);
		pnlRest.add(txfRest);
		
		pnlWeight = new JPanel();
		pnlWeight.setPreferredSize(dimPanel);
		pnlWeight.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlWest.add(pnlWeight);
		
		lblWeight = new JLabel("Truck weight (kg): ");
		lblWeight.setPreferredSize(dimensionLabel);
		pnlWeight.add(lblWeight);
		
		txfWeight = new JTextField();
		txfWeight.setPreferredSize(dimensionComponents);
		pnlWeight.add(txfWeight);
		
		//Panel East
		pnlEast = new JPanel();
		VerticalFlowLayout vfl_pnlEast = new VerticalFlowLayout(FlowLayout.LEFT, 15, 15);
		pnlEast.setLayout(vfl_pnlEast);
		getContentPane().add(pnlEast, BorderLayout.EAST);
		
		pnlPartialOrders = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlPartialOrders.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnlEast.add(pnlPartialOrders);
		
		lblPartialOrder = new JLabel("Partial orders: ");
		pnlPartialOrders.add(lblPartialOrder);
		lblPartialOrder.setPreferredSize(new Dimension(100, 25));
		
		cbbPackageType = new JComboBox<PackageType>();
		cbbPackageType.setPreferredSize(new Dimension(145, 25));
		cbbPackageType.setModel(packageTypeModel);
		cbbPackageType.addItemListener(controller);
		pnlPartialOrders.add(cbbPackageType);
		
		lstPartialOrders = new JList<>();
		lstPartialOrders.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		lstPartialOrders.setModel(partialOrderModel);
		scpPartialOrders = new JScrollPane(lstPartialOrders);
		scpPartialOrders.setPreferredSize(new Dimension(250, 250));
		pnlEast.add(scpPartialOrders);
		
		
		//Panel South
		pnlSouth = new JPanel();
		pnlSouth.setLayout(new FlowLayout(FlowLayout.RIGHT, 15, 15));
		getContentPane().add(pnlSouth, BorderLayout.SOUTH);

		btnOk = new JButton("Ok");
		btnOk.setPreferredSize(new Dimension(50, 25));
		pnlSouth.add(btnOk);
		btnOk.addActionListener(controller);
		
		btnCancel = new JButton("Cancel");
		btnCancel.setPreferredSize(new Dimension(75, 25));
		pnlSouth.add(btnCancel);
		
		pnlNorth = new JPanel();
		getContentPane().add(pnlNorth, BorderLayout.NORTH);
		
		lblRegisterArrival = new JLabel("Register arrival");
		lblRegisterArrival.setFont(new Font("Euphemia", Font.PLAIN, 19));
		pnlNorth.add(lblRegisterArrival);
		btnCancel.addActionListener(controller);
		
		controller.updatePackageTypes();
		controller.updatePartialOrders();
	}
	
	public boolean isModalResult(){
		return modalResult;
	}
	
	public void setModalResult(boolean modalResult){
		this.modalResult = modalResult;
	}

	/**
	 * Method for populating list with trucks
	 */
	private void inputData(){
		List<Truck> trucks = serviceGui.getTrucksWithoutTruckRegister();
		for(Truck t : trucks){
			truckModel.addElement(t);
		}
	}

	private class Controller implements ActionListener, ItemListener{
		
		@Override
		public void actionPerformed(ActionEvent ae){
			if (ae.getSource().equals(btnOk)) {
				List<PartialOrder> partialOrders = lstPartialOrders.getSelectedValuesList();
				if (partialOrders.size() != 0) {
					Truck truck = (Truck) cbbTruck.getSelectedItem();
					if (serviceGui.checkTrucksPartialOrders(truck, partialOrders)){
						try {
							int rest = Integer.parseInt(txfRest.getText());
							int weight = Integer.parseInt(txfWeight.getText());
							if (weight > 0 && weight <= Truck.TRUCKCAPACITY && rest >= 0){
								service.registerArrival(truck, partialOrders, rest, weight);
								setModalResult(true);
								setVisible(false);
							}
							else {
								JOptionPane.showMessageDialog(null, "Truck must be selected \nRest needed must be a number equal or higher than 0 \nTruck weight must be a number \nPartial orders must be selected and weight cannot exceed truck capacity", "Error", JOptionPane.ERROR_MESSAGE);
							}
						} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "Truck must be selected \nRest needed must be a number equal or higher than 0 \nTruck weight must be a number \nPartial orders must be selected and weight cannot exceed truck capacity", "Error", JOptionPane.ERROR_MESSAGE);
						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Truck must be selected \nRest needed must be a number equal or higher than 0 \nTruck weight must be a number \nPartial orders must be selected and weight cannot exceed truck capacity", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Truck must be selected \nRest needed must be a number equal or higher than 0 \nTruck weight must be a number \nPartial orders must be selected and weight cannot exceed truck capacity", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} else if (ae.getSource().equals(btnCancel)) {
				setVisible(false);
			}
		}

		@Override
		public void itemStateChanged(ItemEvent ie) {
			if (ie.getSource().equals(cbbTruck)){
				updatePackageTypes();
			}
			else if (ie.getSource().equals(cbbPackageType)){
				updatePartialOrders();
			}
		}

		private void updatePackageTypes() {
			if (cbbTruck.getSelectedItem() != null){
				Truck selectedTruck = (Truck) cbbTruck.getSelectedItem();
				packageTypeModel.removeAllElements();
				for (PackageType pt : selectedTruck.getPackageTypes()){
					packageTypeModel.addElement(pt);
				}
			}
		}
		
		private void updatePartialOrders(){
			if (cbbPackageType.getSelectedItem() != null){
				partialOrderModel.removeAllElements();
				PackageType pt = (PackageType) cbbPackageType.getSelectedItem();
				List<PartialOrder> pos = serviceGui.getPartialOrdersWithoutTruckRegister(pt);
				for (PartialOrder partialOrder : pos) {
					partialOrderModel.addElement(partialOrder);
				}
			}
		}
	}
}
