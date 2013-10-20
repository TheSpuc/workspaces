package state10OctoberAss2;

public class Door {
	
	private StateInterface state;
	private Gui gui;
	
	public Door(){
		state = new ClosedImpl(this);
	}
	
	public void setState(StateInterface state){
		this.state = state;
		notifyObserver();
	}
	
	public StateInterface getState(){
		return state;
	}
	
	public void click(){
		state.click();
	}
	
	public void addObserver(Gui gui){
		this.gui = gui;
	}
	
	private void notifyObserver(){
		gui.setLabel(state);
	}
}
