package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import service.Service;
import service.ServiceGUI;

import model.Loading;
import model.Ramp;
import model.TruckRegister;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * 
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
public class MainFrame extends JFrame {

	private Service service;
	private ServiceGUI serviceGui;

	private Controller controller;

	private JMenu menCreate;
	private JMenuItem menitTruck, menitTruckregister, menitOrder;

	private DefaultComboBoxModel<Ramp> cbbModel;
	private DefaultComboBoxModel<Loading> lstLoadingModel;

	private JPanel pnlNorth, pnlCenter, pnlWest, pnlEast;

	//West
	private JLabel lblLoadings;
	private JList<Loading> lstLoadings;
	private JScrollPane scpLoadings;
	private JButton btnDone;

	//Center
	private JLabel lblTruckRegister;
	private JTextArea txaTruckRegister;
	private JLabel lblTruck;
	private JTextArea txaTruck;

	//East
	private JLabel lblPartialOrder;
	private JTextArea txaPartialOrder;
	private JScrollPane scpPartialOrder;

	//North
	private JComboBox<Ramp> cbbRamps;
	private JLabel lblRamp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Service.getInstance().createSomeObjects();
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
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 1024, 720);
		this.setLayout(new BorderLayout());
		this.setResizable(false);

		service = Service.getInstance();
		serviceGui = ServiceGUI.getInstance();

		// Controller
		controller = new Controller();

		// Creating MenuBar and MenuItems
		createMenu();

		// Creating Comboboxmodels and adding ramps
		cbbModel = new DefaultComboBoxModel<>();
		inputRamps();
		lstLoadingModel = new DefaultComboBoxModel<>();

		// Setting same size on all JLabels
		Dimension dimensionLabel = new Dimension(308, 25);
		Dimension dimensionTxa = new Dimension(308, 238);

		// Panel West
		pnlWest = new JPanel();
		pnlWest.setLayout(new VerticalFlowLayout(FlowLayout.LEFT, 15, 14));
		this.add(pnlWest, BorderLayout.WEST);

		lblLoadings = new JLabel("Loadings:");
		lblLoadings.setPreferredSize(dimensionLabel);
		pnlWest.add(lblLoadings);

		lstLoadings = new JList<>();
		lstLoadings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstLoadings.addListSelectionListener(controller);
		lstLoadings.setModel(lstLoadingModel);
		scpLoadings = new JScrollPane(lstLoadings);
		scpLoadings.setPreferredSize(new Dimension(308, 500));
		pnlWest.add(scpLoadings);

		btnDone = new JButton("End Current Loading");
		btnDone.setPreferredSize(new Dimension(180, 25));
		btnDone.addActionListener(controller);	
		pnlWest.add(btnDone);

		// Panel Center
		pnlCenter = new JPanel();
		pnlCenter.setLayout(new VerticalFlowLayout(FlowLayout.LEFT, 15, 16));
		this.add(pnlCenter, BorderLayout.CENTER);

		lblTruckRegister = new JLabel("Truck Register:");
		lblTruckRegister.setPreferredSize(dimensionLabel);
		pnlCenter.add(lblTruckRegister);

		txaTruckRegister = new JTextArea();
		txaTruckRegister.setEditable(false);
		txaTruckRegister.setLineWrap(true);
		txaTruckRegister.setWrapStyleWord(true);
		txaTruckRegister.setPreferredSize(dimensionTxa);
		txaTruckRegister.setBorder(new LineBorder(Color.GRAY));
		pnlCenter.add(txaTruckRegister);

		lblTruck = new JLabel("Truck:");
		lblTruck.setPreferredSize(dimensionLabel);
		pnlCenter.add(lblTruck);

		txaTruck = new JTextArea();
		txaTruck.setEditable(false);
		txaTruck.setLineWrap(true);
		txaTruck.setWrapStyleWord(true);
		txaTruck.setPreferredSize(dimensionTxa);
		txaTruck.setBorder(new LineBorder(Color.GRAY));
		pnlCenter.add(txaTruck);

		// Panel East
		pnlEast = new JPanel();
		pnlEast.setLayout(new VerticalFlowLayout(FlowLayout.LEFT, 15, 16));
		this.add(pnlEast, BorderLayout.EAST);

		lblPartialOrder = new JLabel("Partial Order:");
		lblPartialOrder.setPreferredSize(dimensionLabel);
		pnlEast.add(lblPartialOrder);

		txaPartialOrder = new JTextArea();
		txaPartialOrder.setEditable(false);
		txaPartialOrder.setLineWrap(true);
		txaPartialOrder.setWrapStyleWord(true);
		scpPartialOrder = new JScrollPane(txaPartialOrder);
		scpPartialOrder.setPreferredSize(dimensionTxa);
		scpPartialOrder.setBorder(new LineBorder(Color.GRAY));
		pnlEast.add(scpPartialOrder);

		// Panel North
		pnlNorth = new JPanel();
		pnlNorth.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
		this.add(pnlNorth, BorderLayout.NORTH);

		lblRamp = new JLabel("Ramp:");
		lblRamp.setPreferredSize(new Dimension(85, 25));
		pnlNorth.add(lblRamp);

		cbbRamps = new JComboBox<Ramp>();
		cbbRamps.setPreferredSize(new Dimension(150, 25));
		pnlNorth.add(cbbRamps);
		cbbRamps.setModel(cbbModel);
		cbbRamps.addActionListener(controller);
		cbbRamps.setSelectedIndex(0);

	}

	private void inputRamps() {
		List<Ramp> ramps = service.getRamps();
		for (Ramp ramp : ramps) {
			cbbModel.addElement(ramp);
		}
	}

	private void createMenu() {
		JMenuBar menu = new JMenuBar();
		setJMenuBar(menu);

		menCreate = new JMenu("Create");
		menCreate.setMnemonic(KeyEvent.VK_C);
		menu.add(menCreate);

		menitOrder = new JMenuItem("Order");
		menitOrder.addActionListener(controller);
		menCreate.add(menitOrder);

		menitTruck = new JMenuItem("Truck");
		menitTruck.addActionListener(controller);
		menCreate.add(menitTruck);

		menitTruckregister = new JMenuItem("TruckRegister");
		menitTruckregister.addActionListener(controller);
		menCreate.add(menitTruckregister);
	}

	private class Controller implements ActionListener, ListSelectionListener {

		@Override
		public void actionPerformed(ActionEvent ae) {
			if (ae.getSource().equals(menitOrder)) {
				OrderDialog od = new OrderDialog();
				od.setVisible(true);
			} else if (ae.getSource().equals(menitTruck)) {
				TruckDialog td = new TruckDialog();
				td.setVisible(true);
			} else if (ae.getSource().equals(menitTruckregister)) {
				TruckRegisterDialog trd = new TruckRegisterDialog();
				trd.setVisible(true);
				if (trd.isModalResult()){
					updateLoadings();
				}
			} else if (ae.getSource().equals(cbbRamps)) {
				updateLoadings();
				clearTextArea();
			} else if (ae.getSource().equals(btnDone)) {
				Ramp ramp = (Ramp) cbbRamps.getSelectedItem();
				Loading loading = service.getLoadingBeingLoaded(ramp);
				if(loading != null) {
					TruckRegister truckRegister = loading.getTruckRegister();
					NotifyChauffeurDialog ncd = new NotifyChauffeurDialog(truckRegister);
					ncd.setVisible(true);
					if(ncd.isModalResult()){
						if (!service.registerDeparture(truckRegister, loading, ramp)){
							JOptionPane.showMessageDialog(null, "Truck was sent to get repacked", "Error", JOptionPane.INFORMATION_MESSAGE);
						}
						clearTextArea();
					}
					updateLoadings();
				}
			}
		} 
		
		private void clearTextArea() {
			txaTruckRegister.setText("");
			txaPartialOrder.setText(" ");
			txaTruck.setText(" ");
		}

		private void updateLoadings(){
			Ramp r = (Ramp) cbbRamps.getSelectedItem();
			lstLoadingModel.removeAllElements();
			List<Loading> tempLoadings = service.getLoadingsInQueue(r);
			for (Loading loading : tempLoadings) {
				lstLoadingModel.addElement(loading);
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent lse) {
			if (lse.getSource().equals(lstLoadings)) {
				Loading loading = lstLoadings.getSelectedValue();
				if(loading != null) {
					txaTruckRegister.setText(serviceGui.printTruckRegister(loading));
					txaTruck.setText(serviceGui.printTruck(loading));
					txaPartialOrder.setText(serviceGui.printPartialOrder(loading));
				}
			}
		}
	}
}
