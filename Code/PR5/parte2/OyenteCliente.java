package parte2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

// proceso creado por el servidor encargado de interactuar con un determinado cliente
// el servidor crea tantos oyentes como clientes tiene
// cada oyente cliente representa al servidor
public class OyenteCliente extends Thread {

	// canal para la comunicaci�n con el cliente
	private Socket socket;
	
	// identificador del cliente
	private String cliente;

	public OyenteCliente(Socket socket) {
		super();
		this.socket = socket;
	}
	
	// ejecuci�n del hilo
	public void run() {
		try {
			// se crean los flujos para la comunicaci�n con el cliente
			ObjectInputStream lector = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream escritor = new ObjectOutputStream(socket.getOutputStream());
			
			// se atienden las peticiones del cliente
			while(true) {
				// mensaje que ha enviado el cliente
				Mensaje mensaje = (Mensaje) lector.readObject();
				
				// qu� tipo de mensaje es
				switch(mensaje.getTipo()) {
				
				// se ejecuta seg�n el tipo de mensaje
				
				// Mensaje_conexion
				case 0:
					// casting para poder acceder a m�todos que no son de la superclase
					Mensaje_conexion mensaje0 = (Mensaje_conexion) mensaje; 
					
					// se guarda en el atributo de la clase el id del cliente que nos ha enviado el mensaje
					cliente = mensaje0.getOrigen();
					
					// se a�ade el usuario y sus flujos a la tabla de usuarios conectados que est� en el monitor
					Servidor.monitor.anadirUsuario(cliente, lector, escritor);
					
					// para cada fichero del usuario se actualiza en la tabla de ficheros-propietarios
					for(String fichero : mensaje0.getFicherosCliente()) {
						Servidor.actualizaFichero(fichero, cliente);
					}
					
					// se ecribe un mensaje al cliente confirmando que se ha establecido la conexi�n
					escritor.writeObject(new Mensaje_confirmacion_conexion("Oyente cliente", cliente));
					break;
					
				// Mensaje_lista_usuarios
				case 1:
					// lista de usuarios conectados a ficheros que son de su propiedad
					List<Pair<String, List<String>>> lista = new ArrayList<>();
					
					// para cada usuario conectado 
					for(String usuario : Servidor.monitor.listaUsuarios()) {
						// a�adimos este usuario a la lista con una lista vac�a de ficheros propios
						lista.add(new Pair<>(usuario, new ArrayList<>()));
						// para cada fichero del sistema
						for(String fichero : Servidor.listaFicheros()) {
							// comprobamos si el usuario en cuesti�n es propietario suyo
							if(Servidor.esPropietario(usuario, fichero)) {
								// si es as�, lo a�adimos a la lista de ficheros propiedad del usuario
								lista.get(lista.size() - 1).getValue().add(fichero);
							}
						}
					}
					
					// escribimos un mensaje al cliente enviando la lista elaborada
					escritor.writeObject(new Mensaje_confirmacion_lista_usuarios("Oyente cliente", cliente, lista));
					break;
					
				// Mensaje_pedir_fichero
				case 2:
					
					// casting para poder acceder a m�todos que no son de la superclase
					Mensaje_pedir_fichero mensaje2 = (Mensaje_pedir_fichero) mensaje;
					
					// si el fichero que pedimos existe en el sistema
					if(Servidor.existeFichero(mensaje2.getFichero())) {
						String propietarioElegido = null;
						
						// recorremos la lista de propietarios del fichero
						for(String propietario : Servidor.listaPropietarios(mensaje2.getFichero())) {
							// si el propietario que est� conectado
							if(Servidor.monitor.clienteConectado(propietario)) {
								// es el elegido para enviar el fichero
								propietarioElegido = propietario;
								break;
							}
						}
						// si no hay ning�n propietario conectado
						if(propietarioElegido == null) {
							// se env�a un mensaje indicando que no es posible obtener el fichero
							escritor.writeObject(new Mensaje_fichero_no_encontrado("Oyente cliente", cliente));
						}
						// si hay un propietario conectado
						else {
							// se manda un mensaje al propietario elegido pidi�ndole que envie el fichero
							Servidor.monitor.flujoSalida(propietarioElegido)
							.writeObject(new Mensaje_emitir_fichero(cliente, propietarioElegido, 
									mensaje2.getFichero(), Servidor.monitorPuerto.obtenerPuerto()));
						}
					}
					// si el fichero no existe en el sistema
					else {
						// se indica que no es posible obtenerlo
						escritor.writeObject(new Mensaje_fichero_no_encontrado("Oyente cliente", cliente));
					}
					
					break;
					
				// Mensaje_preparado_cliente_servidor
				case 4:
					// casting
					Mensaje_preparado_cliente_servidor mensaje4 = (Mensaje_preparado_cliente_servidor) mensaje;
					
					// se obtiene el flujo del cliente que pidi� el fichero
					ObjectOutputStream escribir = Servidor.monitor.flujoSalida(mensaje4.getDestino());
					
					// se env�a un mensaje a dicho cliente indic�ndole los datos necesarios (IP, puerto)
					// para que establezca la conexi�n con el cliente que le va a enviar el fichero
					escribir.writeObject(new Mensaje_preparado_servidor_cliente("Oyente cliente", 
							mensaje4.getDestino(), mensaje4.getDirIP(), mensaje4.getPuerto()));
					
					break;
					
				// Mensaje_cerrar_conexi�n
				case 5:
					// casting
					Mensaje_cerrar_conexion mensaje5 = (Mensaje_cerrar_conexion) mensaje;
					
					// se elimina al usuario de la lista de conectados
					Servidor.monitor.suprimirUsuario(mensaje5.getOrigen());
					
					// se env�a un mensaje para indicar que se ha finalizado la conexi�n
					escritor.writeObject(new Mensaje_cerrar_conexion("Oyente cliente", 
							mensaje5.getOrigen()));
					
					break;
				}
			}
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
