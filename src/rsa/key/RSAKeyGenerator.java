package rsa.key;

import java.util.Random;

import rsa.auxilliar.Coprime;

public class RSAKeyGenerator {

	public static RSAKey[] generateKeys(int p, int q) {
		RSAKey[] keys = new RSAKey[2];
		//1
		int n = p * q;
		//2
		int z = (p-1) * (q-1);
		//3
		int e;
		int timer = 0;
		do {
			e = new Random().nextInt(z);
			timer++;
			if(timer >= 1000)
				throw new IllegalArgumentException("Values 'p' and 'q' are invalid.");
			System.out.println(timer + ":" + e);
		} while(!(Coprime.isCoprime(e,z) && e > 1 && e < z)); //Este while está mal!!!
		//4
		int d = 1;
		do {
			d++;
		} while(((e*d) % z) != 1.0);
		//5
		keys[0] = new RSAPublicKey(e,n);
		keys[1] = new RSAPrivateKey(d,n);
		return keys;
	}
	
	public static void main(String[] args) {
		System.out.println("RSA Key Generator Test Main (Wikipedia example)");
		System.out.println("p = 61, q = 53");
		RSAKey[] keys = RSAKeyGenerator.generateKeys(7,11);
		for(RSAKey key : keys) {
			System.out.println(key);
		}
	}

}
