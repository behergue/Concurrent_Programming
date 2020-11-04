// Beatriz Herguedas Pinedo

package parte1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

	public static void main(String[] args) throws IOException {
		
		// si no hay puerto error
		if(args.length < 1) {
			System.err.println("No hay puerto");
			System.exit(1);
		}
		
		// se convierte el puerto de string a entero
		int puerto = Integer.parseInt(args[0]);
		
		@SuppressWarnings("resource")
		ServerSocket serversocket = new ServerSocket(puerto);
		
		while(true) {
			// espera a que llegue un cliente para crear el canal
			Socket socket = serversocket.accept();
			
			// crea un hilo que se encargue de gestionar al cliente con el que se ha establecido el canal
			OyenteCliente oyentecliente = new OyenteCliente(socket);
			
			// se arranca el hilo
			oyentecliente.start();
		}
	}

}
