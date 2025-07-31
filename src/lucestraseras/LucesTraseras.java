/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lucestraseras;

/**
 *
 * @author HP I3
 */
public class LucesTraseras {
  
    private boolean encendidas;
    private boolean frenoActivado;
    private int intensidad; // de 0 a 100 (opcional)
    
    // Getters y Setters
    public boolean isEncendidas() {
        return encendidas;
    }

    public void setEncendidas(boolean encendidas) {
        this.encendidas = encendidas;
    }

    public boolean isFrenoActivado() {
        return frenoActivado;
    }

    public void setFrenoActivado(boolean frenoActivado) {
        this.frenoActivado = frenoActivado;
    }

    public int getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(int intensidad) {
        this.intensidad = intensidad;
    }

    // Constructor
    public LucesTraseras() {
        this.encendidas = false;
        this.frenoActivado = false;
        this.intensidad = 50; // valor predeterminado
    }

    // Encender luces traseras
    public void encender() {
        this.encendidas = true;
    }

    // Apagar luces traseras
    public void apagar() {
        this.encendidas = false;
        this.frenoActivado = false;
    }

    // Activar freno (enciende más fuerte)
    public void activarFreno() {
        this.frenoActivado = true;
        this.intensidad = 100;
    }

    // Desactivar freno
    public void desactivarFreno() {
        this.frenoActivado = false;
        this.intensidad = 50;
    }

    
}


