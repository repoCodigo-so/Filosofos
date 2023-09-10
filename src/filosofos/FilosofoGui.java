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
import java.util.Arrays;
import java.util.Random;

public class FilosofoGui extends JFrame {

    private JButton[] filosofos;
    private JButton[] tenedores;
    private EstadoFilosofo[] estadosFilosofos;
    private EstadoTenedor[] estadosTenedores;
    private int numFilosofos;
    private Mesa mesa;
    private boolean simulacionEnEjecucion = true;
    private int[] tiemposComer;
    private Random random;
    private JTextArea estadoComidaTextArea;
    private JScrollPane scrollPane; // Agregamos un JScrollPane

    public FilosofoGui(int numFilosofosIniciales) {
        this.numFilosofos = numFilosofosIniciales;
        this.estadosTenedores = new EstadoTenedor[numFilosofosIniciales];
        this.estadosFilosofos = new EstadoFilosofo[numFilosofosIniciales];
        this.filosofos = new JButton[numFilosofosIniciales];
        this.tenedores = new JButton[numFilosofosIniciales];
        this.mesa = new Mesa(numFilosofosIniciales, filosofos, tenedores);
        this.random = new Random();

        setTitle("Cena de los Filósofos");
        setLayout(null);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        for (int i = 0; i < numFilosofosIniciales; i++) {
            estadosTenedores[i] = EstadoTenedor.LIBRE;
        }

        for (int i = 0; i < numFilosofosIniciales; i++) {
            filosofos[i] = new JButton("Filósofo " + i + " - Pensando");
            filosofos[i].setBounds(50, 50 + i * 40, 200, 30);

            estadosFilosofos[i] = new Pensando();

            add(filosofos[i]);
        }

        for (int i = 0; i < numFilosofosIniciales; i++) {
            tenedores[i] = new JButton("Tenedor " + i + " - Libre");
            tenedores[i].setBounds(300, 50 + i * 40, 200, 30);

            final int index = i;
            tenedores[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Modificamos para mostrar el filósofo en el JTextArea
                    String mensaje = "Tenedor " + index + " - ";
                    if (estadosTenedores[index] == EstadoTenedor.LIBRE) {
                        estadosTenedores[index] = EstadoTenedor.OCUPADO;
                        mensaje += "Ocupado por " + estadosFilosofos[index].getClass().getSimpleName();
                    } else {
                        estadosTenedores[index] = EstadoTenedor.LIBRE;
                        mensaje += "Libre";
                    }
                    actualizarInterfaz();
                    actualizarEstadoComida(mensaje);
                }
            });

            add(tenedores[i]);
        }

        tiemposComer = new int[numFilosofosIniciales];
        Arrays.fill(tiemposComer, 1000);
        for (int i = 0; i < numFilosofosIniciales; i++) {
            JButton ajustarTiempoButton = new JButton("Ajustar Tiempo");
            ajustarTiempoButton.setBounds(500, 50 + i * 40, 150, 30);

            final int index = i;
            ajustarTiempoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String nuevoTiempoStr = JOptionPane.showInputDialog("Nuevo tiempo de comida para Filósofo " + index + " (milisegundos):");
                    try {
                        int nuevoTiempo = Integer.parseInt(nuevoTiempoStr);
                        if (nuevoTiempo >= 0) {
                            tiemposComer[index] = nuevoTiempo;
                        } else {
                            JOptionPane.showMessageDialog(null, "El tiempo de comida debe ser un valor no negativo.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido.");
                    }
                }
            });

            add(ajustarTiempoButton);
        }

        estadoComidaTextArea = new JTextArea();
        estadoComidaTextArea.setEditable(false);
        scrollPane = new JScrollPane(estadoComidaTextArea); // Agregamos el JScrollPane al JTextArea
        scrollPane.setBounds(50, 50 + numFilosofosIniciales * 40 + 120, 500, 100);
        add(scrollPane);

        JButton iniciarSimulacionButton = new JButton("Iniciar Simulación Automática");
        iniciarSimulacionButton.setBounds(50, 50 + numFilosofosIniciales * 40 + 40, 250, 30);
        iniciarSimulacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSimulacion();
            }
        });
        add(iniciarSimulacionButton);

        JButton detenerSimulacionButton = new JButton("Detener Simulación");
        detenerSimulacionButton.setBounds(320, 50 + numFilosofosIniciales * 40 + 80, 200, 30);
        detenerSimulacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detenerSimulacion();
            }
        });
        add(detenerSimulacionButton);

        JButton limpiarEstadoComidaButton = new JButton("Limpiar Estado de Comida");
        limpiarEstadoComidaButton.setBounds(600, 50 + numFilosofosIniciales * 40 + 120, 200, 30);
        limpiarEstadoComidaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                estadoComidaTextArea.setText("");
            }
        });
        add(limpiarEstadoComidaButton);

        JButton reiniciarButton = new JButton("Reiniciar Programa");
        reiniciarButton.setBounds(600, 50 + numFilosofosIniciales * 40 + 40, 200, 30);
        reiniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cerrar la ventana actual
                dispose();
                
                // Crear una nueva instancia de FilosofoGui para reiniciar el programa
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int numFilosofosIniciales = obtenerNumeroFilosofosInicial();
                        if (numFilosofosIniciales < 2 || numFilosofosIniciales > 10) {
                            JOptionPane.showMessageDialog(null, "El número de participantes debe estar entre 2 y 10. Se utilizarán 5 filósofos por defecto.");
                            numFilosofosIniciales = 5;
                        }
                        new FilosofoGui(numFilosofosIniciales);
                    }
                });
            }
        });
        add(reiniciarButton);

        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (simulacionEnEjecucion) {
                    actualizarInterfaz();
                }
            }
        });
        timer.start();

        setVisible(true);
    }

    private void actualizarInterfaz() {
        for (int i = 0; i < numFilosofos; i++) {
            if (estadosFilosofos[i] instanceof Pensando) {
                filosofos[i].setText("Filósofo " + i + " - Pensando");
                filosofos[i].setBackground(Color.BLUE); // Color azul cuando está pensando
            } else if (estadosFilosofos[i] instanceof EsperandoTenedor) {
                filosofos[i].setText("Filósofo " + i + " - EsperandoTenedor");
                filosofos[i].setBackground(Color.ORANGE); // Color naranja cuando está esperando un tenedor
            } else if (estadosFilosofos[i] instanceof Comiendo) {
                filosofos[i].setText("Filósofo " + i + " - Comiendo");
                filosofos[i].setBackground(Color.GREEN); // Color verde cuando está comiendo
            }
        }

        for (int i = 0; i < numFilosofos; i++) {
            if (estadosTenedores[i] == EstadoTenedor.OCUPADO) {
                tenedores[i].setText("Tenedor " + i + " - Ocupado");
                tenedores[i].setBackground(Color.RED);
            } else {
                tenedores[i].setText("Tenedor " + i + " - Libre");
                tenedores[i].setBackground(null);
            }
        }
    }

    private void actualizarEstadoComida(String mensaje) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                estadoComidaTextArea.append(mensaje + "\n");
                // Scroll hasta la parte inferior
                JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
            }
        });
    }

    private void iniciarSimulacion() {
        simulacionEnEjecucion = true;

        Thread[] filosofoThreads = new Thread[numFilosofos];
        for (int i = 0; i < numFilosofos; i++) {
            final int index = i;
            filosofoThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (simulacionEnEjecucion) {
                        estadosFilosofos[index] = new Pensando();
                        actualizarInterfaz();
                        try {
                            Thread.sleep((long) (Math.random() * 1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        estadosFilosofos[index] = new EsperandoTenedor();
                        actualizarInterfaz();
                        Tenedor tenedorIzquierdo = mesa.getTenedorIzquierdo(index);
                        Tenedor tenedorDerecho = mesa.getTenedorDerecho(index);

                        // Intenta tomar tenedores en un orden aleatorio
                        if (random.nextBoolean()) {
                            mesa.tomarTenedor(tenedorIzquierdo, tenedorDerecho, filosofos[index]);
                            actualizarEstadoComida("Filósofo " + index + " está comiendo.");
                        } else {
                            mesa.tomarTenedor(tenedorDerecho, tenedorIzquierdo, filosofos[index]);
                            actualizarEstadoComida("Filósofo " + index + " está comiendo.");
                        }

                        actualizarInterfaz();

                        estadosTenedores[index] = EstadoTenedor.OCUPADO;
                        actualizarInterfaz();

                        estadosFilosofos[index] = new Comiendo();
                        actualizarInterfaz();
                        try {
                            Thread.sleep(tiemposComer[index]);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        estadosTenedores[index] = EstadoTenedor.LIBRE;
                        actualizarInterfaz();

                        estadosFilosofos[index] = new Pensando();
                        actualizarInterfaz();

                        // Suelta los tenedores en cualquier orden
                        if (random.nextBoolean()) {
                            mesa.soltarTenedor(tenedorIzquierdo, tenedorDerecho, filosofos[index]);
                        } else {
                            mesa.soltarTenedor(tenedorDerecho, tenedorIzquierdo, filosofos[index]);
                        }

                        actualizarInterfaz();
                    }
                }
            });
            filosofoThreads[i].start();
        }
    }

    private void detenerSimulacion() {
        simulacionEnEjecucion = false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int numFilosofosIniciales = obtenerNumeroFilosofosInicial();
                if (numFilosofosIniciales < 2 || numFilosofosIniciales > 10) {
                    JOptionPane.showMessageDialog(null, "El número de participantes debe estar entre 2 y 10. Se utilizarán 5 filósofos por defecto.");
                    numFilosofosIniciales = 5;
                }
                new FilosofoGui(numFilosofosIniciales);
            }
        });
    }

    private static int obtenerNumeroFilosofosInicial() {
        String numFilosofosStr = JOptionPane.showInputDialog("Ingrese el número de filósofos:");
        try {
            return Integer.parseInt(numFilosofosStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido. Se utilizarán 5 filósofos por defecto.");
            return 5;
        }
    }
}