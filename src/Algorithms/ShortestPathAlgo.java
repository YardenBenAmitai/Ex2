package Algorithms;

import java.util.ArrayList;
import java.util.Iterator;

import GIS.Fruit;
import GIS.Game;
import GIS.Packman;
import Geom.Point3D;

public class ShortestPathAlgo {
	
	private ArrayList<Path> PackmanPaths=null;
	private ArrayList<Fruit> FruitSet=null;
	
	public ShortestPathAlgo(Game g){
		
		Iterator<Packman> it_p=g.getPackmanSet().iterator();
		Packman p;
		Path initiate;
		while (it_p.hasNext()){
			p=it_p.next();
			initiate=new Path(p.getData().getID(),p.getData().get_Orientation(),p.getSpeed(),p.getRadius());
			PackmanPaths.add(initiate);
		}
		
		Iterator<Fruit> it_f=g.getFruitSet().iterator();
		Fruit f;
		while(it_f.hasNext()){
			f=it_f.next();
			FruitSet.add(f);
		}
		
		//the point holds the id of a packman, the id of the fruit closest to it and the time it takes the Packman to get to the fruit.
		//when a new packman finds a closer fruit it updates.
		Point3D closest=new Point3D(0,0,100);
		
		
		Path route;
		double time;
		
		while(!FruitSet.isEmpty()){
			Iterator<Path> it_path=PackmanPaths.iterator();
			
			while(it_path.hasNext()){
				route=it_path.next();
				it_f=FruitSet.iterator();
				
				while(it_f.hasNext()){
					f=it_f.next();
					time=route.ShortestTime(f.getData().get_Orientation());
					if (closest.iz()>time){
						closest=new Point3D(route.getID(),f.getData().getID(),time);
					}
					
				}
			}
			
			Iterator<Path> route_finder=PackmanPaths.iterator();
			route=null;
			while(route_finder.hasNext()){
				route=route_finder.next();
				if(closest.ix()==route.getID()){
					break;
				}
			}
			
			Iterator<Fruit> fruit_finder=FruitSet.iterator();
			while(fruit_finder.hasNext()){
				f=fruit_finder.next();
				if(closest.iy()==f.getData().getID()){
					route.getPath().add(f.getData().get_Orientation());
					fruit_finder.remove();
					break;
				}
			}
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	//////////////////getters and setters////////////////////
	public ArrayList<Path> getPackmanPaths() {
		return PackmanPaths;
	}

	public void setPackmanPaths(ArrayList<Path> packmanPaths) {
		PackmanPaths = packmanPaths;
	}

	public ArrayList<Fruit> getFruitSet() {
		return FruitSet;
	}

	public void setFruitSet(ArrayList<Fruit> fruitSet) {
		FruitSet = fruitSet;
	}
}
