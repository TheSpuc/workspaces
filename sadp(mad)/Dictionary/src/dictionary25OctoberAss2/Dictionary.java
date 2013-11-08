package dictionary25OctoberAss2;

import java.util.Iterator;

public interface Dictionary<K,V> {
	
	/**
	 * Return value based on key
	 * @param key
	 * @return
	 */
	public V get(K key);
	
	/**
	 * Inserts value based on key, if key exist overwrite it
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(K key, V value);
	
	/**
	 * Remove value based on key
	 * @param key
	 * @return
	 */
	public V remove(K key);
	
	/**
	 * Return Iterator based on keys
	 * @return
	 */
	public Iterator<K> keys();
	
	/**
	 * Return Iterator based on values
	 * @return
	 */
	public Iterator<V> values();
	
	/**
	 * Return number of key, value pairs
	 * @return
	 */
	public int size();
	
	/**
	 * Return whether the Dictionary is empty
	 * @return
	 */
	public boolean isEmpty();
	
}
