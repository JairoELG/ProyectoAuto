/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ACCalefaccion;

/**
 *
 * @author galla
 */
public class ACcalefaccion {
    private boolean airec;
    private boolean calefaccion;
    private int temperaturaInter;

    public boolean isAirec() {
        return airec;
    }

    public boolean isCalefaccion() {
        return calefaccion;
    }

    public int getTemperaturaInter() {
        return temperaturaInter;
    }

    public void setAirec(boolean airec) {
        this.airec = airec;
    }

    public void setCalefaccion(boolean calefaccion) {
        this.calefaccion = calefaccion;
    }

    public void setTemperaturaInter(int temperaturaInter) {
        this.temperaturaInter = temperaturaInter;
    }

    public ACcalefaccion() {
        this.airec = false;
        this.calefaccion = false;
        this.temperaturaInter = 25;
    }
    
    public boolean encenderAC(){
        this.airec=true;
        if (airec==true) {
            this.calefaccion=false;      
        }
        return this.airec;
    }
    
    public boolean encenderCalefaccion(){
        this.calefaccion=true;
        if (calefaccion==true) {
            this.airec=false;
        }
        return this.calefaccion;
    }
    
    public void apagarTodo(){
        this.airec=false;
        this.calefaccion=false;
    }
    
    public int modificarTemperaturaInter(){
        if (airec==true && temperaturaInter>16){
           this.temperaturaInter=this.temperaturaInter-1;
        }
        if (calefaccion==true && temperaturaInter<28){
           this.temperaturaInter= this.temperaturaInter+1; 
        }
      return this.temperaturaInter;
    }
}
