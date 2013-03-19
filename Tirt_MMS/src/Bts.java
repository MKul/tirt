
/**
 * 
 * @author Marcin Kulisiewicz
 *
 */


public class Bts implements Comparable<Bts>{
	
	private final String id;
	private float x;
	private float y;
	private float range;
	private float performence;
	
	public Bts(String id, float posX, float posY){
		this.id=id;
		x=posX;
		y=posY;
	}
	
	public Bts(String id, float posX, float posY, float range){
		this.id=id;
		x=posX;
		y=posY;
		this.range=range;
	}
	
	public Bts(String id, float posX, float posY, float range, float perf){
		this.id=id;
		x=posX;
		y=posY;
		this.range=range;
		performence=perf;
	}
	
	public void setRange(float r){
		range=r;
	}
	
	public void setPerformance(float p){
		performence=p;
	}
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
	public float getRange(){
		return range;
	}
	
	public float getPerformance(){
		return performence;
	}
	
	public String getId(){
		return id;
	}
	
	public String toString(){
		return "[BTS Id:"+id+",x:"+x+",y:"+y+",r:"+range+",p:"+performence+"]";
	}

	@Override
	public int compareTo(Bts o) {
		return this.id.compareTo(o.id);
	}
}
