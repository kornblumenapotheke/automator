import java.util.Map;

public class ComputerName {

	static String getComputerName()
	{
		
		    Map<String, String> env = System.getenv();
		    if (env.containsKey("COMPUTERNAME"))
		        return env.get("COMPUTERNAME");
		    else if (env.containsKey("HOSTNAME"))
		        return env.get("HOSTNAME");
		    else
		        return "Unknown Computer";
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println (ComputerName.getComputerName());
	}

}
