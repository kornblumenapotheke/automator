
public class Buchungszeile 
{
	 String pos;
	 String pzn;
	 String menge;
	 String bezeichnung;
	 String preis_brutto; //mit KOMMA
	 String preis_netto; //mit KOMMA
	 String steuersatz; //in Prozent
	 boolean ist_bankomat; //handelt es sich um eine bankomatzahlung?
	 Umbenennung umbenennung;
	
	Buchungszeile ()
	{
		pzn = "XXX";
		menge= "XXX";
		preis_brutto = "XXX";
		preis_netto = "XXX";
		steuersatz = "123";
		System.out.print("Falsche Initialisierung Buchungszeile!");
		
	}
	
	Buchungszeile (String inPos, String in_betrag, String in_menge, String in_is_bankomat) throws Exception
	{
		pos=inPos;
		
		
	}

	public String getPzn()
	{
		return pzn;
	}
	public String getMenge ()
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
	
	
}
