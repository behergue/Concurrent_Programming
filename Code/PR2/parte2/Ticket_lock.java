package parte2;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

public class Ticket_lock {
	// array de enteros con el turno de cada posición
	private volatile int[] turno;
	// objeto de la clase AtomicInteger que garantiza exclusión mutua
	// se inicializa en 0 (primer turno)
	private volatile AtomicInteger numero = new AtomicInteger(0);
	// turno del siguiente (al que le toca entrar)
	private volatile int siguiente = 0;
	// número de procesos
	private volatile int m;
	
	// constructor
	public Ticket_lock(int m) {
		super();
		this.m = m;
		// lo inicializamos aquí porque necesitamos la m
		this.turno = new int[2*m];
		Arrays.fill(turno, -1);
	}
	
	public void coge_lock(int i) {
		// coge un número en exclusión mutua y deja el siguiente preparado
		turno[i] = numero.getAndAdd(1);
		// hacer en los arrays volátiles
		turno = turno;
		// mientras no sea el turno de este proceso, espera
		while(turno[i] != siguiente) {}
	}
	
	public void suelta_lock(int i) {
		// que entre el siguiente número
		siguiente++;
	}
}
