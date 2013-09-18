package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainPanel extends JPanel
{
    private MainFrame frame;
    private JButton btnTexts, btnCalls, btnPersons;
    
    private Controller controller = new Controller();
    
    public MainPanel(MainFrame frame)
    {
        this.frame = frame;
        this.setSize(300, 400);
        this.setLocation(5, 10);
       
        btnTexts = new JButton("Texts");
        this.add(btnTexts);
        btnTexts.setSize(100, 70);
        btnTexts.setLocation(30, 140);
        btnTexts.addActionListener(controller);
        
        btnCalls = new JButton("Phone");
        this.add(btnCalls);
        btnCalls.setSize(100, 70);
        btnCalls.setLocation(170, 140);
        btnCalls.addActionListener(controller);
        
        btnPersons = new JButton("Contacts");
        this.add(btnPersons);
        btnPersons.setSize(100, 70);
        btnPersons.setLocation(100, 250);
        btnPersons.addActionListener(controller);
    }
    
    private class Controller implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if (e.getSource() == btnTexts) {
                frame.showPanel("Texts");
            }
            
            if (e.getSource() == btnCalls) {
                frame.showPanel("Calls");
            }
            
            if (e.getSource() == btnPersons) {
                frame.showPanel("Contacts");
            }
        }
    }
    
}
