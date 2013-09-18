package dao;

import java.util.ArrayList;

public class Dao {

	private static ArrayList<String> east = new ArrayList<>();
	private static ArrayList<String> west = new ArrayList<>();

	public static void addWest(String st){
		if(!west.contains(st)){
			west.add(st);
		}
	}

	public static void removeEast(String st){
		if(east.contains(st)){
			east.remove(st);
		}
	}

	public static void addEast(int[] list){
		int[] tempList = list;
		if(tempList.length > 0){
			for(int i = tempList.length - 1; i >= 0; i--){
				east.add(west.get(i));
			}
		}
	}

	public static void addEast(String st){
		if(!east.contains(st)){
			east.add(st);
		}
	}

	public static void removeWest(int[] list){
		int[] tempList = list;
		if(tempList.length > 0){
			for(int i = tempList.length - 1; i >= 0; i--){
				west.remove(i);
			}
		}
	}

	public static ArrayList<String> getEast(){
		return new ArrayList<String>(east);
	}

	public static ArrayList<String> getWest(){
		return new ArrayList<String>(west);
	}

	public static void createEastStrings(){
		String st = new String("000");
		Dao.addEast(st);
		st = new String("001");
		Dao.addEast(st);
		st = new String("002");
		Dao.addEast(st);
		st = new String("003");
		Dao.addEast(st);
		st = new String("004");
		Dao.addEast(st);
		st = new String("005");
		Dao.addEast(st);
		st = new String("006");
		Dao.addEast(st);
		st = new String("007");
		Dao.addEast(st);
		st = new String("008");
		Dao.addEast(st);
		st = new String("009");
		Dao.addEast(st);
	}

	public static void createWestStrings(){
		String st = new String("aaa");
		Dao.addWest(st);
		st = new String("bbb");
		Dao.addWest(st);
		st = new String("ccc");
		Dao.addWest(st);
		st = new String("ddd");
		Dao.addWest(st);
		st = new String("eee");
		Dao.addWest(st);
		st = new String("fff");
		Dao.addWest(st);
		st = new String("ggg");
		Dao.addWest(st);
		st = new String("hhh");
		Dao.addWest(st);
		st = new String("iii");
		Dao.addWest(st);
		st = new String("jjj");
		Dao.addWest(st);
	}
}
