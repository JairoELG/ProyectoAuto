/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package SistemaControlCinturones;

/**
 *
 * @author ccore
 */
public enum PosicionCinturon {
     CONDUCTOR("Conductor"),
     ACOMPANANTE("Acompañante"),
     PTRASERO_IZQUIERDO("Pasajero trasero izquierdo"),
     PTRASERO_DERECHO("Pasajero trasero derecho"),
     PTRASERO_CENTRO("Pasajero trasero centro");
     
        
    private final String pasajero;
 
    public String getPasajero() {
        return pasajero;
    }
        
    PosicionCinturon(String pasajero) {
        this.pasajero = pasajero;
    }
        
}
