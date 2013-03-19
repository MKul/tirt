
/**
 * 
 * @author Marcin Kulisiewicz
 *
 */

public class User implements Comparable<User>{
	
	private final String id;
	private float x;
	private float y;
	
	public User(String id, float posX, float posY){
		this.id=id;
		x=posX;
		y=posY;
	}
	
	public void setX(float newX){
		x=newX;
	}
	
	public void setY(float newY){
		y=newY;
	}
	
	public float getX(){
		return x;
	}
		
	public float getY(){
		return y;
	}
	
	public String getId(){
		return id;
	}
	
	public String toString(){
		return "[USER id:"+id+",x:"+x+",y:"+y+"]";
	}

	@Override
	public int compareTo(User o) {
		return this.id.compareTo(o.id);
	}
	
	
}
