
public class EvaDtsParser {
	
	EvaDtsParser (String inFile)
	{
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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
