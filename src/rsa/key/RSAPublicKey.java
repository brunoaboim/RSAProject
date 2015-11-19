package rsa.key;

public class RSAPublicKey extends RSAKey {

	private int e;
	
	public RSAPublicKey(int e, int n) {
		super(n);
		this.e = e;
	}
	
	public int getE() {
		return e;
	}

	@Override
	public String toString() {
		return "PublicKey(" + e + "," + getN() + ")";
	}

}
