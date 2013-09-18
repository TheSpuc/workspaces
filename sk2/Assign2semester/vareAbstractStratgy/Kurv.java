package vareAbstractStratgy;

import java.util.ArrayList;

public class Kurv {
	
	private ArrayList<Vare> varer;
	
	public Kurv(){
		varer = new ArrayList<>();
	}
	
	public void addVare(Vare v){
		varer.add(v);
	}
	
	public void removeVare(Vare v){
		if(varer.contains(v)){
			varer.remove(v);
		}
	}
	
	public ArrayList<Vare> getVarer(){
		return new ArrayList<>(varer);
	}
	
	public double samletBrutto(){
		double result = 0;
		for(Vare v : varer){
			System.out.println(v); //print each 
			result += v.bruttoPris();
		}
		return result;
	}
}
