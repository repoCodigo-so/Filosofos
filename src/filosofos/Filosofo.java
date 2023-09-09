/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filosofos;

/**
 *
 * @author User
 */
import javax.swing.JButton;
import javax.swing.SwingUtilities;

public class Filosofo implements Runnable {

    private int id;
    private Tenedor tenedorIzquierdo;
    private Tenedor tenedorDerecho;
    private Mesa mesa;
    private JButton botonFilosofo; // Agregar una referencia al botón correspondiente en la interfaz

    public Filosofo(int id, Tenedor tenedorIzquierdo, Tenedor tenedorDerecho, Mesa mesa, JButton botonFilosofo) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
        this.mesa = mesa;
        this.botonFilosofo = botonFilosofo; // Asignar el botón de la interfaz al filósofo
    }

    @Override
    public void run() {
        while (true) {
            pensar();
            tomarTenedores();
            comer();
            soltarTenedores();
        }
    }

    private void pensar() {
        System.out.println("Filósofo " + id + " está pensando.");
        try {
            // Simula el tiempo que el filósofo pasa pensando
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tomarTenedores() {
        final JButton botonFilosofo = this.botonFilosofo; // Declara como final

        mesa.tomarTenedor(tenedorIzquierdo, tenedorDerecho, botonFilosofo);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonFilosofo.setText("Filósofo " + id + " - Comiendo");
            }
        });
    }

    private void comer() {
        System.out.println("Filósofo " + id + " está comiendo.");
        try {
            // Simula el tiempo que el filósofo pasa comiendo
            Thread.sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void soltarTenedores() {
        final JButton botonFilosofo = this.botonFilosofo; // Declara como final

        mesa.soltarTenedor(tenedorIzquierdo, tenedorDerecho, botonFilosofo);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonFilosofo.setText("Filósofo " + id + " - Pensando");
            }
        });
    }
}
