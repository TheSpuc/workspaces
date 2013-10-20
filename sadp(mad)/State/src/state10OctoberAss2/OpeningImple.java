package state10OctoberAss2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class OpeningImple implements StateInterface, ActionListener {

	private Door door;
	Timer t;

	public OpeningImple(Door door){
		this.door = door;
		t = new Timer(3000, this);
		t.start();
	}

	@Override
	public void click() {
		t.stop();
		door.setState(new ClosingImpl(door));
	}

	@Override
	public void complete() {
		door.setState(new OpenImpl(door));
	}

	@Override
	public void timeout() {
	}
	
	public void actionPerformed(ActionEvent e){
		t.stop();
		complete();
	}
	
	@Override
	public String toString(){
		return "Opening";
	}

}
