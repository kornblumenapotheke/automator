
public class Buchungszeile 
{
	 String pzn;
	 String menge;
	 String bezeichnung;
	 String preis_brutto; //mit KOMMA
	 String preis_netto; //mit KOMMA
	 int steuersatz; //in Prozent
	 boolean ist_bankomat; //handelt es sich um eine bankomatzahlung?
	 Umbenennung umbenennung;
	
	Buchungszeile ()
	{
		pzn = "XXX";
		menge= "XXX";
		preis_brutto = "XXX";
		preis_netto = "XXX";
		steuersatz = 123;
		System.out.print("Falsche Initialisierung Buchungszeile!");
		
	}
	
	Buchungszeile (String inPos, String in_betrag, String in_menge, String in_is_bankomat) throws Exception
	{
		umbenennung= new Umbenennung("D:\\owncloud\\1_APOTHEKE_Grafenstein_GZ\\LAUFENDER_BETRIEB\\Finanzen\\Automat Grafenstein\\Lagerbeschreibung_Automat.csv");
		Artikel artikel = umbenennung.get(inPos);
		pzn = artikel.get_pzn();
		steuersatz = artikel.get_steuersatz();
		menge=in_menge;
		ist_bankomat = in_is_bankomat;
		
		//finde steuersatz
		//berechne nettowert		
		
	}
}
