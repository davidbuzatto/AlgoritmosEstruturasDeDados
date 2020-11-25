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
import aesd.esdc4.ds.interfaces.Tree;

/**
 * Implementação dos percursos das árvores binárias de busca.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TreeTraversals {
    
    /**
     * Retorna uma lista contendo os ítens das árvores na ordem do percurso
     * especificado.
     * 
     * @param <Key> Tipo da chave do par chave/valor da árvore
     * @param <Value> Tipo do valor do par chave/valor da árvore
     * @param tree Árvore a ser percorrida
     * @param type Tipo do percurso
     * @return 
     */
    public static <Key extends Comparable<Key>, Value> Iterable<Tree.Entry<Key, Value>> traverse( Tree<Key, Value> tree, TraversalTypes type ) {
        
        List<Tree.Entry<Key, Value>> entries = new ResizingArrayList<>();
        
        switch ( type ) {
            case PRE_ORDER:
                preOrder( tree.getRoot(), entries );
                break;
            case IN_ORDER:
                inOrder( tree.getRoot(), entries );
                break;
            case POST_ORDER:
                postOrder( tree.getRoot(), entries );
                break;
            case LEVEL_ORDER:
                levelOrder( tree.getRoot(), entries );
                break;
            case INVERSE_PRE_ORDER:
                inversePreOrder( tree.getRoot(), entries );
                break;
            case INVERSE_IN_ORDER:
                inverseInOrder( tree.getRoot(), entries );
                break;
            case INVERSE_POST_ORDER:
                inversePostOrder( tree.getRoot(), entries );
                break;
            case INVERSE_LEVEL_ORDER:
                inverseLevelOrder( tree.getRoot(), entries );
                break;
        }
        
        
        return entries;
        
    }
    
    /*
     * Métodos privados para os percursos.
     */
    private static <Key extends Comparable<Key>, Value> void preOrder( Tree.Node<Key, Value> no, List<Tree.Entry<Key, Value>> entries ) {
        
        if ( no != null ) {
            entries.add( new Tree.Entry<Key, Value>( no.key, no.value ) );
            preOrder( no.left, entries );
            preOrder( no.right, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inOrder( Tree.Node<Key, Value> node, List<Tree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inOrder( node.left, entries );
            entries.add( new Tree.Entry<Key, Value>( node.key, node.value ) );
            inOrder( node.right, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void postOrder( Tree.Node<Key, Value> node, List<Tree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            postOrder( node.left, entries );
            postOrder( node.right, entries );
            entries.add( new Tree.Entry<Key, Value>( node.key, node.value ) );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void levelOrder( Tree.Node<Key, Value> node, List<Tree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            
            Queue<Tree.Node<Key, Value>> queue = new LinkedQueue<>();
            queue.enqueue( node );

            while ( !queue.isEmpty() ) {

                Tree.Node<Key, Value> current = queue.dequeue();
                entries.add( new Tree.Entry<Key, Value>( current.key, current.value ) );

                if ( current.left != null ) {
                    queue.enqueue( current.left );
                }

                if ( current.right != null ) {
                    queue.enqueue( current.right );
                }

            }
            
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inversePreOrder( Tree.Node<Key, Value> node, List<Tree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            entries.add( new Tree.Entry<Key, Value>( node.key, node.value ) );
            inversePreOrder( node.right, entries );
            inversePreOrder( node.left, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inverseInOrder( Tree.Node<Key, Value> node, List<Tree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inverseInOrder( node.right, entries );
            entries.add( new Tree.Entry<Key, Value>( node.key, node.value ) );
            inverseInOrder( node.left, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inversePostOrder( Tree.Node<Key, Value> node, List<Tree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inversePostOrder( node.right, entries );
            inversePostOrder( node.left, entries );
            entries.add( new Tree.Entry<Key, Value>( node.key, node.value ) );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inverseLevelOrder( Tree.Node<Key, Value> node, List<Tree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            
            Queue<Tree.Node<Key, Value>> queue = new LinkedQueue<>();
            Stack<Tree.Entry<Key, Value>> stack = new ResizingArrayStack<>();
            queue.enqueue( node );

            while ( !queue.isEmpty() ) {

                Tree.Node<Key, Value> current = queue.dequeue();
                stack.push( new Tree.Entry<Key, Value>( current.key, current.value ) );

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
