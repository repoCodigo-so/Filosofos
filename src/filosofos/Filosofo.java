/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filosofos;

/**
 *
 * @author User
 */
public class Filosofo implements Runnable {
    private int id;
    private Tenedor tenedorIzquierdo;
    private Tenedor tenedorDerecho;
    private Mesa mesa;

    public Filosofo(int id, Tenedor tenedorIzquierdo, Tenedor tenedorDerecho, Mesa mesa) {
        this.id = id;
        this.tenedorIzquierdo = tenedorIzquierdo;
        this.tenedorDerecho = tenedorDerecho;
        this.mesa = mesa;
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
        mesa.tomarTenedor(tenedorIzquierdo, tenedorDerecho);
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
        mesa.soltarTenedor(tenedorIzquierdo, tenedorDerecho);
    }
}
