package modelo;

import modelo.actividades.Actividad;
import modelo.actividades.Charla;
import modelo.actividades.Curso;
import modelo.actividades.Taller;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class EventoUniversitario implements Serializable {
    //Atributos
    private final String Id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos;
    //CLASES RELACIONADAS
    private Sala sala;
    //Creación de la lista
    private List<Actividad> actividades = new ArrayList<>();
    //FIN CLASES RELACIONADAS

    //Iniciador estático
    static{
        cantidadEventos = 0;
        System.out.println("Inicializador estático: se cargó la clase EventoUniversitario");
    }

    //1ER MÉTODO, CONSTRUCTOR
    public EventoUniversitario (String id, String titulo, double costo,boolean gratuito){
        this.Id = id;
        setTitulo(titulo); //Lo uso porque hay validación para que el titulo no esté vacío.
        this.costoBase = gratuito ? 0 : costo;
        this.gratuito = gratuito;
        cantidadEventos++;
        this.actividades = new ArrayList<>();
    }

    //2DO MÉTODO, CONSTRUCTOR DE COPIA
    public EventoUniversitario(EventoUniversitario otroEvento){
        this(
                otroEvento.Id + "-COPIA",
                otroEvento.titulo,
                otroEvento.costoBase,
                otroEvento.gratuito
        );
        this.sala = otroEvento.sala;
    }

    //3ER MÉTODO, Calculo el costo base, si el evento no es gratuito
    public double  calcularCostoEstimado(double costoBase){
        if(gratuito){
            return 0.0;
        }
        double costoTotal = costoBase;
        for (Actividad actividad : actividades){
            costoTotal += actividad.calcularCostoMateriales(); //Ligado dinámico
        }
        return costoTotal*1.21;
    }
    //4TO MÉTODO, Asigno la sala
    public void asignarSala(Sala sala){
        this.sala = sala;
    }

    //5TO MÉTODO, Constructor de la actividad y la añado a la lista. Relación de composición actividad/es-Evento
    public void crearActividad(int id, String titulo, int cupo, String tipoActividad){
        Scanner scanner = new Scanner(System.in);
        switch (tipoActividad){
            case "charla":
                System.out.println("Ingrese el nombre del disertante para la charla " + titulo + " :");
                String disertante = scanner.nextLine();
                Actividad charla = new Charla(id, titulo, disertante, cupo);
                this.actividades.add(charla);
                break;
            case "taller":
                System.out.println("El taller requiere notebook S/N?");
                String respuesta = scanner.nextLine().trim().toLowerCase();
                boolean requiereNotebook = false;
                if (respuesta.equals("s") || respuesta.equals("si") || respuesta.equals("sí")) {
                    requiereNotebook = true;
                }
                Actividad taller = new Taller(id, titulo, requiereNotebook, cupo);
                this.actividades.add(taller);
                break;
            case "curso":
                System.out.println("Ingrese el nivel del curso: ");
                int nivel = scanner.nextInt();
                scanner.nextLine();
                Actividad curso = new Curso(id, titulo, cupo, nivel);
                this.actividades.add(curso);
                break;
            default:
                System.out.println("Error: Tipo de actividad no reconocido.");
        }
    }

    //6TO MÉTODO
    public  <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo){
        List<T> resultado = new ArrayList<>();
        for(Actividad actividad : actividades){
            if(tipo.isInstance(actividad)){
                resultado.add(tipo.cast(actividad));
            }
        }
        return resultado;
    }

    //7MO MÉTODO
    public double calcularCostoMateriales(List<? extends Actividad> actividades){
        double total = 0.0;
        for(Actividad actividad : actividades){
            total += actividad.calcularCostoMateriales();
        }
        return total;
    }

    //8VO MÉTODO, Mostrar los datos de los eventos creados y su copia
    public void mostrarDatos(){
        System.out.println("===================================");
        System.out.println("Id: "+Id);
        System.out.println("Título: "+titulo);
        System.out.println("Costo estimado: "+calcularCostoEstimado(costoBase));
        System.out.println("Sala asignada: "+sala.getNombre());
        System.out.println("\nActividades:");
        System.out.println("------------------------------------");
        for (Actividad actividad : actividades){
            actividad.mostrarIdentificacion(); //Invocación polimórfica: la subclase concreta define su tipo en tiempo de ejecución
            actividad.mostrarInscripciones();
        }
        System.out.println("==============================================");
    }

    //9NO MÉTODO
    public boolean persistirEvento() throws IOException {
        String nombreArchivo = "evento_" + this.Id + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream (nombreArchivo))){
            oos.writeObject(this);
            return true;
        }
    }

    //10MO MÉTODO
    public EventoUniversitario recuperarEvento(String id) throws IOException, ClassNotFoundException{
        String nombreArchivo = "evento_"+ id +".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream (nombreArchivo))){
            return (EventoUniversitario) ois.readObject();
        }
    }

    //11VO MÉTODO
    public static int getCantidadEventos(){
        return cantidadEventos;
    }

    public String getId() {
        return Id;
    }

    public static void setCantidadEventos(int cantidadEventos) {
        EventoUniversitario.cantidadEventos = cantidadEventos;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public boolean isGratuito() {
        return gratuito;
    }

    public void setGratuito(boolean gratuito) {
        this.gratuito = gratuito;
    }

    public double getCostoBase() {
        return costoBase;
    }

    public void setCostoBase(double costoBase) {
        this.costoBase = costoBase;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        if(titulo != null && !titulo.isBlank()) {
            this.titulo = titulo;
        }
    }
    //declaro get para obtener la lista de actividades
    public List<Actividad> getActividades(){
        return Collections.unmodifiableList(actividades);
    }
}
