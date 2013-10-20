package state10OctoberAss2;

public class StayOpenImpl implements StateInterface{

	private Door door;

	public StayOpenImpl(Door door){
		this.door = door;
	}
	
	@Override
	public void click() {
		door.setState(new ClosingImpl(door));
	}

	@Override
	public void complete() {
	}

	@Override
	public void timeout() {
	}
	
	@Override
	public String toString(){
		return "StayOpen";
	}
}
