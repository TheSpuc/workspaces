package decorate3OctoberAss2;

public class Leather extends CarExtra {

	public Leather(CarType carType){
		super(carType);
	}

	@Override
	public int cost() {
		return 45000 + getCarType().cost();
	}

	@Override
	public String getDescription() {
		return getCarType().getDescription() + " Leather";
	}
}
