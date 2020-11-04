package parte1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// hilo que se encarga de gestionar al cliente
public class OyenteCliente extends Thread {

	// canal con el que el hilo se comunica con el cliente
	private Socket socket;

	public OyenteCliente(Socket socket) {
		super();
		this.socket = socket;
	}
	
	// ejecución del hilo
	public void run() {
		try {
			// rea los flujos para poder leer y escribir con el cliente
			BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
			
			// lee el fichero que pide el cliente
			String nombreFich = lector.readLine();
			System.out.println("Me han enviado el fichero " + nombreFich);
			
			// abre el fichero y establece un flujo para leer de él
			BufferedReader leeFichero = new BufferedReader(new InputStreamReader(new FileInputStream(nombreFich)));
			
			// se escribe línea a línea en un string el fichero que se ha abierto
			String line, fichero = "";
			while((line = leeFichero.readLine()) != null) {
				fichero += line + '\n';
			}
			
			// para controlar el final del fichero
			fichero += '\0';
			
			// se envía el fichero al cliente
			escritor.println(fichero);
			System.out.println("He enviado el fichero " + fichero);
			
			lector.close();
			escritor.close();
			leeFichero.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
