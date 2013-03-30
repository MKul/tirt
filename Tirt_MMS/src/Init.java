
public class Init {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long time=System.currentTimeMillis();
		Pharser phaser=new Pharser();
		Formic formic=new Formic();
		formic.setPharser(phaser);
		formic.readBtses();
		formic.readUsers();
		formic.compute(2,40,5);
		System.out.println("Formic:");
		System.out.println(formic.getSolve());
		System.out.println(formic.totalDistance(formic.getSolve()));
		System.out.println("Time: "+(System.currentTimeMillis()-time)+"ms");
		
		time=System.currentTimeMillis();
		SSP ssp=new SSP();
		System.out.println("SSP:");
		System.out.println(ssp.getSolve());
		System.out.println(ssp.totalDistance(ssp.getSolve()));
		System.out.println("Time: "+(System.currentTimeMillis()-time)+"ms");
	}

}
