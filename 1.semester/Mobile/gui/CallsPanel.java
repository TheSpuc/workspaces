package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import service.Service;

import model.Contact;
import model.Call;


public class CallsPanel extends JPanel {
	private MainFrame frame;
	private JLabel lblOutgoingCalls,lblIncomingCalls,lblTimer;
	private JComboBox<Contact> cbxContacts;
	private JList<Call> outgoing,incoming;
	private JScrollPane scrOutgoing,scrIncoming;
	private JTextField txfNumber;
	private JButton btnContact, btnRecall, btnStartCall, btnEndCall, btnCallLog,btnZero,btnSquare,btnStar,btnRemoveDigit,btnReturnToPhone,
				btnIncomingCalls,btnOutgoingCalls,btnDeleteOutgoingCall,btnReturnToLogMenu,
				btnDeleteIncomingCall,btnShowIncomingCallsFromContact, jbEndCall;
	private ArrayList<JButton> btnDigit=new ArrayList<JButton>();
	private Controller controller = new Controller();
	private DigitController digitCtrl =new DigitController();
	private static boolean call;
	private long startTime;

	public CallsPanel(MainFrame frame){

		this.frame = frame;
		this.setSize(300, 380);
		this.setLocation(10, 10);
		call=false;

		txfNumber=new JTextField();
		this.add(txfNumber);
		txfNumber.setSize(195,25);
		txfNumber.setLocation(50,10);

		int wh = 64; 
		int sep = 1;
		int x=50;
		int y=40;

		for(int i=1; i<10; i++){
			JButton digit = new JButton(""+i);
			digit.setBounds(x, y, wh, wh);
			digit.addActionListener(digitCtrl);
			btnDigit.add(digit);
			this.add(digit);
			if(i%3 == 0){
				x = 50;
				y += (wh+sep);
			}else {
				x += (wh+sep);
			}
		}

		btnStar=new JButton("*");
		this.add(btnStar);
		btnStar.setSize(64,64);
		btnStar.setLocation(50,235);
		btnStar.addActionListener(controller);

		btnZero=new JButton("0");
		this.add(btnZero);
		btnZero.setSize(64,64);
		btnZero.setLocation(115,235);
		btnZero.addActionListener(digitCtrl);

		btnSquare=new JButton("#");
		this.add(btnSquare);
		btnSquare.setSize(64,64);
		btnSquare.setLocation(180,235);
		btnSquare.addActionListener(controller);

		btnContact=new JButton("Contact");
		this.add(btnContact);
		btnContact.setSize(80,20);
		btnContact.setLocation(15,310);
		btnContact.addActionListener(controller);

		btnCallLog=new JButton("Call Log");
		this.add(btnCallLog);
		btnCallLog.setSize(80,20);
		btnCallLog.setLocation(100,310);
		btnCallLog.addActionListener(controller);

		btnRecall=new JButton("Recall");
		this.add(btnRecall);
		btnRecall.setSize(80,20);
		btnRecall.setLocation(185,310);
		btnRecall.addActionListener(controller);

		btnStartCall=new JButton("Start Call");
		this.add(btnStartCall);
		btnStartCall.setSize(80,20);
		btnStartCall.setLocation(15,340);
		btnStartCall.addActionListener(controller);

		btnEndCall=new JButton("End");
		this.add(btnEndCall);
		btnEndCall.setSize(80,20);
		btnEndCall.setLocation(100,340);
		btnEndCall.addActionListener(controller);

		btnRemoveDigit=new JButton("Delete Digit");
		this.add(btnRemoveDigit);
		btnRemoveDigit.setSize(100,20);
		btnRemoveDigit.setLocation(185,340);
		btnRemoveDigit.addActionListener(controller);

		//CallLogMenu:

		btnIncomingCalls = new JButton("Incoming");
		this.add(btnIncomingCalls);
		btnIncomingCalls.setSize(100,70);
		btnIncomingCalls.setLocation(30,140);
		btnIncomingCalls.setVisible(false);
		btnIncomingCalls.addActionListener(controller);

		btnOutgoingCalls = new JButton("Outgoing");
		this.add(btnOutgoingCalls);
		btnOutgoingCalls.setSize(100,70);
		btnOutgoingCalls.setLocation(170,140);
		btnOutgoingCalls.setVisible(false);
		btnOutgoingCalls.addActionListener(controller);

		btnReturnToPhone=new JButton("Return to Phone");
		this.add(btnReturnToPhone);
		btnReturnToPhone.setSize(120,20);
		btnReturnToPhone.setLocation(165,345);
		btnReturnToPhone.addActionListener(controller);
		btnReturnToPhone.setVisible(false);

		//Incoming Calls Menu:

		lblIncomingCalls=new JLabel("Incoming Calls:");
		this.add(lblIncomingCalls);
		lblIncomingCalls.setSize(80,20);
		lblIncomingCalls.setLocation(10,5);
		lblIncomingCalls.setVisible(false);

		cbxContacts = new JComboBox<Contact>();
		this.add(cbxContacts);
		cbxContacts.setLocation(10,30 );
		cbxContacts.setSize(180, 25);
		cbxContacts.setVisible(false);

		controller.fillCbxContacts();

		incoming = new JList<Call>(Service.getRecievedCalls().toArray(new Call[0]));
		incoming.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrIncoming = new JScrollPane(incoming);
		scrIncoming.setSize(260, 250);
		scrIncoming.setLocation(15, 60);
		this.add(scrIncoming);
		scrIncoming.setVisible(false);

		btnShowIncomingCallsFromContact = new JButton("Show Calls");
		this.add(btnShowIncomingCallsFromContact);
		btnShowIncomingCallsFromContact.setSize(85,20);
		btnShowIncomingCallsFromContact.setLocation(195,30);
		btnShowIncomingCallsFromContact.addActionListener(controller);
		btnShowIncomingCallsFromContact.setVisible(false);

		btnDeleteIncomingCall = new JButton("Delete Call");
		this.add(btnDeleteIncomingCall);
		btnDeleteIncomingCall.setSize(100,20);
		btnDeleteIncomingCall.setLocation(10,320);
		btnDeleteIncomingCall.addActionListener(controller);
		btnDeleteIncomingCall.setVisible(false);

		//Outgoing Calls Menu:

		lblOutgoingCalls = new JLabel("Outgoing Calls:");
		this.add(lblOutgoingCalls);
		lblOutgoingCalls.setSize(80,30);
		lblOutgoingCalls.setLocation(15,15);
		lblOutgoingCalls.setVisible(false);

		outgoing = new JList<Call>(Service.getOutgoingCalls().toArray(new Call[0]));
		outgoing.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrOutgoing = new JScrollPane(outgoing);
		scrOutgoing.setSize(260, 250);
		scrOutgoing.setLocation(15, 60);
		this.add(scrOutgoing);
		scrOutgoing.setVisible(false);

		btnDeleteOutgoingCall=new JButton("Delete Call");
		this.add(btnDeleteOutgoingCall);
		btnDeleteOutgoingCall.setSize(100,20);
		btnDeleteOutgoingCall.setLocation(10,320);
		btnDeleteOutgoingCall.addActionListener(controller);
		btnDeleteOutgoingCall.setVisible(false);

		btnReturnToLogMenu=new JButton("Return To CallLog menu");
		this.add(btnReturnToLogMenu);
		btnReturnToLogMenu.setSize(150,20);
		btnReturnToLogMenu.setLocation(10,345);
		btnReturnToLogMenu.addActionListener(controller);
		btnReturnToLogMenu.setVisible(false);

		//Timer:

		lblTimer = new JLabel();
		this.add(lblTimer);
		lblTimer.setSize(100,25);
		lblTimer.setLocation(20,20);
		lblTimer.setVisible(false);
		
		//End call button
		jbEndCall = new JButton("End");
		jbEndCall.setBounds(30, 60, 100, 25);
		jbEndCall.addActionListener(controller);
		jbEndCall.setVisible(false);
		this.add(jbEndCall);
	}

	private void showCallLogMenu(){
		txfNumber.setVisible(false);		
		for(JButton digit: btnDigit){
			digit.setVisible(false);
		}

		btnStar.setVisible(false);
		btnZero.setVisible(false);
		btnSquare.setVisible(false);
		btnContact.setVisible(false);
		btnCallLog.setVisible(false);
		btnRecall.setVisible(false);
		btnStartCall.setVisible(false);
		btnEndCall.setVisible(false);
		btnRemoveDigit.setVisible(false);
		btnIncomingCalls.setVisible(true);
		btnOutgoingCalls.setVisible(true);
		btnReturnToPhone.setVisible(true);
		lblIncomingCalls.setVisible(false);
		cbxContacts.setVisible(false);
		scrIncoming.setVisible(false);
		btnDeleteIncomingCall.setVisible(false);
		btnShowIncomingCallsFromContact.setVisible(false);
		lblOutgoingCalls.setVisible(false);
		scrOutgoing.setVisible(false);
		btnDeleteOutgoingCall.setVisible(false);
		btnReturnToLogMenu.setVisible(false);
		jbEndCall.setVisible(false);
		lblTimer.setVisible(false);
	}

	private void showOutgoingCallsMenu(){
		txfNumber.setVisible(false);		
		for(JButton digit: btnDigit){
			digit.setVisible(false);
		}

		btnStar.setVisible(false);
		btnZero.setVisible(false);
		btnSquare.setVisible(false);
		btnContact.setVisible(false);
		btnCallLog.setVisible(false);
		btnRecall.setVisible(false);
		btnStartCall.setVisible(false);
		btnEndCall.setVisible(false);
		btnRemoveDigit.setVisible(false);
		btnIncomingCalls.setVisible(false);
		btnOutgoingCalls.setVisible(false);
		lblIncomingCalls.setVisible(false);
		cbxContacts.setVisible(false);
		scrIncoming.setVisible(false);
		btnDeleteIncomingCall.setVisible(false);
		btnShowIncomingCallsFromContact.setVisible(false);
		btnReturnToPhone.setVisible(true);
		lblOutgoingCalls.setVisible(true);
		scrOutgoing.setVisible(true);
		btnDeleteOutgoingCall.setVisible(true);
		btnReturnToLogMenu.setVisible(true);
		jbEndCall.setVisible(false);
		lblTimer.setVisible(false);
	}

	private void showPhoneMenu(){
		txfNumber.setVisible(true);		
		for(JButton digit: btnDigit){
			digit.setVisible(true);
		}

		btnStar.setVisible(true);
		btnZero.setVisible(true);
		btnSquare.setVisible(true);
		btnContact.setVisible(true);
		btnCallLog.setVisible(true);
		btnRecall.setVisible(true);
		btnStartCall.setVisible(true);
		btnEndCall.setVisible(true);
		btnRemoveDigit.setVisible(true);
		btnIncomingCalls.setVisible(false);
		btnOutgoingCalls.setVisible(false);
		lblIncomingCalls.setVisible(false);
		cbxContacts.setVisible(false);
		scrIncoming.setVisible(false);
		btnDeleteIncomingCall.setVisible(false);
		btnShowIncomingCallsFromContact.setVisible(false);
		btnReturnToPhone.setVisible(false);
		lblOutgoingCalls.setVisible(false);
		scrOutgoing.setVisible(false);
		btnDeleteOutgoingCall.setVisible(false);
		btnReturnToLogMenu.setVisible(false);
		jbEndCall.setVisible(false);
		lblTimer.setVisible(false);
	}
	private void showIncomingCallsMenu(){
		txfNumber.setVisible(false);		
		for(JButton digit: btnDigit){
			digit.setVisible(false);
		}
		btnStar.setVisible(false);
		btnZero.setVisible(false);
		btnSquare.setVisible(false);
		btnContact.setVisible(false);
		btnCallLog.setVisible(false);
		btnRecall.setVisible(false);
		btnStartCall.setVisible(false);
		btnEndCall.setVisible(false);
		btnRemoveDigit.setVisible(false);
		btnIncomingCalls.setVisible(false);
		btnOutgoingCalls.setVisible(false);
		lblIncomingCalls.setVisible(true);
		cbxContacts.setVisible(true);
		scrIncoming.setVisible(true);
		btnDeleteIncomingCall.setVisible(true);
		btnShowIncomingCallsFromContact.setVisible(true);
		btnReturnToPhone.setVisible(true);
		lblOutgoingCalls.setVisible(false);
		scrOutgoing.setVisible(false);
		btnDeleteOutgoingCall.setVisible(false);
		btnReturnToLogMenu.setVisible(true);
		lblTimer.setVisible(false);
		jbEndCall.setVisible(false);
	}

	private void showActiveCall(){
		txfNumber.setVisible(false);		
		for(JButton digit: btnDigit){
			digit.setVisible(false);
		}
		btnStar.setVisible(false);
		btnZero.setVisible(false);
		btnSquare.setVisible(false);
		btnContact.setVisible(false);
		btnCallLog.setVisible(false);
		btnRecall.setVisible(false);
		btnStartCall.setVisible(false);
		btnEndCall.setVisible(false);
		btnRemoveDigit.setVisible(false);
		btnIncomingCalls.setVisible(false);
		btnOutgoingCalls.setVisible(false);
		lblIncomingCalls.setVisible(false);
		cbxContacts.setVisible(false);
		scrIncoming.setVisible(false);
		btnDeleteIncomingCall.setVisible(false);
		btnShowIncomingCallsFromContact.setVisible(false);
		btnReturnToPhone.setVisible(false);
		lblOutgoingCalls.setVisible(false);
		scrOutgoing.setVisible(false);
		btnDeleteOutgoingCall.setVisible(false);
		btnReturnToLogMenu.setVisible(false);
		lblTimer.setVisible(true);
		jbEndCall.setVisible(true);
	}

	private void timeTest(){
		startTime = System.currentTimeMillis();
		new Thread(new Runnable()
		{
			public void run() 
			{ try 
			{
				updateTime(); 
			} 
			catch (Exception ie) 
			{ }
			}
		}).start();
	}

	private void updateTime(){
		try
		{
			while(call)
			{
				lblTimer.setText(getTimeElapsed());
				Thread.currentThread();
				Thread.sleep(1000);
			}
		}
		catch (Exception e)
		{
			System.out.println("Exception in Thread Sleep : "+e);
		}
	}

	private String getTimeElapsed(){
		long elapsedTime = System.currentTimeMillis() - startTime;
		elapsedTime = elapsedTime / 1000;

		String seconds = Integer.toString((int)(elapsedTime % 60));
		String minutes = Integer.toString((int)((elapsedTime % 3600) / 60));
		String hours = Integer.toString((int)(elapsedTime / 3600));

		if (seconds.length() < 2)
			seconds = "0" + seconds;

		if (minutes.length() < 2)
			minutes = "0" + minutes;

		if (hours.length() < 2)
			hours = "0" + hours;

		return hours+":"+minutes+":"+seconds;
	}


	public static boolean isCall(){
		return call;
	}
	public static void setCall(boolean setCall){
		call = setCall;
	}
	public void Update(){
		txfNumber.setText(ContactsPanel.getCurrentContact().getNumber());
	}
	private static String addDigit(char digit){
		int number=0;
		String num = Integer.toString(9 * number + (digit - '0'));
		return num;
	}

	private class DigitController implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton source = (JButton) e.getSource();
			char digit = source.getText().charAt(0);
			String insertNumber = txfNumber.getText().trim() + addDigit(digit);
			txfNumber.setText(insertNumber);	
		}
	}
	private class Controller implements ActionListener {
		public void fillCbxContacts() {
			DefaultComboBoxModel cbxContactsModel = new DefaultComboBoxModel(Service.getContacts().toArray());
			cbxContactsModel.insertElementAt(new Contact("","","",""), 0);
			cbxContacts.setModel(cbxContactsModel);
		}
		public void actionPerformed(ActionEvent e) {
			if(e.getSource().equals(btnRemoveDigit)){

				String temp=txfNumber.getText();
				if(temp.length()!=0){
					txfNumber.setText(temp.substring(0, temp.length()-1));
				}
			}else if(e.getSource().equals(btnStar)){
				String InsertStar=txfNumber.getText()+btnStar.getText();
				txfNumber.setText(InsertStar);

			}else if(e.getSource().equals(btnSquare)){
				String InsertSquare=txfNumber.getText()+btnSquare.getText();
				txfNumber.setText(InsertSquare);

			}else if(e.getSource().equals(btnContact)){
				frame.showPanel("Contacts");

			}else if(e.getSource().equals(btnRecall)){
				Service.createCall(Service.getLastCalledContact());
				showActiveCall();
				setCall(true);
				timeTest();
			}else if(e.getSource().equals(btnStartCall)){
				Service.createCall(ContactsPanel.getCurrentContact());
				showActiveCall();
				setCall(true);
				timeTest();
			}else if(e.getSource().equals(btnEndCall)){

			}else if(e.getSource().equals(btnCallLog)){
				showCallLogMenu();
			}else if(e.getSource().equals(btnOutgoingCalls)){
				showOutgoingCallsMenu();
				outgoing.setListData(Service.getOutgoingCalls().toArray(new Call[0]));
			}else if(e.getSource().equals(btnDeleteOutgoingCall)){
				Service.deleteOutgoingCall(outgoing.getSelectedValue());
				outgoing.setListData(Service.getOutgoingCalls().toArray(new Call[0]));
			}else if(e.getSource().equals(btnReturnToLogMenu)){
				showCallLogMenu();

			}else if(e.getSource().equals(btnReturnToPhone)){
				showPhoneMenu();

			}else if(e.getSource().equals(btnIncomingCalls)){
				showIncomingCallsMenu();

			}else if(e.getSource().equals(btnShowIncomingCallsFromContact)){
				incoming.setListData(Service.getRecievedCalls().toArray(new Call[0]));
			}else if(e.getSource().equals(btnDeleteIncomingCall)){
				Service.deleteRecievedCall(incoming.getSelectedValue());
				incoming.setListData(Service.getRecievedCalls().toArray(new Call[0]));
			}else if(e.getSource().equals(jbEndCall)){
				setCall(false);
				showPhoneMenu();
			}

		}
	}
}