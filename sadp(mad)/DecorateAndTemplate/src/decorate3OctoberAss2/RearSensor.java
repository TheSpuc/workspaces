package decorate3OctoberAss2;

public class RearSensor extends CarExtra {
	
	public RearSensor(CarType carType){
		super(carType);
	}
	
	@Override
	public int cost(){
		return 20000 + getCarType().cost();
	}
	
	@Override
	public String getDescription() {
		return getCarType().getDescription() + " RearSensor";
	}
}
