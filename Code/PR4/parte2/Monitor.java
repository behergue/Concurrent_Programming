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
			// no puedo producir as� que me espero
			wait();
		}
		// cuando ya hay espacios libres produzco
		buffer[iniP] = producto;
		System.out.println("El producto " + producto.getValor() + " ha sido producido");
		// hay un espacio libre menos
		cont--;
		// actualizamos la siguiente posici�n para producir
		iniP = (iniP + 1) % tam;
		// despertamos a todos los hilos que est�n esperando
		notifyAll();
	}
	
	public synchronized void consumir() throws InterruptedException {
		// mientras no haya productos
		while(cont == tam) { 
			// no puedo consumir as� que espero
			wait();
		}
		// cuando ya hay productos consumimos el que toque
		Producto myProducto = buffer[iniC];
		System.out.println("El producto " + myProducto.getValor() + " ha sido consumido");
		// hay un espacio libre m�s
		cont++;
		// actualizamos la siguiente posici�n para consumir
		iniC = (iniC + 1) % tam;
		// despertamos a todos los hilos que est�n esperando
		notifyAll();
	}
}
