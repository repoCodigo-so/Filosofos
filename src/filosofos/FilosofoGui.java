/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filosofos;

/**
 *
 * @author User
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;

public class FilosofoGui extends JFrame {
    private JButton[] filosofos;
    private JLabel[] tenedores;
    private int numFilosofos;
    private EstadoFilosofo[] estadosFilosofos;

    public FilosofoGui(int numFilosofosIniciales) {
        this.numFilosofos = numFilosofosIniciales;
        filosofos = new JButton[numFilosofos];
        tenedores = new JLabel[numFilosofos];
        estadosFilosofos = new EstadoFilosofo[numFilosofos];

        // Configurar la ventana principal
        setTitle("Cena de los Filósofos");
        setLayout(null);

        // Panel circular que representa la mesa
        JPanel panelMesa = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                int radius = Math.min(centerX, centerY);

                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.LIGHT_GRAY);
                g2d.fill(new Ellipse2D.Double(centerX - radius, centerY - radius, 2 * radius, 2 * radius));
            }
        };
        panelMesa.setBounds(100, 50, 500, 500); // Ajusta la posición y el tamaño del panel de la mesa
        add(panelMesa);

        // Coordenadas de los botones alrededor del perímetro del círculo
        int centerX = panelMesa.getWidth() / 2;
        int centerY = panelMesa.getHeight() / 2;
        int radius = panelMesa.getWidth() / 2;

        // Crear botones para los filósofos y asignar estados iniciales (Pensando)
        for (int i = 0; i < numFilosofos; i++) {
            double angle = Math.toRadians(360.0 / numFilosofos * i);
            int x = (int) (centerX + radius * Math.cos(angle)) - 30; // Ajusta la posición de los botones
            int y = (int) (centerY + radius * Math.sin(angle)) - 15; // Ajusta la posición de los botones

            filosofos[i] = new JButton("Filósofo " + i + " - Pensando");
            filosofos[i].setBounds(x, y, 100, 30);

            estadosFilosofos[i] = new Pensando(); // Inicialmente, todos los filósofos están pensando

            final int index = i;
            filosofos[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    estadosFilosofos[index] = estadosFilosofos[index].getSiguienteEstado();
                    actualizarInterfaz();
                }
            });

            add(filosofos[i]);
        }

        // Crear etiquetas para los tenedores
        for (int i = 0; i < numFilosofos; i++) {
            double angle = Math.toRadians(360.0 / numFilosofos * i);
            int x = (int) (centerX + (radius - 30) * Math.cos(angle)) - 10; // Ajusta la posición de las etiquetas
            int y = (int) (centerY + (radius - 30) * Math.sin(angle)) + 10; // Ajusta la posición de las etiquetas

            tenedores[i] = new JLabel("Tenedor " + i);
            tenedores[i].setBounds(x, y, 50, 20);

            add(tenedores[i]);
        }

        // Botón para agregar un nuevo filósofo y tenedor
        JButton agregarFilosofoButton = new JButton("Agregar Filósofo");
        agregarFilosofoButton.setBounds(350, 570, 150, 30);
        agregarFilosofoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarFilosofoYTenedor();
            }
        });
        add(agregarFilosofoButton);

        setSize(700, 700); // Ajusta el tamaño de la ventana principal
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    private void agregarFilosofoYTenedor() {
        if (numFilosofos < 10) { // Límite de 10 filósofos
            double angle = Math.toRadians(360.0 / numFilosofos);
            int x = (int) (400 + 250 * Math.cos(angle * numFilosofos));
            int y = (int) (350 + 250 * Math.sin(angle * numFilosofos));

            filosofos[numFilosofos] = new JButton("Filósofo " + numFilosofos + " - Pensando");
            filosofos[numFilosofos].setBounds(x - 30, y - 15, 100, 30);
            estadosFilosofos[numFilosofos] = new Pensando();

            tenedores[numFilosofos] = new JLabel("Tenedor " + numFilosofos);
            tenedores[numFilosofos].setBounds(x - 10, y + 10, 50, 20);

            final int index = numFilosofos;
            filosofos[numFilosofos].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    estadosFilosofos[index] = estadosFilosofos[index].getSiguienteEstado();
                    actualizarInterfaz();
                }
            });

            add(filosofos[numFilosofos]);
            add(tenedores[numFilosofos]);

            numFilosofos++;
            actualizarInterfaz();
        }
    }

    private void actualizarInterfaz() {
        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i].setText("Filósofo " + i + " - " + estadosFilosofos[i].getNombreEstado());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FilosofoGui(5); // Inicialmente, hay 5 filósofos
            }
        });
    }
}
