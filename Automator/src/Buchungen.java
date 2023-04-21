import java.util.ArrayList;

public class Buchungen 
{
	private ArrayList<Buchungszeile> buchungen= new ArrayList<Buchungszeile>();
	private String header = "";
	
	public void add_header(String in_header)
	{
		header += in_header+"\n";
		//System.out.println (header);
	}
	public void add_buchungszeile (String in_column, Float in_menge, Float inBetrag, boolean isBankomat) 
	{
		System.out.println (in_column+" "+in_menge+" "+ inBetrag+" "+ isBankomat);
		buchungen.add(new Buchungszeile());//ausformulieren
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
}
