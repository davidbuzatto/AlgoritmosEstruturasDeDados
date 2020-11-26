/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.interfaces;

/**
 * Interface para implementação de tabelas de símbolos.
 * 
 * @author Prof. Dr. David Buzatto
 * @param <Key> Tipo das chaves que serão armazenadas na tabela.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na tabela.
 */
public interface SymbolTable<Key extends Comparable<Key>, Value> extends Iterable<SymbolTable.Entry<Key, Value>>  {
    
    /**
     * A classe Entry representa um par chave/valor da tabela de símbolos.
     * 
     * @param <Key> Tipo da chave.
     * @param <Value> Tipo do valor.
     */
    public static class Entry<Key extends Comparable<Key>, Value> {
        
        private Key key;
        private Value value;

        public Entry( Key key, Value value ) {
            this.key = key;
            this.value = value;
        }

        public Key getKey() {
            return key;
        }

        public Value getValue() {
            
            return value;
        }

        @Override
        public String toString() {
            return key + " -> " + value;
        }
        
    }
    
    /**
     * Insere um par chave/valor na tabela de símbolos. Caso a chave já exista,
     * sobrescreve o valor antigo. Caso o valor seja null, remove a chave da
     * tabela.
     * 
     * @param key Chave usada na inserção.
     * @param value Valor associado à chave.
     */
    public void put( Key key, Value value ) throws IllegalArgumentException;
    
    /**
     * Obtém o valor associado à uma chave.
     * 
     * @param key Chave usada na busca.
     * @return O valor associado à chave.
     */
    public Value get( Key key ) throws IllegalArgumentException;
    
    /**
     * Remove uma chave seu valor associado da árvore.
     * 
     * @param key Chave usada na busca.
     */
    public void delete( Key key ) throws IllegalArgumentException;
    
    /**
     * Verifica se uma chave está contida na árvore.
     * @param key Chave usada na busca.
     * 
     * @return Verdadeiro, caso a chave exista na árvore, falso caso contrário.
     */
    public boolean contains( Key key ) throws IllegalArgumentException;
    
    /**
     * Remove todos os elementos dessa árvore.
     */
    public void clear();
    
    /**
     * Verifica se a árvore está vazia.
     * 
     * @return Verdadeiro, caso a lista esteja vazia, falso caso contrário.
     */
    public boolean isEmpty();
    
    /**
     * Retorna a quantidade de pares chave/valor contidos na árvore.
     * 
     * @return A quantidade de pares chave/valor.
     */
    public int getSize();
    
}
