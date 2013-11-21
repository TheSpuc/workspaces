package hashing13NovemberAss1;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapHashDictionary<K, V> implements Dictionary<K, V> {

	private java.util.Map<K, V>[] tabel;
	private static int N = 7;
	private int nrOfEntries;
	private static double loadFactor;

	/**
	 * HashingMap constructor comment.
	 */
	@SuppressWarnings("unchecked")
	public MapHashDictionary() {
		tabel = new java.util.HashMap[N];
		nrOfEntries = 0;
		loadFactor = 0.75f;
		for (int i = 0; i < N; i++)
			tabel[i] = new java.util.HashMap<K, V>();
	}

	public V get(K key) {
		int i = key.hashCode() % tabel.length;
		java.util.Map<K, V> m = tabel[i];
		return m.get(key);
	}

	public boolean isEmpty() {
		boolean empty = true;
		int i = 0;
		while (empty && i < tabel.length) {
			empty = (tabel[i]).isEmpty();
			i++;
		}
		return empty;
	}

	public V put(K key, V value) {
		int i = key.hashCode() % tabel.length;
		V result = null;
		java.util.Map<K, V> map = tabel[i];
		if(!map.containsKey(key)){
			map.put(key, value);
			nrOfEntries++;
			if(nrOfEntries/tabel.length > loadFactor){
				rehash();
			}
		}else result = map.put(key, value);
		return result;
	}

	private void rehash(){
		int size = findNextPrime();
		@SuppressWarnings("unchecked")
		Map<K, V>[] newTabel = new HashMap[size];
		for(int i=0; i<size; i++){
			newTabel[i] = new HashMap<>();
		}

		for(Map<K, V> t : tabel){
			for(Map.Entry<K, V> e : t.entrySet()){
				int hashCode = e.getKey().hashCode() % newTabel.length;
				newTabel[hashCode].put(e.getKey(), e.getValue());
			}
		}
		tabel = newTabel;
	}

	private int findNextPrime(){
		int primeIndex = tabel.length*2;
		boolean prime = false;
		while(!prime){
			int index = 2;	
			boolean isPrime = true;
			while(index < tabel.length && isPrime){
				if(primeIndex % index == 0){
					isPrime = false;
				}else index++;
			}
			if(!isPrime){
				primeIndex++;
			}else prime = true;
		}
		return primeIndex;
	}

	public V remove(K key) {
		int i = key.hashCode() % tabel.length;
		java.util.Map<K, V> map = tabel[i];
		V result = map.remove(key);
		if(result != null){
			nrOfEntries--;
		}
		return result;
	}

	public int size() {
		int size = 0;
		for(int i = 0; i < tabel.length; i++){
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
		while (i < tabel.length) {
			java.util.Map<K, V> m = tabel[i];
			s.addAll(m.keySet());
			i++;
		}
		return s.iterator();
	}

	public Iterator<V> values() {
		int i = 0;
		List<V> values = new ArrayList<>();
		while(i < tabel.length){
			java.util.Map<K, V> map = tabel[i];
			values.addAll(map.values());
			i++;
		}
		return values.iterator();
	}

}
