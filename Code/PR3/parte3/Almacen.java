package parte3;

public interface Almacen {

	public void almacenar(Producto producto) throws InterruptedException;
	public Producto extraer() throws InterruptedException;
}
