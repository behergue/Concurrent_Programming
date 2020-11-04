package parte3;

import java.util.ArrayList;
import java.util.List;

public class Monitor {
	private int tam = 500;
	private Producto[] buffer = new Producto[tam];
	private int iniP = 0;
	private int iniC = 0;
	private int cont = tam;
	
	public synchronized void producir(List<Producto> lista) throws InterruptedException {
		
		// mientras no quepa mi producto en el buffer
		while(cont < lista.size()) { 
			// no puedo producir así que me espero
			wait();
		}
		// cuando ya hay espacios libres para todos los productos que quiero producir
		for(Producto p : lista) {
			// produzco
			buffer[iniP] = p;
			// hay un espacio libre menos
			cont--;
			// actualizamos la siguiente posición para producir
			iniP = (iniP + 1) % tam;
		}
		
		// mostramos los productos producidos
		System.out.println("Se han producido " + lista.size() + " productos:");
		
		for(Producto p: lista) {
			System.out.print(p.getValor() + " ");
		}
		
		System.out.println();
		
		// despertamos a todos los hilos que están esperando
		notifyAll();
	}
	
	public synchronized void consumir(int j) throws InterruptedException {
		// mientras no pueda consumir j productos porque hay menos de j
		while(tam - cont < j) { 
			// no puedo consumir así que espero
			wait();
		}
		
		List<Producto> lista = new ArrayList<>();
		for(int i = 0; i < j; i++) {
			// consumimos los productos que toquen
			lista.add(buffer[iniC]);
			// hay un espacio libre más
			cont++;
			// actualizamos la siguiente posición para consumir
			iniC = (iniC + 1) % tam;
		}

		System.out.println("Se han consumido " + j + " productos:");
		
		for(Producto p: lista) {
			System.out.print(p.getValor() + " ");
		}
		
		System.out.println();
		
		// despertamos a todos los hilos que están esperando
		notifyAll();
	}
}
