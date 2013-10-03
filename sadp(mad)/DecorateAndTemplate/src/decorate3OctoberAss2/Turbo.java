package decorate3OctoberAss2;

public class Turbo extends CarExtra{
	
	public Turbo(CarType carType){
		super(carType);
	}
	
	@Override
	public int cost(){
		return 4000 + getCarType().cost();
	}
	
	@Override
	public String getDescription() {
		return getCarType().getDescription() + " Turbo";
	}
}
