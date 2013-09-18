package sorts;

import java.util.ArrayList;

public class Sorting {

	public static <T extends Comparable<T>> void insertArrayList(ArrayList<T> objects, T object){
		int index = objects.size();
		while(index > 0 && objects.get(index-1).compareTo(object) > 0){
			index--;
		}
		objects.add(index, object);
	}
	
	public static <T extends Comparable<T>> void insertArray(T[] objects, T object){
		int index = objects.length;
		while(objects[index-1] == null){
			index--;
		}
		while(index > 0 && objects[index-1].compareTo(object) > 0){
			objects[index] = objects[index-1];
			index--;
		}
		objects[index] = object;
	}
	
	public static <T extends Comparable<T>> ArrayList<T> merge(ArrayList<T> objects1, ArrayList<T> objects2){
		ArrayList<T> resultList = new ArrayList<>();
		int i1 = 0;
		int i2 = 0;
		while(i1 < objects1.size() && i2 < objects2.size()){
			if(objects1.get(i1).compareTo(objects2.get(i2)) <= 0){
				resultList.add(objects1.get(i1));
				i1++;
			}else{
				resultList.add(objects2.get(i2));
				i2++;
			}
		}
		while(i1 < objects1.size()){
			resultList.add(objects1.get(i1));
			i1++;
		}
		while(i2 < objects2.size()){
			resultList.add(objects2.get(i2));
			i2++;
		}
		return resultList;
	}
	
	public static int[] incommen(int[] objects1, int[] objects2){
		int[] result = new int[(objects1.length+objects2.length)];
		int i1 = 0;
		int i2 = 0;
		int index = 0;
		while(i1 < objects1.length && i2 < objects2.length){
			if(objects1[i1] > objects2[i2]){
				i2++;
			}else if(objects1[i1] < objects2[i2]){
				i1++;
			}else {
				result[index] = objects1[i1];
				i1++;
				i2++;
				index++;
			}
		}
		
		return result;
	}
	
	public static <T extends Comparable<T>> ArrayList<T> good(ArrayList<T> objects1, T[] objects2){
		ArrayList<T> resultList = new ArrayList<>();
		int i1 = 0;
		int i2 = 0;
		while(i1 < objects1.size() && i2 < objects2.length){
			if(objects1.get(i1).compareTo(objects2[i2]) < 0){
				resultList.add(objects1.get(i1));
				i1++;
			}else if(objects1.get(i1).compareTo(objects2[i2]) > 0){
				i2++;
			}else {
				i1++;
			}
		}
		while(i1 < objects1.size()){
			resultList.add(objects1.get(i1));
			i1++;
		}
		return resultList;
	}
	
}
