package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class R_Opening extends R_GamePanel {

	private static final long serialVersionUID = 1L;
	R_GamePanel gp = new R_GamePanel();
	private int fWidth;
	private int fHeight;

	public R_Opening(int FRAME_WIDTH, int FRAME_HEIGHT) {
		super();
		this.setfWidth(FRAME_WIDTH);
		this.setfHeight(FRAME_HEIGHT);
	}

	public void openingScene(Graphics g) {
		if(!gp.running) {
			
			g.setColor(Color.yellow);
			g.setFont( new Font("Pixelated",Font.BOLD, 80));
			FontMetrics metrics1 = getFontMetrics(g.getFont());
			g.drawString("SNAKE GAME", (getfWidth() - metrics1.stringWidth("SNAKE GAME"))/2, getfHeight()/2);
			
			g.setFont( new Font("Pixelated",Font.ITALIC, 30));
			FontMetrics metrics2 = getFontMetrics(g.getFont());
			g.drawString("PRESS SPACEBAR TO PLAY", (getfWidth() - metrics2.stringWidth("PRESS SPACEBAR TO PLAY"))/2, 380);
		}
	}
	
	private int getfWidth() {
		return fWidth;
	}
	private void setfWidth(int fWidth) {
		this.fWidth = fWidth;
	}
	private int getfHeight() {
		return fHeight;
	}
	private void setfHeight(int fHeight) {
		this.fHeight = fHeight;
	}
}
