package farvevalg;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SubFrame1 extends JFrame implements Observer {

	JTextField txt = new JTextField();
	JButton btn1 = new JButton("subscribe");
	JButton btn2 = new JButton("unsubscribe");
	private Subject subject;

	public SubFrame1() {
		this.subject = GUIApp.mainFrame;
		// the frame's own attributes...
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		this.setTitle("subFrame1");
		this.setLocation(200,80);
		this.setSize(250,150);

		// controls...
		//Create the radio buttons.
		txt.setText("Vicki");
		txt.setBackground(Color.WHITE);
		this.add(txt);

		btn1.setActionCommand("1A");
		btn1.addActionListener(new Click());
		this.add(btn1);

		btn2.setActionCommand("1B");
		btn2.addActionListener(new Click());
		this.add(btn2);

	}

	// ActionPerformed
	private class Click implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==btn1){
				System.out.println("Subframe 1, knap1");
				subject.addObserver(SubFrame1.this);
			}
			if (e.getSource()==btn2){
				System.out.println("Subframe 1, knap2");
				subject.removeObserver(SubFrame1.this);
			}
		}
	}

	@Override
	public void update(Subject s) {
		if(s instanceof MainFrame){
			txt.setBackground(((MainFrame) s).getColor());
		}

	}
}
