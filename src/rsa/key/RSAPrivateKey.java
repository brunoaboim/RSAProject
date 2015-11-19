package rsa.key;

public class RSAPrivateKey extends RSAKey {

	private int d;
	
	public RSAPrivateKey(int d, int n) {
		super(n);
		this.d = d;
	}
	
	public int getD() {
		return d;
	}

	@Override
	public String toString() {
		return "PrivateKey(" + d + "," + getN() + ")";
	}

}
