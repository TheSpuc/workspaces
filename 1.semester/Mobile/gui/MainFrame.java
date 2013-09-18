package gui;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import service.Service;

public class MainFrame extends JFrame
{
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {
			System.out.println("Error setting look and feel");
		}
	}

	private  static MainFrame frame = new MainFrame();
	private  JPanel pnlContent;

	private JPanel pnlScreen, pnlButtons;
	private JButton btnHome;

	private MainPanel pnlMain;
	private TextsPanel pnlTexts;
	private CallsPanel pnlCalls;
	private ContactsPanel pnlContacts;


	private final Controller controller = new Controller();

	public static void main(String[] args)
	{
		frame.setTitle("Mobile Phone Demo");
		frame.setSize(325, 490);
		frame.setLocation(300, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);   
	}

	public MainFrame() {
		Service.createAndStoreSomeObjects();
		pnlContent = (JPanel) this.getContentPane();
		pnlContent.setLayout(null);

		pnlScreen = new JPanel();
		pnlContent.add(pnlScreen);
		pnlScreen.setSize(300, 380);
		pnlScreen.setLocation(10, 10);
		pnlScreen.setLayout(null);

		pnlButtons = new JPanel();
		pnlContent.add(pnlButtons);
		pnlButtons.setSize(300, 60);
		pnlButtons.setLocation(10, 400);
		pnlButtons.setLayout(null);

		btnHome = new JButton("Home");
		pnlButtons.add(btnHome);
		btnHome.setSize(70, 30);
		btnHome.setLocation(115, 10);
		btnHome.addActionListener(controller);

		//---------------------------------------------------------------------

		Color color = new Color(99, 184, 255); //steelblue

		pnlMain = new MainPanel(this);
		pnlMain.setName("Main");
		pnlMain.setBackground(color);
		pnlMain.setLayout(null);
		pnlScreen.add(pnlMain, "Main");


		pnlTexts = new TextsPanel(this);
		pnlTexts.setName("Texts");
		pnlScreen.add(pnlTexts, "Texts");
		pnlTexts.setBackground(color);
		pnlTexts.setLayout(null);


		pnlCalls = new CallsPanel(this);
		pnlCalls.setName("Calls");
		pnlScreen.add(pnlCalls, "Calls");
		pnlCalls.setBackground(color);
		pnlCalls.setLayout(null);


		pnlContacts = new ContactsPanel(this);
		pnlContacts.setName("Contacts");
		pnlScreen.add(pnlContacts, "Contacts");
		pnlContacts.setBackground(color);
		pnlContacts.setLayout(null);

		showPanel("Main");
	}

	public void showPanel(String name)
	{

		if(name.equals("Main")){
			pnlTexts.setVisible(false);
			pnlCalls.setVisible(false);
			pnlContacts.setVisible(false);
			pnlMain.setVisible(true);
		}
		else if (name.equals("Texts")){	
			pnlCalls.setVisible(false);
			pnlContacts.setVisible(false);
			pnlMain.setVisible(false);
			pnlTexts.setVisible(true);
		}

		else if (name.equals("Calls")){
			if(CallsPanel.isCall()){
				pnlContacts.setVisible(false);
				pnlMain.setVisible(false);
				pnlTexts.setVisible(false);
				pnlCalls.setVisible(true);
				pnlCalls.Update();

			}else{
				pnlContacts.setVisible(false);
				pnlMain.setVisible(false);
				pnlTexts.setVisible(false);
				pnlCalls.setVisible(true);
			}
		}

		else if (name.equals("Contacts")){
			pnlMain.setVisible(false);
			pnlTexts.setVisible(false);
			pnlCalls.setVisible(false);
			pnlContacts.setVisible(true);

		}		
	}

	private class Controller implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getSource() == btnHome) {
				MainFrame.this.showPanel("Main");
			}
		}
	}

}