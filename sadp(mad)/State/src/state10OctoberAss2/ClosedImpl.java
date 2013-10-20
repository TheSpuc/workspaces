package state10OctoberAss2;

public class ClosedImpl implements StateInterface {

	private Door door;
	
	public ClosedImpl(Door door){
		this.door = door;
	}
	
	@Override
	public void click() {
		door.setState(new OpeningImple(door));
	}

	@Override
	public void complete() {
	}

	@Override
	public void timeout() {
	}
	
	@Override
	public String toString(){
		return "Closed";
	}

}
