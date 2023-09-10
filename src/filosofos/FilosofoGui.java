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

public class FilosofoGui extends JFrame {
    private JButton[] filosofos;
    private JButton[] tenedores;
    private EstadoFilosofo[] estadosFilosofos;
    private EstadoTenedor[] estadosTenedores;
    private int numFilosofos;
    private Mesa mesa;
    private boolean simulacionEnEjecucion = true; // Variable para controlar la simulación
    private int[] tiemposComer; // Arreglo para almacenar los tiempos de comida de los filósofos

    public FilosofoGui(int numFilosofosIniciales) {
        this.numFilosofos = numFilosofosIniciales;
        this.estadosTenedores = new EstadoTenedor[numFilosofosIniciales];
        this.estadosFilosofos = new EstadoFilosofo[numFilosofosIniciales];
        this.filosofos = new JButton[numFilosofosIniciales];
        this.tenedores = new JButton[numFilosofosIniciales];
        this.mesa = new Mesa(numFilosofosIniciales, filosofos, tenedores); // Pasar el arreglo de botones de filósofos y tenedores a la Mesa

        // Configurar la ventana principal
        setTitle("Cena de los Filósofos");
        setLayout(null);
        setSize(800, 600); // Ajusta el tamaño de la ventana según tus necesidades
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Inicializar estados de los tenedores como Libres
        for (int i = 0; i < numFilosofosIniciales; i++) {
            estadosTenedores[i] = EstadoTenedor.LIBRE;
        }

        // Crear botones para los filósofos y asignar estados iniciales (Pensando)
        for (int i = 0; i < numFilosofosIniciales; i++) {
            filosofos[i] = new JButton("Filósofo " + i + " - Pensando");
            filosofos[i].setBounds(50, 50 + i * 40, 200, 30);

            estadosFilosofos[i] = new Pensando();

            add(filosofos[i]);
        }

        // Crear botones para los tenedores y asignar estados iniciales (Libres)
        for (int i = 0; i < numFilosofosIniciales; i++) {
            tenedores[i] = new JButton("Tenedor " + i + " - Libre");
            tenedores[i].setBounds(300, 50 + i * 40, 200, 30);

            final int index = i;
            tenedores[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Cambiar el estado del tenedor (Libre a Ocupado o viceversa)
                    if (estadosTenedores[index] == EstadoTenedor.LIBRE) {
                        estadosTenedores[index] = EstadoTenedor.OCUPADO;
                    } else {
                        estadosTenedores[index] = EstadoTenedor.LIBRE;
                    }
                    actualizarInterfaz();
                }
            });

            add(tenedores[i]);
        }

        // Crear botones para ajustar el tiempo de comida de los filósofos
        tiemposComer = new int[numFilosofosIniciales];
        Arrays.fill(tiemposComer, 1000); // Inicializar con 1000 milisegundos (1 segundo)
        for (int i = 0; i < numFilosofosIniciales; i++) {
            JButton ajustarTiempoButton = new JButton("Ajustar Tiempo");
            ajustarTiempoButton.setBounds(500, 50 + i * 40, 150, 30);

            final int index = i;
            ajustarTiempoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Abrir un cuadro de diálogo para que el usuario ingrese el nuevo tiempo de comida
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

        // Botón para ajustar el número de participantes (filósofos)
        JButton ajustarParticipantesButton = new JButton("Ajustar Participantes");
        ajustarParticipantesButton.setBounds(50, 50 + numFilosofosIniciales * 40 + 40, 200, 30);
        ajustarParticipantesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Pedir al usuario el nuevo número de participantes (filósofos)
                String nuevoNumParticipantesStr = JOptionPane.showInputDialog("Nuevo número de participantes (filósofos):");
                try {
                    int nuevoNumParticipantes = Integer.parseInt(nuevoNumParticipantesStr);
                    if (nuevoNumParticipantes >= 2 && nuevoNumParticipantes <= 10) {
                        ajustarNumParticipantes(nuevoNumParticipantes);
                    } else {
                        JOptionPane.showMessageDialog(null, "El número de participantes debe estar entre 2 y 10.");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un valor numérico válido.");
                }
            }
        });
        add(ajustarParticipantesButton);

        // Botón para iniciar la simulación automáticamente
        JButton iniciarSimulacionButton = new JButton("Iniciar Simulación Automática");
        iniciarSimulacionButton.setBounds(50, 50 + numFilosofosIniciales * 40 + 80, 250, 30);
        iniciarSimulacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSimulacion();
            }
        });
        add(iniciarSimulacionButton);

        // Botón para detener la simulación
        JButton detenerSimulacionButton = new JButton("Detener Simulación");
        detenerSimulacionButton.setBounds(320, 50 + numFilosofosIniciales * 40 + 80, 200, 30);
        detenerSimulacionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                detenerSimulacion();
            }
        });
        add(detenerSimulacionButton);

        // Crear un temporizador para actualizar la interfaz cada cierto intervalo
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (simulacionEnEjecucion) {
                    actualizarInterfaz();
                }
            }
        });
        timer.start(); // Iniciar el temporizador

        setVisible(true);
    }

    private void actualizarInterfaz() {
        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i].setText("Filósofo " + i + " - " + estadosFilosofos[i].getNombreEstado());
        }

        for (int i = 0; i < numFilosofos; i++) {
            if (estadosTenedores[i] == EstadoTenedor.OCUPADO) {
                tenedores[i].setText("Tenedor " + i + " - Ocupado");
                tenedores[i].setBackground(Color.RED); // Cambiar el color a rojo cuando el tenedor esté ocupado
            } else {
                tenedores[i].setText("Tenedor " + i + " - Libre");
                tenedores[i].setBackground(null); // Restablecer el color por defecto cuando el tenedor esté libre
            }
        }

        // Cambiar el color de los filósofos que están comiendo a verde
        for (int i = 0; i < numFilosofos; i++) {
            if (estadosFilosofos[i] instanceof Comiendo) {
                filosofos[i].setBackground(Color.GREEN);
            } else {
                filosofos[i].setBackground(null);
            }
        }
    }

    private void ajustarNumParticipantes(int nuevoNumParticipantes) {
        // Detener la simulación si está en ejecución
        detenerSimulacion();
        
        // Limpia la interfaz antes de crear nuevos componentes
        limpiarInterfaz();

        // Actualizar el número de filósofos y tenedores
        this.numFilosofos = nuevoNumParticipantes;
        this.estadosTenedores = new EstadoTenedor[nuevoNumParticipantes];
        this.estadosFilosofos = new EstadoFilosofo[nuevoNumParticipantes];
        this.filosofos = new JButton[nuevoNumParticipantes];
        this.tenedores = new JButton[nuevoNumParticipantes];
        this.mesa = new Mesa(nuevoNumParticipantes, filosofos, tenedores); // Crear una nueva Mesa con el nuevo número de filósofos

        // Inicializar estados de los tenedores como Libres
        for (int i = 0; i < nuevoNumParticipantes; i++) {
            estadosTenedores[i] = EstadoTenedor.LIBRE;
        }

        // Crear botones para los filósofos y asignar estados iniciales (Pensando)
        for (int i = 0; i < nuevoNumParticipantes; i++) {
            filosofos[i] = new JButton("Filósofo " + i + " - Pensando");
            filosofos[i].setBounds(50, 50 + i * 40, 200, 30);

            estadosFilosofos[i] = new Pensando();

            add(filosofos[i]);
        }

        // Crear botones para los tenedores y asignar estados iniciales (Libres)
        for (int i = 0; i < nuevoNumParticipantes; i++) {
            tenedores[i] = new JButton("Tenedor " + i + " - Libre");
            tenedores[i].setBounds(300, 50 + i * 40, 200, 30);

            final int index = i;
            tenedores[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Cambiar el estado del tenedor (Libre a Ocupado o viceversa)
                    if (estadosTenedores[index] == EstadoTenedor.LIBRE) {
                        estadosTenedores[index] = EstadoTenedor.OCUPADO;
                    } else {
                        estadosTenedores[index] = EstadoTenedor.LIBRE;
                    }
                    actualizarInterfaz();
                }
            });

            add(tenedores[i]);
        }

        // Crear botones para ajustar el tiempo de comida de los filósofos
        tiemposComer = new int[nuevoNumParticipantes];
        Arrays.fill(tiemposComer, 1000); // Inicializar con 1000 milisegundos (1 segundo)
        for (int i = 0; i < nuevoNumParticipantes; i++) {
            JButton ajustarTiempoButton = new JButton("Ajustar Tiempo");
            ajustarTiempoButton.setBounds(500, 50 + i * 40, 150, 30);

            final int index = i;
            ajustarTiempoButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Abrir un cuadro de diálogo para que el usuario ingrese el nuevo tiempo de comida
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

        // Ajustar la posición de los botones según el nuevo número de filósofos
        int heightOffset = nuevoNumParticipantes * 40;
        for (int i = 0; i < nuevoNumParticipantes; i++) {
            filosofos[i].setLocation(50, 50 + i * 40);
            tenedores[i].setLocation(300, 50 + i * 40);
        }

        // Actualizar la posición de los botones de ajustar tiempo y el botón de iniciar simulación
        int yPos = 50 + nuevoNumParticipantes * 40 + 40;
        for (int i = 0; i < nuevoNumParticipantes; i++) {
            JButton ajustarTiempoButton = (JButton) getContentPane().getComponent(6 + i);
            ajustarTiempoButton.setLocation(500, yPos + i * 40);
        }

        JButton ajustarParticipantesButton = (JButton) getContentPane().getComponent(6 + nuevoNumParticipantes);
        ajustarParticipantesButton.setLocation(50, yPos);
        JButton iniciarSimulacionButton = (JButton) getContentPane().getComponent(7 + nuevoNumParticipantes);
        iniciarSimulacionButton.setLocation(50, yPos + 40);
        JButton detenerSimulacionButton = (JButton) getContentPane().getComponent(8 + nuevoNumParticipantes);
        detenerSimulacionButton.setLocation(320, yPos + 40);

        // Redimensionar la ventana para acomodar los nuevos componentes
        setSize(800, 600 + heightOffset);

        // Actualizar la interfaz
        actualizarInterfaz();
    }

    private void iniciarSimulacion() {
        simulacionEnEjecucion = true;

        // Crear e iniciar un hilo para cada filósofo
        Thread[] filosofoThreads = new Thread[numFilosofos];
        for (int i = 0; i < numFilosofos; i++) {
            final int index = i;
            filosofoThreads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (simulacionEnEjecucion) {
                        // Simular el proceso de pensar
                        estadosFilosofos[index] = new Pensando();
                        actualizarInterfaz();
                        try {
                            Thread.sleep((long) (Math.random() * 1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Intentar tomar tenedores
                        estadosFilosofos[index] = new EsperandoTenedor();
                        actualizarInterfaz();
                        // Obtener los tenedores correspondientes
                        Tenedor tenedorIzquierdo = mesa.getTenedorIzquierdo(index);
                        Tenedor tenedorDerecho = mesa.getTenedorDerecho(index);
                        mesa.tomarTenedor(tenedorIzquierdo, tenedorDerecho, filosofos[index]);
                        actualizarInterfaz();

                        // Simular el proceso de comer
                        estadosFilosofos[index] = new Comiendo();
                        actualizarInterfaz();
                        try {
                            Thread.sleep(tiemposComer[index]); // Utilizar el tiempo de comida ajustado
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // Soltar tenedores
                        estadosFilosofos[index] = new Pensando();
                        actualizarInterfaz();
                        mesa.soltarTenedor(tenedorIzquierdo, tenedorDerecho, filosofos[index]);
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
    
    private void limpiarInterfaz() {
        getContentPane().removeAll(); // Elimina todos los componentes de la ventana
        setSize(800, 600); // Restablece el tamaño de la ventana
        revalidate(); // Valida la nueva disposición de componentes
        repaint(); // Repinta la ventana
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FilosofoGui(5);
            }
        });
    }
}
