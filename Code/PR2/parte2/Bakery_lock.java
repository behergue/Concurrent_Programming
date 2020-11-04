package parte2;

import java.util.Arrays;

import javafx.util.Pair;

public class Bakery_lock {
	
	// array de enteros con el turno de cada posici�n
	private volatile int[] turno;
	// n�mero de procesos
	private volatile int m;
	
	// constructor
	public Bakery_lock(int m) {
		super();
		this.m = m;
		turno = new int[2*m];
		Arrays.fill(turno, -1);
	}
	
	public void coge_lock(int i) {
		// para marcar a los que est�n interesados en entrar (y que no lleven el valor -1)
		turno[i] = 0;
		turno = turno;
		// para convertirlo a vector, coger el m�ximo y que lo devuelva en entero
		// el +1 es para coger el turno siguiente
		turno[i] = Arrays.stream(turno).max().getAsInt() + 1;
		turno = turno;
		// para cada proceso
		for(int j = 0; j < 2*m; j++) {
			// distinto del proceso en el que estamos
			if(j != i) {
				// mientras el otro proceso con el que me comparo quiera entrar en la secci�n 
				// cr�tica y yo sea mayor que �l, me espero
				while(turno[j] != -1 && 
						mayor(new Pair<Integer, Integer>(turno[i], i), new Pair<Integer, Integer>(turno[j], j))) {}
			}
		}
	}
	
	public void suelta_lock(int i) {
		turno[i] = -1;
		turno = turno;
	}
	
	private boolean mayor(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
		return ((p1.getKey() > p2.getKey()) || (p1.getKey() == p2.getKey() && p1.getValue() > p2.getValue()));
	}
}
