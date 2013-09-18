package biblioTest;

import java.util.Date;

public class Bibliotek {


	/**
	 * Returnerer størrelsen af bøden beregnet i henhold til skemaet
	 * ovenfor 
	 * krav: beregnetDato og faktiskDato indeholder lovlige datoer og
	 *       beregnetDato < faktiskDato
	 *      (beregnetDato er forventet afleveringsdato og
	 *       faktiskDato er den dag bogen blev afleveret; voksen er
	 *       sand, hvis det er en voksen og falsk ellers)
	 */


	public int beregnBoede(Date beregnetDato, Date faktiskDato, boolean voksen){
		int praemie = 0;
		int daysDiff = DateUtil.daysDiff(beregnetDato, faktiskDato);
		if(daysDiff >= 1 && daysDiff <= 7){
			praemie = 10;
		}else if(daysDiff >= 8 && daysDiff <= 14){
			praemie = 30;
		}else if(daysDiff >= 15){
			praemie = 45;
		}
		if(voksen){
			praemie *=2;
		}
		return praemie;
	}
}
