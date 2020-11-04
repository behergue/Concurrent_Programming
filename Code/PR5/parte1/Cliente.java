package parte1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	// main para la programación distribuida (simula una máquina)
	// el argumento que necesita es el puerto para la conexión con el servidor y está en args[0]
	public static void main(String[] args) throws IOException {
		
		// si no hay puerto error
		if(args.length < 1) {
			System.err.println("No hay puerto");
			System.exit(1);
		}
		
		// se convierte el puerto de string a entero
		int puerto = Integer.parseInt(args[0]);
		
		System.out.println("Introduzca el nombre del fichero");

		// se abre un flujo para que el usuario introduzca el nombre de fichero
		Scanner scanner = new Scanner(System.in);
		String nombreFich = scanner.nextLine();
		
		// se crea un canal para la comunicación con el servidor por el puerto indicado
		// InetAddress es la dir IP donde está la máquina
		Socket socket = new Socket(InetAddress.getLocalHost(), puerto);
		
		// se crean los flujos para leer y escribir en el canal establecido con el servidor
		BufferedReader lector = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter escritor = new PrintWriter(socket.getOutputStream(), true);
		
		// se escribe el nombre del fichero deseado en el servidor
		escritor.println(nombreFich);
		
		System.out.println("He pedido el fichero " + nombreFich);
		
		// se lee línea a línea el fichero que envía el servidor hasta que se encuentra el \0
		// que indica el final del fichero
		String line, fichero = "";
		while(true) {
			line = lector.readLine();
			if(line.equals("\0")) {break;}
			fichero += line + '\n';
		}
		
		// se muestra el fichero
		System.out.println("He recibido el fichero " + fichero);
		System.out.println(fichero);
		
		lector.close();
		escritor.close();
		socket.close();
		scanner.close();
	}
}
