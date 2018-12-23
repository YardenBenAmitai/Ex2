package Algorithms;

import java.util.ArrayList;

import java.util.Iterator;

import GIS.Fruit;
import GIS.Game;
import GIS.Packman;

public class ShortestPathAlgo {
	
	private ArrayList<Path> PackmanPaths=null;
	
	public ShortestPathAlgo(Game g){
		//initiate the fields:
		PackmanPaths=new ArrayList<Path>();
		ArrayList<Fruit> FruitSet=new ArrayList<Fruit>();
		
		//going over Game object and creating an empty path for each packman.
		Iterator<Packman> it_p=g.PIterator();
		Packman p;
		Path initiate;
		while (it_p.hasNext()){
			p=it_p.next();
			initiate=new Path(p);
			
			PackmanPaths.add(initiate);
			
		}
		
		//running over Game Object and creating a deep copy for a fruit list
		Iterator<Fruit> it_f=g.getFruitSet().iterator();
		Fruit f;
		while(it_f.hasNext()){
			f=it_f.next();
			FruitSet.add(f);	
		}
		
		if(FruitSet.size()<21){
			SmartPathCalculator(FruitSet);
		}
		else{
			SimplePathCalculator(FruitSet);
		}
	}
	
	public void SmartPathCalculator(ArrayList<Fruit> FruitSet){
		//creating an ArrayList containing the closest fruit to each Packman accordingly, before adding them to their paths.
		ArrayList<Fruit> Closest=new ArrayList<Fruit>();
		
		while(!FruitSet.isEmpty()){
			Iterator<Path>it_path=PackmanPaths.iterator();
			Path p;
			while(it_path.hasNext()){
				p=it_path.next();
				Iterator<Fruit>it_fruit=FruitSet.iterator();
				Closest.add(new Fruit(-1, null, 0));
				Fruit f;
				
				while (it_fruit.hasNext()){
					f=it_fruit.next();
					
					double time1=p.DistanceSpeedTime(f.getData().get_Orientation());
					double time2=p.DistanceSpeedTime(Closest.get(Closest.size()-1).getData().get_Orientation());
					
					if((Closest.get(Closest.size()-1).getData().get_Orientation()==null) || (time1<time2)){
						
						if(Closest.indexOf(f) ==-1){
							Closest.remove(Closest.size()-1);
							Closest.add(f);
							
						}
						else{
							double runningtime1=PackmanPaths.get(Closest.indexOf(f)).getCurrTime()+PackmanPaths.get(Closest.indexOf(f)).DistanceSpeedTime(f.getData().get_Orientation());
							double runningtime2=p.getCurrTime()+p.DistanceSpeedTime(f.getData().get_Orientation());
							if(runningtime2<runningtime1){
								Closest.remove(Closest.size()-1);
								Closest.add(f);
								Closest.get(Closest.indexOf(f)).setWeight(0);
							}
						}
					}
				}
			}
			
			Iterator<Path> it_p1=PackmanPaths.iterator();
			Iterator<Fruit>it_f1=Closest.iterator();
			Path p1;
			Fruit f1;
			while(it_p1.hasNext() && it_f1.hasNext()){
				p1=it_p1.next();
				f1=it_f1.next();
				if(f1.getWeight()!=0){
					p1.addFruit(f1);
					FruitSet.remove(f1);
				}
			}
			Closest.clear();
		}
	}
	
	public void SimplePathCalculator(ArrayList<Fruit> FruitSet){
		while(!FruitSet.isEmpty()){
			
			Iterator<Path>it_path=PackmanPaths.iterator();
			Path p;
			while (it_path.hasNext()){
				p=it_path.next();
				Iterator<Fruit>it_fruit=FruitSet.iterator();
				Fruit f;
				Fruit closest=new Fruit(-1,null,0);
				while(it_fruit.hasNext()){
					f=it_fruit.next();
					if(closest.getData().get_Orientation()==null || p.DistanceSpeedTime(f.getData().get_Orientation())<p.DistanceSpeedTime(closest.getData().get_Orientation())){
						closest=f;
					}
				}
				if(closest.getData().get_Orientation()!=null){
					p.addFruit(closest);
				}
				FruitSet.remove(closest);
			}
		}
		
	}
	
	public String toString(){
		StringBuilder sb=new StringBuilder();
		sb.append("Packman Pathes");
		sb.append('\n');
		Iterator<Path>it=PackmanPaths.iterator();
		while(it.hasNext()){
			sb.append(it.next().toString());
			sb.append('\n');
		}
		return sb.toString();
	}
	
	
	//////////////////getters and setters////////////////////
	public ArrayList<Path> getPackmanPaths() {
		return PackmanPaths;
	}

	public void setPackmanPaths(ArrayList<Path> packmanPaths) {
		PackmanPaths = packmanPaths;
	}

}
