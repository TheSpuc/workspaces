package gui;

import dao.Dao;

public class MainFrameApp {

	public static void main(String[] args){
		Dao.createEastStrings();
		Dao.createWestStrings();
		
		MainFrame frame = new MainFrame();
		
		frame.setVisible(true);
	}
}
