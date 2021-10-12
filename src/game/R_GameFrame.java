package game;

import javax.swing.JFrame;

public class R_GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	R_GameFrame() {
		this.add(new R_GamePanel());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);		
	}
	
}
