import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.*;
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
	/**
	//ALTE VARIANTE WO VOM STICK EINGELESEN WIRD
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
	          //  System.out.println(artikelListe.get(col[0]).getCSV());
	            
	        }
	        br.close();
	        System.out.println ("INIT ARTIKELLISTE"+artikelListe.size());
	        
	        
		
		//file öffnen
		//csv lesen und Artikel anlegen
		
	} **/
	//LIEST DIREKT AUS DER DB
	 Umbenennung (String in_machineID) throws Exception
	{
		 
		//Hole aus DB CSV mit der machine ID und is valid
		
		Connection con =  (java.sql.Connection) DatabaseConnection.getConnection();
		String sql = "";
		Statement statement = con.createStatement();
    	sql="SELECT csv FROM umbenenner WHERE (machineID=? AND is_valid=true);";		
    	PreparedStatement pstmt = con.prepareStatement(
    			   sql);
    	pstmt.setString(1, in_machineID);    	
    	pstmt.execute();
    	ResultSet resultset = pstmt.getResultSet();

    	int i = 0;
    	while(resultset.next()) {
    	    i++;
    	}
    	if (i!=1)
    	{
    		//DIALOGBOX
    		  JFrame frame = new JFrame("ERROR CSV");
    	      
    	       JOptionPane.showMessageDialog(frame, "KEINE DEFINITION FÜR BELEGUNG! "+ in_machineID,"Error", JOptionPane.ERROR_MESSAGE);
    	 
    	       //frame.setSize(350,350);
    	       //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	       //frame.setVisible(true);
    	}
    	
    	resultset.first();
    	String in_csv = resultset.getString(1);
		
		artikelListe = new HashMap<String, Artikel>();
		num_artikelListe = new HashMap <Integer, Artikel>() ;
		    BufferedReader br = null;
	        StringReader reader = new StringReader(in_csv);
	        br = new BufferedReader(reader);
	        String l;
	        String separator =";";
	        
	        
	        while ((l = br.readLine()) != null)
	        {
	            // Zeilen anhand des Separators,
	            // z.B. ";", aufsplitten
	            String[] col = l.split(separator);
	            artikelListe.put(col[0],new Artikel(col[0],col[1],col[2],col[3],col[4]));
	          //  System.out.println(artikelListe.get(col[0]).getCSV());
	            
	        }
	        //br.close();
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
		if (artikelListe.get(inText)!=null)
			return artikelListe.get(inText);
		else
			return new Artikel(); //leerer Aertikel
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
		String umbenenner_filename = prop.getProperty("machineId.korni");
		
		Umbenennung umbenennung = new Umbenennung (umbenenner_filename);
	}

}
