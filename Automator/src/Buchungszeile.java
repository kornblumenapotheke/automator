
public class Buchungszeile 
{
	 String pos;
	 String pzn;
	 Double menge;
	 String bezeichnung;
	 String preis_brutto; //mit KOMMA
	 String preis_netto; //mit KOMMA
	 String steuersatz; //in Prozent
	 Double betrag_automat;
	 Artikel artikel;
	 boolean ist_bankomat; //handelt es sich um eine bankomatzahlung?
	 Umbenennung umbenennung;
	
	Buchungszeile ()
	{
		pzn = "XXX";
		menge= 0.0;
		preis_brutto = "XXX";
		preis_netto = "XXX";
		steuersatz = "123";
		System.out.print("Falsche Initialisierung Buchungszeile!");
		
	}
	
	Buchungszeile (String inPos, Double in_betrag, Double in_menge, boolean in_is_bankomat) throws Exception
	{

		pos=inPos;
		umbenennung= new Umbenennung("D:\\Lagerbeschreibung_Automat.csv");
		artikel = umbenennung.get(inPos);
		pzn = artikel.get_pzn();
		steuersatz = artikel.get_steuersatz();
		menge=in_menge;
		ist_bankomat = in_is_bankomat;
		betrag_automat=in_betrag;
		
		//finde steuersatz
		//berechne nettowert		
		
		
	}

	public String getPzn()
	{
		return pzn;
	}
	public Double getMenge ()
	{
		return menge;
	}
	public String getPreisBrutto()
	{
		return preis_brutto;
	}
	public String getPreisNetto()
	{
		return preis_netto;
		
	}
	public String getSteuersatz()
	{
		return steuersatz;
	}

	public String getTextZusammenfassung() {

		return pos+"\t"+menge+"Stk \t"+pzn+"\t"+artikel.get_artikelname()+"\t à"+preis_netto+"\t("+steuersatz+")Prozent\t"+preis_brutto+"brutto\t=\t"+betrag_automat;
		
	}
	
	
}
