/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filosofos;

/**
 *
 * @author User
 */
public class Tenedor {
    private int id;
    private boolean disponible;

    public Tenedor(int id) {
        this.id = id;
        this.disponible = true;
    }

    public synchronized void agarrar() throws InterruptedException {
        while (!disponible) {
            wait(); // Esperar si el tenedor no está disponible
        }
        disponible = false; // Marcar el tenedor como no disponible
    }

    public synchronized void soltar() {
        disponible = true; // Marcar el tenedor como disponible
        notify(); // Notificar a otros hilos que el tenedor está disponible
    }

    public boolean estaDisponible() {
        return disponible;
    }
}
