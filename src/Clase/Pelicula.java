package Clase;

public class Pelicula {

    private String nombre;

//	Constructor

    public Pelicula(String nombre) {

        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Pelicula [nombre=" + nombre + "]";
    }

}
