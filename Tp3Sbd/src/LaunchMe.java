import java.util.Random;
import java.util.UUID;

public class LaunchMe {
	
	//la cardinalité n du jeu de donnees
	static int n;
	
	//la borne minimale du premier quasi identifiant 
	static int QID1min;
	
	//la borne maximale du premier quasi identifiant 
	static int QID1max;
	
	//la borne minimale du deuxieme quasi identifiant 
	static int QID2min;
	
	//la borne maximale du deuxieme  quasi identifiant 
	static int QID2max;
	
	 //le nombre de valeurs distinctes possibles pour SD
	static int nbVD;
	
	//la valeur du paramètre de confidentialité 
	static int k;
	
	//la méthode void main 
	public static void main (String [] arg){
		
		//Valeurs test
		n=10;
		QID1min=1;
		QID1max=9;
		QID2min=10;
		QID2max=50;
		nbVD=4;
		k=2;
		
		
		//Donnees sensibles :initialistation
		String[] Sd= new String [nbVD];
		
		for (int i=0; i<nbVD; i++){
			
			Sd[i]= UUID.randomUUID().toString().substring(1, 6);
		}
		
		//n-uplets : structure de données 
		Donnees[] data = new Donnees[n];
		//créer le random pour pouvoir generer automatiquement les données 
		Random random = new Random();
		for(int i = 0; i<n; i++){
			
			//generer automatiquement le premier quasi identifiant 
			int qid1 = QID1min + random.nextInt(QID1max - QID1min);
			
			//generer automatiquement le deuxieme quasi identifiant 
			int qid2 = QID2min + random.nextInt(QID2max - QID2min);
			
			//choix du numero de la donnee sensible 
			int numSd = random.nextInt(nbVD);
			
			//n-uplets
			data[i]= new Donnees(qid1, qid2, Sd[numSd]);
			
			
			//Affichage
			for(Donnees d : data){
				System.out.println(d.toString());
			}
			
			
		
		}
		
	}

}
