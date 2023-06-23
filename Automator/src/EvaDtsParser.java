import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EvaDtsParser {
	String file_content;
	String last_transaction;
	String machineID ="N/A";
	String machineModel ="N/A";
	String machineBuild ="N/A";
	String stand_2_euro="0";
	String stand_1_euro="0";
	String stand_050_euro="0";
	String stand_020_euro="0";
	String stand_010_euro="0";
	
	MyArrayList<Buchungszeile> buchungen = new MyArrayList<Buchungszeile> ();
	
	EvaDtsParser (String inFile, String inProduktliste)
	{
		Pattern pattern_machineID =Pattern.compile("CB1[*](.*?)[*]");
		Pattern pattern_machineModel =Pattern.compile("CB1[*].*[*](.*?)[*]");	
		Pattern pattern_machineBuild =Pattern.compile("CB1[*].*[*].*[*](.*$?)");
		Pattern pattern_2_euro =Pattern.compile("CA17[*].*[*]200[*](.*?)[*]");
		Pattern pattern_1_euro =Pattern.compile("CA17[*].*[*]100[*](.*?)[*]");
		Pattern pattern_050_euro =Pattern.compile("CA17[*].*[*]50[*](.*?)[*]");
		Pattern pattern_020_euro =Pattern.compile("CA17[*].*[*]20[*](.*?)[*]");
		Pattern pattern_010_euro =Pattern.compile("CA17[*].*[*]10[*](.*?)[*]");
		//Pattern pattern_geschaeftsfall =Pattern.compile("PA7[*](.*?)[*].*[*].*[*].*[*].*[*][1][*].*",Pattern.MULTILINE); //diese regex passt noch nicht
		Pattern pattern_geschaeftsfall =Pattern.compile("LS[*]0100(.*?)");
		Pattern pattern_column=Pattern.compile("PA7[*](.*?)[*]");
		Pattern pattern_anzahl=Pattern.compile("PA7[*].*[*].*[*].*[*].*[*].*[*].*[*](.*?)[*]");
		Pattern pattern_betrag=Pattern.compile("PA7[*].*[*].*[*].*[*].*[*].*[*].*[*].*[*](.*$?)");
		
		
		
		Path filePath = Path.of(inFile);
		try {
			file_content = Files.readString(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int index_last_transaction = file_content.lastIndexOf("DXS");
		System.out.println("ID: "+index_last_transaction);
		
		last_transaction=file_content.substring(index_last_transaction);
		
		Matcher matcher = pattern_machineID.matcher(last_transaction);
		if (matcher.find()) {
			System.out.println(matcher.group(1));
			machineID=matcher.group(1);			
			}
		matcher=pattern_machineModel.matcher(last_transaction);
		if (matcher.find()) {
			System.out.println(matcher.group(1));
			machineModel=matcher.group(1);			
			}
		matcher=pattern_machineBuild.matcher(last_transaction);
		if (matcher.find()) {
			System.out.println("build:"+matcher.group(1));
			machineBuild=matcher.group(1);			
			}
		matcher=pattern_2_euro.matcher(last_transaction);
		if (matcher.find()) {
			System.out.println("2€: "+matcher.group(1));
			stand_2_euro=matcher.group(1);			
			}

		matcher=pattern_1_euro.matcher(last_transaction);
		if (matcher.find()) {
			System.out.println("1€: "+matcher.group(1));
			stand_1_euro=matcher.group(1);			
			}
		
		matcher=pattern_050_euro.matcher(last_transaction);
		if (matcher.find()) {
			System.out.println("0,50€: "+matcher.group(1));
			stand_050_euro=matcher.group(1);			
			}
		matcher=pattern_020_euro.matcher(last_transaction);
		if (matcher.find()) {
			System.out.println("0,20€: "+matcher.group(1));
			stand_020_euro=matcher.group(1);			
			}
		matcher=pattern_010_euro.matcher(last_transaction);
		if (matcher.find()) {
			System.out.println("0,10€: "+matcher.group(1));
			stand_010_euro=matcher.group(1);			
			}
		
		EvaInfoWindow evaInfoWindow = new EvaInfoWindow ();
		evaInfoWindow.set2Euro (stand_2_euro);
		evaInfoWindow.set1Euro (stand_1_euro);
		evaInfoWindow.set50Cent (stand_050_euro);
		evaInfoWindow.set20Cent (stand_020_euro);
		evaInfoWindow.set10Cent (stand_010_euro);
		evaInfoWindow.setMachineID (machineID);
		
		evaInfoWindow.show();
		
		//hier jetzt treffer suchen
		
		String lines[] = last_transaction.split(System.getProperty("line.separator"));
		System.out.println ("LINES: "+lines.length);
		
		for (int i = 0;i<lines.length;i++)
		{
			String zeile = lines[i];
			//BANKOMATABHANDLUNG
			if (zeile.contains("DA*1") && !((zeile.substring(zeile.length()-2)).equals("*0"))) //banko
			{
				String column="00";
				String anzahl="0";
				String betrag="0";
				
				//System.out.println(zeile.substring(zeile.length()-2)+"==>"+zeile);
				// Es interessiert: *XX* => Column, ...*MENGE*SUMME
				//COL
				matcher=pattern_column.matcher(zeile);
				if (matcher.find()) {
					//System.out.println("Column: "+matcher.group(1));
					column=matcher.group(1);			
					}
				//ANZAHL
				matcher=pattern_anzahl.matcher(zeile);
				if (matcher.find()) {
					//System.out.println("Anzahl: "+matcher.group(1));
					anzahl=matcher.group(1);			
					}
				
				//BETRAG
				matcher=pattern_betrag.matcher(zeile);
				if (matcher.find()) {
					//System.out.println("Betrag: "+matcher.group(1));
					betrag=matcher.group(1);			
					}
				
				try {
					buchungen.add(new Buchungszeile(column, Double.parseDouble(betrag)/100, Double.parseDouble(anzahl), true, inProduktliste));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			//CASH ABHANDLUNG
			
			if (lines[i].contains("CA*0")&& !((zeile.substring(zeile.length()-2)).equals("*0"))) //cash
			{
				String column="00";
				String anzahl="0";
				String betrag="0";
				
				System.out.println(zeile.substring(zeile.length()-2)+"==>"+zeile);
				// Es interessiert: *XX* => Column, ...*MENGE*SUMME
				//COL
				matcher=pattern_column.matcher(zeile);
				if (matcher.find()) {
					System.out.println("Column: "+matcher.group(1));
					column=matcher.group(1);			
					}
				//ANZAHL
				matcher=pattern_anzahl.matcher(zeile);
				if (matcher.find()) {
					System.out.println("Anzahl: "+matcher.group(1));
					anzahl=matcher.group(1);			
					}
				
				//BETRAG
				matcher=pattern_betrag.matcher(zeile);
				if (matcher.find()) {
					System.out.println("Betrag: "+matcher.group(1));
					betrag=matcher.group(1);			
					}
				
				try {
					buchungen.add(new Buchungszeile(column, Double.parseDouble(betrag)/100, Double.parseDouble(anzahl), false,inProduktliste));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		System.out.println (buchungen.getBuchungenAsText());
		
		
		
		//lade File
		//speiche Datei in Datenbank wenn nicht schon existent
		//lies Maschinennummer, Modell, SW
		//Ser # ID1-01, Machine Model ID1-02, Machine Build ID1-03
		//Münzzähler CA17 01 => Rolle, CA17 02 Wert, CA17 03 => Füllstand
		//CASHLESS DA, Münzen TA
		//LS*0100 START GESCHÄFTSFALL
			//PA7
		
		//lies jeden einzelnen Geschäftsfall (beginnt mit LS*0100)
			//lies event time
			//lies betroffenen Artikel
			//lies welche Zahlungsart
		//speichere in Datenbank wenn nicht schon geschehen
		
	}

	private void parse_geschaeftsfall(String in_geschaeftsfall) 
	{
		// TODO Auto-generated method stub
		System.out.println (in_geschaeftsfall);
	}

	public static void main(String[] args) 
	{
	//EvaDtsParser eva = new EvaDtsParser("F:\\10_14061\\EVA_READ.VID",settings.get("machineId.korni")); 	

	}

	public void showtable() {
		
		EVATable evatable = new EVATable();
		
		
		for (int i=0;i<buchungen.size();i++)
		{
			Buchungszeile buchungszeile = buchungen.get(i);
			evatable.addRow(buchungszeile.getZahlungsweise(), buchungszeile.getMenge(), buchungszeile.getPzn(), buchungszeile.getProdukt(), buchungszeile.getEP(), buchungszeile.getSumme());
			
			
		}
		
		// TODO Auto-generated method stub
		
	}

}
