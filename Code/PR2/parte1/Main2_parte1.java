// Javier Guzm�n Mu�oz
// Beatriz Herguedas Pinedo
// Pablo Hern�ndez Aguado
// Jorge Villarrubia Elvira

package parte1;

public class Main2_parte1 {

	// n�mero que sumamos
	private static final int n = 100000;
	// variable que usamos
	private static int valor = 0;
	
	// indican si el hilo quiere entrar en la secci�n cr�tica
	private volatile static boolean in1 = false;
	private volatile static boolean in2 = false;
	
	// algoritmo rompe-empate (el �ltimo que llega se espera)
	private volatile static int last = 1;

	public static void main(String[] args) throws InterruptedException {
		
		// creamos los hilos
		Thread hilo1 = new Thread(()->suma());
		Thread hilo2 = new Thread(()->resta());
		
		// esperamos a los hilos
		hilo1.join();
		hilo2.join();
		
		System.out.println("Han terminado todos los hilos");
		System.out.println("El valor final de la variable es " + valor);
	}
	
	// cada hilo suma el valor n a la variable valor
	private static void suma(){
		for(int i = 0; i < n; i++) {
			// el hilo 1 quiere entrar en la secci�n cr�tica
			in1 = true;
			// el hilo 1 es el �ltimo en llegar
			last = 1;
			// mientras el hilo 2 1uiera entrar en la secci�n cr�tica y 
			// el hilo 1 sea el �ltimo en llegar, el hilo 1 espera
			while(in2 && last == 1) {}
			valor++;
			// el hilo 1 sale de la secci�n cr�tica
			in1 = false;
		}
	}
	
	// cada hilo resta el valor n a la variable valor
	private static void resta(){
		for(int i = 0; i < n; i++) {
			// el hilo 2 quiere entrar en la secci�n cr�tica
			in2 = true;
			// el hilo 2 es el �ltimo en llegar
			last = 2;
			// mientras el hilo 1 1uiera entrar en la secci�n cr�tica y 
			// el hilo 2 sea el �ltimo en llegar, el hilo 2 espera
			while(in1 && last == 2) {}
			valor--;
			// el hilo 2 sale de la secci�n cr�tica
			in2 = false;
		}
	}
}
