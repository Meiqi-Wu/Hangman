package hangman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;

import javax.swing.JComponent;

public class GLabel extends JComponent{
	private int x;
	private int y;
	private int width;
	private int height;
	private double theta;
	private String text;
	private boolean isCentered;
	private Color color;
	private Font font;
	
	public GLabel(String str) {
		this.text = new String(str); 
		this.x = 0;
		this.y = 0;
		this.theta = 0;
		this.color = Color.BLACK;
		this.font = new Font("Times-24", Font.PLAIN, 14);
		this.isCentered = true;
		
		
//		this.setSize(50, 20);
//		this.setPreferredSize(new Dimension(100, 200));
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setText(String str) {
		this.text = new String(str);
	}
	
	public void rotate(double theta) {
		this.theta = theta; 
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	/*
	 * Make x, y the center of the label.
	 */
	public void setCentered(boolean bool) {
		this.isCentered = bool;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
//		System.out.println("Start drawing the Label ...");
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setFont(this.font);
		g2d.setColor(this.color);
		if(isCentered) {
			FontMetrics fm = g2d.getFontMetrics(this.font);
			java.awt.geom.Rectangle2D rect = fm.getStringBounds(this.text, g2d);
			double sw = rect.getWidth();
			double sh = rect.getHeight();
			
//			FontRenderContext frc = g2d.getFontRenderContext();
//			double sw = this.font.getStringBounds(text, frc).getWidth();
//			LineMetrics lm = this.font.getLineMetrics(text, frc);
//			double sh = (lm.getAscent() + lm.getDescent());
					
			this.width = (int)sw;
			this.height = (int)sh;
//			System.out.println("width: "+sw);
//			System.out.println("height: "+sh);
			g2d.translate(this.x-sw/2, this.y+sh/2);
		} else {		
			g2d.translate(this.x, this.y);
		}
		g2d.rotate(this.theta/180* Math.PI);
		g2d.drawString(this.text, 0, 0);
	}


	
	
////	@Override
//	public int getW() {
//		return this.width;
//	}
//	
////	@Override
//	public int getH() {
//		return this.height;
//	}
}
