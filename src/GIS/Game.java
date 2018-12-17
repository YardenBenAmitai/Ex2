package GIS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import Geom.Point3D;


public class Game implements GIS_layer{
	private ArrayList<Packman> PackmanSet;
	private ArrayList<Fruit> FruitSet;
	private Game_data Data;
	
	
	
	/**
	 * Description:
	 * the constructor receives a csv filename as parameter, 
	 * reads from said file and construct a PackmenSet and FruitSet from it.
	 * @param csvFile
	 * @throws FileNotFoundException 
	 */
	public Game(String csvFile) throws FileNotFoundException{
		Data=new Game_data(0,null,'G');
		
		//creating two empty array-lists for later appending.
		ArrayList<Packman> packman_set=new ArrayList<Packman>();
		ArrayList<Fruit> fruit_set=new ArrayList<Fruit>();
		
		BufferedReader csvReader;
		csvReader = new BufferedReader(new FileReader(csvFile));
		String[] fields=null;
		String currLine;
		
		try{
			//reading from csvFile, second line.
			currLine=csvReader.readLine();
			
			while((currLine=csvReader.readLine())!=null){
				fields=currLine.split(",");
				Point3D loc=new Point3D(Double.parseDouble(fields[2]),Double.parseDouble(fields[3]), Double.parseDouble(fields[4]));
				
				if((fields[0]=="p")||(fields[0]=="P")){
					Packman p=new Packman(Integer.parseInt(fields[1]), loc, Double.parseDouble(fields[5]), Double.parseDouble(fields[6]));
					packman_set.add(p);
				}
				else{
					Fruit f=new Fruit(Integer.parseInt(fields[1]), loc, Double.parseDouble(fields[5]));
					fruit_set.add(f);
				}
			}
			
			PackmanSet=packman_set;
			FruitSet=fruit_set;
			csvReader.close();
			
		}catch (IOException e){
			
		}
		
	}
	
	/**
	 * Description:
	 * empty constructor
	 */
	public Game(){
		PackmanSet=null;
		FruitSet=null;
		Data=new Game_data(0,null,'G');
	}
	
	
	/**
	 * Description:
	 * the method converts object Game into csv file at the given parameter.
	 * @param csvFile
	 */
	public void GameToCSV(String csvFile){
		
		
		
	}
	
	
	public Meta_data get_Meta_data() {
		return Data;
	}
	
	
	///////////////getters and setters///////////////
	/**
	 * Description:
	 * method returns ArrayList comprised of Packman objects.
	 * @return PackmanSet
	 */
	public ArrayList<Packman> getPackmanSet() {
		return PackmanSet;
	}
	
	/**
	 * Description:
	 * method sets PackmanSet field with a given ArrayList.
	 * @param packmanSet
	 */
	public void setPackmanSet(ArrayList<Packman> packmanSet) {
		PackmanSet = packmanSet;
	}
	/**
	 * Description:
	 * method returns ArrayList comprised of Fruit objects
	 * @return FruitSet
	 */
	public ArrayList<Fruit> getFruitSet() {
		return FruitSet;
	}
	
	/**
	 * Description:
	 * method sets FruitSet field with a given ArrayList.
	 * @param fruitSet
	 */
	public void setFruitSet(ArrayList<Fruit> fruitSet) {
		FruitSet = fruitSet;
	}

	@Override
	public boolean add(GIS_element e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends GIS_element> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<GIS_element> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
