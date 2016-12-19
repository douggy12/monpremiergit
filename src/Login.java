import java.sql.*;
import java.util.Scanner;



public class Login {
	private String user;
	private String mdp;
	
	public Login(){
		System.out.print("Entrez votre login : ");
		Scanner sc = new Scanner(System.in);
		user = sc.nextLine();
		System.out.print("Entrez votre mdp : ");
		mdp = sc.nextLine();
		
		
	}
	
	public Login(String invite){
		user = invite;
		mdp = invite;
	}
	
	


	
	
	public boolean verifier(){
		
		try{
			
		
		
		
		String queryLogin = "SELECT login,mdp FROM utilisateur WHERE login = '"+user+"';";
		String queryMdp = "SELECT login,mdp FROM utilisateur WHERE mdp = '"+mdp+"';";
		
		ResultSet resultLogin = ConnectBdd.getInstance().createStatement().executeQuery(queryLogin);
		ResultSet resultMdp = ConnectBdd.getInstance().createStatement().executeQuery(queryMdp);
		
		
		if (resultLogin.next()){
			System.out.println("Login OK!");
			if (resultMdp.next()){
				System.out.println("Mdp OK!");
				return true;
			}
			System.out.println("Mauvais Mdp :(");
			return false;
		}
		System.out.println("Mauvais Login :(");
		return false;
		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean creation(){
		try{
		String queryTest = "SELECT * FROM utilisateur WHERE login = ?;";
		PreparedStatement preparedTest = ConnectBdd.getInstance().prepareStatement(queryTest, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		preparedTest.setString(1, user);
		if (preparedTest.executeQuery().next()){
			System.out.println("Ce nom d'utilisateur existe déjà ! ");
			return false;
		}
		
		String query = "INSERT INTO utilisateur(login,mdp) VALUES(?,?);";
		
		
		
		PreparedStatement prepared = ConnectBdd.getInstance().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		prepared.setString(1, user);
		prepared.setString(2, mdp);
		
		
		if(prepared.executeUpdate()==1)System.out.println("*******************Création OK**********************");
		else System.out.println("ERREUR");
		prepared.close();
		return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public void consulterHistorique(){
		try{
			
			int lineCount =0;
			int lineSelect = 0;
			
			String query ="SELECT win,nb_coup,combinaison FROM partie,user_partie,utilisateur WHERE utilisateur.id_user = user_partie.id_user_k AND partie.id_partie = user_partie.id_partie_k AND utilisateur.login = ?; ";
			PreparedStatement prepared = ConnectBdd.getInstance().prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepared.setString(1, user);
			ResultSet result = prepared.executeQuery();
			ResultSetMetaData resultMeta = result.getMetaData();
			System.out.println("\n**********************************************************");
			for (int i = 1 ; i<=resultMeta.getColumnCount();i++){
				System.out.print("\t"+ resultMeta.getColumnName(i).toUpperCase()+"\t *");
			}
			System.out.println("\n**********************************************************");
			
			while(result.next()){
				for (int i =1; i<=resultMeta.getColumnCount();i++){
					System.out.print("\t"+result.getObject(i).toString() + "\t |");
				}
				System.out.println("\n----------------------------------------------------------");
			}
			
			
			
			prepared.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	public int getUserId(){
		
		try{
			String query = "SELECT id_user FROM utilisateur WHERE login = '"+user+"';";
			ResultSet result = ConnectBdd.getInstance().createStatement().executeQuery(query);
			if(result.next())return result.getInt(1);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return 0;
	}
}
