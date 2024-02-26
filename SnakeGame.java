package com.snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class SnakeGame extends JPanel implements ActionListener , KeyListener{
	
	static final int SCREEN_WIDTHX = 600;
	static final int SCREEN_HEIGHTY = 600;
	static final int SIZE = 30;
	static final int DELAY = 100;
	final int [] x = new int[SCREEN_HEIGHTY*SCREEN_WIDTHX];
	final int [] y = new int[SCREEN_HEIGHTY*SCREEN_WIDTHX];
	
	int bodyPart = 3;
	int Score;
	int foodEatten;
	int FoodX;
	int FoodY;
	int currentDirectionX = 1; // RIGHT
	int currentDirectionY = 0; 
	boolean running = false;
	Random random;
	Timer timer;
	
	
	
	
	
	public SnakeGame() {
		random = new Random();
		this.setPreferredSize(new Dimension (SCREEN_HEIGHTY,SCREEN_WIDTHX));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(this);	
		startGame();
		
	}
	
	public void startGame() {
		CreateFood();
		running = true;
		x[0]=SCREEN_WIDTHX/2;   // for start from center
		y[0]=SCREEN_HEIGHTY/2;
		timer = new Timer(DELAY,this);
		timer.start();
		
	}
	
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw (Graphics g) {
		if(running) {
		//for(int i=0;i<SCREEN_HEIGHTY;i++) {                 for grid
		//	g.drawLine(i*SIZE, 0, i*SIZE, SCREEN_HEIGHTY);
	    //    g.drawLine(0, i*SIZE, SCREEN_WIDTHX, i*SIZE);
//		}
		g.setColor(Color.red);
		g.fillOval(FoodX,FoodY,SIZE, SIZE);
		
		for (int i=0 ;i<bodyPart ; i++) {
			g.setColor(Color.green);
			g.fillRect(x[i],y[i],SIZE, SIZE);
			
		}
		}else {
			GameOver(g);
		}
	}
	
	public void CreateFood () {
		FoodX = random.nextInt(((int)SCREEN_WIDTHX/SIZE))*SIZE;
		FoodY = random.nextInt(((int)SCREEN_HEIGHTY/SIZE))*SIZE;
		
		
	}
	
	public void CheckFood () {
		if(x[0] == FoodX && y[0] == FoodY) {
			CreateFood();
			Score++;
			bodyPart++;
			
		}
		
	}
	
	public void CheckCollision() { //collides with body
		for(int i= bodyPart;i>0;i--) {
			if(x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
			
			if(x[0]<0) {  // left border
				running = false;
			}
			
			if (x[0]>=SCREEN_WIDTHX) { // right border
				running = false;
				
			}
			
			if (y[0]<0) { // top border
				running =false;
			}
			
			if (y[0]>=SCREEN_HEIGHTY) { // bottom border
				running =false;
			}
			
			if(!running) {
				timer.stop();
			}
		}
	}
	
	public void move() {
		for(int i=bodyPart ;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		x[0] = x[0]+ currentDirectionX*SIZE;
				y[0] = y[0] + currentDirectionY*SIZE;
		
	}
	
	public void GameOver( Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Serif",Font.BOLD,85));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score :" + Score,SCREEN_HEIGHTY/5,SCREEN_WIDTHX/3);
		g.drawString("Game Over",SCREEN_HEIGHTY/4,SCREEN_WIDTHX/2);
		
		
	}
	
	

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
	   int key = e.getKeyCode();
	   switch(key) {
	   case KeyEvent.VK_LEFT:
		   if(currentDirectionX !=1) { // stop moving left
		   currentDirectionX=-1; // moving left
		   currentDirectionY=0; // stop up and down
		   }
		   break;
		   
		   
	   case KeyEvent.VK_RIGHT:
		   if(currentDirectionX!=-1) { //stop moving right
		   currentDirectionX=1; // moving right
		   currentDirectionY=0; // stop up and down
		   }
		   break;
		   
	   case KeyEvent.VK_UP:
		   if(currentDirectionY!=1) { // stop moving down
		   currentDirectionY=-1; //moving up
		   currentDirectionX=0; // stop left and right
		   }
		   break;
		   
	   case KeyEvent.VK_DOWN:
		   if(currentDirectionY!=-1) { // stop moving up
		   currentDirectionY=1; //moving down
		   currentDirectionX=-0; // stop left and right
		   }
		   break;
		   
	   
	   }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			CheckFood();
			CheckCollision();
		}
		repaint();
	}

}
