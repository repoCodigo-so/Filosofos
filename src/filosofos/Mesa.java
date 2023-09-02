/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filosofos;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class Mesa {
    private Tenedor[] tenedores;
    private Filosofo[] filosofos;

    public Mesa(int numFilosofos) {
        tenedores = new Tenedor[numFilosofos];
        filosofos = new Filosofo[numFilosofos];

        // Inicializar tenedores
        for (int i = 0; i < numFilosofos; i++) {
            tenedores[i] = new Tenedor(i);
        }

        // Inicializar filósofos
        for (int i = 0; i < numFilosofos; i++) {
            Tenedor tenedorIzquierdo = tenedores[i];
            Tenedor tenedorDerecho = tenedores[(i + 1) % numFilosofos]; // Tenedor a la derecha
            filosofos[i] = new Filosofo(i, tenedorIzquierdo, tenedorDerecho, this);
        }
    }

    public void iniciarCena() {
        // Iniciar un hilo para cada filósofo
        for (Filosofo filosofo : filosofos) {
            Thread thread = new Thread(filosofo);
            thread.start();
        }
    }

    public synchronized void tomarTenedor(Tenedor tenedorIzquierdo, Tenedor tenedorDerecho) {
        // Esperar hasta que ambos tenedores estén disponibles
        while (!tenedorIzquierdo.estaDisponible() || !tenedorDerecho.estaDisponible()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            // Tomar los tenedores
            tenedorIzquierdo.agarrar();
        } catch (InterruptedException ex) {
            Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            tenedorDerecho.agarrar();
        } catch (InterruptedException ex) {
            Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public synchronized void soltarTenedor(Tenedor tenedorIzquierdo, Tenedor tenedorDerecho) {
        // Soltar los tenedores
        tenedorIzquierdo.soltar();
        tenedorDerecho.soltar();

        // Notificar a otros filósofos que los tenedores están disponibles
        notifyAll();
    }
}
