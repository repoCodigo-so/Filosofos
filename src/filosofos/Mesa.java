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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Mesa {
    private Tenedor[] tenedores;
    private JButton[] botonesFilosofos;
    private JButton[] botonesTenedores; // Agregamos un arreglo de botones para los tenedores

    public Mesa(int numFilosofos, JButton[] botonesFilosofos, JButton[] botonesTenedores) {
        tenedores = new Tenedor[numFilosofos];
        this.botonesFilosofos = botonesFilosofos;
        this.botonesTenedores = botonesTenedores;

        // Inicializar tenedores
        for (int i = 0; i < numFilosofos; i++) {
            tenedores[i] = new Tenedor(i, botonesTenedores[i]); // Pasar el botón del tenedor
        }
    }

    public void iniciarCena() {
        // Iniciar un hilo para cada filósofo
        for (int i = 0; i < botonesFilosofos.length; i++) {
            JButton botonFilosofo = botonesFilosofos[i];
            Thread thread = new Thread(new Filosofo(i, tenedores[i], getTenedorDerecho(i), this, botonFilosofo));
            thread.start();
        }
    }

    public synchronized void tomarTenedor(Tenedor tenedorIzquierdo, Tenedor tenedorDerecho, JButton botonFilosofo) {
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
            tenedorDerecho.agarrar();
        } catch (InterruptedException ex) {
            Logger.getLogger(Mesa.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Actualizar el estado del botón del filósofo
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonFilosofo.setText("Filósofo " + botonFilosofo.getName() + " - Comiendo");
            }
        });

        // Actualizar el estado y color del botón del tenedor izquierdo a Ocupado y Rojo
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonesTenedores[tenedorDerecho.getId()].setText("Tenedor " + tenedorDerecho.getId() + " - Ocupado");
                botonesTenedores[tenedorDerecho.getId()].setBackground(java.awt.Color.RED);
                botonesTenedores[tenedorIzquierdo.getId()].setText("Tenedor " + tenedorIzquierdo.getId() + " - Ocupado");
                botonesTenedores[tenedorIzquierdo.getId()].setBackground(java.awt.Color.RED);
            }
        });

        // Actualizar el estado y color del botón del tenedor derecho a Ocupado y Rojo
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonesTenedores[tenedorDerecho.getId()].setText("Tenedor " + tenedorDerecho.getId() + " - Ocupado");
                botonesTenedores[tenedorDerecho.getId()].setBackground(java.awt.Color.RED);
                botonesTenedores[tenedorIzquierdo.getId()].setText("Tenedor " + tenedorIzquierdo.getId() + " - Ocupado");
                botonesTenedores[tenedorIzquierdo.getId()].setBackground(java.awt.Color.RED);
            }
        });
    }

    public synchronized void soltarTenedor(Tenedor tenedorIzquierdo, Tenedor tenedorDerecho, JButton botonFilosofo) {
        // Soltar los tenedores
        tenedorIzquierdo.soltar();
        tenedorDerecho.soltar();

        // Actualizar el estado del botón del filósofo
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonFilosofo.setText("Filósofo " + botonFilosofo.getName() + " - Pensando");
            }
        });

        // Actualizar el estado y color del botón del tenedor izquierdo a Libre y restablecer su color
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonesTenedores[tenedorDerecho.getId()].setText("Tenedor " + tenedorDerecho.getId() + " - Libre");
                botonesTenedores[tenedorDerecho.getId()].setBackground(null);
                botonesTenedores[tenedorIzquierdo.getId()].setText("Tenedor " + tenedorIzquierdo.getId() + " - Libre");
                botonesTenedores[tenedorIzquierdo.getId()].setBackground(null);
            }
        });

        // Actualizar el estado y color del botón del tenedor derecho a Libre y restablecer su color
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                botonesTenedores[tenedorDerecho.getId()].setText("Tenedor " + tenedorDerecho.getId() + " - Libre");
                botonesTenedores[tenedorDerecho.getId()].setBackground(null);
                botonesTenedores[tenedorIzquierdo.getId()].setText("Tenedor " + tenedorIzquierdo.getId() + " - Libre");
                botonesTenedores[tenedorIzquierdo.getId()].setBackground(null);
            }
        });

        // Notificar a otros filósofos que los tenedores están disponibles
        notifyAll();
    }

    public Tenedor getTenedorIzquierdo(int filosofoIndex) {
        return tenedores[filosofoIndex];
    }

    public Tenedor getTenedorDerecho(int filosofoIndex) {
        return tenedores[(filosofoIndex + 1) % tenedores.length];
    }
}
