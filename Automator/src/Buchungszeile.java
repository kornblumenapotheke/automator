
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
		umbenennung= new Umbenennung("f:\\\\Lagerbeschreibung_Automat_korni.csv");
		artikel = umbenennung.get(inPos);
		pzn = artikel.get_pzn();
		steuersatz = artikel.get_steuersatz();
		menge=in_menge;
		ist_bankomat = in_is_bankomat;
		betrag_automat=in_betrag;
		
		//finde steuersatz
		//berechne nettowert		
		
		
	}

	
	Buchungszeile (String inPos, Double in_betrag, Double in_menge, boolean in_is_bankomat, String inProduktListe) throws Exception
	{

		pos=inPos;
		umbenennung= new Umbenennung(inProduktListe);
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
	public String getMenge ()
	{
		return Double.toString(menge);
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
		String text = "";
		if (ist_bankomat==true)
		{
			text+="BANKO\t";
		}
		else
		{
			text +="CASH\t";
		}

		return text + pos+"\t"+menge+"Stk \t"+pzn+"\t"+artikel.get_artikelname()+"\t à"+preis_netto+"\t("+steuersatz+")Prozent\t"+preis_brutto+"brutto\t=\t"+betrag_automat;
		
	}
	public String getZahlungsweise() {
		// TODO Auto-generated method stub
		if (ist_bankomat==true)
		{
			return "BANKO\t";
		}
		else
		{
			return "CASH\t";
		}
		
		
	}
	public String getProdukt() {
		// TODO Auto-generated method stub
		return artikel.get_artikelname();
	}
	public String getEP() {
		// TODO Auto-generated method stub
		return artikel.getBrutto();
	}
	public String getSumme() {
		// TODO Auto-generated method stub
		return Double.toString(betrag_automat);
	}
	
	
}
