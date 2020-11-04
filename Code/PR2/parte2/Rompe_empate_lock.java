package parte2;

import java.util.Arrays;

public class Rompe_empate_lock {
	// número de hilos
	private static int n;
	// array que lleva la etapa en la que está el proceso i
	private static volatile int[] in;
	// array que lleva en la etapa i quién es el último proceso que llegó
	private static volatile int[] last;
			
	// constructor
	public Rompe_empate_lock(int n) {
		Rompe_empate_lock.n = n;
		in = new int[2*n];
		last = new int[2*n];
		Arrays.fill(in, -1);
		Arrays.fill(last, -1);
	}

	// el i representa el número de proceso y el j la etapa por la que vamos
	public void coge_lock(int i) {
		
		for(int j = 0; j < 2*n; j++) {
			// el proceso i se encuentra en la etapa j
			in[i] = j;
			// para que se actualice
			in = in;
			// el último en llegar a la etapa j es i
			last[j] = i;
			// para que se actualice
			last = last;
			
			// para cada proceso
			for(int k = 0; k < 2*n; k++) {
				//distinto del proceso en el que estoy
				if(k != i) {
					// si está por delante de este proceso y este proceso es el último de su etapa
					// esperamos
					while(in[k] >= in[i] && last[j] == i) { }
				}
			}
		}
	}
	
	public void suelta_lock(int i) {
		in[i] = -1;
		in = in;
	}
}
