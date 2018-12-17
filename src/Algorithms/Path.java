package Algorithms;

import java.util.ArrayList;
import Geom.Point3D;

public class Path {
	private double Speed;
	private double Radius;
	private ArrayList<Point3D> PathArray=null;
	private int ID;
	
	public Path(int id, Point3D StartingPoint, double speed, double radius){
		setID(id);
		Speed=speed;
		setRadius(radius);
		PathArray.add(StartingPoint);
	}
	
	public double ShortestTime(Point3D p2){
		Point3D p1=PathArray.get(PathArray.size()-1);
		double distance=Math.sqrt(Math.pow(p2.ix()-p1.ix(),2) + Math.pow(p2.iy()-p1.iy(), 2) + Math.pow(p2.iz()-p1.iz(), 2));
		double time= (distance/Speed)*60*60;
		return time;
		
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
	
	public ArrayList<Point3D> getPath(){
		return PathArray;
	}

}
