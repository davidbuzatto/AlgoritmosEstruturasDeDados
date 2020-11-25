/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.exceptions;

/**
 * Exceção para indicar que uma chave não foi encontrada em uma estrutura de 
 * dados.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class KeyNotFoundException extends RuntimeException {
    
    public KeyNotFoundException() {
    }
    
    public KeyNotFoundException( String message ) {
        super( message );
    }
    
}
