package Presentacion;

import Clase.Pelicula;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Presentacion {

    private static Scanner teclado = new Scanner(System.in);
    private static int opcionElegida;
    private static ArrayList<Pelicula> listaPeliculas = new ArrayList<>();
    static String rutaArchivo = "C:\\Users\\chris\\intellij-workspace\\CatalogoDePeliculas\\src\\resources\\peliculas.txt";

    public static void main(String[] args) {

        cargarPeliculasCreadas();

        System.out.println("Bienvenido a la app de Registro de películas \n");
            do {
                System.out.println("**** Catálogo de películas ****");
                mostrarMenu();
                System.out.println("¿Qué desea hacer ? = ");
                try {

                    opcionElegida = teclado.nextInt();
                } catch (Exception e) {
                    System.out.println("El carácter introducido no es un número, vuelva a intentarlo...");
                    System.exit(0);
    //			main(args); --------------> Intento de que no pare la app y requiera de nuevo la información
                }
                System.out.println("\nHa seleccionado la opción " + opcionElegida + "\n");
                gestionarCasuistica(opcionElegida);
            } while (opcionElegida >= 1 && opcionElegida < 5);

    }

    private static void mostrarMenu() {

        System.out.println("\n1.Agregar Pelicula \n" + "2.Mostrar listado de peliculas \n" + "3.Buscar pelicula \n"
                + "4.Salir \n");

    }

    private static void gestionarCasuistica(int opcionElegida) {

        switch (opcionElegida) {
            case 1: {
                crearPelicula();
                break;
            }
            case 2: {
                mostrarPeliculas();
                break;
            }
            case 3: {
                buscarPelicula();
                break;
            }
            case 4: {
                System.out.println("Ha salido con éxito, esperamos verlo de nuevo...");
                System.exit(0);
                break;
            }
            default:
                throw new IllegalArgumentException("Opción no válida" + opcionElegida);
        }

    }

    private static void crearPelicula() {
        String nombre = pedirNombrePelicula();
        Pelicula esNombreDisponible = compararPeliculas(nombre);

        if (esNombreDisponible != null) {
            System.out.println("La película que ha introducido ya está en el sistema...\n");
        } else {
            Pelicula nuevapelicula = new Pelicula(nombre);
            listaPeliculas.add(nuevapelicula);
            guardarPelicula(nuevapelicula);
        }
    }

    private static String pedirNombrePelicula() {
        System.out.println("Escribe el nombre de la película que deseas ingresar = ");
        return teclado.next(".*"); //-------------> A pesar de poner nextLine, no me deja poner la pelicula con espacios.
    }

    private static void guardarPelicula(Pelicula pelicula) {

        File archivoPeliculas = new File(rutaArchivo);
        PrintWriter pw = null;

        try {

            pw = new PrintWriter(new FileWriter(archivoPeliculas, true));
//			pw = new PrintWriter(new FileWriter(new File(rutaArchivo),true));   ->    También lo puedo poner así

            pw.println(pelicula.getNombre());
//--------------------------------------------------- Así las estoy escribiendo en archivo todas de cada vez
//			for (Pelicula i : listaPeliculas) {
//				pw.println(i.getNombre());
//			}
//---------------------------------------------------
//--------------------------------------------------- La misma manera que la anterior pero diferente escrito
//			int i;
//			for(i=0; i<listaPeliculas.size(); i++) {
//				pw.println(listaPeliculas.get(i));
//			}
//----------------------------------------------------
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (archivoPeliculas != null) {
                    pw.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    private static void cargarPeliculasCreadas() {

        BufferedReader br = null;

        try {
            if (!listaPeliculas.isEmpty()) {
                listaPeliculas.clear();
            }

            File nuevoArchivo = new File(rutaArchivo);
            br = new BufferedReader(new FileReader(nuevoArchivo));
//			br = new BufferedReader(new FileReader(new File(rutaArchivo)));  -------> También lo puedo poner así

            String linea = br.readLine();

            if (linea != null) {
                System.out.println("El arhivo ya existe \n");
            } else {
                System.out.println("El erchivo ha sido creado \n");
            }

            while (linea != null) {
                Pelicula nuevaPelicula = new Pelicula(linea);
                listaPeliculas.add(nuevaPelicula);
                linea = br.readLine();
//----------------------------------------------------------------   Me muestra las películas que hay guardadas	separadas por contador
//				int contador = 1;
//				System.out.println(contador + "." + nuevaPelicula.toString() + "\n");
//				contador +=1;
//----------------------------------------------------------------
//----------------------------------------------------------------   Me muestra las peliculas que hay guardadas
//				System.out.println(nuevaPelicula.getNombre());
//----------------------------------------------------------------
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (null != br) {
                    br.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    private static void mostrarPeliculas() {

        if (!listaPeliculas.isEmpty()) {

            System.out.println("\n*** Listado de películas guardadas *** \n");
            listaPeliculas.forEach((pelicula) -> System.out.println(pelicula.getNombre()));
            System.out.println();
//---------------------------------------------------------------------------------------------  También lo puedo escribir así
//			for(Pelicula pelicula : listaPeliculas){
//				System.out.println(pelicula.getNombre());
//			}
//---------------------------------------------------------------------------------------------
        } else {
            System.out.println("\nNo hay ninguna película guardada en el sistema, por favor introduzca una....\n");
        }

    }

    private static Pelicula compararPeliculas(String nombre) {

        Pelicula peliculaEncontrada = null; // ---------------> null (variable que referencia a un objeto se encuentra
                                            //                        sin objeto)
        for (Pelicula pelicula : listaPeliculas) {
            if (pelicula.getNombre().equalsIgnoreCase(nombre)) { // equalsIsIgnoreCase -> No compara de manera absoluta
                peliculaEncontrada = pelicula;                   // es más flexible en el uso de mayúsculas y minúsculas
            }                                                    // si utilizara el operador == compararia sus referencias
        }
        return peliculaEncontrada;
    }

    private static void buscarPelicula() {

        System.out.println("Introduce el nombre de la película que quieres buscar = ");
        String nombre = teclado.next();  //------------------------------ Aquí si creo una pelicula con espacios seguramente
                                         //                               lo tenga que cambiar a nextline
        Pelicula peliculaBuscada = compararPeliculas(nombre);
        if (peliculaBuscada == null) {
            System.out.println("La película que estás buscando no se ha encontrado...");
        } else {
//            for(int i=0; i<listaPeliculas.size(); i++){
//                if(listaPeliculas.get(i) == peliculaBuscada){
//                    System.out.println("Se ha encontrado la película " + peliculaBuscada.getNombre() + " en la linea " + (i+1) + " \n");
//                }
//            }

            //Puedo ponerlo así también

            Pelicula nombrePeli = listaPeliculas.stream().filter(p -> p.getNombre().equals(nombre)).findFirst().orElseThrow();
            System.out.println("Se ha encontrado la película " + nombrePeli.getNombre() + " en la linea " + (listaPeliculas.indexOf(nombrePeli)+1)  + " \n");
        }
    }

}
