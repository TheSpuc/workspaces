package state10OctoberAss2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class OpenImpl implements StateInterface, ActionListener {

	private Door door;
	Timer t;

	public OpenImpl(Door door){
		this.door = door;
		t = new Timer(2000, this);
		t.start();
	}
	
	@Override
	public void click() {
		t.stop();
		door.setState(new StayOpenImpl(door));
	}

	@Override
	public void complete() {
	}

	@Override
	public void timeout() {
		door.setState(new ClosingImpl(door));
	}
	
	
	public void actionPerformed(ActionEvent e){
		t.stop();
		timeout();
	}
	
	@Override
	public String toString(){
		return "Open";
	}
}
