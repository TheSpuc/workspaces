package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import service.Service;

public class MainFrame extends JFrame {

	private JButton addWest, addEast;
	private JList<String> jlWest, jlEast;
	private JScrollPane jsWest, jsEast;
	
	public MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setTitle("MiniApp");
		this.setSize(600, 500);
		this.setLocation(300, 400);
		
		jlWest = new JList<String>(Service.getWest().toArray(new String[0]));
		jlWest.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		jsWest = new JScrollPane(jlWest);
		jsWest.setBounds(10, 10, 200, 300);
		this.add(jsWest);
		
		addWest = new JButton("<<");
		addWest.setBounds(250, 100, 80, 25);
		addWest.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String temp = jlEast.getSelectedValue();
				Service.addWest(temp);
				Service.removeEast(temp);
				updateList();
			}
		});
		this.add(addWest);
		
		addEast = new JButton(">>");
		addEast.setBounds(250, 150, 80, 25);
		addEast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int[] tempList = jlWest.getSelectedIndices();
				Service.addEast(tempList);
				Service.removeWest(tempList);
				updateList();
			}
		});
		this.add(addEast);
		
		jlEast = new JList<String>(Service.getEast().toArray(new String[0]));
		jlEast.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jsEast = new JScrollPane(jlEast);
		jsEast.setBounds(390, 10, 200, 300);
		this.add(jsEast);
		
		
	}
	
	public void updateList(){
		jlWest.setListData(Service.getWest().toArray(new String[0]));
		jlEast.setListData(Service.getEast().toArray(new String[0]));
	}
}
