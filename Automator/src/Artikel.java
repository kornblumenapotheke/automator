
public class Artikel 
{
	String lagerort;
	String artikelname;
	String pzn;
	String preis_brutto;
	String preis_netto;
	String steuersatz;
	
 public Artikel ()
 {
	 artikelname = "NICHT INITIALISIERT";
	 pzn = "NICHT INITIALISIERT";
	 steuersatz = "NICHT INITIALISIERT";
	 lagerort = "NICHT INITIALISIERT";
	 preis_brutto = "NICHT INITIALISIERT";
	 preis_netto= "NICHT INITIALISIERT";
 }
 
 public Artikel (String in_lagerort, String in_pzn, String in_artikelname, String in_preis, String in_steuersatz)
 {
	 lagerort=in_lagerort;
	 artikelname = in_artikelname;
	 pzn = in_pzn;
	 preis_brutto = in_preis;
	 steuersatz = in_steuersatz;
	 preis_netto ="TO DO";
 }
 
 public String get_artikelname ()
 {
	 return artikelname;
 }
 public String get_pzn ()
 {
	 return pzn;
 }
 public String get_steuersatz ()
 {
	 return steuersatz;
 }
 public String getBrutto ()
 {
	return preis_brutto;	 
 }
 
 public String getNetto ()
 {
	 return preis_netto;	 
 }
 
 public String getCSV ()
 {
	 return (lagerort+";"+artikelname+";"+pzn+";"+steuersatz+";\n");
 }

 

}
