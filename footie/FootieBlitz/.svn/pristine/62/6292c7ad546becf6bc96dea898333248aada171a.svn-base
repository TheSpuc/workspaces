package main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Savepoint;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import data.DAOCake;

import model.Settings;
import model.Team;
import model.Player;


public class SettingsFrame extends JFrame{

	Settings settings;
	JTextArea txtCommentary;
	JLabel lblAgMod, lblSpeedMod, lblSharpTurnLimit, lblSpeed, lblDribble, lblAcceleration, lblShooting, lblShotpower, 
	lblPassing, lblTechnique, lblStrength, lblTackling, lblAgility, lblVision, lblJumping, lblStamina, lblMarking;
	JSpinner spnAgMod, spnSpeedMod, spnSharpTurnLimit, spnSpeed, spnAcceleration, spnDribble, spnShooting, spnShotpower,
	spnPassing, spnTechnique, spnStrength, spnTackling, spnAgility, spnVision, spnJumping, spnStamina, spnMarking;
	SpinnerListener lstn;
	JButton btnSave;
	JButton[] btnAll = new JButton[10];
	JComboBox cbPlayer;
	Team teamA, teamB;
	Player currentPlayer;
	JSlider[] sliders;
	String[] sliderTexts = {
			"all",
			"aggression",
			"closingdown",
			"mentality",
			"longshots",
			"throughballs",
			"dribble",
			"runs",
			"crossball",
			"tightmarking",
			"passing"};
	
			
	public SettingsFrame(Team teamA, Team teamB){
		this.teamA = teamA;
		this.teamB = teamB;
		settings = Settings.getInstance();
		
		
		lstn = new SpinnerListener();
		
		setLocation(0, 0);
		setSize(900, 700);
		setVisible(true);
		setLayout(null);
		setTitle("SettingsFrame");
		
		for (int i = 0; i < 10; i++){
			
			btnAll[i] = new JButton("+");
			btnAll[i].setToolTipText("Apply to all");
			btnAll[i].setLocation(230, 200 + i * 30);
			btnAll[i].setSize(40, 25);
			add(btnAll[i]);
		}
			
		
		lblAgMod = new JLabel("Agility Mod:");
		lblAgMod.setLocation(10, 20);
		lblAgMod.setSize(100, 25);
		add(lblAgMod);
		
		lblSpeedMod = new JLabel("Speed Mod:");
		lblSpeedMod.setLocation(10, 50);
		lblSpeedMod.setSize(100, 25);
		add(lblSpeedMod);
		
		lblSharpTurnLimit = new JLabel("Sharp turn limit:");
		lblSharpTurnLimit.setLocation(10, 80);
		lblSharpTurnLimit.setSize(100, 25);
		add(lblSharpTurnLimit);

		//abilities
		
		
		lblSpeed = new JLabel("Speed:");
		lblSpeed.setLocation(10, 200);
		lblSpeed.setSize(100, 25);
		add(lblSpeed);
		

		lblAcceleration = new JLabel("Acceleration:");
		lblAcceleration.setLocation(10, 230);
		lblAcceleration.setSize(100, 25);
		add(lblAcceleration);
		
		lblDribble = new JLabel("Dribble:");
		lblDribble.setLocation(10, 260);
		lblDribble.setSize(100, 25);
		add(lblDribble);
		
		lblShooting = new JLabel("Shooting:");
		lblShooting.setLocation(10, 290);
		lblShooting.setSize(100, 25);
		add(lblShooting);
		
		lblShotpower = new JLabel("Shotpower:");
		lblShotpower.setLocation(10, 320);
		lblShotpower.setSize(100, 25);
		add(lblShotpower);
		
		lblPassing = new JLabel("Passing:");
		lblPassing.setLocation(10, 350);
		lblPassing.setSize(100, 25);
		add(lblPassing);
		
		lblTechnique = new JLabel("Technique:");
		lblTechnique.setLocation(10, 380);
		lblTechnique.setSize(100, 25);
		add(lblTechnique);
		
		lblStrength = new JLabel("Strength:");
		lblStrength.setLocation(10, 410);
		lblStrength.setSize(100, 25);
		add(lblStrength);
		
		lblAgility = new JLabel("Agility:");
		lblAgility.setLocation(10, 440);
		lblAgility.setSize(100, 25);
		add(lblAgility);
		
		lblTackling = new JLabel("Tackling:");
		lblTackling.setLocation(10, 470);
		lblTackling.setSize(100, 25);
		add(lblTackling);
		
		lblVision = new JLabel("Vision:");
		lblVision.setLocation(10, 500);
		lblVision.setSize(100, 25);
		add(lblVision);
		
		lblJumping = new JLabel("Jumping:");
		lblJumping.setLocation(10, 530);
		lblJumping.setSize(100, 25);
		add(lblJumping);
		
		lblStamina = new JLabel("Stamina:");
		lblStamina.setLocation(10, 560);
		lblStamina.setSize(100, 25);
		add(lblStamina);
		
		lblMarking = new JLabel("Marking:");
		lblMarking.setLocation(10, 590);
		lblMarking.setSize(100, 25);
		add(lblMarking);
		
		//Spinners
		spnAgMod = new JSpinner(new SpinnerNumberModel(0, 0, 1, 0.0001));
		spnAgMod.setEditor(new JSpinner.NumberEditor(spnAgMod, "#0.0000"));
		spnAgMod.setValue(settings.getAgilitymod());
		spnAgMod.setLocation(110, 20);
		spnAgMod.setSize(100, 25);
		spnAgMod.addChangeListener(lstn);
		add(spnAgMod);
		
		spnSpeedMod = new JSpinner(new SpinnerNumberModel(0, 0, 100, 0.01));
		spnSpeedMod.setEditor(new JSpinner.NumberEditor(spnSpeedMod, "#0.000"));
		spnSpeedMod.setValue(settings.getSpeedmod());
		spnSpeedMod.setLocation(110, 50);
		spnSpeedMod.setSize(100, 25);
		spnSpeedMod.addChangeListener(lstn);
		add(spnSpeedMod);
		
		spnSharpTurnLimit = new JSpinner(new SpinnerNumberModel(0, 0, 6, 0.1));
		spnSharpTurnLimit.setEditor(new JSpinner.NumberEditor(spnSharpTurnLimit, "#0.0000"));
		spnSharpTurnLimit.setValue(settings.getSharpTurnLimit());
		spnSharpTurnLimit.setLocation(110, 80);
		spnSharpTurnLimit.setSize(100, 25);
		spnSharpTurnLimit.addChangeListener(lstn);
		add(spnSharpTurnLimit);
		
		spnSpeed = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnSpeed.setEditor(new JSpinner.NumberEditor(spnSpeed, "#0"));
		spnSpeed.setLocation(110, 200);
		spnSpeed.setSize(100, 25);
		spnSpeed.addChangeListener(lstn);
		add(spnSpeed);
		
		spnAcceleration = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnAcceleration.setEditor(new JSpinner.NumberEditor(spnAcceleration, "#0"));
		spnAcceleration.setLocation(110, 230);
		spnAcceleration.setSize(100, 25);
		spnAcceleration.addChangeListener(lstn);
		add(spnAcceleration);
		
		spnDribble = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnDribble.setEditor(new JSpinner.NumberEditor(spnDribble, "#0"));
		spnDribble.setLocation(110, 260);
		spnDribble.setSize(100, 25);
		spnDribble.addChangeListener(lstn);
		add(spnDribble);
		
		spnShooting = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnShooting.setEditor(new JSpinner.NumberEditor(spnShooting, "#0"));
		spnShooting.setLocation(110, 290);
		spnShooting.setSize(100, 25);
		spnShooting.addChangeListener(lstn);
		add(spnShooting);
		
		spnShotpower = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnShotpower.setEditor(new JSpinner.NumberEditor(spnShotpower, "#0"));
		spnShotpower.setLocation(110, 320);
		spnShotpower.setSize(100, 25);
		spnShotpower.addChangeListener(lstn);
		add(spnShotpower);
		
		spnPassing = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnPassing.setEditor(new JSpinner.NumberEditor(spnPassing, "#0"));
		spnPassing.setLocation(110, 350);
		spnPassing.setSize(100, 25);
		spnPassing.addChangeListener(lstn);
		add(spnPassing);
		
		spnTechnique = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnTechnique.setEditor(new JSpinner.NumberEditor(spnTechnique, "#0"));
		spnTechnique.setLocation(110, 380);
		spnTechnique.setSize(100, 25);
		spnTechnique.addChangeListener(lstn);
		add(spnTechnique);
		
		spnStrength = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnStrength.setEditor(new JSpinner.NumberEditor(spnStrength, "#0"));
		spnStrength.setLocation(110, 410);
		spnStrength.setSize(100, 25);
		spnStrength.addChangeListener(lstn);
		add(spnStrength);
		
		spnAgility = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnAgility.setEditor(new JSpinner.NumberEditor(spnAgility, "#0"));
		spnAgility.setLocation(110, 440);
		spnAgility.setSize(100, 25);
		spnAgility.addChangeListener(lstn);
		add(spnAgility);
		
		spnTackling = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnTackling.setEditor(new JSpinner.NumberEditor(spnTackling, "#0"));
		spnTackling.setLocation(110, 470);
		spnTackling.setSize(100, 25);
		spnTackling.addChangeListener(lstn);
		add(spnTackling);
		
		spnVision = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnVision.setEditor(new JSpinner.NumberEditor(spnVision, "#0"));
		spnVision.setLocation(110, 500);
		spnVision.setSize(100, 25);
		spnVision.addChangeListener(lstn);
		add(spnVision);
		
		spnJumping = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnJumping.setEditor(new JSpinner.NumberEditor(spnJumping, "#0"));
		spnJumping.setLocation(110, 530);
		spnJumping.setSize(100, 25);
		spnJumping.addChangeListener(lstn);
		add(spnJumping);
		
		spnStamina = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnStamina.setEditor(new JSpinner.NumberEditor(spnStamina, "#0"));
		spnStamina.setLocation(110, 560);
		spnStamina.setSize(100, 25);
		spnStamina.addChangeListener(lstn);
		add(spnStamina);
		
		spnMarking = new JSpinner(new SpinnerNumberModel(0, 0, 200, 1));
		spnMarking.setEditor(new JSpinner.NumberEditor(spnMarking, "#0"));
		spnMarking.setLocation(110, 590);
		spnMarking.setSize(100, 25);
		spnMarking.addChangeListener(lstn);
		add(spnMarking);
		
		btnSave = new JButton("Save settings");
		btnSave.setSize(200, 25);
		btnSave.setLocation(10, 120);
		btnSave.addActionListener(new btnListener());
		add(btnSave);
		
		cbPlayer = new JComboBox();
		cbPlayer.setSize(200, 25);
		cbPlayer.setLocation(10, 160);
		cbPlayer.addActionListener(new btnListener());
		add(cbPlayer);
		
		sliders = new JSlider[sliderTexts.length];
		ToolTipManager.sharedInstance().setInitialDelay(0);
		
		for (int i = 0; i < sliderTexts.length; i++){
			
			JLabel lbl = new JLabel(sliderTexts[i]);
			lbl.setLocation(300, 200 + i * 30);
			lbl.setSize(100, 25);
			add(lbl);
			
			sliders[i] = new JSlider(1, 100);
			sliders[i].setLocation(400, 200 + i * 30);
			sliders[i].setSize(100, 25);
			final String sliderText = sliderTexts[i];
			
			sliders[i].addChangeListener(new ChangeListener() {
		        public void stateChanged(ChangeEvent ce) {
		            JSlider slider = (JSlider)ce.getSource();
		            slider.setToolTipText("" + slider.getValue());
		           //Her prøver jeg at sætte all-slideren til at bestemme hvad alle de andre 
		           //slidere skal være.
		            if (slider.equals(sliders[0])){
		            	for (int j = 1; j < sliderTexts.length; j++){
		            		sliders[j].setValue(slider.getValue());
		            		System.out.println("slider nr " + j + " slider value " + slider.getValue());
		            	}
		            }
		        }
		    });

			
			add(sliders[i]);
		}
		
		JButton btnSaveSliders = new JButton("Ok");
		btnSaveSliders.setLocation(400, 200 + sliderTexts.length * 30);
		btnSaveSliders.setSize(100, 25);
		btnSaveSliders.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				
				updateSliders();
				
				for (Player p : getTeamA().getPlayers()){
					String sql = "UPDATE playertactics SET " +
					" aggression=" + p.getAggression() +
					", closingdown=" + p.getClosingdown() +
					", mentality=" + p.getMentality() +
					", longshots=" + p.getLongshots() +
					", throughballs=" + p.getThroughballs() +
					", dribble=" + p.getDribble() +
					", crossball=" + p.getCrossball() +
					", runs=" + p.getRuns() +
					", tightmarking=" + p.getPressing() +
					", passing=" + p.getShortLongPassing() +
					" WHERE personid=" + p.getId();

					try {
						DAOCake.executeSimpleStatement(sql);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
				for (Player p : getTeamB().getPlayers()){
					String sql = "UPDATE playertactics SET " +
					" aggression=" + p.getAggression() +
					", closingdown=" + p.getClosingdown() +
					", mentality=" + p.getMentality() +
					", throughballs=" + p.getThroughballs() +
					", longshots=" + p.getLongshots() +
					", dribble=" + p.getDribble() +
					", crossball=" + p.getCrossball() +
					", runs=" + p.getRuns() +
					", tightmarking=" + p.getPressing() +
					", passing=" + p.getShortLongPassing() +
					" WHERE personid=" + p.getId();

					System.out.println("Player tactics saved for " + p.getId());
					try {
						DAOCake.executeSimpleStatement(sql);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		add(btnSaveSliders);
		
		txtCommentary = new JTextArea();
		txtCommentary.setLocation(510, 20);
		txtCommentary.setSize(250, 500);
		add(txtCommentary);
		
		setSize(800, 701);
	}
	
	public void addCommentary(String str){
		txtCommentary.setText(str + "\n" + txtCommentary.getText());
	}
	
	
	private void updateSliders(){
	
		currentPlayer.setAggression((byte)sliders[1].getValue());
		currentPlayer.setClosingdown((byte)sliders[2].getValue());
		currentPlayer.setMentality((byte)sliders[3].getValue());
		currentPlayer.setLongshots((byte)sliders[4].getValue());
		currentPlayer.setThroughballs((byte)sliders[5].getValue());
		currentPlayer.setDribble((byte)sliders[6].getValue());
		currentPlayer.setRuns((byte)sliders[7].getValue());
		currentPlayer.setCrossball((byte)sliders[8].getValue());
		currentPlayer.setPressing((byte)sliders[9].getValue());
		currentPlayer.setShortLongPassing((byte)sliders[10].getValue());
	}
	
	public Settings getSettings(){
		return settings;
	}
	
	public void visSpillere(){
		
		if (teamA != null && teamA.getPlayers() != null)
			for (int i = 0; i < teamA.getPlayers().size(); i++)
				cbPlayer.addItem(teamA.getPlayers().get(i));

		if (teamB != null && teamB.getPlayers() != null)
			for (int i = 0; i < teamB.getPlayers().size(); i++){
				cbPlayer.addItem(teamB.getPlayers().get(i));
			}
	}

	public class SpinnerListener implements ChangeListener{

		public void stateChanged(ChangeEvent e) {
			
			if (e.getSource() == spnAgMod){
				settings.setAgilitymod(Double.parseDouble(spnAgMod.getValue().toString()));
			}
			else if (e.getSource() == spnSpeedMod){
				settings.setSpeedmod(Double.parseDouble(spnSpeedMod.getValue().toString()));
			}
			else if (e.getSource() == spnSharpTurnLimit){
				settings.setSharpTurnLimit((Double.parseDouble(spnSharpTurnLimit.getValue().toString())));
			}
			
			else if (e.getSource() == spnSpeed){
				currentPlayer.setTopSpeed(Double.parseDouble(spnSpeed.getValue().toString()));
			}
			else if (e.getSource() == spnAcceleration){
				currentPlayer.setAcceleration(Double.parseDouble(spnAcceleration.getValue().toString()));
			}
			else if (e.getSource() == spnDribble){
				currentPlayer.setDribbling((Double.parseDouble(spnDribble.getValue().toString())));
			}
			else if (e.getSource() == spnShooting){
				currentPlayer.setShooting(Double.parseDouble(spnShooting.getValue().toString()));
			}
			else if (e.getSource() == spnShotpower){
				currentPlayer.setShotpower((Double.parseDouble(spnShotpower.getValue().toString())));
			}
			else if (e.getSource() == spnPassing){
				currentPlayer.setPassing((Double.parseDouble(spnPassing.getValue().toString())));
			}
			else if (e.getSource() == spnTechnique){
				currentPlayer.setTechnique((Double.parseDouble(spnTechnique.getValue().toString())));
			}
			else if (e.getSource() == spnStrength){
				currentPlayer.setStrength((Double.parseDouble(spnStrength.getValue().toString())));
			}
			else if (e.getSource() == spnAgility){
				currentPlayer.setAgility((Double.parseDouble(spnAgility.getValue().toString())));
			}
			else if (e.getSource() == spnTackling){
				currentPlayer.setTackling((Double.parseDouble(spnTackling.getValue().toString())));
			}
			else if (e.getSource() == spnVision){
				currentPlayer.setVision((Double.parseDouble(spnVision.getValue().toString())));
			}
			else if (e.getSource() == spnJumping){
				currentPlayer.setJumping((Double.parseDouble(spnJumping.getValue().toString())));
			}
			else if (e.getSource() == spnStamina){
				currentPlayer.setStamina((Double.parseDouble(spnStamina.getValue().toString())));
			}
			else if (e.getSource() == spnMarking){
				currentPlayer.setStamina((Double.parseDouble(spnMarking.getValue().toString())));
			}
		}
		
	}
	
	public class btnListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == btnAll[0]){
				for (Player p : currentPlayer.getMyTeam().getPlayers())
					p.setTopSpeed(Double.parseDouble(spnSpeed.getValue().toString()));
			}
			else if (e.getSource() == btnAll[1]){
				for (Player p : currentPlayer.getMyTeam().getPlayers())
				currentPlayer.setAcceleration(Double.parseDouble(spnAcceleration.getValue().toString()));
			}

			if (e.getSource() == btnSave)
				try {
					settings.saveSettingsFile("settings.dat");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			else if (e.getSource() == cbPlayer)
					currentPlayer = (Player)cbPlayer.getSelectedItem();

			System.out.println("Energy: " + currentPlayer.getEnergy());
			
			spnSpeed.setValue(((int)currentPlayer.getTopSpeed()));
			spnAcceleration.setValue(currentPlayer.getAcceleration());
			spnDribble.setValue(currentPlayer.getDribbling());
			spnShooting.setValue(currentPlayer.getShooting());
			spnShotpower.setValue(currentPlayer.getShotpower());
			spnPassing.setValue(currentPlayer.getPassing());		
			spnTechnique.setValue(currentPlayer.getTechnique());	
			spnStrength.setValue(currentPlayer.getStrength());
			spnAgility.setValue(currentPlayer.getAgility());		
			spnTackling.setValue(currentPlayer.getTackling());
			spnVision.setValue(currentPlayer.getVision());
			spnJumping.setValue(currentPlayer.getJumping());
			spnStamina.setValue(currentPlayer.getStamina());
			spnMarking.setValue(currentPlayer.getMarking());
			
			sliders[1].setValue(currentPlayer.getAggression());
			sliders[2].setValue(currentPlayer.getClosingdown());
			sliders[3].setValue(currentPlayer.getMentality());
			sliders[4].setValue(currentPlayer.getLongshots());
			sliders[5].setValue(currentPlayer.getThroughballs());
			sliders[6].setValue(currentPlayer.getDribble());
			sliders[7].setValue(currentPlayer.getRuns());
			sliders[8].setValue(currentPlayer.getCrossball());
			sliders[9].setValue(currentPlayer.getPressing());
			sliders[10].setValue(currentPlayer.getShortLongPassing());
			
		}



	}

	public Team getTeamA() {
		return teamA;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

}
