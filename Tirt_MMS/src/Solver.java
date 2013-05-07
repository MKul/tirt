import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;


/**
 * @author Everybody
 * 
 * This class is the base of system - control data flow. Algorithm class extend it to solve problem
 * with specific way.
 */

public abstract class Solver {
	
	protected ArrayList<User> users;
	protected ArrayList<Bts> btses;
	private Pharser pharser;
	private float penalty = 0;
	
	public Solver(){
		//TODO: anything?
	}
	
	public final void readUsers(){
		users=pharser.readUsers();
//		System.out.println("users:"+users.toString());
	}
	
	public final void readBtses(){
		btses=pharser.readBtses();
//		System.out.println("btses:"+btses.toString());
	}
	
	public final void setPharser(Pharser p){
		pharser=p;
	}
	
	public final void setPenalty(float penalty){
		this.penalty = penalty;
	}
	
	public final ArrayList<User> getUsers(){
		if(users != null && !users.isEmpty()){
			return users;
		}else{
			System.out.println("[No users!]");
			return null;
		}
		
	}
	
	public final ArrayList<Bts> getBtses(){
		if(btses != null && !btses.isEmpty()){
			return btses;
		}else{
			System.out.println("[No btses!]");
			return null;
		}
	}
	
	public final float getDistance(User u, Bts b){
		if(b==null || b.getId().equals("~UNALLOCATED")){
			System.out.println(penalty);
		return penalty;	// Dopuszczam sytuacjê, ¿e user nie jest w zasiêgu ¿adnego BTS'a, wtedy wskoczy tu null, wiêc zwracam 1000 jako swego rodzaju karê za nieobs³u¿enie usera
		
		}
		float dx=u.getX()-b.getX();
		float dy=u.getY()-b.getY();
		return (float) Math.sqrt(dx*dx+dy*dy);
	}
	
	public final float getTotalDistance(TreeMap<User, Bts> assignment)
	{
		if(assignment!=null){
			float dist=0;
			for(User u:assignment.keySet())
			{
				Bts b=assignment.get(u);
				dist+=getDistance(u, b);
			}
			return dist;	
		}else{
			return -1;
		}
	}
	
	public final void printPath(TreeMap<User,Bts> path){
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
	
	public final TreeMap<User,TreeMap<Bts,Float>> getDistanceMap(){
		TreeMap<User,TreeMap<Bts,Float>> ans=new TreeMap<User,TreeMap<Bts,Float>>();
		for(int i=0;i<users.size();i++){
			TreeMap<Bts,Float> temp=new TreeMap<Bts,Float>();
			for(int j=0;j<btses.size();j++){
				Bts bts=btses.get(j);
				float dist=getDistance(users.get(i), bts);
				if(dist<=bts.getRange()){
					temp.put(bts, dist);
				}
			}
			ans.put(users.get(i), temp);
			//System.out.println(users.get(i).toString()+"->"+temp.toString());
		}
//		System.out.println(ans.toString());
		return ans;
	}
	
	public abstract TreeMap<User,Bts> getSolve();
	
	
}
