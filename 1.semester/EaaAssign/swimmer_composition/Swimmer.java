package swimmer_composition;

import java.util.ArrayList;

/**
 * Modellerer en svoemmer 
 */
public class Swimmer {
	private String name;
	private String club;
	private int year;
	private ArrayList<Double> times;

	/**
	 * Initialiserer en ny svoemmer med navn, aargang, klub og tider.
	 * Krav: aargang > 1900.
	 */   
	Swimmer(String name, String club, int year, ArrayList<Double> times){
		this.times = times;
		this.name = name;
		this.club = club;
		this.year = year;
	}

	/**
	 * Returnerer svoemmerens navn
	 * @return
	 */
	public String getName(){
		return name;
	}

	/**
	 * Returnerer svoemmerens aargang.
	 */
	public int getYear(){
		return year;
	}

	/**
	 * Returnerer svoemmerens klub.
	 */
	public String getClub(){
		return club;
	}


	/**
	 * Registrerer svoemmerens klub
	 * @param klub
	 */
	public void setClub(String club){
		this.club = club;
	}

	public ArrayList<Double> getList(){
		ArrayList<Double> newList = new ArrayList<Double>(times);
		return newList;
	}

	/**
	 * Returnerer den hurtigste tid svoemmeren har opnoeet
	 */
	public double bedsteTid(){
		double d = Double.MAX_VALUE;
		if(times.size() > 0){
			for(double dou : times){
				if(dou < d){
					d = dou;
				}
			}
		}
		return d;
	}

	/**
	 * Returnerer gennemsnittet af de tider svoemmeren har
	 * opnaaet
	 */
	public double gennemsnitAfTid(){
		double res = 0;
		if(times.size() > 0){
			for(double dou: times){
				res += dou;
			}
		}
		return res/times.size();
	}

	/**
	 * Slave maaden!
	 * @return
	 */
	public double snitUdenDaarligsteSlaveMaaden(){
		double low = Double.MIN_VALUE;
		double res = 0;
		if(times.size() > 0){
			for(double dou : times){
				if(dou > low){
					low = dou;
					res += dou;
				}else res +=dou;
			}
		}
		return (res-low)/(times.size()-1);
	}

	//	/**
	//	 * Returnerer gennemsnittet af de tider svoemmeren har
	//	 * opnaaet idet den langsomste tid ikke er medregnet
	//	 * Det er langsommere på grund af to gennemløb.
	//	 */
	//	public double snitUdenDaarligste(){
	//		ArrayList<Double> list = this.getList();
	//		double res = 0;
	//		Collections.sort(list);
	//
	//		for(int i = 0; i<list.size()-1; i++){
	//			res += list.get(i);
	//		}
	//		return res/(list.size()-1);
	//	}
}
