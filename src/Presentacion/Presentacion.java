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

        try {
            do {
                System.out.println("**** Catálogo de películas ****");
                mostrarMenu();
                System.out.println("¿Qué desea hacer ? = ");
                opcionElegida = teclado.nextInt();
                System.out.println("\nHa seleccionado la opción " + opcionElegida + "\n");
                gestionarCasuistica(opcionElegida);
            } while (opcionElegida >= 1 && opcionElegida < 5);
        } catch (Exception e) {
            System.out.println("El carácter introducido no es un número, vuelva a intentarlo...");
            System.exit(0);
        }
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

        System.out.println("Escribe el nombre de la película que deseas ingresar = ");
        String nombre = teclado.next();
        Pelicula esNombreDisponible = compararPeliculas(nombre);

        if (esNombreDisponible != null) {
            System.out.println("La película que ha introducido ya está en el sistema...\n");
        } else {
            Pelicula nuevapelicula = new Pelicula(nombre);
            listaPeliculas.add(nuevapelicula);
            guardarPelicula(nuevapelicula);
        }

    }

    private static void guardarPelicula(Pelicula pelicula) {

        File archivoPeliculas = new File(rutaArchivo);
        PrintWriter pw = null;

        try {

            pw = new PrintWriter(new FileWriter(archivoPeliculas, true));
            pw.println(pelicula.getNombre());

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

        } else {
            System.out.println("\nNo hay ninguna película guardada en el sistema, por favor introduzca una....\n");
        }

    }

    private static Pelicula compararPeliculas(String nombre) {

        Pelicula peliculaEncontrada = null;
        for (Pelicula pelicula : listaPeliculas) {
            if (pelicula.getNombre().equalsIgnoreCase(nombre)) {
                peliculaEncontrada = pelicula;
            }
        }
        return peliculaEncontrada;
    }

    private static void buscarPelicula() {

        System.out.println("Introduce el nombre de la película que quieres buscar = ");
        String nombre = teclado.next();

        Pelicula peliculaBuscada = compararPeliculas(nombre);
        if (peliculaBuscada == null) {
            System.out.println("La película que estás buscando no se ha encontrado...");
        } else {
            for(int i=0; i<listaPeliculas.size(); i++){
                if(listaPeliculas.get(i) == peliculaBuscada){
                    System.out.println("Se ha encontrado la película " + peliculaBuscada.getNombre() + " en la linea " + (i+1) + " \n");
                }
            }
        }
    }

}
