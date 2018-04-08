package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;

@SuppressWarnings("serial")
public class PacmanPicture extends JPanel {
    private static final int PREF_W = 800;
    private static final int PREF_H = 640;
    private static final int TIMER_DELAY = 50;

    private int pacmanX = 400;
    private int pacmanY = 320;
    private PacmanDirection pacmanDirection = PacmanDirection.RIGHT;
    private PacmanView pacman = null;
    private Timer timer = null;

    public PacmanPicture() {
        try {
        	pacman = new PacmanView(pacmanDirection, pacmanX, pacmanY);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        setBackground(Color.WHITE);

        setKeyBindings(PacmanDirection.LEFT, KeyEvent.VK_LEFT);
        setKeyBindings(PacmanDirection.RIGHT, KeyEvent.VK_RIGHT);
        setKeyBindings(PacmanDirection.FORWARD, KeyEvent.VK_DOWN);
        setKeyBindings(PacmanDirection.AWAY, KeyEvent.VK_UP);

        timer = new Timer(TIMER_DELAY, new TimerListener());
        timer.start();
    }

    private void setKeyBindings(PacmanDirection dir, int keyCode) {
        int condition = WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();

        KeyStroke keyPressed = KeyStroke.getKeyStroke(keyCode, 0, false);
        KeyStroke keyReleased = KeyStroke.getKeyStroke(keyCode, 0, true);

        inputMap.put(keyPressed, keyPressed.toString());
        inputMap.put(keyReleased, keyReleased.toString());

        actionMap.put(keyPressed.toString(), new MoveAction(dir, false));
        actionMap.put(keyReleased.toString(), new MoveAction(dir, true));
    }

    @Override
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return new Dimension(PREF_W, PREF_H);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        pacman.draw(g);
    }

    private class MoveAction extends AbstractAction {
        private PacmanDirection dir;
        private boolean released;

        public MoveAction(PacmanDirection dir, boolean released) {
            this.dir = dir;
            this.released = released;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (released) {
            	pacman.setMoving(true);
            } else {
            	pacman.setMoving(true);
            	pacman.setDirection(dir);
            }
        }
    }

    private class TimerListener implements ActionListener {
        @Override
            public void actionPerformed(ActionEvent e) {
                if (pacman.isMoving()) {
                	pacman.tick();
                }
                repaint();
            }
    }

    private static void createAndShowGui() {
    		PacmanPicture mainPanel = new PacmanPicture();

        JFrame frame = new JFrame("PacMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGui());
    }
}