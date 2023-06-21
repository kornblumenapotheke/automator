

	// Packages to import
	import javax.swing.JFrame;
	import javax.swing.JScrollPane;
	import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
	 
	public class EVATable {
	    // frame
	    JFrame f;
	    // Table
	    JTable j;
	 
	    EVATable ()
	    {
	    	// Frame initialization
	        f = new JFrame();	 
	        // Frame Title
	        f.setTitle("VERKÄUFE");	 
	        // Data to be displayed in the JTable	 
	        j= new JTable(new DefaultTableModel(new Object[]{"ZHLG", "MENGE","PZN","PRODUKT","EP","Preis"},0));
	        j.setBounds(30, 40, 300, 600);	   	 
	        // adding it to JScrollPane
	        JScrollPane sp = new JScrollPane(j);
	        f.add(sp);
	        // Frame Size
	        f.setSize(800, 300);
	        // Frame Visible = true
	        f.setVisible(true);	    
	    }
	    // Constructor
	    
	    // Driver  method
	    
	    public void addRow (String inZahlung, String inMenge, String inPZN, String inProdukt, String inEP, String inPreis)
	    {
	    	DefaultTableModel model = (DefaultTableModel) j.getModel();
	    	model.addRow(new Object[]{inZahlung, inMenge, inPZN, inProdukt, inEP, inPreis});	
	    }
	    
	    public static void main(String[] args)
	    {
	        new EVATable();
	    }
	}

