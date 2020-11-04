package parte2;

// mensaje de confirmación de conexión correcta
@SuppressWarnings("serial")
public class Mensaje_confirmacion_conexion extends Mensaje {

	public Mensaje_confirmacion_conexion(String origen, String destino) {
		super(origen, destino);
	}

	public int getTipo() {
		return 0;
	}
}
