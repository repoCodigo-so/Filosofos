/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package filosofos;

/**
 *
 * @author User
 */
public class Filosofos {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                FilosofoGui gui = new FilosofoGui(5); // 5 es el número de filósofos, puedes cambiarlo según tus necesidades
            }
        });
    }
} 