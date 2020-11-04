package parte2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javafx.util.Pair;

// monitor que protege la tabla de usuarios conectados a sus flujos para asegurar la exclusión mutua
public class Monitor_usuarios {
	
	// tabla de cada usuario conectado a sus dos flujos, de lectura y escritura, porque no siempre 
	// cada oyente cliente está interactuando con su cliente, sino que hay veces que necesita
	// interactuar con otro cliente para pedirle un fichero, y para eso usa esta tabla
	private Map<String, Pair<ObjectInputStream, ObjectOutputStream>> mapaUsuarios = new TreeMap<>();
	
	// añade un usuario con sus dos flujos
	synchronized void anadirUsuario(String usuario, ObjectInputStream lector, ObjectOutputStream escritor) {
		mapaUsuarios.put(usuario, new Pair<>(lector, escritor));
	}
	
	// devuelve la lista de los usuarios conectados
	synchronized List<String> listaUsuarios(){
		List<String> lista = new ArrayList<>();
		
		for(String s : mapaUsuarios.keySet()) {
			lista.add(s);
		}
		
		return lista;
	}
	
	// comprueba si un usuario está conectado
	synchronized boolean clienteConectado(String cliente) {
		return mapaUsuarios.containsKey(cliente);
	}
	
	// devuelve el flujo para escribirle a un usuario
	synchronized ObjectOutputStream flujoSalida(String cliente) {
		return mapaUsuarios.get(cliente).getValue();				
	}
	
	// suprime un usuario
	synchronized void suprimirUsuario(String usuario) {
		mapaUsuarios.remove(usuario);
	}
}
