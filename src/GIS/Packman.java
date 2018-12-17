package GIS;

import Geom.Geom_element;
import Geom.Point3D;

public class Packman implements GIS_element{
	
	private Game_data Data;
	private double Speed;
	private double Radius;
	
	/**
	 * Description:
	 * constructor with Point3D
	 * @param id
	 * @param location
	 * @param speed
	 * @param radius
	 */
	public Packman(int id, Point3D location, double speed, double radius){
		Data=new Game_data(id, location, 'P');
		Speed=speed;
		Radius=radius;
		
	}
	
	
	public Geom_element getGeom() {
		return Data.get_Orientation();
	}


	public Meta_data getData() {
		return Data;
	}


	@Override
	public void translate(Point3D vec) {
		// TODO Auto-generated method stub
		
	}
	
	/////////////getters and setters////////////

	public double getSpeed() {
		return Speed;
	}

	public void setSpeed(double speed) {
		Speed = speed;
	}

	public double getRadius() {
		return Radius;
	}

	public void setRadius(double radius) {
		Radius = radius;
	}


	
	
	

}
