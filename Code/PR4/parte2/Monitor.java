package parte2;

public class Monitor {
	private int tam = 10;
	private Producto[] buffer = new Producto[tam];
	private int iniP;
	private int iniC;
	private int cont = tam;
	
	public synchronized void producir(Producto producto) throws InterruptedException {
		// mientras no haya espacios libres en el buffer
		while(cont == 0) { 
			// no puedo producir así que me espero
			wait();
		}
		// cuando ya hay espacios libres produzco
		buffer[iniP] = producto;
		System.out.println("El producto " + producto.getValor() + " ha sido producido");
		// hay un espacio libre menos
		cont--;
		// actualizamos la siguiente posición para producir
		iniP = (iniP + 1) % tam;
		// despertamos a todos los hilos que están esperando
		notifyAll();
	}
	
	public synchronized void consumir() throws InterruptedException {
		// mientras no haya productos
		while(cont == tam) { 
			// no puedo consumir así que espero
			wait();
		}
		// cuando ya hay productos consumimos el que toque
		Producto myProducto = buffer[iniC];
		System.out.println("El producto " + myProducto.getValor() + " ha sido consumido");
		// hay un espacio libre más
		cont++;
		// actualizamos la siguiente posición para consumir
		iniC = (iniC + 1) % tam;
		// despertamos a todos los hilos que están esperando
		notifyAll();
	}
}
