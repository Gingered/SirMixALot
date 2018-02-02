package SirMixALotMainPackage;

public class MainAc {
	private Games[] games;
	private int gameAtHand;
	
	
	public MainAc(){ 
		new GameEngine(this); // DON'T TOUCH THIS
		
	}
	
	public void tick(){ // Her skal de forskellige ticks ind
		games[gameAtHand].tick();
	}
	
	public void render(){ // Her skal de forskellige renders ind
		games[gameAtHand].render();
	}
	
	public static void main(String[] args) { // starter spillet
		new MainAc();
		
	}

}
