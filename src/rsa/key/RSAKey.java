package rsa.key;

public abstract class RSAKey {

	private int n;
	
	public RSAKey(int n) {
		this.n = n;
	}
	
	public int getN() {
		return n;
	}
	
	@Override
	public abstract String toString();

}
