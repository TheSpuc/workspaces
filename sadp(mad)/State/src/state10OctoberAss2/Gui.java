package state10OctoberAss2;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Gui extends JFrame{

	private Door d;
	private JButton click;
	private JLabel label;
	
	public static void main(String[] args){
		Gui g = new Gui();
		g.setVisible(true);
	}
	
	public Gui(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setName("Door");
		setLayout(new FlowLayout());
		setBounds(100, 100, 300, 200);
		d = new Door();
		d.addObserver(this);
		
		label = new JLabel();
		label.setText(d.getState().toString());
		add(label);
		
		
		click = new JButton("Click");
		click.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				d.click();
			}
		});
		click.setSize(100, 25);
		add(click);
	}
	
	public void setLabel(StateInterface state){
		label.setText(state.toString());
	}
}
