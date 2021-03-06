package gui;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;


import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Kunde;
import service.Service;

public class KundeMainFrame extends JFrame {

	private JButton btnOpretKunde;
	private JButton btnOpdaterKunde;
	private JButton btnOpretBil;

	private JScrollPane scrollPane;
	private JList<Kunde> list;
	private JLabel lblKunde;
	private JTextField txfKundeNavn;
	private JLabel lblCpr;
	private JTextField txfCpr;
	private JLabel lblKaj;
	private JLabel lblTekst;
	private JCheckBox box;

	private Controller controller = new Controller();

	private BilDialog bilDialog;

	public KundeMainFrame() {

		// the frame's own attributtes...
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("Kajs biler");
		this.setLocation(50, 50);
		this.setSize(460, 460);

		// controls...
		btnOpretKunde = new JButton();
		btnOpretKunde.setLocation(40, 300);
		btnOpretKunde.setSize(150, 25);
		btnOpretKunde.setText("Opret kunde");
		this.add(btnOpretKunde);

		btnOpdaterKunde = new JButton();
		btnOpdaterKunde.setLocation(40, 340);
		btnOpdaterKunde.setSize(150, 25);
		btnOpdaterKunde.setText("Opdater kunde");
		this.add(btnOpdaterKunde);

		btnOpretBil = new JButton();
		btnOpretBil.setLocation(200, 300);
		btnOpretBil.setSize(150, 25);
		btnOpretBil.setText("Opret bil");
		this.add(btnOpretBil);

		// Liste
		list = new JList<Kunde>();
		list.setListData(Service.getAlleKunder().toArray(new Kunde[0]));
		// this.add(list); //hvis ej i scrollpane
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.addListSelectionListener(controller);

		// ScrollPane
		scrollPane = new JScrollPane(list); // så der kan scrolles i listen
		scrollPane.setLocation(20, 100);
		scrollPane.setSize(200, 190);
		this.add(scrollPane);

		lblKaj = new JLabel("Velkommen til Kajs biler");
		this.add(lblKaj);
		lblKaj.setLocation(10, 10);
		lblKaj.setSize(300, 25);

		lblKunde = new JLabel("Kunde:");
		this.add(lblKunde);
		lblKunde.setLocation(10, 40);
		lblKunde.setSize(100, 25);

		txfKundeNavn = new JTextField();
		this.add(txfKundeNavn);
		txfKundeNavn.setLocation(100, 40);
		txfKundeNavn.setSize(200, 25);

		lblCpr = new JLabel("Cpr:");
		this.add(lblCpr);
		lblCpr.setLocation(10, 70);
		lblCpr.setSize(100, 25);

		txfCpr = new JTextField();
		this.add(txfCpr);
		txfCpr.setLocation(100, 70);
		txfCpr.setSize(200, 25);

		// Checkbox
		box = new JCheckBox("VIP-kunde");
		this.add(box);
		box.setLocation(350, 70);
		box.setSize(100, 25);

		lblTekst = new JLabel();
		this.add(lblTekst);
		lblTekst.setLocation(10, 360);
		lblTekst.setSize(150, 25);

		bilDialog = new BilDialog();

		btnOpretKunde.addActionListener(controller);
		btnOpdaterKunde.addActionListener(controller);
		btnOpretBil.addActionListener(controller);
		box.addItemListener(controller);

	}

	// ActionPerformed
	private class Controller implements ActionListener, ListSelectionListener,
			ItemListener {
		private Kunde aktuelKunde = null;

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnOpretKunde) {
				String navn = txfKundeNavn.getText().trim();
				String cprNrStr = txfCpr.getText().trim();
				int cpr = -1;
				if (navn.length() > 0 && cprNrStr.length() > 0) {
					cpr = Integer.parseInt(txfCpr.getText());
					Kunde aktuelKunde = Service.createKunde(navn, cpr);
					txfCpr.setText("");
					txfKundeNavn.setText("");
					aktuelKunde = null;
					list.setListData(Service.getAlleKunder().toArray(new Kunde[0]));
				}
			} else if (e.getSource() == btnOpdaterKunde) {
				String navn = txfKundeNavn.getText().trim();
				String cprNrStr = txfCpr.getText().trim();
				int cpr = -1;
				if (navn.length() > 0 && cprNrStr.length() > 0
						&& aktuelKunde != null) {
					cpr = Integer.parseInt(txfCpr.getText());
					Service.updateKunde(aktuelKunde, cpr, navn);
					txfCpr.setText("");
					txfKundeNavn.setText("");
					list.setListData(Service.getAlleKunder().toArray(new Kunde[0]));
				}
			} else if (e.getSource() == btnOpretBil) {
				bilDialog = new BilDialog();
				bilDialog.setVisible(true);
				// bilDialog er åben
				if (bilDialog.modalResult) { // bilDialog er lukket
					lblTekst.setText("Bil er oprettet");
				} else
					lblTekst.setText(" ");
			}
		}

		public void valueChanged(ListSelectionEvent event) {
			if (list.isSelectionEmpty()) {

			} else {
				// Single selection
				aktuelKunde = (Kunde) list.getSelectedValue();
				
				txfKundeNavn.setText((aktuelKunde.getNavn()));
				txfCpr.setText("" + aktuelKunde.getCpr());
				
				//multiple selection
//				List<Kunde> kunder = list.getSelectedValuesList(); 
//				System.out.println(kunder);
//				txfKundeNavn.setText((kunder.get(0).getNavn()));
//				txfCpr.setText("" + kunder.get(0).getCpr());

			}

		}

		public void itemStateChanged(ItemEvent e) {
			if (box.isSelected()) {
				lblTekst.setText("Knden er en VIP-kunde");
			} else {
				lblTekst.setText(" ");
			}
		}
	}

}
