package SirMixALotMainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import render.Texture;

public class SpaceGame extends Games{
	
	private Texture tBackground, tSun, tRocket;
	private double shipRotation = 0, velX, velY, x, y, acX, acY, a, x1, y1, g, rotate;
	 
	
	public SpaceGame(){
		tBackground = new Texture("Space");
		tSun = new Texture("sun");
		tRocket = new Texture("rocket");
		x = MainAc.WIDTH /2 + tRocket.getImage().getWidth() / 2;
		y = 10 + tRocket.getImage().getHeight() / 2;
		velX = 2;
		velY = 2; 
		x1 = MainAc.WIDTH/2 - 10;
		y1 = MainAc.HEIGHT/2 - 40;
		g = 0.1;
	}
	
	public void tick(){
		
		System.out.println(velX + " " + velY + " " +x + " " + y + " " + acX + " " + acY + " " + a + " " + x1 + " " + y1 + " " + Math.atan(a));
		if(x-x1 != 0){
			a =(y1-y)/(x1-x);
			
			if(y > y1){
				a = -a;
			}
			if(x > x1){
				a = -a;
			}
			
			acX = Math.cos(Math.atan(a)) * g;
			acY = Math.sin(Math.atan(a)) * g;
			
			if(y > y1){
				acY = -acY;
			}
			if(x > x1){
				acX = -acX;
			}
			
			
			/////////////////////////////////////////////////////////////
			
			if(velX > 0 && velY > 0){
				rotate = Math.PI/2 - Math.atan(Math.abs(velX)/Math.abs(velY));
				
			}else if(velX > 0 && velY < 0){
				rotate = Math.PI/2 + Math.atan(Math.abs(velX)/Math.abs(velY));
				
			}else if(velX < 0 && velY < 0){
				rotate = Math.PI*3/2 - Math.atan(Math.abs(velX)/Math.abs(velY));
				
			}else if(velX < 0 && velY > 0){
				rotate =  Math.PI*3/2 + Math.atan(Math.abs(velX)/Math.abs(velY));
				
			}
			
			shipRotation = rotate;
			
			rotate = 0;
			
			/////////////////////////////////////////////////////////////
			
		}else if(y > y1){
			acX = 0;
			acY = -g;
		}else if(y < y1){
			acX = 0;
			acY = g;
		}
		
		velX += acX;
		velY += acY;
		
		y += velY;
		x += velX;
		
		
		
	}
	
	public void render(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		
		tBackground.render(g, 0, 0);
		tSun.render(g, MainAc.WIDTH/2 - 225, MainAc.HEIGHT/2 - 250);
		tRocket.renderRotated(g2d, x - tRocket.getImage().getWidth()/2, y - tRocket.getImage().getHeight()/2, shipRotation);
		
		g.setColor(Color.red);
		g.fillRect((int)x1, (int)y1, 10, 10);
		g.fillRect((int) x, (int) y, 10, 10);
		g.drawString("shipRotation: " + shipRotation, 100, 100);
	}
}
