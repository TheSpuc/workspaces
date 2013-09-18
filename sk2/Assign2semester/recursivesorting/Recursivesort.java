package recursivesorting;

import java.util.ArrayList;
import java.util.List;

public class Recursivesort {

	private static List<Integer> henrik;


	public static void main(String[] args) {
		henrik = new ArrayList<>();

		//assign1
		for(int i=0; i<10; i++){
			henrik.add(2);
		}

		if(!henrik.isEmpty()){
			System.out.println(sum());
		}

		//assign2
		for(int i=0; i<10; i+=2){
			henrik.set(i, 0);
		}
		System.out.println(findNrZero());

	}

	//assign1
	public static int sum(){
		return sumOfList(0, henrik.size()-1);
	}

	private static int sumOfList(int lo, int hi){
		if(lo == hi){
			return henrik.get(lo);
		}else{
			int mid = (lo+hi)/2;
			int loRes = sumOfList(lo, mid);
			int hiRes = sumOfList(mid+1, hi);
			return (loRes + hiRes);
		}
	}

	//assign2
	public static int findNrZero(){
		return sumNrOfZero(0, henrik.size()-1);
	}

	private static int sumNrOfZero(int lo, int hi){
		int result = 0;
		if(lo == hi){
			if(henrik.get(lo) == 0){
				result = 1;
			}
		}else{
			int mid = (lo+hi)/2;
			int loRes = sumNrOfZero(lo, mid);
			int hiRes = sumNrOfZero(mid+1, hi);
			result = (loRes+hiRes);
		}
		return result;
	}

}
