package decorate3OctoberAss2;

public class MetalCoat extends CarExtra {

	public MetalCoat(CarType carType) {
		super(carType);
	}
	
	@Override
	public int cost(){
		return 5000 + getCarType().cost();
	}
	
	@Override
	public String getDescription() {
		return getCarType().getDescription() + " MetalCoat";
	}
	
	
}
