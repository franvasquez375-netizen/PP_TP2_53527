package modelo.actividades;

public class Charla extends Actividad {
    //ATRIBUTO
    private String disertante;

    //CONSTRUCTOR
    public Charla(int id, String titulo, String disertante, int cupo){
        super (id, titulo, cupo);
        this.disertante = disertante;
    }

    //1ER MÉTODO
    @Override
    public double calcularCostoMateriales(){
        return 0.0;
    }

    //2DO MÉTODO
    @Override
    public String getTipo(){
        return this.getClass().getSimpleName();
    }

    public String getDisertante() {
        return disertante;
    }

    public void setDisertante(String disertante) {
        this.disertante = disertante;
    }
}
