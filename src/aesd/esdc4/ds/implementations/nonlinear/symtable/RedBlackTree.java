/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.symtable;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.algorithms.tree.TreeTraversals;
import java.util.Iterator;
import aesd.esdc4.ds.interfaces.BinaryTree;
import aesd.esdc4.ds.interfaces.Queue;

/**
 * Implementação de uma árvore vermelho-preto (Red-Black Tree).
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @param <Key> Tipo das chaves que serão armazenadas na árvore.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na árvore.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RedBlackTree<Key extends Comparable<Key>, Value> implements BinaryTree<Key, Value> {

    /*
     * Classe interna estática que define os nós da árvore vermelho-preto.
     */
    private static class RBNode<Key extends Comparable<Key>, Value> extends BinaryTree.Node<Key, Value> {
        
        public NodeColor color;
        public int size;
        
        @Override
        public String toString() {
            return key + " -> " + value + " (" + ( color == NodeColor.RED ? "R" : "B" ) + ")";
        }
        
    }
    
    /*
     * Enumeração para especificação da cor do nó.
     */
    private enum NodeColor {
        RED,
        BLACK
    }

    // raiz da árvore
    private RBNode<Key, Value> root;
    
    /**
     * Constrói uma Árvore vermelho-preto vazia.
     */
    public RedBlackTree() {
        root = null;
    }
    
    /**
     * Verifica se um nó é vermelho.
     * 
     * @param node O nó a ser verificado
     * @return true caso o nó seja vermelho, false caso seja preto ou null.
     */
    private boolean isRed( BinaryTree.Node<Key, Value> node ) {
        if ( node == null ) {
            return false;
        }
        return ( ( RBNode<Key, Value>) node ).color == NodeColor.RED;
    }

    /**
     * Retorna o tamanho do nó.
     * 
     * @param node O nó a ser verificado.
     * @return O tamanho do nó na subárvore enraizada em node ou 0
     * caso seja null.
     */
    private int nodeSize( BinaryTree.Node<Key, Value> node ) {
        if ( node == null ) {
            return 0;
        }
        return ( (RBNode<Key, Value>) node ).size;
    }
    
    @Override
    public int getSize() {
        return nodeSize( root );
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
        
        root = (RBNode<Key, Value>) put( root, key, value );
        root.color = NodeColor.BLACK;
        
    }
    
    private BinaryTree.Node<Key, Value> put( BinaryTree.Node<Key, Value> node, Key key, Value value ) {
        
        if ( node == null ) {
            
            RBNode<Key, Value> rbNode = new RBNode<>();
            rbNode.key = key;
            rbNode.value = value;
            rbNode.left = null;
            rbNode.right = null;
            rbNode.color = NodeColor.RED;
            rbNode.size = 1;
            
            node = rbNode;
            
            return node;
            
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
            
        // consertando os links inclinados à right
        if ( isRed( node.right ) && !isRed( node.left ) ) {
            node = rotateLeft( node );
        }
        if ( isRed( node.left ) && isRed( node.left.left ) ) {
            node = rotateRight( node );
        }
        if ( isRed( node.left ) && isRed( node.right ) ) {
            flipColors( node );
        }

        ( (RBNode<Key, Value>) node ).size = nodeSize( node.left ) + nodeSize( node.right ) + 1;

        return node;
        
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
        
        // se ambos os filhos da raiz forem pretos, configura a raiz como vermelho
        if ( !isRed( root.left ) && !isRed( root.right ) ) {
            root.color = NodeColor.RED;
        }

        root = (RBNode<Key, Value>) delete( root, key );
        if ( !isEmpty() ) {
            root.color = NodeColor.BLACK;
        }
        
    }
    
    private BinaryTree.Node<Key, Value> delete( BinaryTree.Node<Key, Value> node, Key key ) {

        if ( key.compareTo( node.key ) < 0 ) {
            
            if ( !isRed( node.left ) && !isRed( node.left.left ) ) {
                node = moveRedLeft( node );
            }
            
            node.left = delete( node.left, key );
            
        } else {
            
            if ( isRed( node.left ) ) {
                node = rotateRight( node );
            }
            
            if ( key.compareTo( node.key ) == 0 && ( node.right == null ) ) {
                return null;
            }
            
            if ( !isRed( node.right ) && !isRed( node.right.left ) ) {
                node = moveRedRight( node );
            }
            
            if ( key.compareTo( node.key ) == 0 ) {
                BinaryTree.Node<Key, Value> x = min( node.right );
                node.key = x.key;
                node.value = x.value;
                node.right = deleteMin( node.right );
            } else {
                node.right = delete( node.right, key );
            }
            
        }
        
        return balance( node );
        
    }
    
    @Override
    public boolean contains( Key key ) throws IllegalArgumentException {
        return get( key ) != null;
    }
    
    private BinaryTree.Node<Key, Value> deleteMin( BinaryTree.Node<Key, Value> node ) {
        
        if ( node.left == null ) {
            return null;
        }

        if ( !isRed( node.left ) && !isRed( node.left.left ) ) {
            node = moveRedLeft( node );
        }

        node.left = deleteMin( node.left );
        
        return balance( node );
        
    }
    
    /**
     * Faz com que o link inclinado à esquerda se incline para a direita.
     * 
     * @param node nó de origem
     * @return a nova raiz da subárvore.
     */
    private BinaryTree.Node<Key, Value> rotateRight( BinaryTree.Node<Key, Value> node ) {
        
        RBNode<Key, Value> newRoot = (RBNode<Key, Value>) node.left;
        node.left = newRoot.right;
        
        newRoot.right = node;
        newRoot.color = ( ( RBNode<Key, Value> ) newRoot.right ).color;
        ( ( RBNode<Key, Value> ) newRoot.right ).color = NodeColor.RED;
        newRoot.size = ( ( RBNode<Key, Value> ) node ).size;
        
        ( ( RBNode<Key, Value> ) node ).size = nodeSize( node.left ) + nodeSize( node.right ) + 1;
        
        return newRoot;
        
    }

    /**
     * Faz com que o link inclinado à direita se incline para a esquerda.
     * 
     * @param node nó de origem
     * @return a nova raiz da subárvore.
     */
    private BinaryTree.Node<Key, Value> rotateLeft( BinaryTree.Node<Key, Value> node ) {
        
        RBNode<Key, Value> newRoot = (RBNode<Key, Value>) node.right;
        node.right = newRoot.left;
        
        newRoot.left = node;
        newRoot.color = ( ( RBNode<Key, Value> ) newRoot.left ).color;
        ( ( RBNode<Key, Value> ) newRoot.left ).color = NodeColor.RED;
        newRoot.size = ( ( RBNode<Key, Value> ) node ).size;
        
        ( ( RBNode<Key, Value> ) node ).size = nodeSize( node.left ) + nodeSize( node.right ) + 1;
        
        return newRoot;
        
    }

    /**
     * Inverte a cor de um nó e de seus filhos.
     * 
     * @param rbNode nó a ser alterado.
     */
    private void flipColors( BinaryTree.Node<Key, Value> node ) {
        
        RBNode<Key, Value> rbNode = (RBNode<Key, Value>) node;
        RBNode<Key, Value> rbNodeLeft = (RBNode<Key, Value>) node.left;
        RBNode<Key, Value> rbNodeRight = (RBNode<Key, Value>) node.right;
        
        rbNode.color           = rbNode.color == NodeColor.RED ? NodeColor.BLACK : NodeColor.RED;
        rbNodeLeft.color   = rbNodeLeft.color == NodeColor.RED ? NodeColor.BLACK : NodeColor.RED;
        rbNodeRight.color = rbNodeRight.color == NodeColor.RED ? NodeColor.BLACK : NodeColor.RED;
        
    }

    /**
     * Assumindo que o nó é vermelho e ambos os seus filhos são pretos, faz
     * com que a esquerda do nó ou um de seus filhos seja vermelho.
     * 
     * @param node nó a ser movido.
     * @return o nó modificado.
     */
    private BinaryTree.Node<Key, Value> moveRedLeft( BinaryTree.Node<Key, Value> node ) {

        flipColors( node );
        
        if ( isRed( node.right.left ) ) {
            node.right = rotateRight( node.right );
            node = rotateLeft( node );
            flipColors( node );
        }
        
        return node;
        
    }

    /**
     * Assumindo que o nó é vermelho e ambos os seus filhos são pretos, faz
     * com que nó da direita ou um de seus filhos seja vermelho.
     * 
     * @param no nó a ser movido.
     * @return o nó modificado.
     */
    private BinaryTree.Node<Key, Value> moveRedRight( BinaryTree.Node<Key, Value> no ) {
        
        flipColors( no );
        
        if ( isRed( no.left.left ) ) {
            no = rotateRight( no );
            flipColors( no );
        }
        
        return no;
        
    }

    /**
     * Recupera a condição de existência (invariante) para a árvore vermelho-preto.
     * 
     * @param node nó de origem.
     * @return o nó modificado.
     */
    private BinaryTree.Node<Key, Value> balance( BinaryTree.Node<Key, Value> node ) {

        if ( isRed( node.right ) ) {
            node = rotateLeft( node );
        }
        
        if ( isRed( node.left ) && isRed( node.left.left ) ) {
            node = rotateRight( node );
        }
        
        if ( isRed( node.left ) && isRed( node.right ) ) {
            flipColors( node );
        }

        ( (RBNode<Key, Value>) node ).size = nodeSize( node.left ) + nodeSize( node.right ) + 1;
        
        return node;
        
    }
    
    private BinaryTree.Node<Key, Value> min( BinaryTree.Node<Key, Value> node ) {
        
        if ( node.left == null ) {
            return node;
        } else {
            return min( node.left );
        }
        
    }
    
    @Override
    public void clear() {
        root = (RBNode<Key, Value>) clear( root );
    }

    private BinaryTree.Node<Key, Value> clear( BinaryTree.Node<Key, Value> node ) {

        if ( node != null ) {
            node.left = clear( node.left );
            node.right = clear( node.right );
        }

        return null;

    }
    
    @Override
    public boolean isEmpty() {
        return root == null;
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
            sb.append( "empty red-black tree!\n" );
        }
        
        return sb.toString();
        
    }*/
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if ( !isEmpty()) {
            preOrder( root, "", null, sb );
        } else {
            sb.append( "empty red-black tree!\n" );
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
