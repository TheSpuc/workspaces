package service;

public class Service {
	
	private static Service service;
	
	private Service(){
	}
	
	public static Service getInstance(){
		if(service == null){
			service = new Service();
		}
		return service;
	}
	
	
}
