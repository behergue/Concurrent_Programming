package parte3;

import java.util.concurrent.Semaphore;

// se pueden producir tam productos y según se vayan consumiendo se pueden producir más hasta llegar a tam
public class Implementa_almacen implements Almacen{
	// tamaño del buffer
	private final int tam = 10;
	// semáforo que miran los consumidores para saber si hay algo que consumir
	// 0 indica que no hay nada que consumir
	private Semaphore lleno = new Semaphore(0);
	// semáforo que miran los productores para saber si pueden producir
	// >0 indica que se puede seguir produciendo
	private Semaphore vacio = new Semaphore(tam);
	// variable para que productor y consumidor compartan el producto
	private Producto[] buffer = new Producto[tam];
	// marca la posición por la que voy produciendo
	private int iniP = 0;
	// marca la posición por la que voy consumiendo
	// invariante: iniP >= iniC
	private int iniC = 0;
	// mutex para proteger la variable iniP
	private Semaphore mutexP = new Semaphore(1);
	// mutex para proteger la variable iniC
	private Semaphore mutexC = new Semaphore(1);

	
	// almacena un producto
	public void almacenar(Producto producto) throws InterruptedException {
		// vemos si podemos pasar (si vacio >0)
		// un semaforo no puede quedar nunca a negativo entonces si es 0 me espero
		vacio.acquire();
		// cogemos el mutex de los productores
		mutexP.acquire();
		// ponemos el producto en el buffer para que lo cojan los consumidores
		buffer[iniP] = producto;
		// actualizamos la pos de producción
		iniP = (iniP + 1) % tam;
		System.out.println("El producto " + producto.getValor() + " ha sido producido");
		// soltamos el mutex de productores
		mutexP.release();
		// avisamos de que hay un producto más para consumir
		lleno.release();
	}
	
	// extrae un producto
	public Producto extraer() throws InterruptedException {
		// vemos si podemos pasar
		lleno.acquire();
		// cogemos el mutex de los consumidores
		mutexC.acquire();
		// cogemos el producto del buffer
		Producto myProducto = buffer[iniC];
		System.out.println("El producto " + myProducto.getValor() + " ha sido consumido");
		// soltamos el mutex de los consumidores
		mutexC.release();
		// avisamos de que hay un hueco más para un nuevo producto
		vacio.release();
		// devolvemos el producto aunque no se usa
		return myProducto;
	}
}
