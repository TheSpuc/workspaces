package model;

public class Sysout {
	
	public static Settings settings = null;
	
	public static void print(String text, String category){
		if (settings == null) settings = Settings.getInstance();
		
		if (settings != null && settings.sysoutCategory(category))
			System.out.println(text);
		else if (settings == null){
			System.out.println("Settings = null in Sysout");
		}
	}
}
