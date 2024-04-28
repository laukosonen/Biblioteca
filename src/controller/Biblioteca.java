package controller;
import model.Libro;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;

/*Creamos la clase Biblioteca, que será nuestro controller pues a través de ella podremos hacer todas las funcionalidades relacionadas con
* la biblioteca. La establecemos como genérica extendiendo de Libro para que restrinja el tipo de libro que queramos registrar según el tipo
* de biblioteca creada (gernérica o especializada en algún género) */
public class Biblioteca<T extends Libro> {

    /*Declaramos sus atributos, entre ellos estarán las clase anidada Catálogo porque el catálogo depende de la biblioteca para existir y también
    * hemos establecido un array de libros que usaremos a modo de array auxiliar al catálogo cuando sepamos la capacidad del catálogo*/
    private String nombre, director;
    private Catalogo catalogo;

    /*Agregamos el constructor vacío, otro con los atributos nombre de la biblioteca y director de la bibilioteca y todos los getter y setter*/
    public Biblioteca() {
    }

    public Biblioteca(String nombre, String director) {
        this.nombre = nombre;
        this.director = director;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    //Método que muestra datos de la biblioteca:
    public void mostrarDatos(){
        System.out.println("Nombre = " + nombre);
        System.out.println("Director = " + director);
    }
    /*Método que construye el catálogo creando un nuevo arrayList con objetos de tipo Libro y un array estático del tamaño del número de libros
    pasado por parámetro*/
    public void construirCatalogo(int numeroLibros){
        this.catalogo=new Catalogo();
        System.out.println("Catálogo creado");
    }
    //Método para añadir al catálogo los 4 primeros libros que nos pide el enunciaco crear
    public void registroLibrosIniciales(T libro)
    {
        catalogo.listaLibros.add(libro);
    }

    //Método que nos devuelve el número de libros que contiene el catálogo en el momento de la consulta
    public int consultarNumeroLibrosCatalogo(){

        int librosEnCatalogo=0;
        for (T item: catalogo.listaLibros)
        {
            if (item!=null)
            {
                librosEnCatalogo++;
            }
        }
        return librosEnCatalogo;
    }

    //Método que nos muestra los datos de todos los libros que hay registrados en el catálogo
    public void mostrarTodosLibrosCatalogo(){
        System.out.println("El catálogo tiene actualmente "+consultarNumeroLibrosCatalogo()+" libros");
        System.out.println("Estos son los libros:");
        System.out.println();
        for (T item: getCatalogo().listaLibros)
        {
            item.mostrarDatos();
            System.out.println();
        }
    }

    /*Método que agrega un libro al catálogo pasado por parámetro siempre que haya espacio en el mismo, para lo cual nos apoyamos de un try-catch que nos permitirá captar un posible error
    de índice de array fuera de los límites del mismo si intentamos añadir un libro a un catálogo que ya tiene todas sus posiciones ocupadas*/
    public void agregarLibro(T libro,int numeroLibros) {

        try{
            Object[]arrayCatalogo=new Object[numeroLibros];
            arrayCatalogo[consultarNumeroLibrosCatalogo()]=libro;
            catalogo.listaLibros.add(libro);
            }catch(ArrayIndexOutOfBoundsException e){
                System.out.println("Capturando error deArrayIndexOutOfBoundsException");
                System.out.println("No hay espacio en el catálogo para registrar más libros");
                System.out.println();
            }
    }

    //Método para sacar un libro del catálogo siempre que el ISBN pasado por parámetro coincida con alguno de los registrados en el arrayList del catálogo
    public void sacarLibro (String ISBN)
    {
        for (Libro item:catalogo.listaLibros)
        {
         if(item.getISBN().equalsIgnoreCase(ISBN))
         {
             catalogo.listaLibros.remove(item);
             break;
         }
        }
    }

    /*Método para buscar un libro del catálogo siempre que el ISBN pasado por parámetro coincida con alguno de los registrados en el arrayList del catálogo, en cuyo
    * caso mostrará los datos del item (libro) con la coincidencia de ISBN. En caso de que no sea encontrado en el arrayList captaremos con un try-catch
    * el error de nulo por la inexistencia del libro   */
    public void buscarLibro(String ISBN)
    {
        Libro libroAux=null;
        boolean encontrado=false;

        for (T item:catalogo.listaLibros)
        {
                if(item.getISBN().equalsIgnoreCase(ISBN))
                {
                    encontrado=true;
                    System.out.println("A continuación se muestran los datos del libro:");
                    item.mostrarDatos();
                    System.out.println();
                    break;
                }
                else{
                    encontrado=false;
                }
        }
        if(!encontrado)
        {
            try{
                libroAux.setISBN(ISBN);
                libroAux.mostrarDatos();
                System.out.println("El libro con el ISBN introducido no está en el catálogo");
                }catch(NullPointerException e)
                {
                    System.out.println("Caputurando error de nulo");
                    System.out.println("El libro con ISBN "+ISBN+" no existe");
                }
        }
    }


    //Método que nos devuelve un booleano para indicar si el libro con ISBN pasado por parámetro está o no en el catálogo
    public boolean estaLibro(String ISBN)
    {
        boolean libroRegistrado=false;
        for (T item: catalogo.listaLibros)
        {
            if(item.getISBN().equalsIgnoreCase(ISBN))
            {
                libroRegistrado=true;
                break;
            }
        }
        return libroRegistrado;
    }


    /*Catalogo es clase anidada de Bibilioteca porque la primera no puede existir sin la segunda. Declaramos como único atributo el ArrayList de objetos tipo
     Libro que representará el catálogo que tenga la bibilioteca*/
    class Catalogo {

        ArrayList<T>listaLibros;
        public Catalogo() {
            this.listaLibros = new ArrayList<T>();
        }

        public void setListaLibros(ArrayList<T> listaLibros) {
            this.listaLibros = listaLibros;
        }

    }


    /*A continiación se presenta el código para el desarrollo del último punto de la actividad (exportación de los libros del catálogo fichero .obj):*/
    public void escrituraObjeto(){

        /*En la escritura del objeto Libro trabajo con tres objetos (los correspondientes a su flujo de datos: ObjectOutputStream,FileOutputStream
        y File */
        //Inicializamos tanto el ObjectOutputStream como el FileOutputStream a null porque después tendré que cerrarlos

        ObjectOutputStream objectOutputStream=null;
        FileOutputStream fileOutputStream=null;
        File file=new File("src/resources/libros.obj");

        /*Ahora creamos el flujo de datos correspondiente a la escritura del fichero. En el proceso se me obligará a implementar varias excepciones:
        una de posible error de escritura (IOException) y tendremos que cerrar su flujo de datos que a su vez puede dar error de cerrado, y capturamos
        también el posible error de nulo*/
        //Abrimos el flujo de datos en modo salida hacia un flujo de datos en modo salida dirigido hacia nuestro fichero
        /*Escribimos el objeto mediante writeObject que nos permite guardar objetos serializables, como nuestro arrayList del catálogo, ya que está
             formado por objetos de tipo Libro que previamente hemos hecho serializable.*/

        try {
            objectOutputStream=new ObjectOutputStream(new FileOutputStream(file));
            objectOutputStream.writeObject(catalogo.listaLibros);
        }
        catch (IOException e)
        {
            System.out.println("Error de escritura");
        }finally{
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                System.out.println("Error de cerrado");
            }catch(NullPointerException e){
                System.out.println("Cerrado en nulo");
            }
        }

    }

    /*Añadimos también un método de lectura del objeto para que podamos ver por pantalla los objetos del nuestro arrayList guardados en el fichero
    de una manera legible*/
    /*Aquí captaremos posibles errores de lectura, de casteo de la clase, de clase no encontrada, de cerrado del flujo de datos o de nulo  */
    public void lecturaObjeto(){

        ObjectInputStream objectInputStream=null;
        FileInputStream fileInputStream=null;
        File file=new File("src/resources/libros.obj");

        try {
            objectInputStream=new ObjectInputStream(new FileInputStream(file));
            catalogo.listaLibros= (ArrayList<T>) objectInputStream.readObject();
            for (Libro item:catalogo.listaLibros)
            {
                System.out.println(item);
            }
            }
            catch (IOException e)
            {
                System.out.println("Error de lectura");
            }
            catch (ClassCastException e)
            {
                System.out.println("Error en el casteo de la clase");
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("Clase no encontrada");
            } finally
            {
                try {
                    objectInputStream.close();
                    } catch (IOException e)
                    {
                        System.out.println("Error en el cerrado");
                    }
                    catch(NullPointerException e)
                    {
                        System.out.println("Error de nulo");
                    }
            }
    }
}
