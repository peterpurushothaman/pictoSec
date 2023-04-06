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
	
	public static user createUser(String idText, String passText, user u) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		u.username = idText;
		SecureRandom random = new SecureRandom();
		//creating a separate salt for the password and a new hash
		byte[] salt = new byte[64];
		u.passSalt = salt;
		random.nextBytes(salt);
		String str = createHash(passText, salt);
		u.passHash =  str;
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
	private static boolean verifyUsername(String username, user t) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		//dummy line to prevent error, this will actually be a check if the username entered is a valid entry in the hashmap
		String str1 = username;
		if(str1.compareTo(t.username) == 0) {
			System.out.println("Verified user ID");
			return true;
		}else {
			System.out.println("Could not verify user ID");
			return false;
		}
	}
	//function to verify the user password
	private static boolean verifyPassword(String password, byte[] passSalt, user t) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		String str1 = createHash(password, passSalt);
		if(str1.compareTo(t.passHash) == 0) {
			System.out.println("Verified user password");
			return true;
		}else {
			System.out.println("Could not verify user password");
			return false;
		}
	}
	public static boolean verifyLogin(String username, String password, user t) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		boolean check;
		user u = t;
		String str = username;
		check = verifyUsername(str, u);
		if(!check) {
			return false;
		}
		check = verifyPassword(password, u.passSalt, u);
		if(!check) {
			return false;
		}else{
			return true;
		}
	}
	
}