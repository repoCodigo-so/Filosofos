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
        if (Math.random() < 0.3) {
            return new Hambriento();
        } else {
            return this; // Permanece pensando en la mayoría de los casos
        }
    }
}

class Hambriento extends EstadoFilosofo {
    public Hambriento() {
        super("Hambriento");
    }

    @Override
    public EstadoFilosofo getSiguienteEstado() {
        if (Math.random() < 0.5) {
            return new EsperandoTenedor();
        } else {
            return new Pensando(); // Vuelve a pensar en algunos casos
        }
    }
}

class EsperandoTenedor extends EstadoFilosofo {
    public EsperandoTenedor() {
        super("EsperandoTenedor");
    }

    @Override
    public EstadoFilosofo getSiguienteEstado() {
        if (Math.random() < 0.7) {
            return new Comiendo();
        } else {
            return this; // Permanece esperando en algunos casos
        }
    }
}

class Comiendo extends EstadoFilosofo {
    public Comiendo() {
        super("Comiendo");
    }

    @Override
    public EstadoFilosofo getSiguienteEstado() {
        if (Math.random() < 0.4) {
            return new Pensando();
        } else {
            return this; // Permanece comiendo en algunos casos
        }
    }
}
