package parte2;

// mensaje de fichero no encontrado
@SuppressWarnings("serial")
public class Mensaje_fichero_no_encontrado extends Mensaje{

	public Mensaje_fichero_no_encontrado(String origen, String destino) {
		super(origen, destino);
	}

	public int getTipo() {
		return 3;
	}
}
