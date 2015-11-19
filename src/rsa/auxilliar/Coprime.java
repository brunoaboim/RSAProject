package rsa.auxilliar;

public class Coprime {

	private static int getGCDByModulus(int value1, int value2) {
	    while (value1 != 0 && value2 != 0) {
	        if (value1 > value2)
	            value1 %= value2;
	        else
	            value2 %= value1;
	    }
	    return Math.max(value1, value2);
	}
	
	public static boolean isCoprime(int value1, int value2) {
	    return getGCDByModulus(value1, value2) == 1;
	}

}
