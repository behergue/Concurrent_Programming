package parte1;

//Beatriz Herguedas Pinedo
//Pablo Hernández Aguado
//Jorge Villarrubia Elvira

import java.util.ArrayList;
import java.util.List;

// incremento-decremento
public class Main4_parte1 {
	
	// número de hilos
	private static final int m = 10000;

	public static void main(String[] args) throws InterruptedException {
		Monitor monitor = new Monitor();
		
		// creamos la lista de hilos
		List<Thread> hilos = new ArrayList<>();
		
		// creamos los hilos que van a sumar y los lanzamos
		for(int i = 0; i < m; i++) {
			hilos.add(new Thread(()->monitor.suma()));
			hilos.get(i).start();
		}
		
		// creamos los hilos que van a restar y los lanzamos
		for(int i = m; i < 2*m; i++) {
			hilos.add(new Thread(()->monitor.resta()));
			hilos.get(i).start();
		}
		
		// esperamos a todos los hilos
		for(int i = 0; i < 2*m; i++) {
			hilos.get(i).join();
		}
		
		System.out.println("Han terminado todos los hilos");
		System.out.println("El valor final de la variable es " + monitor.getValor());
	}
}
