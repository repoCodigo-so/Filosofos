/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filosofos;

/**
 *
 * @author User
 */
public enum EstadoTenedor {
    LIBRE("Libre"),
    OCUPADO("Ocupado");

    private final String nombreEstado;

    EstadoTenedor(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public EstadoTenedor getSiguienteEstado() {
        // Cambiar el estado del tenedor al siguiente estado
        if (this == LIBRE) {
            return OCUPADO;
        } else {
            return LIBRE;
        }
    }
}