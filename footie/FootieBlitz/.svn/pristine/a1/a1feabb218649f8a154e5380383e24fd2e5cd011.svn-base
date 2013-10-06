package model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;


public class Pitch extends JPanel{

	int pitchHeight;
	int pitchWidth;

	int pitchPosX;
	int pitchPosY;

	public Pitch(int pitchHeight, int pitchPosX, int pitchPosY, int pitchWidth) {
		this.pitchHeight = pitchHeight;
		this.pitchPosX = pitchPosX;
		this.pitchPosY = pitchPosY;
		this.pitchWidth = pitchWidth;
	}

	public Point stayOnPitch(int x, int y){
		
		Point result = new Point(x, y);
		
		if (result.x < pitchPosX) result.x = pitchPosX + 5;
		if (result.x > pitchPosX + pitchWidth) result.x = pitchPosX + pitchWidth - 5;
		if (result.y < pitchPosY) result.y = pitchPosY + 5;
		if (result.y > pitchPosY + pitchHeight) result.y = pitchPosY + pitchHeight - 5;
		
		return result;
	}
	
	public boolean isOut(int x, int y){
		boolean result = false;

		if (x < pitchPosX || x > pitchPosX + pitchWidth)
			result = true;
		if (y < pitchPosY || y > pitchPosY + pitchHeight)
			result = true;

		return result;
	}

	public boolean isAlmostOut(int x, int y){
		boolean result = false;

		if (x < pitchPosX + 50 || x > pitchPosX + pitchWidth - 50)
			result = true;
		if (y < pitchPosY + 50 || y > pitchPosY + pitchHeight - 50)
			result = true;

		return result;
	}

	public int getPitchHeight() {
		return pitchHeight;
	}

	public int getPitchWidth() {
		return pitchWidth;
	}

	public int getPitchMidX(){
		int mid = pitchWidth / 2 + pitchPosX;

		return mid;
	}

	public int getPitchMidY(){
		int mid = pitchHeight / 2 + pitchPosY;

		return mid;
	}
	public int getPitchPosX() {
		return pitchPosX;
	}

	public int getPitchPosY() {
		return pitchPosY;
	}


}
