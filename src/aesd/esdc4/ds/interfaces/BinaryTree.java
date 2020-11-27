/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.interfaces;

import aesd.esdc4.algorithms.tree.TraversalTypes;

/**
 * Interface para implementação de árvores binárias.
 * 
 * @author Prof. Dr. David Buzatto
 * @param <Key> Tipo das chaves que serão armazenadas na árvore binária.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na árvore binária.
 */
public interface BinaryTree<Key extends Comparable<Key>, Value> extends SymbolTable<Key, Value> {
    
    /*
     * Classe interna estática que define a estrutura básica dos nós das árvores.
     * Ela e seus membros são públicos para poder expor a estrutura dos nós.
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
     * Método para executar percursos.
     * 
     * @param type O tipo do percurso a ser executado.
     * @return Um iterável com todos os elementos visitados na ordem do percureso.
     */
    public Iterable<Entry<Key, Value>> traverse( TraversalTypes type );
    
}
