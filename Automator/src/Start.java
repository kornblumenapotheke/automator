import java.awt.Color;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	
	
	
	private static void rittiBelegungPressed() throws Exception {
		String machineID=settings.get("machineId.ritti");
		//file laden
		String umbenenner_file_name = settings.get("umbenenner.filename_ritti");
		
		File file = new File(umbenenner_file_name);
		FileInputStream fis=null;;		
		fis = new FileInputStream(file);		
		byte[] data = new byte[(int) file.length()];		
		fis.read(data);		
		fis.close();		
		String csv = new String(data, "UTF-8");
		//datenbank alle alten auf invalid
		Connection con =  (java.sql.Connection) DatabaseConnection.getConnection();
		String sql = "";
		Statement statement = con.createStatement();
    	sql="UPDATE umbenenner SET is_valid=false WHERE machineID=\'"+machineID+"\';";
    	statement.executeUpdate(sql);    	
		//dieses schreiben
    	PreparedStatement pstmt = con.prepareStatement(
    			   "INSERT into umbenenner VALUES ('',?,?,?,true);");
    	pstmt.setString(1, machineID);
    	pstmt.setString(2, csv);
    	pstmt.setString(3, "???");
    	pstmt.execute();
		
	}

	private static void korniBelegungPressed() throws Exception {
		String machineID=settings.get("machineId.korni");
		//file laden
		String umbenenner_file_name = settings.get("umbenenner.filename_korni");
		
		File file = new File(umbenenner_file_name);
		FileInputStream fis=null;;		
		fis = new FileInputStream(file);		
		byte[] data = new byte[(int) file.length()];		
		fis.read(data);		
		fis.close();		
		String csv = new String(data, "UTF-8");
		//datenbank alle alten auf invalid
		Connection con =  (java.sql.Connection) DatabaseConnection.getConnection();
		String sql = "";
		Statement statement = con.createStatement();
    	sql="UPDATE umbenenner SET is_valid=false WHERE machineID=\'"+machineID+"\';";
    	statement.executeUpdate(sql);    	
		//dieses schreiben
    	PreparedStatement pstmt = con.prepareStatement(
    			   "INSERT into umbenenner VALUES ('',?,?,?,true);");
    	pstmt.setString(1, machineID);
    	pstmt.setString(2, csv);
    	pstmt.setString(3, "???");
    	pstmt.execute();
	}

	private static Object korniButtonPressed() throws Exception {
		// TODO Auto-generated method stub
		//parameter korni laden
		//Dann ab die post
		System.out.println ("KORNI SELECTED");
		Umbenennung umbenennung = new Umbenennung(settings.get("machineId.korni"));
		Datei datei = new Datei();
		datei.readFile(settings.get("audit_file_korni"),umbenennung);		
		datei.save_csv("");
		datei.copyEVA(settings.get("eva_file_korni"));
		datei.getZusammenfassung();
		
		datei.parseEVA(settings.get("eva_file_korni"),umbenennung);
		return null;
	}

	private static Object rittiButtonPressed() throws Exception {
		// TODO Auto-generated method stub
		//parameter korni laden
		//Dann ab die post
		Umbenennung umbenennung = new Umbenennung(settings.get("machineId.ritti"));
		System.out.println ("ritti SELECTED");
		Datei datei = new Datei();
		datei.readFile(settings.get("audit_file_ritti"),umbenennung);		
		datei.save_csv("");
		datei.copyEVA(settings.get("eva_file_ritti"));
		datei.getZusammenfassung();
		
		datei.parseEVA(settings.get("eva_file_ritti"),umbenennung);
		return null;
	}

	public static void main(String[] args) 
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
		JButton button_korni_csv = new JButton ("Korni Belegung laden");
		button_korni_csv.addActionListener(e->{
			try {
				korniBelegungPressed();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JButton button_ritti_csv = new JButton ("Ritti Belegung laden");
		button_ritti_csv.addActionListener(e->{
			try {
				rittiBelegungPressed();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JButton button_korni_refill = new JButton ("Korni Refill");
		button_korni_csv.addActionListener(e->{
			try {
				//korniButtonPressed();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JButton button_ritti_refill = new JButton ("Ritti Refill");
		button_korni_csv.addActionListener(e->{
			try {
				//korniButtonPressed();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//info ob Ritte oder Korni als Globale Variable??
		button_ritti.setBackground(Color.cyan);
		button_korni.setBackground(Color.gray);
		button_korni_refill.setBackground(Color.yellow);
		button_ritti_refill.setBackground(Color.yellow);
		button_korni_csv.setBackground(Color.orange);
		button_ritti_csv.setBackground(Color.orange);
		panel2.setLayout(new GridLayout(5,5));
		panel2.add(button_ritti);
		panel2.add(button_korni);
		panel2.add(button_korni_refill);
		panel2.add(button_ritti_refill);
		panel2.add(button_korni_csv);
		panel2.add(button_ritti_csv);
		
		
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

}
