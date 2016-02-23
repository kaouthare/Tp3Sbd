//structure de données représentant une classe d équivalence
public class Donnees {
	
	//les deux quasi identifiants
	public int QID1;
	public int QID2;
	//attribut représentant une données sensible 
	public String Ds;
	

	//le constructeur de la classe 
	public Donnees(int qID1, int qID2, String ds) {
		super();
		QID1 = qID1;
		QID2 = qID2;
		Ds = ds;
	}
	//getters et setters des attributs 
	public int getQID1() {
		return QID1;
	}
	public void setQID1(int qID1) {
		QID1 = qID1;
	}
	public int getQID2() {
		return QID2;
	}
	public void setQID2(int qID2) {
		QID2 = qID2;
	}
	public String getDs() {
		return Ds;
	}
	public void setDs(String ds) {
		Ds = ds;
	}
	
	//to String
	@Override
	public String toString() {
		return "Donnees [QID1=" + QID1 + ", QID2=" + QID2 + ", Ds=" + Ds + "]";
	}
	
	
	
	
	
}
