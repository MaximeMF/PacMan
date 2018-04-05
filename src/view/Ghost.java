package view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ghost extends Figure{

	private int x,y,width,height;
	private final static String dir =  "rsc/ghost.png" ;
	private  BufferedImage img;
	
	public Ghost(int x, int y, int width, int height) {
		try {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.img =  ImageIO.read(new File(dir));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
		try {
			Canvas canvas = Canvas.getCanvas();
			this.img = ImageIO.read(new File(dir));
			canvas.drawImage(canvas, this.img, this.x, this.y, this.width, this.height);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
	
	public void move()
    {
        move(this.x, this.y++);
        invariant();
    }
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public void erase() {
		// TODO Auto-generated method stub
		
	}
	
}
