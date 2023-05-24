import java.awt.GridLayout;

import javax.swing.*;

public class Refill 
{
	Refill(String inMachineId)
	{
		
	}
	
	public void start ()
	{
		String[] column_names= {"Menge","Bezeichnung","PZN"};
		Object[][] eintraege_test= {
										{"1","Zeile1","123456"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},
										{"2","Zeile2","234567"},										
										{"3","Zeile3","345678"}
									};
		
									
		JTable jTable = new JTable(eintraege_test,column_names);
		JFrame frame = new JFrame (); 
		JScrollPane jScrollPane=new JScrollPane(jTable);
		jTable.setFillsViewportHeight(true);
		//frame.getContentPane().add(panel2);
		frame.setVisible(true);
		frame.setSize(500, 500);
		//frame.setLocation(loc_output_2);
		frame.setTitle("Auswahl");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(2,2));
		
		JButton button_cancel = new JButton ("ABBRUCH");
		JButton button_ok = new JButton ("BUCHEN");
		
		frame.add(jScrollPane);
		frame.add(button_cancel);
		frame.add(button_ok);
		
	}

	public static void main (String[] args)
	{
		Refill refill = new Refill("");
		refill.start();
		
	}
}
