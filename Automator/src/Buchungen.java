import java.util.ArrayList;

public class Buchungen 
{
	private ArrayList<Buchungszeile> buchungen= new ArrayList<Buchungszeile>();
	private String header = "";
	String text = "NADA";
	public void add_header(String in_header)
	{
		header += in_header+"\n";
		//System.out.println (header);
	}
	/**
	 * 
	 * @param in_column Position im Gerät
	 * @param in_menge verkaufte Menge
	 * @param inBetrag gesamtbetrag
	 * @param isBankomat ist_bankomat?
	 * @throws Exception 
	 */
	public void add_buchungszeile (String in_column, Double in_menge, Double inBetrag, boolean isBankomat) throws Exception 
	{
		System.out.println (in_column+" "+in_menge+" "+ inBetrag+" "+ isBankomat);
		buchungen.add(new Buchungszeile(in_column,inBetrag, in_menge, isBankomat));//ausformulieren
		
	}

	public String return_bar_csv ()
	{
		return "ERROR";
	}
	public String return_bankomat_csv ()
	{
		return "ERROR";
	}
	public String get_header()
	{
		return header;
	}
	public String getText() {
		// TODO Auto-generated method stub
		text = "";
		for (int i = 0;i<buchungen.size();i++)
		{
			Buchungszeile buchungszeile = buchungen.get(i);
			
			
		}
		return "XX";
	}
	public int size()
	{
		return buchungen.size();
	}
}
