package parte2;

import java.util.ArrayList;
import java.util.List;

/* 	Provocar una condición de carrera. 
 * 
 * 	Programa concurrente en el que múltiples threads comparten y modifican una variable de tipo int 
 * 	de forma que el resultado final de la variable una vez que los threads terminan no sea el valor esperado.
 * 	Tendremos dos tipos de procesos, decrementadores e incrementadores, que realizan N decrementos e 
 * 	incrementos, respectivamente, sobre una misma variable (n) de tipo int inicializada a 0. 
 * 	El programa concurrente pondrá en marcha M procesos de cada tipo y una vez que todos los threads han
 * 	terminado, imprimirá el valor de la variable compartida. El valor final de la variable debería ser 0
 * 	ya que se habrán producido M * N decrementos (n--) y M * N incrementos (n++). Sin embargo, 
 * 	si dos operaciones (tanto de decremento como de incremento) se realizan a la vez, el resultado puede 
 * 	no ser el esperado (por ejemplo, dos incrementos podrían terminar por no incrementar la variable en 2).
 */

public class Main1_parte2 {
	
	// número de hilos
	private static final int m = 10000;
	// número que sumamos
	private static final int n = 100000;
	// variable que usamos
	private static int valor = 0;

	public static void main(String[] args) throws InterruptedException {
		// creamos la lista de hilos
		List<Thread> hilos = new ArrayList<>();
		
		// creamos los hilos que van a sumar y los lanzamos
		for(int i = 0; i < m; i++) {
			hilos.add(new Thread(()->suma()));
			hilos.get(i).start();
		}
		
		// creamos los hilos que van a restar y los lanzamos
		for(int i = m; i < 2*m; i++) {
			hilos.add(new Thread(()->resta()));
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
	private static void suma(){
		for(int i = 0; i < n; i++) {
			valor++;
		}
	}
	
	// cada hilo resta el valor n a la variable valor
	private static void resta(){
		for(int i = 0; i < n; i++) {
			valor--;
		}
	}
}
