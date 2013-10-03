package decorate3OctoberAss2;

public abstract class CarType {
	
	private String description;
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public String getDescription(){
		return description;
	}
	
	public abstract int cost();
}
