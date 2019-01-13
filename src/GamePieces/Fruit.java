package GamePieces;

import java.awt.Image;
import java.util.Date;

import javax.swing.ImageIcon;

import Coords.LatLonAlt;

/**
 * Description: the class represents a motionless fruit with weight, location
 * and id. the fruits are to be eaten by a packman object.
 * 
 * @author User
 *
 */
public class Fruit {

	private int ID;
	private LatLonAlt Location = null;
	private long InitTime;
	private double Weight;
	private Image fruit_img;

	/**
	 * Description: constructor
	 * 
	 * @param id
	 * @param location
	 * @param weight
	 */
	public Fruit(int id, LatLonAlt location, double weight) {
		ID = id;
		Location = new LatLonAlt(location);
		Weight = weight;
		InitTime = new Date().getTime();
		loadImage();
	}

	public Fruit(String str) {
		String[] fields = str.split(",");
		ID = Integer.parseInt(fields[1]);
		Location = new LatLonAlt(fields[2] + "," + fields[3] + "," + fields[4]);
		Weight = Double.parseDouble(fields[5]);
		InitTime = new Date().getTime();
		loadImage();
	}

	public Fruit(Fruit f) {
		ID = f.getID();
		Location = new LatLonAlt(f.getLocation());
		Weight = f.getWeight();
		InitTime = new Date().getTime();
		loadImage();
	}

	private void loadImage() {
		ImageIcon iif = new ImageIcon("img/fruit.png");
		fruit_img = iif.getImage();
	}

	public void loadImage(String FilePath) {
		ImageIcon iif = new ImageIcon(FilePath);
		fruit_img = iif.getImage();
	}

	public String toString() {
		return ("F: " + ID + ", Location: " + Location);
	}

	////////////////// getters and setters//////////
	public Image getImage() {
		return fruit_img;
	}

	/**
	 * Description: method returns the weight of a fruit
	 * 
	 * @return weight
	 */
	public double getWeight() {
		return Weight;
	}

	/**
	 * Description: method sets a new weight to fruit according to given param.
	 * 
	 * @param weight
	 */
	public void setWeight(double weight) {
		Weight = weight;
	}

	public int getID() {
		return ID;
	}

	public long getInitTime() {
		return InitTime;
	}

	public LatLonAlt getLocation() {
		return Location;
	}

}
