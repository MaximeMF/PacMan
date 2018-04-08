package view;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.*;
import java.util.Map;

import javax.imageio.ImageIO;

 class PacmanView {
 
    private static final int MAX_MOVING_INDEX = 4;
    private static final int Speed = 4;
    private PacmanDirection direction;
    private Map<PacmanDirection, Image> standingImgMap = new EnumMap<>(PacmanDirection.class);
    private Map<PacmanDirection, List<Image>> movingImgMap = new EnumMap<>(PacmanDirection.class);
    private int x;
    private int y;
    private boolean moving = false;
    private int movingIndex = 0;

    public PacmanView(PacmanDirection direction, int x, int y) throws IOException {
        this.direction = direction;
        this.x = x;
        this.y = y;
        createSprites();
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
    
     
    
    private void createSprites() throws IOException 
    {
       
        BufferedImage imgup = ImageIO.read(new File("image/pacmanup.png"));
        BufferedImage imgdown = ImageIO.read(new File("image/pacmandown.png"));
        BufferedImage imgleft = ImageIO.read(new File("image/pacmanleft.png"));
        BufferedImage imgright = ImageIO.read(new File("image/pacmanright.png"));
        
        imgup  = PacmanView.resize(imgup, 40, 40);
        imgdown= PacmanView.resize(imgdown, 40, 40) ;
        imgleft= PacmanView.resize(imgleft, 40, 40);
        imgright= PacmanView.resize(imgright, 40, 40);
        
        // get sub-images (sprites) from the sprite sheet
       
        
        for (int row = 0; row < 4; row++) {
            PacmanDirection dir = PacmanDirection.values()[row];
            List<Image> imgList = new ArrayList<>();
            imgList.add(imgup);
           
            movingImgMap.put(dir, imgList);
            for (int col = 0; col < 5; col++) {
                if (col == 0) {
                    // first image is standing
                		standingImgMap.put(dir, imgup);	
                }
                else
                {
                		imgList.add(imgup);
                		imgList.add(imgup);
                		imgList.add(imgup);
                }
                
                
                
            
            }
        }
            
            
            
            /*for (int col = 0; col < 5; col++) {
                if (col == 0) {
                    // first image is standing
                    standingImgMap.put(dir, imgup);
                }/* else {
                    // all others are moving
                	 	imgList.add(imgdown);
                	 	imgList.add(imgright);
                	 	imgList.add(imgleft);
                	 	
                }*/
            //}
            
       // }
        
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
            switch (direction) {
            case RIGHT:
                x += Speed;
                break;
            case LEFT:
                x -= Speed;
                break;
            case FORWARD:
                y += Speed;
                break;
            case AWAY:
                y -= Speed;
            }
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