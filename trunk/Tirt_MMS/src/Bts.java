
//author: Marcin Kulisiewicz

public class Bts {
	
	private int x;
	private int y;
	private int range;
	private int performence;
	
	public Bts(int posX, int posY){
		x=posX;
		y=posY;
	}
	
	public Bts(int posX, int posY, int range){
		x=posX;
		y=posY;
		this.range=range;
	}
	
	public Bts(int posX, int posY, int range, int perf){
		x=posX;
		y=posY;
		this.range=range;
		perf=performence;
	}
	
	public void setRange(int r){
		range=r;
	}
	
	public void setPerformance(int p){
		performence=p;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getRange(){
		return range;
	}
	
	public int getPerformance(){
		return performence;
	}
}
