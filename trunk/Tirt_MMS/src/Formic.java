import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeMap;


/**
 * 
 * @author Marcin Kulisiewicz
 *
 */

public class Formic extends Solver{
	
	private TreeMap<User,TreeMap<Bts,Float>> distanceMap;
	private TreeMap<User,TreeMap<Bts,TreeMap<Bts,Float>>> pheromoneMap;
	private User queen;
	private Bts nest;
	
	public Formic(){
		queen=new User(" Queen",0,0);
		nest=new Bts("Nest",0,0,0,0);
	}
	
	
	//---------------------  method contain algorithm  ---------------------
	public void compute(int antN, int iterationN, int userN){
		
		prepareData();
		//TODO:
		Bts currBts;
		User currUser;
		TreeMap<User,Bts> currPath=new TreeMap<User, Bts>();
		for(int i=0;i<iterationN;i++){
			currBts=nest;
			currUser=queen;
			currPath.put(currUser, currBts);
			for(User u:distanceMap.keySet()){
				
				for(Bts b:distanceMap.get(u).keySet()){
					//TODO:oblicz prawdopodobieñstwo wyboru stacji b
				}
				//TODO:wylosuj krok wg ustalonego prawdopodobieñstwa
				
				currBts=null;
				
				//TODO:zmniejsz wydajnoœæ wybranej stacji
				
				//TODO:sprawdŸ gdzie jesteœ
				
				
				currUser=u;
			}
			
			//TODO: obliczanie feromonów
			
			currPath.clear();
		}
	
	}//---------------------------------------------------------------------
	
	private void prepareData(){
		//load possible ant path
		distanceMap=getDistanceMap();
		
		//create pheromoneMap and fill by default 1
		createPheromoneMap();
	}
	
	private void createPheromoneMap() {
		pheromoneMap=new TreeMap<User,TreeMap<Bts,TreeMap<Bts,Float>>>();
		boolean first=true;
		User currUser=queen;
		TreeMap temp=new TreeMap<Bts,TreeMap<Bts,Float>>();
		TreeMap temp2=new TreeMap<Bts,Float>();
		
		pheromoneMap.put(currUser, temp);
		for(User u:distanceMap.keySet()){
			if(first){	//	in the first iteration insert b_leaf to destination set of nest 
				for(Bts b_leaf:distanceMap.get(u).keySet()){
					temp2.put(b_leaf, 1);
				}
				temp.put(nest, temp2);
				first=false;
				
				
			}else{		// next iteration insert b_leaf to destination sets of b_leaf from previous iteration, now - b_source
				for(Bts b_source:distanceMap.get(currUser).keySet()){
					for(Bts b_leaf:distanceMap.get(u).keySet()){
						temp2.put(b_leaf, 1);
					}
					temp.put(b_source, temp2.clone());
				}
			}
			pheromoneMap.put(currUser, (TreeMap<Bts, TreeMap<Bts, Float>>) temp.clone());
			temp.clear();
			temp2.clear();
			currUser=u;
		}
//		System.out.println("PHEROMONES");
//		System.out.println(pheromoneMap.toString());
	}


	private float countCriterion(float dist){
		return 1/dist;
	}
	
	
	@Override
	public TreeMap<User, Bts> getSolve() {
		// TODO Auto-generated method stub
		return null;
	}

}
