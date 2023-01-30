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
		t.UID = 1111111111;
		System.out.println("This is the username before hashed: " + username);
		System.out.println("This is the password before hashed: " + password);
		System.out.println("This is the user ID: " + t.UID + "\n");
		userHash hash = new userHash();
		t = hash.createUser(username, password, t);
		System.out.println("This is the username after hashed: " + t.usernameHash);
		System.out.println("This is the password after hashed: " + t.passHash + "\n");
		/*here i use the username and password strings i created directly, but
		 * it would be easy to create a login page and read them from the user
		 * in the app.
		 */
		userHash.verifyUsername(username, t.usernameSalt, t);
		userHash.verifyPassword(password, t.passSalt, t);
	}

}
