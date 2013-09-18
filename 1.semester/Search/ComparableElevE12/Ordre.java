
package ComparableElevE12;


public class Ordre implements Comparable<Ordre> {
	private int nr;
	private String type;
	/**
	 *
	 */
	public Ordre(int nr, String type){
		this.nr = nr;
		this.type = type;
	}
	public int getNr(){
		return nr;
	}
	public String getType(){
		return type;
	}
	public int compareTo(Ordre arg0) {
		Ordre ordreInd = (Ordre)arg0;
		return this.nr - ordreInd.getNr();
		
		//eller
//		if (this.nr < ordreInd.getNr()) 
//			return -1;
//		else if (nr > ordreInd.getNr())
//			return 1;
//		else 
//			return 0;
//		
	
	
	}
	public String toString(){
		return getNr() + "  " + getType();
	}

}
