package game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class R_GamePanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	static final int SCREEN_WIDTH = 540;
	static final int SCREEN_HEIGHT = 510;
	static final int FRAME_WIDTH = 600;
	static final int FRAME_HEIGHT = 600;
	static final int UNIT_SIZE = 30;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static int DELAY = 175;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX, appleY;
	int count = 0;
	int Hrange = SCREEN_HEIGHT - 60 + 1;
    int Wrange = SCREEN_WIDTH - 30 + 1;
	char direction = 'R';
	boolean running = false;
	boolean powerStatus = false;
	boolean opening = true;
	boolean gameOver = false;
	Timer timer;
	Timer countDown;
	Random random;
	CountDownLatch latch;
	
	R_GamePanel(){
		x[0] = 30;
		y[0] = 60;
		random = new Random();
		latch = new CountDownLatch(1);
		this.setPreferredSize(new Dimension(FRAME_WIDTH,FRAME_HEIGHT));
		this.setBackground(new Color(44,53,82));
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		if(opening) {
			running = false;
			gameOver = false;
		}
		
		else {
			newApple();
			running = true; 
			timer = new Timer(DELAY,this);
			timer.start();	
		}	
	}
	
	class countDownLatch extends Thread {
		private int delay;
		private CountDownLatch latch;
		public countDownLatch(int delay, CountDownLatch latch) {
			this.delay = delay;
			this.latch = latch;
		}
		
		@Override
		public void run() {
			try {
				Thread.sleep(delay);
				latch.countDown();
//				System.out.println(Thread.currentThread().getName() + " finished");
				powerStatus = false;
				time();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void time() {
		countDownLatch cd = new countDownLatch(5000, latch);
		if(!powerStatus) {
			timer.setDelay(175);
		}
		
		else if(powerStatus) {
			cd.start();
			timer.setDelay(100);
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		if(opening) {
			R_Opening op = new R_Opening(FRAME_WIDTH, FRAME_HEIGHT);
			op.openingScene(g);
		}
		
		else if(running && !gameOver) {
			
//			Gridlines 
//			for(int i = 0; i < SCREEN_WIDTH/UNIT_SIZE; i++) {
//				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
//			}
			
		// Playground
	        g.setColor(Color.black);
			g.fillRect(30, 60, 540, 510);
			
		// Draw apple
			g.setColor(Color.red);
			g.fillRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			if(powerStatus == false) {
				// Draw the initial snake
				for(int i = 0; i < bodyParts; i++) {
					if(i == 0) {    
						g.setColor(Color.green);
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					}
					
					else {
						g.setColor(new Color(55,180,0));	
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					}			
				}
			}
			
			else if(powerStatus == true) {
				// Draw the blue snake for the powerUp
				for(int i = 0; i < bodyParts; i++) {
					if(i == 0) {
						g.setColor(Color.blue);
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					}
					else {
						g.setColor(new Color(0,128,255));
						g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
					}			
				}
				
				// If the snake ate 5 apples, it will be having powerUp
				time();
			}
			
			g.setColor(Color.yellow);
			g.setFont( new Font("Pixelated",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("SCORE : "+applesEaten, (FRAME_WIDTH - metrics.stringWidth("SCORE : "+applesEaten))/2, 45);
		}
		
		if (gameOver){
			R_GameOver go = new R_GameOver(FRAME_WIDTH, FRAME_HEIGHT, applesEaten);
			go.gameEnd(g);
		}
	}
	
	// Spawn new apple with random position
	public void newApple() {
		appleX = random.nextInt((int)(Wrange/UNIT_SIZE))*UNIT_SIZE + 30;
        appleY = random.nextInt((int)(Hrange/UNIT_SIZE))*UNIT_SIZE + 60;

        // To make the apple not spawn in the body of the snake
		for(int i = bodyParts; i > 0; i--) {
			if(appleX == x[i]) {
				appleX = random.nextInt((int)(Wrange/UNIT_SIZE))*UNIT_SIZE + 30;
			}
			
			if(appleY == y[i]) {
				appleY = random.nextInt((int)(Hrange/UNIT_SIZE))*UNIT_SIZE + 60;
			}
		}
	}
	
	// Snake Movement
	public void move(){
		for(int i = bodyParts; i > 0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
	
		switch(direction) {
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
		}
	}
	
	// Check if the apple is eaten
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++;
			applesEaten++;
		
			if(powerStatus != true) {
				count++;
			}
			
			if(count == 5) {
				powerStatus = true;
				count = 0;
			}
	
			newApple();
		}
	}

	// Check Collisions
	public void checkCollisions() {
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				gameOver = true;
			}
		}
			
		if(x[0] < 30 || y[0] < 60) {
			gameOver = true;
		}
		
		if(x[0] >= SCREEN_WIDTH + 30 || y[0] >= SCREEN_HEIGHT + 60) {
			gameOver = true;
		}
		
		if(gameOver) {
			timer.stop();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			case KeyEvent.VK_SPACE:
				if(opening) {
					opening = false;
					startGame();
				}
				break;
			}
		}
	}
}
