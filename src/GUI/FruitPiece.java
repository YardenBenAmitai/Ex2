package GUI;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import GIS.Map;
import Geom.Point3D;


public class FruitPiece implements GamePiece{
	private ImageIcon iif=new ImageIcon("img/Fruit.png");
	private int x;
	private int y;
	
	public FruitPiece(Point3D p){
		x=p.ix();
		y=p.iy();
	}
	public void animate() {
		
		
	}

	
	public void paint(Graphics2D g2d) {
		g2d.drawImage(iif.getImage(), x,y,30,30,null);
		
	}
	
	public int getx(){
		return x;
	}
	public int gety(){
		return y;
	}
	
}
