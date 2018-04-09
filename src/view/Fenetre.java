package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.CompoundBorder;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.border.EtchedBorder;

public class Fenetre {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fenetre window = new Fenetre();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Fenetre() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 622, 556);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel mazePane = new JPanel();
		mazePane.setForeground(UIManager.getColor("Button.light"));
		mazePane.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null), new EtchedBorder(EtchedBorder.LOWERED, new Color(9, 80, 208), null)));
		mazePane.setBounds(31, 58, 585, 451);
		frame.getContentPane().add(mazePane);
		
		JLabel Score = new JLabel("Score : ");
		Score.setBackground(new Color(175, 238, 238));
		Score.setForeground(new Color(0, 0, 128));
		Score.setFont(new Font("Lucida Bright", Font.BOLD, 13));
		Score.setBounds(34, 16, 113, 26);
		frame.getContentPane().add(Score);
		
		JLabel Lives = new JLabel("Lives :");
		Lives.setFont(new Font("Lucida Bright", Font.BOLD, 13));
		Lives.setBackground(new Color(255, 255, 255));
		Lives.setForeground(new Color(0, 128, 0));
		Lives.setBounds(398, 21, 61, 16);
		frame.getContentPane().add(Lives);
		frame.getContentPane().setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{mazePane, Score, Lives}));
	}
}
