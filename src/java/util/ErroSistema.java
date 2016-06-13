package util;

/**
 *
 * @author lber
 */

public class ErroSistema extends Exception{

    public ErroSistema(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ErroSistema(String message){ 
        super(message);
    }
}