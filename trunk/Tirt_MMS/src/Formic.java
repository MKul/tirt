import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * @author Marcin Kulisiewicz
 *
 */

public class Formic extends Solver{
	
	private HashMap<User,HashMap<Bts,Integer>> distanceMap;
	
	public Formic(){
		
	}
	
	
	//method contain algorithm
	
	private void compute(int antN, int iterationN, int userN){
		//load possible path
		distanceMap=getDistanceMap();
		
		for(int i=0;i<iterationN;i++){
			//find path
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
