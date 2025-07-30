/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Radio;

/**
 *
 * @author HP I3
 */
public class radio {
     private boolean encendido;
    private String modo; // "AM", "FM", "Bluetooth"

   

    // Getter: Saber si está encendida
    public boolean isEncendido() {
        return encendido;
    }

    // Setter: Encender o apagar
    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }
    
     // Constructor
    public radio() {
        this.encendido = false;
        this.modo = "FM"; // Modo por defecto
    }

    // Método: Alternar encendido/apagado
    public void alternarEncendido() {
        this.encendido = !this.encendido;
    }

    // Getter: Obtener modo actual
    public String getModo() {
        return modo;
    }

    // Setter: Establecer modo manualmente
    public void setModo(String modo) {
        if (modo.equals("AM") || modo.equals("FM") || modo.equals("Bluetooth")) {
            this.modo = modo;
        }
        // No lanza mensaje, se puede manejar error desde interfaz si hace falta
    }

    // Método: Cambiar al siguiente modo en orden
    public void cambiarModo() {
        switch (modo) {
            case "AM" -> modo = "FM";
            case "FM" -> modo = "Bluetooth";
            case "Bluetooth" -> modo = "AM";
        }
    }

    // Método útil si la interfaz quiere mostrar un resumen
    public String obtenerEstado() {
        if (encendido) {
            return "Encendida - Modo: " + modo;
        } else {
            return "Apagada";
        }
    }
}
