/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SistemaNavegacion;

/**
 *
 * @author Usuario
 */
public class GPS {
    private int velocidadActual;
    private int tiempoEstimado;
    private String rutaActual;
    private String destinoActual;
    private String direccionActual;
    private double longitud;
    private double latitud;
    private double distanciaDestino;
    private boolean gpsActivo;
    private boolean traficoActivo;

    public int getVelocidadActual() {
        return velocidadActual;
    }
    public int getTiempoEstimado() {
        return tiempoEstimado;
    }
    public String getRutaActual() {
        return rutaActual;
    }
    public String getDestino() {
        return destinoActual;
    }
    public String getDireccionActual() {
        return direccionActual;
    }
    public double getLongitud() {
        return longitud;
    }
    public double getLatitud() {
        return latitud;
    }
    public double getDistanciaDestino() {
        return distanciaDestino;
    }
    public boolean isGpsActivo() {
        return gpsActivo;
    }
    public boolean isTraficoActivo() {
        return traficoActivo;
    }
    
    public void setGpsActivo(boolean gpsActivo){
        this.gpsActivo=gpsActivo;
        if(!gpsActivo){
            this.direccionActual="";
            this.rutaActual="GPS Inactivo";
        }
    }
    public void setDestinoActual(String destinoActual){
        this.direccionActual=destinoActual;
        if(gpsActivo && !destinoActual.isEmpty()){
            calcularRuta();
        }
    }
    public void setLatitud(double latitud){
        if(latitud>=-90.0 && latitud<90.0){
            this.latitud=latitud;
        }
    }
    public void setLongitud(double longitud){
        if (longitud>=-180.0 && longitud<=180.0) {
            this.longitud=longitud;
        }
    }
    public void setVelocidadActual(int velocidadActual){
        if(velocidadActual>=0){
            this.velocidadActual=velocidadActual;
            actualizarTiempoEstimado();
        }
    }
    public void setDireccionActual(String direccionActual){
        this.direccionActual=direccionActual;
    }
    public void setTraficoActivo(boolean traficoActivo){
        this.traficoActivo=traficoActivo;
        if(gpsActivo && !destinoActual.isEmpty()){
            actualizarTiempoEstimado();
        }
    }
    public void setDistanciaDestino(double distanciaDestino){
        if(distanciaDestino>=0){
            this.distanciaDestino=distanciaDestino;
            actualizarTiempoEstimado();
        }
    }
    public void setTiempoEstimado(int tiempoEstimado){
        if(tiempoEstimado>=0){
            this.tiempoEstimado=tiempoEstimado;
        }
    }
    public void setRutaActual(String rutaActual){
        this.rutaActual=rutaActual;
    }
    
    private void calcularRuta(){
        if(!destinoActual.isEmpty() && gpsActivo){
            distanciaDestino=Math.random()*50+5;   // Simular el cálculo de la ruta
            rutaActual="Ruta calculada hacia: "+destinoActual;
            actualizarTiempoEstimado();
        }
    } 
    private void actualizarTiempoEstimado(){
        if(distanciaDestino>0 && velocidadActual>0){
            double tiempoBase=(distanciaDestino/velocidadActual)*60; // a minutos
            
            // Ajustar por tráfico
            if (traficoActivo){
                if(velocidadActual<30){
                    tiempoBase*=1.5;// 50% más tiempo por tráfico
                }else if(velocidadActual<60){
                    tiempoBase *= 1.2; // 20% más tiempo
                }
            }
            tiempoEstimado=(int)Math.round(tiempoBase);
        }else{
            tiempoEstimado = 0;
        }
    }
    public void activarGps(){
        gpsActivo=true;
        latitud=9.928;
        longitud=-84.090;
        rutaActual="GPS Activo - Ubicacion detectada";
    }
    public void desactivarGps(){
        setGpsActivo(false);
        latitud=0;
        longitud=0;
        rutaActual="";
        distanciaDestino=0.0;
        tiempoEstimado=0;
    }
    public boolean establecerDestino(String destino){
        if(gpsActivo && destino!=null && !destino.trim().isEmpty()) {
            this.destinoActual = destino.trim();
            calcularRuta();
            return true;
        }
        return false;
    }
    public void cancelarNavegacion(){
        destinoActual="";
        distanciaDestino=0.0;
        tiempoEstimado=0;
        rutaActual=gpsActivo ? "Sin ruta activa" : "GPS Desactivado";
    }
    public void actualizarVelocidad(int velocidad){
        setVelocidadActual(velocidad);
    }
    public void llegarADestino(){
        if(!destinoActual.isEmpty()) {
            destinoActual="";
            distanciaDestino=0.0;
            tiempoEstimado=0;
            rutaActual="Destino alcanzado";
        }
    }
    public String obtenerInfoTrafico(){
        if (!traficoActivo){
            return "Información de tráfico inactiva";
        }
        if (velocidadActual==0){
            return "Vehículo detenido";
        } else if (velocidadActual<30){
            return "Tráfico congestionado";
        } else if (velocidadActual<60){
            return "Tráfico moderado";
        } else {
            return "Tráfico fluido";
        }
    }
    public boolean tieneRutaActiva(){
        return !destinoActual.isEmpty() && gpsActivo;
    }
                 
}
