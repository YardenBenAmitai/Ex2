package GUI;

import java.awt.EventQueue;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import javax.swing.JFrame;
import GIS.Game;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MyFrame extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;
	private GameBoard g;
	private int[] piece;
	
	
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
		
	public MyFrame(){
		
		initGameGUI();
	}
	
	
	public void initGameGUI(){
		
		JMenuBar m=new JMenuBar();
		JMenu menu=new JMenu("Game Sets");
		
		JMenuItem m1=new JMenuItem("Game Set 1");
		JMenuItem m2=new JMenuItem("Game Set 2");
		JMenuItem m3=new JMenuItem("Game Set 3");
		JMenuItem m4=new JMenuItem("Game Set 4");
		JMenuItem m5=new JMenuItem("Game Set 5");
		JMenuItem m6=new JMenuItem("Game Set 6");
		JMenuItem m7=new JMenuItem("Save Game");
		
		setJMenuBar(m);
		m.add(menu);
		menu.add(m1); menu.add(m2); menu.add(m3); menu.add(m4); menu.add(m5); menu.add(m6); menu.add(m7);
		
		JMenu create=new JMenu("New Game");
		JMenuItem c1=new JMenuItem("Create New Game");
		JMenuItem c2=new JMenuItem("New Pacman");
		JMenuItem c3=new JMenuItem("New Fruit");
		JMenuItem c4=new JMenuItem("Save Game");
		
		m.add(create);
		create.add(c1); create.add(c2); create.add(c3); create.add(c4);
		
		setSize(1433,642);
		setResizable(false);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
		///////////////////menu action listeners////////////////////
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
		
		m7.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				g.getGame().GameToCSV("game2csv.csv");
			}
		});
		
		c1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					loadBoard("");
					piece=new int[4];
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		c2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				piece[0]=0;
				piece[3]++;
			}
		});
		
		c3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				piece[0]=1;
				piece[3]++;
			}
		});
		
		c4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				g.getGame().GameToCSV("game2csv.csv");
			}
		});
		
	}
	
	
	public void loadBoard(String str) throws FileNotFoundException{
		if(g!=null){
			this.remove(g);
		}
		Game game;
		if(str=="")
			game=new Game();
		else
			game=new Game(str);
		g=new GameBoard(game);
		add(g);
		System.out.println(game);
		g.addMouseListener(new MouseListener() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		    	piece[2]=e.getX();
		        piece[1]=e.getY();
		        
		        g.LoadGamePiece(piece);
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
		
		pack();
		repaint();
	}
	
	
	
	public void paintComponents(Graphics g){
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
