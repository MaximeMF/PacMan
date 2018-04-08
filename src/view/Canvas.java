package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 *
 * @author 
 */
public class Canvas {

    //-------------------------------------------------------------------------
    // Static part
    //-------------------------------------------------------------------------
    public static final int WIDTH = 800, HEIGHT = 800;
    public static final Color BACKGROUND = Color.WHITE;

    private static Canvas instance;

    /**
     * Factory method to get the canvas singleton object.
     *
     * @return the canvas instance
     */
    public static Canvas getCanvas() {
        if (Canvas.instance == null) {
            Canvas.instance = new Canvas();
        }
        if (!Canvas.instance.frame.isVisible()) {
            Canvas.instance.frame.setVisible(true);
        }
        return Canvas.instance;
    }

    //-------------------------------------------------------------------------
    // Instance part
    //-------------------------------------------------------------------------
    private final JFrame frame;
    private final CanvasPane canvas;
    private final Queue<Object> objects;
    private final Map<Object, ColoredShape> shapes;
    private boolean upPressed, downPressed, leftPressed, rightPressed;

    /**
     * Create a Canvas.
     */
    private Canvas() {
        this.objects = new ConcurrentLinkedQueue<>();
        this.shapes = new ConcurrentHashMap<>();

        this.canvas = new CanvasPane();
        this.canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.frame = new JFrame();
        this.frame.setContentPane(canvas);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocation(30, 30);

        this.frame.pack();

        this.canvas.addKeyListener(new KeyboardListener());
        this.canvas.setFocusable(true);
    }

    //-------------------------------------------------------------------------
    // Key pressed accessors
    //-------------------------------------------------------------------------
    /**
     * Check whether the UP key is currently pressed
     *
     * @return true if the UP key is currently pressed
     */
    public boolean isUpPressed() {
        return upPressed;
    }

    /**
     * Check whether the DOWN key is currently pressed
     *
     * @return true if the DOWN key is currently pressed
     */
    public boolean isDownPressed() {
        return downPressed;
    }

    /**
     * Check whether the LEFT key is currently pressed
     *
     * @return true if the LEFT key is currently pressed
     */
    public boolean isLeftPressed() {
        return leftPressed;
    }

    /**
     * Check whether the RIGHT key is currently pressed
     *
     * @return true if the RIGHT key is currently pressed
     */
    public boolean isRightPressed() {
        return rightPressed;
    }

    //-------------------------------------------------------------------------
    // Draw
    //-------------------------------------------------------------------------
    /**
     * Redraw all shapes currently on the Canvas.
     */
    public void redraw() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                canvas.repaint();
            }
        });
    }

    /**
     * Wait for a specified number of milliseconds before finishing. This
     * provides an easy way to specify a small delay which can be used when
     * producing animations.
     *
     * @param milliseconds the delay to wait for in milliseconds
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // ignoring exception at the moment
        }
    }

    /**
     * Draw a given shape onto the canvas.
     *
     * @param referenceObject an object to define identity for this shape
     * @param color the color of the shape
     * @param shape the shape object to be drawn on the canvas
     */
    public void draw(Object referenceObject, Color color, Shape shape) {
        this.objects.remove(referenceObject);   // just in case it was already there
        this.objects.add(referenceObject);      // add at the end
        this.shapes.put(referenceObject, new ColoredShape(shape, color));
    }

    /**
     * Draw a given text onto the canvas.
     *
     * @param referenceObject an object to define identity for this shape
     * @param color the color of the text
     * @param text the text
     * @param x the x location of the text
     * @param y the y location of the text
     */
    public void drawString(Object referenceObject, Color color, String text, int x, int y) {
        this.objects.remove(referenceObject);   // just in case it was already there
        this.objects.add(referenceObject);      // add at the end
        this.shapes.put(referenceObject, new ColoredShape(text, x, y, color));
    }
    
    
    public void drawImage(Object referenceObject,Image image,int x,int y, int width,int height)
    {
    	 	this.objects.remove(referenceObject);   // just in case it was already there
         this.objects.add(referenceObject);      // add at the end
         this.shapes.put(referenceObject, new ColoredShape(image,x,y,width,height));
    }
    /**
     * Erase a given shape's from the screen.
     *
     * @param referenceObject the shape object to be erased
     */
    public void erase(Object referenceObject) {
        this.objects.remove(referenceObject);
        this.shapes.remove(referenceObject);
    }

    //-------------------------------------------------------------------------
    // Inner classes
    //-------------------------------------------------------------------------
    /**
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the shapes drawn on it.
     */
    private class CanvasPane extends JPanel {

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.setColor(BACKGROUND);
            g.fillRect(0, 0, getWidth(), getHeight());
            for (Object shape : objects) {
                shapes.get(shape).draw((Graphics2D) g);
            }
        }
    }

    /**
     * Inner class ColoredShape. Represents a shape and its color
     */
    private class ColoredShape {

        private Shape shape;
        private String text;
        private int x, y,width,height;
        private Color color;
        private Image image;

        public ColoredShape(Shape shape, Color color) {
            this.shape = shape;
            this.color = color;
        }

        public ColoredShape(String text, int x, int y, Color color) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.color = color;
        }
        
        public ColoredShape(Image image,int x,int y,int width,int height)
        {
        		this.image = image;
        		this.x = x;
        		this.y = y;
        		this.width = width;
        		this.height = height;
        }
        

        /**
         * Draw the shape using the given graphic object
         *
         * @param graphic AWT graphic object
         */
        public void draw(Graphics2D graphic) {
            graphic.setColor(color);
            if (shape != null) {
                graphic.fill(shape);
            } else if (text != null) {
                graphic.drawString(text, x, y);
            } else if (image != null)
            {
            	graphic.drawImage(image, 0, y, width, height, null);
            }
        }
    }

    /**
     * Inner class KeyboardListener - listens for the UP, DOWN, RIGHT, LEFT
     * keys.
     */
    private class KeyboardListener extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_UP:
                    upPressed = true;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = true;
                    break;
                case KeyEvent.VK_LEFT:
                    leftPressed = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = true;
                    break;
            }
        }

        @Override
        public void keyReleased(KeyEvent event) {
            switch (event.getKeyCode()) {
                case KeyEvent.VK_UP:
                    upPressed = false;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = false;
                    break;
                case KeyEvent.VK_LEFT:
                    leftPressed = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = false;
                    break;
            }
        }
    }
}