/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Motor;

/**
 *
 * @author galla
 */
public class motor {
    private boolean estadoMotor;
    private int temperaturaMotor;

    public boolean isEstadoMotor() {
        return estadoMotor;
    }

    public int getTemperaturaMotor() {
        return temperaturaMotor;
    }

    public void setEstadoMotor(boolean estadoMotor) {
        this.estadoMotor = estadoMotor;
    }

    public void setTemperaturaMotor(int temperaturaMotor) {
        this.temperaturaMotor = temperaturaMotor;
    }

    public motor() {
        this.estadoMotor = false;
        this.temperaturaMotor = 20;
    }
 
    public int aumentarTemperaturaM(){
        if (temperaturaMotor<=90) {
          this.temperaturaMotor=this.temperaturaMotor+1;
           return this.temperaturaMotor;
        }
       return this.temperaturaMotor;
    }
    
     public int disminuirTemperaturaM(){
        if (temperaturaMotor>=20) {
            this.temperaturaMotor=this.temperaturaMotor-1;
           return this.temperaturaMotor;
        }
       return this.temperaturaMotor;
    }
    
    
    public boolean encenderM(){
        this.estadoMotor=true;
        return this.estadoMotor;
    }
    
    public boolean apagarM(){
        this.estadoMotor=false;
        return this.estadoMotor;
    }
    
}
