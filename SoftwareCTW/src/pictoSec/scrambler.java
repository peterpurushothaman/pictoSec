package pictoSec;
import java.lang.*;
import java.util.*;

public class scrambler {

	private static char [] set1 = {'M', '!', 'z', 'd', '0', 'H', 't', 'E', 'i', '@', 'k', 'l', 'Y', 'n', '5', 'p', 'W', 'r', '8', 'g',
							'u', 'T', 'w', 'R', 'y', 'c', '&', 'B', '9', 'D', 'h', '4', 'G', 'f', 'I', 'J', '3', 'L', 'a', 'N', 'O', '$', 'Q', 'x', 'S', 'v',
							'U', '1', 'q', 'X', 'm', 'Z', 'e', '%', '2', 'K', 'F', 'o', '6', 'V', 's', 'C', 'b', 'j', '#', 'P', '7', 'A'};
	private static char [] set2 = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p', 'q', 'r', 's', 't',
			'u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P', 'Q', 'R', 'S', 'T',
			'U','V','W','X','Y','Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private static int setLength = 0;
	private static int passLength = 0;
	private static char[] set;
	
	public scrambler() {
	}
	
	public static String generatePassword(int setChoice, int length) {
		Random rand = new Random();
		int num;
		int index;
		char c;
		if(setChoice == 1) {
			set = set1;
		}else{
			set = set2;
		}
		setLength = set.length;
		passLength = length;
		String str = "";
		for(int i = 0; i < length; i++) {
			num = Math.abs(rand.nextInt());
			index = (num % set.length);
			c = set[index];
			str = str + c;
		}
		str = ensureStrength(str);
		return str;
	}
	private static String ensureStrength(String string) {
		String str = string;
		char[] specialC = {'!', '@', '#', '$', '%', '&'};
		Random rand = new Random();
		int num;
		int num2;
		char c;
		if(string.matches("[0-9]+")) {
			num = rand.nextInt(9);
			c = (char) ('0' + num);
			str = str.substring(0, string.length()-2) + c;
		}
		if(string.matches("!|@|#|$|%|&")) {
			num = (string.length() - 1)/2;
			num2 = rand.nextInt(6);
			c = specialC[num2];
			str = str.substring(0, num) + c + str.substring(num, string.length()-1);
		}
		return str;
	}
}
