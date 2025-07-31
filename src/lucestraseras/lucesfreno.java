/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lucestraseras;

/**
 *
 * @author HP I3
 */
public class LucesFreno {
     private boolean encendidas; // true = prendidas, false = apagadas

    // Constructor: inicialmente están apagadas
    public LucesFreno() {
        this.encendidas = false;
    }

    // Getter: saber si están encendidas
    public boolean isEncendidas() {
        return encendidas;
    }

    // Setter: cambiar el estado (true o false)
    public void setEncendidas(boolean encendidas) {
        this.encendidas = encendidas;
    }

    // Método adicional (opcional): cambiar entre encendidas y apagadas
    public void alternarEstado() {
        this.encendidas = !this.encendidas;
    }
}


