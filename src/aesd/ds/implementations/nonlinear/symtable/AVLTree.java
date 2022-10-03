/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.symtable;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.algorithms.tree.TraversalTypes;
import aesd.algorithms.tree.TreeTraversals;
import java.util.Iterator;
import aesd.ds.interfaces.BinaryTree;
import aesd.ds.interfaces.Queue;

/**
 * Implementação de uma árvore AVL (Adelson-Velsky e Landis).
 * 
 * Implementação baseada na obra: WEISS, M. A. Data Structures and Algorithm
 * Analysis in Java. 3. ed. Pearson Education: New Jersey, 2012. 614 p.
 * 
 * @param <Key> Tipo das chaves que serão armazenadas na árvore.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na árvore.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AVLTree<Key extends Comparable<Key>, Value> implements BinaryTree<Key, Value> {

    /*
     * Classe interna estática que define os nós da árvore AVL.
     */
    private static class AVLNode<Key extends Comparable<Key>, Value> extends BinaryTree.Node<Key, Value> {
        
        public int height;
        
        @Override
        public String toString() {
            return key + " -> " + value + " (" + height + ")";
        }
        
    }
    
    // raiz da árvore
    private AVLNode<Key, Value> root;
    
    // tamanho da árvore (quantidade de pares chave/valor)
    private int size;
    
    // fator de balanceamento
    // valor máximo na diferença de alturas de duas subárvores
    private static final int ALLOWED_IMBALANCE = 1;
    
    /**
     * Constrói uma Árvore AVL vazia.
     */
    public AVLTree() {
        root = null;
    }
    
    @Override
    public void put( Key key, Value value ) throws IllegalArgumentException {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "first argument to put() is null" );
        }
        
        if ( value == null ) {
            delete( key );
            return;
        }
        
        root = (AVLNode<Key, Value>) put( root, key, value );
        
    }
    
    private BinaryTree.Node<Key, Value> put( BinaryTree.Node<Key, Value> node, Key key, Value value ) {
        
        if ( node == null ) {
            
            AVLNode<Key, Value> avlNode = new AVLNode<>();
            avlNode.key = key;
            avlNode.value = value;
            avlNode.left = null;
            avlNode.right = null;
            avlNode.height = 1;
            
            node = avlNode;
            
            size++;
            
        } else {

            int comp = key.compareTo( node.key );

            if ( comp < 0 ) {
                node.left = put( node.left, key, value );
            } else if ( comp > 0 ) {
                node.right = put( node.right, key, value );
            } else {
                node.value = value;
            }
        
        }
        
        // balanceia a árvore
        return balance( node );
        
    }
    
    @Override
    public Value get( Key key ) throws IllegalArgumentException {
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to get() is null" );
        }
        return get( root, key );
    }
    
    private Value get( BinaryTree.Node<Key, Value> node, Key key ) {
        
        while ( node != null ) {
            
            int comp = key.compareTo( node.key );
            
            if ( comp < 0 ) {
                node = node.left;
            } else if ( comp > 0 ) {
                node = node.right;
            } else {
                return node.value;
            }
            
        }

        return null;

    }
    
    @Override
    public void delete( Key key ) throws IllegalArgumentException {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to delete() is null" );
        }
        
        if ( !contains( key ) ) {
            return;
        }
        
        root = (AVLNode<Key, Value>) delete( root, key );
        size--;
        
    }

    private BinaryTree.Node<Key, Value> delete( BinaryTree.Node<Key, Value> node, Key key ) {
        
        if ( node == null ) {
            return node;
        }
        
        int comp = key.compareTo( node.key );

        if ( comp < 0 ) {
            node.left = delete( node.left, key );
        } else if ( comp > 0 ) {
            node.right = delete( node.right, key );
        } else {
            
            // dois filhos
            if ( node.left != null && node.right != null ) {
                
                Node<Key, Value> min = min( node.right );
                node.key = min.key;
                node.value = min.value;
                
                node.right = delete( node.right, node.key );
                
                // um ou nenhum filho
            } else {
                node = ( node.left != null ) ? node.left : node.right;
            }
            
        }
        
        return balance( node );
        
    }
    
    @Override
    public boolean contains( Key key ) throws IllegalArgumentException {
        return get( key ) != null;
    }

    public BinaryTree.Node<Key, Value> min() {
        
        if ( isEmpty() ) {
            return null;
        }
        
        return min( root );
        
    }
    
    private BinaryTree.Node<Key, Value> min( BinaryTree.Node<Key, Value> node ) {
        
        if ( node == null ) {
            return node;
        }

        while ( node.left != null ) {
            node = node.left;
        }
        
        return node;
        
    }

    public BinaryTree.Node<Key, Value> max() {
        
        if ( isEmpty() ) {
            return null;
        }
        
        return max( root );
        
    }
    
    private BinaryTree.Node<Key, Value> max( BinaryTree.Node<Key, Value> node ) {
        
        if ( node == null ) {
            return node;
        }

        while ( node.right != null ) {
            node = node.right;
        }
        
        return node;
        
    }

    /**
     * Esvazia a árvore.
     */
    @Override
    public void clear() {
        root = (AVLNode<Key, Value>) clear( root );
        size = 0;
    }

    /*
     * Método privado para remoção de todos os itens de forma recursiva.
     */
    private BinaryTree.Node<Key, Value> clear( BinaryTree.Node<Key, Value> node ) {

        if ( node != null ) {
            node.left = clear( node.left );
            node.right = clear( node.right );
        }

        return null;

    }
    
    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    
    @Override
    public int getSize() {
        return size;
    }
    
    private BinaryTree.Node<Key, Value> balance( BinaryTree.Node<Key, Value> node ) {
        
        if ( node == null ) {
            return node;
        }

        if ( height( node.left ) - height( node.right ) > ALLOWED_IMBALANCE ) {
            if ( height( node.left.left ) >= height( node.left.right ) ) {
                node = rotateWithLeftChild( node );
            } else {
                node = doubleWithLeftChild( node );
            }
        } else if ( height( node.right ) - height( node.left ) > ALLOWED_IMBALANCE ) {
            if ( height( node.right.right ) >= height( node.right.left ) ) {
                node = rotateWithRightChild( node );
            } else {
                node = doubleWithRightChild( node );
            }
        }

        ( (AVLNode<Key, Value>) node ).height = Math.max( height( node.left ), height( node.right ) ) + 1;
        
        return node;
        
    }

    /**
     * Retorna a altura de um nó ou 0 caso o nó seja nulo.
     */
    private int height( BinaryTree.Node<Key, Value> node ) {
        return node == null ? 0 : ( (AVLNode<Key, Value>) node ).height;
    }

    /**
     * Rotação EE/LL - simétrica à DD/RR
     * 
     * Para a inserção, N foi inserido na subárvore esquerda da subárvore
     * esquerda de A, ou seja, rotacionada um nó com filho à esquerda.
     *
     * Para as árvores AVL, essa é uma rotação simples apresentada no slide 11.
     * 
     * Diferença entre alturas:
     *     A = +2;
     *     B = +1;
     * 
     * Ao final, atualiza as alturas e retorna a nova raiz.
     */
    private BinaryTree.Node<Key, Value> rotateWithLeftChild( BinaryTree.Node<Key, Value> a ) {
        
        BinaryTree.Node<Key, Value> b = a.left;
        a.left = b.right;
        b.right = a;
        
        AVLNode<Key, Value> aAvl = (AVLNode<Key, Value>) a;
        AVLNode<Key, Value> bAvl = (AVLNode<Key, Value>) b;
        
        aAvl.height = Math.max( height( a.left ), height( a.right ) ) + 1;
        bAvl.height = Math.max( height( b.left ), height( b.right ) ) + 1;
        
        return b;
        
    }

    /**
     * Rotação DD/RR - simétrica à EE/LL
     * 
     * Para a inserção, N foi inserido na subárvore direita da subárvore
     * direita de A, ou seja, rotacionada um nó com filho à direita.
     *
     * Para as árvores AVL, essa é uma rotação simples apresentada no slide 17.
     * 
     * Diferença entre alturas:
     *     A = -2;
     *     B = -1;
     * 
     * Ao final, atualiza as alturas e retorna a nova raiz.
     */
    private BinaryTree.Node<Key, Value> rotateWithRightChild( BinaryTree.Node<Key, Value> a ) {
        
        BinaryTree.Node<Key, Value> b = a.right;
        a.right = b.left;
        b.left = a;
        
        AVLNode<Key, Value> aAvl = (AVLNode<Key, Value>) a;
        AVLNode<Key, Value> bAvl = (AVLNode<Key, Value>) b;
        
        aAvl.height = Math.max( height( a.left ), height( a.right ) ) + 1;
        bAvl.height = Math.max( height( b.left ), height( b.right ) ) + 1;
        
        return b;
        
    }

    /**
     * Rotação ED/LR - simétrica à DE/RL
     * 
     * Para a inserção, N foi inserido na subárvore direita da subárvore
     * esquerda de A, ou seja, realiza uma rotação dupla:
     *     1 - filho da esquerda com seu filho da direita;
     *     2 - nó A com seu novo filho à esquerda.
     *
     * Para as árvores AVL, essa é uma rotação dupla apresentada nos slides
     * 23, 24 e 25.
     * 
     * Diferença entre alturas:
     *     A = +2;
     *     B = -1;
     * 
     * Ao final, atualiza as alturas e retorna a nova raiz.
     */
    private BinaryTree.Node<Key, Value> doubleWithLeftChild( BinaryTree.Node<Key, Value> a ) {
        a.left = rotateWithRightChild( a.left );
        return rotateWithLeftChild( a );
    }

    /**
     * Rotação DE/RL - simétrica à ED/LR
     * 
     * Para a inserção, N foi inserido na subárvore esquerda da subárvore
     * direita de A, ou seja, realiza uma rotação dupla:
     *     1 - filho da direita com seu filho da esquerda;
     *     2 - nó A com seu novo filho à direita.
     *
     * Para as árvores AVL, essa é uma rotação dupla apresentada nos slides
     * 37, 38 e 39.
     * 
     * Diferença entre alturas:
     *     A = -2;
     *     B = +1;
     * 
     * Ao final, atualiza as alturas e retorna a nova raiz.
     */
    private BinaryTree.Node<Key, Value> doubleWithRightChild( BinaryTree.Node<Key, Value> a ) {
        a.right = rotateWithLeftChild( a.right );
        return rotateWithRightChild( a );
    }
    
    @Override
    public Iterable<Entry<Key, Value>> traverse( TraversalTypes type ) {
        return TreeTraversals.traverse( root, type );
    }
    
    @Override
    public Iterator<BinaryTree.Entry<Key, Value>> iterator() {
        return traverse(TraversalTypes.INORDER ).iterator();
    }
    
    @Override
    public Iterable<Key> getKeys() {
        Queue<Key> keys = new LinkedQueue<>();
        for ( BinaryTree.Entry<Key, Value> e : traverse(TraversalTypes.INORDER ) ) {
            keys.enqueue( e.getKey() );
        }
        return keys;
    }
    
    /*@Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if ( !isEmpty() ) {
            
            for ( BinaryTree.Entry<Key, Value> e : TreeTraversals.traverse(root, TraversalTypes.INORDER ) ) {
                
                if ( e.getKey().equals( root.key ) ) {
                    sb.append( e ).append( " <- root\n" );
                } else {
                    sb.append( e ).append( "\n" );
                }
                
            }
            
        } else {
            sb.append( "empty AVL tree!\n" );
        }
        
        return sb.toString();
        
    }*/
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if ( !isEmpty()) {
            preOrder( root, "", null, sb );
        } else {
            sb.append( "empty AVL tree!\n" );
        }
        
        return sb.toString();
        
    }
    
    private void preOrder( Node<Key, Value> node, String ident, String leftRight, StringBuilder sb ) {
        
        if ( node != null ) {
            
            String rootIdent = "";
            String leafIdent = "";
            
            if ( node != root ) {
                rootIdent = ident + "|--";
                leafIdent = ident + "|  ";
            }
            
            sb.append( rootIdent );
            if ( leftRight != null ) {
                sb.append( "(" ).append( leftRight ).append( ") " );
            }
            sb.append( node );
            if ( node == root ) {
                sb.append(  " <- root" );
            }
            sb.append( "\n" );
            
            preOrder( node.left, leafIdent, "L", sb );
            preOrder( node.right, leafIdent, "R", sb );
            
        }
        
    }

}
