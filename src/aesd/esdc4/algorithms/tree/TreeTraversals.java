/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.tree;

import aesd.esdc4.ds.implementations.LinkedQueue;
import aesd.esdc4.ds.implementations.ResizingArrayList;
import aesd.esdc4.ds.implementations.ResizingArrayStack;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;
import aesd.esdc4.ds.interfaces.BinaryTree;

/**
 * Implementação dos percursos das árvores binárias de busca.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TreeTraversals {
    
    /**
     * Retorna um iterável contendo os ítens das árvores na ordem do percurso
     * especificado.
     * 
     * @param <Key> Tipo da chave do par chave/valor da árvore
     * @param <Value> Tipo do valor do par chave/valor da árvore
     * @param node Árvore a ser percorrida
     * @param type Tipo do percurso
     * @return 
     */
    public static <Key extends Comparable<Key>, Value> Iterable<BinaryTree.Entry<Key, Value>> traverse( BinaryTree.Node<Key, Value> node, TraversalTypes type ) {
        
        List<BinaryTree.Entry<Key, Value>> entries = new ResizingArrayList<>();
        
        switch ( type ) {
            case PRE_ORDER:
                preOrder( node, entries );
                break;
            case IN_ORDER:
                inOrder( node, entries );
                break;
            case POST_ORDER:
                postOrder( node, entries );
                break;
            case LEVEL_ORDER:
                levelOrder( node, entries );
                break;
            case INVERSE_PRE_ORDER:
                inversePreOrder( node, entries );
                break;
            case INVERSE_IN_ORDER:
                inverseInOrder( node, entries );
                break;
            case INVERSE_POST_ORDER:
                inversePostOrder( node, entries );
                break;
            case INVERSE_LEVEL_ORDER:
                inverseLevelOrder( node, entries );
                break;
        }
        
        
        return entries;
        
    }
    
    /*
     * Métodos privados para os percursos.
     */
    private static <Key extends Comparable<Key>, Value> void preOrder( BinaryTree.Node<Key, Value> no, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( no != null ) {
            entries.add( new BinaryTree.Entry<Key, Value>( no.key, no.value ) );
            preOrder( no.left, entries );
            preOrder( no.right, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inOrder( node.left, entries );
            entries.add( new BinaryTree.Entry<Key, Value>( node.key, node.value ) );
            inOrder( node.right, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void postOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            postOrder( node.left, entries );
            postOrder( node.right, entries );
            entries.add( new BinaryTree.Entry<Key, Value>( node.key, node.value ) );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void levelOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            
            Queue<BinaryTree.Node<Key, Value>> queue = new LinkedQueue<>();
            queue.enqueue( node );

            while ( !queue.isEmpty() ) {

                BinaryTree.Node<Key, Value> current = queue.dequeue();
                entries.add( new BinaryTree.Entry<Key, Value>( current.key, current.value ) );

                if ( current.left != null ) {
                    queue.enqueue( current.left );
                }

                if ( current.right != null ) {
                    queue.enqueue( current.right );
                }

            }
            
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inversePreOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            entries.add( new BinaryTree.Entry<Key, Value>( node.key, node.value ) );
            inversePreOrder( node.right, entries );
            inversePreOrder( node.left, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inverseInOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inverseInOrder( node.right, entries );
            entries.add( new BinaryTree.Entry<Key, Value>( node.key, node.value ) );
            inverseInOrder( node.left, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inversePostOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inversePostOrder( node.right, entries );
            inversePostOrder( node.left, entries );
            entries.add( new BinaryTree.Entry<Key, Value>( node.key, node.value ) );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inverseLevelOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            
            Queue<BinaryTree.Node<Key, Value>> queue = new LinkedQueue<>();
            Stack<BinaryTree.Entry<Key, Value>> stack = new ResizingArrayStack<>();
            queue.enqueue( node );

            while ( !queue.isEmpty() ) {

                BinaryTree.Node<Key, Value> current = queue.dequeue();
                stack.push( new BinaryTree.Entry<Key, Value>( current.key, current.value ) );

                if ( current.left != null ) {
                    queue.enqueue( current.left );
                }

                if ( current.right != null ) {
                    queue.enqueue( current.right );
                }

            }

            while ( !stack.isEmpty() ) {
                entries.add( stack.pop() );
            }
        
        }
        
    }
    
}
