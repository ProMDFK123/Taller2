package cl.ucn.disc.pa.bibliotech.model;

import java.util.regex.Pattern;

/**
 * Clase que representa un Libro.
 *
 * @author Programacion Avanzada.
 */
public final class Libro {

    /**
     * The ISBN.
     */
    private String isbn;

    /**
     * The Titulo.
     */
    private String titulo;

    /**
     * The Author.
     */
    private String autor;

    /**
     * The Categoria
     */
    private String categoria;

    //Calificación del libro
    private double calificacion;
    private int cantidadCalificaciones;

    //Patron de ISBN
    private static final Pattern ISBN = Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");

    /**
     * The Constructor.
     *
     * @param isbn      del libro.
     * @param titulo    del libro.
     * @param autor     del libro
     * @param categoria del libro.
     */
    public Libro(final String isbn, final String titulo, final String autor, final String categoria) {
        //Validación del ISBN.
        this.validarIsbn(isbn);
        this.isbn = isbn;

        // validación del titulo.
        if (titulo == null || titulo.length() == 0) {
            throw new IllegalArgumentException("Titulo no valido!");
        }
        this.titulo = titulo;

        //validación del autor.
        if(autor==null || autor.length()==0){
            throw new IllegalArgumentException("Autor/a no vaido/a!");
        }
        this.autor = autor;

        //Validación de la categoria.
        if(categoria==null || categoria.length()==0){
            throw new IllegalArgumentException("Categoria no vaida!");
        }
        this.categoria = categoria;

        //Definición de la calificación inicial.
        this.calificacion=0;
        this.cantidadCalificaciones=0;
    }

    /**
     * @return the ISBN.
     */
    public String getIsbn() {
        return this.isbn;
    }

    /**
     * @return the titulo.
     */
    public String getTitulo() {
        return this.titulo;
    }

    /**
     * @return the autor.
     */
    public String getAutor() {
        return this.autor;
    }

    /**
     * @return the categoria.
     */
    public String getCategoria() {
        return this.categoria;
    }

    /**
     * @return calificaión del libro.
     */
    public double getCalificacion() {
        return calificacion;
    }

    /**
     * Validador del ISBN.
     * @param isbn - ISBN a validar.
     */
    private void validarIsbn(final String isbn){
        if(!ISBN.matcher(isbn).matches()){
            throw new IllegalArgumentException("El ISBN no es valido.");
        }
    }

    /**
     * @return veces que el libro fue calificado.
     */
    public int getCantidadCalificaciones() {
        return cantidadCalificaciones;
    }

    /**
     * @param cantidadCalificaciones nuevo valor.
     */
    public void setCantidadCalificaciones(int cantidadCalificaciones) {
        this.cantidadCalificaciones = cantidadCalificaciones;
    }

    /**
     * @param calificacion actualización de la calificación.
     */
    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }
}
