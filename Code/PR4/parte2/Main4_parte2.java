package parte2;

//Beatriz Herguedas Pinedo
//Pablo Hernández Aguado
//Jorge Villarrubia Elvira

import java.util.ArrayList;
import java.util.List;

public class Main4_parte2 {
	
	// número de hilos
	private static final int m = 10;
	private static Monitor monitor = new Monitor();

	public static void main(String[] args) throws InterruptedException {
		// creamos la lista de hilos
		List<Thread> hilos = new ArrayList<>();
		
		// creamos los hilos productores
		for(int i = 0; i < m; i++) {
			int j = i;
			hilos.add(new Thread(()->productor(j)));
			hilos.get(i).start();
		}
		
		// creamos los hilos consumidores
		for(int i = m; i < 2*m; i++) {
			hilos.add(new Thread(()->consumidor()));
			hilos.get(i).start();
		}
		
	}
	
	// cada hilo productor almacena un valor que se corresponde con su número de hilo (j)
	private static void productor(int j){
		// para producir infinitos productos
		while(true) {
			try {
				monitor.producir(new Producto(j));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// cada hilo consumidor extrae un producto
	private static void consumidor(){
		// para consumir infinitos productos
		while(true) {
			try {
				monitor.consumir();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
