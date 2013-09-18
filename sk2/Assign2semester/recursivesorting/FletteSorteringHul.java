package recursivesorting;

import java.util.ArrayList;
import java.util.List;

public class FletteSorteringHul {


	// den liste der skal sorteres skal være global for de rekursive kald
	private static List<Integer> list;
	private static List<Integer> temp;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		list = new ArrayList<>();
		list.add(8);
		list.add(56);
		list.add(45);
		list.add(34);
		list.add(15);
		list.add(12);
		list.add(34);
		list.add(44);
		System.out.println(list);
		fletteSort();
		System.out.println(list);

	}

	// den metode der søtter fletningen i gang
	public static void fletteSort() {
		mergesort(0, list.size() - 1);
	}

	// den rekursive metode der implementere divide and conquer skabelonen
	private static void mergesort(int l, int h) {
		if (l < h) {
			int m = (l + h) / 2;
			mergesort(l, m);
			mergesort(m + 1, h);
			merge(l, m, h);
		}
	}

	// kombiner er realiseret ved fletteskabelonen
	private static void merge(int lo, int mid, int hi) {
		temp = new ArrayList<>(list);
		
		int i = lo;
		int j = mid+1;
		int inIndex = lo; //the index we have to put the value in

		while(i <= mid && j <= hi){
			if(temp.get(i) <= temp.get(j)){
				list.set(inIndex, temp.get(i));
				i++;
			}else {
				list.set(inIndex, temp.get(j));
				j++;
			}
			inIndex++; //every time count it one up, so the next time we insert in the right place
		}

		while(i <= mid){ //cleans the rest up till middle
			list.set(inIndex, temp.get(i));
			i++;
			inIndex++;
		}
		while(j <= hi){ //cleans the rest up till high
			list.set(inIndex, temp.get(j));
			j++;
			inIndex++;
		}
	}
}

