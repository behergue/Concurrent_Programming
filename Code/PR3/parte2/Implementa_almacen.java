package parte2;

import java.util.concurrent.Semaphore;

public class Implementa_almacen implements Almacen{
	// semáforo que miran los consumidores para saber si hay algo que consumir
	// 0 indica que no hay nada que consumir
	private Semaphore lleno = new Semaphore(0);
	// semáforo que miran los productores para saber si pueden producir
	// 1 indica que sí pueden consumir
	// el invariante es que la suma de estos semáforos sea 1
	// si los dos son 0 se bloquea, y si los dos son 1 no habría exclusión mutua
	private Semaphore vacio = new Semaphore(1);
	// variable para que productor y consumidor compartan el producto
	private Producto buffer;
	
	// almacena un producto
	public void almacenar(Producto producto) throws InterruptedException {
		// ponemos vacío a 0 por lo que ya nadie puede producir
		vacio.acquire();
		// ponemos el producto en el buffer para que lo cojan los consumidores
		buffer = producto;
		System.out.println("El producto " + producto.getValor() + " ha sido producido");
		// ponemos lleno a 1 por lo que alguien puede consumir
		lleno.release();
	}
	
	// extrae un producto
	public Producto extraer() throws InterruptedException {
		// ponemos lleno a 0 por lo que nadie puede consumir
		lleno.acquire();
		// cogemos el producto del buffer (luego otro productor escribe encima)
		Producto myProducto = buffer;
		System.out.println("El producto " + myProducto.getValor() + " ha sido consumido");
		// ponemos vacio a 1 permitiendo a un productor que produzca
		vacio.release();
		// devolvemos el producto aunque no se usa
		return myProducto;
	}
}
