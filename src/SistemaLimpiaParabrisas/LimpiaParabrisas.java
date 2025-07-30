/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaLimpiaParabrisas;

/**
 *
 * @author ccore
 */
public class LimpiaParabrisas {
    
    private Velocidad velocidadActual;
    private boolean estaActivo;
    
    
     public Velocidad getVelocidadActual() {
        return velocidadActual;
    }
    
    public LimpiaParabrisas() {
        velocidadActual = Velocidad.APAGADO;
        estaActivo = false;
    }
    
   
    public void cambiarVelocidad(Velocidad nuevaVelocidad) {
        detenerMovimiento();
        velocidadActual = nuevaVelocidad;
        
        if(velocidadActual != Velocidad.APAGADO) {
            estaActivo = true;
            System.out.println("Limpiaparabrisas activado: " + 
                velocidadActual.getVelocidades());
        } else {
            System.out.println("Limpiaparabrisas apagado");
        }
    }
    
   
    public void realizarCiclo() {
        if(estaActivo && velocidadActual != Velocidad.APAGADO) {
            System.out.println("Realizando ciclo de limpieza (" + 
                velocidadActual.getVelocidades() + ")");
            // Aquí iría la lógica de movimiento en una implementación gráfica
        }
    }
    
    private void detenerMovimiento() {
        estaActivo = false;
    }
    
    
    public boolean estaActivo() {
        return estaActivo;
    }
}


