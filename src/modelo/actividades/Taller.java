package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

import java.io.Serializable;

public class Taller extends Actividad implements Certificable {
    //ATRIBUTO
    private boolean requiereNotebook;

    //CONSTRUCTOR
    public Taller(int id, String titulo,boolean requiereNotebook, int cupo){
        super(id, titulo, cupo);
        this.requiereNotebook = requiereNotebook;
    }
    //1ER MÉTODO
    @Override
    public double calcularCostoMateriales(){
        if(requiereNotebook){
            return 5000.0;
        }
        return 2000.0;
    }

    //2DO MÉTODO
    @Override
    public String getTipo(){
        return this.getClass().getSimpleName();
    }

    @Override
    public String generarCertificado(Estudiante estudiante){
        return ("Certificado emitido por "+ ENTIDAD_EMISORA+
                ": se deja constancia de que "+ estudiante.getNombre()+
                " participó en el taller \"" +getTitulo() + "\".");
    }

    public boolean isRequiereNotebook() {
        return requiereNotebook;
    }

    public void setRequiereNotebook(boolean requiereNotebook) {
        this.requiereNotebook = requiereNotebook;
    }
}
