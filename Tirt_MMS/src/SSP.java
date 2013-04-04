import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;


public class SSP extends Solver {
	
	private Pharser parser;


	public SSP() {
		parser=new Pharser();
		setPharser(parser);
		readBtses();
		readUsers();
	}
	
	public TreeMap<User,Bts> findPath(TreeMap<User,TreeMap<Bts,Float>> distanceMap) // Znajduje najkrotsza sciezke dla danej distanceMap- bez wiekszej filozofii, bo zawsze najkrotsza bedzie ta, gdzie dla kolejnych userow bedziemy wybierac najblizszy BTS
	{
		TreeMap<User, Bts> path= new TreeMap<User, Bts>();
		for(User u:distanceMap.keySet())
		{
			Bts b=selectNearestBTS(u,distanceMap);
			path.put(u,b);
		}
		return path;
	}
	
	public Bts selectNearestBTS(User u, TreeMap<User,TreeMap<Bts,Float>> distanceMap) // Wybiera najblizszy BTS dla danego usera (z tych, w ktorych zasiegu miesci sie user), jesli brak takiego to zwraca null
	{
		TreeMap<Bts, Float> btses=distanceMap.get(u);
		Bts best=null;
		float bestDist=Integer.MAX_VALUE;
		for(Bts b:btses.keySet())
		{
			if(btses.get(b)<bestDist)
			{
				best=b;
				bestDist=btses.get(b);
			}
		}
		return best;
	}
	
	public boolean isOverloaded(TreeMap<User, Bts> assignment) // Sprawdza, czy wybrane rozwiazanie nie przeciaza zadnego BTS'a. Troche zakombinowane, ze wzgledu na plytki sposob klonowania TreeMap przez funkcje clone()
	{
		TreeMap<String,Float> performance=new TreeMap<String,Float>();
		ArrayList<Bts> btses=getBtses();
		for(Bts b:btses)
		{
			performance.put(b.getId(), b.getPerformance());
		}
		
		for(User u:assignment.keySet())
		{
			if(assignment.get(u)!=null)
			{
				String id=assignment.get(u).getId();
				float perf=performance.remove(id);
				if(perf-1<0) return true;
				performance.put(id,perf-1);
			}
		}
		return false;
	}	
	
	@Override
	public TreeMap<User, Bts> getSolve() { //Szuka najlepszego rozwiazania
		LinkedList< TreeMap<User, Bts> > paths=new LinkedList< TreeMap<User, Bts> >();
		LinkedList< TreeMap<User, TreeMap<Bts, Float> > > maps=new LinkedList< TreeMap<User, TreeMap<Bts, Float> > >();
		
		paths.add( findPath( getDistanceMap() ) );
		maps.add((TreeMap<User, TreeMap<Bts, Float>>) deepClone(getDistanceMap()));
		
		while( isOverloaded( paths.peekFirst() ) )
		{
			paths.pop();
			TreeMap<User, TreeMap<Bts, Float> > tempMap=maps.pop();
			for(User u:tempMap.keySet())
			{
				TreeMap<User, TreeMap<Bts, Float>> newTempMap=deepClone(tempMap);
				Bts bestBts=selectNearestBTS(u, newTempMap);
				if(bestBts!=null)
				{
					newTempMap.get(u).remove(bestBts);
					paths.add(findPath(newTempMap));
					maps.add(newTempMap);
				}
			}
		}
		// Po wyjsciu z petli posiadam juz pierwsze rozwiazanie, ktore nie przeciaza BTS-ow, ale niekoniecznie optymalne
		TreeMap<User, Bts> bestPath=paths.pop();
		float bestDist=getTotalDistance(bestPath);
		while(!paths.isEmpty())
		{
			TreeMap<User, Bts> tempPath=paths.pop();
			if(!isOverloaded(tempPath) && getTotalDistance(tempPath)<bestDist)
			{
				bestPath=tempPath;
				bestDist=getTotalDistance(tempPath);
			}
		}
		return bestPath;
	}
	
	public TreeMap<User, TreeMap<Bts, Float>> deepClone(TreeMap<User, TreeMap<Bts, Float>> map) // Potrzebne ze wzgledu na to, ze zwykly clone() dzialal nie tak jak oczekiwalem, gdy byla zagniezdzona TreeMapa
	{
		TreeMap<User, TreeMap<Bts, Float>> newMap= new TreeMap<User, TreeMap<Bts, Float>>();
		for(User u:map.keySet())
		{
			TreeMap<Bts, Float> tm=map.get(u);
			newMap.put(u,(TreeMap<Bts, Float>)tm.clone());
		}
		return newMap;
	}

}
