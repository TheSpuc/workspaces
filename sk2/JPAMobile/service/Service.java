package service;

import java.util.List;

import model.Mobile;

import dao.JpaDao;

public class Service {
	private static Service service;

	public static Service getService() {
		if (service == null)
			service = new Service();
		return service;
	}


	private Service() {
	}

	public static Mobile newPerson() {
		return new Mobile();
	}

	public static List<Mobile> getMobile() {
		return JpaDao.getAllMobile();
	}

	public static void store(Mobile mobile) {
		JpaDao.store(mobile);
	}
	
	public static void remove(Mobile mobile){
		JpaDao.remove(mobile);
	}

	public static void close(){
		JpaDao.close();
	}
}
