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
	
	/**
	 * Description:
	 * the constructor uses its default coordinates for topleft and bottomright.
	 * @param pathName
	 */
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
		return new Point3D(Math.abs(pix.x()), Math.abs(pix.y()), Math.abs(pix.z()));
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
	
	/**
	 * Description:
	 * the method calculate the coordinate between source(gps0) and destination(gps1) 
	 * where the source advances a given distance in meters.
	 * the method uses Haversine formula to find the distance between source to destination,
	 * and uses it to find the ratio to alter the source.
	 * 
	 * see also: https://stackoverflow.com/questions/3073281/how-to-move-a-marker-100-meters-with-coordinates
	 * 
	 * @param gps0
	 * @param gps1
	 * @param distance
	 * @return new coordinate
	 */
	public Point3D AdvanceDistance(Point3D gps0, Point3D gps1, double distance){
		
		double radius = 6371000; // radius of earth in meters
		double latDist = gps0.x() - gps1.x();
		double lngDist = gps0.y() - gps1.y();
		
		latDist = Math.toRadians(latDist);
		lngDist = Math.toRadians(lngDist);
		latDist = Math.sin(latDist);
		lngDist = Math.sin(lngDist);
		
		double cosLat1 = Math.cos(Math.toRadians(gps0.x()));
		double cosLat2 = Math.cos(Math.toRadians(gps1.x()));
		double a = (latDist/2)*(latDist/2) + cosLat1*cosLat2*(lngDist/2)*(lngDist/2);
		
		if(a<0){
			a = -1*a;
		}
		
		double ratio=distance/(radius*2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a)));
		return new Point3D(gps0.x() + ((gps1.x() - gps0.x()) * ratio), gps0.y() + ((gps1.y() - gps0.y()) * ratio),0);
	}
	
	
	////////////////getters and setters///////////////////
	
	public BufferedImage getImage(){
		return img;
	}
}
