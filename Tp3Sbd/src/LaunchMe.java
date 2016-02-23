import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
			/*for(Donnees d : data){
				System.out.println(d.toString());
			}*/
			
			//Ecriture sur un fichier CSV
			BufferedWriter bw;
			try{
				bw = new BufferedWriter( new FileWriter(
					new File("./monJeuDeDonnees.csv"), false));
				bw.append("colonne1, colonne2 \n");
				bw.flush();
				bw.close();
		
			}catch (IOException e)
				{
			// TODO Auto-generated catch block
				e.printStackTrace();
				}
		
		}
		
	}
	
	public void mondrian(Donnees[] data, int k){
		int dim= chooseDimension(data);
		
	}
	public int chooseDimension(Donnees[] data){
		
		int qid1min = Integer.MAX_VALUE;
		int qid1max = Integer.MIN_VALUE;
		int qid2min = Integer.MAX_VALUE;
		int qid2max = Integer.MIN_VALUE;
		
		for (Donnees d : data){
			if(d.getQID1() < qid1min)
				qid1min = d.getQID1();
			
			if(d.getQID1() > qid1max)
				qid1max = d.getQID1();
			
			if(d.getQID2() < qid2min)
				qid2min = d.getQID2();
			
			if(d.getQID2() > qid2max)
				qid2max = d.getQID2();
		}
		
		int dif1= qid1max - qid1min;
		int dif2 =qid2max - qid2min;
		
		if(dif1> dif2)
			return 1;
		else
			return 2;
		
	}
	
	
	
}
