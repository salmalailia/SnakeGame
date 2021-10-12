package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class R_GameOver extends R_GamePanel {

	private static final long serialVersionUID = 1L;
	R_GamePanel gp = new R_GamePanel();
	private int fWidth;
	private int fHeight;
	private int total;
	
	public R_GameOver(int FRAME_WIDTH, int FRAME_HEIGHT, int applesEaten) {
		super();
		this.setfWidth(FRAME_WIDTH);
		this.setfHeight(FRAME_HEIGHT);
		this.setTotal(applesEaten);
	}
	
	public void gameEnd(Graphics g) {
		this.setBackground(new Color(44,53,82));
		
		//Score
		g.setColor(Color.yellow);
		g.setFont( new Font("Pixelated", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("SCORE : " + getTotal(), (getfWidth() - metrics1.stringWidth("SCORE : " + getTotal()))/2, getfHeight()/3);
			
		//Game Over text
		g.setColor(Color.yellow);
		g.setFont( new Font("Pixelated",Font.BOLD, 80));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (getfWidth() - metrics2.stringWidth("GAME OVER"))/2, getfHeight()/2);
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
	private int getTotal() {
		return total;
	}
	private void setTotal(int total) {
		this.total = total;
	}
}
