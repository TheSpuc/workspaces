package dialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MainFrame extends JFrame {
	private JList<String> list;
	private JScrollPane scroll;
	private JTextField txtField;
	private JButton but;

	private DialogGui dialogGUI;

	private Controller controller;

	public MainFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("GUIApp");
		this.setLocation(300, 200);
		this.setSize(400, 200);

		ArrayList<String> personer = new ArrayList<String>();
		personer.add("Jens");
		personer.add("Ole");
		personer.add("Hanne");
		personer.add("Per");
		personer.add("Finn");
		personer.add("Mike");

		list = new JList<String>(personer.toArray((new String[0])));
		// list = new JList<String>();
		// list.setListData(personer.toArray((new String[0])));

		scroll = new JScrollPane(list);
		scroll.setLocation(25, 25);
		scroll.setSize(75, 100);
		this.add(scroll);

		txtField = new JTextField();
		txtField.setLocation(110, 25);
		txtField.setSize(75, 25);
		this.add(txtField);

		controller = new Controller();
		list.addListSelectionListener(controller);

		but = new JButton("Ny dialog");
		but.setLocation(110, 50);
		but.setSize(100, 25);
		this.add(but);
		but.addActionListener(controller);

		dialogGUI = new DialogGui("En lille dialog");
	}

	private class Controller implements ListSelectionListener, ActionListener {

		public void valueChanged(ListSelectionEvent event) {

			if (!list.isSelectionEmpty()) {
				String s = list.getSelectedValue();
				txtField.setText(s);

			}

		}

		@Override
		public void actionPerformed(ActionEvent event) {
			dialogGUI.setVisible(true);
			if (dialogGUI.modalResult) {
				txtField.setText("OK");
			} else {
				txtField.setText("Cancel");
			}

		}

	}
}
