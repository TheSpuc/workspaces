package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import service.Service;

import model.Bil;
import dao.Dao;

public class BilDialog extends JDialog {

	public boolean modalResult; // bem�rk at den er public, udtrykker om der er
								// trykket
								// p� den ene eller den anden knap for at lukke
								// dialogen

	private JButton luk;
	private JButton opretBil;

	private JScrollPane scrollPane;
	private JList<Bil> list;
	private JLabel lblMaerke;
	private JTextField txfMaerke;
	private JLabel lblRegnr;
	private JTextField txfRegnr;
	// der er flere felter p� en bil!!

	private Controller controller = new Controller();

	public BilDialog() {

		// Specielt for en JDialog
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.setModal(true); // g�res modal, kan kun arbejde her indtil dialogen
								// lukkes

		this.setLayout(null);
		this.setTitle("Bil");
		this.setLocation(150, 150);
		this.setSize(360, 360);

		// controls...
		opretBil = new JButton();
		opretBil.setLocation(40, 255);
		opretBil.setSize(100, 25);
		opretBil.setText("Opret bil");
		this.add(opretBil);

		luk = new JButton();
		luk.setLocation(200, 255);
		luk.setSize(100, 25);
		luk.setText("Luk");
		this.add(luk);

		list = new JList<Bil>();
		list.setListData(Dao.getAlleBiler().toArray(new Bil[0]));

		scrollPane = new JScrollPane(list); // s� der kan scrolles i listen
		scrollPane.setLocation(20, 100);
		scrollPane.setSize(200, 150);
		this.add(scrollPane);

		lblMaerke = new JLabel("Maerke:");
		this.add(lblMaerke);
		lblMaerke.setLocation(10, 40);
		lblMaerke.setSize(100, 25);

		txfMaerke = new JTextField();
		this.add(txfMaerke);
		txfMaerke.setLocation(100, 40);
		txfMaerke.setSize(200, 25);

		lblRegnr = new JLabel("Regnr:");
		this.add(lblRegnr);
		lblRegnr.setLocation(10, 70);
		lblRegnr.setSize(100, 25);

		txfRegnr = new JTextField();
		this.add(txfRegnr);
		txfRegnr.setLocation(100, 70);
		txfRegnr.setSize(200, 25);

		opretBil.addActionListener(controller);
		luk.addActionListener(controller);

	}

	// ActionPerformed
	private class Controller implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == opretBil) {
				String maerke = txfMaerke.getText().trim();
				String regNrTxt = txfRegnr.getText().trim();
				int regnr = -1;
				if (maerke.length() > 0 && regNrTxt.length() > 0) {
					regnr = Integer.parseInt(regNrTxt);
					Bil bil = Service.createBil(regnr, maerke);
					list.setListData(Dao.getAlleBiler().toArray(new Bil[0]));
					modalResult = true;
					BilDialog.this.setVisible(false); // pga. vi er i en indre
														// klasse
				}
			}
			if (e.getSource() == luk) {
				modalResult = false;
				BilDialog.this.setVisible(false);

			}
		}
	}

}
