/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package HUD;
import LucesDelanteras.LucesDelantera;
import LucesDelanteras.lucesintermitentes;
import lucestraseras.LucesTraseras;
import lucestraseras.lucesemergencia;
import Motor.motor;
import SistemaAlarmayBloqueo.AlarmayBloqueo;
import SistemaNavegacion.GPS;
import SistemaPuertas.ControlPuertas;
import ACCalefaccion.ACcalefaccion;
import Ventilador.ventilador;
import Ventilador.VelocidadVentiladores;
import Radio.radio;
import SistemaControlCinturones.ControlCinturones;
import SistemaControlCinturones.PosicionCinturon;
import SistemaLimpiaParabrisas.LimpiaParabrisas;
import SistemaLimpiaParabrisas.Velocidad;
import SensoresReversayFrenoMano.SensorReversa;
import Combustible.Combustible;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author Usuario
 */
public class GUI extends javax.swing.JFrame {

    private motor motorVehiculo;
    private AlarmayBloqueo sistemaAlarma;
    private GPS gpsSystem;
    private LucesDelantera lucesDelanteras;
    private lucesintermitentes intermitentesIzq;
    private lucesintermitentes intermitentesDer;
    private LucesTraseras lucesTraseras;
    private lucesemergencia lucesEmergencia;
    private ControlPuertas controlPuertas;
    private ACcalefaccion sistemaClima;  
    private ventilador ventiladorSystem;
    private radio radioSystem;
    private ControlCinturones controlCinturones;
    private LimpiaParabrisas limpiaparabrisas;
    private SensorReversa sensorReversa;
    private Combustible sistemaCombustible;
    private Timer leftBlinkTimer;
    private Timer rightBlinkTimer;
    private Timer emergencyBlinkTimer;
    private Timer combustibleTimer;
    private boolean leftBlinkState = false;
    private boolean rightBlinkState = false;
    private boolean emergencyBlinkState = false;
     
    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        inicializarSistemas();
        setupEventListeners();
        setupBlinkTimers();
        updateAllInterfaceStates();
        
    }

        private void inicializarSistemas() {
        // Sistemas principales
        motorVehiculo = new motor();
        sistemaAlarma = new AlarmayBloqueo();
        gpsSystem = new GPS();
        lucesDelanteras = new LucesDelantera();
        intermitentesIzq = new lucesintermitentes(500);
        intermitentesDer = new lucesintermitentes(500);
        lucesTraseras = new LucesTraseras();
        lucesEmergencia = new lucesemergencia(500);
        
        // Sistemas auxiliares
        controlPuertas = new ControlPuertas();
        sistemaClima = new ACcalefaccion();
        ventiladorSystem = new ventilador();
        radioSystem = new radio();
        controlCinturones = new ControlCinturones();
        limpiaparabrisas = new LimpiaParabrisas();
        sensorReversa = new SensorReversa();
        sistemaCombustible = new Combustible();
    }
        
        private void setupEventListeners() {
        
        // Motor
        EngineStartStop.addActionListener(e -> {
            if (motorVehiculo.isEstadoMotor()) {
                motorVehiculo.apagarM();
                EngineStartStop.setText("Engine Start");
                EngineStartStop.setSelected(false);
                txtluz.setText("Motor: APAGADO");
                // Parar consumo de combustible
                if (combustibleTimer != null) combustibleTimer.stop();
            } else {
                motorVehiculo.encenderM();
                EngineStartStop.setText("Engine Stop");
                EngineStartStop.setSelected(true);
                txtluz.setText("Motor: ENCENDIDO");
                // Iniciar consumo de combustible
                startCombustibleConsumption();
            }
        });
        
        // Acelerador y Freno
        Accelerate.addActionListener(e -> {
            motorVehiculo.aumentarTemperaturaM();
            Speed.setValue(motorVehiculo.getTemperaturaMotor());
            // Aumentar consumo de combustible
            increaseCombustibleConsumption();
            
            // Actualizar GPS si está activo
            if (gpsSystem.isGpsActivo()) {
                gpsSystem.actualizarVelocidad(motorVehiculo.getTemperaturaMotor());
            }
        });
        
        Brake.addActionListener(e -> {
            motorVehiculo.disminuirTemperaturaM();
            Speed.setValue(motorVehiculo.getTemperaturaMotor());
            
            // Activar luces de freno
            lucesTraseras.activarFreno();
            LuzTrasera1.setBackground(Color.RED);
            LuzTracera2.setBackground(Color.RED);
            
            // Desactivar luces de freno después de 2 segundos
            Timer brakeTimer = new Timer(2000, ev -> {
                lucesTraseras.desactivarFreno();
                updateTrunkLightsDisplay();
            });
            brakeTimer.setRepeats(false);
            brakeTimer.start();
        });
        // Acelerador y Freno
        Accelerate.addActionListener(e -> {
            motorVehiculo.aumentarTemperaturaM();
            Speed.setValue(motorVehiculo.getTemperaturaMotor());
            // Aumentar consumo de combustible
            increaseCombustibleConsumption();
            
            // Actualizar GPS si está activo
            if (gpsSystem.isGpsActivo()) {
                gpsSystem.actualizarVelocidad(motorVehiculo.getTemperaturaMotor());
            }
        });
        
        Brake.addActionListener(e -> {
            motorVehiculo.disminuirTemperaturaM();
            Speed.setValue(motorVehiculo.getTemperaturaMotor());
            
            // Activar luces de freno
            lucesTraseras.activarFreno();
            LuzTrasera1.setBackground(Color.RED);
            LuzTracera2.setBackground(Color.RED);
            
            // Desactivar luces de freno después de 2 segundos
            Timer brakeTimer = new Timer(2000, ev -> {
                lucesTraseras.desactivarFreno();
                updateTrunkLightsDisplay();
            });
            brakeTimer.setRepeats(false);
            brakeTimer.start();
        });
        
        // Gps
        GPSOnOff.addActionListener(e -> {
            if (gpsSystem.isGpsActivo()) {
                gpsSystem.desactivarGps();
                GPSOnOff.setText("GPS On");
                GPSOnOff.setSelected(false);
                txtgps1.setText("GPS: INACTIVO");
            } else {
                gpsSystem.activarGps();
                GPSOnOff.setText("GPS Off");
                GPSOnOff.setSelected(true);
                txtgps1.setText("GPS: ACTIVO - Ubicación detectada");
            }
        });
        
        // Alarma
        AlarmaONOFF.addActionListener(e -> {
            sistemaAlarma.cambiarAlarma();
            if (sistemaAlarma.isAlarmaActiv()) {
                AlarmaONOFF.setText("Alarm OFF");
                AlarmaONOFF.setBackground(Color.RED);
            } else {
                AlarmaONOFF.setText("Alarm ON");
                AlarmaONOFF.setBackground(null);
            }
        });
        
        // Seguros
        SegurosActivoInactivo.addActionListener(e -> {
            sistemaAlarma.cambiarBloqueo();
            controlPuertas.setSegurosActiv(sistemaAlarma.isPuertasBloq());
            updateLockDisplay();
        });
        
        // Luces
        Luces.addActionListener(e -> {
            String seleccion = (String) Luces.getSelectedItem();
            switch (seleccion) {
                case "Off":
                    lucesDelanteras.setEncendidas(false);
                    break;
                case "Cortas":
                    lucesDelanteras.setEncendidas(true);
                    lucesDelanteras.setModo("cortas");
                    break;
                case "Largas":
                    lucesDelanteras.setEncendidas(true);
                    lucesDelanteras.setModo("largas");
                    break;
            }
            updateFrontLightsDisplay();
        });
        
        // Intermitentes
        IntermitenteIzquierda.addActionListener(e -> toggleLeftBlinker());
        IntermitenteDerecha.addActionListener(e -> toggleRightBlinker());
        Emergency.addActionListener(e -> toggleEmergencyLights());
        
        // Puertas
        setupDoorControls();
        
        // AC
        setupClimateControls();
        
        // Cinturones
        setupSeatBeltControls();
        
        // Radio
        setupRadioControls();
        
        // Otros
        setupOtherControls();
        
        // Botones individuales de luces
        setupIndividualLightButtons();
    }
       
        private void setupDoorControls() {
        // Puertas individuales
        PuertaIzqDelantera.addActionListener(e -> {
            if (PuertaIzqDelantera.isSelected()) {
                boolean abierta = controlPuertas.abrirPuertas("izq_adelant");
                if (abierta) {
                    PuertaIzqDelantera.setText("ABIERTA");
                    PuertaIzqDelantera.setBackground(Color.RED);
                } else {
                    PuertaIzqDelantera.setSelected(false);
                    showMessage("¡Puertas bloqueadas!");
                }
            } else {
                controlPuertas.cerrarPuertas("izq_adelant");
                PuertaIzqDelantera.setText("CERRADA");
                PuertaIzqDelantera.setBackground(Color.GREEN);
            }
        });
        
        PuertaDerDelantera.addActionListener(e -> {
            if (PuertaDerDelantera.isSelected()) {
                boolean abierta = controlPuertas.abrirPuertas("der_adelant");
                if (abierta) {
                    PuertaDerDelantera.setText("ABIERTA");
                    PuertaDerDelantera.setBackground(Color.RED);
                } else {
                    PuertaDerDelantera.setSelected(false);
                    showMessage("¡Puertas bloqueadas!");
                }
            } else {
                controlPuertas.cerrarPuertas("der_adelant");
                PuertaDerDelantera.setText("CERRADA");
                PuertaDerDelantera.setBackground(Color.GREEN);
            }
        });
        
        PuertaIzqTrasera.addActionListener(e -> {
            if (PuertaIzqTrasera.isSelected()) {
                boolean abierta = controlPuertas.abrirPuertas("izq_atras");
                if (abierta) {
                    PuertaIzqTrasera.setText("ABIERTA");
                    PuertaIzqTrasera.setBackground(Color.RED);
                } else {
                    PuertaIzqTrasera.setSelected(false);
                    showMessage("¡Puertas bloqueadas!");
                }
            } else {
                controlPuertas.cerrarPuertas("izq_atras");
                PuertaIzqTrasera.setText("CERRADA");
                PuertaIzqTrasera.setBackground(Color.GREEN);
            }
        });
        
        PuertaDerTrasera.addActionListener(e -> {
            if (PuertaDerTrasera.isSelected()) {
                boolean abierta = controlPuertas.abrirPuertas("der_atras");
                if (abierta) {
                    PuertaDerTrasera.setText("ABIERTA");
                    PuertaDerTrasera.setBackground(Color.RED);
                } else {
                    PuertaDerTrasera.setSelected(false);
                    showMessage("¡Puertas bloqueadas!");
                }
            } else {
                controlPuertas.cerrarPuertas("der_atras");
                PuertaDerTrasera.setText("CERRADA");
                PuertaDerTrasera.setBackground(Color.GREEN);
            }
        });
        
        Cajuela.addActionListener(e -> {
            if (Cajuela.isSelected()) {
                boolean abierta = controlPuertas.abrirPuertas("cajuela");
                if (abierta) {
                    Cajuela.setText("ABIERTA");
                    Cajuela.setBackground(Color.RED);
                } else {
                    Cajuela.setSelected(false);
                    showMessage("¡Cajuela bloqueada!");
                }
            } else {
                controlPuertas.cerrarPuertas("cajuela");
                Cajuela.setText("CERRADA");
                Cajuela.setBackground(Color.GREEN);
            }
        });
        
        Capo.addActionListener(e -> {
            if (Capo.isSelected()) {
                controlPuertas.abrirPuertas("capo");
                Capo.setText("ABIERTO");
                Capo.setBackground(Color.ORANGE);
            } else {
                controlPuertas.cerrarPuertas("capo");
                Capo.setText("CERRADO");
                Capo.setBackground(Color.GREEN);
            }
        });
        
        // Botones de control masivo
        Alldoorsopen.addActionListener(e -> {
            controlPuertas.abrirTodasLasPuertas();
            updatePuertasDisplay();
        });
        
        Alldoorsclosed.addActionListener(e -> {
            controlPuertas.cerrarTodasLasPuertas();
            updatePuertasDisplay();
        });
    }
        
    private void setupClimateControls() {
        acToggle.addActionListener(e -> {
            if (acToggle.isSelected()) {
                sistemaClima.encenderAC();
                acToggle.setText("A/C ON");
                acToggle.setBackground(Color.CYAN);
                // Auto-apagar calefacción
                calefaccion.setSelected(false);
                updateCalefaccionDisplay();
            } else {
                sistemaClima.apagarTodo();
                acToggle.setText("A/C OFF");
                acToggle.setBackground(null);
            }
            updateTemperaturaDisplay();
        });
        
        calefaccion.addActionListener(e -> {
            if (calefaccion.isSelected()) {
                sistemaClima.encenderCalefaccion();
                calefaccion.setText("HEAT ON");
                calefaccion.setBackground(Color.RED);
                // Auto-apagar A/C
                acToggle.setSelected(false);
                updateACDisplay();
            } else {
                sistemaClima.apagarTodo();
                calefaccion.setText("HEAT OFF");
                calefaccion.setBackground(null);
            }
            updateTemperaturaDisplay();
        });
        
        tempmas.addActionListener(e -> {
            sistemaClima.modificarTemperaturaInter();
            updateTemperaturaDisplay();
        });
        
        tempmenos.addActionListener(e -> {
            // Implementar lógica para bajar temperatura
            if (sistemaClima.getTemperaturaInter() > 16) {
                sistemaClima.setTemperaturaInter(sistemaClima.getTemperaturaInter() - 1);
            }
            updateTemperaturaDisplay();
        });
        
        VentiladoresVelocidad.addActionListener(e -> {
            String nivel = (String) VentiladoresVelocidad.getSelectedItem();
            switch(nivel) {
                case "0": ventiladorSystem.setVelocidadVen(VelocidadVentiladores.NIVEL0); break;
                case "1": ventiladorSystem.setVelocidadVen(VelocidadVentiladores.NIVEL1); break;
                case "2": ventiladorSystem.setVelocidadVen(VelocidadVentiladores.NIVEL2); break;
                case "3": ventiladorSystem.setVelocidadVen(VelocidadVentiladores.NIVEL3); break;
                case "4": ventiladorSystem.setVelocidadVen(VelocidadVentiladores.NIVEL4); break;
            }
        });
    }
    
        private void setupSeatBeltControls() {
        CinturonConductor.addActionListener(e -> {
            controlCinturones.setEstadoCinturon(PosicionCinturon.CONDUCTOR, 
                CinturonConductor.isSelected());
            updateCinturonDisplay(CinturonConductor, "CONDUCTOR");
        });
        
        CinturonCopiloto.addActionListener(e -> {
            controlCinturones.setEstadoCinturon(PosicionCinturon.ACOMPANANTE, 
                CinturonCopiloto.isSelected());
            updateCinturonDisplay(CinturonCopiloto, "COPILOTO");
        });
        
        CinturonAtrasIzq.addActionListener(e -> {
            controlCinturones.setEstadoCinturon(PosicionCinturon.PTRASERO_IZQUIERDO, 
                CinturonAtrasIzq.isSelected());
            updateCinturonDisplay(CinturonAtrasIzq, "TRAS IZQ");
        });
        
        CinturonAtrasDer.addActionListener(e -> {
            controlCinturones.setEstadoCinturon(PosicionCinturon.PTRASERO_DERECHO, 
                CinturonAtrasDer.isSelected());
            updateCinturonDisplay(CinturonAtrasDer, "TRAS DER");
        });
        
        CinturonAtrasCent.addActionListener(e -> {
            controlCinturones.setEstadoCinturon(PosicionCinturon.PTRASERO_CENTRO, 
                CinturonAtrasCent.isSelected());
            updateCinturonDisplay(CinturonAtrasCent, "TRAS CEN");
        });
    }
    
    private void setupRadioControls() {
        Radiotoggle.addActionListener(e -> {
            radioSystem.alternarEncendido();
            if (radioSystem.isEncendido()) {
                Radiotoggle.setText("RADIO ON");
                Radiotoggle.setBackground(Color.GREEN);
                Radiomode.setEnabled(true);
                txtdoors.setText("Radio: " + radioSystem.obtenerEstado());
            } else {
                Radiotoggle.setText("RADIO OFF");
                Radiotoggle.setBackground(null);
                Radiomode.setEnabled(false);
                txtdoors.setText("Radio: Apagada");
            }
        });
        
        Radiomode.addActionListener(e -> {
            if (radioSystem.isEncendido()) {
                String modo = (String) Radiomode.getSelectedItem();
                radioSystem.setModo(modo);
                txtdoors.setText("Radio: " + radioSystem.obtenerEstado());
            }
        });
    }
    
    private void setupOtherControls() {
        windshieldspeed.addActionListener(e -> {
            String velocidad = (String) windshieldspeed.getSelectedItem();
            switch(velocidad) {
                case "0": limpiaparabrisas.cambiarVelocidad(Velocidad.APAGADO); break;
                case "1": limpiaparabrisas.cambiarVelocidad(Velocidad.BAJA); break;
                case "2": limpiaparabrisas.cambiarVelocidad(Velocidad.MEDIA); break;
                case "3": limpiaparabrisas.cambiarVelocidad(Velocidad.ALTA); break;
            }
        });
    }
    
    // actualizacion del display
    private void updateLockDisplay() {
        if (sistemaAlarma.isPuertasBloq()) {
            SegurosActivoInactivo.setText("UNLOCK");
            SegurosActivoInactivo.setBackground(Color.ORANGE);
            jLabel4.setText("Estado: BLOQUEADO");
        } else {
            SegurosActivoInactivo.setText("LOCK");
            SegurosActivoInactivo.setBackground(Color.GREEN);
            jLabel4.setText("Estado: DESBLOQUEADO");
        }
    }
    
    private void updatePuertasDisplay() {
        // Actualizar estado visual de todas las puertas
        PuertaIzqDelantera.setSelected(controlPuertas.isPuertaIzqAdelante());
        PuertaDerDelantera.setSelected(controlPuertas.isPuertaDerAdelante());
        PuertaIzqTrasera.setSelected(controlPuertas.isPuertaIzqAtras());
        PuertaDerTrasera.setSelected(controlPuertas.isPuertaDerAtras());
        Cajuela.setSelected(controlPuertas.isCajuela());
        Capo.setSelected(controlPuertas.isCapo());
        
        // Actualizar textos y colores
        updateDoorDisplay(PuertaIzqDelantera);
        updateDoorDisplay(PuertaDerDelantera);
        updateDoorDisplay(PuertaIzqTrasera);
        updateDoorDisplay(PuertaDerTrasera);
        updateDoorDisplay(Cajuela);
        updateDoorDisplay(Capo);
    }
    
    private void updateDoorDisplay(JToggleButton door) {
        if (door.isSelected()) {
            door.setText("ABIERTA");
            door.setBackground(Color.RED);
        } else {
            door.setText("CERRADA");
            door.setBackground(Color.GREEN);
        }
    }
    
    private void updateFrontLightsDisplay() {
        if (lucesDelanteras.isEncendidas()) {
            Color color = lucesDelanteras.getModo().equals("largas") ? Color.WHITE : Color.YELLOW;
            LuzDelantera1.setBackground(color);
            LuzDelantera2.setBackground(color);
        } else {
            LuzDelantera1.setBackground(Color.DARK_GRAY);
            LuzDelantera2.setBackground(Color.DARK_GRAY);
        }
    }
    
    private void updateTrunkLightsDisplay() {
        if (lucesTraseras.isEncendidas() || lucesTraseras.isFrenoActivado()) {
            LuzTrasera1.setBackground(Color.RED);
            LuzTracera2.setBackground(Color.RED);
        } else {
            LuzTrasera1.setBackground(Color.DARK_GRAY);
            LuzTracera2.setBackground(Color.DARK_GRAY);
        }
    }
    
    private void updateTemperaturaDisplay() {
        jLabel5.setText("Temperatura: " + sistemaClima.getTemperaturaInter() + "°C");
    }
    
    private void updateCinturonDisplay(JToggleButton button, String posicion) {
        if (button.isSelected()) {
            button.setText(posicion + " ✓");
            button.setBackground(Color.GREEN);
        } else {
            button.setText(posicion + " ✗");
            button.setBackground(Color.RED);
        }
    }
    
    private void updateACDisplay() {
        if (sistemaClima.isAirec()) {
            acToggle.setText("A/C ON");
            acToggle.setBackground(Color.CYAN);
            acToggle.setSelected(true);
        } else {
            acToggle.setText("A/C OFF");
            acToggle.setBackground(null);
            acToggle.setSelected(false);
        }
    }
    
    private void updateCalefaccionDisplay() {
        if (sistemaClima.isCalefaccion()) {
            calefaccion.setText("HEAT ON");
            calefaccion.setBackground(Color.RED);
            calefaccion.setSelected(true);
        } else {
            calefaccion.setText("HEAT OFF");
            calefaccion.setBackground(null);
            calefaccion.setSelected(false);
        }
    }
    
    private void startCombustibleConsumption() {
        combustibleTimer = new Timer(5000, e -> { // Cada 5 segundos
            float consumo = 0.5f; // Consumo base
            
            // Aumentar consumo si sistemas están encendidos
            if (sistemaClima.isAirec() || sistemaClima.isCalefaccion()) {
                consumo += 0.2f;
            }
            if (ventiladorSystem.encendidoV()) {
                consumo += 0.1f;
            }
            if (radioSystem.isEncendido()) {
                consumo += 0.05f;
            }
            
            sistemaCombustible.setNivel(sistemaCombustible.getNivel() - consumo);
            
            // Alerta de combustible bajo
            if (sistemaCombustible.getNivel() < 10) {
                showMessage("¡Combustible bajo!");
            }
            
            // Si se acaba el combustible, apagar motor
            if (sistemaCombustible.getNivel() <= 0) {
                motorVehiculo.apagarM();
                EngineStartStop.setSelected(false);
                EngineStartStop.setText("Engine Start");
                txtluz.setText("Motor: SIN COMBUSTIBLE");
                combustibleTimer.stop();
            }
        });
        combustibleTimer.start();
    }
    
    private void increaseCombustibleConsumption() {
        // Consumo adicional al acelerar
        sistemaCombustible.setNivel(sistemaCombustible.getNivel() - 0.1f);
    }
    
    // Parpadeo intermitentes
    private void setupBlinkTimers() {
        leftBlinkTimer = new Timer(500, e -> {
            leftBlinkState = !leftBlinkState;
            Color color = leftBlinkState ? Color.ORANGE : Color.DARK_GRAY;
            InterIzqDelantera.setBackground(color);
            InterIzqTrasera.setBackground(color);
            intermitentesIzq.alternarEstado();
        });
        
        rightBlinkTimer = new Timer(500, e -> {
            rightBlinkState = !rightBlinkState;
            Color color = rightBlinkState ? Color.ORANGE : Color.DARK_GRAY;
            InterDerDelantera.setBackground(color);
            InterDerTrasera.setBackground(color);
            intermitentesDer.alternarEstado();
        });
        
        emergencyBlinkTimer = new Timer(500, e -> {
            emergencyBlinkState = !emergencyBlinkState;
            Color color = emergencyBlinkState ? Color.ORANGE : Color.DARK_GRAY;
            InterIzqDelantera.setBackground(color);
            InterDerDelantera.setBackground(color);
            InterIzqTrasera.setBackground(color);
            InterDerTrasera.setBackground(color);
            lucesEmergencia.alternarEstado();
        });
    }
    
    private void toggleLeftBlinker() {
        // Parar emergencia si está activa
        if (emergencyBlinkTimer.isRunning()) {
            Emergency.setSelected(false);
            toggleEmergencyLights();
        }
        
        if (intermitentesIzq.isActivadas()) {
            intermitentesIzq.desactivar();
            leftBlinkTimer.stop();
            InterIzqDelantera.setBackground(Color.DARK_GRAY);
            InterIzqTrasera.setBackground(Color.DARK_GRAY);
            IntermitenteIzquierda.setText("Left");
        } else {
            intermitentesIzq.activar();
            leftBlinkTimer.start();
            IntermitenteIzquierda.setText("Left ON");
        }
    }
    
    private void toggleRightBlinker() {
        // Parar emergencia si está activa
        if (emergencyBlinkTimer.isRunning()) {
            Emergency.setSelected(false);
            toggleEmergencyLights();
        }
        
        if (intermitentesDer.isActivadas()) {
            intermitentesDer.desactivar();
            rightBlinkTimer.stop();
            InterDerDelantera.setBackground(Color.DARK_GRAY);
            InterDerTrasera.setBackground(Color.DARK_GRAY);
            IntermitenteDerecha.setText("Right");
        } else {
            intermitentesDer.activar();
            rightBlinkTimer.start();
            IntermitenteDerecha.setText("Right ON");
        }
    }
    
    private void toggleEmergencyLights() {
        // Parar intermitentes individuales
        if (leftBlinkTimer.isRunning()) {
            IntermitenteIzquierda.setSelected(false);
            toggleLeftBlinker();
        }
        if (rightBlinkTimer.isRunning()) {
            IntermitenteDerecha.setSelected(false);
            toggleRightBlinker();
        }
        
        if (lucesEmergencia.isActivadas()) {
            lucesEmergencia.desactivar();
            emergencyBlinkTimer.stop();
            InterIzqDelantera.setBackground(Color.DARK_GRAY);
            InterDerDelantera.setBackground(Color.DARK_GRAY);
            InterIzqTrasera.setBackground(Color.DARK_GRAY);
            InterDerTrasera.setBackground(Color.DARK_GRAY);
            Emergency.setText("Emergency");
        } else {
            lucesEmergencia.activar();
            emergencyBlinkTimer.start();
            Emergency.setText("Emergency ON");
        }
    }
    
    private void setupIndividualLightButtons() {
        LuzDelantera1.addActionListener(e -> toggleSingleLight(LuzDelantera1, "Luz delantera izquierda"));
        LuzDelantera2.addActionListener(e -> toggleSingleLight(LuzDelantera2, "Luz delantera derecha"));
        LuzTrasera1.addActionListener(e -> toggleSingleLight(LuzTrasera1, "Luz trasera izquierda"));
        LuzTracera2.addActionListener(e -> toggleSingleLight(LuzTracera2, "Luz trasera derecha"));
        
        InterIzqDelantera.addActionListener(e -> toggleLeftBlinker());
        InterDerDelantera.addActionListener(e -> toggleRightBlinker());
        InterIzqTrasera.addActionListener(e -> toggleLeftBlinker());
        InterDerTrasera.addActionListener(e -> toggleRightBlinker());
    }
    
    private void toggleSingleLight(javax.swing.JButton lightButton, String lightName) {
        if (lightButton.getBackground() == null || lightButton.getBackground().equals(Color.DARK_GRAY)) {
            lightButton.setBackground(Color.YELLOW);
            System.out.println(lightName + " encendida");
        } else {
            lightButton.setBackground(Color.DARK_GRAY);
            System.out.println(lightName + " apagada");
        }
    }
    
    private void updateAllInterfaceStates() {
        // Estados iniciales del Speed ProgressBar
        Speed.setMinimum(0);
        Speed.setMaximum(100);
        Speed.setValue(20); // Temperatura inicial del motor
        
        // Colores iniciales de las luces - todas apagadas
        LuzDelantera1.setBackground(Color.DARK_GRAY);
        LuzDelantera2.setBackground(Color.DARK_GRAY);
        LuzTrasera1.setBackground(Color.DARK_GRAY);
        LuzTracera2.setBackground(Color.DARK_GRAY);
        InterIzqDelantera.setBackground(Color.DARK_GRAY);
        InterDerDelantera.setBackground(Color.DARK_GRAY);
        InterIzqTrasera.setBackground(Color.DARK_GRAY);
        InterDerTrasera.setBackground(Color.DARK_GRAY);
        
        // Hacer botones opacos para mostrar colores
        makeButtonsOpaque();
        
        // Estados iniciales de texto
        txtluz.setText("Motor: APAGADO");
        txtgps1.setText("GPS: INACTIVO");
        jLabel4.setText("Estado: DESBLOQUEADO");
        
        // Configurar ComboBoxes
        setupComboBoxes();
        
        // Estados iniciales de puertas
        updatePuertasDisplay();
        
        // Estados iniciales de temperatura
        updateTemperaturaDisplay();
        
        // Estados iniciales de cinturones
        updateAllSeatBelts();
    }
    
    private void makeButtonsOpaque() {
        // Hacer todos los botones de luces visibles
        LuzDelantera1.setOpaque(true);
        LuzDelantera2.setOpaque(true);
        LuzTrasera1.setOpaque(true);
        LuzTracera2.setOpaque(true);
        InterIzqDelantera.setOpaque(true);
        InterDerDelantera.setOpaque(true);
        InterIzqTrasera.setOpaque(true);
        InterDerTrasera.setOpaque(true);
        
        // Hacer opacos los botones de control también
        EngineStartStop.setOpaque(true);
        GPSOnOff.setOpaque(true);
        AlarmaONOFF.setOpaque(true);
        SegurosActivoInactivo.setOpaque(true);
        acToggle.setOpaque(true);
        calefaccion.setOpaque(true);
        Radiotoggle.setOpaque(true);
        
        // Botones de puertas
        PuertaIzqDelantera.setOpaque(true);
        PuertaDerDelantera.setOpaque(true);
        PuertaIzqTrasera.setOpaque(true);
        PuertaDerTrasera.setOpaque(true);
        Cajuela.setOpaque(true);
        Capo.setOpaque(true);
        
        // Botones de cinturones
        CinturonConductor.setOpaque(true);
        CinturonCopiloto.setOpaque(true);
        CinturonAtrasIzq.setOpaque(true);
        CinturonAtrasDer.setOpaque(true);
        CinturonAtrasCent.setOpaque(true);
    }
    
    private void setupComboBoxes() {
        // Configurar ComboBox de luces si no está configurado
        if (Luces.getItemCount() == 0) {
            Luces.addItem("Off");
            Luces.addItem("Cortas");
            Luces.addItem("Largas");
        }
        
        // Configurar ComboBox de ventiladores
        if (VentiladoresVelocidad.getItemCount() == 0) {
            VentiladoresVelocidad.addItem("0");
            VentiladoresVelocidad.addItem("1");
            VentiladoresVelocidad.addItem("2");
            VentiladoresVelocidad.addItem("3");
            VentiladoresVelocidad.addItem("4");
        }
        
        // Configurar ComboBox de radio
        if (Radiomode.getItemCount() == 0) {
            Radiomode.addItem("AM");
            Radiomode.addItem("FM");
            Radiomode.addItem("Bluetooth");
        }
        
        // Configurar ComboBox de limpiaparabrisas
        if (windshieldspeed.getItemCount() == 0) {
            windshieldspeed.addItem("0");
            windshieldspeed.addItem("2");
            windshieldspeed.addItem("4");
            windshieldspeed.addItem("6");
        }
        
        // Inicializar estados
        Radiomode.setEnabled(false); // Radio inicialmente apagada
    }
    
    private void updateAllSeatBelts() {
        updateCinturonDisplay(CinturonConductor, "CONDUCTOR");
        updateCinturonDisplay(CinturonCopiloto, "COPILOTO");
        updateCinturonDisplay(CinturonAtrasIzq, "TRAS IZQ");
        updateCinturonDisplay(CinturonAtrasDer, "TRAS DER");
        updateCinturonDisplay(CinturonAtrasCent, "TRAS CEN");
    }
    
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Aviso del Sistema", JOptionPane.INFORMATION_MESSAGE);
    }
    
    //Estado completo del sistema
    public String getEstadoCompleto() {
        StringBuilder estado = new StringBuilder();
        estado.append("=== ESTADO DEL VEHÍCULO ===\n");
        estado.append("Motor: ").append(motorVehiculo.isEstadoMotor() ? "ENCENDIDO" : "APAGADO").append("\n");
        estado.append("Temperatura Motor: ").append(motorVehiculo.getTemperaturaMotor()).append("°C\n");
        estado.append("GPS: ").append(gpsSystem.isGpsActivo() ? "ACTIVO" : "INACTIVO").append("\n");
        estado.append("Alarma: ").append(sistemaAlarma.getEstadoSistema()).append("\n");
        estado.append("Puertas abiertas: ").append(controlPuertas.getCantPuertasAbiertas()).append("\n");
        estado.append("Combustible: ").append(String.format("%.1f", sistemaCombustible.getNivel())).append("%\n");
        estado.append("A/C: ").append(sistemaClima.isAirec() ? "ON" : "OFF").append("\n");
        estado.append("Calefacción: ").append(sistemaClima.isCalefaccion() ? "ON" : "OFF").append("\n");
        estado.append("Temperatura Interior: ").append(sistemaClima.getTemperaturaInter()).append("°C\n");
        estado.append("Ventilador: Nivel ").append(ventiladorSystem.getVelocidadVen().getValor()).append("\n");
        estado.append("Radio: ").append(radioSystem.obtenerEstado()).append("\n");
        estado.append("Cinturones: ").append(controlCinturones.todosAbrochados() ? "Todos abrochados" : "Revisar").append("\n");
        estado.append("Limpiaparabrisas: ").append(limpiaparabrisas.getVelocidadActual().getVelocidades()).append("\n");
        estado.append("Freno de mano: ").append(sensorReversa.isFrenoDeManoActivado() ? "PUESTO" : "QUITADO").append("\n");
        estado.append("Reversa: ").append(sensorReversa.isEnReversa() ? "SÍ" : "NO").append("\n");
        
        return estado.toString();
    }
    
    // Mostrar estado
    public void imprimirEstado() {
        System.out.println(getEstadoCompleto());
    }
    
    // Adicionales
    
    /**
     * Método para simular el llenado de combustible
     * Puedes conectarlo a un botón si quieres
     */
    public void llenarTanque() {
        sistemaCombustible.llenarTanque();
        showMessage("Tanque lleno - Combustible: 100%");
    }
    
    /**
     * Método para activar modo valet (solo puertas delanteras)
     */
    public void activarModoValet() {
        // Cerrar puertas traseras y cajuela
        controlPuertas.cerrarPuertas("izq_atras");
        controlPuertas.cerrarPuertas("der_atras");
        controlPuertas.cerrarPuertas("cajuela");
        
        // Activar seguros
        sistemaAlarma.setPuertasBloq(true);
        controlPuertas.setSegurosActiv(true);
        
        updatePuertasDisplay();
        updateLockDisplay();
        showMessage("Modo Valet activado - Solo puertas delanteras disponibles");
    }
    
    /**
     * Método para modo de emergencia
     */
    public void activarModoEmergencia() {
        // Activar luces de emergencia
        if (!lucesEmergencia.isActivadas()) {
            Emergency.setSelected(true);
            toggleEmergencyLights();
        }
        
        // Desbloquear todas las puertas
        sistemaAlarma.setPuertasBloq(false);
        controlPuertas.setSegurosActiv(false);
        
        // Abrir todas las puertas
        controlPuertas.abrirTodasLasPuertas();
        
        updatePuertasDisplay();
        updateLockDisplay();
        showMessage("MODO EMERGENCIA ACTIVADO");
    }
    
    /**
     * Método para verificar estado antes de arrancar
     */
    private boolean verificarCondicionesArranque() {
        // Verificar combustible
        if (sistemaCombustible.getNivel() <= 0) {
            showMessage("No se puede arrancar: Sin combustible");
            return false;
        }
        
        // Verificar cinturón del conductor
        if (!controlCinturones.conductorAbrochado()) {
            showMessage("Advertencia: Cinturón del conductor desabrochado");
            // Permitir arranque pero con advertencia
        }
        
        // Verificar freno de mano
        if (!sensorReversa.isFrenoDeManoActivado()) {
            showMessage("Advertencia: Freno de mano no activado");
        }
        
        return true;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtluz = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        EngineStartStop = new javax.swing.JToggleButton();
        Accelerate = new javax.swing.JButton();
        Brake = new javax.swing.JButton();
        Speed = new javax.swing.JProgressBar();
        txtgps = new javax.swing.JLabel();
        GPSOnOff = new javax.swing.JToggleButton();
        txtgps1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        Luces = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        IntermitenteIzquierda = new javax.swing.JToggleButton();
        IntermitenteDerecha = new javax.swing.JToggleButton();
        InterIzqDelantera = new javax.swing.JButton();
        InterDerDelantera = new javax.swing.JButton();
        LuzDelantera1 = new javax.swing.JButton();
        LuzDelantera2 = new javax.swing.JButton();
        LuzTrasera1 = new javax.swing.JButton();
        LuzTracera2 = new javax.swing.JButton();
        InterIzqTrasera = new javax.swing.JButton();
        InterDerTrasera = new javax.swing.JButton();
        Emergency = new javax.swing.JToggleButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        Capo = new javax.swing.JToggleButton();
        PuertaIzqDelantera = new javax.swing.JToggleButton();
        PuertaDerDelantera = new javax.swing.JToggleButton();
        PuertaIzqTrasera = new javax.swing.JToggleButton();
        PuertaDerTrasera = new javax.swing.JToggleButton();
        Cajuela = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        SegurosActivoInactivo = new javax.swing.JToggleButton();
        AlarmaONOFF = new javax.swing.JToggleButton();
        Alldoorsopen = new javax.swing.JButton();
        Alldoorsclosed = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        acToggle = new javax.swing.JToggleButton();
        calefaccion = new javax.swing.JToggleButton();
        tempmas = new javax.swing.JButton();
        tempmenos = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        VentiladoresVelocidad = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        CinturonConductor = new javax.swing.JToggleButton();
        CinturonAtrasIzq = new javax.swing.JToggleButton();
        CinturonAtrasCent = new javax.swing.JToggleButton();
        CinturonAtrasDer = new javax.swing.JToggleButton();
        CinturonCopiloto = new javax.swing.JToggleButton();
        jLabel7 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        Radiotoggle = new javax.swing.JButton();
        Radiomode = new javax.swing.JComboBox<>();
        txtdoors = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        windshieldspeed = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtluz.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtluz.setText("Lights");

        EngineStartStop.setText("Engine Start/Stop");

        Accelerate.setText("Accelerate");

        Brake.setText("Brake");

        txtgps.setText("GPS");

        GPSOnOff.setText("On/Off");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(EngineStartStop, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtgps))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Accelerate, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Brake, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addComponent(Speed, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(GPSOnOff, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Accelerate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Brake, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Speed, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EngineStartStop, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtgps)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(GPSOnOff)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txtgps1.setText("Motor & GPS");

        Luces.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Off", "Low", "High Beams", "Automatic" }));
        Luces.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LucesActionPerformed(evt);
            }
        });

        jLabel2.setText("Flashings");
        jLabel2.setToolTipText("");

        IntermitenteIzquierda.setText("Left");
        IntermitenteIzquierda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IntermitenteIzquierdaActionPerformed(evt);
            }
        });

        IntermitenteDerecha.setText("Right");

        InterIzqDelantera.setOpaque(true);

        Emergency.setText("Emergency");
        Emergency.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EmergencyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(InterIzqTrasera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(Luces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(IntermitenteIzquierda)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(IntermitenteDerecha))))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jLabel2)))
                                .addGap(51, 51, 51))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(Emergency, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(InterIzqDelantera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LuzDelantera1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LuzTrasera1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InterDerDelantera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LuzDelantera2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LuzTracera2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InterDerTrasera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(InterDerDelantera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LuzDelantera2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LuzTracera2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(Luces, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IntermitenteIzquierda)
                            .addComponent(IntermitenteDerecha))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(Emergency))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(InterIzqDelantera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LuzDelantera1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LuzTrasera1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(InterIzqTrasera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(InterDerTrasera, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jLabel1.setText("Doors");

        Capo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CapoActionPerformed(evt);
            }
        });

        PuertaIzqTrasera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuertaIzqTraseraActionPerformed(evt);
            }
        });

        PuertaDerTrasera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PuertaDerTraseraActionPerformed(evt);
            }
        });

        jLabel4.setText("Temperature");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(PuertaIzqTrasera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(PuertaDerTrasera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(PuertaIzqDelantera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(PuertaDerDelantera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Cajuela, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Capo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(15, 15, 15))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Capo, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PuertaIzqDelantera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PuertaDerDelantera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PuertaIzqTrasera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PuertaDerTrasera, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Cajuela, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jLabel4))
        );

        jLabel3.setText("Alarm & Lock");

        SegurosActivoInactivo.setText("Lock On/Off");
        SegurosActivoInactivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SegurosActivoInactivoActionPerformed(evt);
            }
        });

        AlarmaONOFF.setText("Alarm On/Off");

        Alldoorsopen.setText("Open All");

        Alldoorsclosed.setText("Close All");
        Alldoorsclosed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AlldoorsclosedActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SegurosActivoInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AlarmaONOFF, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(Alldoorsopen, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Alldoorsclosed, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(SegurosActivoInactivo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AlarmaONOFF, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Alldoorsopen)
                    .addComponent(Alldoorsclosed))
                .addGap(0, 9, Short.MAX_VALUE))
        );

        acToggle.setText("A/C");

        calefaccion.setText("Heat");

        tempmas.setText("+");
        tempmas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempmasActionPerformed(evt);
            }
        });

        tempmenos.setText("-");
        tempmenos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempmenosActionPerformed(evt);
            }
        });

        jLabel5.setText("Fans");

        VentiladoresVelocidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Off", "1", "2", "3", "4" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tempmas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(acToggle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(calefaccion)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel5))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(VentiladoresVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(tempmenos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(acToggle, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(calefaccion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(VentiladoresVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tempmas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tempmenos, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setText("Seat Belt");

        CinturonConductor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CinturonConductorActionPerformed(evt);
            }
        });

        CinturonAtrasIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CinturonAtrasIzqActionPerformed(evt);
            }
        });

        CinturonAtrasCent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CinturonAtrasCentActionPerformed(evt);
            }
        });

        CinturonAtrasDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CinturonAtrasDerActionPerformed(evt);
            }
        });

        CinturonCopiloto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CinturonCopilotoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(CinturonAtrasIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CinturonAtrasCent, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CinturonAtrasDer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(CinturonConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(CinturonCopiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CinturonConductor, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CinturonCopiloto, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CinturonAtrasIzq, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CinturonAtrasCent, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(CinturonAtrasDer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Others");

        Radiotoggle.setText("Radio");

        Radiomode.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bluetooth", "AM", "FM" }));

        txtdoors.setText("On/Off");

        jLabel9.setText("Windshield");

        windshieldspeed.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "0", "2", "4", "6" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Radiotoggle, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Radiomode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(txtdoors))
                    .addComponent(windshieldspeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Radiotoggle, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtdoors))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Radiomode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(windshieldspeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addGap(96, 96, 96)
                                        .addComponent(txtluz, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtgps1)
                                .addGap(128, 128, 128)
                                .addComponent(jLabel1)
                                .addGap(110, 110, 110)
                                .addComponent(jLabel3)
                                .addGap(34, 34, 34)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel6))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(8, 8, 8))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtgps1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtluz))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LucesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LucesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LucesActionPerformed

    private void SegurosActivoInactivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SegurosActivoInactivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SegurosActivoInactivoActionPerformed

    private void IntermitenteIzquierdaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IntermitenteIzquierdaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IntermitenteIzquierdaActionPerformed

    private void EmergencyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EmergencyActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_EmergencyActionPerformed

    private void CapoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CapoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CapoActionPerformed

    private void PuertaIzqTraseraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuertaIzqTraseraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PuertaIzqTraseraActionPerformed

    private void PuertaDerTraseraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PuertaDerTraseraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PuertaDerTraseraActionPerformed

    private void tempmasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempmasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tempmasActionPerformed

    private void tempmenosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempmenosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tempmenosActionPerformed

    private void CinturonConductorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CinturonConductorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CinturonConductorActionPerformed

    private void CinturonAtrasIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CinturonAtrasIzqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CinturonAtrasIzqActionPerformed

    private void CinturonAtrasCentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CinturonAtrasCentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CinturonAtrasCentActionPerformed

    private void CinturonAtrasDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CinturonAtrasDerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CinturonAtrasDerActionPerformed

    private void CinturonCopilotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CinturonCopilotoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CinturonCopilotoActionPerformed

    private void AlldoorsclosedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AlldoorsclosedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AlldoorsclosedActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Accelerate;
    private javax.swing.JToggleButton AlarmaONOFF;
    private javax.swing.JButton Alldoorsclosed;
    private javax.swing.JButton Alldoorsopen;
    private javax.swing.JButton Brake;
    private javax.swing.JToggleButton Cajuela;
    private javax.swing.JToggleButton Capo;
    private javax.swing.JToggleButton CinturonAtrasCent;
    private javax.swing.JToggleButton CinturonAtrasDer;
    private javax.swing.JToggleButton CinturonAtrasIzq;
    private javax.swing.JToggleButton CinturonConductor;
    private javax.swing.JToggleButton CinturonCopiloto;
    private javax.swing.JToggleButton Emergency;
    private javax.swing.JToggleButton EngineStartStop;
    private javax.swing.JToggleButton GPSOnOff;
    private javax.swing.JButton InterDerDelantera;
    private javax.swing.JButton InterDerTrasera;
    private javax.swing.JButton InterIzqDelantera;
    private javax.swing.JButton InterIzqTrasera;
    private javax.swing.JToggleButton IntermitenteDerecha;
    private javax.swing.JToggleButton IntermitenteIzquierda;
    private javax.swing.JComboBox<String> Luces;
    private javax.swing.JButton LuzDelantera1;
    private javax.swing.JButton LuzDelantera2;
    private javax.swing.JButton LuzTracera2;
    private javax.swing.JButton LuzTrasera1;
    private javax.swing.JToggleButton PuertaDerDelantera;
    private javax.swing.JToggleButton PuertaDerTrasera;
    private javax.swing.JToggleButton PuertaIzqDelantera;
    private javax.swing.JToggleButton PuertaIzqTrasera;
    private javax.swing.JComboBox<String> Radiomode;
    private javax.swing.JButton Radiotoggle;
    private javax.swing.JToggleButton SegurosActivoInactivo;
    private javax.swing.JProgressBar Speed;
    private javax.swing.JComboBox<String> VentiladoresVelocidad;
    private javax.swing.JToggleButton acToggle;
    private javax.swing.JToggleButton calefaccion;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JButton tempmas;
    private javax.swing.JButton tempmenos;
    private javax.swing.JLabel txtdoors;
    private javax.swing.JLabel txtgps;
    private javax.swing.JLabel txtgps1;
    private javax.swing.JLabel txtluz;
    private javax.swing.JComboBox<String> windshieldspeed;
    // End of variables declaration//GEN-END:variables
}
