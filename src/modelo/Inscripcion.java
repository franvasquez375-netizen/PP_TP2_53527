package modelo;

import modelo.actividades.Actividad;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    //Atributos
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;
    private Actividad actividad;
    //Clase miembro
    private TicketDeAcceso ticket;

    //Clase anidada
    public final class TicketDeAcceso implements Serializable{
        //Atributos de la clase anidada
        private String idTicket;
        private LocalDate fechaEmision;

        public TicketDeAcceso(){
            this.idTicket = "TICKET-"+actividad.getId()+" - "+estudiante.getLegajo()+ " - "+ System.currentTimeMillis();
            this.fechaEmision = LocalDate.now();
            System.out.println("Ticket generado para la inscripción: "+ idTicket);
        }

        public void enviarTicket(){
            System.out.println("Enviando ticket "+ idTicket+" al estudiante "+ estudiante.getNombre()+" para la actividad "+actividad.getTitulo());
        }

    }

    //Método para confimar inscripción
    public void confirmar(){
        this.estado = "CONFIRMADA";
        this.ticket = new TicketDeAcceso();
    }

    public TicketDeAcceso getTicket(){
        return ticket;
    }

    //Constructor
    public Inscripcion(LocalDate fecha, String estado, Estudiante estudiante, Actividad actividad ) {
        this.actividad=actividad;
        this.estudiante=estudiante;
        this.fecha = fecha;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "Fecha=" + fecha +
                "Estado='" + estado + '\'' +
                "Estudiante=" + estudiante +
                "Actividad=" + actividad +
                '}';
    }

    //Getter y Setters

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }
}
