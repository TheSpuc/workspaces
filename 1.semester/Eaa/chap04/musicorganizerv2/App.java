package chap04.musicorganizerv2;

public class App
{
	public static void main(String[] args)
	{
		MusicOrganizer organizer = new MusicOrganizer();
		String filename1 = "audio/BigBillBroonzy-BabyPleaseDontGo1.mp3";
		organizer.addFile(filename1);
		organizer.startPlaying(0);
	}
}
