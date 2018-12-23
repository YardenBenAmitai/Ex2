package GIS;

import Geom.Geom_element;
import Geom.Point3D;

public class Fruit implements GIS_element{
	
	private Game_data Data;
	private double Weight;
	
	/**
	 * Description:
	 * constructor
	 * 
	 * @param id
	 * @param location
	 * @param weight
	 */
	public Fruit(int id, Point3D location, double weight){
		Data=new Game_data(id, location, 'F');
		Weight=weight;
	}
	
	
	public Geom_element getGeom() {
		return Data.get_Orientation();
	}


	@Override
	public Game_data getData() {
		return Data;
	}
	
	public String toString(){
		return ("F: "+Data.getID()+", Location: "+Data.get_Orientation());
	}
	@Override
	public void translate(Point3D vec) {
		// TODO Auto-generated method stub
		
	}
	
	//////////////////getters and setters//////////
	
	/**
	 * Description:
	 * method returns the weight of a fruit
	 * @return weight
	 */
	public double getWeight() {
		return Weight;
	}
	
	/**
	 * Description:
	 * method sets a new weight to fruit according to given param.
	 * @param weight
	 */
	public void setWeight(double weight) {
		Weight = weight;
	}


	

}
