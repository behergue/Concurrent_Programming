package parte2;

// monitor que protege el puerto para que se pueda establecer el peer to peer, 
// ya que no se pueden hacer dos peer to peer en el mismo puerto simultáneamente
// debemos asegurar la exclusión mutua
public class Monitor_puerto_disponible {
	
	// puerto inicial escogido
	private int puerto = 1235;
	
	// cuando se pide un puerto disponible devuelve el siguiente,
	// y cada muchos puertos (por ejemplo al llegar al millón), vuelve al puerto inicial
	synchronized int obtenerPuerto() {
		int myPuerto = puerto;
		puerto++;
		if(puerto == 1000000) {
			puerto = 1235;
		}
		return myPuerto;
	}
}
