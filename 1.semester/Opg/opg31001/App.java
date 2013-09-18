package opg31001;

public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Team team1 = new Team("Hej hej");
		
		Player p1 = new Player("Søren", 5, 100);
		Player p2 = new Player("Casper", 99, 2);
		Player p3 = new Player("Christian", 50, 40);
		Player p4 = new Player("Mark", 0, 10);
		
		team1.addPlayer(p1);
		team1.addPlayer(p2);
		team1.addPlayer(p3);
		team1.addPlayer(p4);
		
		System.out.println(team1);
		System.out.println("Bestplayer: " + team1.bestPlayer());
		System.out.println("Average age: " + team1.calAverageAge());
		System.out.println("Old players: " + team1.calOldPlayers(30));
		System.out.println("Total score: " + team1.calTotalScore());
		System.out.println("Max score: " + team1.maxScore());

	}

}
