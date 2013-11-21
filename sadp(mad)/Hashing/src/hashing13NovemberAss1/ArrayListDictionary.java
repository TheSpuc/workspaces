package hashing13NovemberAss1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ArrayListDictionary<K, V> implements Dictionary<K, V> {


	private List<Entry>[] group;
	private static int N = 7;
	private int nrOfEntries;
	private static double loadfactor;

	@SuppressWarnings("unchecked")
	public ArrayListDictionary(){
		nrOfEntries = 0;
		group = new ArrayList[N];
		for(int i=0; i<N; i++){
			group[i] = new ArrayList<Entry>();
		}
		loadfactor = 0.75f;
	}

	@Override
	public V get(K key) {
		int i = key.hashCode() % group.length;
		V result = null;
		List<Entry> list = group[i];
		boolean found = false;
		int index = 0;
		while(!found && index < list.size()){
			if(list.get(index).key.equals(key)){
				result = list.get(index).value;
				found = true;
			}else index++;
		}
		return result;
	}

	@Override
	public boolean isEmpty() {
		return nrOfEntries == 0;
	}

	@Override
	public V put(K key, V value) {
		int i = key.hashCode() % group.length;
		V result = null;
		Entry in = new Entry();
		in.key = key;
		in.value = value;

		List<Entry> list = group[i];
		boolean found = false;
		int index = 0;
		while(!found && index < list.size()){
			if(list.get(index).key.equals(key)){
				found = true;
				result = list.get(index).value;
				list.set(index, in);
			}else index++;
		}
		if(!found){
			list.add(in);
			nrOfEntries++;
			if(nrOfEntries/group.length > loadfactor){
				rehash();
			}
		}
		return result;
	}

	private void rehash(){
		int size = findNextPrime();
		@SuppressWarnings("unchecked")
		List<Entry>[] newGroup = new ArrayList[size];
		for(int i=0; i<size; i++){
			newGroup[i] = new ArrayList<Entry>();
		}

		for(List<Entry> l : group){
			for(Entry e : l){
				int hashCode = e.key.hashCode() % newGroup.length;
				newGroup[hashCode].add(e);
			}
		}
		group = newGroup;
	}

	private int findNextPrime(){
		int primeIndex = group.length*2;
		boolean prime = false;
		while(!prime){
			int index = 2;	
			boolean isPrime = true;
			while(index < group.length && isPrime){
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

	@Override
	public V remove(K key) {
		V result = null;
		boolean found = false;
		int index = 0;
		int i = key.hashCode() % group.length;
		List<Entry> list = group[i];
		while(!found && index < list.size()){
			if(list.get(index).key.equals(key)){
				found = true;
				result = list.get(index).value;
				list.remove(index);
				nrOfEntries--;
			}else index++;
		}
		return result;
	}

	@Override
	public int size() {
		return nrOfEntries;
	}

	@Override
	public Iterator<K> keys() {
		int i = 0;
		Set<K> keys = new HashSet<>();
		while(i < group.length){
			List<Entry> entry = group[i];
			for(Entry e : entry){
				keys.add(e.key);
			}
			i++;
		}
		return keys.iterator();
	}

	@Override
	public Iterator<V> values() {
		int i = 0;
		List<V> values = new ArrayList<>();
		while(i < group.length){
			List<Entry> entry = group[i];
			for(Entry e : entry){
				values.add(e.value);
			}
			i++;
		}
		return values.iterator();
	}

	private class Entry{
		K key;
		V value;
	}

}
