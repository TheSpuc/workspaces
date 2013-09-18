/**
 * @author mlch
 * Date 2005-05-25
 */
package gui;

import dao.Dao;


public class KundeMainFrameApp {
    public static void main(String[] args) {
    	Dao.createAndStoreSomeObjects();
    	
        KundeMainFrame frame = new KundeMainFrame();
        frame.setVisible(true);
    }
}
