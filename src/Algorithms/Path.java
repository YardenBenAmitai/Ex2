package Algorithms;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Packman;
import Geom.Point3D;

/**
 * Description:
 * the class represents the path of a pacman.
 * the class's fields are a pacman object, a time variable which updates itself 
 * after every fruit the pacman eats according to its speed and the fruit's distance.
 * the class also contains arraylist of fruits in the order they were meant to be eaten.
 * 
 * @author Yarden and Caroline
 *
 */
public class Path {
	
	private Packman head;
	private ArrayList<Fruit> PathArray=null;
	private double currTime;
	
	/**
	 * Description:
	 * constructor which receives a a parameter the initial packman object, 
	 * creates a list which the packman location as the first object
	 * and sets the time to 0 as no fruits were eaten yet.
	 * @param StartingPoint
	 */
	public Path(Packman StartingPoint){
		
		head=new Packman(StartingPoint.getData().getID(), StartingPoint.getData().get_Orientation(), StartingPoint.getSpeed(), StartingPoint.getRadius());
		
		PathArray=new ArrayList<Fruit>();
		Fruit head=new Fruit(-1, StartingPoint.getData().get_Orientation(), 0);
		PathArray.add(head);
		setCurrTime(0);
		
	}
	
	/**
	 * Description:
	 * the method calculates the time it would take the packman to move 
	 * from given coordinate to the location of the last fruit on the list (also the last stop of the packman before advancing).
	 * @param p2
	 * @return the time in seconds altered by the packman's speed and the fruit's distance
	 */
	public double DistanceSpeedTime(Point3D p2){
		if (p2==null){
			return -1;
		}
		Point3D p1=PathArray.get(PathArray.size()-1).getData().get_Orientation();
		MyCoords c=new MyCoords();
		double distance=c.distance3d(p1,p2);
		return (distance/head.getSpeed())*60*60;
		
	}
	
	/**
	 * Description:
	 * the method returns the length of the list.
	 * @return the number of fruits that were eaten
	 */
	public int FruitsEaten(){
		return PathArray.size()-1;
	}
	
	/**
	 * Description:
	 * the method is given a fruit object.
	 * appends it to the end of the list and set the time according to the speed, time and fruit's location
	 * @param f
	 */
	public void addFruit(Fruit f){
		currTime+= this.DistanceSpeedTime(f.getData().get_Orientation());
		PathArray.add(f);
	}
	
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("Path: "+head.getData().getID());
		sb.append('\n');
		Iterator<Fruit> it=PathArray.iterator();
		while(it.hasNext()){
			sb.append("-> "+it.next().toString());
		}
		return sb.toString();
	}
	
	
	////////////////////getters and setters//////////////////////
	
	
	
	public Packman getHead() {
		return head;
	}
	
	public void setHeadLocation(Point3D p){
		head.getData().setLocation(p);
	}
	
	
	public ArrayList<Fruit> getPath(){
		return PathArray;
	}

	public double getCurrTime() {
		return currTime;
	}

	public void setCurrTime(double currTime) {
		this.currTime = currTime;
	}

}
