/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package SistemaLimpiaParabrisas;

/**
 *
 * @author ccore
 */
public enum Velocidad {
    
    APAGADO(0, "Apagado"),
    BAJA(2, "Baja velocidad"), 
    MEDIA(4, "Media velocidad"),
    ALTA(6, "Alta velocidad");
    
    private final int ciclosPorMinuto;
    private final String velocidades;
    
    Velocidad(int ciclos, String velocidades) {
        this.ciclosPorMinuto = ciclos;
        this.velocidades= velocidades;
    }
    
    public int getCiclosPorMinuto() {
        return ciclosPorMinuto;
    }
    
    public String getVelocidades() {
        return velocidades;
    }
}
