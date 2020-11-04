package parte1;

public class Monitor {
	private int valor = 0;
	
	public int getValor() {
		return valor;
	}
	
	public synchronized void suma() {
		valor++;
	}

	public synchronized void resta() {
		valor--;
	}
}