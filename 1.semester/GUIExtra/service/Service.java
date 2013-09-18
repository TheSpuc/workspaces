package service;

import java.util.ArrayList;

import dao.Dao;

public class Service {
	
	public static ArrayList<String> getWest(){
		return Dao.getWest();
	}
	
	public static ArrayList<String> getEast(){
		return Dao.getEast();
	}
	
	public static void removeWest(int[] list){
		Dao.removeWest(list);
	}
	
	public static void removeEast(String st){
		Dao.removeEast(st);
	}
	
	public static String addWest(String st){
		String temp = st;
		Dao.addWest(temp);
		return temp;
	}
	
	public static int[] addEast(int[] list){
		int[] tempList = list;
		Dao.addEast(tempList);
		return tempList;
	}
}
