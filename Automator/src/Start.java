import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;



public class Start {
	
	private static Buchungen buchungen ;

	public static void main(String[] args) throws Exception 
	{
		buchungen = new Buchungen();
		//Fenster generieren
		Preferences pref = Preferences.userNodeForPackage(Start.class);
		
		String newValue = "D:\\\\owncloud\\\\1_APOTHEKE_Grafenstein_GZ\\\\LAUFENDER_BETRIEB\\\\Dokumente\\\\Impfstation Poggersdorf\\Archiv\\2022-01";
		String filename_selected = "";
		String file_path=newValue;
		
		Point loc_filechooser = new Point (100,100);
		Point loc_output = new Point (100,200);
		
		//load settings
		if (pref.get("path", "empty")=="empty")
		{
			System.out.println ("Node does not exist...");
			pref.put("path", newValue);
		
		}
		file_path=pref.get("path","empty");
		loc_filechooser = new Point(Integer.valueOf(pref.get("filechooserx","100")),Integer.valueOf(pref.get("filechoosery","100")));
		System.out.println (pref.get("filechooserx","XX100"));
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JTextArea text = new JTextArea();
		panel.add(text);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.setLocation(loc_output);
		frame.setTitle("Ergebnis");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        System.exit(0);
		    }
		});
		
		PrintStream someOtherStream = new PrintStream(System.out)
		{

			@Override
			public void println(String s) {
				
				text.setText(text.getText()+"\n"+s);
			}
		};
		System.setOut(someOtherStream);
		
		
		
		
		
		
		
		
		
        // JFileChooser-Objekt erstellen
        JFileChooser chooser = new JFileChooser();
        chooser.setLocation(loc_filechooser);
        chooser.setPreferredSize(new Dimension (1000,1000));
        // Dialog zum Oeffnen von Dateien anzeigen
        File f = new File(file_path);
        chooser.setCurrentDirectory(f);
        
        
        //chooser.setCurrentDirectory(null);
        int rueckgabeWert = chooser.showOpenDialog(null);
        
        /* Abfrage, ob auf "Öffnen" geklickt wurde */
        if(rueckgabeWert == JFileChooser.APPROVE_OPTION)
        {
             // Ausgabe der ausgewaehlten Datei
        	filename_selected = chooser.getSelectedFile().getName();
			System.out.println("Die zu öffnende Datei ist: "+filename_selected) ;
            File curDir = chooser.getCurrentDirectory();
            
            //props.setProperty(PREF_NAME, curDir.getCanonicalPath());
            filename_selected=curDir.getCanonicalPath()+"/"+filename_selected;  
            pref.put("path", curDir.getCanonicalPath());
            int loc_x=chooser.getLocation().x;
           // System.out.println("IS "+loc_x);
            int loc_y=chooser.getLocation().y;
            pref.put("filechooserx",Integer.toString(loc_x));
            pref.put("filechoosery",Integer.toString(loc_y));
            
        }
        else
        {
        	System.exit (101);
        }
        
        
        //Datei öffnen
        BufferedReader br = null;
        FileReader fr = null;
        fr = new FileReader(filename_selected);
        br = new BufferedReader(fr);
        String l;
        String separator ="\t";
        
        while (!(l=br.readLine()).contains("Column"))
        {
        	buchungen.add_header(l);        	
        }
        
        while ((l = br.readLine()) != null)
        {
            // Zeilen anhand des Separators,
            // z.B. ";", aufsplitten
            String[] col = l.split(separator);
            //System.out.println ("IN: "+l);
            String column = col[0];
            String einzelpreis = col[1];
            String gesamt_verkaeufe =col[2];
            String zaehler_verkaeufe =col[3];
            String gesamtbetrag =col[4];
            String bargeld = col[5];
            Float bankomat_menge = (Float.valueOf(gesamtbetrag)-Float.valueOf(bargeld))/Float.valueOf(einzelpreis);
            Float bankomat_betrag= Float.valueOf(gesamtbetrag)-Float.valueOf(bargeld);
            Float bar_menge=Float.valueOf(bargeld)/Float.valueOf(einzelpreis);
            Float bar_betrag=Float.valueOf(bargeld);
            String subcolumn = column.length() > 2 ? column.substring(column.length() - 2) : column;
            if (bar_menge > 0.1)
            		buchungen.add_buchungszeile(subcolumn, bar_menge, bar_betrag, false);
            if (bankomat_menge > 0.1)
            		buchungen.add_buchungszeile(subcolumn, bankomat_menge, bankomat_betrag, true);            
        }
        System.out.println (buchungen.get_header());
        
		//importieren
		//exportieren

	}

}
