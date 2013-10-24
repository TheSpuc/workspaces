package iterator24OctoberAss1;

import java.util.Iterator;

public interface IndexListI<E> {
	/**
	 * @return returnerer elementet på plads nummer i i listen
	 * @throws IndexOutOfBoundsEception
	 *             hvis i<0 eller i>= antal elementer i listen
	 */
	public E get(int i);

	/**
	 * @return fjerner og returnerer elementet på plads nummer i i listen
	 * @throws IndexOutOfBoundsEception
	 *             hvis i<0 eller i>= antal elementer i listen
	 */
	public E remove(int i);

	/**
	 * @return Indsætter elementet e på indeks plads i i listen. Elementerne på
	 *         plads i og frem, får indeks plads en højere
	 * @throws IndexOutOfBoundsEception
	 *             hvis i<0 eller i> antal elementer i listen
	 */
	public void add(int i, E e);

	/**
	 * @return Overskriver elementet på indeks plads i i listen med elementet
	 *         E.. Det element der oprindeligt stod på indeks plads i
	 *         returneres.
	 * @throws IndexOutOfBoundsEception
	 *             hvis i<0 eller i>= antal elementer i listen
	 */
	public E set(int i, E e);

	/**
	 * 
	 * @return Returnerer antallet af elementer i listen
	 */
	public int size();
	
	/**
	 * 
	 * @return Returnerer om listen er tom
	 */
	public boolean isEmpty();
	
	
	public Iterator<E> iterator();
}
