package decorate3OctoberAss2;

public class Fiesta extends CarType {

	public Fiesta() {
		setDescription("Fiesta");
	}

	@Override
	public int cost() {
		return 200000;
	}

}
