/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ventilador;

/**
 *
 * @author galla
 */
public class ventilador {
   
    private VelocidadVentiladores velocidadVen;


    public VelocidadVentiladores getVelocidadVen() {
        return velocidadVen;
    }


    public void setVelocidadVen(VelocidadVentiladores velocidadVen) {
        this.velocidadVen = velocidadVen;
    }

    public ventilador() {
        this.velocidadVen = VelocidadVentiladores.NIVEL0;
    }
    
    public boolean encendidoV(){
        if (velocidadVen==VelocidadVentiladores.NIVEL0) {
            return false;
        }
        return true;
    }
    
}
