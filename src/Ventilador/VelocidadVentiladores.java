/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Ventilador;

/**
 *
 * @author galla
 */
public enum VelocidadVentiladores {
    NIVEL0(0),
    NIVEL1(1),
    NIVEL2(2),
    NIVEL3(3),
    NIVEL4(4);

    private int valor;

    public int getValor() {
        return valor;
    }

    private VelocidadVentiladores(int valor) {
        this.valor = valor;
    }

}
