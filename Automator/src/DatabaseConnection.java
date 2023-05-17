import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

//import org.mariadb.jdbc.*;
import org.apache.commons.dbcp2.*;
import java.sql.*;


public class DatabaseConnection {

	 private static BasicDataSource ds = new BasicDataSource();
	    
	    static {
	        ds.setUrl("jdbc:mariadb://10.139.1.20:3306/verkaufsautomat");
	        ds.setUsername("verkaufsautomat");
	        ds.setPassword("v1erkaufsautomat23!");
	        ds.setMinIdle(5);
	        ds.setMaxIdle(10);
	        ds.setMaxOpenPreparedStatements(100);
	    }
	    
	    public static Connection getConnection() throws SQLException {
	        Connection con =  ds.getConnection();
	     // create a Statement from the connection
	        Statement statement =  con.createStatement();

	        // insert the data
	        String day = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());
	        String time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
	        statement.executeUpdate("INSERT INTO Connections " + "VALUES (\'"+day+"\',\'"+time+"\',\'"+ComputerName.getComputerName()+"\',\'X\',\'\');");
	        return con;
	    }
	    
	    private DatabaseConnection(){ }
    
	public static void main(String[] args) {
		 String url = "jdbc:mariadb://10.139.1.20:3306/verkaufsautomat";
		 String username = "verkaufsautomat";
		 String password = "v1erkaufsautomat23!";

		 System.out.println("Connecting database...");
		
		 try (Connection connection = DriverManager.getConnection(url, username, password)) {
		     System.out.println("Database connected!");
		 } catch (SQLException e) {
		     throw new IllegalStateException("Cannot connect the database!", e);
		 }
		 
		
		 try {
		DatabaseConnection.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
