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
	private static Settings settings;  
	public static void main(String[] args) throws Exception 
	{
		buchungen = new Buchungen();
		settings  = Settings.getInstance();
		//Fenster generieren
		
		
		
		
		//Point loc_filechooser = new Point (100,100);
		Point loc_output_1 = new Point (100,200);
		Point loc_output_2 = new Point (600,200);
		
		//load settings
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JTextArea text = new JTextArea();
		JScrollPane scroll = new JScrollPane (text, 
		JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setSize(500, 1000);

		frame.add(scroll);
		//panel.add(scroll);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		frame.setSize(500, 1500);
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
		//Actionlistener hinbzufügen
		button_ritti.addActionListener(e->{
			try {
				rittiButtonPressed();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//info ob Ritte oder Korni als Globale Variable??
		JButton button_korni = new JButton ("Korni");
		button_korni.addActionListener(e->{
			try {
				korniButtonPressed();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//info ob Ritte oder Korni als Globale Variable??
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
		
		       
		//importieren
		//exportieren

	}

	private static Object korniButtonPressed() throws Exception {
		// TODO Auto-generated method stub
		//parameter korni laden
		//Dann ab die post
		System.out.println ("KORNI SELECTED");
		Datei datei = new Datei();
		datei.readFile(settings.get("audit_file_korni"));
		
		datei.save_csv("");
		datei.getZusammenfassung();
		return null;
	}

	private static Object rittiButtonPressed() throws Exception {
		// TODO Auto-generated method stub
		//parameter korni laden
		//Dann ab die post
		System.out.println ("RITTI SELECTED");
		Datei datei = new Datei();
		datei.readFile(settings.get("audit_file_ritti"));
		datei.save_csv("");
		datei.getZusammenfassung();
		return null;
	}

}
