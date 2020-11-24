/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.exceptions;

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
