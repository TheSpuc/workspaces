package template3OctoberAss4;

import java.util.Iterator;

public interface Sekvens {

	   // Objektet obj er adderet bagest i sekvensen
	    public void add(Comparable obj);

	   //  objekterne fra s er adderet bagest i 
	   //       sekevensen, i samme r�kkef�lge som de er i S
	    public void addAll(Sekvens s);

	   // Krav: sekvensen er ikke tom
	   // Returnerer det f�rste objekt i sekvensen. 
	   //       Sekvensen �ndres ikke
	    public Comparable head();

	   // Returnerer om sekvensen er tom
	    public boolean isEmpty();

	   // Returnerer en iterator over sekvensen
	    public Iterator iterator();

	   // Krav: sekvensen er ikke tom
	   // Det f�rste objekt i sekvensen er fjernet fra
	   //       sekvensen
	    public void tail();

}
