package hashMapFileRead;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Fileread {

	/**
	 * Reads rating information for from file (hereby movie list file), 
	 * when key already exist increment counter on same specific key.
	 * @param args file to read
	 */
	public static void main(String[] args) {
		//HashMap for containing movie information and calculated rating
		Map<String, Integer> movieRating = new HashMap<>();
		//HashMap for containing number of times a movie as appeared.
		Map<String, Integer> movieTimes = new HashMap<>();

		//BufferedReader for taking input.
		try(BufferedReader br = new BufferedReader(new FileReader("/Users/MMB/Dropbox/workspace/macworkspace/Assign2semester/hashMapFileRead/movielist.txt"))) {

			String current = "";

			//Reading in the while loop and thereby checking if there is input
			while((current = br.readLine()) != null){
				int counter = 1;
				int value = 0;
				//Indivial rating for each movie
				value = Integer.parseInt(br.readLine().trim());
				
				//Check whether HashMap already contains the current movie
				//if it does, then calculate new values.
				if(movieRating.containsKey(current)){
					value += movieRating.get(current);
					counter += movieTimes.get(current);
				} 
					movieRating.put(current, value);
					movieTimes.put(current, counter);
			}
			
			//Print system for getting everything out pretty.
			for(Entry<String, Integer> e : movieRating.entrySet()){
				String key = e.getKey();
				double nr = movieTimes.get(key);
				System.out.println(key + ": " + nr + ", gennemsnit: " + (e.getValue()/nr));
			}
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}


