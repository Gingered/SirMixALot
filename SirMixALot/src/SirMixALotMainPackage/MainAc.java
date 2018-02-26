package SirMixALotMainPackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import render.Texture;

public class MainAc extends Canvas implements Runnable {
	private static final String GAMENAME = "SirMixALot";
	public static final int WIDTH = 1150;
	public static final int HEIGHT = WIDTH / 4 * 3;

	private Games[] games;
	private int gameAtHand;
	public JFrame frame = new JFrame(GAMENAME);
	
	private boolean running = false; // K�rer loopet
	private SpaceGame game1;


	
	public MainAc() {
		game1 = new SpaceGame();
		
		frame.add(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(true);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();

		start();
		
		
	}

	public void tick() { // Her skal de forskellige ticks ind
//		 games[gameAtHand].tick();
		game1.tick();
	}

	public void render() { // Her skal de forskellige renders ind

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		game1.render(g);
		// games[gameAtHand].render(g);

		g.dispose();
		bs.show();
	}
	
	private void start() { // S�tter loopet til at k�rer
		if (running) {
			return;
		}
		running = true;
		new Thread(this, "MainGame-Thread").start(); // Starter Threaden
	}

	private void stop() { // stopper loopet
		if (!running) {
			return;
		}
		running = false;
	}

	public void run() {
		double target = 60.0; // m�ngden af FPS
		double nsPerTick = 1000000000.0 / target; // Den tid det skal tage f�r
													// der kommer et nyt frame
		long lastTime = System.nanoTime(); // Tiden nu, skal bruges til at
											// tjekke hvorn�r den n�ste frame
											// skal sendes
		double newFrameCount = 0.0; // M�ngden af tid der er g�et uden at nogen
									// frames er blevet udstedet
		long now = System.nanoTime(); // Finder tiden nu, hvilket skal bruges
										// til at finde forskellen p� nu og
										// sidste gang og hj�lpe med at finde
										// hvorn�r n�ste frame skal ud
		double timer = 0.0; // bruges til at finde m�ngden af FPS
		int fps = 0; // m�ngden af FPS

		while (running) { // Tjekker bare om der er gang i programmet
			now = System.nanoTime();
			newFrameCount += (now - lastTime) / nsPerTick; // tjekker forskellen p� sidste gang divideret med et sek/FPS alts� jo h�jere FPS jo st�rrer bliver dette, dette bliver lagt sammen med sidste gang for at t�lle op indtil der bliver lavet et nyt frame
			timer += (now - lastTime) / 1000000000.0; // finder ud af hvorn�r der er g�et et sek
			lastTime = now; // Last time skal fornyes efter newFrameCount har talt opad, da der ellers ville komme stor tidsforskel i l�ngden og det ikke ville give mening

			if (newFrameCount >= 1.0) { // Hvis der er talt op til en ny frame laves den og der hele spillet ticker
				tick(); // Der tickes
				render(); // Der renders
				newFrameCount--; // Vi skal have sat dette tilbage
				fps++;
			}

			if (timer >= 1.0) { // Viser hvor mange FPS der er
				System.out.println("FPS " + fps);
				timer--;
				fps = 0;
			}
		}

		System.exit(0); // hvis lortet crasher er dette fejlkoden herfra

	}

	public static void main(String[] args) { // starter spillet
		new MainAc();

	}

}
