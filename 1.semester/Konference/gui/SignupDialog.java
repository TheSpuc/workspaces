package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import service.Service;

import model.Conference;
import model.Hotel;
import model.Participant;
import model.Trip;

/**
 * Dialog for a creating a signup
 * @author Mark Medum Bundgaard
 *
 */
public class SignupDialog extends JDialog {
	private JList<Participant> jlsParticipants;
	private JList<Hotel> jlsHotels;
	private JList<Trip> jlsTrips;
	private JScrollPane jscParticipants, jscHotels, jscTrips;
	private JTextField jtfParticipantName, jtfParticipantAddress,
	jtfParticipantCity, jtfParticipantCountry, jtfParticipantPhone,
	jtfParticipantCompany, jtfParticipantCompanyPhone,
	jtfCompanionName, jtfAddition;
	private JLabel jlbParticipantName, jlbParticipantAddress,
	jlbParticipantCity, jlbParticipantCountry, jlbParticipantPhone,
	jlbParticipantCompany, jlbParticipantCompanyPhone, jlbParticipants,
	jlbParticipantInfo, jlbCompanion, jlbHotels, jlbTrips,
	jlbConference;
	private JCheckBox jcbCompanion, jcbAddition, jcbLecturer;
	private JButton jbCreate, jbCancel;
	private Controller controller;
	private Conference conference;

	public SignupDialog(Conference conference) {
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setSize(810, 400);
		this.setLocation(200, 200);
		this.setLayout(null);
		this.setModal(true);
		this.setTitle("Create signup for " + conference);
		controller = new Controller();
		this.conference = conference;

		jlbParticipants = new JLabel("Previous participants: ");
		jlbParticipants.setBounds(10, 10, 150, 25);
		this.add(jlbParticipants);

		jlsParticipants = new JList<Participant>(Service.getParticipante().toArray(new Participant[0]));
		jlsParticipants.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jscParticipants = new JScrollPane(jlsParticipants);
		jscParticipants.setBounds(10, 35, 150, 280);
		this.add(jscParticipants);

		jlbParticipantInfo = new JLabel("New participant info: ");
		jlbParticipantInfo.setBounds(185, 10, 150, 25);
		this.add(jlbParticipantInfo);

		jlbParticipantName = new JLabel("Name*: ");
		jlbParticipantName.setBounds(185, 35, 100, 25);
		this.add(jlbParticipantName);

		jtfParticipantName = new JTextField();
		jtfParticipantName.setBounds(275, 35, 120, 25);
		this.add(jtfParticipantName);

		jlbParticipantAddress = new JLabel("Address*: ");
		jlbParticipantAddress.setBounds(185, 70, 100, 25);
		this.add(jlbParticipantAddress);

		jtfParticipantAddress = new JTextField();
		jtfParticipantAddress.setBounds(275, 70, 120, 25);
		this.add(jtfParticipantAddress);

		jlbParticipantCity = new JLabel("City*: ");
		jlbParticipantCity.setBounds(185, 105, 100, 25);
		this.add(jlbParticipantCity);

		jtfParticipantCity = new JTextField();
		jtfParticipantCity.setBounds(275, 105, 120, 25);
		this.add(jtfParticipantCity);

		jlbParticipantPhone = new JLabel("Phone nr*: ");
		jlbParticipantPhone.setBounds(185, 140, 100, 25);
		this.add(jlbParticipantPhone);

		jtfParticipantPhone = new JTextField();
		jtfParticipantPhone.setBounds(275, 140, 120, 25);
		this.add(jtfParticipantPhone);

		jlbParticipantCountry = new JLabel("Country*: ");
		jlbParticipantCountry.setBounds(185, 175, 100, 25);
		this.add(jlbParticipantCountry);

		jtfParticipantCountry = new JTextField();
		jtfParticipantCountry.setBounds(275, 175, 120, 25);
		this.add(jtfParticipantCountry);

		jlbParticipantCompany = new JLabel("Company:");
		jlbParticipantCompany.setBounds(185, 210, 100, 25);
		this.add(jlbParticipantCompany);

		jtfParticipantCompany = new JTextField();
		jtfParticipantCompany.setBounds(275, 210, 120, 25);
		this.add(jtfParticipantCompany);

		jlbParticipantCompanyPhone = new JLabel("C. phone: ");
		jlbParticipantCompanyPhone.setBounds(185, 245, 100, 25);
		this.add(jlbParticipantCompanyPhone);

		jtfParticipantCompanyPhone = new JTextField();
		jtfParticipantCompanyPhone.setBounds(275, 245, 120, 25);
		this.add(jtfParticipantCompanyPhone);

		jcbCompanion = new JCheckBox("Companion");
		jcbCompanion.setBounds(185, 277, 80, 15);
		this.add(jcbCompanion);
		jcbCompanion.addItemListener(controller);

		jcbLecturer = new JCheckBox("Lecturer");
		jcbLecturer.setBounds(275, 277, 100, 15);
		this.add(jcbLecturer);

		jlbCompanion = new JLabel("Name*:");
		jlbCompanion.setBounds(185, 300, 100, 25);
		this.add(jlbCompanion);
		jlbCompanion.setVisible(false);

		jtfCompanionName = new JTextField();
		jtfCompanionName.setBounds(275, 300, 120, 25);
		this.add(jtfCompanionName);
		jtfCompanionName.setVisible(false);

		jlbHotels = new JLabel("Available hotels:");
		jlbHotels.setBounds(435, 10, 140, 25);
		this.add(jlbHotels);

		jlsHotels = new JList<Hotel>(Service.getHotels(conference).toArray(new Hotel[0]));
		jlsHotels.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlsHotels.addListSelectionListener(controller);
		jscHotels = new JScrollPane(jlsHotels);
		jscHotels.setBounds(435, 35, 150, 220);
		this.add(jscHotels);

		jtfAddition = new JTextField();
		jtfAddition.setBounds(435, 265, 150, 25);
		jtfAddition.setEditable(false);
		jtfAddition.setVisible(false);
		this.add(jtfAddition);


		jcbAddition = new JCheckBox("Addition");
		jcbAddition.setBounds(432, 295, 100, 25);
		jcbAddition.setVisible(false);
		this.add(jcbAddition);

		jlbTrips = new JLabel("Companion trips:");
		jlbTrips.setBounds(645, 10, 140, 25);
		this.add(jlbTrips);

		jlsTrips = new JList<Trip>(Service.getTrips(conference).toArray(new Trip[0]));
		jlsTrips.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jscTrips = new JScrollPane(jlsTrips);
		jscTrips.setBounds(645, 35, 150, 280);
		this.add(jscTrips);

		jlbConference = new JLabel("Fields with \"*\" are required.");
		jlbConference.setBounds(10, 325, 500, 25);
		this.add(jlbConference);

		jbCreate = new JButton("Create signup");
		jbCreate.setBounds(565, 345, 140, 25);
		this.add(jbCreate);
		jbCreate.addActionListener(controller);

		jbCancel = new JButton("Cancel");
		jbCancel.setBounds(700, 345, 100, 25);
		this.add(jbCancel);
		jbCancel.addActionListener(controller);
	}

	private class Controller implements ActionListener, ItemListener, ListSelectionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(jbCreate)) {
				Participant participant = null;
				if (jlsParticipants.isSelectionEmpty()){
					String name = jtfParticipantName.getText().trim();
					String address = jtfParticipantAddress.getText().trim();
					String city = jtfParticipantCity.getText().trim();
					String country = jtfParticipantCountry.getText().trim();
					String phone = jtfParticipantPhone.getText().trim();
					String companyName = jtfParticipantCompany.getText().trim();
					String companyPhone = jtfParticipantCompanyPhone.getText().trim();
					if(name.length() > 0 && address.length() > 0 && city.length() > 0 && country.length() > 0 && phone.length() > 0){
						participant = Service.createParticipant(name, address, city, country, phone, companyName, companyPhone);
					}
				} else if (!jlsParticipants.isSelectionEmpty()) {
					participant = jlsParticipants.getSelectedValue();
				}
				if (participant != null) {
					String companionName = "";
					boolean lecturer = jcbLecturer.isSelected();
					boolean gotCompanion = jcbCompanion.isSelected();
					companionName = jtfCompanionName.getText().trim();

					Hotel hotel = null;
					boolean hotelAddition = false;
					if (!jlsHotels.isSelectionEmpty()) {
						hotel = jlsHotels.getSelectedValue();
						if(Service.getHotelAddition(hotel)){
							hotelAddition = jcbAddition.isSelected();
						}
					}
					Service.createSignup(participant, conference, hotel, lecturer, hotelAddition, gotCompanion, companionName);
					if (gotCompanion && companionName.length() > 0) {
						Service.setCompanionTrips(Service.getCompanion(participant), new ArrayList<Trip>(jlsTrips.getSelectedValuesList()));
					}
				}
				SignupDialog.this.setVisible(false);
			} else if (e.getSource().equals(jbCancel)) {
				SignupDialog.this.setVisible(false);
			}
		}

		@Override
		public void itemStateChanged(ItemEvent arg0) {
			if (jcbCompanion.isSelected()) {
				jlbCompanion.setVisible(true);
				jtfCompanionName.setVisible(true);
			} else if (!jcbCompanion.isSelected()) {
				jlbCompanion.setVisible(false);
				jtfCompanionName.setVisible(false);
			} 
		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getSource().equals(jlsHotels)){
				Hotel hotel = jlsHotels.getSelectedValue();
				if(Service.getHotelAddition(hotel)){
					jcbAddition.setVisible(true);
					jtfAddition.setVisible(true);
					jtfAddition.setText(Service.getHotelAdditionDescription(hotel));
				}else {
					jcbAddition.setVisible(false);
					jtfAddition.setVisible(false);
				}
			}
		}
	}
}