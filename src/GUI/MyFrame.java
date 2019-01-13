package GUI;

import java.awt.EventQueue;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import GamePieces.Fruit;
import GamePieces.Game;
import GamePieces.Packman;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Coords.Map;

/**
 * Description: the class is the main window of the game. the class's fields are
 * a default serialVersionUID, a GameBoard where the actual game takes place and
 * an array, set to null, unless the user create a new game piece. the class has
 * two menues: GameSets where complete game sets are stores and can be
 * displayed. "New Game" where the user can create a new game set and save it.
 * 
 * in ex4 a new menu was added that allows the player to pick a game of packman
 * to play, and also let the auto pilot play instead. the player choose a game
 * from data folder, if the player would like the auto pilot on, then he press
 * on the option in the menu and then set the initial location of the player on
 * the board. the player uses the arrow keys to move the game piece around
 * without touching any obstacles or ghosts, while eating the fruits and
 * packmans.
 * 
 * 
 * @author Yarden
 *
 */
public class MyFrame extends JFrame implements MouseListener {

	private static final long serialVersionUID = 1L;
	private GameBoard g; // ex 2-3
	private double[] piece = null;
	private Game game = null;
	private Board board; // ex 4

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					MyFrame frame = new MyFrame();
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Description: constructor that starts the gui functionality.
	 */
	public MyFrame() {

		initGameGUI();
	}

	/**
	 * Description: the method where the JFrame assembles all its components which
	 * are 2 menues, BoardGame(JPanel) action listeners to all the menu buttons and
	 * a mouse listener to the screen for when the user places a new game piece.
	 */
	public void initGameGUI() {
		try {
			LoadPanel();
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		JMenuBar m = new JMenuBar();
		setJMenuBar(m);

		JMenu menu = new JMenu("Game Sets");
		JMenuItem m1 = new JMenuItem("Game Set 1");
		JMenuItem m2 = new JMenuItem("Game Set 2");
		JMenuItem m3 = new JMenuItem("Game Set 3");
		JMenuItem m4 = new JMenuItem("Game Set 4");
		JMenuItem m5 = new JMenuItem("Game Set 5");
		JMenuItem m6 = new JMenuItem("Game Set 6");

		m.add(menu);
		menu.add(m1);
		menu.add(m2);
		menu.add(m3);
		menu.add(m4);
		menu.add(m5);
		menu.add(m6);

		JMenu create = new JMenu("New Game");
		JMenuItem c1 = new JMenuItem("Create New Game");
		JMenuItem c2 = new JMenuItem("New Pacman");
		JMenuItem c3 = new JMenuItem("New Fruit");
		JMenuItem c4 = new JMenuItem("Save Game");
		JMenuItem c5 = new JMenuItem("Play Game");

		m.add(create);
		create.add(c1);
		create.add(c2);
		create.add(c3);
		create.add(c4);
		create.add(c5);

		JMenu play = new JMenu("Play");
		JMenuItem p1 = new JMenuItem("Load Game");
		JMenuItem p2 = new JMenuItem("Automatic Pilot");

		m.add(play);
		play.add(p1);
		play.add(p2);

		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		/////////////////// Game Set Animation Menu////////////////////
		m1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543684662657.csv");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		m2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543685769754.csv");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		m3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {

					loadBoard("csv/game_1543693822377.csv");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		m4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543693911932.csv");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		m5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543693911932_a.csv");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		m6.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("csv/game_1543693911932_b.csv");

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		/////////////// New Game Menu////////////////////////

		c1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("");
					piece = new double[3];
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		c2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				piece[0] = 0;
			}
		});

		c3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				piece[0] = 1;
			}
		});

		c4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				game = new Game();
				Map map = new Map();
				int i = 0;
				for (Packman p : g.getGame().getPackmans()) {
					game.addPackman(new Packman(i, map.PixelsToCoords(p.getLocation()), p.getSpeed(), p.getRadius()));
					i++;
				}
				i = 0;
				for (Fruit f : g.getGame().getFruits()) {
					game.addFruit(new Fruit(i, map.PixelsToCoords(f.getLocation()), f.getWeight()));
					i++;
				}
				game.GameToCSV("csv/game2csv.csv");
			}
		});

		c5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (game != null) {
					try {
						loadBoard("csv/game2csv.csv");
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		//////////////////////// Play Menu//////////////////
		p1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new java.io.File("."));
				fileChooser.setDialogTitle("User/eclipse-workspace/Ex4_OOP/data/");
				int result = fileChooser.showOpenDialog(getParent());
				if (result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					try {
						LoadPanel(selectedFile);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		p2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				board.AutoPilotOn();
			}
		});
	}

	/**
	 * Description: creates an empty Board to display only the background of the
	 * game, for esthetic reasons.
	 * 
	 * @throws FileNotFoundException
	 */
	private void LoadPanel() throws FileNotFoundException {
		board = new Board();
		add(board);
		pack();

	}

	/**
	 * Description: creates a new Board based on the game file selected by the user
	 * 
	 * @param selectedFile
	 * @throws FileNotFoundException
	 */
	private void LoadPanel(File selectedFile) throws FileNotFoundException {
		remove(board);
		board = new Board(selectedFile.getPath());
		add(board);
		pack();
		setVisible(true);
	}

	/**
	 * Description: a method called forth by the initGameGUI to set the GameBoard
	 * parameters according to the button's menu, clicked.
	 * 
	 * @param str
	 * @throws FileNotFoundException
	 */
	public void loadBoard(String str) throws FileNotFoundException {
		if (g != null) {
			this.remove(g);
		}
		Game game;
		if (str == "")
			game = new Game();
		else
			game = new Game(str);
		g = new GameBoard(game);
		add(g);
		pack();
		g.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (piece != null) {
					piece[1] = e.getX();
					piece[2] = e.getY();

					g.LoadGamePiece(piece);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/**
	 * Description: default method of Swing components to display the game pieces.
	 */
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}

	/////////////////////
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
