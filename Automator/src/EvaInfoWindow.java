import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class EvaInfoWindow {
	JFrame f;
    // Table
    JTable j;
    EvaInfoWindow()
    {
	    f = new JFrame();	 
	    // Frame Title
	    f.setTitle("INFOS");	 
	    // Data to be displayed in the JTable	 
	    j= new JTable(new DefaultTableModel(new Object[]{"Wert", "Anzahl","MAX"},0));
	    j.setBounds(300, 40, 500, 300);	   	 
	    // adding it to JScrollPane
	    JScrollPane sp = new JScrollPane(j);
	    f.add(sp);
	    // Frame Size
	    f.setSize(300, 200);
	    // Frame Visible = true
	    f.setVisible(true);	    
    }
	public void set2Euro(String stand_2_euro) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) j.getModel();
    	model.addRow(new Object[]{"2 Euro",stand_2_euro,"XXX"});	
	}
	public void set1Euro(String stand_1_euro) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) j.getModel();
    	model.addRow(new Object[]{"1 Euro",stand_1_euro,"XXX"});
		
	}
	public void set50Cent(String stand_050_euro) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) j.getModel();
    	model.addRow(new Object[]{"50 Cent",stand_050_euro,"XXX"});
		
	}
	public void set20Cent(String stand_020_euro) {
		DefaultTableModel model = (DefaultTableModel) j.getModel();
    	model.addRow(new Object[]{"20 Cent",stand_020_euro,"XXX"});
		// TODO Auto-generated method stub
		
	}
	public void set10Cent(String stand_010_euro) {
		DefaultTableModel model = (DefaultTableModel) j.getModel();
    	model.addRow(new Object[]{"10 Cent",stand_010_euro,"XXX"});
		
		// TODO Auto-generated method stub
		
	}
	public void setMachineID(String machineID) {
		// TODO Auto-generated method stub
		DefaultTableModel model = (DefaultTableModel) j.getModel();
    	model.addRow(new Object[]{"MACHINE ID",machineID,"XXX"});
		
    	if (machineID.equals ("VND001688745709"))
    	{
    		
        	model.addRow(new Object[]{"STANDORT","RITTERSPORN",""});
    	}
    	else
    	{
    		model.addRow(new Object[]{"STANDORT","KORNBLUME",""});
    	}
    		
		
	}
	public void show() {
		// TODO Auto-generated method stub
		
	}
}
