package aesd.ds.exceptions;

/**
 * Exceção para indicar um índice inválido de uma lista.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ListIndexOutOfBoundsException extends RuntimeException {
    
    public ListIndexOutOfBoundsException() {
    }
    
    public ListIndexOutOfBoundsException( String message ) {
        super( message );
    }
    
}
