package SirMixALotMainPackage;

public class GameEngine implements Runnable{
	private boolean running = false; // K�rer loopet
	private MainAc game;
	
	public GameEngine(MainAc game){ // Constructer starter tingen
		this.game = game;
		start();
	}
	
	private void start(){ //S�tter loopet til at k�rer
		if(running){
			return;
		}
		running = true; 
		new Thread(this, "MainGame-Thread").start(); // Starter Threaden
	}
	
	private void stop(){ // stopper loopet 
		if(!running){
			return;
		}
		running = false;
	}
	
	
	public void run() {
		double target = 60.0; //m�ngden af FPS
		double nsPerTick = 1000000000.0 / target; //Den tid det skal tage f�r der kommer et nyt frame
		long lastTime = System.nanoTime(); //Tiden nu, skal bruges til at tjekke hvorn�r den n�ste frame skal sendes
		double newFrameCount = 0.0; //M�ngden af tid der er g�et uden at nogen frames er blevet udstedet 
		long now = System.nanoTime(); // Finder tiden nu, hvilket skal bruges til at finde forskellen p� nu og sidste gang og hj�lpe med at finde hvorn�r n�ste frame skal ud
		double timer = 0.0; // bruges til at finde m�ngden af FPS
		int fps = 0; // m�ngden af FPS
		
		while(running){ //Tjekker bare om der er gang i programmet
			now = System.nanoTime();
			newFrameCount += (now - lastTime) / nsPerTick; // tjekker forskellen p� sidste gang divideret med et sek/FPS alts� jo h�jere FPS jo st�rrer bliver dette, dette bliver lagt sammen med sidste gang for at t�lle op indtil der bliver lavet et nyt frame
			timer += (now - lastTime) / 1000000000.0; // finder ud af hvorn�r der er g�et et sek
			lastTime = now; //Last time skal fornyes efter newFrameCount har talt opad, da der ellers ville komme stor tidsforskel i l�ngden og det ikke ville give mening

			if(newFrameCount >= 1.0){ // Hvis der er talt op til en ny frame laves den og der hele spillet ticker
				tick(); //Der tickes
				render(); //Der renders
				newFrameCount--; //Vi skal have sat dette tilbage 
				fps++;
			}
			
			if(timer >= 1.0){
				System.out.println("FPS " + fps);
				timer--;
				fps = 0;
			}
		}
		
		System.exit(0); // hvis lortet crasher er dette fejlkoden herfra
		
	}
	
	public void tick(){ //ticks the game
		game.tick();
	}
	
	public void render(){ //renders the game
		game.render();
	}
	
}
