package parte3;

//Beatriz Herguedas Pinedo
//Pablo Hernández Aguado
//Jorge Villarrubia Elvira

import java.util.ArrayList;
import java.util.List;

public class Main4_parte3 {
	
	// número de hilos
	private static final int m = 100;
	// monitor
	private static Monitor monitor = new Monitor();

	public static void main(String[] args) throws InterruptedException {
		// creamos la lista de hilos
		List<Thread> hilos = new ArrayList<>();
		
		// creamos los hilos productores
		for(int i = 0; i < m; i++) {
			int j = i;
			// el j + 1 es para que al menos se produzca un producto
			hilos.add(new Thread(()->productor(j + 1)));
			hilos.get(i).start();
		}
		
		// creamos los hilos consumidores
		for(int i = m; i < 2*m; i++) {
			int j = i - m;
			// el j + 1 es para que al menos se consuma un producto
			hilos.add(new Thread(()->consumidor(j + 1)));
			hilos.get(i).start();
		}
		
	}
	
	// cada hilo productor produce j productos
	private static void productor(int j){
		// para producir infinitas veces
		while(true) {
			try {
				// creamos una lista y añadimos el producto j, j veces
				List<Producto> lista = new ArrayList<>();
				for(int i = 0; i < j; i++) {
					lista.add(new Producto(j));
				}
				monitor.producir(lista);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	// cada hilo consumidor consume j productos
	private static void consumidor(int j){
		// para consumir infinitos productos
		while(true) {
			try {
				monitor.consumir(j);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
