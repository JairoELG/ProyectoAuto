/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaPuertas;

/**
 *
 * @author Usuario
 */
public class ControlPuertas {
    private int cantPuertasAbiertas;
    private boolean puertaIzqAdelante;
    private boolean puertaDerAdelante;
    private boolean puertaIzqAtras;
    private boolean puertaDerAtras;
    private boolean cajuela;
    private boolean capo;
    private boolean segurosActiv;
    private boolean alarmaActiv;

    public int getCantPuertasAbiertas() {
        return cantPuertasAbiertas;
    }
    public boolean isPuertaIzqAdelante() {
        return puertaIzqAdelante;
    }
    public boolean isPuertaDerAdelante() {
        return puertaDerAdelante;
    }
    public boolean isPuertaIzqAtras() {
        return puertaIzqAtras;
    }
    public boolean isPuertaDerAtras() {
        return puertaDerAtras;
    }
    public boolean isCajuela() {
        return cajuela;
    }
    public boolean isCapo() {
        return capo;
    }
    public boolean isSegurosActiv() {
        return segurosActiv;
    }
    public boolean isAlarmaActiv() {
        return alarmaActiv;
    }

    public void setPuertaIzqAdelante(boolean puertaIzqAdelante) {
        this.puertaIzqAdelante = puertaIzqAdelante;
        actualizarContador();
    }
    public void setPuertaDerAdelante(boolean puertaDerAdelante) {
        this.puertaDerAdelante = puertaDerAdelante;
        actualizarContador();
    }
    public void setPuertaIzqAtras(boolean puertaIzqAtras) {
        this.puertaIzqAtras = puertaIzqAtras;
        actualizarContador();
    }
    public void setPuertaDerAtras(boolean puertaDerAtras) {
        this.puertaDerAtras = puertaDerAtras;
        actualizarContador();
    }
    public void setCajuela(boolean cajuela) {
        this.cajuela = cajuela;
        actualizarContador();
    }
    public void setCapo(boolean capo) {
        this.capo = capo;
        actualizarContador();
    }
    public void setSegurosActiv(boolean segurosActiv) {
        this.segurosActiv = segurosActiv;
    }
    public void setAlarmaActiv(boolean alarmaActiv) {
        this.alarmaActiv = alarmaActiv;
    }

    public ControlPuertas() {
        this.cantPuertasAbiertas = 0;
        this.puertaIzqAdelante = false;
        this.puertaDerAdelante = false;
        this.puertaIzqAtras = false;
        this.puertaDerAtras = false;
        this.cajuela = false;
        this.capo = false;
        this.segurosActiv = true;
        this.alarmaActiv = false;
        }
    
    private void actualizarContador() {
        cantPuertasAbiertas = 0;
        if (puertaIzqAdelante) cantPuertasAbiertas++;
        if (puertaDerAdelante) cantPuertasAbiertas++;
        if (puertaIzqAtras) cantPuertasAbiertas++;
        if (puertaDerAtras) cantPuertasAbiertas++;
        if (cajuela) cantPuertasAbiertas++;
        if (capo) cantPuertasAbiertas++;
    }
    
    public void cambiarSeguros(){
        segurosActiv=!segurosActiv;
    }
    
    public void abrirTodasLasPuertas(){
        if(!segurosActiv){
            setPuertaIzqAdelante(true);
            setPuertaDerAdelante(true);
            setPuertaIzqAtras(true);
            setPuertaDerAtras(true);
            setCajuela(true);
        }
        setCapo(true);
    }
    
    public void cerrarTodasLasPuertas(){
        setPuertaIzqAdelante(false);
        setPuertaDerAdelante(false);
        setPuertaIzqAtras(false);
        setPuertaDerAtras(false);
        setCajuela(false);
        setCapo(false);
        }
    
     public boolean abrirPuertas(String puerta) {
        if (segurosActiv && !puerta.equals("capo")) {
            return false;
        }
        
        switch (puerta.toLowerCase()) {
            case "izq_adelant":
                setPuertaIzqAdelante(true);
                return true;
            case "der_adelant":
                setPuertaDerAdelante(true);
                return true;
            case "izq_atras":
                setPuertaIzqAtras(true);
                return true;
            case "der_atras":
                setPuertaDerAtras(true);
                return true;
            case "cajuela":
                setCajuela(true);
                return true;
            case "capo":
                setCapo(true);
                return true;
            default:
                return false;
        }
    }
    
    public boolean cerrarPuertas(String puerta) {
        switch (puerta.toLowerCase()) {
            case "izq_adelant":
                setPuertaIzqAdelante(false);
                return true;
            case "der_adelant":
                setPuertaDerAdelante(false);
                return true;
            case "izq_atras":
                setPuertaIzqAtras(false);
                return true;
            case "der_atras":
                setPuertaDerAtras(false);
                return true;
            case "cajuela":
                setCajuela(false);
                return true;
            case "capo":
                setCapo(false);
                return true;
            default:
                return false;
        }
    }
    
}
