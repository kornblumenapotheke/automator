import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Datei {
	
	String machineID = "NIX";
	double umsatz_gesamt = 0.00;
	double bankomat_gesamt = 0.00;
	double bargeld_gesamt = 0.00;
	int anzahl_verkaeufe = 0;
	Buchungen buchungen = new Buchungen();
	
	Datei ()
	{
		
	}
	
	public Buchungen readFile (String inDateiname) throws Exception
	{
		    BufferedReader br = null;
	        FileReader fr = null;
	        fr = new FileReader(inDateiname);
	        br = new BufferedReader(fr);
	        String l;
	        String separator ="\t";
	        machineID=br.readLine();
	        int posID=machineID.indexOf("VND");
	        machineID=machineID.substring(posID);
	        System.out.println ("ID MACHINE: "+machineID);
	        
	        
	        boolean bankomat_1_done = false;
	        while ((l = br.readLine()) != null)
	        {
	        if (l.indexOf("Gesamtbetrag aller Verkäufe seit letzter Ablesung")!=-1)
	        {
	        	umsatz_gesamt=Double.parseDouble(l.substring(l.indexOf("\t")+1));
	        	System.out.println ("Umsatz gesamt: "+umsatz_gesamt);
	        }
	        if (l.indexOf("Gesamtwert bargeldlose Verkäufe seit letzter Ablesung:")!=-1&&!bankomat_1_done)
	        {
	        		        	
	        	String parse_bargeldlos = l.substring(56);
	        	bankomat_gesamt=Double.parseDouble(parse_bargeldlos)/100;
	        	System.out.println ("davon Banomat: "+bankomat_gesamt);
	        	bankomat_1_done = true;
	        	bargeld_gesamt=(umsatz_gesamt-bankomat_gesamt);
	        	System.out.println ("somit bar: "+bargeld_gesamt);
	        }
	        //Anzahl bargeldlöse Verkaeufe
	        //Anzahl gesamt Verkaeufe seit ablesung
	        
	        if (l.indexOf("Preis")!=-1) //wir sind jetzt in den Positionen!
	        {
	        	while ((l = br.readLine()) != null) //jeden Artikel auswerten
	        	{
	        		 String[] col = l.split(separator);
	        		 String artikelID= col [0];
	        		 String einzelpreis=col[1];
	        		 String anzahl_abgaben=col[3];
	        		 String betrag_umsatz=col[5];
	        		 
	        		 System.out.println ("Artikel: "+artikelID+" AVP "+einzelpreis + " verkauft: " +anzahl_abgaben+" gesamtbetrag: " +betrag_umsatz);
	        		 
	        		 //Buchungszeile hinzufügen wenn was verkauft wurde
	        		 buchungen.add_buchungszeile(artikelID, anzahl_abgaben, betrag_umsatz, false);
	        		 
	        	}
	        	
	        	
	        }
	        
	        	
	        		
	        			
	     }
	        return buchungen;
	            
	}

	public static void main(String[] args) 
	{
		Datei datei = new Datei ();
		try
		{
			Buchungen return_buchungen = datei.readFile("D:\\AUDIT.txt");
			System.out.println(return_buchungen.getText());
			
		}
		catch (Exception e)
		{
			
		}
	}

}
