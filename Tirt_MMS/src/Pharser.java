import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;


public class Pharser {

	private String usersPath, btsesPath;
	
	public Pharser(){
		btsesPath="C:\\Users\\MKul\\workspace_eclipse\\Tirt_MMS\\btses30.txt";
		usersPath="C:\\Users\\MKul\\workspace_eclipse\\Tirt_MMS\\users30.txt";
	}
	
	public ArrayList<Bts> readBtses(){
		ArrayList<Bts> btses=new ArrayList<Bts>();
		try {
			BufferedReader br=new BufferedReader(new FileReader(btsesPath));
			StreamTokenizer st=new StreamTokenizer(br);
			st.nextToken();
			int n=(int) st.nval;
			for(int i=0;i<n;i++){
				st.nextToken();
				String id=st.sval;
				st.nextToken();
				float x=(float) st.nval;
				st.nextToken();
				float y=(float) st.nval;
				st.nextToken();
				float r=(float) st.nval;
				st.nextToken();
				float p=(float) st.nval;
				Bts bts=new Bts(id,x,y,r,p);
				btses.add(bts);
				//System.out.println(bts.toString());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return btses;
	}
	
	public ArrayList<User> readUsers(){
		ArrayList<User> users=new ArrayList<User>();
		try {
			BufferedReader br=new BufferedReader(new FileReader(usersPath));
			StreamTokenizer st=new StreamTokenizer(br);
			st.nextToken();
			int n=(int) st.nval;
			for(int i=0;i<n;i++){
				st.nextToken();
				String id=st.sval;
				st.nextToken();
				float x=(float) st.nval;
				st.nextToken();
				float y=(float) st.nval;
				User user=new User(id,x,y);
				users.add(user);
				//System.out.println(user.toString());
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	
	public void setUsersPath(String p){
		usersPath=p;
	}
	
	public void setBtsesPath(String p){
		btsesPath=p;
	}
	
	public String getUsersPath(){
		return usersPath;
	}
	
	public String getBtsesPath(){
		return btsesPath;
	}
	
}
