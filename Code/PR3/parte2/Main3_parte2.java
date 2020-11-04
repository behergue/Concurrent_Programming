package parte2;

//Beatriz Herguedas Pinedo
//Pablo Hern�ndez Aguado
//Jorge Villarrubia Elvira

import java.util.ArrayList;
import java.util.List;

public class Main3_parte2 {
	
	// n�mero de hilos
	private static final int m = 10;
	// almac�n
	private static Almacen almacen = new Implementa_almacen();

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
	
	// cada hilo productor almacena un valor que se corresponde con su n�mero de hilo (j)
	private static void productor(int j){
		// para producir infinitos productos
		while(true) {
			try {
				almacen.almacenar(new Producto(j));
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
				almacen.extraer();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
