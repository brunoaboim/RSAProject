package rsa.decrypter;

import java.math.BigInteger;

import rsa.encrypter.RSAEncrypter;
import rsa.key.RSAKey;
import rsa.key.RSAKeyGenerator;
import rsa.key.RSAPrivateKey;
import rsa.key.RSAPublicKey;

public class RSADecrypter {

	public static String decrypt(int[] encryptedMessage, RSAPrivateKey privateKey) {
		BigInteger[] decrypted = new BigInteger[encryptedMessage.length];
		char[] finalDecrypted = new char[encryptedMessage.length];
		int d = privateKey.getD();
		int n = privateKey.getN();
		for(int i = 0; i < encryptedMessage.length; i++) {
			int m = encryptedMessage[i];
			decrypted[i] = new BigInteger("" + m);
			decrypted[i] = decrypted[i].pow(d);
			decrypted[i] = decrypted[i].mod(new BigInteger("" + n));
		}
		for(int i = 0; i < encryptedMessage.length; i++) {
			finalDecrypted[i] = (char)decrypted[i].intValue();
		}
		return new String(finalDecrypted);
	}
	
	public static void main(String[] args) {
		System.out.println("RSA Decryption Test Main");
		System.out.println("Original Message: 'Adamastor'");
		System.out.println("RSA Key Generator p and q: p = 61, q = 53 (Wikipedia example)");
		RSAKey[] keys = RSAKeyGenerator.generateKeys(61,53);
		System.out.println(keys[0]);
		System.out.println(keys[1]);
		int[] result = RSAEncrypter.encrypt("Adamastor", 
				(RSAPublicKey)keys[0]);
		String resultS = "[";
		for(int i = 0; i < result.length; i++) {
			resultS += " " + result[i];
		}
		resultS += " ]";
		System.out.println("Encrypted Message: " + resultS);
		System.out.println("Decrypted Message: " + 
				RSADecrypter.decrypt(result, (RSAPrivateKey)keys[1]));
	}

}
