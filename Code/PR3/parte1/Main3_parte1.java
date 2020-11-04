package parte1;

//Beatriz Herguedas Pinedo
//Pablo Hern�ndez Aguado
//Jorge Villarrubia Elvira

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main3_parte1 {
	
	// n�mero de hilos
	private static final int m = 10000;
	// n�mero que sumamos
	private static final int n = 100;
	// variable que usamos
	private static int valor = 0;
	// sem�foro
	private static Semaphore semaforo = new Semaphore(1);

	public static void main(String[] args) throws InterruptedException {
		// creamos la lista de hilos
		List<Thread> hilos = new ArrayList<>();
		
		// creamos los hilos que van a sumar y los lanzamos
		for(int i = 0; i < m; i++) {
			int j = i;
			hilos.add(new Thread(()->suma(j)));
			hilos.get(i).start();
		}
		
		// creamos los hilos que van a restar y los lanzamos
		for(int i = m; i < 2*m; i++) {
			int j = i;
			hilos.add(new Thread(()->resta(j)));
			hilos.get(i).start();
		}
		
		// esperamos a todos los hilos
		for(int i = 0; i < 2*m; i++) {
			hilos.get(i).join();
		}
		
		System.out.println("Han terminado todos los hilos");
		System.out.println("El valor final de la variable es " + valor);
	}
	
	// cada hilo suma el valor n a la variable valor
	private static void suma(int j){
		for(int i = 0; i < n; i++) {
			try {
				// cojo el sem�foro (resta 1)
				semaforo.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			valor++;
			// suelto el sem�foro (suma 1)
			semaforo.release();
		}
	}
	
	// cada hilo resta el valor n a la variable valor
	private static void resta(int j){
		for(int i = 0; i < n; i++) {
			try {
				// cojo el sem�foro
				semaforo.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			valor--;
			// suelto el sem�foro
			semaforo.release();
		}
	}
}
