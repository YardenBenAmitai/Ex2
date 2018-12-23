package GIS;

import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Coords.MyCoords;
import Geom.Point3D;

/**
 * Description:
 * the class represents a picture of a map 
 * and its fields are an image, gps points of top left and bottom right corners of the image and the bounds of the image.
 * the class also turns gps coordinates into x,y pixels, and back, and calculates the 2d distance between two points.
 * 
 * @author Yarden and Caroline
 *
 */
public class Map{
	
	BufferedImage img=null;
	Point3D TopLeft=null;
	Point3D BottomRight=null;
	Point3D Bounds=null;
	
	/**
	 * Description:
	 * the constructor receives a file name for the image and the top left and bottom right gps coordinates 
	 * for the image's corners.
	 * the constructor treats the top left corner as the (0,0) and by using the method "vector3D" from MyCoords class
	 * we are given the x,y values in meters and thus the bounds of the image.
	 * 
	 * @param pathName
	 * @param top_left
	 * @param bottom_right
	 */
	public Map(String pathName, Point3D top_left, Point3D bottom_right){
		
		TopLeft=top_left;
		BottomRight=bottom_right;
		MyCoords m=new MyCoords();
		Bounds=new Point3D(m.vector3D(TopLeft, BottomRight));
		try{
			img=ImageIO.read(new File(pathName));
		}catch (IOException e){
			
		}
	}
	
	public Map(String pathName){
		
		MyCoords m=new MyCoords();
		Bounds= new Point3D(1433,642,0);
		TopLeft= new Point3D(32.1057528,35.2021084,0);
		BottomRight=new Point3D(m.add(TopLeft, Bounds));
		
		try{
			img=ImageIO.read(new File(pathName));
		}catch (IOException e){
			
		}
	}
	
	
	/**
	 * Description:
	 * the method receives a gps point and returns it as x,y values
	 * of the given image.
	 * altitude will remain 0.
	 * 
	 * @param coord
	 * 
	 */
	public Point3D CoordsToPixels(Point3D coord){
		MyCoords m=new MyCoords();
		Point3D pix=new Point3D(m.vector3D(TopLeft, coord));
		return pix;
	}
	
	
	/**
	 * Description:
	 * the method receives a pixel(x,y) and turns it into gps.
	 * altitude will remain 0.
	 * 
	 * @param pxl
	 */
	public Point3D PixelsToCoords(Point3D pxl){
		MyCoords m=new MyCoords();
		Point3D coord=new Point3D(m.add(TopLeft, pxl));
		return coord;
	}
	
	/**
	 * Description:
	 * the method calculates the distance between two given pixels.
	 * 
	 * @param pxl1
	 * @param pxl2
	 */
	public double PixelDistance(Point3D pxl1, Point3D pxl2){
		double result=Math.sqrt(Math.pow(pxl2.x()-pxl1.x(), 2)+Math.pow(pxl2.y()-pxl1.y(), 2));
		return result;
	}
	
	
	////////////////
	public BufferedImage getImage(){
		return img;
	}
}
