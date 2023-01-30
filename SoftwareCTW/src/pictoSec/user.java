/* Basic user class to store the credentials as they are generated
 * and then store them into the database easily
 */
package pictoSec;

public class user {
	public String usernameHash;
	public byte[] usernameSalt;
	public String passHash;
	public byte[] passSalt;
	public int UID;
	
	//constructor
	public user() {
	}
}

