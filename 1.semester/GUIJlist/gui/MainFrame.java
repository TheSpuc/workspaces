package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainFrame extends JFrame {
	private JList<String> list;
	private JScrollPane scroll;
	private JTextField txtField;

	private Controller controller;

	public MainFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("GUIApp");
		this.setLocation(300, 200);
		this.setSize(400, 200);

		ArrayList<String> personer = new ArrayList<String>();
		personer.add("Jens");
		personer.add("Margrethe Mosb�k Dybdahl");
		// personer.add("Hanne");
		// personer.add("Per");
		// personer.add("Finn");
		// personer.add("Mike");
		//
		list = new JList<String>(personer.toArray((new String[0])));
		// list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		scroll = new JScrollPane(list);
		scroll.setLocation(25, 25);
		scroll.setSize(75, 100);
		this.add(scroll);

		txtField = new JTextField();
		txtField.setLocation(110, 25);
		txtField.setSize(200, 25);
		this.add(txtField);

		controller = new Controller();
		list.addListSelectionListener(controller);

	}

	private class Controller implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent event) {

			if (!list.isSelectionEmpty()) {
				// String s = list.getSelectedValue();
				// txtField.setText(s);
				List<String> strings = list.getSelectedValuesList();
				String result = "";
				for (int i = 0; i < strings.size(); i++) {
					result = result + strings.get(i) + " ";
				}
				txtField.setText(result);

			}

		}

	}
}
