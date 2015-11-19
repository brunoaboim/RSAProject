package rsa.encrypter;

import java.math.BigInteger;

import rsa.key.RSAKey;
import rsa.key.RSAKeyGenerator;
import rsa.key.RSAPublicKey;

public class RSAEncrypter {

	public static int[] encrypt(String message, RSAPublicKey publicKey) {
		char[] original = message.toCharArray();
		BigInteger[] encrypted = new BigInteger[original.length];
		int[] finalEncrypted = new int[original.length];
		int e = publicKey.getE();
		int n = publicKey.getN();
		for(int i = 0; i < original.length; i++) {
			int m = (int) (original[i]);
			encrypted[i] = new BigInteger("" + m);
			encrypted[i] = encrypted[i].pow(e);
			encrypted[i] = encrypted[i].mod(new BigInteger("" + n));
		}
		for(int i = 0; i < original.length; i++) {
			finalEncrypted[i] = encrypted[i].intValue();
		}
		return finalEncrypted;
	}
	
	public static void main(String[] args) {
		System.out.println("RSA Encryption Test Main");
		System.out.println("Original Message: 'Adamastor'");
		System.out.println("RSA Key Generator p and q: p = 61, q = 53 (Wikipedia example)");
		RSAKey[] keys = RSAKeyGenerator.generateKeys(61,53);
		System.out.println(keys[0]);
		System.out.println(keys[1]);
		int[] result = RSAEncrypter.encrypt("Nao tenho mais nada que fazer.", 
				(RSAPublicKey)keys[0]);
		String resultS = "[";
		for(int i = 0; i < result.length; i++) {
			resultS += " " + result[i];
		}
		resultS += " ]";
		System.out.println("Encrypted Message: " + resultS);
	}

}
