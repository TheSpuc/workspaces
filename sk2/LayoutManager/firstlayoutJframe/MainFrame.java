package firstlayoutJframe;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	private JScrollPane scpList;
	private JList<String> list;
	private JTextField txfname; 
	private DefaultListModel<String> listModel;

	public MainFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle("Jframe Manager");
		this.setLocation(100, 100);
		this.setSize(400, 300);
		
		//Jlist related
		String[] items={"Anne","Bettina","Camilla","Dorte","Erica",
                "Fellicia","Gitte","Hanne","Irene","Jane",
                "Kirsten","Lotte","Mette","Nete","Ophelia"};
		
		listModel = new DefaultListModel<>();
		list = new JList<>(listModel);
		
		for(String s : items){
			listModel.addElement(s);
		}
		
		//center panel related
		JPanel center = new JPanel();
		center.setLayout(new BorderLayout());
		
		scpList = new JScrollPane(list);
		center.add(scpList, BorderLayout.CENTER);
		
		//add center panel to the content pane, placed in center.
		this.add(center, BorderLayout.CENTER);
		
		//North panel related
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout());
		
		JLabel lblnavn = new JLabel("Name:");
		north.add(lblnavn);
		
		txfname = new JTextField();
		int h = txfname.getPreferredSize().height;
		txfname.setPreferredSize(new Dimension(200, h));
		north.add(txfname);
		
		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			
			//for adding a name from JTextField to the list.
			@Override
			public void actionPerformed(ActionEvent e) {
				String input = txfname.getText().trim();
				if(!input.equals("")){
					listModel.addElement(input);
					txfname.setText("");
				}
			}
		});
		north.add(add);
		//add north panel to the content pane, placed in north.
		this.add(north, BorderLayout.NORTH);
		
		//south panel related
		JPanel south = new JPanel();
		south.setLayout(new FlowLayout());
		
		JButton get = new JButton("Get");
		south.add(get);
		
		JButton save = new JButton("Save");
		south.add(save);
		
		//add south panel to the content pane, placed in south.
		this.add(south, BorderLayout.SOUTH);
	}
	
	public static void main(String[] args){
		MainFrame m = new MainFrame();
		m.setVisible(true);
	}
}
