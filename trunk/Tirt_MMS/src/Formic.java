import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;


/**
 * 
 * @author Marcin Kulisiewicz
 *
 */

public class Formic extends Solver{
	
	private int userN;
	private TreeMap<User,Bts> lastPath;
	private TreeMap<User,TreeMap<Bts,Float>> distanceMap;
	private TreeMap<User,TreeMap<Bts,TreeMap<Bts,Float>>> pheromoneMap;
	private User queen;
	private Bts nest, unallocated;
	private final float ALPHA=1f, BETA=1f, PHEROMONE_DECAY=5f, PHER_STAT=1000f;	
												//wspó³czynniki: ALPHA- wp³yw feromonów, 
												//BETA- wp³yw funkcji kryterium,
												//PHEROMONE_DECAY- zanikanie feromonów
												//PHER_STAT- sta³a przy obliczaniu feromonów
	
	public Formic(){
		queen=new User(" Queen",0,0);
		nest=new Bts("Nest",0,0,0,0);
		unallocated=new Bts("~UNALLOCATED",0,0,0,Float.MAX_VALUE);
	}
	
	/**
	 * 
	 * @param antN
	 * @param iterationN
	 * 
	 * Metoda zawieraj¹ca przebieg algorytmu pobiera w parametrach informacjê o 
	 * iloœci iteracji jak¹ chcemy wykonaæ (iterationN). Parametr antN równie¿ mówi o iloœci iteracji jednak w
	 * trochê inny sposób. Algorytm mrówkowy ma kilka wersji. Zaimplementowana zosta³a wersja, gdzie mrówki
	 * przechodz¹c po grafie w kolejnych iteracjach nie zostawiaj¹ feromonów od razu. Feromony na œcie¿kach grafu
	 * pojawiaj¹ siê dopiero po przejœciu wszystkich mrówek w danej iteracji.
	 * 
	 * Wiêcej nie t³umaczê - by³o trudno napisaæ, ma byæ trudno zrozumieæ
	 *  
	 */
	//---------------------  method contain algorithm  ---------------------
	public void compute(int antN, int iterationN){
		prepareData();
//		System.out.println("START: antN-"+antN+" iterN-"+iterationN+" userN-"+userN);
		Bts currBts;
		User currUser;
		TreeMap<User,Bts> currPath;
		TreeMap<Bts,Float> performances;
		ArrayList<TreeMap<User,Bts>> paths;
		boolean isOutOfRange=false;
		for(int i=0;i<iterationN;i++){
//			System.out.println();
//			System.out.println(" i:"+i);
			boolean isBreaked=false;
			paths=new ArrayList<TreeMap<User,Bts>>();
			for(int iAnt=0;iAnt<antN;iAnt++){
//				System.out.print(" a:"+iAnt);
				performances = getPerformences();
				currPath=new TreeMap<User, Bts>();
				currBts=nest;
				currUser=queen;
				currPath.put(currUser, currBts);
				User last=distanceMap.lastKey();
				for(User u:distanceMap.keySet()){
					Set<Bts> btsSet=distanceMap.get(u).keySet();
					int numberOfPosibilities=btsSet.size();
					Bts[] btsList=new Bts[numberOfPosibilities];
					float[] probabilities=new float[numberOfPosibilities];
					if(numberOfPosibilities>0){
						float critSum=0;
						int iter=0;
						for(Bts b:btsSet){
							if(numberOfPosibilities==1){	//only one way possibly
								//set probability at 1 only if performance allowed
								probabilities[0] = performances.get(b)<=0 ? 0 : 1;	 
								critSum= probabilities[0];
								btsList[0]=b;
							}else{
								btsList[iter]=b;
								if(performances.get(b)==0){
									probabilities[iter]=0;
								}else{
									float pheromone = pheromoneMap.get(currUser).get(currBts).get(b);
									float criter = 1/(distanceMap.get(u).get(b));
									probabilities[iter]=setImpact(pheromone,ALPHA)*setImpact(criter,BETA);
								}
								critSum+=probabilities[iter];
								iter++;
							}
						}
						for(int j=0;j<probabilities.length;j++){
							probabilities[j]/=critSum;
						}
					}else{
						System.out.println("Out of Range");
						isOutOfRange=true;
					}
					
					if(!isOutOfRange){
						int choosed=randomWithProbability(probabilities);
						if(choosed==-1){
							isBreaked=true;
							break;
						}else{
							currBts=btsList[choosed];
							//decrease performance of current bts 
							if(performances.get(currBts)<=0){
								isBreaked=true;
								break;
							}
							performances.put(currBts,performances.get(currBts)-1);
							currUser=u;
							currPath.put(currUser, currBts);
						}
					}else{
						isOutOfRange=false;
						currBts=unallocated;
						currUser=u;
						currPath.put(currUser, currBts);
					}
				}
				if(!isBreaked){
					paths.add(clonePath(currPath));
					lastPath=clonePath(currPath);
//					printPath(currPath);
//					printPerformences(performances);
				}else{
					//do nothing because path didn't found
				}
				currPath.clear();
			}
			computePheromone(paths);
		}
//		if(lastPath!=null) printPath(lastPath);
	}//---------------------------------------------------------------------
	
	private void printPerformences(TreeMap<Bts,Float> perf){
		for(Bts b: perf.keySet()){
			System.out.print(perf.get(b)+" ");
		}
		System.out.println();
	}
	
	public void printPath(TreeMap<User,Bts> path){
		for(User u:path.keySet()){
			if(path.get(u)== null){
				System.out.print(u.getId()+"->~UNALLOCATED  ");
			}else{
				if(!u.getId().equals(" Queen"))
				System.out.print(u.getId()+"->"+path.get(u).getId()+"  ");
			}
		}
		System.out.println();
	}
	
	public TreeMap<User,Bts> clonePath(TreeMap<User,Bts> path){
		TreeMap<User,Bts> clone=new TreeMap<User,Bts>();
		for(User u:path.keySet()){
			clone.put(u, path.get(u));
		}
		return clone;
	}
	
	private TreeMap<Bts,Float> getPerformences(){
		TreeMap<Bts,Float> performences=new TreeMap<Bts,Float>();
		for(Bts b:btses){
			performences.put(b, b.getPerformance());
		}
		return performences;
	}
	
	private void computePheromone(ArrayList<TreeMap<User,Bts>> paths){
		
		//parowanie starych
		for(User u:pheromoneMap.keySet()){
			for(Bts b_source:pheromoneMap.get(u).keySet()){
				for(Bts b_leaf:pheromoneMap.get(u).get(b_source).keySet()){
					float newPherValue=pheromoneMap.get(u).get(b_source).get(b_leaf);
					if(newPherValue-PHEROMONE_DECAY>1)newPherValue-=PHEROMONE_DECAY;
					else newPherValue=1f;
					pheromoneMap.get(u).get(b_source).put(b_leaf, newPherValue);
				}
			}
		}
		
		
		//pozostawienie nowych
		for(int i=0;i<paths.size();i++){
			TreeMap<User,Bts> path=paths.get(i);
			User currUser=queen;
			Bts currBts=nest;
			float totalDist=0f;		//summary distance of all path
			for(User u:path.keySet()){	
				if(!u.equals(queen)){
					totalDist+=distanceMap.get(u).get(path.get(u));
				}
			}
			float newPher=PHER_STAT/totalDist;
			currUser=queen;
			currBts=nest;
			for(User u:path.keySet()){
				if(!u.equals(queen)){
					Bts b=path.get(u);
					float actualPher=pheromoneMap.get(currUser).get(currBts).get(b);
					pheromoneMap.get(currUser).get(currBts).put(b, actualPher+newPher);
					currUser=u;
					currBts=b;
				}
			}
			
//			System.out.println(pheromoneMap.toString());
			
		}
		
		
	}
	
	private float setImpact(float x, float impact){
		return (float) Math.pow(x, impact);
	}
	
	private int randomWithProbability(float[] probabilities){
		
		Random rand=new Random();
		float value=rand.nextFloat();
		int i=0;
		int size=probabilities.length;
		float sum=probabilities[0];
		boolean isAny= (sum==0) ? false : true;
		while(value>sum && (i+1)<size){
			if(probabilities[i+1]!=0)isAny=true;
			sum+=probabilities[i+1];
			i++;
		}
		return isAny ? i : -1;
	}
	
	private void prepareData(){
		//add to unallocated bts
		btses.add(unallocated);
		
		//load possible ant path
		distanceMap=getDistanceMap();
		
		//TODO: set max distance to all connection with unallocated bts
		setMaxDistToUnallocated();
		
		//create pheromoneMap and fill by default 1
		createPheromoneMap();
		
		userN=users.size();
	}
	
	private void setMaxDistToUnallocated(){
		for(User u:distanceMap.keySet()){
			distanceMap.get(u).put(unallocated, Float.MAX_VALUE);
		}
	}
	
	private void createPheromoneMap() {
		pheromoneMap=new TreeMap<User,TreeMap<Bts,TreeMap<Bts,Float>>>();
		boolean first=true;
		User currUser=queen;
		TreeMap temp;
		TreeMap temp2;
		
		for(User u:distanceMap.keySet()){
			temp=new TreeMap<Bts,TreeMap<Bts,Float>>();
			temp2=new TreeMap<Bts,Float>();
			if(first){	//	in the first iteration insert b_leaf to destination set of nest
				for(Bts b_leaf:distanceMap.get(u).keySet()){
					temp2.put(b_leaf, 1f);
				}
				temp.put(nest, temp2);
				first=false;
			}else{		// next iteration insert b_leaf to destination sets of b_leaf from previous iteration, now - b_source
				for(Bts b_source:distanceMap.get(currUser).keySet()){
					for(Bts b_leaf:distanceMap.get(u).keySet()){
						temp2.put(b_leaf, 1f);
					}
					temp.put(b_source, temp2.clone());
				}
			}
			pheromoneMap.put(currUser, (TreeMap<Bts, TreeMap<Bts, Float>>) temp.clone());
			
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
//		printPath(lastPath);
		return lastPath;
	}

}
