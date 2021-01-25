/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc5.sorting.integer;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.ds.implementations.nonlinear.symtable.RedBlackTree;
import aesd.esdc4.ds.interfaces.BinaryTree;
import aesd.esdc4.ds.interfaces.SymbolTable.Entry;

/**
 * Ordenação usando árvore binária de busca (Binary Tree Sort)
 *
 * Neste algoritmo é usada uma árvore binária de busca balanceada,
 * onde os elementos do array a ser ordenado são inseridos um
 * a um na árvore e após a inserção, a árvore é percorrida em
 * ordem e cada nó visitado é inserido de volta no
 * array do início ao fim.
 *
 * In-place? Não
 *  Estável? Sim
 *
 * Complexidade:
 *       Pior caso: O(n log n)
 *      Caso médio: O(n log n)
 *     Melhor caso: O(n log n)
 *
 * @author Prof. Dr. David Buzatto
 */
public class BinaryTreeSort {
    
    public static void sort( int[] array ) {
        
        // controla a iteração sobre o array a ser
        // ordenado
        int i = 0;

        // uma árvore binária de busca balanceada
        BinaryTree<Integer, Integer> a = new RedBlackTree<>();

        // itera pelo array inserindo os valores
        // na árvore
        for ( Integer v : array ) {
            a.put( v, 0 );
        }
        
        // caminha na árvore usando o percurso em ordem e coleta os valores
        for ( Entry<Integer, Integer> e : a.traverse( TraversalTypes.INORDER ) ) {
            array[i++] = e.getKey();
        }
    
    }
    
}
