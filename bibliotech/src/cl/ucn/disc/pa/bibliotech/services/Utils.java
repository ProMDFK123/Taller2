package cl.ucn.disc.pa.bibliotech.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Clase que reune los metodos utilitarios.
 *
 * @author Gabriel López
 */
public final class Utils {

    /**
     * The Email validator.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");

    //Validador de la contraseña.
    private static final Pattern Password = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$");

    /**
     * Constructor privado: nadie puede instanciar esta clase.
     */
    private Utils() {
        // nothing here
    }

    /**
     * Add theObject to theStaticArray.
     *
     * @param theStaticArray the array.
     * @param theObject      the object to append.
     * @param <T>            generic to use.
     * @return the static array.
     */
    public static <T> T[] append(T[] theStaticArray, T theObject) {
        // new arraylist
        List<T> theList = new ArrayList<>();
        // copy all the items from [] to the list
        Collections.addAll(theList, theStaticArray);
        // add the object
        theList.add(theObject);
        // return the static array
        return theList.toArray(theStaticArray);
    }

    /**
     * Valida un correo electronico, en caso de no ser valido se lanza una Exception.
     *
     * @param email a validar.
     */
    public static void validarEmail(final String email) {
        // el correo debe ser estructuralmente valido
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Correo Electronico no valido: " + email);
        }
    }

    /**
     * Método que valida si la contraseña sigue un patron definido
     * @param clave - Contraseña del socio a validar.
     */
    public static void validarClave(final String clave){
        if(!Password.matcher(clave).matches()){
            throw new IllegalArgumentException("Contraseña invalida.");
        }
    }
}
