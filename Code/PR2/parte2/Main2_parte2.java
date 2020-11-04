package parte2;

//Javier Guzmán Muñoz
//Beatriz Herguedas Pinedo
//Pablo Hernández Aguado
//Jorge Villarrubia Elvira

import java.util.ArrayList;
import java.util.List;

public class Main2_parte2 {
	
	// número de hilos
	private static final int m = 20;
	// número que sumamos
	private static final int n = 10;
	// variable que usamos
	private static int valor = 0;
	// cerrojos
	private static Rompe_empate_lock lock_rompeempate = new Rompe_empate_lock(m);
	private static Ticket_lock lock_ticket = new Ticket_lock(m);
	private static Bakery_lock lock_bakery = new Bakery_lock(m);

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
			//lock_rompeempate.coge_lock(j);
			//lock_ticket.coge_lock(j);
			lock_bakery.coge_lock(j);
			valor++;
			//lock_rompeempate.suelta_lock(j);
			//lock_ticket.suelta_lock(j);
			lock_bakery.suelta_lock(j);
		}
	}
	
	// cada hilo resta el valor n a la variable valor
	private static void resta(int j){
		for(int i = 0; i < n; i++) {
			//lock_rompeempate.coge_lock(j);
			//lock_ticket.coge_lock(j);
			lock_bakery.coge_lock(j);
			valor--;
			//lock_rompeempate.suelta_lock(j);
			//lock_ticket.suelta_lock(j);
			lock_bakery.suelta_lock(j);
		}
	}
}
