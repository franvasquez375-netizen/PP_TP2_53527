package hilos;

import modelo.EventoUniversitario;
import modelo.Inscripcion;
import modelo.actividades.Actividad;

public class EnvioTicketsThread extends Thread{
    //Atributo
    public EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento){
        super("Hilo-Envio-Tickets");
        this.evento = evento;
    }

    @Override
    public void run(){
        System.out.println(" ["+getName()+"] Inicio del envío de tickets.");
        for(Actividad actividad : evento.getActividades()){
            for(Inscripcion inscripcion : actividad.getInscripciones()){
                if("CONFIRMADA" .equals (inscripcion.getEstado())){
                    inscripcion.getTicket().enviarTicket();
                    try{
                        Thread.sleep(500);
                    }catch(InterruptedException e){
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        System.out.println(" ["+getName()+"] Fin del envío de tickets.");
    }
}
