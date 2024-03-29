package cl.ucn.disc.pa.bibliotech;

import cl.ucn.disc.pa.bibliotech.services.*;
import edu.princeton.cs.stdlib.StdIn;
import edu.princeton.cs.stdlib.StdOut;

import java.io.IOException;
import java.util.Objects;

/**
 * The Main.
 *
 * @author Gabriel López - 21.583.391-7
 */
public final class Main {

    /**
     * The main.
     *
     * @param args to use.
     * @throws IOException en caso de un error.
     */
    public static void main(final String[] args) throws IOException {
        mainMenu();
    }

    /**
     * Primera pantalla que aparece al iniciar el programa
     * @throws IOException en caso de error
     */
    private static void mainMenu() throws IOException {
        // inicializacion del sistema.
        Sistema sistema = new Sistema();

        StdOut.println(sistema.obtegerCatalogoLibros());

        String opcion = null;
        while (!Objects.equals(opcion, "2")) {

            StdOut.println("""
                    [*] Bienvenido a BiblioTech [*]
                                    
                    [1] Iniciar Sesion
                    [2] Salir
                    """);
            StdOut.print("Escoja una opcion: ");
            opcion = StdIn.readLine();

            switch (opcion) {
                case "1" -> iniciarSesion(sistema);
                case "2" -> StdOut.println("¡Hasta Pronto!");
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    /**
     * Inicia la sesion del Socio en el Sistema.
     *
     * @param sistema a utilizar.
     */
    private static void iniciarSesion(final Sistema sistema) throws IOException {
        sistema.socio = sistema.logedIn();

        // mostrar menu principal
        menuPrincipal(sistema);
    }

    /**
     * Método que despliega el menu principal tras iniciar sesión.
     * @param sistema a utilizar.
     * @throws IOException en caso de error.
     */
    private static void menuPrincipal(final Sistema sistema) throws IOException {
        String opcion = null;
        while (!Objects.equals(opcion, "4")) {
            StdOut.println("""
                    [*] BiblioTech [*]
                                        
                    [1] Prestamo de un libro
                    [2] Editar información
                    [3] Calificar libro
                                        
                    [4] Cerrar sesion
                    """);

            StdOut.print("Escoja una opcion: ");
            opcion = StdIn.readLine();

            switch (opcion) {
                case "1" -> menuPrestamo(sistema);
                case "2" -> editarInformacion(sistema);
                case "3" -> calificarlibro(sistema);
                case "4" -> sistema.cerrarSession();
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    /**
     * Método que arroja el menú del prestamo de un libro.
     * @param sistema a utilizar.
     */
    private static void menuPrestamo(Sistema sistema) {
        StdOut.println("[*] Préstamo de un Libro [*]");
        StdOut.println(sistema.obtegerCatalogoLibros());

        StdOut.print("Ingrese el ISBN del libro a tomar prestado: ");
        String isbn = StdIn.readLine();

        try {
            sistema.realizarPrestamoLibro(isbn);
        } catch (IOException ex) {
            StdOut.println("Ocurrio un error, intente nuevamente: " + ex.getMessage());
        }
    }

    /**
     * Método que depliega el menú de editar perfil.
     * @param sistema a utilizar.
     */
    private static void editarInformacion(Sistema sistema) {

        String opcion = null;
        while (!Objects.equals(opcion, "3")) {

            StdOut.println("[*] Editar Perfil [*]");
            StdOut.println(sistema.obtenerDatosSocioLogeado());
            StdOut.println("""               
                    [1] Editar correo Electronico
                    [2] Editar Contraseña
                                        
                    [3] Volver atrás
                    """);
            StdOut.print("Escoja una opción: ");
            opcion = StdIn.readLine();

            switch (opcion) {
                case "1" -> editarCorreo(sistema);
                case "2" -> cambiarContrasenia(sistema);
                case "3" -> StdOut.println("Volviendo al menú anterior...");
                default -> StdOut.println("Opcion no valida, intente nuevamente");
            }
        }
    }

    /**
     * Método para cambiar la contraseña.
     * @param sistema a utilizar.
     */
    private static void cambiarContrasenia(Sistema sistema) {
        String nuevaClave;
        String claveNueva;
        while(true){
            try {
                StdOut.print("Ingrese su nueva contraseña: ");
                nuevaClave = StdIn.readString();
                StdOut.print("Ingrese su nueva contraseña: ");
                claveNueva = StdIn.readLine();
                Utils.validarClave(nuevaClave);
                if (nuevaClave.equalsIgnoreCase(claveNueva)) {
                    sistema.socio.setContrasenia(nuevaClave);
                    break;
                }
                StdOut.println("Las contraseñas no coinciden, intente nuevamente.");
            }catch (IllegalArgumentException exception) {
                StdOut.println("Ocurrio un error, intente nuevamente.");
                return;
            }
        }

    }

    /**
     * Método para cambiar el email del usuario.
     * @param sistema a utilizar.
     */
    private static void editarCorreo(Sistema sistema) {
        boolean estado = true;
        String nuevoEmail;
        while (estado) {
            try {
                StdOut.print("Ingrese su nuevo correo: ");
                nuevoEmail = StdIn.readLine();
                Utils.validarEmail(nuevoEmail);
                sistema.socio.setCorreoElectronico(nuevoEmail);
                estado=false;
            } catch (IllegalArgumentException exception) {
                StdOut.println("Ocurrio un error, intente nuevamente.");
                return;
            }
        }
    }

    /**
     * Método para calificar un libro.
     * @param sistema a utilizar.
     * @throws IOException en caso de error.m
     */
    private static void calificarlibro(Sistema sistema) throws IOException {
        StdOut.println("[*] Calificar un Libro [*]");
        StdOut.println(sistema.obtegerCatalogoLibros());

        StdOut.print("Ingrese el ISBN del libro a tomar prestado: ");
        String isbn = StdIn.readLine();

        sistema.realizarCalificacion(isbn);
    }
}
