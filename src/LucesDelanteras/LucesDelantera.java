/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package LucesDelanteras;

/**
 *
 * @author HP I3
 */
public class LucesDelantera {
    private boolean encendidas;
    private String modo; // "cortas" (baja intensidad) o "largas" (alta intensidad)


    // Getter y Setter para encendidas
    public boolean isEncendidas() {
        return encendidas;
    }

    public void setEncendidas(boolean encendidas) {
        this.encendidas = encendidas;
    }

    // Getter y Setter para modo
    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        if (modo.equals("cortas") || modo.equals("largas")) {
            this.modo = modo;
        }
    }

    // Cambia entre "cortas" y "largas"
    public void alternarModo() {
        if (modo.equals("cortas")) {
            modo = "largas";
        } else {
            modo = "cortas";
        }
    }
    
    // Constructor
    public LucesDelantera() {
        this.encendidas = false;
        this.modo = "cortas";
    }
    
    // Devuelve la intensidad asociada al modo
    public String getIntensidad() {
    if (modo.equals("largas")) {
        return "Alta";
    } else {
        return "Baja";
    }
}


    // Cambiar estado encendido/apagado
    public void alternarEstado() {
        encendidas = !encendidas;
    }
}


