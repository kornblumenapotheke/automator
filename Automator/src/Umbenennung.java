import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Lädt die Umbenennungstabelle, Format BezeichnungAutomat;PZN;Artikelname;Steuersatz
 * @author GuntherBackoffice
 *
 */
public class Umbenennung {
	//eine tabelle mit suchmaske interne bezueichung erstellen
	//lade umbenennungstabelle
	HashMap <String, Artikel> artikelListe;  
	HashMap <Integer, Artikel> num_artikelListe;
	
	Umbenennung (String in_Filename, boolean is_test)
	{
		artikelListe = new HashMap<String, Artikel>();
		num_artikelListe = new HashMap <Integer, Artikel>() ;
		artikelListe.put("TEST", new Artikel ("TEST","TEST","TEST","TEST","TEST"));
		artikelListe.put("TEST1", new Artikel ("TEST1","TEST1","TEST1","TEST1","TEST1"));
		System.out.println("ADDED");
		System.out.println("SIZE "+artikelListe.size());
		 for (int i = 0;i<artikelListe.size();i++)
	        {
	        	System.out.println(artikelListe.toString());
	        }
		 System.out.println(artikelListe.get("TEST").getCSV());
		 System.out.println(artikelListe.get("TEST1").getCSV());
		
		
		//file öffnen
		//csv lesen und Artikel anlegen
		
	}
	
	Umbenennung (String in_Filename) throws Exception
	{
		artikelListe = new HashMap<String, Artikel>();
		num_artikelListe = new HashMap <Integer, Artikel>() ;
		    BufferedReader br = null;
	        FileReader fr = null;
	        fr = new FileReader(in_Filename);
	        br = new BufferedReader(fr);
	        String l;
	        String separator =";";
	        
	        
	        while ((l = br.readLine()) != null)
	        {
	            // Zeilen anhand des Separators,
	            // z.B. ";", aufsplitten
	            String[] col = l.split(separator);
	            artikelListe.put(col[0],new Artikel(col[0],col[1],col[2],col[3],col[4]));
	            System.out.println(artikelListe.get(col[0]).getCSV());
	            
	        }
	        br.close();
	        System.out.println ("INIT ARTIKELLISTE"+artikelListe.size());
	        
	        
		
		//file öffnen
		//csv lesen und Artikel anlegen
		
	}
	/**
	 * Gibt Artikelobjekt zur entsprechenden internen Bezeichnung retour.
	 * @param inText
	 * @return
	 */
	public Artikel get (String inText)
	{
		return artikelListe.get(inText);
		//return new Artikel ();
	}
	
	public static void main(String[] args) throws Exception 
	{
		
		Properties prop = new Properties();
		String fileName = "src/settings/app.config";
		try (FileInputStream fis = new FileInputStream(fileName)) {
		    prop.load(fis);
		} catch (FileNotFoundException ex) {
		    
		} catch (IOException ex) {
		    
		}
		String umbenenner_filename = prop.getProperty("umbenenner.filename");
		
		Umbenennung umbenennung = new Umbenennung (umbenenner_filename);
	}

}
