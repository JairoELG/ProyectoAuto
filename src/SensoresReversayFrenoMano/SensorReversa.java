/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SensoresReversayFrenoMano;

/**
 *
 * @author ccore
 */
public class SensorReversa {

    private boolean frenoDeManoActivado;
    private boolean enReversa;
    private boolean obstaculoDetectado;
    private boolean alarmaActiva;
    private static final double DISTANCIA_SEGURIDAD = 1.5; 

  
    public void setFrenoDeMano(boolean activado) {
        this.frenoDeManoActivado = activado;
        verificarCondicionesAlarma();
    }

    public void setReversa(boolean activado) {
        this.enReversa = activado;
        verificarCondicionesAlarma();
    }

     public boolean isFrenoDeManoActivado() {
        return frenoDeManoActivado;
    }

    public boolean isEnReversa() {
        return enReversa;
    }

    public boolean isObstaculoDetectado() {
        return obstaculoDetectado;
    }

    public boolean isAlarmaActiva() {
        return alarmaActiva;
    }
    
    
    public SensorReversa() {
        frenoDeManoActivado = true; 
        enReversa = false;
        obstaculoDetectado = false;
        alarmaActiva = false;
    }
    
    public void actualizarDistanciaObstaculo(double distancia) {
        this.obstaculoDetectado = distancia < DISTANCIA_SEGURIDAD;
        verificarCondicionesAlarma();
    }

    
    private void verificarCondicionesAlarma() {
        boolean nuevaAlarma = false;

        // Condición 1: Movimiento en reversa con freno de mano puesto
        if(enReversa && frenoDeManoActivado) {
            nuevaAlarma = true;
        }
        
        // Condición 2: Obstáculo detectado mientras está en reversa
        if(enReversa && obstaculoDetectado) {
            nuevaAlarma = true;
        }

        // Activar/desactivar alarma según condiciones
        if(nuevaAlarma != alarmaActiva) {
            alarmaActiva = nuevaAlarma;
            if(alarmaActiva) {
                System.out.println("¡ALARMA ACTIVADA!"); 
            } else {
                System.out.println("Alarma desactivada");
            }
        }
    }

}


