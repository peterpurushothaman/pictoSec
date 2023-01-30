package pictoSec;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class userHash {
	
	public userHash() {
	}
	
	public user createUser(String idText, String passText, user u) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		SecureRandom random = new SecureRandom();
		//creating the salt and hash for the username
		byte[] salt1 = new byte[64];
		u.usernameSalt = salt1;
		random.nextBytes(salt1);
		String str = createHash(idText, salt1);
		u.usernameHash =  str;
		
		//creating a separate salt for the password and a new hash
		byte[] salt2 = new byte[64];
		u.passSalt = salt2;
		random.nextBytes(salt2);
		String str2 = createHash(passText, salt2);
		u.passHash =  str2;
		return u;
	}
	//creates the hash text to be stored (into database eventually)
	public static String createHash(String text, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		KeySpec spec = new PBEKeySpec(text.toCharArray(), salt, 65536, 128);
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		byte[] hash = factory.generateSecret(spec).getEncoded();
		String str = new String(hash);
		return str;
	}
	
	/*both of these functions pull data from the dummy main class, but eventually will pull from the database.
	 * likely, they will also return a value so we can re-prompt if the user enters information incorrectly,
	 * but for now, it is void*/
	
	//function to verify the username
	public static void verifyUsername(String username, byte[] IDsalt, user t) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		String str1 = createHash(username, IDsalt);
		if(str1.compareTo(t.usernameHash) == 0) {
			System.out.println("Verified user ID");
		}else {
			System.out.println("Could not verify user ID");
		}
	}
	//function to verify the user password
		public static void verifyPassword(String password, byte[] passSalt, user t) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
			String str1 = createHash(password, passSalt);
			if(str1.compareTo(t.passHash) == 0) {
				System.out.println("Verified user password");
			}else {
				System.out.println("Could not verify user password");
			}
		}
}