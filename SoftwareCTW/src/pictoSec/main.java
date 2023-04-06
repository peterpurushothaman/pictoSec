package pictoSec;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class main {
/* Dummy main class to simply test the other classes
 * Hashes all the "user info" (lol), then verifies it
 * This class will likely not exist in the application, just for testing and prototyping
 */
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException {
		String username = "johnSmith";
		String password = "strongPassword";
		user t = new user();
		t = userHash.createUser(username, password, t);
		boolean test = userHash.verifyLogin(username, password, t);
		String foo;
		for(int i  = 0; i < 10; i++) {
			foo = scrambler.generatePassword(1, 9);
			System.out.println("TestPassword: " + foo);
		}
	}

}
