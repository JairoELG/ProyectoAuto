/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LucesFreno;

/**
 *
 * @author HP I3
 */
public class LucesEmergencia {
   
    private boolean activadas;
    private boolean estadoActual; // encendidas o apagadas
    private long intervalo; // tiempo entre parpadeos (en milisegundos)
    
     // Getters y Setters
    public boolean isActivadas() {
        return activadas;
    }

    public void setActivadas(boolean activadas) {
        this.activadas = activadas;
    }

    public boolean isEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(boolean estadoActual) {
        this.estadoActual = estadoActual;
    }

    public long getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(long intervalo) {
        this.intervalo = intervalo;
    }

    // Constructor
    public LucesEmergencia(long intervalo) {
        this.activadas = false;
        this.estadoActual = false;
        this.intervalo = intervalo;
    }

    // Encender luces de emergencia
    public void activar() {
        this.activadas = true;
    }

    // Apagar luces de emergencia
    public void desactivar() {
        this.activadas = false;
        this.estadoActual = false; // se apagan visualmente
    }

    // Cambia entre encendidas/apagadas
    public void alternarEstado() {
        if (activadas) {
            this.estadoActual = !this.estadoActual;
        } else {
            this.estadoActual = false;
        }
    }

   
}


