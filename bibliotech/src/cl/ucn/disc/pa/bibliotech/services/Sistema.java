package cl.ucn.disc.pa.bibliotech.services;

import cl.ucn.disc.pa.bibliotech.model.Libro;
import cl.ucn.disc.pa.bibliotech.model.Socio;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.princeton.cs.stdlib.StdOut;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The Sistema.
 *
 * @author Gabriel López
 */
public final class Sistema {

    /**
     * Procesador de JSON.
     */
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * The list of Socios.
     */
    private Socio[] socios;

    /**
     * The list of Libros.
     */
    private Libro[] libros;

    //Lista de libros disponibles
    private Libro[] disponibles;

    /**
     * Socio en el sistema.
     */
    public Socio socio;

    /**
     * The Sistema.
     */
    public Sistema() throws IOException {

        // no hay socio logeado.
        this.socios = new Socio[0];
        this.libros = new Libro[0];
        this.socio = null;

        // carga de los socios y libros.
        try {
            this.cargarInformacion();
        } catch (FileNotFoundException ex) {
            // no se encontraron datos, se agregar los por defecto.

            // creo un socio
            Socio newSocio = new Socio("John", "Doe", "john.doe@ucn.cl", 1, "john123");
            this.socios = Utils.append(this.socios,newSocio);

            // creo un libro y lo agrego al arreglo de libros.
            Libro libro1 = new Libro("1541910777", "Head First Java: A Brain-Friendly Guide", " Kathy Sierra", "Programming Languages");
            this.validarIsbn(libro1);

            // creo otro libro y lo agrego al arreglo de libros.
            Libro libro2 = new Libro("1491910771", "Effective Java", "Joshua Bloch", "Programming Languages");
            this.validarIsbn(libro2);

        } finally {
            // guardo la informacion.
            this.guardarInformacion();
        }

    }

    /**
     * Activa (inicia sesion) de un socio en el sistema.
     *
     * @param numeroDeSocio a utilizar.
     * @param contrasenia   a validar.
     */
    public void iniciarSession(final int numeroDeSocio, final String contrasenia) {

        // el numero de socio siempre es positivo.
        if (numeroDeSocio <= 0) {
            throw new IllegalArgumentException("El numero de socio no es valido!");
        }

        for(int i=0;i<socios.length;i++){
            if(this.socios[i].getNumeroDeSocio()==numeroDeSocio && this.socios[i].getContrasenia().equals(contrasenia)){
                this.socio=socios[i];
            } else{
                throw new IllegalArgumentException("La clave o número de socio no corresponden a un usuario registrado, verifique sus datos e intente nuevamente.");
            }
        }
    }

    /**
     * Cierra la session del Socio.
     */
    public void cerrarSession() {
        this.socio = null;
    }

    /**
     * Metodo que mueve un libro de los disponibles y lo ingresa a un Socio.
     *
     * @param isbn del libro a prestar.
     */
    public void realizarPrestamoLibro(final String isbn) throws IOException {
        // el socio debe estar activo.
        if (this.socio == null) {
            throw new IllegalArgumentException("Socio no se ha logeado!");
        }

        // busco el libro.
        Libro libro = this.buscarLibro(isbn);

        // si no lo encontre, lo informo.
        if (libro == null) {
            throw new IllegalArgumentException("Libro con isbn " + isbn + " no existe o no se encuentra disponible.");
        }

        // agrego el libro al socio.
        this.socio.agregarLibro(libro);

        eliminarDisponible(isbn);

        // se actualiza la informacion de los archivos
        this.guardarInformacion();

    }

    /**
     * Obtiene un String que representa el listado completo de libros disponibles.
     *
     * @return the String con la informacion de los libros disponibles.
     */
    public String obtegerCatalogoLibros() {

        StringBuilder sb = new StringBuilder();
        for (Libro libro : this.libros) {
            sb.append("Titulo    : ").append(libro.getTitulo()).append("\n");
            sb.append("Autor     : ").append(libro.getAutor()).append("\n");
            sb.append("ISBN      : ").append(libro.getIsbn()).append("\n");
            sb.append("Categoria : ").append(libro.getCategoria()).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Metodo que busca un libro en los libros disponibles.
     *
     * @param isbn a buscar.
     * @return el libro o null si no fue encontrado.l
     */
    private Libro buscarLibro(final String isbn) {
        // recorro el arreglo de libros.
        for (Libro libro : this.libros) {
            // si lo encontre, retorno el libro.
            if (libro.getIsbn().equals(isbn)) {
                return libro;
            }
        }
        // no lo encontre, retorno null.
        return null;
    }

    /**
     * Lee los archivos libros.json y socios.json.
     *
     * @throws FileNotFoundException si alguno de los archivos no se encuentra.
     */
    private void cargarInformacion() throws FileNotFoundException {

        // trato de leer los socios y los libros desde el archivo.
        this.socios = GSON.fromJson(new FileReader("socios.json"), Socio[].class);
        this.libros = GSON.fromJson(new FileReader("libros.json"), Libro[].class);
    }

    /**
     * Guarda los arreglos libros y socios en los archivos libros.json y socios.json.
     *
     * @throws IOException en caso de algun error.
     */
    private void guardarInformacion() throws IOException {

        // guardo los socios.
        try (FileWriter writer = new FileWriter("socios.json")) {
            GSON.toJson(this.socios, writer);
        }

        // guardo los libros.
        try (FileWriter writer = new FileWriter("libros.json")) {
            GSON.toJson(this.libros, writer);
        }

    }

    public String obtenerDatosSocioLogeado() {
        if (this.socio == null) {
            throw new IllegalArgumentException("No hay un Socio logeado");
        }

        return "Nombre: " + this.socio.getNombreCompleto() + "\n"
                + "Correo Electronico: " + this.socio.getCorreoElectronico();
    }

    /**
     * Metodo auxiliar para buscar a un socio dado su número.
     * @param numeroSocio
     */
    private void buscarSocioNumero(int numeroSocio){
        for(int i=0;i<socios.length;i++){
            if(socios[i].getNumeroDeSocio()==numeroSocio){break;}else{continue;}
        }
        StdOut.println("El usuario no existe o no esta registrado.");
    }

    /**
     * Método que ayuda con la validación de la contraseña
     * @param numeroSocio
     * @param clave del socio
     */
    private void validarClave(int numeroSocio, String clave){
        for(int i=0;i<socios.length;i++){
            if (socios[i].getNumeroDeSocio()==numeroSocio){
                if(socios[i].getContrasenia().equalsIgnoreCase(clave)){
                    break;
                }else{
                    StdOut.println("La contraseña es incorrecta");
                }
            }
        }
    }

    private Socio socioEncontrado(int numeroSocio, String clave){
        for(int i=0;i<socios.length;i++){
            if (socios[i].getNumeroDeSocio()==numeroSocio){
                if(socios[i].getContrasenia().equalsIgnoreCase(clave)){
                    return this.socio = socios[i];
                }else{
                    StdOut.println("La contraseña es incorrecta");
                }
            }
        }
        return null;
    }

    private void eliminarDisponible(String isbn){
        for (int i=0;i<this.disponibles.length;i++){
            if(this.disponibles[i].getIsbn().equalsIgnoreCase(isbn)){
                this.disponibles[i]=null;
                break;
            }
        }
    }

    /**
     * Método que valida si el ISBN no se repite; agrega el libro en caso de no repetirse.
     * @param libro - Libro a analizar.
     */
    private void validarIsbn(Libro libro){
        for(int i = 0; i<this.libros.length;i++){
            if(libros[i].getIsbn().equalsIgnoreCase(libro.getIsbn())){StdOut.println("No pueden haber 2 o mas libros con el mismo ISBN.");}
            else{
                this.libros=Utils.append(this.libros,libro);
                this.disponibles=Utils.append(this.disponibles,libro);
            }
        }
    }
}
