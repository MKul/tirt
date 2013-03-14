import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author Everybody
 * 
 * This class is the base of system - control data flow. Algorithm class extend it to solve problem
 * with specific way.
 */

public abstract class Solver {
	
	protected ArrayList<User> users;
	protected ArrayList<Bts> btses;
	
	public Solver(){
		//TODO: anything?
	}
	
	public final void readUsers(String path){
		//TODO: use Parser, path from GUI
	}
	
	public final void readBtses(String path){
		//TODO: use Parser, path from GUI
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
	
	public abstract HashMap<User,Bts> getSolve();
	
	
}
