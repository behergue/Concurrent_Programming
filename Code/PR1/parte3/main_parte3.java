package parte3;

import java.util.ArrayList;
import java.util.List;

/*	Multiplicación de matrices por N threads. 
 * 
 * 	Implementación de la multiplicación de dos matrices de tamaño N*N utilizando N threads 
 * 	de manera que cada thread calcula una fila del producto.
 */

public class main_parte3 {
	private static final int n = 3;
	private static List<List<Integer>> matriz1 = new ArrayList<>();
	private static List<List<Integer>> matriz2 = new ArrayList<>();
	private static List<List<Integer>> resultado = new ArrayList<>();
	
	public static void main(String[] args) throws InterruptedException {
		
		// inicializamos las matrices
		for(int i = 0; i < n; i++) {
			matriz1.add(new ArrayList<>());
			matriz2.add(new ArrayList<>());
			resultado.add(new ArrayList<>());
			
			for(int j = 0; j < n; j++) {
				matriz1.get(i).add(1);
				matriz2.get(i).add(1);
				resultado.get(i).add(0);
			}
		}
		
		// creamos la lista de hilos
		List<Thread> hilos = new ArrayList<>();
		
		// creamos los hilos y los lanzamos
		for(int i = 0; i < n; i++) {
			int j = i;
			hilos.add(new Thread(()->multiplicaFila(j)));
			hilos.get(i).start();
		}
		
		// esperamos a todos los hilos
		for(int i = 0; i < n; i++) {
			hilos.get(i).join();
		}
		
		System.out.println("Han terminado todos los hilos");
		System.out.println("La matriz resultado es");
		
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(resultado.get(i).get(j) + " ");
			}
			System.out.println();
		}
	}
	
	// multiplica la fila i de la matriz por la otra matriz entera
	private static void multiplicaFila(int i){
		int aux;
		
		for(int j = 0; j < n; j++) {
			aux = 0;
			for(int k = 0; k < n; k++) {
				aux += matriz1.get(i).get(k) * matriz2.get(k).get(j);
			}
			resultado.get(i).set(j, aux);
		}
	}
}
