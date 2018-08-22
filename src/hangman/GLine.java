package hangman;

import java.awt.Graphics;

import javax.swing.JComponent;

public class GLine extends JComponent{
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	public GLine(double x1, double y1, double x2, double y2){
		this.x1 = (int) x1;
		this.y1 = (int) y1;
		this.x2 = (int) x2;
		this.y2 = (int) y2;
	}
	
	@Override
	public void paintComponent(Graphics g) {	
		super.paintComponent(g);
		g.drawLine(x1, y1, x2, y2);

	}
}
