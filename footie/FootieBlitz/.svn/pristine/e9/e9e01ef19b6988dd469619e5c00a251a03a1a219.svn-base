package model;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;


public class Settings {

	double agilitymod = 0;
	public double speedmod = 0;
	double sharpTurnLimit = 0;
	double Speed = 0, Dribble = 0, Acceleration = 0, Shooting = 0, Shotpower = 0, Passing = 0, Technique = 0,
	Strength = 0, Agility = 0, Tackling = 0, Vision = 0, Jumping = 0, Stamina = 0, Marking = 0;

	String sqlConn = "";
	String replayDir = "";
	boolean sysoutAlt = false;
	boolean sysoutBold = false, sysoutPlayer = false, sysoutMoveGK = false, sysoutSave = false, sysoutBoldBehaviour = false, sysoutStateOppHasBall = false;
	boolean sysoutMarking = false, sysoutAIRunOffBall = false, sysoutAIRunWithBall = false, sysoutAIPass = false, sysoutStateHasBall = false;
	boolean sysoutAIHoldUpBall = false, sysoutDecision = false;
	
	HashMap<String, Boolean> sysouts = new HashMap<String, Boolean>();  
	
	public final static boolean SO = false;
	public final static boolean DEBUG = false;

	public static Settings instance = null;
	
	boolean DoNotSysout = true;
	
	
	public static Settings getInstance(){
		if (instance == null){
			instance = new Settings();
			instance.loadSettingsFile("settings.dat");
		}
		
		return instance;
	}
	
	private Settings(){
		
	}
	
	public boolean sysoutCategory(String categories){
		
		if (DoNotSysout && false) return false;
		else{

			boolean result = false;

			if (sysoutAlt){
				return true;
			}
			else{

				String[] arrCat = categories.split(",");

				for (String category : arrCat){

					if (sysouts.get(category.toLowerCase()) != null && sysouts.get(category.toLowerCase()))
						result = true;

					if (category.trim().toLowerCase().equals("bold") && sysoutBold)
						result = true;

					if (category.trim().toLowerCase().equals("player") && sysoutPlayer)
						result = true;

					if (category.trim().toLowerCase().equals("movegk") && sysoutMoveGK)
						result = true;

					if (category.trim().toLowerCase().equals("save") && sysoutSave)
						result = true;

					if (category.trim().toLowerCase().equals("boldbehaviour") && sysoutBoldBehaviour)
						result = true;

					if (category.trim().toLowerCase().equals("stateopphasball") && sysoutStateOppHasBall)
						result = true;

					if (category.trim().toLowerCase().equals("statehasball") && sysoutStateHasBall)
						result = true;

					if (category.trim().toLowerCase().equals("marking") && sysoutMarking)
						result = true;

					if (category.trim().toLowerCase().equals("airunoffball") && sysoutAIRunOffBall)
						result = true;

					if (category.trim().toLowerCase().equals("airunwithball") && sysoutAIRunWithBall)
						result = true;

					if (category.trim().toLowerCase().equals("aipass") && sysoutAIPass)
						result = true;

					if (category.trim().toLowerCase().equals("aiholdupball") && sysoutAIHoldUpBall)
						result = true;

					if (category.trim().toLowerCase().equals("decision") && sysoutDecision)
						result = true;

				}
			}

			return result;
		}
	}
	
	public void loadSettingsFile(String fileName) {

		FileInputStream stream;
		try {
			stream = new FileInputStream(fileName);
			
			Scanner scanner = new Scanner(stream);
			
			while (scanner.hasNext()){

				String line = scanner.nextLine();

				try{

					if (line.split("=").length > 1){
						String param = line.split("=")[0].trim();
						String value = line.substring(line.indexOf("=") + 1, line.length()).trim();
//						System.out.println(param + ":" + value);
						
						if (param.startsWith("sysout")){
							sysouts.put(param.toLowerCase().replace("sysout", ""), Boolean.parseBoolean(value));
							System.out.println(param.toLowerCase().replace("sysout", "") + ": " + Boolean.parseBoolean(value));
						}
						if (param.equals("sqlConn"))
							sqlConn = value;
						else if (param.equals("replayDir")) {
							replayDir = value;
						}
						else if (param.equals("agilitymod")) {
							agilitymod = Double.parseDouble(value);
						}
						else if (param.equals("speedmod")) {
							speedmod = Double.parseDouble(value);
						}
						else if (param.equals("sharpTurnLimit")) {
							sharpTurnLimit = Double.parseDouble(value);
						}
						else if (param.equals("sysoutAlt")) {
							sysoutAlt = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutBold")) {
							sysoutBold = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutPlayer")) {
							sysoutPlayer = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutMoveGK")) {
							sysoutMoveGK = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutSave")) {
							sysoutSave = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutBoldBehaviour")) {
							sysoutBoldBehaviour = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutStateOppHasBall")) {
							sysoutStateOppHasBall = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutStateHasBall")) {
							sysoutStateHasBall = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutMarking")) {
							sysoutMarking = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutAIRunOffBall")) {
							sysoutAIRunOffBall = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutAIRunWithBall")) {
							sysoutAIRunWithBall = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutAIPass")) {
							sysoutAIPass = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutAIHoldUpBall")) {
							sysoutAIHoldUpBall = Boolean.parseBoolean(value);
						}
						else if (param.equals("sysoutDecision")) {
							sysoutDecision = Boolean.parseBoolean(value);
						}
						else{
//							System.out.println("Ukendt parameter i settings: " + param);
						}
					}
					else{
						System.out.println("Settings kunne ikke læse linien: " + line + " - param: " + line.split("=")[0].trim() + ", value: " + line.substring(line.indexOf("=") + 1, line.length()).trim());
					}
				}
				catch (Exception e){
					System.out.println("Exception ved læsning af linie i settings: " + line);
				}
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}		
		
		//Wrapper til sysouts der kan filtrere
	}
	
	public void saveSettingsFile(String fileName) throws IOException{
		
		PrintWriter pw = new PrintWriter(fileName);
		
		pw.println("jdbc:postgresql://localhost/fb?user=postgres&password=Lommen");
		pw.println(agilitymod);
		pw.println(speedmod);
		pw.println(sharpTurnLimit);
		
		pw.println(Speed);
		pw.println(Dribble);
		pw.println(Acceleration);
		pw.println(Shooting);
		pw.println(Shotpower);
		
		pw.flush();
		pw.close();
		
		System.out.println("Settings saved successfully.");
	}
	
	public double getAgilitymod() {
		return agilitymod;
	}
	public void setAgilitymod(double agilitymod) {
		this.agilitymod = agilitymod;
	}

	public double getSpeedmod() {
		return speedmod;
	}
	public void setSpeedmod(double speedmod) {
		this.speedmod = speedmod;
	}

	public double getSharpTurnLimit() {
		return sharpTurnLimit;
	}
	public void setSharpTurnLimit(double sharpTurnLimit) {
		this.sharpTurnLimit = sharpTurnLimit;
	}
	public double getSpeed() {
		return Speed;	}
	public void setSpeed(double speed) {
		Speed = speed;
	}
	
	public double getDribble() {
		return Dribble;}
	public void setDribble(double dribble) {
		Dribble = dribble;
	}
	public double getAcceleration() {
		return Acceleration;	}
	public void setAcceleration(double acceleration) {
		Acceleration = acceleration;
	}
	public double getShooting() {
		return Shooting;}
	public void setShooting(double shooting) {
		Shooting = shooting;
	}
	public double getShotpower() {
		return Shotpower;}
	public void setShotpower(double shotpower) {
		Shotpower = shotpower;
	}
	public double getPassing() {
		return Passing;}
	public void setPassing(double passing) {
		Passing = passing;
	}
	public double getTechnique() {
		return Technique;	}
	public void setTechnique(double technique) {
		Technique = technique;	
		}
	public double getStrength() {
		return Strength;	}
	public void setStrength(double strength) {
		Strength = strength;
	}
	public double getAgility() {
		return Agility;	}
	public void setAgility(double agility) {
		Agility = agility;
	}
	public double getTackling() {
		return Tackling;	}
	public void setTackling(double tackling) {
		Tackling = tackling;
	}
	public void setVision(double vision) {
		Vision = vision;	}
	public double getVision() {
		return Vision;
	}
	public void setJumping(double jumping) {
		Jumping = jumping;	}
	public double getJumping() {
		return Jumping;
	}

	public double getStamina() {
		return Stamina;
	}

	public void setStamina(double stamina) {
		Stamina = stamina;
	}

	public String getSqlConn() {
		return sqlConn;
	}

	public void setSqlConn(String sqlConn) {
		this.sqlConn = sqlConn;
	}
	
	public double getMarking() {
		return Marking;
	}
	
	public void setMarking(double marking) {
		Marking = marking;
	}

	public boolean isSysoutAlt() {
		return sysoutAlt;
	}

	public void setSysoutAlt(boolean sysoutAlt) {
		this.sysoutAlt = sysoutAlt;
	}
	
}
