package model;

import controller.Biblioteca;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Entrada {

    public static void main(String[] args) {

        boolean respuestaValida=true;
        boolean repetirPrimerMenu=false;
        boolean numeroValido=false;

        //Creamos una biblioteca y cuatro libros como nos pide el enunciado
        Biblioteca biblioteca=new Biblioteca("Gloria Fuertes","Javier López");
        Terror libro1=new Terror("Dracula","Bram Stoker","1111A",488,"novela vampírica");
        Terror libro2=new Terror("Terrores nocturnos","Emma Entrena","2222A",224,"juvenil");
        Comedia libro3=new Comedia("Yo,Tu,Éx","Miguel Ángel Bargueño","3333A",336,"humor inteligente");
        Policiaca libro4=new Policiaca("El ladrón de miedos","Luis David Pérez","4444A",269,Trama.misterio, new String[]{"Marta Escudero", "Rodrigo"});
        int numeroLibros=0;

        /*Como se puede obtener información de un libro sin que pertenezca a un catálogo creamos el ArrayList "librosSinBibilioteca" que será independiente
        al catálogo pero irá recogiendo todos los libros que se vayan generando*/
        ArrayList<Libro>librosSinBiblioteca=new ArrayList<>();
        librosSinBiblioteca.add(libro1);
        librosSinBiblioteca.add(libro2);
        librosSinBiblioteca.add(libro3);
        librosSinBiblioteca.add(libro4);
        System.out.println("Hay cuatro libros para consultar");
        System.out.println();

        /*Creamos un primer menú que nos da la opción de consultar datos de un libro buscado por su ISBN, crear una bibilioteca o salir de la aplicación. Éste es el primer menú y
        * también será siempre el último que aparezca antes de cerrar la aplicación*/
        do{
            boolean libroExiste=false;
            System.out.println("¿Qué quieres hacer?:");
            System.out.println("1. Ver datos de un libro");
            System.out.println("2. Crear una biblioteca");
            System.out.println("3. Salir");
            Scanner scanner=new Scanner(System.in);
            int menuPrincipal= scanner.nextInt();
            if(menuPrincipal==1)
            {
                repetirPrimerMenu=true;
                System.out.println("¿Qué libro quieres buscar?  Indica el ISBN:");
                String ISBN= scanner.next();
                for (Libro item:librosSinBiblioteca)
                {
                    if(ISBN.equalsIgnoreCase(item.getISBN()))
                    {
                        libroExiste=true;
                        item.mostrarDatos();
                    }
                }
                if(!libroExiste)
                {
                    System.out.println("No existe un libro con ISBN "+ISBN);
                }
                System.out.println();

            //Dentro del siguiente else-if entra lo referente a la gestión del catálogo
            } else if (menuPrincipal==2)
            {
                repetirPrimerMenu=true;
                System.out.println("Creando biblioteca...");
                System.out.println("Has creado la siguiente biblioteca:");
                biblioteca.mostrarDatos();
                System.out.println();
                //Incluimos el siguiente do-while para volver a este punto en caso de que el usuario introduzca una respuesta no válida
                    do{
                        System.out.println("¿Quieres crear un catálogo para tu biblioteca? SI o NO");
                        Scanner scanner2=new Scanner (System.in);
                        String respuesta=scanner2.next();

                        /*En caso de que sí quiera crear un catálogoa (new ArrayList), antes de construir éste se le pregunta al usuario la capacidad que quiere
                        que tenga el catálogo, y a partir de ese número construiremos también el array estático que comentamos en la linea 63 de Biblioteca */
                        if(respuesta.equalsIgnoreCase("SI"))
                        {
                            respuestaValida=true;
                            //Introducimos el siguiente do while para captar posibles respuestas no válidas del usuario (0 o número negativo):
                            do{
                                numeroValido=true;
                                System.out.println("¿Cuántos libros quieres que tenga tu catálogo?");
                                numeroLibros=scanner.nextInt();
                                if(numeroLibros<0)
                                {
                                    numeroValido=false;
                                    System.out.println("Introduce un valor válido");
                                } else if (numeroLibros==0)
                                {
                                    numeroValido=false;
                                    System.out.println("No se puede crear un catálogo sin libros");
                                }
                                else
                                {
                                    biblioteca.construirCatalogo(numeroLibros);
                                }
                                }while(!numeroValido);

                            System.out.println();
                /*Con el siguiente try-catch queremos captar el posible error en el registro de los 4 primeros libros que nos pide el
                * enunciado si la capacidad del catálogo introducida por el usuario es menor a 4. Si no hay problema en este primer registro de libros, el posible
                * error futuro de registro de libros por tener el catálogo lleno lo captaremos con el try-catch incluido en el método agregarLibro de la clase
                *  Biblioteca (linea 103) */
                            try{
                                Libro[]arrayCatalogoLibros=new Libro[numeroLibros];
                                arrayCatalogoLibros[0]=libro1;
                                biblioteca.registroLibrosIniciales(libro1);
                                arrayCatalogoLibros[1]=libro2;
                                biblioteca.registroLibrosIniciales(libro2);
                                arrayCatalogoLibros[2]=libro2;
                                biblioteca.registroLibrosIniciales(libro3);
                                arrayCatalogoLibros[3]=libro2;
                                biblioteca.registroLibrosIniciales(libro4);
                                arrayCatalogoLibros= biblioteca.getArrayCatalogo();

                                }catch(ArrayIndexOutOfBoundsException e)
                                {
                                    System.out.println("Capturando error ArrayIndexOutOfBoundsException");
                                    System.out.println("No hay suficiente espacio en el catálogo para el registro de los 4 primeros libros");
                                    System.out.println();
                                }
                        }
                        else if (!respuesta.equalsIgnoreCase("NO"))
                        {
                            respuestaValida=false;
                            System.out.println("Respuesta no válida");
                        }
                        }while(!respuestaValida);

                        /*A continuación se presenta un menú con las distintas funcionalidades del catálogo. La gestión de las distintas opciónes se
                         realizará con un switch*/
                        /*Incluimos el siguiente do-while para volver a este punto en caso de que el usuario introduzca una respuesta no válida o quiera
                          realizar otra operación*/
                        do
                        {
                            System.out.println("¿Qué operación deseas realizar?:");
                            System.out.println("1. Mostrar todos los libros del catálogo");
                            System.out.println("2. Agregar libro a catálogo");
                            System.out.println("3. Sacar libro del catálogo");
                            System.out.println("4. Buscar un libro en el catálogo");
                            int operacion=scanner.nextInt();
                            Libro libro=null;

                            /*Incluimos aquí un try-catch para captar un posible error RunTime en el caso de que se quiera hacer alguna función del catálogo
                            * sin que éste exista  */
                            try {
                                    switch (operacion)
                                    {
                                        case 1:
                                            System.out.println("El catálogo de esta bibilioteca tiene actualmente "+biblioteca.consultarNumeroLibrosCatalogo()+" libros");
                                            biblioteca.mostrarTodosLibrosCatalogo();
                                            break;

                                        case 2:
                                            /*El siguiente do while se establece para volver al siguiente menú en caso de que el usuario introduzca una
                                            respuesta no válida*/
                                            /*Usamos un switch para los tres tipos de género de libro. En cada uno se pedirán los datos del libro a registrar
                                            y se creará un nuevo objeto tipo género correspondiente (Terror, comedia o policiaca) del libro*/
                                            do {
                                                System.out.println("Indica el género del libro:");
                                                System.out.println("Terror: 1");
                                                System.out.println("Comedia: 2");
                                                System.out.println("Policiaca: 3");
                                                int genero = scanner.nextInt();
                                                Scanner scanner1=new Scanner(System.in);
                                                switch (genero)
                                                {
                                                    case 1:
                                                        respuestaValida = true;
                                                        libro = new Terror();
                                                        pedirDatosComunes(libro);
                                                        System.out.println("Clasificacion:");
                                                        String clasificacion = scanner1.nextLine();
                                                        libro = new Terror(libro.getTitulo(), libro.getAutor(), libro.getISBN(), libro.getNumeroPaginas(), clasificacion);
                                                        break;

                                                    case 2:
                                                        respuestaValida = true;
                                                        libro = new Comedia();
                                                        pedirDatosComunes(libro);
                                                        System.out.println("Tipo de humor:");
                                                        String tipoHumor = scanner1.nextLine();
                                                        libro = new Comedia(libro.getTitulo(), libro.getAutor(), libro.getISBN(), libro.getNumeroPaginas(), tipoHumor);
                                                        break;

                                                    case 3:
                                                        respuestaValida = true;
                                                        libro = new Policiaca();
                                                        pedirDatosComunes(libro);
                                                        System.out.println("Trama (misterio o intriga):");
                                                        Trama trama = Trama.valueOf(scanner1.next());
                                                        System.out.println("¿Cuántos personajes tiene el libro?");
                                                        int numeroPersonajes = scanner1.nextInt();
                                                        String[] arrayPersonajes = new String[numeroPersonajes];
                                                        for (int i = 0; i < numeroPersonajes; i++)
                                                        {
                                                            System.out.println("Introduce el nombre del personaje " + (i + 1) + ":");
                                                            Scanner scanner2=new Scanner(System.in);
                                                            String nombre = scanner2.nextLine();
                                                            arrayPersonajes[i] = nombre;
                                                        }
                                                        libro = new Policiaca(libro.getTitulo(), libro.getAutor(), libro.getISBN(), libro.getNumeroPaginas(), trama, arrayPersonajes);
                                                        break;

                                                    default:
                                                        respuestaValida = false;
                                                        System.out.println("La opción introducida no es válida");
                                                        System.out.println();
                                                }
                                                } while (!respuestaValida);

                                            /*Establecemos un if-else para que se agregue el libro al arrayList del catálogo y al arrayList de libros sin catálogo
                                            * en caso de que el ISBN no se encuentre en el catálogo; en caso contrario no se agregará nada    */
                                            if (!biblioteca.estaLibro(libro.getISBN()))
                                            {
                                                biblioteca.agregarLibro(libro);
                                                librosSinBiblioteca.add(libro);
                                            } else
                                            {
                                                System.out.println("Ya hay un libro con este ISBN registrado en el catálogo");
                                            }
                                            break;

                                    case 3:
                                        System.out.println("¿Qué libro quieres sacar?  Indica el ISBN:");
                                        String ISBN = scanner.next();
                                        if (biblioteca.estaLibro(ISBN))
                                        {
                                            biblioteca.sacarLibro(ISBN);
                                            System.out.println("El libro con ISBN " + ISBN + " se ha sacado correctamente del catálogo");
                                        }
                                        else
                                        {
                                            System.out.println("El libro con el ISBN introducido no está en el catálogo");
                                        }
                                        break;
                                    case 4:
                                        System.out.println("¿Qué libro quieres buscar?  Indica el ISBN:");
                                        ISBN = scanner.next();
                                        biblioteca.buscarLibro(ISBN);
                                        break;
                                    default:
                                        System.out.println("La opción introducida no es válida");
                                    }
                                }
                                catch(RuntimeException e)
                                {
                                    System.out.println("Capturando error de ejecución");
                                }
                        }while(otraOperacion());
            }
            //La siguiente es la única respuesta tras la que no se volverá al menú principal y se saldrá de la aplicación totalmente:
            else if(menuPrincipal==3)
            {
                repetirPrimerMenu=false;
                System.out.println("Gracias por utilizar nuestro servicio");
                System.out.println();
            }
            else
            {
                System.out.println("La opción introducida no existe");
                repetirPrimerMenu=true;
            }
            } while(repetirPrimerMenu);


    /*Una vez salimos de la aplición de gestión de la biblioteca, si un catálogo ha sido creado los libros del mismo se exportarán
    al fichero libros.obj y después serán leídos por pantalla */
    /*Introducimos una excepción de nulo para captar la posibilidad de que no se pueda escribir ni leer ningún fichero de libros
    * porque no se haya creado un catálogo */

        System.out.println("Se va a realizar la lectura del fichero de libros creado a partir del último catálogo...");
    try{
        biblioteca.escrituraObjeto();
        System.out.println();
        biblioteca.lecturaObjeto();
        }catch(NullPointerException e)
        {
            System.out.println("Error de nulo");
            System.out.println("No se ha creado ningún catálogo por tanto no se puede crear ningún fichero de libros ");
        }
    }

    //Hemos creado este método fuera del main para pedir en una sola línea de código datos comunes a todos los libros independientemente de su género
    public static void pedirDatosComunes(Libro libro)
    {
        boolean numeroValido=false;
        Scanner scanner=new Scanner(System.in);
        System.out.println("Introduce todos los datos");
        System.out.println("Título:");
        String titulo= scanner.nextLine();
        libro.setTitulo(titulo);
        System.out.println("Autor:");
        String autor=scanner.nextLine();
        libro.setAutor(autor);
        System.out.println("ISBN:");
        String ISBN=scanner.next();
        libro.setISBN(ISBN);
        do{
            System.out.println("Número de páginas:");
            int numeroPaginas= scanner.nextInt();
            if(numeroPaginas>0)
            {
                numeroValido=true;
                libro.setNumeroPaginas(numeroPaginas);
            }else if (numeroPaginas==0)
            {
                numeroValido=false;
                System.out.println("No puede haber un libro sin páginas");
            }else
            {
                numeroValido=false;
                System.out.println("Introduce un valor válido");
            }
            }while(!numeroValido);
            }

    /*El siguiente método permite volver al menú de gestión del catálogo en caso de que el usuario quiera realizar varias
    operaciones con el mismo*/
    public static boolean otraOperacion(){

        boolean respuestaValida=false;

        do{
            System.out.println("¿Deseas realizar otra operación en esta biblioteca? SI/NO");
            respuestaValida=false;
            Scanner scanner1=new Scanner(System.in);
            String otra=scanner1.next();
            if(otra.equalsIgnoreCase("si"))
            {
                respuestaValida=true;
                return true;
            }
            else if (otra.equalsIgnoreCase("no"))
            {
                System.out.println("Cerrando aplicación de la biblioteca...");
                System.out.println("Redirigiendo al menú principal...");
                System.out.println();
                respuestaValida=true;
                return false;
            }
            else
            {
                System.out.println("Respuestá no válida");
                respuestaValida=false;
            }
            }while(!respuestaValida);
            return false;
    }
}
