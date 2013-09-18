package chap04.webloganalyzer;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogAnalyzer log = new LogAnalyzer("pony.txt");
		log.analyzeHourlyData();
		log.printHourlyCounts();
		System.out.println("accesses: " + log.numberOfAccesses());

//		LogfileCreator fileCreate = new LogfileCreator();
//		fileCreate.createFile("pony.txt", 10);
	}

}
