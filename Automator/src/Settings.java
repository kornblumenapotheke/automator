import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
	 private static String filename_settings="src/settings/app.config";
	 
	  static {
	    instance = new Settings();
	  }

	  private Settings() { 

		    prop = new Properties();
			
			try (FileInputStream fis = new FileInputStream(filename_settings)) {
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
