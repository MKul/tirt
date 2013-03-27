
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
		System.out.println("Time: "+(System.currentTimeMillis()-time)+"ms");
	}

}
