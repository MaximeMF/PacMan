package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class GhostView {
	private static final int MAX_MOVING_INDEX = 4;
    private static final int Speed = 4;
    private PacmanDirection direction;
    private Map<PacmanDirection, Image> standingImgMap = new EnumMap<>(PacmanDirection.class);
    private Map<PacmanDirection, List<Image>> movingImgMap = new EnumMap<>(PacmanDirection.class);
    private int x;
    private int y;
    private boolean moving = false;
    private int movingIndex = 0;

    public GhostView(PacmanDirection direction, int x, int y) throws IOException {
        this.direction = direction;
        this.x = x;
        this.y = y;
        createGhost();
    }

    public void draw(Graphics g) {
        Image img = null;
        if (!moving) {
            img = standingImgMap.get(direction);
        } else {
            img = movingImgMap.get(direction).get(movingIndex);
        }
        g.drawImage(img, x, y, null);
    }
    
     
    
    private void createGhost() throws IOException 
    {
       
        BufferedImage ghost = ImageIO.read(new File("res/ghost.png"));
        ghost  = GhostView.resize(ghost, 40, 40);
        
              
    }

    public PacmanDirection getDirection() {
        return direction;
    }

    public void setDirection(PacmanDirection direction) {
        if (this.direction != direction) {
            setMoving(false);
        }
        this.direction = direction;

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

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
        if (!moving) {
            movingIndex = 0;
        }
    }

    public void tick() {
        if (moving) {
            
            movingIndex++;
            movingIndex %= MAX_MOVING_INDEX;
        }
    }

    public int getMovingIndex() {
        return movingIndex;
    }

    public void setMovingIndex(int movingIndex) {
        this.movingIndex = movingIndex;
    }
    
    
    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
