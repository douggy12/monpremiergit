
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MMPlateau {

	final int NBCOMBI = 4;
	private int nbEssai = 10;
	private int nbCoup = 0;
	private MMPion[] tabRand = new MMPion[NBCOMBI];
	private MMPion[] tabProp = new MMPion [NBCOMBI];
	private MMPion[] tabComp = new MMPion [NBCOMBI];
	private ArrayList<MMHistorique> historique = new ArrayList<>();
	private int cptBp, cptMp;
	
	private int idUser;



	public MMPlateau(Login login){
		this.idUser = login.getUserId();
		jouer();

	}
	public void jouer(){

		for (int i=0; i<tabRand.length; i++){
			int rand = (int)new Random().nextInt(8)+1;

			tabRand[i] = new MMPion(rand);

		}

		System.out.println("Ca y'est j'ai trouvé la combinaison à deviner ! c'est "+ Arrays.toString(tabRand));

		proposer();
	}



	@SuppressWarnings("resource")
	public void proposer(){
		int prop;
		System.out.println("Proposer une séquence de " + NBCOMBI + " chiffres entre 1 et 8");
		System.out.println("Entrez un 0 pour quitter");
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i<NBCOMBI;i++){

			System.out.print("Chiffre "+(i+1)+" : ");
			prop = sc.nextInt();
			while (prop >8 || prop <0){
				System.out.println("Mauvaise entrée, proposez une chiffre entre 1 et 8");
				System.out.print("Chiffre "+(i+1)+" : ");
				prop = sc.nextInt();
			}
			//if(prop == 0 )quitter();

			tabProp[i]= new MMPion(prop);

		}

		bienPlace();

	}
	/**
	 * compare les bien placés
	 */
	public void bienPlace(){
		cptBp =0; cptMp =0;


		for (int i =0; i<tabRand.length;i++){
			if (tabRand[i].getnPion() == tabProp[i].getnPion()){
				tabComp[i] = new MMPion(0);
				cptBp ++;
			}
			else tabComp[i] = new MMPion(tabProp[i].getnPion());

		}
		if (cptBp == tabRand.length){
			System.out.println("BRAVO c'est gagné !");
			nbCoup++;
			sendPartie(true);

		}
		else{

			malPlace();

		}
	}

	public void malPlace(){
		MMPion[] tabRandCopie = new MMPion[NBCOMBI];
		for (int i=0; i<NBCOMBI;i++){
			tabRandCopie[i] = new MMPion(tabRand[i].getnPion());
			
		}

		for (int i = 0; i <NBCOMBI; i++){
			
				for (int j = 0; j<NBCOMBI; j++){
					if (tabRandCopie[j].getnPion() == tabComp[i].getnPion() && tabComp[i].getnPion() != 0 && tabComp[j].getnPion() != 0){
						//tabComp[i].setnPion(0);
						tabRandCopie[j].setnPion(0);
						cptMp ++;
					}
					
				}
			
			
		}
		System.out.println();
		

		MMHistorique vHisto = new MMHistorique(tabProp,cptBp,cptMp);
		historique.add(vHisto);


		for (int i =0; i<historique.size();i++) {
			historique.get(i).afficher();
		}




		System.out.println();
		nbEssai --;
		nbCoup++;



		if (nbEssai>0){

			proposer();
		}
		else{
			System.out.println();
			System.out.println("Tu as perdu ! LOOSER !");
			System.out.println("la combinaison à trouver était : "+ Arrays.toString(tabRand));
			System.out.println();
			sendPartie(false);

		}


	}
	public void sendPartie(boolean win){
		int idPartie = 0;
		try{
			

			//requete sql pour insert la partie
			String queryPartie = "INSERT INTO partie(win,nb_coup,combinaison) VALUES (?,?,?) RETURNING id_partie;";
			

			PreparedStatement prepared = ConnectBdd.getInstance().prepareStatement(queryPartie, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepared.setBoolean(1, win);
			prepared.setInt(2, nbCoup);
			prepared.setString(3, Arrays.toString(tabRand));
			prepared.execute();
			
			ResultSet result = prepared.getResultSet();
			if(result.first()) idPartie = result.getInt(1);
			
			
			//requete sql pour insert la table de jointure
			String queryUserPartie = "INSERT INTO user_partie(id_user_k,id_partie_k) VALUES (?,?);";
			
			PreparedStatement prepared2 = ConnectBdd.getInstance().prepareStatement(queryUserPartie, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			prepared2.setInt(1, idUser);
			prepared2.setInt(2, idPartie);
			prepared2.executeUpdate();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 

	@SuppressWarnings("resource")
	public void connexion(){
		System.out.print("Entrez votre login : ");
		Scanner sc = new Scanner(System.in);
		String user1 = sc.nextLine();
		System.out.println("Entrez votre mdp : ");
		String mdp1 = sc.nextLine();

		Login login = new Login(user1,mdp1);

		if (login.verifier())user = user1;

	}


	@SuppressWarnings("resource")
	public void creerCompte(){
		System.out.println("***********Création de compte*****************");
		System.out.print("Entrez votre login : ");
		Scanner sc = new Scanner(System.in);
		String user1 = sc.nextLine();
		System.out.print("Entrez votre mdp : ");
		String mdp1 = sc.nextLine();

		Login login = new Login(user1,mdp1);
		login.creation();
	}

	 */



}