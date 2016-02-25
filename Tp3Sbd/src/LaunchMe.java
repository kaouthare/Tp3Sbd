import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;
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
	
	//resultat
	static List<ArrayList<Donnees>> listeFinalMondrian;
	
	//la méthode void main 
	public static void main (String [] arg){
		
		//Valeurs test
		n=30;
		QID1min=0;
		QID1max=20;
		QID2min=40;
		QID2max=50;
		nbVD=5;
		k=4;
		
		
		//Donnees sensibles :initialistation
		String[] Sd= new String [nbVD];
		
		for (int i=0; i<nbVD; i++){
			
			Sd[i]= UUID.randomUUID().toString().substring(1, 6);
		}
		
		//n-uplets : structure de données 
		ArrayList<Donnees> data = new ArrayList<Donnees>();
		//créer le random pour pouvoir generer automatiquement les données 
		Random random = new Random();
		for(int i = 0; i<n; i++){
			
			//generer automatiquement le premier quasi identifiant 
			int qid1 = QID1min + random.nextInt((QID1max - QID1min) +1);
			
			//generer automatiquement le deuxieme quasi identifiant 
			int qid2 = QID2min + random.nextInt((QID2max - QID2min) +1 );
			
			//choix du numero de la donnee sensible 
			int numSd = random.nextInt(nbVD);
			
			//n-uplets
			data.add(new Donnees(qid1, qid2, Sd[numSd]));
		}
			
		
			//Affichage
			/*for(Donnees d : data){
				System.out.println(d.toString());
			}*/
			
			//Ecriture sur un fichier CSV
			BufferedWriter bw;
			try{
				bw = new BufferedWriter( new FileWriter(
			
				new File("./monJeuDeDonnees.csv"), false));
				
				for(Donnees d : data){
				//bw.append("colonne1, colonne2 \n");
					//System.out.println(d.getQID1());
					//System.out.println(d.getQID2());
					
				bw.append(d.getDs()+ " "+ d.getQID1()+" " + d.getQID2()+ "\n");
				}
				bw.flush();
				bw.close();
						
			}catch (IOException e)
				{
			// TODO Auto-generated catch block
				e.printStackTrace();
				}
			
			 listeFinalMondrian= new ArrayList<ArrayList<Donnees>>();
			 
			mondrian(data, k);
			
			for(ArrayList<Donnees> LF : listeFinalMondrian){
				System.out.println("Le Groupe:");
				for (Donnees d: data){
					System.out.println(d.toString());
				}
			
			}
	}
	
	public static void mondrian(ArrayList<Donnees> data, int k){
		//s'il n'existe pas de découpe possible, il retourne le groupe 
		if (data.size()<(2*k)){
			listeFinalMondrian.add(data);
			
		}else{
			//choisir la dimension avec laquel on fera la découpe 
				int dim= chooseDimension(data);
			//définir des valeurs uniques prises par les tuples pour la dimension choisie , chaque paire avec le nombre de fois où il apparaît
				TreeMap<Integer, Integer> fs = frequencySet(data, dim);
			//Trouver le mediane
				int median = findMedian(fs, data.size());
				//deux liste de données L left, R right 
				ArrayList<Donnees> L= new ArrayList<Donnees>();
				ArrayList<Donnees> R= new ArrayList<Donnees>();
				
				if (dim==1){
					for(Donnees d: data){
						if(d.getQID1()<= median){
							L.add(d);
						}
					}
				}else 
				{
					
					for(Donnees d: data)
					{
						if(d.getQID1()>= median)
						{
							
							R.add(d);
						}
					}
				}
				mondrian(L, k);
				mondrian(R, k);
		}
	}
	
	//trouver la median 
	public static int findMedian(TreeMap<Integer, Integer> frequency, int size){
		//initialisation des valeurs à -1
		int cleMedian =-1;
		//somme des valeurs 
		int somme =0;
		//mettre un iterateur sur the frequency 
		Iterator<Entry<Integer,Integer>> it= frequency.entrySet().iterator();
		//tant que la valeur est inférieur à la dimension du tableau sur 2
		while(it.hasNext() && somme<size/2){
			//récuperer la prochaine valeur 
			Entry<Integer, Integer> entree = it.next();
			//recuperer la clé (la valeur de la clé)
			cleMedian += entree.getValue();
			
		}
		
		return cleMedian;
	}
	
	public static TreeMap<Integer, Integer> frequencySet(ArrayList<Donnees> data, int dim){
		//new Hash Map 
		HashMap<Integer, Integer> frequence = new HashMap<Integer, Integer>();
		//si la dimension numéro 1 est choisie 
		
		if (dim ==1){
			//pour tester
			//System.out.println("hey");
			for(Donnees d: data){
				
				//compteur
				int compt=0;
				//si fs contient deja la clé donc 
				if (frequence.containsKey(d.getQID1())){
					
					//prendre la valeur de la clé 
					compt=frequence.get(d.getQID1());
					//pour tester : System.out.println(compt);
				}
				//ajouter les valeurs à notre hashmap 
				frequence.put(d.getQID1(), compt+1);
			}
		}else{
			//pour tester
			System.out.println("coucou");
			for(Donnees d: data){
				//compteur
				int compt=0;
				//si fs contient deja la clé donc 
				if (frequence.containsKey(d.getQID2())){
					//prendre la valeur de la clé 
					compt=frequence.get(d.getQID2());
				}
				//ajouter les valeurs à notre hashmap 
				frequence.put(d.getQID1(), compt+1);
		
		}
		}
		//retourner la treemap qui contient l'element et le nombre de fois ou il a été vu 
		return new TreeMap<Integer, Integer>(frequence);
		
	
	}
	public static int chooseDimension(ArrayList<Donnees> data){
		//valeur min de qid1 on lui donne pour l'instant une valeur max pour pouvoir comparer plus tard
		int qid1min = Integer.MAX_VALUE;
		//valeur max de qid1 on lui donne pour l'instant une valeur min pour pouvoir comparer plus tard 
		int qid1max = Integer.MIN_VALUE;
		//valeur min de qid2 on lui donne pour l'instant une valeur max
		int qid2min = Integer.MAX_VALUE;
		//valeur max de qid2 on lui donne pour l'instant une valeur min 
		int qid2max = Integer.MIN_VALUE;
		
		for (Donnees d : data){
			if(d.getQID1() < qid1min)
				//valeur min de qid1
				qid1min = d.getQID1();
			
			if(d.getQID1() > qid1max)
				//valeur max de qid1
				qid1max = d.getQID1();
			
			if(d.getQID2() < qid2min)
				//valeur min de qid2
				qid2min = d.getQID2();
			
			if(d.getQID2() > qid2max)
				//valeur max de qid2
				qid2max = d.getQID2();
		}
		
		//la difference entre le max et le min nous donnera la dimension la plus large.
		int dimf1= qid1max - qid1min;
		int dimf2 =qid2max - qid2min;
		//si la dimension 1 est la plus large on retourne 1
		if(dimf1> dimf2)
			return 1;
		//si la dimension 2 est la plus large on retourne 2
		else
			return 2;
		
	}
	
	
	
}
