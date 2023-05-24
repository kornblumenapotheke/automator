import java.io.BufferedReader;
import java.io.*;
import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.*;
import javax.swing.JOptionPane;

//import org.mariadb.jdbc.Statement;

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
	
	public void copyEVA (String inDateiname) throws Exception
	{
		Connection con =  (java.sql.Connection) DatabaseConnection.getConnection();
		boolean bereitsGelesen =false;
		BufferedReader br = null;
		String sha256 = FileSHA256.createSHA256(inDateiname);
		//schauen ob schlüssel schon in db
		Statement statement = con.createStatement();
    	String sql="SELECT * from EVA where (SHA256=\'"+sha256+"\');";
    	ResultSet result = statement.executeQuery(sql);
    	if (result.next() == false) //leer, existiert noch nicht
    	{
    		//EVA öffnen
    		//EVA einfach parsen
    		//schreiben
    		File file = new File(inDateiname);
   			FileInputStream fis = new FileInputStream(file);
   			PreparedStatement pstmt = con
   					.prepareStatement("insert into EVA values ( \'\',\'DATUM\',\'ZEIT\',\'ID\', ?,\'"+sha256+"\')");
   			pstmt.setBinaryStream(1, fis, (int) file.length());
   			pstmt.executeUpdate();
   			JFrame frame = new JFrame(); 
    		JOptionPane.showMessageDialog(frame, "EVA in Datenbank GESICHERT!");
    		
    	}
		 
    	else
    	{
    		JFrame frame = new JFrame(); 
    		JOptionPane.showMessageDialog(frame, "EVA bereits in Datenbank. Keine Sicherungskopie!");
    		
    	}
			
	}
	
	/**
	 * liest ein file ein und speichert es dann unter einem qualifizeirten namen wieder ab.
	 * @param inDateiname
	 * @return
	 * @throws Exception
	 */
	public Buchungen readFile (String inDateiname) throws Exception
	{
			Connection con =  (java.sql.Connection) DatabaseConnection.getConnection();
			boolean bereitsGelesen =false;
			String transaction_key_id ="";
		    BufferedReader br = null;
	        FileReader fr = null;
	        fr = new FileReader(inDateiname);
	        br = new BufferedReader(fr);
	        String sql = "";
	        String l;
	        String separator ="\t";
	        // Erste Zeile ist die ID der Machine
	        
	        
	        machineID=br.readLine();
	        int posID=machineID.indexOf("VND");
	        machineID=machineID.substring(posID);
	        System.out.println ("ID MACHINE: "+machineID);
	      //jetzt noch Sicherung erstellen.
        	
	        br.readLine();
	        
	        //Auslesezeit 
	        String zeit_tmp=br.readLine();
	        zeit=zeit_tmp.substring(8,18);
	        zeit=zeit+"_"+zeit_tmp.substring(28);
	        zeit=zeit.replace(":","_");
	        zeit=zeit.replace("/","_");
	        System.out.println ("ZEIT:" + zeit);
	        transaction_key_id=machineID+"_"+zeit;
	        Path src=Paths.get(inDateiname);
        	Path dest=Paths.get("F:\\"+machineID+"_"+zeit+"_AUDIT.txt");
        	try
        	{
        		Files.copy(src, dest);
        	}
        	catch (Exception e)
        	{
        		JFrame frame = new JFrame(); 
        		JOptionPane.showMessageDialog(frame, "Datei wurde bereits eingelesen!Keine Sicherungskopie!");
        		bereitsGelesen=true;
        	}
        	
        	//datenbank abfragen nach id
        	Statement statement = con.createStatement();
        	sql="SELECT * from KeyRead where (key_stick=\'"+transaction_key_id+"\');";
        	ResultSet result = statement.executeQuery(sql);
        	String day = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
		    String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        	if (result.next() == false)
        	{
            	//existiert nicht
            	//eintragen
        	sql="INSERT INTO KeyRead VALUES (\'\',\'"+transaction_key_id+"\',\'"+day+"\',\'"+time+"\');";
        	
        	statement = con.createStatement();
        	try
        	{
        		statement.executeUpdate(sql);
        	}
        	catch (Exception e)
        	{
        		
        	}
        	System.out.println(sql);
        	bereitsGelesen = false;
            	//bereitsgelesen false

        		
        	}
        	else 
        	{
        		bereitsGelesen=true;
        		JFrame frame = new JFrame(); 
        		JOptionPane.showMessageDialog(frame, "Datei wurde bereits eingelesen! Kein Eintrag in Datenbank!");
        	}
	        
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
	        		 String string_artikelID= col[0].substring(7);
	        		 String string_einzelpreis=col[1];
	        		 String string_anzahl_abgaben=col[3];
	        		 String string_betrag_umsatz=col[5];
	        		 
	        		 System.out.println ("Artikel: "+string_artikelID+" AVP "+string_einzelpreis + " verkauft: " +string_anzahl_abgaben+" gesamtbetrag: " +string_betrag_umsatz);
	        		 
	        		 if (Double.parseDouble(string_anzahl_abgaben) > 0.0) //Buchungszeile hinzufügen wenn was verkauft wurde
	        		 {
	        			 buchungen.add_buchungszeile(string_artikelID, Double.parseDouble (string_anzahl_abgaben), Double.parseDouble(string_betrag_umsatz), false);
	        			
	        			sql="";
	        			sql+="\'\',";
	     	        	sql+="\'"+machineID+"\',";
	     	        	sql+="\'"+day+"\',";
	     	        	sql+="\'"+time+"\',";
	     	        	sql+="\'"+col[0]+"\',";
	     	        	sql+="\'"+col[1]+"\',";
	     	        	sql+="\'"+col[2]+"\',";
	     	        	sql+="\'"+col[3]+"\',";
	     	        	sql+="\'"+col[4]+"\',";
	     	        	sql+="\'"+col[5]+"\',";
	     	        	sql+="\'NOT IMPLEMENTED\',";
	     	        	sql+="\'NOT IMPLEMENTED\'";
	     	        	sql = "INSERT into FaelleStick VALUES ("+sql+");"; 
	     	        	statement = con.createStatement();
	     	        	if (!bereitsGelesen)
	     	        		statement.executeUpdate(sql);
	     	        	
	     	        	
	     	        	
	        		 }
	        		// buchungen.add_buchungszeile(artikelID, anzahl_abgaben, betrag_umsatz, false);
	        		 
	        	}
	        	System.out.println ("INTERESSANTE BUCHUNGEN: "+buchungen.size());
	        	
	        	
	        	
		        
	        	
	        	
		        
	        	
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
