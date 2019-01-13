package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Algorithms.Play;
import AutoPilot.Node;
import AutoPilot.PlayerAlgo;
import Coords.LatLonAlt;
import Coords.Map;
import GamePieces.Fruit;
import GamePieces.Game;
import GamePieces.Obstacle;
import GamePieces.Packman;
import Geom.Point3D;

/**
 * Description: the class represents the game-board when the game takes place.
 * the class's fields are a JLabel that constantly displays the statistics of
 * the game, the game file and an array containing the players' id numbers, Play
 * object that is in charge of updating the location of the moving game-pieces
 * as well as updating the game's score a degree instance that changes according
 * to the players' keyboard press (used only on manual mode), map object for
 * coordinates alteration and display, and a boolean variable to initiate
 * AutoPilot. the class implements both KeyListener, for the players to move
 * their game piece around the board and a MouseListener to set the initial
 * location of the game-piece.
 * 
 * @author Yarden
 *
 */
public class Board extends JPanel implements KeyListener, MouseListener {

	private static final long serialVersionUID = 1L;

	private JLabel score;
	private String selectedFile = null;
	private long[] ids;
	private Map map = null;
	private Play play = null;
	private boolean auto = false;
	private double deg = -1;

	/**
	 * Description: an empty constructor used only to display the background image
	 * of the game.
	 * 
	 * @throws FileNotFoundException
	 */
	public Board() throws FileNotFoundException {
		initBoard();
	}

	/**
	 * Description: a constructor that receives a string for a path. the method then
	 * send to another method to take the players' id numbers and then set up the
	 * board's parameters.
	 * 
	 * @param selectedFile
	 * @throws FileNotFoundException
	 */
	public Board(String selectedFile) throws FileNotFoundException {
		this.selectedFile = selectedFile;
		SetIDPane();
		initBoard();
	}

	/**
	 * Description: the method creates three JOption panels one after the other. the
	 * players type their id numbers and press OK. the method saves the ids as a
	 * class field.
	 * 
	 */
	private void SetIDPane() {
		ArrayList<String> ans = new ArrayList<String>();
		ans.add(JOptionPane.showInputDialog(this, "Enter ID1", null));
		ans.add(JOptionPane.showInputDialog(this, "Enter ID2", null));
		ans.add(JOptionPane.showInputDialog(this, "Enter ID3", null));
		while (ans.remove("")) {
		}
		;
		ids = new long[ans.size()];
		for (int i = 0; i < ans.size(); i++) {
			ids[i] = Integer.parseInt(ans.get(i));
		}
	}

	/**
	 * Description: the method setup Board's parameters by: adding a MouseListener
	 * and a KeyListener to the JPanel, creating the JLabel to hold the game's
	 * statistics, and if the object was not created empty, the method creates a new
	 * Play object using the file path, and then set ids accordingly.
	 * 
	 * @throws FileNotFoundException
	 */
	private void initBoard() throws FileNotFoundException {
		map = new Map();
		setPreferredSize(new Dimension(map.getImage().getWidth(this), map.getImage().getHeight(this)));

		addMouseListener(this);

		addKeyListener(this);
		setFocusable(true);
		this.requestFocusInWindow();
		this.setFocusTraversalKeysEnabled(false);

		score = new JLabel();
		score.setLocation(0, 630);
		score.setForeground(Color.white);
		this.add(score);

		repaint();

		if (selectedFile != null) {
			play = new Play(selectedFile);
			if (ids.length == 3) {
				play.setIDs(ids[0], ids[1], ids[2]);
			} else if (ids.length == 2) {
				play.setIDs(ids[0], ids[1]);
			} else if (ids.length == 1) {
				play.setIDs(ids[0]);
			}
			repaint();
		}
	}

	/**
	 * Description: mouseClicked method, uses the location received by the
	 * MouseEvent to set the initial location of the player. the method then creates
	 * a thread and call for a method to animate the process of the game.
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		LatLonAlt p = map.PixelsToCoords(new Point3D(e.getX(), e.getY(), 0.0));
		play.setInitLocation(p.lat(), p.lon());
		repaint();
		play.start();
		if (auto) {
			advance();
		} else {
			(new Thread() {
				public void run() {
					animate();
				}
			}).start();
		}
	}

	/**
	 * Description: according to each arrow key pressed on the keyboard, the method
	 * updates the 'deg' field to match the direction of the arrow, as the player
	 * sees it.
	 */

	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		if (code == KeyEvent.VK_UP) {
			deg = 0;
		}
		if (code == KeyEvent.VK_DOWN) {
			deg = 180;
		}
		if (code == KeyEvent.VK_LEFT) {
			deg = 270;
		}
		if (code == KeyEvent.VK_RIGHT) {
			deg = 90;
		}
	}

	/**
	 * Description: animate method is in charge of keeping the moving game pieces in
	 * motion, by calling a Play method to alter their location and then repainting.
	 * the method uses a thread and set it to sleep after every motion, for the
	 * repaint method to catch up. the play method 'rotate' is given the direction
	 * (in degrees) for the player to advance toward.
	 */
	private void animate() {
		while (play.isRunning()) {
			if (deg != -1) {
				play.rotate(deg);
				repaint();
			}
			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		play.stop();
	}

	/**
	 * Description: advance method is called when automatic pilot is set on. the
	 * method divides the various possible games into 2 cases: one where there are
	 * no obstacles on the field, and the other where there are. the method runs as
	 * long as play is running. by the end, auto pilot is turned off.
	 */
	private void advance() {
		while (play.isRunning()) {
			if (play.getGame().getObstacles().isEmpty()) {

				eatClosestFruit();
			} else {
				getTarget();
			}
		}
		play.stop();
		auto = false;
	}

	/**
	 * Description: the method is called forth by advance when there are no
	 * obstacles on the field. the method finds the closest fruit to the current
	 * position on the player. while it is still on the board, it will call rotate
	 * at the fruit's direction again and again until the player or other game-piece
	 * eats it.
	 */
	private void eatClosestFruit() {
		int ind = play.getClosestFruit(play.getGame().getPlayer());
		Fruit f = play.getGame().getFruits().get(ind);
		while (play.getGame().getFruits().contains(f)) {
			double degree = play.getGame().getPlayer().getLocation().azimuth_elevation_dist(f.getLocation())[0];
			play.rotate(degree);
			repaint();

			try {
				Thread.sleep(60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Description: getTarget is called forth by advance when the game contains
	 * obstacles. in order to create a same path to the fruits, a PlayerAlgo object
	 * is created, the method uses the path created by it and calls rotate
	 * repeatedly until player has visited all the points in path.
	 * 
	 */
	public void getTarget() {
		PlayerAlgo pa = new PlayerAlgo(play.getGame().getPlayer(), play.getGame().getFruits(),
				play.getGame().getObstacles());
		ArrayList<Node> path = new ArrayList<Node>();
		path = pa.createPath();

		while (!path.isEmpty()) {

			double degree = play.getGame().getPlayer().getLocation()
					.azimuth_elevation_dist(path.get(0).getLocation())[0];
			double dist = play.getGame().getPlayer().getRadius() + play.getGame().getPlayer().getSpeed() / 10;

			while (play.getGame().getPlayer().getLocation().GPS_distance(path.get(0).getLocation()) >= dist) {
				play.rotate(degree);
				repaint();

				try {
					Thread.sleep(60);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			path.remove(0);
		}
	}

	/**
	 * Description: set auto pilot on. after one game, auto pilot is reversed to off
	 * again.
	 */
	public void AutoPilotOn() {
		auto = true;
	}

	/**
	 * Description: draw the game on the screen.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(map.getImage(), 0, 0, null);

		if (play != null) {
			drawGame(g);
		}
	}

	/**
	 * Description: draw the game pieces on the screen.
	 * 
	 * @param g
	 */
	private void drawGame(Graphics g) {

		Game game = play.getGame();
		Point3D p1;
		score.setText(play.getStatistics());

		for (Obstacle ob : game.getObstacles()) {

			p1 = map.CoordsToPixels(ob.getBounds().get(0));
			Point3D p2 = map.CoordsToPixels(ob.getBounds().get(1));
			int w = Math.abs(p2.ix() - p1.ix());
			int h = Math.abs(p2.iy() - p1.iy());
			g.setColor(Color.BLACK);
			g.fillRect(p2.ix() - w, p2.iy(), w, h);
		}

		for (Fruit fr : game.getFruits()) {
			p1 = map.CoordsToPixels(fr.getLocation());
			g.drawImage(fr.getImage(), p1.ix(), p1.iy(), 30, 30, null);
		}
		for (Packman p : game.getPackmans()) {
			p1 = map.CoordsToPixels(p.getLocation());
			g.drawImage(p.getImage(), p1.ix(), p1.iy(), 30, 30, null);
		}
		for (Packman gh : game.getGhosts()) {
			p1 = map.CoordsToPixels(gh.getLocation());
			g.drawImage(gh.getImage(), p1.ix(), p1.iy(), 50, 50, null);
		}
		if (game.getPlayer() != null) {
			p1 = map.CoordsToPixels(game.getPlayer().getLocation());
			g.drawImage(game.getPlayer().getImage(), p1.ix(), p1.iy(), 30, 30, null);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {

		// TODO Auto-generated method stub
	}
}
