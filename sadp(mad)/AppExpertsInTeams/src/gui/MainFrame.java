package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.LineBorder;

public class MainFrame extends JFrame {

	private JButton infojb, searchjb, mapjb, contactjb;

	private JTextField search;

	private JTextArea textA;

	private JLabel picLabel, logoLabel;

	private JComboBox<String> dropdown;


	private JPanel northPanel, primaryPanel, centerPanel, southPanel;

	private ActionController controller;


	public static void main(String[] args) throws IOException{
		MainFrame m = new MainFrame();
	}

	public MainFrame() throws IOException{
		setBounds(100, 100, 350, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("AppenSkralde");
		setResizable(false);
		primaryPanel = new JPanel();
		primaryPanel.setLayout(new BorderLayout());
		primaryPanel.setBackground(new Color(24, 166, 222));
		add(primaryPanel);

		//controller
		controller = new ActionController();

		//south
		southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		southPanel.setBackground(new Color(24, 166, 222));
		primaryPanel.add(southPanel, BorderLayout.SOUTH);

		//info button
		infojb = new JButton("Info");
		infojb.setSize(50, 25);
		infojb.addActionListener(controller);;
		southPanel.add(infojb);

		//map button
		mapjb = new JButton("Kort");
		mapjb.setSize(50, 25);
		mapjb.addActionListener(controller);
		southPanel.add(mapjb);

		//contact button
		contactjb = new JButton("Kontakt");
		contactjb.setSize(50, 25);
		contactjb.addActionListener(controller);
		southPanel.add(contactjb);

		//north panel
		northPanel = new JPanel();
		northPanel.setLayout(new FlowLayout());
		northPanel.setBackground(new Color(24, 166, 222));
		primaryPanel.add(northPanel, BorderLayout.NORTH);

		//search
		search = new JTextField();
		search.setColumns(10);
		search.setBorder(new LineBorder(Color.BLACK));
		northPanel.add(search);

		searchjb = new JButton("Søg");
		searchjb.setSize(20, 25);
		searchjb.addActionListener(controller);
		northPanel.add(searchjb);

		//center
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		centerPanel.setBackground(new Color(24, 166, 222));
		primaryPanel.add(centerPanel, BorderLayout.CENTER);


		//textA
		textA = new JTextArea();
		textA.setEditable(false);
		textA.setColumns(25);
		textA.setBorder(new LineBorder(Color.BLACK));
		centerPanel.add(textA);

		//Picture
		picLabel = new JLabel();
		BufferedImage img1 = ImageIO.read(new File("/Users/mmb/git/workspaces/sadp(mad)/AppExpertsInTeams/skrald.png"));
		Image dimg1 = img1.getScaledInstance(380, 280, Image.SCALE_SMOOTH);
		picLabel.setIcon(new ImageIcon(dimg1));
		picLabel.setSize(300, 200);
		centerPanel.add(picLabel);


		//logo
		logoLabel = new JLabel();
		BufferedImage img2 = ImageIO.read(new File("/Users/mmb/git/workspaces/sadp(mad)/AppExpertsInTeams/logo.png"));
		Image dimg2 = img2.getScaledInstance(214, 70, Image.SCALE_SMOOTH);
		logoLabel.setIcon(new ImageIcon(dimg2));
		centerPanel.add(logoLabel);

		setVisible(true);
	}

	private class ActionController implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(e.getSource().equals(infojb)){
				textA.setText("");
				String insert = getInfo("info");
				search.setText("");
				textA.append(insert);
				try {
					updateLabel("info");
				} catch (IOException e1) {
					//Do nothing
				}
			}else if(e.getSource().equals(searchjb)){
				textA.setText("");
				String insert = getInfo(search.getText());
				textA.append(insert);
				try {
					updateLabel(search.getText());
				} catch (IOException e1) {
					//Do nothing
				}
			}else if(e.getSource().equals(mapjb)){
				textA.setText("");
				try {
					updateLabel("map");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else if(e.getSource().equals(contactjb)){
				textA.setText("Provas kontakt");
				try {
					updateLabel("kontakt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}


	public String getInfo(String search){
		if(search.toLowerCase().equals("brød")){
			return "Det er organisk";
		}else if(search.toLowerCase().equals("banan")){
			return "Det er organisk";
		}else if(search.toLowerCase().equals("plastikkrus")){
			return "Det er uorganisk";
		}else if(search.toLowerCase().equals("info")){
			return "Hvorfor er det vigtigt at sortere affald?";
		}else return "";
	}

	public void updateLabel(String search) throws IOException{
		if(search.toLowerCase().trim().equals("brød")){
			BufferedImage img = ImageIO.read(new File("/Users/mmb/git/workspaces/sadp(mad)/AppExpertsInTeams/grov.jpg"));
			Image dimg = img.getScaledInstance(300, 280, Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(dimg));
			picLabel.setBorder(new LineBorder(Color.BLACK));

			//colors
			centerPanel.setBackground(new Color(57, 222, 24));
			primaryPanel.setBackground(new Color(57, 222, 24));
			northPanel.setBackground(new Color(57, 222, 24));
			southPanel.setBackground(new Color(57, 222, 24));

		}else if(search.toLowerCase().trim().equals("banan")){
			BufferedImage img = ImageIO.read(new File("/Users/mmb/git/workspaces/sadp(mad)/AppExpertsInTeams/banan.jpg"));
			Image dimg = img.getScaledInstance(300, 280, Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(dimg));
			picLabel.setBorder(new LineBorder(Color.BLACK));

			//colors
			centerPanel.setBackground(new Color(57, 222, 24));
			primaryPanel.setBackground(new Color(57, 222, 24));
			northPanel.setBackground(new Color(57, 222, 24));
			southPanel.setBackground(new Color(57, 222, 24));

		}else if(search.toLowerCase().trim().equals("plastikkrus")){
			BufferedImage img = ImageIO.read(new File("/Users/mmb/git/workspaces/sadp(mad)/AppExpertsInTeams/plastik.jpg"));
			Image dimg = img.getScaledInstance(300, 280, Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(dimg));
			picLabel.setBorder(new LineBorder(Color.BLACK));

			//colors
			centerPanel.setBackground(new Color(161, 171, 161));
			primaryPanel.setBackground(new Color(161, 171, 161));
			northPanel.setBackground(new Color(161, 171, 161));
			southPanel.setBackground(new Color(161, 171, 161));

		}else if(search.toLowerCase().trim().equals("info") || search.toLowerCase().trim().equals("kontakt")){
			BufferedImage img = ImageIO.read(new File("/Users/mmb/git/workspaces/sadp(mad)/AppExpertsInTeams/skrald.png"));
			Image dimg = img.getScaledInstance(380, 280, Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(dimg));
			picLabel.setBorder(null);

			//colors
			centerPanel.setBackground(new Color(24, 166, 222));
			primaryPanel.setBackground(new Color(24, 166, 222));
			northPanel.setBackground(new Color(24, 166, 222));
			southPanel.setBackground(new Color(24, 166, 222));

		}else if(search.toLowerCase().trim().equals("map")){
			BufferedImage img = ImageIO.read(new File("/Users/mmb/git/workspaces/sadp(mad)/AppExpertsInTeams/haderslev.png"));
			Image dimg = img.getScaledInstance(300, 280, Image.SCALE_SMOOTH);
			picLabel.setIcon(new ImageIcon(dimg));
			picLabel.setBorder(new LineBorder(Color.BLACK));

			//colors
			centerPanel.setBackground(new Color(24, 166, 222));
			primaryPanel.setBackground(new Color(24, 166, 222));
			northPanel.setBackground(new Color(24, 166, 222));
			southPanel.setBackground(new Color(24, 166, 222));
		}
	}
}
