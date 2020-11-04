package parte1;
import java.util.ArrayList;
import java.util.List;

/*  Creación de procesos (threads).
 * 
 * 	Programa concurrente que arranca N procesos (threads) y termina cuando los N threads terminen.
 *  A cada thread se le asigna un identificador único. Todos los threads realizan el mismo trabajo: 
 *  imprimir su identificador, dormir durante T milisegundos y terminar imprimiendo su identificador. 
 *  El thread principal además de poner en marcha los procesos, debe imprimir una línea avisando de 
 *  que todos los threads han terminado una vez lo hayan hecho. Cada ejecución lleva a resultados diferentes.
 */

public class Main1_parte1 {
	
	// número de hilos
	private static final int n = 10;
	// tiempo de espera
	private static final int t = 1000;

	public static void main(String[] args) throws InterruptedException {
		// creamos la lista de hilos
		List<Thread> hilos = new ArrayList<>();
		
		// creamos los hilos y los lanzamos
		for(int i = 0; i < n; i++) {
			hilos.add(new Thread(()->inicia()));
			hilos.get(i).start();
		}
		
		// esperamos a todos los hilos
		for(int i = 0; i < n; i++) {
			hilos.get(i).join();
		}
		
		System.out.println("Han terminado todos los hilos");
	}
	
	
	private static void inicia(){
		// hilo actual
		Thread myHilo = Thread.currentThread();
		// muestra la id del hilo
		System.out.println("Se abre el hilo " + myHilo.getId());
		
		try {
			// el hilo duerme un rato
			Thread.sleep(t);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// muestra la id del hilo otra vez
		System.out.println("Se cierra el hilo " + myHilo.getId());
	}
}
