package GamePieces;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import Coords.LatLonAlt;

public class Game {
	private Packman _player = null;
	private ArrayList<Packman> _packmans = null;
	private ArrayList<Packman> _ghosts = null;
	private ArrayList<Fruit> _fruits = null;
	private ArrayList<Obstacle> _obstacles = null;
	private long InitTime;

	/**
	 * Description: the constructor receives a csv filename as parameter, reads from
	 * said file and construct a PackmenSet and FruitSet from it.
	 * 
	 * @param csvFile
	 * @throws FileNotFoundException
	 */
	public Game(String csvFile) throws FileNotFoundException {

		// creating empty array-lists for later appending.
		_packmans = new ArrayList<Packman>();
		_ghosts = new ArrayList<Packman>();
		_fruits = new ArrayList<Fruit>();
		_obstacles = new ArrayList<Obstacle>();

		BufferedReader csvReader = new BufferedReader(new FileReader(csvFile));
		String currLine;

		try {
			// reading from csvFile, second line.
			currLine = csvReader.readLine();

			while ((currLine = csvReader.readLine()) != null) {

				if (currLine.charAt(0) == 'P') {
					Packman p = new Packman(currLine);
					p.setImage("img/pacman.png");
					_packmans.add(p);
				} else if (currLine.charAt(0) == 'F') {
					Fruit f = new Fruit(currLine);
					_fruits.add(f);
				} else if (currLine.charAt(0) == 'G') {
					Packman g = new Packman(currLine);
					g.setImage("img/ghost.png");
					_ghosts.add(g);
				} else if (currLine.charAt(0) == 'B') {
					Obstacle ob = new Obstacle(currLine);
					_obstacles.add(ob);
				} else if (currLine.charAt(0) == 'M') {
					_player = new Packman(currLine);
					_player.setImage("img/player.png");
				}
			}

			csvReader.close();

		} catch (IOException e) {

		}

	}

	/**
	 * Description: empty constructor
	 */
	public Game() {
		_packmans = new ArrayList<Packman>();
		_ghosts = new ArrayList<Packman>();
		_fruits = new ArrayList<Fruit>();
		_obstacles = new ArrayList<Obstacle>();
		InitTime = new Date().getTime();
	}

	public Game(Game game) {
		_packmans = new ArrayList<Packman>();
		_ghosts = new ArrayList<Packman>();
		_fruits = new ArrayList<Fruit>();
		_obstacles = new ArrayList<Obstacle>();
		InitTime = new Date().getTime();

		setPackmans(game.getPackmans());
		setFruits(game.getFruits());
		setGhosts(game.getGhosts());
		setObstacles(game.getObstacles());
		setPlayer(game.getPlayer());
	}

	/**
	 * Description: the method converts object Game into csv file at the given
	 * parameter.
	 * 
	 * @param csvFile
	 */
	public void GameToCSV(String csvFile) {

		try {
			PrintWriter pw = new PrintWriter(new File(csvFile));
			StringBuilder sb = new StringBuilder();
			sb.append("Type" + ',' + "id" + ',' + "Lat" + ',' + "Lon" + ',' + "Alt" + ',' + "Speed/Weight" + ','
					+ "Radius" + ',' + _packmans.size() + ',' + _fruits.size() + ',' + _obstacles.size());
			sb.append('\n');

			Iterator<Packman> it_p = _packmans.iterator();
			Packman p;
			while (it_p.hasNext()) {
				p = it_p.next();
				sb.append('P' + ',' + Integer.toString(p.getID()) + ',' + Double.toString(p.getLocation().x()) + ','
						+ Double.toString(p.getLocation().y()) + ',' + Double.toString(p.getLocation().z()) + ','
						+ Double.toString(p.getSpeed()) + ',' + Double.toString(p.getRadius()));
				sb.append('\n');
			}

			Iterator<Packman> it_g = _ghosts.iterator();
			Packman g;
			while (it_g.hasNext()) {
				g = it_g.next();
				sb.append('G' + ',' + Integer.toString(g.getID()) + ',' + Double.toString(g.getLocation().x()) + ','
						+ Double.toString(g.getLocation().y()) + ',' + Double.toString(g.getLocation().z()) + ','
						+ Double.toString(g.getSpeed()) + ',' + Double.toString(g.getRadius()));
				sb.append('\n');
			}

			Iterator<Fruit> it_f = _fruits.iterator();
			Fruit f;
			while (it_f.hasNext()) {
				f = it_f.next();
				sb.append('F' + ',' + Integer.toString(f.getID()) + ',' + Double.toString(f.getLocation().x()) + ','
						+ Double.toString(f.getLocation().y()) + ',' + Double.toString(f.getLocation().z()) + ','
						+ Double.toString(f.getWeight()));
				sb.append('\n');
			}

			Iterator<Obstacle> it_ob = _obstacles.iterator();
			Obstacle ob;
			ArrayList<LatLonAlt> bounds = new ArrayList<LatLonAlt>();
			while (it_ob.hasNext()) {
				ob = it_ob.next();
				bounds = ob.getBounds();
				sb.append('B' + ',' + Integer.toString(ob.getID()) + ',' + Double.toString(bounds.get(0).x()) + ','
						+ Double.toString(bounds.get(0).y()) + ',' + Double.toString(bounds.get(0).z()) + ','
						+ Double.toString(bounds.get(1).x()) + ',' + Double.toString(bounds.get(1).y()) + ','
						+ Double.toString(bounds.get(1).z()) + ',' + Double.toString(ob.getWeight()));
				sb.append('\n');
			}
			pw.write(sb.toString());
			pw.close();
		} catch (IOException e) {

		}

	}

	public int FruitIndex(Fruit f) {
		Iterator<Fruit> it = _fruits.iterator();
		int counter = 0;
		Fruit fr;
		while (it.hasNext()) {
			fr = it.next();
			if (fr.getID() == f.getID() && fr.getLocation() == f.getLocation()) {
				break;
			}
			counter++;
		}
		return counter;
	}

	public boolean addPackman(Packman p) {
		return _packmans.add(p);
	}

	public boolean addGhost(Packman g) {
		return _ghosts.add(g);
	}

	public boolean addFruit(Fruit f) {
		return _fruits.add(f);
	}

	public boolean addObstacle(Obstacle ob) {
		return _obstacles.add(ob);
	}

	public Iterator<Packman> PIterator() {
		Iterator<Packman> it = _packmans.iterator();
		return it;
	}

	public Iterator<Packman> GIterator() {
		Iterator<Packman> it = _ghosts.iterator();
		return it;
	}

	public Iterator<Fruit> FIterator() {
		Iterator<Fruit> it = _fruits.iterator();
		return it;
	}

	public Iterator<Obstacle> OBIterator() {
		Iterator<Obstacle> it = _obstacles.iterator();
		return it;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		Iterator<Packman> itp = _packmans.iterator();
		while (itp.hasNext()) {
			sb.append(itp.next().toString());
			sb.append('\n');
		}
		Iterator<Packman> itg = _ghosts.iterator();
		while (itg.hasNext()) {
			sb.append(itg.next().toString());
			sb.append('\n');
		}
		Iterator<Fruit> itf = _fruits.iterator();
		while (itf.hasNext()) {
			sb.append(itf.next().toString());
			sb.append('\n');
		}
		Iterator<Obstacle> ito = _obstacles.iterator();
		while (ito.hasNext()) {
			sb.append(ito.next().toString());
			sb.append('\n');
		}
		if (_player != null) {
			sb.append(_player.toString());
		}
		return sb.toString();
	}

	/////////////// getters and setters///////////////
	public Packman getPlayer() {
		return _player;
	}

	public void setPlayer(Packman pl) {
		if (pl != null) {
			_player = new Packman(pl);
		}
	}

	public ArrayList<Packman> getGhosts() {
		return _ghosts;
	}

	public void setGhosts(ArrayList<Packman> ghosts) {
		if (!ghosts.isEmpty()) {
			_ghosts = ghosts;
		}
	}

	public ArrayList<Obstacle> getObstacles() {
		return _obstacles;
	}

	public void setObstacles(ArrayList<Obstacle> obstacles) {
		if (!obstacles.isEmpty()) {
			_obstacles = obstacles;
		}
	}

	/**
	 * Description: method returns ArrayList comprised of Packman objects.
	 * 
	 * @return PackmanSet
	 */
	public ArrayList<Packman> getPackmans() {
		return _packmans;
	}

	/**
	 * Description: method sets PackmanSet field with a given ArrayList.
	 * 
	 * @param packmanSet
	 */
	public void setPackmans(ArrayList<Packman> packmans) {
		if (!packmans.isEmpty()) {
			_packmans = packmans;
		}
	}

	/**
	 * Description: method returns ArrayList comprised of Fruit objects
	 * 
	 * @return FruitSet
	 */
	public ArrayList<Fruit> getFruits() {
		return _fruits;
	}

	/**
	 * Description: method sets FruitSet field with a given ArrayList.
	 * 
	 * @param fruitSet
	 */
	public void setFruits(ArrayList<Fruit> fruits) {
		if (!fruits.isEmpty()) {
			_fruits = fruits;
		}
	}

}
