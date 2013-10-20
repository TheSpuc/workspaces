package state10OctoberAss2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class ClosingImpl implements StateInterface, ActionListener {

	private Door door;
	Timer t;

	public ClosingImpl(Door door){
		this.door = door;
		t = new Timer(4000, this);
		t.start();
	}
	
	@Override
	public void click() {
		t.stop();
		door.setState(new OpeningImple(door));
	}

	@Override
	public void complete() {
		door.setState(new ClosedImpl(door));
	}

	@Override
	public void timeout() {
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		t.stop();
		complete();
	}
	
	@Override
	public String toString(){
		return "Closing";
	}

}
