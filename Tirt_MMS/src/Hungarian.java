import java.util.ArrayList;

/**
 * 
 * @author Sonia Kot
 * 
 * TO DO: To powstanie niebawem implementacja algorytmu wêgierskiego
 *
 */
public class Hungarian extends Pharser {
	ArrayList<Bts> btsList = new ArrayList<Bts>();
	ArrayList<User> userList = new ArrayList<User>();
	int userSize = 0;

	public Hungarian(String btses, String users) {
		super(btses, users);
		btsList = readBtses();
		userList = readUsers();
		userSize = userList.size();
	}

	public void solve() {
		int[][] tab = new int[userSize][btsSize];
		tab = createMatrix();
		
		tab = subtractMinFromRow(tab);
		
		tab = subtractMinFromColumn(tab);
		
		System.out.println();

		for(int i=0;i<tab.length;i++){
			for(int j=0;j<tab[0].length;j++){
				System.out.print(tab[i][j]+" ");
			}
			System.out.println();
		}
		
		
	}
	
	int btsSize = 0;
	private int[][] createMatrix(){
		for(Bts b: btsList){
			btsSize += b.getPerformance();
		}
		int[][] tab = new int[userSize][btsSize];
		for(int i=0; i<tab.length; i++){
			int j=0;
			for(Bts b: btsList){
				int temp = (int) b.getPerformance();
				for(int k=0;k<temp; k++){
					tab[i][k+j] = (int)countDistance(b, userList.get(i));
				}
				j += temp;
			}
		}
		
		return tab;
	}

	private int[][] subtractMinFromRow(int[][] matrix){
		int[][] m2 = new int[userSize][btsSize];
		for(int i=0; i<matrix.length; i++){
			int min = 0;
			for(int j=0; j<matrix[0].length; j++){
				if(j==0) min = matrix[i][j];
				else if(min > matrix[i][j]) min = matrix[i][j];
			}
			
			for(int j=0; j<matrix[0].length; j++){
				m2[i][j] = matrix[i][j] - min;
			}
		}
		return m2;
			
	}
	
	private int[][] subtractMinFromColumn(int[][] matrix){
		int[][] m2 = new int[userSize][btsSize];
		for(int i=0; i<matrix[0].length; i++){
			int min = 0;
			for(int j=0; j<matrix.length; j++){
				if(j==0) min = matrix[j][i];
				else if(min > matrix[j][i]) min = matrix[j][i];
			}
			
			for(int j=0; j<matrix.length; j++){
				m2[j][i] = matrix[j][i] - min;
			}
		}
		return m2;
			
	}
	
	private float countDistance(Bts b, User u) {
		float dx = u.getX() - b.getX();
		float dy = u.getY() - b.getY();
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
}
