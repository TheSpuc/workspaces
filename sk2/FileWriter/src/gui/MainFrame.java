package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import service.Service;

public class MainFrame extends JFrame{
	
	public static void main(String[] args){
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}
	
	private Service service;
	private Controller controller;
	
	private JPanel north, south, east, west, center;
	
	private JButton jbFilePath;
	private final JFileChooser fcFilePath;
	
	public MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("FileWriter");
		this.setBounds(100, 100, 400, 600);
		
		service = Service.getInstance();
		controller = new Controller();
		
		//north related
		north = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		jbFilePath = new JButton("Choose file");
		jbFilePath.setSize(100, 25);
		jbFilePath.addActionListener(controller);
		north.add(jbFilePath);
		
		fcFilePath = new JFileChooser();
		
		this.add(north, BorderLayout.NORTH);
	}
	
	private class Controller implements ActionListener{
		
		private int returnVal;
		
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(jbFilePath)){
				returnVal = fcFilePath.showOpenDialog(MainFrame.this);
				
				if(returnVal == JFileChooser.APPROVE_OPTION){
					
				}
			}
			
			
		}
	}
}
