
package GUI;

import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.*;

import Algorithms.Path;
import Algorithms.ShortestPathAlgo;
import GIS.Fruit;
import GIS.Game;
import GIS.Map;
import GIS.Packman;
import Geom.Point3D;


public class GameBoard extends JPanel{
	
	
	private static final long serialVersionUID = 1L;
	private Image img;
	
	private int B_Width;
	private int B_Height;
	private Map map;
	private Game game=null;
	private ShortestPathAlgo set=null;
	
	
	private List<PackmanPiece> pieces=null;
	
	
	
    public GameBoard(Game g) {

        initBoard(g);
    }
    
    
    

	private void initBoard(Game g) {
        
        loadImage();
        B_Width= img.getWidth(this);
        B_Height =  img.getHeight(this);
        setPreferredSize(new Dimension(B_Width, B_Height));
        game=g;
        if(game==null){
        	animate();
        }
        repaint();
    }
    
	private void animate(){
		set=new ShortestPathAlgo(game);
        
        Iterator<Path> it=set.getPackmanPaths().iterator();
		pieces=new ArrayList<PackmanPiece>();
		while(it.hasNext()){
			pieces.add(new PackmanPiece(it.next().getPath(),map));
		}
		
		Timer timer = new Timer(300, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PackmanPiece pp  : pieces) {
                    if(!pp.fp.isEmpty()){
                    	pp.animate();
                    }
                }
                repaint();
            }
        });
        timer.start();
	}
    
    private void loadImage() {
        ImageIcon iim = new ImageIcon("img/Ariel1.png");
        img = iim.getImage();
        map=new Map("img/Ariel1.png");
    }
    
    
    public void LoadGamePiece(int[] piece){
    	if(piece[0]==0){
    		game.addPackman(new Packman(piece[3], map.PixelsToCoords(new Point3D(piece[1], piece[2],0)),1,1));
    	}
    	else{
    		game.addFruit(new Fruit(piece[3], map.PixelsToCoords(new Point3D(piece[1], piece[2],0)),1));
    	}
    	System.out.println(game);
    	animate();
    	repaint();
    }
    




	@Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        if(set!=null){
        	drawSet(g);
        }
        
    }
    
    public void drawSet(Graphics g){
    	for (PackmanPiece pp : pieces) {
            Graphics2D g2d = (Graphics2D) g.create();
            pp.paint(g2d);
            g2d.dispose();
    	}
    }
    
    
    public Game getGame(){
    	return game;
    }
}
	


