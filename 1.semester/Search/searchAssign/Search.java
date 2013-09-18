package searchAssign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Search {

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static boolean oddNumber(int[] list){
		boolean found = false;
		int i = 0;
		while(i < list.length && !found){
			if(list[i]%2 != 0){
				found = true;
			}else i++;
		}
		return found;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static int intervalNumber(int[] list){
		boolean found = false;
		int target = -1;
		int i = 0;
		while(i < list.length && !found){
			int temp = list[i];
			if(temp >=10 && temp <= 15){
				found = true;
				target = temp;
			}else i++;
		}
		return target;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static boolean twoNumbers(int[] list){
		boolean found = false;
		int i = 1;
		while(i < list.length && !found){
			if(list[i] == list[(i-1)]){
				found = true;
			}
			else i++;
		}
		return found;
	}

	/**
	 * 
	 * @param list
	 * @param score
	 * @return
	 */
	public static Player findScoreLinear(ArrayList<Player> list, int score){
		boolean found = false;
		int i = 0;
		Player p = null;
		while(i < list.size() && !found){
			if(list.get(i).getGoals() == score){
				p = list.get(i);
				found = true;
			}else i++;
		}
		return p;
	}

	/**
	 * 
	 * @param list
	 * @param score
	 * @return
	 */
	public static Player findScoreBinary(ArrayList<Player> list, int score){
		boolean found = false;
		int left = 0;
		int right = list.size()-1;
		Player p = null; 
		while(left <= right && !found){
			int middle = (left+right)/2;
			int tempScore = list.get(middle).getGoals();
			if(tempScore == score){
				found = true;
				p = list.get(middle);
			}else if(tempScore > score){
				right = middle-1;
			}else if(tempScore < score){
				left = middle+1;
			}
		}
		return p;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static String findGoodPlayer(ArrayList<Player> list){
		boolean found = false;
		int i = 0;
		String name = "";
		while(i<list.size() && !found){
			Player p = list.get(i);
			if(p.getGoals() > 50 && p.getHeight() < 170){
				found = true;
				name = p.getName();
			}else i++;
		}
		return name;
	}

	/**
	 * Iterator players
	 * @param list
	 * @return
	 */
	public static String findGoodPlayerIterator(ArrayList<Player> list){
		Iterator<Player> it = list.iterator();
		String name = "";
		while(it.hasNext()){
			Player p = it.next();
			if(p.getGoals() > 50 && p.getHeight() < 170) {
				name = p.getName();
			}
		}
		return name;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public static int highestSquareRootLinear(int n){
		int i = 0;
		int r = -1;
		boolean found = false;
		while(!found && i<=n){
			if((i*i) <= n && (i+1)*(i+1) > n){
				found = true;
				r = i;
			}else i++;
		}
		return r;
	}

	/**
	 * 
	 * @param n
	 * @return
	 */
	public static int highestSquareRootBinary(int n){
		boolean found = false;
		int left = 0;
		int right = n;
		int result = -1;
		while(!found){
			int middle = (left+right)/2;
			if((middle*middle) <= n && n < (middle+1)*(middle+1)){
				found = true;
				result = middle;
			}else if((middle*middle) <= n){
				left = middle + 1;
			}else right = middle - 1;
		}
		return result;
	}

	/**
	 * 
	 * @param list
	 * @param n
	 * @return
	 */
	public static int find(ArrayList<Integer> list, int n){
		boolean found = false;
		int i = 0;
		while(i < list.size() && !found){
			if(list.get(i) == n){
				found = true;
				if(i>0){
					int temp = list.get(i-1);
					list.set(i-1, list.get(i));
					list.set(i, temp);
					i--;
				}
			}else i++;
		}
		if(found){
			return i;
		}else return -1;
	}

	/**
	 * 
	 * @param list
	 * @param x
	 * @return
	 */
	public static int findSpecificElement(ArrayList<Integer> list, int x){
		boolean found = false;
		int left = 0;
		int right = list.size()-1;
		int result = -1;
		while(!found && left <= right){
			int middle = (left+right)/2;
			if(list.get(middle) > x){
				right = middle-1;
			}else if(list.get(middle) == x || middle == list.size()-1 || list.get(middle+1) > x){
				result = list.get(middle);
				found = true;
			}else {
				left = middle + 1;
			}
		}
		return result;
	}

	/**
	 * Iterator String
	 * @param list
	 * @return
	 */
	public static String findBiggestString(String[] list){
		Iterator<String> it = Arrays.asList(list).iterator();
		String name = "";
		while(it.hasNext()){
			String temp = it.next();
			if(name.compareTo(temp) > 0){
				name = temp;
			}
		}
		return name;
	}

	/**
	 * 
	 * @param list
	 * @return
	 */
	public static Integer findBiggestInteger(Integer[] list){
		Iterator<Integer> it = Arrays.asList(list).iterator();
		Integer result = -1;
		while(it.hasNext()){
			Integer temp = it.next();
			if(result.compareTo(temp) < 0){
				result = temp;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param objects 
	 * @return 
	 */
	public static <T extends Comparable<T>> T findBiggestComparable(T[] objects){
		Iterator<T> it = Arrays.asList(objects).iterator();
		T result = null;
		if(it.hasNext()){
			result = it.next();
			while(it.hasNext()){
				T temp = it.next();
				if(result.compareTo((T) temp) < 0){
					result = temp;
				}
			}	
		}
		return result;
	}

	/**
	 * Henrik
	 * @param objects
	 * @return
	 */
//	public static Comparable findBigComparable(Comparable[] objects){
//		Comparable biggest = objects[0];
//		Iterator<Comparable> it = Arrays.asList(objects).iterator();
//		while (it.hasNext()){
//			Comparable temp = it.next();
//			if (biggest.compareTo(temp) < 0){
//				biggest = temp;
//			}
//		}
//		return biggest;
//	}

	/**
	 * 
	 * @param objects
	 * @param target
	 * @return
	 */
	public static <T extends Comparable<T>> int findComparableTargetLinear(T[] objects, T target){
		int index = 0;
		boolean found = false;
		while(index < objects.length && !found){
			T temp = objects[index];
			if(target.compareTo(temp) == 0){
				found = true;
			}else index++;
		}
		return index;
	}

	/**
	 * 
	 * @param objects
	 * @param target
	 * @return
	 */
	public static <T extends Comparable<T>> int findComparableTargetBinary(T[] objects, T target){
		int lo = 0;
		int hi = objects.length-1;
		boolean found = false;
		int result = -1;
		while(!found && lo <= hi){
			int middle = (lo+hi)/2;
			int temp = target.compareTo(objects[middle]);
			if(temp == 0){
				found = true;
				result = middle;
			}else if(temp > 0){
				lo = middle+1;
			}else hi = middle-1;
		}
		return result;
	}

	/**
	 * 
	 * @param searchString
	 * @param k
	 * @return
	 */
	public static boolean kSame(String searchString, int k){
		boolean found = false;
		int i = 0;
		while((i <= searchString.length()-k) && !found){
			if(match(searchString, i, k)){
				found = true;
			}else i++;
		}
		return found;
	}

	/**
	 * 
	 * @param searchString
	 * @param current
	 * @param length
	 * @return
	 */
	public static boolean match(String searchString, int current, int length){
		boolean searching = true;
		int j = 1;
		while(searching && j<length){
			if(searchString.charAt(current) != searchString.charAt(j+current)){
				searching = false;
			}else j++;
		}
		return searching;
	}

	/**
	 * 
	 * @param customers
	 * @return
	 */
	public static boolean afterXenia(Customer[] customers){
		Customer henrik = new Customer("Xenia", "Zachariassen", 23);
		boolean found = false;
		int i = 0;
		while(!found && i<customers.length){
			if(customers[i].compareTo(henrik) > 0){
				found = true;
			}else i++;
		}
		return found;
	}
}


