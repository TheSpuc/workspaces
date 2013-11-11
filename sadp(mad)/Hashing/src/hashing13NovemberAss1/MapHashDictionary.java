package hashing13NovemberAss1;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MapHashDictionary<K, V> implements Dictionary<K, V> {

	private java.util.Map<K, V>[] tabel;
	private static int N = 10;

	/**
	 * HashingMap constructor comment.
	 */
	@SuppressWarnings("unchecked")
	public MapHashDictionary() {
		tabel = new java.util.HashMap[N];
		for (int i = 0; i < N; i++)
			tabel[i] = new java.util.HashMap<K, V>();
	}

	public V get(K key) {
		int i = key.hashCode() % N;
		java.util.Map<K, V> m = tabel[i];
		return m.get(key);
	}

	public boolean isEmpty() {
		boolean empty = true;
		int i = 0;
		while (empty && i < N) {
			empty = (tabel[i]).isEmpty();
			i++;
		}
		return empty;
	}

	public V put(K key, V value) {
		int i = key.hashCode() % N;
		V result = null;
		java.util.Map<K, V> map = tabel[i];
		if(!map.containsKey(key)){
			map.put(key, value);
		}else result = map.put(key, value);
		return result;
	}

	public V remove(K key) {
		int i = key.hashCode() % N;
		java.util.Map<K, V> map = tabel[i];
		return map.remove(key);
	}

	public int size() {
		int size = 0;
		for(int i = 0; i < N; i++){
			size += tabel[i].size();
		}
		return size;
	}

	/**
	 * @see dictionary.Dictionary#keys()
	 */
	public Iterator<K> keys() {

		int i = 0;
		Set<K> s = new HashSet<K>();
		while (i < N) {
			java.util.Map<K, V> m = tabel[i];
			s.addAll(m.keySet());
			i++;
		}
		return s.iterator();
	}

	public Iterator<V> values() {
		int i = 0;
		Set<V> values = new HashSet<>();
		while(i < N){
			java.util.Map<K, V> map = tabel[i];
			values.addAll(map.values());
			i++;
		}
		return values.iterator();
	}

}
