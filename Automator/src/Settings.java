import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

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
	 private static String package_filename_settings="src/settings/app.config"; //lokale settings
	 private static String filename_settings="app.config";
	 
	 
	  static {
	    instance = new Settings();
	  }

	  private Settings() { 

		    prop = new Properties();
		    String user_home = System.getProperty("user.home");
		    
		    //pr�fe ob settings bereits extistiert
		    
		    //nein, dann copy from package
		    
		   
				 
			
			
		    
			try (FileInputStream fis = new FileInputStream(package_filename_settings)) {
			    prop.load(fis);
				} 
				catch (Exception ex) {
					System.out.println (ex.toString());
			    
				} 
				 
			
	  }    

	  public static Settings getInstance() {
	    return instance;
	  }
	  
	  /**
	   * Gibt den entsprechenden Eintrag zur�ck
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
		  //pr�fe ob schon existiert
		  //wenn existiert dann return false
		  //wenn nicht existiert, dann setzen und return true;
		  return false;
	  }
	  /**
	   * setzt einen parameter ohne zu pr�fen ob er schon existiert, er wird �BERSCHRIEBEN!
	   * @param in_identifier
	   * @param in_value
	   */
	  public void overwrite(String in_identifier, String in_value)
	  {
		  prop.replace(in_identifier, in_value);
		  
	  }
	  
	  
	  

}
