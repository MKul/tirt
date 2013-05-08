import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 
 * @author Sonia Kot
 * 
 *         TO DO: To powstanie niebawem implementacja algorytmu wêgierskiego
 * 
 */
public class Hungarian extends Pharser {
	ArrayList<Bts> btsList = new ArrayList<Bts>();
	ArrayList<User> userList = new ArrayList<User>();
	ArrayList<MatrixPoint> matrixPoint = new ArrayList<MatrixPoint>();
	int userSize = 0, btsSize = 0;

	public Hungarian(String btses, String users) {
		super(btses, users);
		btsList = readBtses();
		userList = readUsers();
		userSize = userList.size();
		btsSize = btsList.size();
	}

	public void compute() {
		int[][] matrix = null;
		int tryb = 0;
//		btsSize = 5;
//		userSize = 4;
		if (btsSize > userSize) {
			tryb = 1;
			matrix = new int[btsSize][btsSize];
			// zapelniamy macierz wartoœci¹ -1 - wtedy jesli zostan¹ jakieœ
			// wart. to bêdziemy mogli je wszystkie zamieniæ na najwiêksz¹ wart.
			// macierzy
			for (int i = 0; i < btsSize; i++)
				for (int j = 0; j < btsSize; j++)
					matrix[i][j] = -1;
		}else{
			tryb = 2;
			matrix = new int[userSize][userSize];
			for (int i = 0; i < userSize; i++)
				for (int j = 0; j < userSize; j++)
					matrix[i][j] = -1;
		}

			int max = 0;
			// zape³niami macierz odlegloœciami btsów od userów
			for (int i = 0; i < btsSize; i++)
				for (int j = 0; j < userSize; j++) {
					int distance = (int) countDistance(btsList.get(i),
							userList.get(j));
					matrix[i][j] = distance;
					if (distance > max)
						max = distance;
				}

			/* przyk³ad
			int max = 19;
			
			matrix[0][0] = 10;
			matrix[0][1] = 19;
			matrix[0][2] = 8;
			matrix[0][3] = 15;

			matrix[1][0] = 10;
			matrix[1][1] = 18;
			matrix[1][2] = 7;
			matrix[1][3] = 17;
			
			matrix[2][0] = 13;
			matrix[2][1] = 16;
			matrix[2][2] = 9;
			matrix[2][3] = 14;
			
			matrix[3][0] = 12;
			matrix[3][1] = 19;
			matrix[3][2] = 8;
			matrix[3][3] = 18;
			
			matrix[4][0] = 14;
			matrix[4][1] = 17;
			matrix[4][2] = 10;
			matrix[4][3] = 19;
			*/
			
			// zapelniamy reszte macierzy maxem -> mamy macierz kwadratow¹
			switch(tryb){
			case 1:
				for (int i = 0; i < btsSize; i++)
					for (int j = 0; j < btsSize; j++)
						if (matrix[i][j] == -1)
							matrix[i][j] = max;
				System.out.println();
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[i].length; j++) {
						System.out.print(matrix[i][j] + " ");
					}
					System.out.println();
				}

				// pierwszy krok
				matrix = subtractMinFromRow(matrix);
				// drugi krok
				matrix = subtractMinFromColumn(matrix);

				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[i].length; j++) {
						System.out.print(matrix[i][j] + " ");
					}
					System.out.println();
				}

				int[][] m2 = new int[matrix.length][matrix.length];
				// trzeci krok
				m2 = copy(matrix, m2);
				m2 = coverTheZeroes(m2);

				System.out.println(wywolania);

				while (wywolania < btsSize) {
					// krok 4
					m2 = addToCover(matrix, m2);

					// krok 5
					m2 = subtractMin(m2);
					
					// krok 3
					m2 = coverTheZeroes(m2);
					
					System.out.println(wywolania);
					// wywolania++;
				}

				System.out.println();
				for (int i = 0; i < m2.length; i++) {
					for (int j = 0; j < m2[i].length; j++) {
						System.out.print(m2[i][j] + " ");
					}
					System.out.println();
				}
				break;
			case 2:
				for (int i = 0; i < userSize; i++)
					for (int j = 0; j < userSize; j++)
						if (matrix[i][j] == -1)
							matrix[i][j] = max;
				System.out.println();
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[i].length; j++) {
						System.out.print(matrix[i][j] + " ");
					}
					System.out.println();
				}

				// pierwszy krok
				matrix = subtractMinFromRow(matrix);
				// drugi krok
				matrix = subtractMinFromColumn(matrix);

				System.out.println();
				for (int i = 0; i < matrix.length; i++) {
					for (int j = 0; j < matrix[i].length; j++) {
						System.out.print(matrix[i][j] + " ");
					}
					System.out.println();
				}

				m2 = new int[matrix.length][matrix.length];
				// trzeci krok
				m2 = copy(matrix, m2);
				m2 = coverTheZeroes(m2);

				System.out.println(wywolania);

				while (wywolania < userSize) {
					// krok 4
					m2 = addToCover(matrix, m2);

					// krok 5
					m2 = subtractMin(m2);
					
					// krok 3
					m2 = coverTheZeroes(m2);
					
					System.out.println(wywolania);
					// wywolania++;
				}

				System.out.println();
				for (int i = 0; i < m2.length; i++) {
					for (int j = 0; j < m2[i].length; j++) {
						System.out.print(m2[i][j] + " ");
					}
					System.out.println();
				}
				break;
			}
			
		

	}

	private int[][] copy(int[][] matrix, int[][] m2) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++)
				m2[i][j] = matrix[i][j];
		}
		return m2;
	}
	
	private void clean(int[][] matrix){
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++)
				if(matrix[i][j] == -1) matrix[i][j] = 0;
				else matrix[i][j] = 1;
		}
	}
	
	private void selectZero(int[][] matrix){
		clean(matrix);
		
	}

	private int[][] addToCover(int[][] matrix, int[][] m2) {
		int min = 0;
		boolean wstawiono = false;
		// wyszukujemy najmniejszego elementu z elementów nie skreœlonych
		for (int i = 0; i < m2.length; i++) {
			for (int j = 0; j < m2[i].length; j++) {
				if (m2[i][j] != -1 && m2[i][j] != -2 && m2[i][j] != -3)
					if (!wstawiono) {
						// warunek tylko dla pierwszego przejœcia
						min = m2[i][j];
						wstawiono = true;
					} else if (min > m2[i][j])
						min = m2[i][j];
			}
		}
		// dodajemy znaleziony najmniejszy element do wszystkich elementów
		// skreœlonych
		System.out.println(min);
		for (int i = 0; i < m2.length; i++) {
			for (int j = 0; j < m2[i].length; j++) {
				if (m2[i][j] == -1 || m2[i][j] == -2) {
					m2[i][j] = matrix[i][j] + min;
					// System.out.println(matrix[i][j]);
				} else if (m2[i][j] == -3) {
					m2[i][j] = matrix[i][j] + min + min;
					// System.out.println(matrix[i][j]);
				}
			}
		}
		return m2;

	}

	int wywolania = 0;

	private int[][] coverTheZeroes(int[][] matrix) {
		wywolania = 0;
		boolean jest = false;
		int[][] m2 = new int[matrix.length][matrix.length];
		copy(matrix, m2);
		for (int k = matrix.length; k > 0; k--) {

			// sprawdza ile zer jest w danym wierszu
			for (int i = 0; i < matrix.length; i++) {
				int licznik = 0;
				for (int j = 0; j < matrix[i].length; j++) {
					if (matrix[i][j] == 0)
						licznik++;
				}
				// wykresl caly wiersz
				if (licznik == k)
					matrix = coverRow(matrix, i);
			}

			// sprawdza ile zer jest w danej kolumnie
			for (int i = 0; i < matrix.length; i++) {
				int licznik = 0;
				for (int j = 0; j < matrix[i].length; j++) {
					if (matrix[j][i] == 0)
						licznik++;
				}
				// wykresl caly wiersz
				if (licznik == k)
					matrix = coverColumn(matrix, i);
			}

		}

		System.out.println();
		System.out.println();
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}

		return matrix;

	}

	private int[][] coverRow(int[][] matrix, int i) {
		wywolania++;
		for (int j = 0; j < matrix[i].length; j++) {
			if (matrix[i][j] == 0)
				matrix[i][j] = -1;
			else if (matrix[i][j] == -1 || matrix[i][j] == -2)
				matrix[i][j] = -3;
			else
				matrix[i][j] = -2;
		}

		// System.out.println();
		// System.out.println("Cover Row");
		// for (int k = 0; k < matrix.length; k++) {
		// for (int j = 0; j < matrix[k].length; j++) {
		// System.out.print(matrix[k][j] + " ");
		// }
		// System.out.println();
		// }

		return matrix;
	}

	private int[][] coverColumn(int[][] matrix, int i) {
		wywolania++;
		for (int j = 0; j < matrix[i].length; j++) {
			if (matrix[j][i] == 0)
				matrix[j][i] = -1;
			else if ((matrix[j][i] == -1 || matrix[j][i] == -2))
				matrix[j][i] = -3;
			else
				matrix[j][i] = -2;
		}

		// System.out.println();
		// System.out.println("Cover Column");
		// for (int k = 0; k < matrix.length; k++) {
		// for (int j = 0; j < matrix[k].length; j++) {
		// System.out.print(matrix[k][j] + " ");
		// }
		// System.out.println();
		// }

		return matrix;
	}

	

	// Odejmujemy najmniejszy element od wszystkich elementów
	private int[][] subtractMin(int[][] matrix) {
		int[][] m2 = new int[matrix.length][matrix.length];
		int min = matrix[0][0];
		for (int i = 1; i < matrix.length; i++)
			for (int j = 1; j < matrix[i].length; j++)
				if (min > matrix[i][j])
					min = matrix[i][j];

		for (int i = 0; i < matrix.length; i++)
			for (int j = 1; j < matrix[i].length; j++)
				m2[i][j] = matrix[i][j] - min;

		return m2;

	}

	// Odejmujemy najmniejsz¹ wartoœæ wiersza od wszystkich elementów tego
	// wiersza
	private int[][] subtractMinFromRow(int[][] matrix) {
		int[][] m2 = new int[matrix.length][matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			int min = 0;
			for (int j = 0; j < matrix[0].length; j++) {
				if (j == 0)
					min = matrix[i][j];
				else if (min > matrix[i][j])
					min = matrix[i][j];
			}

			for (int j = 0; j < matrix[0].length; j++) {
				m2[i][j] = matrix[i][j] - min;
			}
		}
		return m2;

	}

	// Odejmujemy najmniejsz¹ wartoœæ kolumny od wszystkich elementów tej
	// kolumny
	private int[][] subtractMinFromColumn(int[][] matrix) {
		int[][] m2 = new int[matrix.length][matrix.length];
		for (int i = 0; i < matrix[0].length; i++) {
			int min = 0;
			for (int j = 0; j < matrix.length; j++) {
				if (j == 0)
					min = matrix[j][i];
				else if (min > matrix[j][i])
					min = matrix[j][i];
			}

			for (int j = 0; j < matrix.length; j++) {
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
