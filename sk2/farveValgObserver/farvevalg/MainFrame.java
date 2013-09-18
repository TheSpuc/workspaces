package farvevalg;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.*;

public class MainFrame extends JFrame implements Subject {

	JRadioButton redButton = new JRadioButton("red");
	JRadioButton greenButton = new JRadioButton("green");
	JRadioButton blueButton = new JRadioButton("blue");
	private Set<Observer> observers;
	private Color color;

	public MainFrame() {
		//init Set and Color
		observers = new HashSet<>();
		color = Color.WHITE;
		// the frame's own attributes...
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new GridLayout(3,1));
		this.setTitle("MyGUIApp");
		this.setLocation(50,50);
		this.setSize(180,150);

		// controls...
		//Create the radio buttons.
		redButton.setMnemonic(KeyEvent.VK_R);
		redButton.setActionCommand("red");
		redButton.setSelected(true);

		greenButton.setMnemonic(KeyEvent.VK_G);
		greenButton.setActionCommand("green");

		blueButton.setMnemonic(KeyEvent.VK_B);
		blueButton.setActionCommand("blue");

		//Group the radio buttons.
		ButtonGroup group = new ButtonGroup();
		group.add(redButton);
		group.add(greenButton);
		group.add(blueButton);


		this.add(redButton);
		this.add(greenButton);
		this.add(blueButton);
		//Register a listener for the radio buttons.
		redButton.addActionListener(new Click());
		greenButton.addActionListener(new Click());
		blueButton.addActionListener(new Click());
	}

	// ActionPerformed
	private class Click implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==redButton){
				System.out.println("red");
				setColor(Color.RED);
			}
			if (e.getSource()==greenButton){
				System.out.println("green");
				setColor(Color.GREEN);
			}
			if (e.getSource()==blueButton){
				System.out.println("blue");
				setColor(Color.BLUE);
			}
			notifyObservers();
		}
	}

	public Color getColor(){
		return color;
	}

	public void setColor(Color color){
		this.color = color;
	}

	@Override
	public void addObserver(Observer o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObservers() {
		Iterator<Observer> it = observers.iterator();
		while(it.hasNext()){
			it.next().update(this);
		}
	}
}
