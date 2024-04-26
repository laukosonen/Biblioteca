package model;

import java.util.Scanner;

/* Esta clase hereda de la clase Libro porque es un tipo de dicha clase, hereda sus atributos y su método de mostrar los datos  */
public class Comedia extends Libro {

    private String tipoHumor;

    public Comedia() {
    }

    public Comedia(String ISBN, String titulo, String autor, int numeroPaginas, String tipoHumor) {
        super(ISBN, titulo, autor, numeroPaginas);
        this.tipoHumor = tipoHumor;
    }

    public String getTipoHumor() {
        return tipoHumor;
    }

    public void setTipoHumor(String tipoHumor) {
        this.tipoHumor = tipoHumor;
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Tipo de humor = " + tipoHumor);
    }
}
