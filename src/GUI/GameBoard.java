
package GUI;

import java.awt.Dimension;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;

import Algorithms.Path;
import Algorithms.ShortestPathAlgo;
import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Map;
import GIS.Packman;
import Geom.Point3D;


/**
 * Description:
 * class's fields:img, fruit, pacman are the images displayed on the screen.
 * B_Width, B_Height are the bounds of the background image.
 * map, game, set are the objects used to create the display.
 * 
 * this class displays the actual game according to the shortest path algorithm(set).
 * @author Yarden and Caroline
 *
 */
public class GameBoard extends JPanel{
	
	
	private static final long serialVersionUID = 1L;
	private Image img;
	private Image fruit;
	private Image pacman;
	
	private int B_Width;
	private int B_Height;
	private Map map;
	private Game game=null;
	private ShortestPathAlgo set=null;
	private Timer timer;
	
	
	
	
	/**
	 * Description:
	 * simple constructor that calls initBoard method to set the parameters.
	 * @param g
	 */
    public GameBoard(Game g) {

        initBoard(g);
    }
    
    
    /**
     * Description:
     * the method loads the proper images to the fields and set the bounds of the component.
     * if the user picked a complete game set the method calls forth the animation method
     * else the program waits for the user to act.
     * @param g
     */
	private void initBoard(Game g) {
        
        loadImage();
        B_Width= img.getWidth(this);
        B_Height =  img.getHeight(this);
        setPreferredSize(new Dimension(B_Width, B_Height));
        game=g;
        if(game!=null){
        	animate();
        }
    }
    
	/**
	 * Description:
	 * the method uses the shortestPathAlgo to animate the game.
	 * the method uses AdvanceDistance to set each packman exactly in its right location according to its speed, every second.
	 * the packman eats the fruit from the distance of its radius or less, each fruit eaten disappears from the screen.
	 * the method stops when all the fruits in each path were eaten. 
	 */
	private void animate(){
		set=new ShortestPathAlgo(game);
		
		timer = new Timer(1, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int size=set.getPackmanPaths().size();
				Iterator<Path> it=set.getPackmanPaths().iterator();
	            Path path;
	            Point3D point;
	            MyCoords m=new MyCoords();
	            if (size==0){
	            	timer.stop();
	            }
	            else{
	            	while (it.hasNext()){
		            	path=it.next();
		            	if(path.getHead()!=null && !path.getPath().isEmpty()){
		            		point=map.AdvanceDistance(
		            				path.getHead().getData().get_Orientation(), 
		            				path.getPath().get(0).getData().get_Orientation(), 
		            				path.getHead().getSpeed());
		            		if (m.distance3d(path.getHead().getData().get_Orientation(), path.getPath().get(0).getData().get_Orientation())<=path.getHead().getRadius()){
		            			path.setHeadLocation(path.getPath().get(0).getData().get_Orientation());
		            			path.getPath().remove(0);
		            		}
		            		else{
		            			path.setHeadLocation(map.AdvanceDistance(
		            					path.getHead().getData().get_Orientation(), 
		                				path.getPath().get(0).getData().get_Orientation(), 
		                				path.getHead().getSpeed()));
		            		}		
		            	}
		            	else{
		            		size--;
		            	}
		            	repaint();
		            }
	            }
			}
		});
		timer.start();
	}
    
	/**
	 * Description:
	 * the method loads the images to the class's fields.
	 */
    private void loadImage() {
    	map=new Map("img/Ariel1.png");
        ImageIcon iim = new ImageIcon("img/Ariel1.png");
        img = iim.getImage();
        ImageIcon iif=new ImageIcon("img/Fruit.png");
        fruit = iif.getImage();
        ImageIcon iip=new ImageIcon("img/Pacman.png");
        pacman = iip.getImage();
    }
    
    /**
     * Description:
     * the method loads a game piece to game field according to its parameter and then displays the pieces motionless.
     * @param piece
     */
    public void LoadGamePiece(int[] piece){
    	if(piece[0]==0){
    		game.addPackman(new Packman(piece[3], map.PixelsToCoords(new Point3D(piece[1], piece[2],0)),1,1));
    	}
    	else{
    		game.addFruit(new Fruit(piece[3], map.PixelsToCoords(new Point3D(piece[1], piece[2],0)),1));
    	}
    	set=new ShortestPathAlgo(game);
    	//animate();
    	repaint();
    }
    
    
    /**
     * Description:
     * default method to display the game pieces.
     */
	@Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        if(set!=null){
        	drawSet(g);
        }
        
    }
    
    public void drawSet(Graphics g){
    	for (Path p: set.getPackmanPaths()){
    		Point3D loc=map.CoordsToPixels(p.getHead().getData().get_Orientation());
    		g.drawImage(pacman, loc.ix(), loc.iy(), 30, 30, null);
    		
    		for(Fruit f: p.getPath()){
    			loc=map.CoordsToPixels(f.getData().get_Orientation());
    			g.drawImage(fruit, loc.ix(), loc.iy(), 30, 30, null);
    			
    		}
    	}
    }
    
    //////////////setters and getters/////////////////
    public Game getGame(){
    	return game;
    }
}
	


