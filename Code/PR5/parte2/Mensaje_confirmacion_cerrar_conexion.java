package parte2;

// mensaje de confirmación de cierre de conexión
@SuppressWarnings("serial")
public class Mensaje_confirmacion_cerrar_conexion extends Mensaje{

	public Mensaje_confirmacion_cerrar_conexion(String origen, String destino) {
		super(origen, destino);
	}

	public int getTipo() {
		return 0;
	}
}
