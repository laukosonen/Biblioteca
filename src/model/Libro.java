package model;

import java.io.Serializable;
import java.util.Scanner;

/*Como queremos guardar el objeto Libro en el fichero libros.obj implementamos en esta clase la interfaz serializable */
public abstract class Libro implements Serializable {

    /*Como hemos hecho el objeto Libro serializado es conveniente asignarle un número único representado por el serialVersion UID */
    private static final long serialVersionUID = 6529685098267757690L;
    private String titulo,autor,ISBN;
    private int numeroPaginas;

    public Libro() {
    }

    public Libro(String titulo, String autor, String ISBN, int numeroPaginas) {
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.numeroPaginas = numeroPaginas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public void mostrarDatos(){

        System.out.println("Título = " + titulo);
        System.out.println("Autor = " + autor);
        System.out.println("ISBN = " + ISBN);
        System.out.println("Número de páginas = " + numeroPaginas);
    }

    /*Creo el método toString para que el objeto Libro pueda ser representado en formato JSON */
    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", numeroPaginas=" + numeroPaginas +
                '}';
    }
}
