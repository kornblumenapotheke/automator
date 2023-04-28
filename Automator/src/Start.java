import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
		
		//Point loc_filechooser = new Point (100,100);
		Point loc_output_1 = new Point (100,200);
		Point loc_output_2 = new Point (600,200);
		
		//load settings
		if (pref.get("path", "empty")=="empty")
		{
			System.out.println ("Node does not exist...");
			pref.put("path", newValue);
		
		}
		file_path=pref.get("path","empty");
		loc_output_1 = new Point(Integer.valueOf(pref.get("output_1_x","100")),Integer.valueOf(pref.get("output_1_y","100")));
		loc_output_2 = new Point(Integer.valueOf(pref.get("output_2_x","600")),Integer.valueOf(pref.get("output_2_y","100")));
		//System.out.println (pref.get("filechooserx","XX100"));
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JTextArea text = new JTextArea();
		JScrollPane scroll = new JScrollPane (text, 
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setSize(500, 500);

		frame.add(scroll);
		//panel.add(scroll);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.setSize(500, 500);
		frame.setLocation(loc_output_1);
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
		
		
		//Zweites Window für Auswahl Ritti oder Korni
		JFrame frame2 = new JFrame();
		JPanel panel2 = new JPanel();
		JButton button_ritti = new JButton ("Ritti");
		JButton button_korni = new JButton ("Korni");
		button_ritti.setBackground(Color.cyan);
		button_korni.setBackground(Color.gray);
		panel2.setLayout(new GridLayout(2,2));
		panel2.add(button_ritti);
		panel2.add(button_korni);
		frame2.getContentPane().add(panel2);
		frame2.setVisible(true);
		frame2.setSize(500, 500);
		frame2.setLocation(loc_output_2);
		frame2.setTitle("Auswahl");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setVisible(true);
		
		
		
		
		
		
		
    
        
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
            String bankomat_menge = Float.toString((Float.valueOf(gesamtbetrag)-Float.valueOf(bargeld)/Float.valueOf(einzelpreis)));
            String bankomat_betrag=Float.toString( Float.valueOf(gesamtbetrag)-Float.valueOf(bargeld));
            String bar_menge=Float.toString(Float.valueOf(bargeld)/Float.valueOf(einzelpreis));
            String bar_betrag=bargeld;
            String subcolumn = column.length() > 2 ? column.substring(column.length() - 2) : column;
            buchungen.add_buchungszeile(subcolumn, bar_menge, bar_betrag, false);
            buchungen.add_buchungszeile(subcolumn, bankomat_menge, bankomat_betrag, true);            
        }
        System.out.println (buchungen.get_header());
        
		//importieren
		//exportieren

	}

}
