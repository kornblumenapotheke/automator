import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileSHA256 {
	
	public static String createSHA256 (String inFilename) throws IOException, NoSuchAlgorithmException
	{
		Path filePath = Path.of(inFilename);

		byte[] data = Files.readAllBytes(Paths.get(filePath.toUri()));
		byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
		return new BigInteger(1, hash).toString(16);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileSHA256 file = new FileSHA256();
		try {
			System.out.println(file.createSHA256("F:\\10_14061\\EVA_READ.VID"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
