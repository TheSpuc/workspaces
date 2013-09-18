package dao;

import java.util.ArrayList;

import model.Kasse;
import model.Kunde;

public class Dao {
	
	private static ArrayList<Kunde> kunder = new ArrayList<>();
	private static ArrayList<Kasse> kasser = new ArrayList<>();
	
	public static ArrayList<Kunde> getKunder(){
		return new ArrayList<>(kunder);
	}
	
	public static void addKunde(Kunde kunde){
		if(!kunder.contains(kunde)){
			kunder.add(kunde);
		}
	}
	
	public static void removeKunde(Kunde kunde){
		if(kunder.contains(kunde)){
			kunder.remove(kunde);
		}
	}
	
	public static ArrayList<Kasse> getKasser(){
		return new ArrayList<>(kasser);
	}
	
	public static void addKasse(Kasse kasse){
		if(!kasser.contains(kasse)){
			kasser.add(kasse);
		}
	}
	
	public static void removeKasse(Kasse kasse){
		if(kasser.contains(kasse)){
			kasser.remove(kasse);
		}
	}
	
	
}
