import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class TacticsApplet extends Applet {

	boolean debug = true;
	String team = "2";
		
	Connection con;
	String clubId;
	Statement stmt;
	
	Image backbuffer;
	Graphics backbuffergc;
	
	final int SIZE_FACTOR = 2;

	int windowHeight = 780 / SIZE_FACTOR;
	int windowWidth = 1700 / SIZE_FACTOR;

	int pitchHeight = 513 / SIZE_FACTOR;
	int pitchWidth = 880 / SIZE_FACTOR;

	int pitchPosX = 90 / SIZE_FACTOR;
	int pitchPosY = 80 / SIZE_FACTOR;
	
	int goalX, goalY, goalSize;
	int goal2X, goal2Y, goal2Size;

	Color firstColor, secondColor;

	ArrayList<Player> players = new ArrayList<Player>();
	int[] first = new int[18];
	Point[] p = new Point[11];
	int selectedPoint = 15;
	PlayerRole roles[] = PlayerRole.values();
	
	JPanel pnlRole, pnlLineup;
	JList lstLineup, lstRest;
	DefaultListModel model = new DefaultListModel();
	DefaultListModel model2 = new DefaultListModel();
	DefaultComboBoxModel roleModel = new DefaultComboBoxModel();
	
	JButton btnSwap;
	JComboBox[] cmbRole = new JComboBox[11];
	
	public void start(){
		
		paint(getGraphics());
	}
	
	public void init(){
		
		clubId = getParameter("clubId");
		
		//Temp
		if (clubId == null || clubId == "") clubId = team;

		setSize(windowWidth, windowHeight);
		addMouseListener(new ml());
		
		setLayout(null);
		
		pnlLineup = new JPanel(true);
		pnlLineup.setSize(windowWidth - (pitchWidth + pitchPosX), windowHeight);
		pnlLineup.setLocation(pitchPosX + pitchWidth + pitchPosX - 20, 0);
		pnlLineup.setVisible(true);
		pnlLineup.setLayout(null);
		pnlLineup.setBackground(Color.green.darker());
		add(pnlLineup);
		
		
		lstLineup = new JList(model); 
		lstLineup.setLocation(80, pitchPosY);
		lstLineup.setSize(100, 307);
		lstLineup.setVisible(true);
		lstLineup.setBackground(Color.green.darker().darker());
		lstLineup.setForeground(Color.white);
		lstLineup.setSelectionBackground(Color.white);
		lstLineup.setSelectionForeground(Color.green.darker());
		lstLineup.addListSelectionListener(new ListSelectionListener() {

			public void valueChanged(ListSelectionEvent e) {
				selectedPoint = lstLineup.getSelectedIndex();
				fillRoles();
				repaint();
			}
				
		});
		pnlLineup.add(lstLineup);
		
		lstRest = new JList(model2); 
		lstRest.setLocation(185, pitchPosY);
		lstRest.setSize(100, 307);
		lstRest.setVisible(true);
		lstRest.setBackground(Color.green.darker().darker().darker());
		lstRest.setForeground(Color.white);
		lstRest.setSelectionBackground(Color.white);
		lstRest.setSelectionForeground(Color.green.darker());
		pnlLineup.add(lstRest);
		
		btnSwap = new JButton("Swap");
		btnSwap.setSize(100, 25);
		btnSwap.setLocation(130, pitchPosY + 307 + 5);
		btnSwap.setVisible(true);
		btnSwap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (lstLineup.getSelectedValue() != null && lstRest.getSelectedValue() != null){
					
					Player p1 = (Player) lstLineup.getSelectedValue();
					Player p2 = (Player) lstRest.getSelectedValue();
					
					p2.setName((lstLineup.getSelectedIndex() + 1) + ": " + p2.getName());
					if (lstLineup.getSelectedIndex() < 9)
						p1.setName(p1.getName().substring(3, p1.getName().length()));
					else
						p1.setName(p1.getName().substring(4, p1.getName().length()));
					
					model.set(lstLineup.getSelectedIndex(), p2);
					model2.set(lstRest.getSelectedIndex(), p1);
					
					
					try {
						for (int i = 1; i < 19; i++){
							Player p = (Player)model.get(i - 1);
							String sql = "UPDATE lineup set pl" + i + "Id=" + p.getId() + " WHERE clubId=" + clubId + ";";
							stmt.execute(sql);
							System.out.println(i + ". " + p.getId());
						}
						
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					repaint();
				}
			}
		});
		pnlLineup.add(btnSwap);
		
		for (int i = 0; i < 11; i++)
			p[i] = new Point(20 + i * 15, 10);
		
		backbuffer = createImage(windowWidth - 200, windowHeight);
		backbuffergc = backbuffer.getGraphics();
		
		goalSize = 59;
		goalX = pitchPosX + pitchWidth;
		goalY =(pitchHeight - goalSize) / 2 + pitchPosY;
		goal2Size = 59;
		goal2X = pitchPosX;
		goal2Y = (pitchHeight - goalSize) / 2 + pitchPosY;
		
		try {
			Class.forName("org.postgresql.Driver");
			
			String path = "http://" + getCodeBase().getHost();
			
			String url = "jdbc:postgresql://87.104.160.134/fb?user=footie&password=nytpw";
			//url = "jdbc:postgresql://192.168.0.130/?user=postgres&password=Lommen";
			if (!debug) url = "jdbc:postgresql:" + path + "?user=postgres&password=Lommen";
			
			System.out.println(url);
			con = DriverManager.getConnection(url);
			
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sql = "SELECT firstColor, secondColor FROM club WHERE clubId=" + clubId;

		try {
			ResultSet res = stmt.executeQuery(sql);

			if (res.next()){
				switch (res.getInt(1)){

				case 1:
					firstColor = Color.red;
					break;

				case 2:
					firstColor = Color.white;
					break;

				case 3:
					firstColor = Color.blue;
					break;

				}
				switch (res.getInt(2)){

				case 1:
					secondColor = Color.red;
					break;

				case 2:
					secondColor = Color.white;
					break;

				case 3:
					secondColor = Color.blue;
					break;
					
				case 4:
					secondColor = Color.black;
					break;

				}
			}
			
			
			sql = "SELECT * FROM lineup WHERE clubId=" + clubId + ";";
			res = stmt.executeQuery(sql);
			
			if (res.next()){
				for (int i = 1; i < 12; i++){

					String s = res.getString("pos" + i);

//					System.out.print("\"" + s + "\", ");
					
					String pos[] = s.split(",");
					
					p[i-1].setX((Integer.parseInt(pos[0]) / SIZE_FACTOR) * 2 + pitchPosX);
					p[i-1].setY(Integer.parseInt(pos[1]) / SIZE_FACTOR + pitchPosY);
				}
			}

			sql = "SELECT * FROM lineup WHERE clubId=" + clubId + ";";
			res = stmt.executeQuery(sql);
			
			if (res.next()){
				for (int i = 1; i < 12; i++){
					
					roles[i-1] = null;
					
					int value = res.getInt("role" + i);
					for (PlayerRole p : PlayerRole.values())
						if (p.ordinal() == value)
							roles[i-1] = p;
				}
			}
			
			sql = "SELECT * FROM lineup WHERE clubId=" + clubId + ";";
			res = stmt.executeQuery(sql);

			if (res.next()){

				for (int i = 1; i < 19; i++){
					first[i-1] = res.getInt("pl" + i + "Id");
				}
			}
			
			for (int i = 1; i < 19; i++){
				
				sql = "SELECT firstname, lastname FROM person WHERE personId=" + first[i - 1] + ";";
				res = stmt.executeQuery(sql);
				
				if (i < 12){
					
					cmbRole[i-1] = new JComboBox(new DefaultComboBoxModel(PlayerRole.values()));
					cmbRole[i-1].setLocation(0, pitchPosY + (i-1) * 17);
					cmbRole[i-1].setSize(75, 16);
					cmbRole[i-1].setVisible(true);
					final int q = i;
					cmbRole[i-1].addItemListener(new ItemListener() {

					
						public void itemStateChanged(ItemEvent e) {
							
							try {
								JComboBox cmb = (JComboBox)e.getSource();
								PlayerRole pr = (PlayerRole)cmb.getSelectedItem();
								String sql = "UPDATE lineup SET role" + q + "=" + pr.ordinal() + " WHERE clubId=" + clubId + ";";
								stmt.execute(sql);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}

					});
					pnlLineup.add(cmbRole[i-1]);
				
				}
				if (res.next()){
					model.addElement(new Player(res.getString(1).substring(0, 1) + ". " + res.getString(2), first[i-1]));

				}
			}

			sql = "SELECT firstname, lastname, p.personId FROM person p, contract c WHERE p.personId=c.personId AND c.clubId=" + clubId;
			res = stmt.executeQuery(sql);
			
			while (res.next()){
				Player p = new Player(res.getString(1).substring(0, 1) + ". " + res.getString(2), res.getInt(3));
				boolean add = true;
				
				for (int i = 0; i < model.getSize(); i++){
					
					Player r = (Player) model.get(i);
					
					if (r.getId() == p.getId())
						add = false;
				}
				
				if (add)
					model2.addElement(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		for (int i = 0; i < 11; i++)
//			cmbRole[i].setModel(new DefaultComboBoxModel(PlayerRole.values()));
		
		for (int i = 0; i < 11; i++)
			for (PlayerRole p : PlayerRole.values())
				if (roles[i].ordinal() == p.ordinal())
					cmbRole[i].setSelectedIndex(p.ordinal());
		
	}

	private void fillRoles(){
		
		DefaultComboBoxModel m = new DefaultComboBoxModel();
		roleModel.removeAllElements();
		
		ArrayList<PlayerRole> roles = new ArrayList<PlayerRole>();
		
		if (selectedPoint < 11){
			if (p[selectedPoint].getX() < pitchPosX + 35)
				roles.add(PlayerRole.GK);

			if (p[selectedPoint].getX() < pitchPosX + 150){

				if (p[selectedPoint].getY() > pitchPosY + pitchHeight / 1.4 || p[selectedPoint].getY() < pitchPosY + pitchHeight / 3.2){
					roles.add(PlayerRole.SB);
					roles.add(PlayerRole.WB);
				}

				if (p[selectedPoint].getY() < pitchPosY + pitchHeight / 1.3 && p[selectedPoint].getY() > pitchPosY + pitchHeight / 4){
					roles.add(PlayerRole.CB);
					roles.add(PlayerRole.DM);
				}


			}
			else if (p[selectedPoint].getX() < pitchPosX + 370){
				if (p[selectedPoint].getY() < pitchPosY + pitchHeight / 1.3 && p[selectedPoint].getY() > pitchPosY + pitchHeight / 4){
					if (p[selectedPoint].getX() < pitchPosX + 300) roles.add(PlayerRole.CM);
					if (p[selectedPoint].getX() < pitchPosX + 250) roles.add(PlayerRole.DM);
					if (p[selectedPoint].getX() > pitchPosX + 290) roles.add(PlayerRole.AM);
				}

				if (p[selectedPoint].getY() > pitchPosY + pitchHeight / 1.4 || p[selectedPoint].getY() < pitchPosY + pitchHeight / 3.2){
					roles.add(PlayerRole.SM);
					roles.add(PlayerRole.WF);
				}
			}
			else
				roles.add(PlayerRole.CF);

		}
		
	}
	
	public void paint(Graphics g) {
	
		Font font = new Font("Helvetica", Font.PLAIN,  10);
		backbuffergc.setFont(font);
		
		Graphics2D g2d = (Graphics2D)g;
		FontMetrics fontMetrics = getFontMetrics(font);
		
		backbuffergc.setColor(Color.GREEN.darker());
		backbuffergc.fillRect(0, 0, windowWidth, windowHeight);

		//Football pitch
		backbuffergc.setColor(Color.WHITE);
		backbuffergc.drawRect(pitchPosX, pitchPosY, pitchWidth, pitchHeight);//Banen
		backbuffergc.drawLine(pitchWidth/SIZE_FACTOR+pitchPosX, pitchPosY, pitchWidth / SIZE_FACTOR + pitchPosX, pitchPosY + pitchHeight);//midterlinje
		backbuffergc.drawRect(pitchPosX, (pitchHeight - 322 / SIZE_FACTOR) / SIZE_FACTOR + pitchPosY, 132 / SIZE_FACTOR, 322 / SIZE_FACTOR);//straffefelt 178
		backbuffergc.drawRect(pitchPosX + pitchWidth - 132 / SIZE_FACTOR, (pitchHeight - 322 / SIZE_FACTOR) / SIZE_FACTOR + pitchPosX, 132 / SIZE_FACTOR, 322 / SIZE_FACTOR);//straffefelt
		backbuffergc.drawRect(pitchPosX, (pitchHeight - 146 / SIZE_FACTOR) / SIZE_FACTOR + pitchPosY, 44 / SIZE_FACTOR, 146 / SIZE_FACTOR);//målfelt
		backbuffergc.drawRect(pitchPosX + pitchWidth - 44 / SIZE_FACTOR, (pitchHeight - 146 / SIZE_FACTOR) / SIZE_FACTOR + pitchPosX, 44 / SIZE_FACTOR, 146 / SIZE_FACTOR);//målfelt
		backbuffergc.drawOval((pitchWidth - 146 / SIZE_FACTOR) / SIZE_FACTOR + pitchPosX, (pitchHeight - 146 / SIZE_FACTOR) / SIZE_FACTOR + pitchPosY, 146 / SIZE_FACTOR, 146 / SIZE_FACTOR);//midtercirkel
		backbuffergc.drawArc(pitchPosX + 88 / SIZE_FACTOR - 73 / SIZE_FACTOR, pitchHeight / SIZE_FACTOR + pitchPosY - 73 / SIZE_FACTOR, 146 / SIZE_FACTOR, 146 / SIZE_FACTOR, -52, 105);//straffehalv-cirkel
		backbuffergc.drawArc(pitchPosX + pitchWidth - 88 / SIZE_FACTOR - 73 / SIZE_FACTOR, pitchHeight / SIZE_FACTOR + pitchPosY - 73 / SIZE_FACTOR, 146 / SIZE_FACTOR, 146 / SIZE_FACTOR, 128, 105);//straffehalv-cirkel
		backbuffergc.drawArc(pitchPosX - 8 / SIZE_FACTOR, pitchPosY - 8 / SIZE_FACTOR, 16 / SIZE_FACTOR, 16 / SIZE_FACTOR, 0, -90);//Hjørneflag
		backbuffergc.drawArc(pitchPosX + pitchWidth - 8 / SIZE_FACTOR, pitchPosY - 8 / SIZE_FACTOR, 16 / SIZE_FACTOR, 16 / SIZE_FACTOR, -90, -90);//Hjørneflag
		backbuffergc.drawArc(pitchPosX - 8 / SIZE_FACTOR, pitchPosY + pitchHeight - 8 / SIZE_FACTOR, 16 / SIZE_FACTOR, 16 / SIZE_FACTOR, 0, 90);//Hjørneflag
		backbuffergc.drawArc(pitchPosX + pitchWidth - 8 / SIZE_FACTOR, pitchPosY + pitchHeight - 8 / SIZE_FACTOR, 16 / SIZE_FACTOR, 16 / SIZE_FACTOR, -180, -90);//Hjørneflag		
		backbuffergc.fillOval(pitchWidth / SIZE_FACTOR + pitchPosX - 3, pitchHeight / SIZE_FACTOR + pitchPosY - 3, 6, 6);//midterpletten
		backbuffergc.fillOval(pitchPosX + 88 / SIZE_FACTOR - 1, pitchHeight / SIZE_FACTOR + pitchPosY - 1, 3, 3);//straffepletten
		backbuffergc.fillOval(pitchPosX + pitchWidth - 88 / SIZE_FACTOR - 1, pitchHeight / SIZE_FACTOR + pitchPosY - 1, 3, 3);//straffepletten

		backbuffergc.setColor(firstColor);
		for (int i=0; i<11;i++){
			if (i == selectedPoint){
				backbuffergc.setColor(secondColor);
				backbuffergc.fillOval(p[i].getX() - 7, p[i].getY() - 5, 14, 14);
				backbuffergc.setColor(firstColor);
				backbuffergc.drawOval(p[i].getX() - 7, p[i].getY() - 5, 14, 14);
				backbuffergc.drawString(Integer.toString(i+1), p[i].getX() - fontMetrics.stringWidth(Integer.toString(i+1)) / 2, p[i].getY() + 5);
			}
			else{
				backbuffergc.setColor(firstColor);
				backbuffergc.fillOval(p[i].getX() - 7, p[i].getY() - 5, 14, 14);
				backbuffergc.setColor(secondColor);
				backbuffergc.drawOval(p[i].getX() - 7, p[i].getY() - 5, 14, 14);
				backbuffergc.drawString(Integer.toString(i+1), p[i].getX() - fontMetrics.stringWidth(Integer.toString(i+1)) / 2, p[i].getY() + 5);
				backbuffergc.setColor(firstColor);
			}
			
			if (model.getSize() > 10){
				Player pl = (Player)model.getElementAt(i);
				backbuffergc.drawString(pl.getName(), p[i].getX() - fontMetrics.stringWidth(pl.getName()) / 2, p[i].getY() + 18);
			}
			
			
		}
		g.drawImage(backbuffer, 0, 0, windowWidth - 200, windowHeight, this);
		
		pnlLineup.repaint();
	}

	private class Point{
		
		int x = 0;
		int y = 0;
		
		public Point(){
			
		}
		
		public Point(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public int getX() {
			return x;
		}
		public void setX(int x) {
			this.x = x;
		}
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
		
	}
	
	private class ml implements MouseListener{

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent arg0) {
			
		}

		public void mouseEntered(MouseEvent arg0) {
			
		}

		public void mouseExited(MouseEvent arg0) {
			
		}

		public void mousePressed(MouseEvent arg0) {
			if (arg0.getX() > pitchPosX && arg0.getX() < pitchPosX + pitchWidth &&
					arg0.getY() > pitchPosY && arg0.getY() < pitchPosY + pitchHeight){
				for (int i = 0; i < 11; i++)
					if (Math.abs(p[i].getX() - arg0.getX()) < 7 && Math.abs(p[i].getY() - arg0.getY()) < 7)
						if (selectedPoint == i){
							selectedPoint = 15;
						}
						else{
							selectedPoint = i;
							lstLineup.setSelectedIndex(i);
						}

				if (selectedPoint < 11){
					p[selectedPoint].setX(arg0.getX());
					p[selectedPoint].setY(arg0.getY());

					fillRoles();

					String sql = "UPDATE lineup SET pos" + (selectedPoint + 1) + "='" +
					(((p[selectedPoint].getX() - pitchPosX) * SIZE_FACTOR) / 2) + "," + 
					((p[selectedPoint].getY() - pitchPosY) * SIZE_FACTOR) + "' WHERE clubId=" + 
							clubId + ";";
					
					try {
						stmt.execute(sql);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			paint(getGraphics());
		}

		public void mouseReleased(MouseEvent arg0) {
			
			
			
		}
		
	}
	
	public class Player{
		
		String name = "";
		int id = 0;

		public Player(String name, int id) {
			this.id = id;
			this.name = name;
		}
		
		public String toString(){
			return name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
		
		
	}
	
	private enum PlayerRole {
		GK,//Keeper
		SB,//Side back
		CB,//Center back
		SW,//Sweeper
		WB,//Wing back
		DM,//Defensive mid
		SM,//Side mid
		AM,//Attacking mid
		CM,//Center mid
		WF,//Wing forward
		CF//Center forward
	}
}
