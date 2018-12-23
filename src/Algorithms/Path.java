package Algorithms;

import java.util.ArrayList;
import java.util.Iterator;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Packman;
import Geom.Point3D;

public class Path {
	private int ID;
	private double Speed;
	private double Radius;
	private ArrayList<Fruit> PathArray=null;
	private double currTime;
	
	
	public Path(Packman StartingPoint){
		
		ID=StartingPoint.getData().getID();
		Speed=StartingPoint.getSpeed();
		Radius=StartingPoint.getRadius();
		PathArray=new ArrayList<Fruit>();
		Fruit head=new Fruit(-1, StartingPoint.getData().get_Orientation(), StartingPoint.getRadius());
		PathArray.add(head);
		setCurrTime(0);
		
	}
	
	
	public double DistanceSpeedTime(Point3D p2){
		if (p2==null){
			return -1;
		}
		Point3D p1=PathArray.get(PathArray.size()-1).getData().get_Orientation();
		MyCoords c=new MyCoords();
		double distance=c.distance3d(p1,p2);
		return (distance/Speed)*60*60;
		
	}
	
	public int FruitsEaten(){
		return PathArray.size()-1;
	}
	
	public boolean addFruit(Fruit f){
		currTime+= this.DistanceSpeedTime(f.getData().get_Orientation());
		PathArray.add(f);
		return true;
		
	}
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("Path: "+ID);
		sb.append('\n');
		Iterator<Fruit> it=PathArray.iterator();
		while(it.hasNext()){
			sb.append("-> "+it.next().toString());
		}
		return sb.toString();
	}
	
	
	////////////////////getters and setters//////////////////////
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public double getRadius() {
		return Radius;
	}

	public void setRadius(double radius) {
		Radius = radius;
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
