package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.TextMessage;
import service.Service;

public class TextsPanel extends JPanel {
	private int keyState;
	private MainFrame frame;
	private JButton jbTextMessage, jbOutbox, btnCreate, btnRead, btnDelete, btnSend, jbSendText;
	private Controller controller = new Controller();
	private JList<TextMessage> inbox, outbox;
	private JTextField txfNumber;
	private JScrollPane scrInbox, scrOutbox, scrMessage;
	private JTextArea jtaMessage;

	public TextsPanel(MainFrame frame) {
		this.frame = frame;
		this.setSize(300, 400);
		this.setLocation(5, 10);

		jbTextMessage = new JButton("Inbox");
		jbTextMessage.setBounds(30, 140, 100, 70);
		jbTextMessage.addActionListener(controller);
		this.add(jbTextMessage);

		jbOutbox = new JButton("Outbox");
		jbOutbox.setBounds(170, 140, 100, 70);
		jbOutbox.addActionListener(controller);
		this.add(jbOutbox);
		
		inbox = new JList<TextMessage>(Service.getInbox().toArray(new TextMessage[0]));
		inbox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrInbox = new JScrollPane(inbox);
		scrInbox.setSize(260, 300);
		scrInbox.setLocation(15, 10);
		this.add(scrInbox);
		scrInbox.setVisible(false);
		
		outbox = new JList<TextMessage>(Service.getOutbox().toArray(new TextMessage[0]));
		outbox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrOutbox = new JScrollPane(outbox);
		scrOutbox.setSize(260, 300);
		scrOutbox.setLocation(15, 10);
		this.add(scrOutbox);
		scrOutbox.setVisible(false);

		btnCreate = new JButton("Create");
		this.add(btnCreate);
		btnCreate.setSize(65, 40);
		btnCreate.setLocation(40, 320);
		btnCreate.setVisible(false);
		btnCreate.addActionListener(controller);

		btnRead = new JButton("Read");
		this.add(btnRead);
		btnRead.setSize(65, 40);
		btnRead.setLocation(110, 320);
		btnRead.setVisible(false);
		btnRead.addActionListener(controller);

		btnDelete = new JButton("Delete");
		this.add(btnDelete);
		btnDelete.setSize(65, 40);
		btnDelete.setLocation(180, 320);
		btnDelete.setVisible(false);
		btnDelete.addActionListener(controller);
		
		btnSend = new JButton("Send");
		this.add(btnSend);
		btnSend.setSize(200, 40);
		btnSend.setLocation(50, 310);
		btnSend.setVisible(false);
		btnSend.addActionListener(controller);
		
		txfNumber = new JTextField("");
		this.add(txfNumber);
		txfNumber.setSize(240,20);
		txfNumber.setLocation(30, 20);
		txfNumber.setVisible(false);
		
		jtaMessage = new JTextArea();
		jtaMessage.setWrapStyleWord(true);
		jtaMessage.setLineWrap(true);
		scrMessage = new JScrollPane(jtaMessage);
		scrMessage.setBounds(30, 60, 240, 160);
		this.add(scrMessage);
		scrMessage.setVisible(false);
		
		jbSendText = new JButton("Send");
		jbSendText.setBounds(80, 210, 100, 70);
		jbSendText.addActionListener(controller);
		this.add(jbSendText);

	}

	private class Controller implements ActionListener {
		private void openInbox() {
			scrMessage.setVisible(false);
			jbTextMessage.setVisible(false);
			jbOutbox.setVisible(false);
			btnDelete.setVisible(true);
			btnRead.setVisible(true);
			btnCreate.setVisible(true);
			scrInbox.setVisible(true);
			jbSendText.setVisible(false);
		}

		private void openOutbox() {
			scrMessage.setVisible(false);
			jbTextMessage.setVisible(false);
			jbOutbox.setVisible(false);
			scrOutbox.setVisible(true);
			btnDelete.setVisible(true);
			btnDelete.setLocation(150, 320);
			btnRead.setVisible(true);
			btnRead.setLocation(80, 320);
			jbSendText.setVisible(false);
		}
		
		private void createTextmessage(){
			scrMessage.setVisible(true);
			btnDelete.setVisible(false);
			btnRead.setVisible(false);
			btnCreate.setVisible(false);
			scrInbox.setVisible(false);
			txfNumber.setVisible(true);
			btnSend.setVisible(true);
			jbSendText.setVisible(false);
			jbOutbox.setVisible(false);
			jbTextMessage.setVisible(false);
		}
		
		private void sendTextmessage(){
			scrMessage.setVisible(false);
			txfNumber.setVisible(false);
			btnSend.setVisible(false);
			jbOutbox.setVisible(true);
			jbTextMessage.setVisible(true);
			jbSendText.setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(jbTextMessage)) {
				openInbox();
			} else if (e.getSource().equals(jbOutbox)) {
				openOutbox();
			} else if (e.getSource().equals(btnCreate)){
				createTextmessage();
			} else if (e.getSource().equals(btnSend)){
				sendTextmessage();
			} else if(e.getSource().equals(jbSendText)){
				createTextmessage();
			}
		}
	}
}