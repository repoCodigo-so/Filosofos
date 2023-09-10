/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filosofos;

/**
 *
 * @author User
 */
import javax.swing.SwingUtilities;
import javax.swing.JButton;

public class Tenedor {
    private int id;
    private boolean disponible;
    private JButton botonTenedor; // Agregamos un botón para el tenedor

    public Tenedor(int id, JButton botonTenedor) {
        this.id = id;
        this.disponible = true;
        this.botonTenedor = botonTenedor;
    }

    public synchronized void agarrar() throws InterruptedException {
        while (!disponible) {
            wait(); // Esperar si el tenedor no está disponible
        }
        disponible = false; // Marcar el tenedor como no disponible

        // Actualizar el estado del botón del tenedor a Ocupado y su color a Rojo
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonTenedor.setText("Tenedor " + id + " - Ocupado");
                botonTenedor.setBackground(java.awt.Color.RED);
            }
        });
    }

    public synchronized void soltar() {
        disponible = true; // Marcar el tenedor como disponible
        notify(); // Notificar a otros hilos que el tenedor está disponible

        // Actualizar el estado del botón del tenedor a Libre y restablecer su color
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonTenedor.setText("Tenedor " + id + " - Libre");
                botonTenedor.setBackground(null);
            }
        });
    }

    public boolean estaDisponible() {
        return disponible;
    }

    public int getId() {
        return id;
    }
}
