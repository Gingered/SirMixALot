package render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import managers.TextureManager;

public class Texture {
	
	private final static Map<String, TextureManager> texMap = new HashMap<String, TextureManager>();
	private TextureManager manager;
	private String fileName;
	
	public Texture(String fileName){
		this.fileName = fileName;
		TextureManager oldTexture = texMap.get(fileName);
		if(oldTexture != null){
			manager = oldTexture;
			manager.addReference();
		}else{
			try{
				System.out.println("Loading texture: " + fileName);
				manager = new TextureManager(ImageIO.read(new File("./resources/textures/" + fileName + ".png")));
				texMap.put(fileName, manager);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		
		
	}
	
	@Override
	protected void finalize() throws Throwable {
		if(manager.removeReference() && !fileName.isEmpty()){
				texMap.remove(fileName);
				}
		super.finalize();
	}
	
	public void render(Graphics g, double x, double y){
		g.drawImage(manager.getImage(), (int) x, (int) y, null);
	}
	
	public void renderRotated(Graphics2D g, double x, double y, double degrees){
		double locationX = manager.getImage().getWidth() /2;
		double locationY = manager.getImage().getHeight() /2;
		
		AffineTransform tx = AffineTransform.getRotateInstance(degrees, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		
		g.drawImage(op.filter(manager.getImage(), null), (int) x, (int) y, null);
	}
	
	public BufferedImage getImage(){
		return manager.getImage();
	}
}
