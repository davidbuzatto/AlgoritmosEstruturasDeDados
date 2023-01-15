package aesd.ds.interfaces;

import aesd.ds.exceptions.EmptyListException;
import aesd.ds.exceptions.ListIndexOutOfBoundsException;

/**
 * Interface para implementação de listas.
 * 
 * @param <Type> Tipo dos valores que serão armazenados na lista.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface List<Type> extends Iterable<Type> {
    
    /**
     * Insere um valor no fim da lista.
     * 
     * @param value Valor a ser inserido no fim.
     */
    public void add( Type value );
    
    /**
     * Insere um valor em uma posição da lista.
     * 
     * @param index Índice em que o valor será inserido.
     * @param value Item a ser inserido.
     */
    public void add( int index, Type value ) throws ListIndexOutOfBoundsException;
    
    /**
     * Retorna um valor de uma posição específica da lista.
     * 
     * @param index Índice em que o valor está.
     * @return O value da lista.
     */
    public Type get( int index ) 
            throws EmptyListException, ListIndexOutOfBoundsException;
    
    /**
     * Altera o valor de uma posição da lista.
     * 
     * @param index Índice do valor que será alterado.
     */
    public void set( int index, Type value ) 
            throws EmptyListException, ListIndexOutOfBoundsException;
    
    /**
     * Remove um valor de uma posição da lista.
     * 
     * @param index Índice do valor que será removido.
     * @return O valor da posição.
     */
    public Type remove( int index ) 
            throws EmptyListException, ListIndexOutOfBoundsException;
    
    /**
     * Remove todos os elementos dessa lista.
     */
    public void clear();
    
    /**
     * Verifica se a lista está vazia.
     * 
     * @return Verdadeiro, caso a lista esteja vazia, falso caso contrário.
     */
    public boolean isEmpty();
    
    /**
     * Retorna a quantidade de valores da lista.
     * 
     * @return A quantidade de valores.
     */
    public int getSize();

}
