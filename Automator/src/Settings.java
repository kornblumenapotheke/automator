import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author Gunther
 *
 */
public class Settings {
	 private static Settings instance;
	 Properties prop;
	 /**
	  * 
	  */
	 private static String package_filename_settings="settings/app.config"; //lokale settings
	 private static String filename_settings="app.config";
	 
	 
	  static {
	    instance = new Settings();
	  }

	  private Settings() { 

		    prop = new Properties();
		    String user_home = System.getProperty("user.home");
		    
		    //prüfe ob settings bereits extistiert
		    
		    //nein, dann copy from package
		    
		   //prop.load(new FileInputStream(getClass().getResource("/src/settings/app.config").toURI()));
				 
			
			
		    /**
			try (FileInputStream fis = new FileInputStream(package_filename_settings)) {
			    prop.load(fis);
				} 
				catch (Exception ex) {
					System.out.println ("SETTINGS ERROR:"+ex.toString());
					JFrame frame = new JFrame(); 
	        		JOptionPane.showMessageDialog(frame, "SETTINGS ERROR:"+ex.toString());
			    
				}
			**/
		    try 
		    {
		    	InputStream in = getClass().getResourceAsStream(package_filename_settings);
		    	//BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		    	prop.load(in);
		    	    // Use resource
		    }
		    catch (Exception ex)
		    {
		    	System.out.println ("SETTINGS ERROR:"+ex.toString());
				JFrame frame = new JFrame(); 
        		JOptionPane.showMessageDialog(frame, "SETTINGS ERROR:"+ex.toString());
		    
		    	
		    }
		    
				 
			
	  }    

	  public static Settings getInstance() {
	    return instance;
	  }
	  
	  /**
	   * Gibt den entsprechenden Eintrag zurück
	   * @param in_value
	   * @return
	   */
	  public String get (String in_value)
	  {
		  return prop.getProperty(in_value, "LEER");
		  
	  }
	  
	  /**
	   * setting a value.
	   * @param in_identifier
	   * @param in_value
	   * @return false=existiert bereits true=gesetzt
	   */
	  public boolean set (String in_identifier, String in_value)
	  {
		  if (!prop.containsKey(in_identifier))
		  {
			  prop.setProperty(in_identifier, in_value);
			  return true;
		  }
		  //prüfe ob schon existiert
		  //wenn existiert dann return false
		  //wenn nicht existiert, dann setzen und return true;
		  return false;
	  }
	  /**
	   * setzt einen parameter ohne zu prüfen ob er schon existiert, er wird ÜBERSCHRIEBEN!
	   * @param in_identifier
	   * @param in_value
	   */
	  public void overwrite(String in_identifier, String in_value)
	  {
		  prop.replace(in_identifier, in_value);
		  
	  }
	  
	  
	  

}
