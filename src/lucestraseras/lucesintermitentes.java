/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lucestraseras;

/**
 *
 * @author HP I3
 */
public class lucesintermitentes {
     private boolean activadas;        // Si están activadas o no
    private boolean estadoActual;     // Si están encendidas o apagadas
    private long intervalo;           // Tiempo de parpadeo en milisegundos

   // Getter y Setter de activadas
    public boolean isActivadas() {
        return activadas;
    }

    public void setActivadas(boolean activadas) {
        this.activadas = activadas;
    }

    // Getter y Setter de estadoActual
    public boolean isEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(boolean estadoActual) {
        this.estadoActual = estadoActual;
    }

    // Getter y Setter de intervalo
    public long getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(long intervalo) {
        this.intervalo = intervalo;
    }

   
     // Constructor
    public lucesintermitentes(long intervalo) {
        this.activadas = false;
        this.estadoActual = false;
        this.intervalo = intervalo;
    }

    
    
     // Método para activar las luces intermitentes
    public void activar() {
        this.activadas = true;
    }

    // Método para desactivar las luces intermitentes
    public void desactivar() {
        this.activadas = false;
        this.estadoActual = false; // se apagan cuando se desactivan
    }

    // Alternar el estado (encendidas/apagadas)
    public void alternarEstado() {
        if (activadas) {
            this.estadoActual = !this.estadoActual;
        } else {
            this.estadoActual = false;
        }
    }
}
