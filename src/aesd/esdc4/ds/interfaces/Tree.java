/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.interfaces;

/**
 *
 * @author David
 */
public interface Tree<Key extends Comparable<Key>, Value> extends SymbolTable<Key, Value>, Iterable<SymbolTable.Entry<Key, Value>> {
    
    /*
     * Classe interna estática que define a estrutura básica dos nós das árvores.
     * Ela e seus membros são públicos para poder expor a estrutura dos nós.
     * Normalmente deveria ser privada.
     */
    public static class Node<Key extends Comparable<Key>, Value> {
        
        public Key key;
        public Value value;
        public Node<Key, Value> left;
        public Node<Key, Value> right;
        
        @Override
        public String toString() {
            return key + " -> " + value;
        }
        
    }
    
   
    /**
     * Método para obter o nó da raiz.
     * 
     * @return A raiz da árvore.
     */
    public Node<Key, Value> getRoot();
    
}
