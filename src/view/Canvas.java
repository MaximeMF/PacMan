package view;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
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
 * @author Pascale Launay
 */
public class Canvas
{

    //-------------------------------------------------------------------------
    // Static part
    //-------------------------------------------------------------------------
    public static final int WIDTH = 500, HEIGHT = 500;
    public static final Color BACKGROUND = Color.WHITE;

    private static Canvas instance;

    /**
     * Factory method to get the canvas singleton object.
     *
     * @return the canvas instance
     */
    public static Canvas getCanvas()
    {
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

    /**
     * Create a Canvas.
     */
    private Canvas()
    {
        this.objects = new ConcurrentLinkedQueue<>();
        this.shapes = new ConcurrentHashMap<>();

        this.canvas = new CanvasPane();
        this.canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        this.frame = new JFrame();
        this.frame.setContentPane(canvas);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocation(30, 30);

        this.frame.pack();

    }

    //-------------------------------------------------------------------------
    // Getters
    //-------------------------------------------------------------------------
    /**
     * Give the width of the drawing zone of the canvas
     *
     * @return the canvas width
     */
    public int getWidth()
    {
        return this.canvas.getWidth();
    }

    /**
     * Give the height of the drawing zone of the canvas
     *
     * @return the canvas height
     */
    public int getHeight()
    {
        return this.canvas.getHeight();
    }

    //-------------------------------------------------------------------------
    // Draw
    //-------------------------------------------------------------------------
    /**
     * Draw a given shape onto the canvas.
     *
     * @param referenceObject an object to define identity for this shape
     * @param color the color of the shape
     * @param shape the shape object to be drawn on the canvas
     */
    public void draw(Object referenceObject, Color color, Shape shape)
    {
        this.objects.remove(referenceObject);   // just in case it was already there
        this.objects.add(referenceObject);      // add at the end
        this.shapes.put(referenceObject, new ColoredShape(shape, color));
    }

    /**
     * Erase a given shape's from the screen.
     *
     * @param referenceObject the shape object to be erased
     */
    public void erase(Object referenceObject)
    {
        this.objects.remove(referenceObject);
        this.shapes.remove(referenceObject);
    }

    /**
     * Wait for a specified number of milliseconds before finishing. This
     * provides an easy way to specify a small delay which can be used when
     * producing animations.
     *
     * @param milliseconds the delay to wait for in milliseconds
     */
    public void wait(int milliseconds)
    {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // ignoring exception at the moment
        }
    }

    /**
     * Redraw all shapes currently on the Canvas.
     */
    public void redraw()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                canvas.repaint();
            }
        });
    }

    //-------------------------------------------------------------------------
    // Inner classes
    //-------------------------------------------------------------------------
    /**
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the shapes drawn on it.
     */
    private class CanvasPane extends JPanel
    {

        @Override
        public void paint(Graphics g)
        {
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
    private class ColoredShape
    {

        private Shape shape;
        private Color color;

        public ColoredShape(Shape shape, Color color)
        {
            this.shape = shape;
            this.color = color;
        }

        /**
         * Draw the shape using the given graphic object
         *
         * @param graphic AWT graphic object
         */
        public void draw(Graphics2D graphic)
        {
            graphic.setColor(color);
            graphic.fill(shape);
        }
    }
}
