package hashing13NovemberAss1;

import java.util.Iterator;

public interface Dictionary<K, V> {

	/**
	 * Returnerer elementet hoerende til noeglen k, hvis noeglen ikke findes
	 * returneres null
	 * 
	 * @return objektet med noegle key
	 * @param key
	 *            noeglen elementet skal findes til
	 */
	public V get(K key);

	/**
	 * Returnerer om dictionary er tom
	 * 
	 * @return om map'en er tom
	 */
	public boolean isEmpty();

	/**
	 * Inds???tter objektet value med noegle key I dictionary. Hvis key allerede
	 * eksisterer overskrives value hoerende til key vaerdi og den gamle value
	 * returneres. Hvis key ikke er der returneres null. Hverken key eller value
	 * maa vaere null
	 * 
	 * @return
	 * @param key
	 *            noeglen objektet skal indsaettes med
	 * @param value
	 *            objektet der skal indsaettes
	 */
	public V put(K key, V value);

	/**
	 * Fjerner (noegle, vaerdi)- parret med noeglen key fra dictionary'en og
	 * value returneres.
	 * 
	 * @return objektet hoerende til noeglen key; null hvis key ikke findes
	 * @param key
	 *            noeglen der med tilhoerende objekt skal fjernes
	 */
	public V remove(K key);

	/**
	 * Returnerer antallet af elementer i dictionary
	 * 
	 * @return antal elementer i dictionary
	 */
	public int size();

	/**
	 * Returnerer en iterator med alle noegler i dictionay.
	 * 
	 * @return en iterator med noeglerne
	 */
	public Iterator<K> keys();

	/**
	 * Returnerer en iterator med alle vaerdier i dictionary.
	 * 
	 * @return en iterator med vaerdierne
	 */
	public Iterator<V> values();

}