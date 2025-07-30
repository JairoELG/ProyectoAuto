/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaControlCinturones;

/**
 *
 * @author ccore
 */
public class ControlCinturones {
 
    private final boolean[] estadoCinturones;
    
    
    public void setEstadoCinturon(PosicionCinturon posicion, boolean abrochado) {
        estadoCinturones[posicion.ordinal()] = abrochado;
    }
    
    
    public boolean getEstadoCinturon(PosicionCinturon posicion) {
        return estadoCinturones[posicion.ordinal()];
    }
    
    public String getResumenEstado() {
    String resumen = "Estado de cinturones:\n";
        for(int i = 0; i < estadoCinturones.length; i++) {
        String descripcion = PosicionCinturon.values()[i].getPasajero();
        String estado = estadoCinturones[i] ? "Abrochado" : "Desabrochado";
        resumen += descripcion + ": " + estado + "\n";
    }
    
    return resumen;
}
 
    public ControlCinturones() {
    estadoCinturones = new boolean[PosicionCinturon.values().length];
        for(int i = 0; i < estadoCinturones.length; i++) {
            estadoCinturones[i] = false;
        }
    }
        
        
    public boolean todosAbrochados() {
        for(boolean estado : estadoCinturones) {
            if(!estado) {
                return false;
            }
        }
        return true;
    }
  
    
    public boolean conductorAbrochado() {
        return estadoCinturones[PosicionCinturon.CONDUCTOR.ordinal()];
    }
}


