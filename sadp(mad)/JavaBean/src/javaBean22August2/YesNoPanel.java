package javaBean22August2;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class YesNoPanel extends JPanel {
	
	private JButton yes, no;
	private JTextField txfText;
	private JPanel south;
	private List<AnswerListener> listeners;

	public YesNoPanel(){
		listeners = new ArrayList<>();
		setLayout(new BorderLayout());

		yes = new JButton("Yes");
		yes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fireAnswerEvent(true, new AnswerEvent(this));
			}
		});

		no = new JButton("No");
		no.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				fireAnswerEvent(false, new AnswerEvent(this));
			}
		});


		south = new JPanel(new FlowLayout());
		south.add(yes);
		south.add(no);
		add(south, BorderLayout.SOUTH);

		txfText = new JTextField();
		txfText.setSize(300, 25);
		add(txfText, BorderLayout.NORTH);
	}

	public void setQuestion(String text){
		txfText.setText(text);
	}

	public String getQuestion(){
		return txfText.getText();
	}
	
	public void setYesText(String text){
		yes.setText(text);
	}
	
	public String getYesText(){
		return yes.getText();
	}
	
	public void setNoText(String text){
		no.setText(text);
	}
	
	public String getNoText(){
		return no.getText();
	}


	/**
	 * For adding a listener to the AnswerEvent
	 * @param anw
	 */
	public void addAnswerListener(AnswerListener anw){
		listeners.add(anw);
	}

	/**
	 * For removing a listener to the AnswerEvent
	 * @param anw
	 */
	public void removeAnswerListener(AnswerListener anw){
		listeners.remove(anw);
	}

	private void fireAnswerEvent(boolean yes, AnswerEvent ae){
		List<AnswerListener> list;
		synchronized (this) {
			list = new ArrayList<>(listeners);
		}
		for(AnswerListener l : list){
			if(yes){
				l.yes(ae);
			}else l.no(ae);
		}
	}
}
