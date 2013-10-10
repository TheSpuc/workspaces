package state10OctoberAss2;

public class Door {
	
	private StateInterface state;
	
	public Door(){
		state = new ClosedImpl(this);
	}
}
