package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import service.Service;
import specification.DateUtil;

import model.Karakter;
import model.Restaurant;

/**
 * Gui for SK January 2013
 * @author Mark Medum Bundgaard
 *
 */

public class MainFrame extends JFrame {

	private Controller controller;
	private JList<Restaurant> jlRestaurant;
	private JList<String> jlRestaurantTransport;
	private JScrollPane jspRestaurant, jspRestaurantTransport;
	private JTextField jtfRestaurant, jtfStjerner, jtfTransport;
	private JButton jbOpret, jbOpdater, jbVis, jbSort, jbFiveStar;
	private JLabel jlRestaurantNavn, jlStjerner, jlTransport, jlRestauranter;

	public static void main(String[] args){
		Service.createSomeObjects();
		MainFrame frame = new MainFrame();
		frame.setVisible(true);
	}

	public MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("Title");
		this.setLocation(100, 100);
		this.setSize(600, 500);
		controller = new Controller();

		jlRestauranter = new JLabel("Restauranter:");
		jlRestauranter.setBounds(10, 10, 100, 25);
		this.add(jlRestauranter);

		jlRestaurant = new JList<Restaurant>(Service.getRestauranter().toArray(new Restaurant[0]));
		jlRestaurant.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlRestaurant.addListSelectionListener(controller);
		jspRestaurant = new JScrollPane(jlRestaurant);
		jspRestaurant.setBounds(10, 35, 150, 200);
		this.add(jspRestaurant);

		jbSort = new JButton("Sort");
		jbSort.setBounds(10, 250, 100, 25);
		jbSort.addActionListener(controller);
		this.add(jbSort);

		jbFiveStar = new JButton("FiveStar");
		jbFiveStar.setBounds(10, 280, 100, 25);
		jbFiveStar.addActionListener(controller);
		this.add(jbFiveStar);

		jlRestaurantTransport = new JList<String>();
		jlRestaurantTransport.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jlRestaurantTransport.addListSelectionListener(controller);
		jspRestaurantTransport = new JScrollPane(jlRestaurantTransport);
		jspRestaurantTransport.setBounds(400, 250, 150, 200);
		this.add(jspRestaurantTransport);

		jlRestaurantNavn = new JLabel("Restaurant: ");
		jlRestaurantNavn.setBounds(300, 15, 100, 25);
		this.add(jlRestaurantNavn);

		jtfRestaurant = new JTextField();
		jtfRestaurant.setBounds(380, 15, 200, 25);
		this.add(jtfRestaurant);

		jlStjerner = new JLabel("Stjerner: ");
		jlStjerner.setBounds(300, 45, 100, 25);
		this.add(jlStjerner);

		jtfStjerner = new JTextField();
		jtfStjerner.setBounds(380, 45, 80, 25);
		this.add(jtfStjerner);

		jbOpret = new JButton("Opret");
		jbOpret.setBounds(380, 100, 80, 25);
		jbOpret.addActionListener(controller);
		this.add(jbOpret);

		jbOpdater = new JButton("Opdater");
		jbOpdater.setBounds(470, 100, 80, 25);
		jbOpdater.addActionListener(controller);
		this.add(jbOpdater);

		jlTransport = new JLabel("Transport for dag: ");
		jlTransport.setBounds(300, 150, 200, 25);
		this.add(jlTransport);

		jtfTransport = new JTextField();
		jtfTransport.setBounds(420, 150, 100, 25);
		this.add(jtfTransport);

		jbVis = new JButton("Vis");
		jbVis.setBounds(520, 150, 80, 25);
		jbVis.addActionListener(controller);
		this.add(jbVis);
	}

	private void setListData(){
		jlRestaurant.setListData(Service.getRestauranter().toArray(new Restaurant[0]));
	}

	private class Controller implements ActionListener, ListSelectionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(jbOpret)){
				String restaurantNavn = jtfRestaurant.getText().trim();
				Karakter stjerner = Karakter.valueOf(jtfStjerner.getText().trim());
				if(!restaurantNavn.isEmpty()){
					Service.createRestaurant(restaurantNavn, stjerner);
					setListData();
				}
			}else if(e.getSource().equals(jbOpdater)){
				Restaurant restaurant = jlRestaurant.getSelectedValue();
				String restaurantNavn = jtfRestaurant.getText().trim();
				Karakter stjerner = Karakter.valueOf(jtfStjerner.getText().trim());
				if(!restaurantNavn.isEmpty() && restaurant != null){
					Service.updateRestaurant(restaurant, restaurantNavn, stjerner);
					setListData();
				}
			}else if(e.getSource().equals(jbSort)){
				jlRestaurant.setListData(Service.sortRestauranter().toArray(new Restaurant[0]));
			}else if(e.getSource().equals(jbVis)){
				if(jlRestaurant.getSelectedValue() != null){
					String date = jtfTransport.getText().trim();
					if(!date.isEmpty() && date !=null){
						Date d = DateUtil.createDate(date);
						jlRestaurantTransport.setListData(Service.getTransportForRestaurant(jlRestaurant.getSelectedValue(), d).toArray(new String[0]));
					}
				}
			}else if(e.getSource().equals(jbFiveStar)){
				jlRestaurant.setListData(Service.femStjernerRestauranter().toArray(new Restaurant[0]));
			}
		}

		public void valueChanged(ListSelectionEvent e) {
			if(e.getSource().equals(jlRestaurant)){
				if(e.getValueIsAdjusting()){
					Restaurant r = jlRestaurant.getSelectedValue();
					jtfRestaurant.setText(r.getNavn());
					jtfStjerner.setText(r.getStjerner().toString());
				}
			}
		}
	}
}

