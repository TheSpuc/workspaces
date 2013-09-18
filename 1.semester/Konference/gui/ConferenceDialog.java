package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import service.Service;

import model.Conference;
import model.Hotel;
import model.Trip;

/**
 * Dialog for creating a conference
 * @author Mark Medum Bundgaard
 *
 */
public class ConferenceDialog extends JDialog {

	private JLabel jlConferenceInformation, jlHotelInformation, jlTripInformation, jlConferenceName, jlConferencePrice, jlConferenceNumberOfDays, 
	jlConferenceFromDate, jlConferenceToDate, jlAllHotels, jlHotelName, jlHotelSinglePrice, 
	jlHotelDoublePrice, jlHotelAddition, jlHotelAdditionDescription, jlAllTrips, jlTripName, jlTripPrice, jlTripDate;
	private JTextField jtfConferenceName, jtfConferencePrice, jtfConferenceNumberOfDays, jtfConferenceFromDate, jtfConferenceToDate, jtfHotelName, 
	jtfHotelSinglePrice, jtfHotelDoublePrice, jtfHotelAddition, jtfAdditionDescription, jtfTripName, jtfTripPrice, jtfTripDate;
	private JButton jbRemoveHotel, jbAddHotel, jbAddTrip, jbRemoveTrip, jbDone, jbCancel;
	private JList<Hotel> hotelJList;
	private JList<Trip> tripsJList;
	private JScrollPane jspHotel, jspTrip;
	private Controller controller;

	public ConferenceDialog(){
		controller = new Controller();
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setLayout(null);
		this.setSize(1230, 350);
		this.setLocation(200, 350);
		this.setModal(true);
		this.setTitle("Edit Conference");

		jlConferenceInformation = new JLabel("Conference Information:");
		jlConferenceInformation.setBounds(10, 10, 200, 25);
		this.add(jlConferenceInformation);

		jlConferenceName = new JLabel("Name*: ");
		jlConferenceName.setBounds(10, 40, 120, 25);
		this.add(jlConferenceName);

		jtfConferenceName = new JTextField();
		jtfConferenceName.setBounds(110, 40, 150, 25);
		this.add(jtfConferenceName);

		jlConferencePrice = new JLabel("Price*: ");
		jlConferencePrice.setBounds(10, 80, 120, 25);
		this.add(jlConferencePrice);

		jtfConferencePrice = new JTextField();
		jtfConferencePrice.setBounds(110, 80, 150, 25);
		this.add(jtfConferencePrice);

		jlConferenceFromDate = new JLabel("From Date*: ");
		jlConferenceFromDate.setBounds(10, 120, 120, 25);
		this.add(jlConferenceFromDate);

		jtfConferenceFromDate = new JTextField();
		jtfConferenceFromDate.setBounds(110, 120, 150, 25);
		this.add(jtfConferenceFromDate);

		jlConferenceToDate = new JLabel("To Date*:");
		jlConferenceToDate.setBounds(10, 160, 120, 25);
		this.add(jlConferenceToDate);

		jtfConferenceToDate = new JTextField();
		jtfConferenceToDate.setBounds(110, 160, 150, 25);
		this.add(jtfConferenceToDate);

		jlConferenceNumberOfDays = new JLabel("Nr of days*:");
		jlConferenceNumberOfDays.setBounds(10, 200, 120, 25);
		this.add(jlConferenceNumberOfDays);

		jtfConferenceNumberOfDays = new JTextField();
		jtfConferenceNumberOfDays.setBounds(110, 200, 150, 25);
		this.add(jtfConferenceNumberOfDays);

		jlAllHotels = new JLabel("Pick Hotels:");
		jlAllHotels.setBounds(298, 10, 160, 25);
		this.add(jlAllHotels);

		jlHotelInformation = new JLabel("New Hotel Information:");
		jlHotelInformation.setBounds(490, 10, 200, 25);
		this.add(jlHotelInformation);

		hotelJList = new JList<Hotel>(Service.getHotels().toArray(new Hotel[0]));
		hotelJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jspHotel = new JScrollPane(hotelJList);
		jspHotel.setBounds(298, 35, 180, 205);
		this.add(jspHotel);

		jbRemoveHotel = new JButton("Remove Hotel");
		jbRemoveHotel.setBounds(292, 243, 156, 25);
		jbRemoveHotel.addActionListener(controller);
		this.add(jbRemoveHotel);

		jlHotelName = new JLabel("Name*:");
		jlHotelName.setBounds(490, 40, 120, 25);
		this.add(jlHotelName);

		jtfHotelName = new JTextField();
		jtfHotelName.setBounds(590, 40, 150, 25);
		this.add(jtfHotelName);

		jlHotelSinglePrice = new JLabel("Price single*:");
		jlHotelSinglePrice.setBounds(490, 80, 120, 25);
		this.add(jlHotelSinglePrice);

		jtfHotelSinglePrice = new JTextField();
		jtfHotelSinglePrice.setBounds(590, 80, 150, 25);
		this.add(jtfHotelSinglePrice);

		jlHotelDoublePrice = new JLabel("Price double*:");
		jlHotelDoublePrice.setBounds(490, 120, 120, 25);
		this.add(jlHotelDoublePrice);

		jtfHotelDoublePrice = new JTextField();
		jtfHotelDoublePrice.setBounds(590, 120, 150, 25);
		this.add(jtfHotelDoublePrice);

		jlHotelAddition = new JLabel("Addition Price:");
		jlHotelAddition.setBounds(490, 160, 120, 25);
		this.add(jlHotelAddition);

		jtfHotelAddition = new JTextField();
		jtfHotelAddition.setBounds(590, 160, 150, 25);
		this.add(jtfHotelAddition);

		jlHotelAdditionDescription = new JLabel("Description:");
		jlHotelAdditionDescription.setBounds(490, 200, 120, 25);
		this.add(jlHotelAdditionDescription);

		jtfAdditionDescription = new JTextField();
		jtfAdditionDescription.setBounds(590, 200, 150, 25);
		this.add(jtfAdditionDescription);

		jbAddHotel = new JButton("Add New Hotel");
		jbAddHotel.setBounds(587, 243, 156, 25);
		jbAddHotel.addActionListener(controller);
		this.add(jbAddHotel);

		jlAllTrips = new JLabel("Pick Trips:");
		jlAllTrips.setBounds(779, 10, 160, 25);
		this.add(jlAllTrips);

		jlTripInformation = new JLabel("New Trip Information:");
		jlTripInformation.setBounds(970, 10, 160, 25);
		this.add(jlTripInformation);

		tripsJList = new JList<Trip>(Service.getTrips().toArray(new Trip[0]));
		tripsJList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jspTrip = new JScrollPane(tripsJList);
		jspTrip.setBounds(780, 35, 180, 205);
		this.add(jspTrip);

		jbRemoveTrip = new JButton("Remove Trip");
		jbRemoveTrip.setBounds(774, 243, 156, 25);
		jbRemoveTrip.addActionListener(controller);
		this.add(jbRemoveTrip);

		jlTripName = new JLabel("Name*:");
		jlTripName.setBounds(970, 40, 120, 25);
		this.add(jlTripName);

		jtfTripName = new JTextField();
		jtfTripName.setBounds(1074, 40, 150, 25);
		this.add(jtfTripName);

		jlTripPrice = new JLabel("Price*:");
		jlTripPrice.setBounds(970, 80, 120, 25);
		this.add(jlTripPrice);

		jtfTripPrice = new JTextField();
		jtfTripPrice.setBounds(1074, 80, 150, 25);
		this.add(jtfTripPrice);

		jlTripDate = new JLabel("Date*:");
		jlTripDate.setBounds(970, 120, 120, 25);
		this.add(jlTripDate);

		jtfTripDate = new JTextField();
		jtfTripDate.setBounds(1074, 120, 150, 25);
		this.add(jtfTripDate);

		jbAddTrip = new JButton("Add Trip");
		jbAddTrip.setBounds(1072, 243, 156, 25);
		jbAddTrip.addActionListener(controller);
		this.add(jbAddTrip);

		jbDone = new JButton("Done");
		jbDone.setBounds(1030, 300, 100, 25);
		jbDone.addActionListener(controller);
		this.add(jbDone);

		jbCancel = new JButton("Cancel");
		jbCancel.setBounds(1130, 300, 100, 25);
		jbCancel.addActionListener(controller);
		this.add(jbCancel);
	}

	private class Controller implements ActionListener{
		public void actionPerformed(ActionEvent ae){
			if(ae.getSource().equals(jbDone)){
				String conferenceName = jtfConferenceName.getText().trim();
				int conferencePrice = 0;
				int conferenceDays = -1;
				try{
					conferencePrice = Integer.parseInt(jtfConferencePrice.getText().trim());
					conferenceDays = Integer.parseInt(jtfConferenceNumberOfDays.getText().trim());
				}
				catch(NumberFormatException e){
					jtfConferencePrice.setText("Input a number");
					jtfConferenceNumberOfDays.setText("Input a number");
				}
				String fromDate = jtfConferenceFromDate.getText().trim();
				String toDate = jtfConferenceToDate.getText().trim();

				if(conferenceName.length() > 0 && fromDate.length() > 0 && toDate.length() > 0){
					Conference con = Service.createConference(conferenceName, conferencePrice, conferenceDays, fromDate, toDate);
					Service.setHotels(con, new ArrayList<Hotel>(hotelJList.getSelectedValuesList()));
					Service.setTrips(con, new ArrayList<Trip>(tripsJList.getSelectedValuesList()));
				}
				ConferenceDialog.this.setVisible(false);
			}else if(ae.getSource().equals(jbAddHotel)){
				String name = jtfHotelName.getText().trim();
				int priceSingleRoomPrNight = -1;
				int priceDoubleRoomPrNight = -1;
				int addition = -1;
				try{
					priceSingleRoomPrNight = Integer.parseInt(jtfHotelSinglePrice.getText().trim());
					priceDoubleRoomPrNight = Integer.parseInt(jtfHotelDoublePrice.getText().trim());
					addition = Integer.parseInt(jtfHotelAddition.getText().trim());
				}
				catch(NumberFormatException e){
					jtfHotelSinglePrice.setText("Input a number");
					jtfHotelDoublePrice.setText("Input a number");
					jtfHotelAddition.setText("Input a number");
				}
				String additionDescription = jtfAdditionDescription.getText().trim();
				if(addition > 0 && additionDescription.length() == 0){
					jtfAdditionDescription.setText("Please input description");
				}else if(name.length() > 0 && priceSingleRoomPrNight >= 0 && priceDoubleRoomPrNight >= 0 && addition >= 0){
					Service.createHotel(name, priceSingleRoomPrNight, priceDoubleRoomPrNight, addition, additionDescription);
					hotelJList.setListData(Service.getHotels().toArray(new Hotel[0]));
				}
			}else if(ae.getSource().equals(jbRemoveHotel)){
				Hotel hotel = hotelJList.getSelectedValue();
				Service.removeHotel(hotel);
				hotelJList.setListData(Service.getHotels().toArray(new Hotel[0]));
			}else if(ae.getSource().equals(jbAddTrip)){
				String name = jtfTripName.getText().trim();
				int price = -1;
				try{
					price = Integer.parseInt(jtfTripPrice.getText().trim());
				}
				catch(NumberFormatException e){
					jtfTripPrice.setText("Input a number");
				}
				String date = jtfTripDate.getText().trim();
				if(name.length() > 0 && price >= 0){
					Service.createTrip(name, price, date);
					tripsJList.setListData(Service.getTrips().toArray(new Trip[0]));
				}
			}else if(ae.getSource().equals(jbRemoveTrip)){
				Trip trip = tripsJList.getSelectedValue();
				Service.removeTrip(trip);
				tripsJList.setListData(Service.getTrips().toArray(new Trip[0]));
			}else if(ae.getSource().equals(jbCancel)){
				ConferenceDialog.this.setVisible(false);
			}
		}
	}
}
