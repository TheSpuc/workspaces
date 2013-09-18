package arkitekturE12.gui;

import javax.swing.UIManager;

 
import arkitekturE12.service.Service;

/**
 * The ArchitectureExample application shows how to program an application
 * with graphical user interface and business classes. The architecture is 
 * a 4-layered architecture with gui, service, dao and model layers.
 * @author mlch
 */
public class ArchitectureExampleApp
{
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            System.out
                    .println("Error setting look and feel: " + e.getMessage());
        }
        
        Service.createAndStoreSomeObjects();
        
        MainFrame frame = new MainFrame();
        frame.setVisible(true);
    }
}
