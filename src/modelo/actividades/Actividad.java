package modelo.actividades;

import exepciones.CupoExcedidoException;
import modelo.Estudiante;
import modelo.Inscripcion;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Actividad implements Serializable {
    //Atributos
    private int id;
    private String titulo;
    private int cupoMaximo;
    public static final int CUPO_MINIMO;
    private ArrayList<Inscripcion> inscripciones= new ArrayList<>();

    static { //se realiza una vez, para la primer clase creada
        CUPO_MINIMO = 5;
        System.out.println("Inicializar estátic: se cargó la clase actividad.");
    }

    //CONSTRUCTOR
    public Actividad (int id, String titulo, int cupo){
        this.id = id;
        this.titulo = titulo;
        this. cupoMaximo = (cupo > CUPO_MINIMO) ? cupo : CUPO_MINIMO;
        this.inscripciones = new ArrayList<>();
    }

    //1ER MÉTODO
    //Constructor de inscricpin (le paso un estudiante como parametro para que me cree una inscripcion)
    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        if(this.inscripciones.size() >= this.cupoMaximo){
            throw new CupoExcedidoException("No se puede inscribir al estudiante " + estudiante.getNombre() +". Cupo máximo alcanzado.");
        }
        Inscripcion nuevaInscripcion = new Inscripcion(LocalDate.now(), "CONFIRMADO", estudiante,this);
        this.inscripciones.add(nuevaInscripcion);
        return nuevaInscripcion;
    }

    public ArrayList<Inscripcion> getInscripciones(){
        return inscripciones;
    }

    //2DO MÉTODO
    public void mostrarInscripciones(){
        if(inscripciones.isEmpty()){
            System.out.println(" Sin inscripciones registradas.");
            return;
        }
        System.out.println(" Inscripciones registradas:");
        for(Inscripcion inscripcion : inscripciones){
            System.out.println(" " + inscripcion.getFecha() +" - " + inscripcion.getEstado() + " - " + inscripcion.getEstudiante().getNombre() +" (Legajo: " + inscripcion.getEstudiante().getLegajo() + ")" );
        }
    }

    //3ER MÉTODO
    public final void mostrarIdentificacion(){
    System.out.println("- "+ getTipo() + ": " + titulo + "(id=" + id + ")" + " - Cupo máximo: " + cupoMaximo);
    }

    //4TO MÉTODO
    public abstract double calcularCostoMateriales();

    //5TO MÉTODO
    public abstract String getTipo();

    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo == null || titulo.isBlank()){
            return;
        }
        this.titulo = titulo;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }
}
