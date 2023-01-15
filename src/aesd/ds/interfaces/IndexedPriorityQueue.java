package aesd.ds.interfaces;

import java.util.NoSuchElementException;

/**
 * Interface para implementação de filas de prioridades indexadas.
 * 
 * @param <Key> Tipo das chaves que serão armazenadas na fila de prioridades.
 * 
 * @author Prof. Dr. David Buzatto
 */
public interface IndexedPriorityQueue<Key extends Comparable<Key>> extends Iterable<Integer> {
    
    /**
     * Insere uma nova chave à essa fila de prioridades associada à um índice.
     *
     * @param index índice que a chave será inserida
     * @param key a nova chave a ser inserida na fila de prioridades e associada
     * ao índice
     * @throws IllegalArgumentException se o índice já existir na fila de
     * prioridades
     */
    public void insert( int index, Key key ) throws IllegalArgumentException;
    
    /**
     * Retorna o índice associado à chave que tem maior prioridade definida de
     * acordo com a invariante da fila de prioridades implemnetada,
     * sem removê-la.
     *
     * @return a chave com maior prioridade da fila de prioridades
     * @throws NoSuchElementException se essa fila de prioridades estiver vazia
     */
    public int peekIndex() throws NoSuchElementException;
    
    /**
     * Retorna a chave que tem maior prioridade definida de acordo com a 
     * invariante da fila de prioridades implementada, sem removê-la.
     *
     * @return a chave com maior prioridade da fila de prioridades
     * @throws NoSuchElementException se essa fila de prioridades estiver vazia
     */
    public Key peekKey() throws NoSuchElementException;
    
    /**
     * Remove o índice que tem maior prioridade definida de acordo com a
     * invariante da fila de prioridades implementada.
     *
     * @return o índice que tem maior prioridade
     * @throws NoSuchElementException se essa fila de prioridades estiver vazia
     */
    public int delete() throws NoSuchElementException;
    
    /**
     * Remove a chave associada ao índice.
     *
     * @param index índice da chave que será removida
     * @throws IllegalArgumentException Se for um índice inválido
     * @throws NoSuchElementException se não houver chave associada ao índice
     */
    public void delete( int index );
    
    /**
     * Verifica se um inteiro é um índice da fila de prioridades.
     * 
     * @param index O inteiro a ser verificado.
     * @return verdadeiro se i for um índice, false caso contrário
     */
    public boolean contains( int index ) throws IllegalArgumentException;
    
    /**
     * Retorna a chave associada ao índice.
     *
     * @param index o índice da chave
     * @return a chave associada ao índice
     * @throws IllegalArgumentException se for um índice inválido
     * @throws NoSuchElementException se não existir uma chave associada ao
     * índice
     */
    public Key keyOf( int index );

    /**
     * Altera a chave associada ao índice
     *
     * @param index o índice da chave que será alterada
     * @param key a nova chave que será associada ao índice
     * @throws IllegalArgumentException se for um índice inválido
     */
    public void changeKey( int index, Key key );
    
    /**
     * Aumenta a chave associada ao índice ao valor especificado.
     *
     * @param index o índice da chave que será aumentada
     * @param key aumenta a chave associada ao índice à essa chave
     * @throws IllegalArgumentException se o índice for inválido
     * @throws IllegalArgumentException se a nova chave for menor ou igual à atual
     * @throws NoSuchElementException se não houver chave associada ao índice
     */
    public void increaseKey( int index, Key key );
    
    /**
     * Diminui a chave associada ao índice ao valor especificado.
     *
     * @param index o índice da chave que será diminuída
     * @param key diminui a chave associada ao índice à essa chave
     * @throws IllegalArgumentException se o índice for inválido
     * @throws IllegalArgumentException se a nova chave for maior ou igual à atual
     * @throws NoSuchElementException se não houver chave associada ao índice
     */
    public void decreaseKey( int index, Key key );
    
    /**
     * Remove todos os elementos dessa fila de prioridades.
     */
    public void clear();
    
    /**
     * Verifica se a fila de prioridades está vazia.
     * 
     * @return Verdadeiro, caso a fila de prioridades esteja vazia, falso caso
     * contrário.
     */
    public boolean isEmpty();
    
    /**
     * Retorna a quantidade de chaves da fila de prioridades.
     * 
     * @return A quantidade de chaves.
     */
    public int getSize();
    
}
