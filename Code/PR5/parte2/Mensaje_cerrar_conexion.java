package parte2;

// mensaje para cerrar conexión
@SuppressWarnings("serial")
public class Mensaje_cerrar_conexion extends Mensaje{

	public Mensaje_cerrar_conexion(String origen, String destino) {
		super(origen, destino);
	}

	public int getTipo() {
		return 5;
	}
}
