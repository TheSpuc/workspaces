package drawing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class TegnePanel extends JPanel {

	public void paintComponent(Graphics g) {
		setBackground(Color.WHITE);
		super.paintComponent(g);
		
		
		// Tilf�j kode der laver den �nskede figur
		
		//draw arrows
//		int x = 100;
//		int y = 75;
//		int size = 16;
//		
//		g.drawLine(x, y, x+10, y+size);
//		g.drawLine(x, y, x+10, y-size);
//		y = 125;
//		g.drawLine(x, y, x+10, y+size);
//		g.drawLine(x, y, x+10, y-size);
		
		//9 lines
//		int x1 = 100;
//		int y1 = 100;
//		int x2 = 20;
//		int y2 = 10;
//		
//		while(x2 <= 180){
//			g.drawLine(x1, y1, x2, y2);
//			x2 += 20;
//		}
		
		//5 strait lines vertical
//		int x1 = 40;
//		int y1 = 20;
//
//		while(x1 <= 180){
//			g.drawLine(x1, y1, x1, y1+100);
//			x1 += 30;
//		}
		
		//5 stait lines horizontal
//		int x1 = 40;
//		int y1 = 20;
//		
//		while(y1 <= 180){
//			g.drawLine(x1, y1, x1+100, y1);
//			y1 += 30;
//		}
		
		//5 strait lines small to big
//		int x1 = 90;
//		int y1 = 10;
//		int size = 10;
//		
//		while(x1 >= 0){
//			g.drawLine(x1, y1, x1+size, y1);
//			x1 -= 10;
//			y1 += 10;
//			size += 20;
//		}
		
		//ovals fancy
//		int x1 = 100;
//		int y1 = 100;
//		int r = 20;
//		
//		while(r <=120){
//			g.drawOval(x1-r, y1-r, r*2, r*2);
//			r += 20;
//		}
		
		
		//ovals to the left
//		int x1 = 20;
//		int y1 = 100;
//		int r = 20;
//		
//		while(r <= 120){
//			g.drawOval(x1, y1-r, r*2, r*2);
//			r += 20;
//		}
		
		//ovals all over the place
//		int x1 = 100;
//		int y1 = 100;
//		int r1 = 10;
//		int r2 = 20;
//		
//		while(r1 <= 120){
//			g.drawOval(x1, y1, r1*2, r2*2);
//			x1 -= 10;
//			r1 += 10;
//			
//		}
		
		//String fun
//		String foo = "Datamatiker";
//		int i = 1;
//		int x = 10;
//		int y = 10;
//		
//		while(i <= foo.length()){
//			g.drawString(foo.substring(0, i), x, y);
//			i++;
//			y += 12;
//		}
		
		//draw happy square
//		int x1 = 100;
//		int y1 = 200;
//		
//		g.setColor(Color.RED);
//		g.fillRect(x1, y1, 30, 30);
		
		//x line
//		int x1 = 0;
//		int x2 = 20;
//		int y1 = 200;
//
//		for(int i=0; i<=10; i++){
//			g.drawLine(x1, y1, x2, y1);
//			x1 = x2;
//			if(i == 0 || i == 5 || i == 10){
//			g.drawLine(x1-10, y1-8, x1-10, y1+8);
//			g.drawString(" "+i, x1-18, y1+25);
//			}else g.drawLine(x1-10, y1-5, x1-10, y1+5);
//			x2 += 20;
//		}
//		g.drawLine(x1, y1, x1-6, y1-4);
//		g.drawLine(x1, y1, x1-6, y1+4);
		
		//for sentence assign 5
//		String foo = "Datamatiker";
//		int x = 10;
//		int y = 10;
//		
//		for(int i=0; i<=foo.length(); i++){
//			g.drawString(foo.substring(0, i), x, y);
//			y += 15;
//		}
		
		//perspective lines to the left
//		int x1 = 16;
//		int y1 = 160;
//		int y2 = 80;
//		
//		for(int i=0; i<10; i++){
//			g.drawLine(x1, y1, x1, y2);
//			x1 += 16;
//			y1 -= 12;
//			y2 -= 8;
//		}
		
		//perspective lines to the right
//		int x1 = 180;
//		int y1 = 30;
//		int y2 = 130;
//		
//		for(int i=0; i<10; i++){
//			g.drawLine(x1, y1, x1, y2);
//			x1 = (int) ((int) x1*0.75);
//			y1 += 2;
//			y2 -= 6;
//		}
		
		
	}
}
