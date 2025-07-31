/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Combustible;

/**
 *
 * @author galla
 */
public class Combustible {
    private float nivel;
    private float consumo;

    public float getNivel() {
        return nivel;
    }

    public void setNivel(float nivel) {
        this.nivel = nivel;
    }

    public float getConsumo() {
        return consumo;
    }

    public void setConsumo(float consumo) {
        this.consumo = consumo;
    }
    
    public Combustible() {
        this.nivel = 100.0f;
        this.consumo =  0.0f;
    }
    // hay que crear un metodo que consuma mas combustible si el aire o ciertas cosas estan ensendidas 
    
    
    public float llenarTanque(){
        this.nivel=100.0f;
        return this.nivel;
    }
    
    public void consumo(){
        
    }
}
