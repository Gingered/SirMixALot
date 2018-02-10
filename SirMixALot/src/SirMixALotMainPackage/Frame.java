package SirMixALotMainPackage;

import java.awt.Canvas;
import javax.swing.JFrame;

public class Frame extends Canvas{
	
	private static final String GAMENAME = "SirMixALot";
	private static final int WIDTH = 640;
	private static final int HEIGHT = WIDTH / 4 * 3;
	
	public JFrame frame = new JFrame(GAMENAME);
	
	public Frame(){
		frame.add(this);
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.requestFocus();
	}
	
}
