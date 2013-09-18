
public class Spiritus {
	
	public int prisen;
	public String betegnelse;
	
	public Spiritus(String betegnelse, int prisen){
		this.betegnelse = betegnelse;
		this.prisen = prisen;
	}
	
	public int getPrisen(){
		return prisen;
	}
	
	public void setPrisen(int prisen){
		this.prisen = prisen;
	}
	
	public String getBetegnelse(){
		return betegnelse;
	}
	
	public void setBetegnelse(String betegnelse){
		this.betegnelse = betegnelse;
	}
	
	public double hentMoms(){
		return prisen * 0.50;
	}
}
