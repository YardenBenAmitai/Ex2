package AutoPilot;

import java.util.ArrayList;

import Coords.Cords;
import Coords.LatLonAlt;
import GamePieces.Fruit;
import GamePieces.Obstacle;
import GamePieces.Packman;

/**
 * Description: the class represents all the obstacles vertexes and fruits as
 * Node elements, in such a way that the class can generate a path from the
 * player's location to the games fruits without points lost by Obstacles in the
 * process. the class's fields are an obstacles list, fruits list, vertexes list
 * containing all the fruits and obstacles and the currant location of the
 * player.
 * 
 * @author Yarden
 *
 */
public class PlayerAlgo {
	private ArrayList<Obstacle> obstacles;
	private ArrayList<Node> vertex = null;
	private ArrayList<Fruit> fruits = null;
	private Packman curr;

	/**
	 * Description: Constructor calls for two methods, one to turn every relevant
	 * point on the field into Node object and the other to assign each node
	 * additional information to generate a path.
	 * 
	 * @param p
	 * @param fl
	 * @param ol
	 */
	public PlayerAlgo(Packman p, ArrayList<Fruit> fl, ArrayList<Obstacle> ol) {
		obstacles = new ArrayList<Obstacle>();
		obstacles.addAll(ol);
		fruits = new ArrayList<Fruit>();
		fruits.addAll(fl);
		vertex = new ArrayList<Node>();
		curr = p;
		LoadVertex();
		GenerateVertex();
	}

	/**
	 * Description: the method finds all four vertexes of the obstacles and then
	 * create a node, for each of them, that is slightly altered to a safe
	 * direction, so that the player can use them without point loss. the method
	 * also turns all the fruits into Node objects and uses their exact location.
	 */
	private void LoadVertex() {
		LatLonAlt lla1, lla2;
		Node n1, n2, n3, n4;

		for (Obstacle ob : obstacles) {
			lla1 = new LatLonAlt(ob.getBounds().get(0));
			lla2 = new LatLonAlt(ob.getBounds().get(1));
			double[] ll1 = { lla1.lat(), lla1.lon(), lla1.alt() };
			double[] ll2 = { lla2.lat(), lla2.lon(), lla2.alt() };
			double[] a1 = Cords.offsetLatLonAzmDist(ll1, 225, 5);
			double[] a2 = Cords.offsetLatLonAzmDist(ll2, 45, 5);

			n1 = new Node(ob.getID() + 0.1, new LatLonAlt(a1[0], a1[1], lla1.z()));// bottom left
			n2 = new Node(ob.getID() + 0.2, new LatLonAlt(a2[0], a2[1], lla2.z()));// top right

			double[] ll3 = { lla2.lat(), lla1.lon(), lla1.alt() };
			double[] ll4 = { lla1.lat(), lla2.lon(), lla2.alt() };
			double[] a3 = Cords.offsetLatLonAzmDist(ll3, 315, 5);
			double[] a4 = Cords.offsetLatLonAzmDist(ll4, 135, 5);

			n3 = new Node(ob.getID() + 0.3, new LatLonAlt(a3[0], a3[1], lla1.z()));// top left
			n4 = new Node(ob.getID() + 0.4, new LatLonAlt(a4[0], a4[1], lla2.z()));// bottom right
			vertex.add(n1);
			vertex.add(n2);
			vertex.add(n3);
			vertex.add(n4);
		}
		for (Fruit f : fruits) {
			lla1 = new LatLonAlt(f.getLocation());
			n1 = new Node(f.getID(), lla1);
			n1.setNearFruit(0);
			vertex.add(n1);
		}
	}

	/**
	 * Description: the method assign each Node a special number that indicate how
	 * many vertexes away is it from a fruit. fruits are obviously assigned 0. any
	 * Node that has a direct line to a fruit(with no obstacles in the middle) is
	 * assigned 1 etc.
	 */
	private void GenerateVertex() {
		for (Fruit f : fruits) {
			for (Node n1 : vertex) {
				if (obstacleFree(f.getLocation(), n1.getLocation()) && n1.getNearFruit() == -1) {
					n1.setNearFruit(1);
				}
			}
		}
		for (int i = 0; i < 10; i++) {// runs only up to 10 because thats the maximus needed to map the important
										// nodes. anything not mapped by then is irrelevant.
			for (Node n2 : vertex) {
				if (n2.getNearFruit() == -1) {
					for (Node n3 : vertex) {
						if (obstacleFree(n2.getLocation(), n3.getLocation()) && n2 != n3 && n3.getNearFruit() != -1) {
							if (n2.getNearFruit() == -1 || (n3.getNearFruit() + 1) < n2.getNearFruit()) {
								n2.setNearFruit(n3.getNearFruit() + 1);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Description: the method is called each time for a fruit to be eaten. the
	 * method searches for nodes that are linked directly to the player and send the
	 * one closest to a method that generates a path.
	 * 
	 * @return path
	 */
	public ArrayList<Node> createPath() {
		ArrayList<Node> direct = new ArrayList<Node>();
		int counter = 0;

		while (direct.isEmpty()) {
			for (Node n : vertex) {
				if (obstacleFree(curr.getLocation(), n.getLocation()) && n.getNearFruit() == counter) {
					direct.add(n);
				}
			}
			counter++;
		}

		Node closest = direct.get(0);
		for (Node n2 : direct) {
			if (closest.getLocation().GPS_distance(curr.getLocation()) > n2.getLocation()
					.GPS_distance(curr.getLocation())) {
				closest = new Node(n2);
			}
		}
		ArrayList<Node> path = new ArrayList<Node>();
		path = GeneratePath(closest);
		return path;
	}

	/**
	 * Description: the method receives the closest node to the player that is also
	 * the closest to a fruit. while path is yet to contain a fruit, it would find a
	 * node with direct line to closest and that is closer to a fruit and add it to
	 * a path (and repeat the process until a fruit is inside path).
	 * 
	 * @param closest
	 * @return path
	 */
	private ArrayList<Node> GeneratePath(Node closest) {
		ArrayList<Node> path = new ArrayList<Node>();
		path.add(closest);
		while (path.get(path.size() - 1).getNearFruit() != 0) {
			for (Node n1 : vertex) {
				if (obstacleFree(closest.getLocation(), n1.getLocation())
						&& (n1.getNearFruit() == closest.getNearFruit() - 1)) {
					path.add(n1);
					closest = new Node(n1);
				}
			}
		}
		return path;
	}

	/**
	 * Description: test a given LatLonAlt whether its inside an obstacle bounds.
	 * 
	 * @param lla
	 * @return
	 */
	private boolean Valid(LatLonAlt lla) {
		for (Obstacle ob : obstacles) {
			if (ob.inBounds(lla)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Description: the method is given two LatLonAlt elements and test to see
	 * whether a player can move from one to the other without any points lost in
	 * the process.
	 * 
	 * @param lla1
	 * @param lla2
	 * @return true if the way is obstacle free and false otherwise
	 */
	private boolean obstacleFree(LatLonAlt lla1, LatLonAlt lla2) {
		Packman p = new Packman(-1, lla1, 20, 1);
		p.setOrientation(lla2);

		double distance = p.getRadius() + p.getSpeed() / 10;
		while (Valid(p.getLocation())) {
			if (p.getLocation().GPS_distance(lla2) < distance) {
				return true;
			}
			p.move(100);
		}
		return false;
	}
}
