package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javafx.util.Pair;

// proceso que crea cada cliente para comunicarse con el servidor mientras la clase 
// cliente sigue interactuando con el usuario por consola
// hay tantos oyentes servidores como clientes
public class OyenteServidor extends Thread{
	
	// en los atributos no llevamos el socket (canal) porque para que solo haya un flujo de 
	// entrada-salida por socket es necesario que lo cree el cliente y se lo pase a su 
	// oyente servidor, en lugar de que cada oyente servidor obtenga un flujo nuevo
	
	// flujo para escribir al oyente cliente
	private ObjectOutputStream escritor;
	
	// flujo para leer al oyente cliente
	private ObjectInputStream lector;
	
	// referencia al cliente
	private Cliente cliente;
	
	public OyenteServidor(ObjectOutputStream escritor, ObjectInputStream lector, Cliente cliente) {
		super();
		this.escritor = escritor;
		this.lector = lector;
		this.cliente = cliente;
	}

	// ejecución del hilo
	public void run() {
		try {
			
			// se atienden las respuestas del oyente cliente
			while(true) {
				// mensaje que ha enviado el oyente cliente
				Mensaje mensaje = (Mensaje) lector.readObject();
				
				// dependiendo del tipo de mensaje
				switch(mensaje.getTipo()) {
				
				// Mensaje_confirmacion_conexion
				case 0:
					System.out.println("El usuario "+ cliente.getNombreUser() +" ha establecido la conexión correctamente");
					
					break;
					
				// Mensaje_confirmacion_lista_usuarios
				case 1:
					// casting
					Mensaje_confirmacion_lista_usuarios mensaje1 = (Mensaje_confirmacion_lista_usuarios) mensaje;
					
					// se recorre la lista de usuarios-ficheros propios y se muestra
					for(Pair<String, List<String>> dato : mensaje1.getLista()) {
						System.out.println("El usuario "+ dato.getKey() + " es propietario de los ficheros: ");
						for(String fichero : dato.getValue()) {
							System.out.print(fichero + " ");
						}
						System.out.println();
					}
					
					break;
					
				// Mensaje_emitir_fichero
				case 2:
					
					// casting
					Mensaje_emitir_fichero mensaje2 = (Mensaje_emitir_fichero) mensaje;
					
					// se crea un proceso emisor que se encarga de enviar el fichero pedido
					Emisor emisor = new Emisor(mensaje2.getPuerto(), mensaje2.getFichero());
					
					// se inicia la ejecución del proceso emisor
					emisor.start();
					
					// se envía un mensaje al oyente cliente indicando que el emisor ya está 
					// preparado para enviar el fichero 
					escritor.writeObject(new Mensaje_preparado_cliente_servidor("Oyente servidor", 
							mensaje2.getOrigen(), cliente.getDirIP(), mensaje2.getPuerto()));
					
		
					System.out.println("Fichero " + mensaje2.getFichero() + " emitido");
					
					break;
					
				// Mensaje_fichero_no_encontrado
				case 3:
	
					System.out.println("Fichero no encontrado");
					
					break;
					
				// Mensaje_preparado_servidor_cliente
				case 4:
					// casting
					Mensaje_preparado_servidor_cliente mensaje4 = (Mensaje_preparado_servidor_cliente) mensaje;
					
					// se crea el proceso que recibirá el fichero
					Receptor receptor = new Receptor(mensaje4.getDirIP(), mensaje4.getPuerto());
					
					// se inicia el proceso del receptor
					receptor.start();
					
					break;
					
				// Mensaje_confirmacion_cerrar_conexion
				case 5:
					
					System.out.println("La conexión se ha cerrado correctamente");
					
					// se cierra la ejecución
					System.exit(1);
					
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
