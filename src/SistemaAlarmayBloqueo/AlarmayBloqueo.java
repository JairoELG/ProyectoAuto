/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaAlarmayBloqueo;

/**
 *
 * @author Usuario
 */
public class AlarmayBloqueo {
    private boolean alarmaActiv;
    private boolean puertasBloq;
    private boolean alarmaDisp;
    private boolean sonidoActiv;
    private int nivelSens;
    private int tiempoDisp;
    private String estadoSistema;

    public boolean isAlarmaActiv() {
        return alarmaActiv;
    }
    public boolean isPuertasBloq() {
        return puertasBloq;
    }
    public boolean isAlarmaDisp() {
        return alarmaDisp;
    }
    public boolean isSonidoActiv() {
        return sonidoActiv;
    }
    public int getNivelSens() {
        return nivelSens;
    }
    public int getTiempoDisp() {
        return tiempoDisp;
    }
    public String getEstadoSistema() {
        return estadoSistema;
    }

    public void setAlarmaActiv(boolean alarmaActiv) {
        this.alarmaActiv = alarmaActiv;
        actualizarEstado();
    }

    public void setPuertasBloq(boolean puertasBloq) {
        this.puertasBloq = puertasBloq;
        actualizarEstado();
    }

    public void setAlarmaDisp(boolean alarmaDisp) {
        this.alarmaDisp = alarmaDisp;
        if(alarmaDisp){
            sonidoActiv=true;
            tiempoDisp=30;
        }else{
            sonidoActiv=false;
            tiempoDisp=0;
        }
    }

    public void setSonidoActiv(boolean sonidoActiv) {
        this.sonidoActiv = sonidoActiv;
    }

    public void setNivelSens(int nivelSens) {
        if(nivelSens>=1 && nivelSens<=5){
            this.nivelSens = nivelSens;    
        }        
    }

    public void setTiempoDisp(int tiempoDisp) {
        if(tiempoDisp>=0){
            this.tiempoDisp = tiempoDisp;    
        }
    }

    public AlarmayBloqueo() {
        this.alarmaActiv = false;
        this.puertasBloq = false;
        this.alarmaDisp = false;
        this.sonidoActiv = false;
        this.nivelSens = 3; //por defecto
        this.tiempoDisp = 0;
        this.estadoSistema = "Sistema Inactivo";
    }
    
    private void actualizarEstado() {
        if (alarmaDisp) {
            estadoSistema = "¡ALARMA DISPARADA!";
        } else if (alarmaActiv && puertasBloq) {
            estadoSistema = "Sistema Activado - Vehículo Seguro";
        } else if (alarmaActiv) {
            estadoSistema = "Alarma Activada - Puertas Desbloqueadas";
        } else if (puertasBloq) {
            estadoSistema = "Solo Puertas Bloqueadas";
        } else {
            estadoSistema = "Sistema Inactivo";
        }
    }
    
    public void activarAlarma(){
        alarmaActiv = true;
        puertasBloq = true;
        alarmaDisp = false;
        actualizarEstado();
    }
    
    public void desactivarAlarma(){
        alarmaActiv = false;
        puertasBloq = false;
        alarmaDisp = false;
        sonidoActiv= false;
        tiempoDisp= 0;
        actualizarEstado();
    }
    
    public void cambiarAlarma(){
        if(alarmaActiv){
            desactivarAlarma();
        }else{
            activarAlarma();
        }
    }
    
    public void cambiarBloqueo(){
        puertasBloq=!puertasBloq;
        actualizarEstado();
    }
    
    public void detectIntruso(){
        if(alarmaActiv){
            setAlarmaDisp(true);
        }
    }
    
    public void silenciarAlarma(){
        if(alarmaDisp){
            alarmaDisp=false;
            sonidoActiv=false;
            tiempoDisp=0;
            actualizarEstado();
        }
    }
    
    public void cambiarSens(int nivel){
        setNivelSens(nivel);
    }
}
