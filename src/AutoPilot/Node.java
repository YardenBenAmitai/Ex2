package AutoPilot;

import Coords.LatLonAlt;

/**
 * Description: the class represents vertexes in the game where the auto pilot
 * is trying to create a path to the fruits. the class's fields are an index,
 * the location and a variable that holds the number of the vertexes away to a
 * nearby fruit.
 * 
 * @author Yarden
 *
 */
public class Node {
	private LatLonAlt Location;
	private int nearF = -1;
	private double index;

	/**
	 * Description: Constructor with index and location
	 * 
	 * @param ind
	 * @param loc
	 */
	public Node(double ind, LatLonAlt loc) {
		index = ind;
		Location = new LatLonAlt(loc);
	}

	/**
	 * Description: Constructor from another Node object
	 * 
	 * @param n
	 */
	public Node(Node n) {
		index = n.getIndex();
		nearF = n.getNearFruit();
		Location = new LatLonAlt(n.getLocation());
	}

	public double getIndex() {
		return index;
	}

	public LatLonAlt getLocation() {
		return Location;
	}

	public void setIndex(double ind) {
		index = ind;
	}

	public void setLocation(LatLonAlt lla) {
		Location = new LatLonAlt(lla);
	}

	public void setNearFruit(int i) {
		nearF = i;
	}

	public int getNearFruit() {
		return nearF;
	}

	public String toString() {
		return "Node: " + index + ", Nearby Fruit:" + nearF + ", Location: " + Location;
	}
}
