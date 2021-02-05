/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdi3.algorithms.tree;

import aesd.esdi3.ds.implementations.linear.LinkedQueue;
import aesd.esdi3.ds.implementations.linear.ResizingArrayList;
import aesd.esdi3.ds.implementations.linear.ResizingArrayStack;
import aesd.esdi3.ds.interfaces.List;
import aesd.esdi3.ds.interfaces.Queue;
import aesd.esdi3.ds.interfaces.Stack;
import aesd.esdi3.ds.interfaces.BinaryTree;

/**
 * Implementação dos percursos das árvores binárias de busca.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TreeTraversals {
    
    /**
     * Retorna um iterável contendo os pares chave/valor das árvores na ordem do
     * percurso especificado.
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
            case PREORDER:
                preOrder( node, entries );
                break;
            case INORDER:
                inOrder( node, entries );
                break;
            case POSTORDER:
                postOrder( node, entries );
                break;
            case LEVEL_ORDER:
                levelOrder( node, entries );
                break;
            case INVERSE_PREORDER:
                inversePreOrder( node, entries );
                break;
            case INVERSE_INORDER:
                inverseInOrder( node, entries );
                break;
            case INVERSE_POSTORDER:
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
    private static <Key extends Comparable<Key>, Value> void preOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            entries.add( new BinaryTree.Entry<>( node.key, node.value ) );
            preOrder( node.left, entries );
            preOrder( node.right, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inOrder( node.left, entries );
            entries.add( new BinaryTree.Entry<>( node.key, node.value ) );
            inOrder( node.right, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void postOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            postOrder( node.left, entries );
            postOrder( node.right, entries );
            entries.add( new BinaryTree.Entry<>( node.key, node.value ) );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void levelOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            
            Queue<BinaryTree.Node<Key, Value>> queue = new LinkedQueue<>();
            queue.enqueue( node );

            while ( !queue.isEmpty() ) {

                BinaryTree.Node<Key, Value> current = queue.dequeue();
                entries.add( new BinaryTree.Entry<>( current.key, current.value ) );

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
            entries.add( new BinaryTree.Entry<>( node.key, node.value ) );
            inversePreOrder( node.right, entries );
            inversePreOrder( node.left, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inverseInOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inverseInOrder( node.right, entries );
            entries.add( new BinaryTree.Entry<>( node.key, node.value ) );
            inverseInOrder( node.left, entries );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inversePostOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            inversePostOrder( node.right, entries );
            inversePostOrder( node.left, entries );
            entries.add( new BinaryTree.Entry<>( node.key, node.value ) );
        }
        
    }
    
    private static <Key extends Comparable<Key>, Value> void inverseLevelOrder( BinaryTree.Node<Key, Value> node, List<BinaryTree.Entry<Key, Value>> entries ) {
        
        if ( node != null ) {
            
            Queue<BinaryTree.Node<Key, Value>> queue = new LinkedQueue<>();
            Stack<BinaryTree.Entry<Key, Value>> stack = new ResizingArrayStack<>();
            queue.enqueue( node );

            while ( !queue.isEmpty() ) {

                BinaryTree.Node<Key, Value> current = queue.dequeue();
                stack.push( new BinaryTree.Entry<>( current.key, current.value ) );

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
