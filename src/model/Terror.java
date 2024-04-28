package model;

/* Esta clase hereda de la clase Libro porque es un tipo de dicha clase, hereda sus atributos y su método de mostrar los datos  */
public class Terror extends Libro {

    private String clasificacion;


    public Terror() {
    }

    public Terror(String ISBN, String titulo, String autor, int numeroPaginas, String clasificacion) {
        super(ISBN, titulo, autor, numeroPaginas);
        this.clasificacion = clasificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Calificación = " + clasificacion);
    }


}
