import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		

		//ConnectBdd.getInstance();
		//MMPlateau game = new MMPlateau();
		//game.proposer();
		//Login user = new Login("test", "testmdp");
		//user.verifier();

		//game.creerCompte();
		menu1();
		
		//Login login = new Login();
		//login.consulterHistorique();
		
	}

	@SuppressWarnings("resource")
	public static void menu1(){
		int rep1;
		
		Scanner sc = new Scanner(System.in);
		do{
			System.out.println("-------------Bienvenue dans le MASTERMIND 2.0------------");
			System.out.println("");
			System.out.println("1- Se connecter à un compte existant");
			System.out.println("2- Créer un compte");
			System.out.println("3- Se connecter en tant qu'invité");
			System.out.println("4- Quitter");
			rep1 = sc.nextInt();
		}while(rep1<0 && rep1>4);
		
		switch(rep1){
		case 1 :
			Login login1 = new Login();
			if(login1.verifier())menuJoueur(login1);
			break;
			
		case 2 :
			Login login2 = new Login();
			if(login2.creation())menuJoueur(login2);
			break;
			
		case 3 :
			Login login3 = new Login("invite");
			if(login3.verifier())menuJoueur(login3);
			break;
			
			

		default:
			System.out.println("BYE Bye !");
			
		}

	}
	
	@SuppressWarnings("resource")
	public static void menuJoueur(Login login){
		int rep2;
		Scanner sc = new Scanner(System.in);
		do{
			System.out.println(">>>>>>>>>>>>>>>Menu Joueur<<<<<<<<<<<<<<<<<<");
			System.out.println();
			System.out.println("1- Consulter l'historique");
			System.out.println("2- Jouer au MasterMind");
			System.out.println("3- Quitter");
			rep2 = sc.nextInt();
		}while(rep2<0 && rep2>3);
		
		switch(rep2){
		case 1 :
			login.consulterHistorique();
			menuJoueur(login);
			break;
		case 2 :
			MMPlateau game = new MMPlateau(login);
			menuJoueur(login);
			break;
		case 3 :
			System.out.println("Bye bye !");
			
		}
	
	}
	
	
}






