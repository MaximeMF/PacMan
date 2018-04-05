package view;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Test {
	Canvas canvas ;
	PacMan pacman;
	Ghost ghost;
	
	public Test()
	{
		this.canvas = Canvas.getCanvas();
		this.pacman = new PacMan(240,140,40,40);
		this.ghost = new Ghost(120,200,40,40);
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
			
				
				/*
				PacMan pm = new PacMan(240,140,40,40);
				pm.draw();
				pm.animate();
				*/
				
				
				Test t =new Test();
				t.draw();
				t.animate();
			    
			
	}
	
	public void draw()
    {
        this.pacman.draw();
        this.ghost.draw();
        this.canvas.redraw();
    }
	
	
	public void animate()
    {
        for (int i = 0; i < 30; i++) {
            this.canvas.wait(500);
            this.pacman.move();
            this.ghost.move();
            this.canvas.redraw();
        }
    }

}
