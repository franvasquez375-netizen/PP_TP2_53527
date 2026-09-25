package modelo.actividades;

import modelo.Estudiante;
import modelo.certificacion.Certificable;

public class Curso extends Actividad implements Certificable {
    //ATRIBUTOS
    private int nivel;

    //CONSTRUCTOR
    public Curso(int id, String titulo, int cupo, int nivel){
        super(id, titulo, cupo);
        this.nivel = nivel;
    }

    //1ER MÉTODO
    @Override
    public double calcularCostoMateriales(){
        switch (this.nivel){
            case 1: return 1000;
            case 2: return 2000;
            case 3: return 3000;
            default: return 0.0;
        }
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
                " participó en el curso \"" +getTitulo() + "\".");
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
