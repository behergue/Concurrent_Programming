package parte2;

import java.util.List;

// mensaje para iniciar conexión
@SuppressWarnings("serial")
public class Mensaje_conexion extends Mensaje{
	
	// lista de los ficheros que tiene el cliente que inicia la conexión
	private List<String> ficherosCliente;

	public List<String> getFicherosCliente() {
		return ficherosCliente;
	}

	public Mensaje_conexion(String origen, String destino, List<String> ficherosCliente) {
		super(origen, destino);
		this.ficherosCliente = ficherosCliente;
	}

	public int getTipo() {
		return 0;
	}
}
