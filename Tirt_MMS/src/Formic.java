import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * @author Marcin Kulisiewicz
 *
 */

public class Formic extends Solver{
	
	private HashMap<User,HashMap<Bts,Integer>> distanceMap;
	private HashMap<User,HashMap<Bts,HashMap<Bts,Integer>>> pheromoneMap;
	
	public Formic(){
		
	}
	
	
	//method contain algorithm
	
	private void compute(int antN, int iterationN, int userN){
		
		prepareData();
		
		for(int i=0;i<iterationN;i++){
			//find path
		}
	
	}
	
	private void prepareData(){
		//load possible ant path
		distanceMap=getDistanceMap();
		
		//create pheromoneMap and fill by default 1
		createPheromoneMap();
	}
	
	private void createPheromoneMap() {
		pheromoneMap=new HashMap<User,HashMap<Bts,HashMap<Bts,Integer>>>();
		User nest=new User(0,0);
		for(User u:distanceMap.keySet()){
			
		}
	}


	private float countCriterion(float dist){
		
		
		return 0;
	}
	
	
	@Override
	public HashMap<User, Bts> getSolve() {
		// TODO Auto-generated method stub
		return null;
	}

}
