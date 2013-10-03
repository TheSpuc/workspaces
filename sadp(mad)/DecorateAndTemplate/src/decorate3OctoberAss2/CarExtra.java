package decorate3OctoberAss2;

public abstract class CarExtra extends CarType {
	
	private CarType carType;
	
	public CarExtra(CarType carType){
		super();
		this.carType = carType;
	}
	
	public abstract String getDescription();
	
	public CarType getCarType(){
		return carType;
	}
	
}
