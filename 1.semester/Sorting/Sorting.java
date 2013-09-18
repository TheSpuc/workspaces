import java.util.ArrayList;
import java.util.Iterator;


public class Sorting {

	/**
	 * bubble sort for array, where the highest number is being placed first
	 * @param objects
	 */
	public static  <T extends Comparable<T>> void bubbleSort(T[] objects){ // bubble sort method generic, for taking all array types
		for(int position = objects.length-1; position >= 0; position--){ //makes certain that we end up with the last number sorted first
			int scan; //local variables for getting the positions 
			for(scan = 0; scan < position; scan++){ //+1 so we don't run out of room for the comparison
				if(objects[scan].compareTo(objects[scan+1]) > 0){ //if scan is bigger than scan+1
					swap(objects, scan, scan+1); //swap scan and scan+1 if scan is bigger.
				}
			}
		}
	}
	
	public static  <T extends Comparable<T>> void bubbleSort(ArrayList<T> objects){ // bubble sort method generic, for taking all array types
		for(int position = objects.size()-1; position >= 0; position--){ //makes certain that we end up with the last number sorted first
			int scan; //local variables for getting the positions 
			for(scan = 0; scan < position; scan++){ //+1 so we don't run out of room for the comparison
				if(objects.get(scan).compareTo(objects.get(scan+1)) > 0){ //if scan is bigger than scan+1
					swap(objects, scan, scan+1); //swap scan and scan+1 if scan is bigger.
				}
			}
		}
	}

	/**
	 * selection sort for array, where the smallest number is being placed first
	 * @param objects
	 */
	public static <T extends Comparable<T>> void selectionSort(T[] objects){
		for(int index=0; index<objects.length-1; index++){ //runs to objects.length-1 because scan start on index+1
			int min = index; //sets the index we look at as minimum for comparison
			for(int scan=index+1; scan<objects.length; scan++){ //runs until there is no more. 
				if(objects[scan].compareTo(objects[min]) < 0){ //if the one scan is looking at right now is < then the saved min
					min = scan; //set current min to the new min
				}
			}
			swap(objects, min, index); //when the smallest is found, we swap the index we are at and the minimum we found
		}
	}

	/**
	 * selection sort for ArrayList, where the smallest number is being placed first
	 * @param objects
	 */
	public static <T extends Comparable<T>> void selectionSort(ArrayList<T> objects){
		for(int index=0; index<objects.size()-1; index++){ //runs to objects.size()-1 because scan start on index+1
			int min = index; //sets the index we look at as minimum for comparison
			for(int scan=index+1; scan<objects.size(); scan++){ //runs until there is no more. 
				if(objects.get(scan).compareTo(objects.get(min)) < 0){ //if the one scan is looking at right now is < then the saved min
					min = scan; //set current min to the new min
				}
			}
			swap(objects, min, index); //when the smallest is found, we swap the index we are at and the minimum we found
		}
	}

	/**
	 * swap method for arrays
	 * @param data
	 * @param index1
	 * @param index2
	 */
	private static <T> void swap(T[] data, int index1, int index2){ //helping method for swapping the data
		T temp = data[index1]; //generic value for holding one value and swapping the others 
		data[index1] = data[index2]; //data swap
		data[index2] = temp; //data swap
	}

	/**
	 * swap method for ArrayList
	 * @param data
	 * @param index1
	 * @param index2
	 */
	private static <T> void swap(ArrayList<T> data, int index1, int index2){ //helping method for swapping the data
		T temp = data.get(index1); //generic value for holding one value and swapping the others 
		data.set(index1, data.get(index2)); //data swap
		data.set(index2, temp); //data swap
	}

	/**
	 * insertion sort for array, where the entire list ends sorted
	 * @param objects
	 */
	public static <T extends Comparable<T>> void insertionSort(T[] objects){
		for(int index = 1; index<objects.length; index++){ //run through total length
			T key = objects[index]; //hold the one we have to move
			int position = index; //saves the position we start looking from
			while(position > 0 && objects[position-1].compareTo(key) > 0){ //check how many times we have to move something to the right
				objects[position] = objects[position-1]; //if the object before is > then we move it to the right
				position--; //afterwards go back and check the position before that
			}
			objects[position] = key; //set key into the right position based on comparison
		}
	}

	/**
	 * insertion sort for ArrayList, where the entire list ends sorted
	 * @param objects
	 */
	public static <T extends Comparable<T>> void insertionSort(ArrayList<T> objects){
		for(int index = 1; index<objects.size(); index++){ //run through total length
			T key = objects.get(index); //hold the one we have to move
			int position = index; //saves the position we start looking from
			while(position > 0 && objects.get(position-1).compareTo(key) > 0){ //check how many times we have to move something to the right
				objects.set(position, objects.get(position-1)); //if the object before is > then we move it to the right
				position--; //afterwards go back and check the position before that
			}
			objects.set(position, key); //set key into the right position based on comparison
		}
	}
}




