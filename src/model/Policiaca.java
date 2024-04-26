package model;

import java.util.Scanner;

/* Esta clase hereda de la clase Libro porque es un tipo de dicha clase, hereda sus atributos y su método de mostrar los datos  */
/* Entre sus atributos está el Enum de la clase Trama porque ésta tiene como datos fijos misterio e intriga. Además, empleamos
* un array de Strings para recoger a los personajes de este tipo de novela  */
public class Policiaca extends Libro{

    private Trama trama;

    private String [] personajes;

    public Policiaca() {
    }

    public Policiaca(String ISBN, String titulo, String autor, int numeroPaginas, Trama trama, String[] personajes) {
        super(ISBN, titulo, autor, numeroPaginas);
        this.trama = trama;
        this.personajes = personajes;
    }

    public String[] getPersonajes() {
        return personajes;
    }

    public void setPersonajes(String[] personajes) {
        this.personajes = personajes;
    }

    public Trama getTrama() {
        return trama;
    }

    public void setTrama(Trama trama) {
        this.trama = trama;
    }

    @Override
    public void mostrarDatos() {
        super.mostrarDatos();
        System.out.println("Personajes: ");
        for (int i = 0; i < personajes.length; i++)
        {
            System.out.println(personajes[i]);
        }
        System.out.println("Trama = " + trama);
    }
}
