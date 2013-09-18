package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import service.Service;


import model.Participant;
import model.Hotel;
import model.Conference;
import model.Signup;
import model.Trip;

/**
 * Frame for the KAS-system
 * @author Mark Medum Bundgaard
 *
 */
public class MainFrame extends JFrame {

	private JLabel jlConferences, jlConferenceParticipants, jlHotels, jlCompanionName, jlTrips, jlPrice;
	private JButton addConference, jbAddSignup, jbPrint;
	private DefaultComboBoxModel<Conference> model;
	private JComboBox<Conference> jcbConferences;
	private JTextField jtfHotelDescription, jtfCompanionName, jtfPrice;
	private JScrollPane jspConferenceParticipants, jspHotels, jspTrips, jspPrint, jspCompanion; 
	private JTextArea jtaPrint;
	private JCheckBox jcbHotelAddition, jcbGotCompanion, jcbIsLecturer;
	private JList<Participant> participantsJList;
	private JList<Hotel> hotelsJList;
	private JList<Trip> tripsJList;
	private Controller controller;
	private ConferenceDialog conferenceDialog;
	private SignupDialog signupDialog;

	public MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(850,800);
		this.setLocation(100, 100);
		this.setTitle("KAS-System");

		controller = new Controller();

		jlConferences = new JLabel("Conference:");
		jlConferences.setBounds(10, 10, 100, 25);
		this.add(jlConferences);

		jcbConferences = new JComboBox<>();
		jcbConferences.addItemListener(controller);
		model = new DefaultComboBoxModel<>();
		jcbConferences.setModel(model);
		jcbConferences.setBounds(90, 10, 200, 25);
		this.add(jcbConferences);

		addConference = new JButton("Add Conference");
		addConference.setBounds(290, 10, 160, 25);
		addConference.addActionListener(controller);
		this.add(addConference);

		jlConferenceParticipants = new JLabel("Conference Participants:");
		jlConferenceParticipants.setBounds(10, 50, 200, 25);
		this.add(jlConferenceParticipants);

		participantsJList = new JList<Participant>();
		jspConferenceParticipants = new JScrollPane(participantsJList);
		participantsJList.addListSelectionListener(controller);
		jspConferenceParticipants.setBounds(10, 80, 300, 300);
		this.add(jspConferenceParticipants);

		jcbGotCompanion = new JCheckBox("Companion");
		jcbGotCompanion.setBounds(5, 380, 140, 25);
		jcbGotCompanion.setEnabled(false);
		this.add(jcbGotCompanion);

		jcbIsLecturer = new JCheckBox("Lecturer");
		jcbIsLecturer.setBounds(160, 380, 100, 25);
		jcbIsLecturer.setEnabled(false);
		this.add(jcbIsLecturer);

		jlCompanionName = new JLabel("Name:");
		jlCompanionName.setBounds(10, 415, 100, 25);
		this.add(jlCompanionName);

		jtfCompanionName = new JTextField();
		jtfCompanionName.setEditable(false);
		jspCompanion = new JScrollPane(jtfCompanionName);
		jspCompanion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		jspCompanion.setBounds(60, 410, 206, 35);
		this.add(jspCompanion);

		jlHotels = new JLabel("Hotels:");
		jlHotels.setBounds(325, 50, 200, 25);
		this.add(jlHotels);

		hotelsJList = new JList<Hotel>();
		jspHotels = new JScrollPane(hotelsJList);
		jspHotels.setBounds(325, 80, 250, 300);
		this.add(jspHotels);

		jtfHotelDescription = new JTextField();
		jtfHotelDescription.setBounds(322, 383, 170, 25);
		jtfHotelDescription.setEditable(false);
		this.add(jtfHotelDescription);

		jcbHotelAddition = new JCheckBox("Addition");
		jcbHotelAddition.setBounds(490, 382, 90, 25);
		jcbHotelAddition.setEnabled(false);
		this.add(jcbHotelAddition);

		jlTrips = new JLabel("Trips for Companion:");
		jlTrips.setBounds(590, 50, 200, 25);
		this.add(jlTrips);

		tripsJList = new JList<Trip>();
		jspTrips = new JScrollPane(tripsJList);
		jspTrips.setBounds(590, 80, 250, 300);
		this.add(jspTrips);

		jbAddSignup = new JButton("Create Signup");
		jbAddSignup.setBounds(697, 385, 150, 25);
		jbAddSignup.addActionListener(controller);
		this.add(jbAddSignup);

		jlPrice = new JLabel("Price:");
		jlPrice.setBounds(640, 425, 120, 25);
		this.add(jlPrice);

		jtfPrice = new JTextField();
		jtfPrice.setEditable(false);
		jtfPrice.setBounds(695, 425, 150, 25);
		this.add(jtfPrice);

		jbPrint = new JButton("Print statistic");
		jbPrint.addActionListener(controller);
		jbPrint.setBounds(4, 445, 140, 25);
		this.add(jbPrint);

		jtaPrint = new JTextArea();
		jtaPrint.setEditable(false);
		jspPrint = new JScrollPane(jtaPrint);
		jspPrint.setBounds(10, 470, 830, 300);
		this.add(jspPrint);

	}

	private class Controller implements ActionListener, ItemListener, ListSelectionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(addConference)){
				conferenceDialog = new ConferenceDialog();
				conferenceDialog.setVisible(true);
				ArrayList<Conference> tempList = Service.getConferences();
				model.removeAllElements();
				for(Conference con: tempList){
					model.addElement(con);
				}
			}else if(e.getSource().equals(jbAddSignup)){
				Conference con = (Conference)jcbConferences.getSelectedItem();
				if(con != null){
					signupDialog = new SignupDialog(con);
					signupDialog.setVisible(true);
					participantsJList.setListData(Service.getConferenceParticipants(con).toArray(new Participant[0]));

				}
			}else if(e.getSource().equals(jbPrint)){
				Conference con = (Conference)jcbConferences.getSelectedItem();
				if(con != null){
					jtaPrint.setText("");
					jtaPrint.append("Conference information:\n");
					jtaPrint.append(con + ": \n\n");
					jtaPrint.append("Participant statistic:\n");
					for(Signup signup : con.getSignups()){
						Participant participant = signup.getParticipant();
						jtaPrint.append(participant + " \n" +
								participant.getCompanion() + " \n\n");
					}
					jtaPrint.append("Trips statistic:\n");
					for(Trip trip : con.getTrips()){
						jtaPrint.append(trip + ":\n");
						if(trip.getCompanions() != null){
							jtaPrint.append(" " + trip.getCompanions() + "\n");
						}
						jtaPrint.append("\n");
					}

					jtaPrint.append("Hotel statistic:\n");
					for(Hotel hotel : con.gethotels()){
						jtaPrint.append(hotel + ":\n");
						for(Signup signup : con.getSignups()){
							if(signup.getHotel() != null){
								if(signup.getHotel().equals(hotel)){
									jtaPrint.append(" " + signup.getParticipant() + "\n");
								}
							}
						}
						jtaPrint.append("\n"); 
					}
				}
			}
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED){
				Conference con = (Conference) jcbConferences.getSelectedItem();
				hotelsJList.setListData(Service.getHotels(con).toArray(new Hotel[0]));
				tripsJList.setListData(Service.getTrips(con).toArray(new Trip[0]));
				participantsJList.setListData(Service.getConferenceParticipants(con).toArray(new Participant[0]));
			}

		}

		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getSource().equals(participantsJList)){
				Conference con = (Conference) jcbConferences.getSelectedItem();
				Participant par = participantsJList.getSelectedValue();
				Signup signup = Service.getSignup(con, par);

				if(par != null){
					if(signup.getCompanion()){
						jcbGotCompanion.setSelected(true);
						jtfCompanionName.setText(Service.getCompanion(par) + "");
					}else{
						jcbGotCompanion.setSelected(false);
						jtfCompanionName.setText("No companion");
						tripsJList.clearSelection();
					}
					Hotel hotel = signup.getHotel();
					if(hotel != null){
						hotelsJList.setSelectedValue(hotel, true);
						if(signup.getHotelAddition()){
							jcbHotelAddition.setSelected(true);
							jtfHotelDescription.setText(hotel.getAdditionDescription());
						}else {
							jcbHotelAddition.setSelected(false);
							jtfHotelDescription.setText("");
						}
					}else {
						hotelsJList.clearSelection();
					}
					jtfPrice.setText(Service.calculatePrice(signup) + "");
				}
			}
		}
	}
}
