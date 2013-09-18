import java.util.ArrayList;


public class RunTimeTest {

	public static void main(String[] args) {
		ArrayList<Long> estimatedBubbleTimeArray = new ArrayList<>();
		ArrayList<Long> estimatedBubbleTimeArrayList = new ArrayList<>();
		long estimatedInsertionTime = 0;
		long estimatedSelectionTime = 0;

		//Base for measuring time in sorting with Strings
		for(int i=0; i<10; i++){
			String[] stringArrayBubble = {"Erna", "Elly", "Laurits", "Bertha", "Christian", "August", "Marius", "John", "Tove", "Poul"}; 

			long startTimeArray = System.nanoTime();
			Sorting.<String>bubbleSort(stringArrayBubble);
			estimatedBubbleTimeArray.add(System.nanoTime()-startTimeArray);

			ArrayList<String> stringArrayListBubble = new ArrayList<>();
			stringArrayListBubble.add("Erna");
			stringArrayListBubble.add("Elly");
			stringArrayListBubble.add("Laurits");
			stringArrayListBubble.add("Bertha");
			stringArrayListBubble.add("Christian");
			stringArrayListBubble.add("August");
			stringArrayListBubble.add("Marius");
			stringArrayListBubble.add("John");
			stringArrayListBubble.add("Tove");
			stringArrayListBubble.add("Poul");

			long startTimeArrayList = System.nanoTime();
			Sorting.<String>bubbleSort(stringArrayListBubble);
			estimatedBubbleTimeArrayList.add(System.nanoTime()-startTimeArrayList);

		}
		System.out.println(estimatedBubbleTimeArray);
		System.out.println(estimatedBubbleTimeArrayList);
		
		String[] stringArrayBubble = {"Mark", "Nikolaj", "Henrik", "Tommy", "Jesper", "Jonas", "Margrethe", "Liv", "Birgitte", "Anders"}; 
		long startTimeArrayList = System.nanoTime();
		Sorting.<String>bubbleSort(stringArrayBubble);
		System.out.println(System.nanoTime()-startTimeArrayList);
	}
}
