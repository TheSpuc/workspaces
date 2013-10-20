package javaBean10October;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Kamp {
	
	private char raekke;
	private int nr;
	private Spiller vinder;
	private Spiller[] deltagere;
	
	private PropertyChangeSupport propChange;
	
	//Out event
	private List<EliteVinderListener> eliteListener;
	
	public Kamp(char raekke, int nr, Spiller deltager1, Spiller deltager2){
		this.raekke = raekke;
		this.nr = nr;
		vinder = null;
		deltagere = new Spiller[] {deltager1, deltager2};
		
		propChange = new PropertyChangeSupport(this);
		
		eliteListener = new ArrayList<>();
	}
	
	public int getNr(){
		return nr;
	}
	
	public void setNr(int nr){
		int oldValue = this.nr;
		this.nr = nr;
		propChange.firePropertyChange("nr", oldValue, nr);
	}
	
	public char getRaekke(){
		return raekke;
	}
	
	public void setRaekke(char raekke){
		this.raekke = raekke;
	}
	
	public void setVinder(Spiller spiller){
		vinder = spiller;
		//Firing the event if this fight is a E fight
		if(raekke == 'E'){
			fireEliteVinderEvent();
		}
	}
	
	public Spiller getVinder(){
		return vinder;
	}
	
	public Spiller[] getDeltagere(){
		Spiller[] result = new Spiller[2];
		System.arraycopy(deltagere, 0, result, 0, deltagere.length);
		return result;
	}
	
	
	//Adding and removing propertyChangeListeners, when the bound 
	//property changes it will notify all listeners.
	
	public void addPropertyChangeListener(PropertyChangeListener l){
		propChange.addPropertyChangeListener(l);;
	}
	
	public void removePropertyChangeListener(PropertyChangeListener l){
		propChange.removePropertyChangeListener(l);
	}
	
	//Adding and removing out own listeners, when the specific 
	//property changes it will notify all listeners.
	//Synchronized because there is a possibility alot of ppl calls it.
	
	public synchronized void addEliteVinderListener(EliteVinderListener l){
		eliteListener.add(l);
	}
	
	public synchronized void removeEliteVinderListener(EliteVinderListener l){
		eliteListener.remove(l);
	}
	
	private void fireEliteVinderEvent(){
		List<EliteVinderListener> listeners;
		synchronized (this) {
			listeners = new ArrayList<>(eliteListener);
		}
		
		for(EliteVinderListener l : listeners){
			l.eliteVinder(new EliteVinderEvent(this));
		}
	}
	
	
	@Override
	public String toString(){
		return "Kamp " + nr + " " + raekke;
	}
}
