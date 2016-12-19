import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectBdd {
	
	 static String url ="jdbc:postgresql://localhost:5432/ZeGame";
	 static String user = "postgres";
	 static String passwd ="linux";
	 public static Connection mycon;
	 
	 private ConnectBdd(String url, String user, String passwd){
			try{


				Class.forName("org.postgresql.Driver");
				
				mycon = DriverManager.getConnection(url, user , passwd);
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}

		public static Connection getInstance(){
			if(mycon == null) new ConnectBdd(url,user,passwd);
			return mycon;
		}
	 
	 

}
