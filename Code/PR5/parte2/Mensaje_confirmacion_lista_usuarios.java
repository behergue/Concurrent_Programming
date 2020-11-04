package parte2;

import java.util.List;

import javafx.util.Pair;

// mensaje en el que se envía la lista de usuarios y se lleva para cada usuario
// la lista de ficheros de los que es propietario
@SuppressWarnings("serial")
public class Mensaje_confirmacion_lista_usuarios extends Mensaje{
	
	// lista de pares usuario-ficheros propios
	private List<Pair<String, List<String>>> lista;

	public List<Pair<String, List<String>>> getLista() {
		return lista;
	}

	public Mensaje_confirmacion_lista_usuarios(String origen, String destino, List<Pair<String, List<String>>> lista) {
		super(origen, destino);
		this.lista = lista;
	}

	public int getTipo() {
		return 1;
	}
}
