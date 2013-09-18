package singletonCalculator;

import javax.swing.JFrame;

public class TraficLight extends JFrame {
	
	private static TraficLight traficLight;
	
	private TraficLight(){
		
	}
	
	public static TraficLight getInstance(){
		if(traficLight == null){
			traficLight = new TraficLight();
		}
		return traficLight;
	}
}
