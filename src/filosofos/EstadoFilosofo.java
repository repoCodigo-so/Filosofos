/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filosofos;

/**
 *
 * @author User
 */
public abstract class EstadoFilosofo {
    private String nombreEstado;

    public EstadoFilosofo(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public abstract EstadoFilosofo getSiguienteEstado();
}

class Pensando extends EstadoFilosofo {
    public Pensando() {
        super("Pensando");
    }

    @Override
    public EstadoFilosofo getSiguienteEstado() {
        return new Comiendo();
    }
}

class Comiendo extends EstadoFilosofo {
    public Comiendo() {
        super("Comiendo");
    }

    @Override
    public EstadoFilosofo getSiguienteEstado() {
        return new Pensando();
    }
}
