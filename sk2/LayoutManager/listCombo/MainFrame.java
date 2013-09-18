package listCombo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class MainFrame extends JFrame {
	
	private JButton save;
	private JTextField txfname;
	private JList<String> list;
	private JScrollPane jsScrollPane;
	private JComboBox<String> combo;
	private DefaultComboBoxModel<String> model;
	
	
	public MainFrame(){
		this.setLayout(new BorderLayout());
		this.setTitle("Vicki");
		this.setBounds(100, 100, 500, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		model = new DefaultComboBoxModel<>();
		
		
		//south related
		JPanel south = new JPanel(new FlowLayout());
		
		
		save = new JButton("Save");
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				addElement();
			}
		});
		int sh = save.getPreferredSize().height;
		save.setPreferredSize(new Dimension(100, sh));
		south.add(save);
		this.add(south, BorderLayout.SOUTH);
		
		//west related
		JPanel west = new JPanel();
		west.setLayout(new VerticalFlowLayout());
		
		txfname = new JTextField();
		int nw = txfname.getPreferredSize().height;
		txfname.setPreferredSize(new Dimension(100, nw));

		
		west.add(txfname);
		this.add(west, BorderLayout.WEST);
		
		
		//center related
		list = new JList<>(model);
		jsScrollPane = new JScrollPane(list);
		this.add(jsScrollPane);
		
		//east
		JPanel east = new JPanel(new VerticalFlowLayout());
		
		combo = new JComboBox<>(model);
		int ch = combo.getPreferredSize().height;
		combo.setPreferredSize(new Dimension(100, ch));
		east.add(combo);
		
		this.add(east, BorderLayout.EAST);
		
		
	}
	
	private void addElement(){
		String name = txfname.getText();
		model.addElement(name);
		txfname.setText("");
	}

	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.setVisible(true);
	}

}
