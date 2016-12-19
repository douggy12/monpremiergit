import java.util.Arrays;

public class MMHistorique {
	private MMPion[] histoProp;
	private int bP,mP;
	private int propLen;
	
	
	public MMHistorique(MMPion[] tabProp, int bP, int mP){
		histoProp = new MMPion[tabProp.length];
		for (int i = 0 ; i < tabProp.length; i++) {
			this.histoProp[i] = tabProp[i];
		}
		this.bP = bP;
		this.mP = mP;
		this.propLen = tabProp.length;
	}


	public MMPion[] getHistoProp() {
		return this.histoProp;
	}


	public int getbP() {
		return this.bP;
	}
	
	public int getmP() {
		return this.mP;
	}
	
	public void afficher(){
		System.out.print(Arrays.toString(histoProp)+ " ");
		for (int i = 0 ; i<bP;i++){
			System.out.print("O ");
		}
		for (int i=0; i<mP;i++){
			System.out.print("X ");
		}
		for (int i = 0;i<(propLen-(bP+mP));i++){
			System.out.print("_ ");
		}
		System.out.print("\n");
	}


	@Override
	public String toString() {
		return ( Arrays.toString(histoProp) + " BP : " + bP + " MP : " + mP);
	}
	
	
	
	

}
