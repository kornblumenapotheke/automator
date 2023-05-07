import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Datei {
	
	String machineID = "NIX";
	String zeit="NIX";
	double umsatz_gesamt = 0.00;
	double bankomat_gesamt = 0.00;
	double bargeld_gesamt = 0.00;
	int anzahl_verkaeufe = 0;
	Buchungen buchungen = new Buchungen();
	
	Datei ()
	{
		
	}
	
	/**
	 * liest ein file ein und speichert es dann unter einem qualifizeirten namen wieder ab.
	 * @param inDateiname
	 * @return
	 * @throws Exception
	 */
	public Buchungen readFile (String inDateiname) throws Exception
	{
		    BufferedReader br = null;
	        FileReader fr = null;
	        fr = new FileReader(inDateiname);
	        br = new BufferedReader(fr);
	        String l;
	        String separator ="\t";
	        // Erste Zeile ist die ID der Machine
	        
	        machineID=br.readLine();
	        int posID=machineID.indexOf("VND");
	        machineID=machineID.substring(posID);
	        System.out.println ("ID MACHINE: "+machineID);
	        br.readLine();
	        
	        //Auslesezeit 
	        String zeit_tmp=br.readLine();
	        zeit=zeit_tmp.substring(8,18);
	        zeit=zeit+"_"+zeit_tmp.substring(28);
	        zeit=zeit.replace(":","_");
	        zeit=zeit.replace("/","_");
	        System.out.println ("ZEIT:" + zeit);
	        
	        //Datei sichern in entsprechendes Verzeichnis
	        
	        boolean bankomat_1_done = false; // Es gibt zwei Bankomateinträge. 1 ist der für normale Kunden, der insteresiert.
	        
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
	        		 String string_artikelID= col [0];
	        		 String string_einzelpreis=col[1];
	        		 String string_anzahl_abgaben=col[3];
	        		 String string_betrag_umsatz=col[5];
	        		 
	        		 System.out.println ("Artikel: "+string_artikelID+" AVP "+string_einzelpreis + " verkauft: " +string_anzahl_abgaben+" gesamtbetrag: " +string_betrag_umsatz);
	        		 
	        		 if (Double.parseDouble(string_anzahl_abgaben) > 0.0) //Buchungszeile hinzufügen wenn was verkauft wurde
	        		 {
	        			 buchungen.add_buchungszeile(string_artikelID, Double.parseDouble (string_anzahl_abgaben), Double.parseDouble(string_betrag_umsatz), false);
	        		 }
	        		// buchungen.add_buchungszeile(artikelID, anzahl_abgaben, betrag_umsatz, false);
	        		 
	        	}
	        	System.out.println ("INTERESSANTE BUCHUNGEN: "+buchungen.size());
	        	
	        	//jetzt noch Sicherung erstellen.
	        	Path src=Paths.get("D:\\AUDIT.txt");
	        	Path dest=Paths.get("D:\\"+machineID+"_"+zeit+"_AUDIT.txt");
	        	Files.copy(src, dest);
	        	
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
			//datei.copyFile("D:\\AUDIT.txt", "D:\\xxx\\"+datei.getMachineID()+"_"+datei.getZeit()+"AUDIT.txt");
			System.out.println(return_buchungen.getText());
			
		}
		catch (Exception e)
		{
			System.out.println (e.toString());
			
		}
	}
	
	private String getZeit() {
		// TODO Auto-generated method stub
		return zeit;
	}

	private String getMachineID() {
		// TODO Auto-generated method stub
		return machineID;
	}

	/**
	 * speichert das errechnete CSV in das entsprechende Verzeichnis
	 */

	public void save_csv(String in_filename) {
		// TODO Auto-generated method stub
		
		//speichere das erstellte csv in datei.
	}

	/**
	 * gibt die Zusammenfassung am Bildschirm aus.
	 */
	public void getZusammenfassung() {
		// TODO Auto-generated method stub
		//drucke die relevanten positionen am bildschirm aus.
		System.out.println (buchungen.getText());
		
	}

}
