/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.algorithms.tree.TreeTraversals;
import aesd.esdc4.ds.exceptions.KeyNotFoundException;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Tree;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de uma árvore vermelho e preto.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RBTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    /*
     * Classe interna estática que define os nós da árvore vermelho e preto.
     * Ela e seus membros são públicos para poder expor a estrutura dos nós.
     * Normalmente deveria ser privada.
     */
    public static class Node<Key extends Comparable<Key>, Value> extends Tree.Node<Key, Value> {
        
        public NodeColor color;
        public int n;
        
        @Override
        public String toString() {
            return key + " -> " + value + " (" + ( color == NodeColor.RED ? "V" : "P" ) + ")";
        }
        
    }
    
    /*
     * Enumeração para especificação da cor do nó.
     */
    public enum NodeColor {
        RED,
        BLACK
    }

    // root da árvore
    protected Node<Key, Value> root;
    
    // tamanho da árvore (quantidade de pares chave/valor)
    protected int size;
    
    /**
     * Constrói uma Árvore Vermelho e Preta vazia.
     */
    public RBTree() {
        root = null;
    }
    
    /**
     * Verifica se um nó é vermelho.
     * 
     * @param node O nó a ser verificado
     * @return true caso o nó seja vermelho, false caso seja preto ou null.
     */
    private boolean isRed( Tree.Node<Key, Value> node ) {
        if ( node == null ) {
            return false;
        }
        return ( ( Node<Key, Value>) node ).color == NodeColor.RED;
    }

    /**
     * Retorna o tamanho do nó.
     * number of node in subtree rooted at x; 0 if x is null
     * 
     * @param no O nó a ser verificado.
     * @return O número do nó na sub-árvore enraizada nó ou 0 caso seja null.
     */
    private int nodeSize( Tree.Node<Key, Value> no ) {
        if ( no == null ) {
            return 0;
        }
        return ( (Node<Key, Value>) no ).n;
    }

    public int nodeSize() {
        return nodeSize( root );
    }

    
    @Override
    public void put( Key key, Value value ) {
        
        root = (Node<Key, Value>) put( root, key, value );
        root.color = NodeColor.BLACK;
        
    }
    
    private Tree.Node<Key, Value> put( Tree.Node<Key, Value> node, Key key, Value value ) {
        
        if ( node == null ) {
            
            RBTree.Node<Key, Value> rbNode = new RBTree.Node<>();
            rbNode.key = key;
            rbNode.value = value;
            rbNode.left = null;
            rbNode.right = null;
            rbNode.color = NodeColor.RED;
            rbNode.n = 1;
            
            node = rbNode;
            
            size++;
            
            return node;
            
        }

        int comp = key.compareTo( node.key );
        boolean reorganizeNodes = true;
        
        if ( comp < 0 ) {
            node.left = put( node.left, key, value );
        } else if ( comp > 0 ) {
            node.right = put( node.right, key, value );
        } else { // não faz nada
            reorganizeNodes = false;
        }

        if ( reorganizeNodes ) {
            
            // consertando os links inclinados à right
            if ( isRed( node.right ) && !isRed( node.left ) ) {
                node = rotateLeft( node );
            }
            if ( isRed( node.left ) && isRed( node.left.left ) ) {
                node = rotateRight( node );
            }
            if ( isRed( node.left ) && isRed( node.right ) ) {
                changeColor( node );
            }

            ( (Node<Key, Value>) node ).n = nodeSize( node.left ) + nodeSize( node.right ) + 1;
            
        }

        return node;
        
    }
    
    @Override
    public Value get( Key key ) throws KeyNotFoundException {
        return get( root, key );
    }
    
    private Value get( Tree.Node<Key, Value> node, Key key ) throws KeyNotFoundException {
        
        if ( node != null ) {
            
            int comp = key.compareTo( node.key );
            
            if ( comp == 0 ) {
                return node.value;
            } else if ( comp < 0 ) {
                return get( node.left, key );
            } else { // comparacao > 0
                return get( node.right, key );
            }
            
        }

        throw new KeyNotFoundException( key + " not found!" );

    }
    
    @Override
    public void delete( Key key ) {
        
        if ( !contains( key ) ) {
            return;
        }
        
        // se ambos os filhos da root forem pretos, configura a root como vermelho
        if ( !isRed( root.left ) && !isRed( root.right ) ) {
            root.color = NodeColor.RED;
        }

        root = (Node<Key, Value>) delete( root, key );
        if ( !isEmpty() ) {
            root.color = NodeColor.BLACK;
        }
        
        size--;
        
    }
    
    private Tree.Node<Key, Value> delete( Tree.Node<Key, Value> node, Key key ) {

        if ( key.compareTo( node.key ) < 0 ) {
            
            if ( !isRed( node.left ) && !isRed( node.left.left ) ) {
                node = moveRedToLeft( node );
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
                node = moveRedToRight( node );
            }
            
            if ( key.compareTo( node.key ) == 0 ) {
                node.key = min( node.right ).key;
                node.right = deleteMin( node.right );
            } else {
                node.right = delete( node.right, key );
            }
            
        }
        
        return balancear( node );
        
    }
    
    @Override
    public boolean contains( Key key ) {
        return contains( root, key );
    }
    
    private boolean contains( Tree.Node<Key, Value> node, Key key ) {
        
        while ( node != null ) {
            
            int comparacao = key.compareTo( node.key );

            if ( comparacao == 0 ) {
                return true;
            } else if ( comparacao < 0 ) {
                return contains( node.left, key );
            } else { // comparacao > 0
                return contains( node.left, key );
            }
        }

        return false;
        
    }
    
    public void deleteMin() {
        
        if ( isEmpty() ) {
            throw new NoSuchElementException( "Não há nó mínimo - BST underflow" );
        }

        // se ambos os filhos da root forem pretos, configura a root como vermelho
        if ( !isRed( root.left ) && !isRed( root.right ) ) {
            root.color = NodeColor.RED;
        }

        root = (Node<Key, Value>) deleteMin( root );
        if ( !isEmpty() ) {
            root.color = NodeColor.BLACK;
        }
        
    }
    
    private Tree.Node<Key, Value> deleteMin( Tree.Node<Key, Value> no ) {
        
        if ( no.left == null ) {
            return null;
        }

        if ( !isRed( no.left ) && !isRed( no.left.left ) ) {
            no = moveRedToLeft( no );
        }

        no.left = deleteMin( no.left );
        
        return balancear( no );
        
    }
    
    public void deleteMax() {
        
        if ( isEmpty() ) {
            throw new NoSuchElementException( "Não há nó máximo - BST underflow" );
        }

        // se ambos os filhos da root forem pretos, configura a root como vermelho
        if ( !isRed( root.left ) && !isRed( root.right ) ) {
            root.color = NodeColor.RED;
        }

        root = (Node<Key, Value>) deleteMax( root );
        if ( !isEmpty() ) {
            root.color = NodeColor.BLACK;
        }
        
    }
    
    private Tree.Node<Key, Value> deleteMax( Tree.Node<Key, Value> no ) {
        
        if ( isRed( no.left ) ) {
            no = rotateRight( no );
        }

        if ( no.right == null ) {
            return null;
        }

        if ( !isRed( no.right ) && !isRed( no.right.left ) ) {
            no = moveRedToRight( no );
        }

        no.right = deleteMax( no.right );

        return balancear( no );
        
    }
    
    /**
     * Faz com que o link inclinado à left se incline para a right.
     * 
     * @param node nó de origem
     * @return a nova root da subárvore.
     */
    private Tree.Node<Key, Value> rotateRight( Tree.Node<Key, Value> node ) {
        
        Node<Key, Value> newRoot = (Node<Key, Value>) node.left;
        node.left = newRoot.right;
        
        newRoot.right = node;
        newRoot.color = ( ( Node<Key, Value> ) newRoot.right ).color;
        ( ( Node<Key, Value> ) newRoot.right ).color = NodeColor.RED;
        newRoot.n = ( ( Node<Key, Value> ) node ).n;
        
        ( ( Node<Key, Value> ) node ).n = nodeSize( node.left ) + nodeSize( node.right ) + 1;
        
        return newRoot;
        
    }

    /**
     * Faz com que o link inclinado à right se incline para a left.
     * 
     * @param node nó de origem
     * @return a nova root da subárvore.
     */
    private Tree.Node<Key, Value> rotateLeft( Tree.Node<Key, Value> node ) {
        
        Node<Key, Value> newRoot = (Node<Key, Value>) node.right;
        node.right = newRoot.left;
        
        newRoot.left = node;
        newRoot.color = ( ( Node<Key, Value> ) newRoot.left ).color;
        ( ( Node<Key, Value> ) newRoot.left ).color = NodeColor.RED;
        newRoot.n = ( ( Node<Key, Value> ) node ).n;
        
        ( ( Node<Key, Value> ) node ).n = nodeSize( node.left ) + nodeSize( node.right ) + 1;
        
        return newRoot;
        
    }

    /**
     * Inverte a cor de um nó e de seus filhos.
     * 
     * @param rbNode nó a ser alterado.
     */
    private void changeColor( Tree.Node<Key, Value> node ) {
        
        Node<Key, Value> rbNode = (Node<Key, Value>) node;
        Node<Key, Value> rbNodeLeft = (Node<Key, Value>) node.left;
        Node<Key, Value> rbNodeRight = (Node<Key, Value>) node.right;
        
        rbNode.color = rbNode.color == NodeColor.RED ? NodeColor.BLACK : NodeColor.RED;
        rbNodeLeft.color = rbNodeLeft.color == NodeColor.RED ? NodeColor.BLACK : NodeColor.RED;
        rbNodeRight.color = rbNodeRight.color == NodeColor.RED ? NodeColor.BLACK : NodeColor.RED;
        
    }

    /**
     * Assumindo que o nó é vermelho e ambos os seus filhos são pretos, faz
     * com que no.left ou um de seus filhos seja vermelho.
     * 
     * @param node nó a ser movido.
     * @return o nó modificado.
     */
    private Tree.Node<Key, Value> moveRedToLeft( Tree.Node<Key, Value> node ) {

        changeColor( node );
        
        if ( isRed( node.right.left ) ) {
            node.right = rotateRight( node.right );
            node = rotateLeft( node );
            //trocarCor( no );
        }
        
        return node;
        
    }

    /**
     * Assumindo que o nó é vermelho e ambos os seus filhos são pretos, faz
     * com que no.right ou um de seus filhos seja vermelho.
     * 
     * @param no nó a ser movido.
     * @return o nó modificado.
     */
    private Tree.Node<Key, Value> moveRedToRight( Tree.Node<Key, Value> no ) {
        
        changeColor( no );
        
        if ( isRed( no.left.left ) ) {
            no = rotateRight( no );
            //trocarCor( no );
        }
        
        return no;
        
    }

    /**
     * Recupera a condição de existência (invariante) para a árvore vermelho-preto.
     * 
     * @param node nó de origem.
     * @return o nó modificado.
     */
    private Tree.Node<Key, Value> balancear( Tree.Node<Key, Value> node ) {

        if ( isRed( node.right ) ) {
            node = rotateLeft( node );
        }
        
        if ( isRed( node.left ) && isRed( node.left.left ) ) {
            node = rotateRight( node );
        }
        
        if ( isRed( node.left ) && isRed( node.right ) ) {
            changeColor( node );
        }

        ( (Node<Key, Value>) node ).n = nodeSize( node.left ) + nodeSize( node.right ) + 1;
        
        return node;
        
    }

    public int height() {
        return height( root );
    }

    private int height( Tree.Node<Key, Value> x ) {
        
        if ( x == null ) {
            return 0;
        }
        
        return 1 + Math.max( height( x.left ), height( x.right ) );
        
    }
    
    public Key min() {
        
        if ( isEmpty() ) {
            return null;
        }
        
        return min( root ).key;
        
    }
    
    private Tree.Node<Key, Value> min( Tree.Node<Key, Value> node ) {
        
        //assert x != null;
        
        if ( node.left == null ) {
            return node;
        } else {
            return min( node.left );
        }
        
    }
    
    public Key max() {
        
        if ( isEmpty() ) {
            return null;
        }
        
        return max( root ).key;
        
    }
    
    private Tree.Node<Key, Value> max( Tree.Node<Key, Value> node ) {
        
        //assert no != null;
        
        if ( node.right == null ) {
            return node;
        } else {
            return max( node.right );
        }
        
    }
    
    public Key floor( Key key ) {
        
        Tree.Node<Key, Value> x = floor( root, key );
        
        if ( x == null ) {
            return null;
        } else {
            return x.key;
        }
        
    }
    
    private Tree.Node<Key, Value> floor( Tree.Node<Key, Value> node, Key key ) {
        
        if ( node == null ) {
            return null;
        }
        
        int comparacao = key.compareTo( node.key );
        
        if ( comparacao == 0 ) {
            return node;
        }
        if ( comparacao < 0 ) {
            return floor( node.left, key );
        }
        
        Tree.Node<Key, Value> t = floor( node.right, key );
        if ( t != null ) {
            return t;
        } else {
            return node;
        }
        
    }
    
    public Key ceil( Key key ) {
        
        Tree.Node<Key, Value> x = ceil( root, key );
        
        if ( x == null ) {
            return null;
        } else {
            return x.key;
        }
        
    }
    
    private Tree.Node<Key, Value> ceil( Tree.Node<Key, Value> node, Key key ) {
        
        if ( node == null ) {
            return null;
        }
        
        int comparacao = key.compareTo( node.key );
        
        if ( comparacao == 0 ) {
            return node;
        }
        if ( comparacao > 0 ) {
            return ceil( node.right, key );
        }
        
        Tree.Node<Key, Value> t = ceil( node.left, key );
        if ( t != null ) {
            return t;
        } else {
            return node;
        }
        
    }
    
    public Key select( int k ) {
        
        if ( k < 0 || k >= nodeSize() ) {
            return null;
        }
        
        Tree.Node<Key, Value> x = select( root, k );
        return x.key;
        
    }
    
    private Tree.Node<Key, Value> select( Tree.Node<Key, Value> node, int k ) {
        
        //assert no != null;
        //assert k >= 0 && k < tamanho( no );
        
        int t = nodeSize( node.left );
        
        if ( t > k ) {
            return select( node.left, k );
        } else if ( t < k ) {
            return select( node.right, k - t - 1 );
        } else {
            return node;
        }
        
    }
    
    public int rank( Key key ) {
        return rank( root, key );
    }
    
    private int rank( Tree.Node<Key, Value> node, Key key ) {
        
        if ( node == null ) {
            return 0;
        }
        
        int comparacao = key.compareTo( node.key );
        
        if ( comparacao < 0 ) {
            return rank( node.left, key );
        } else if ( comparacao > 0 ) {
            return 1 + nodeSize( node.left ) + rank( node.right, key );
        } else {
            return nodeSize( node.left );
        }
        
    }
    
    public Queue<Key> keys() {
        return keys( min(), max() );
    }
    
    public Queue<Key> keys( Key menor, Key maior ) {
        
        Queue<Key> fila = new LinkedQueue<Key>();
        
        if ( isEmpty() || menor.compareTo( maior ) > 0 ) {
            return fila;
        }
        
        keys( root, fila, menor, maior );
        
        return fila;
        
    }
    
    private void keys( Tree.Node<Key, Value> node, Queue<Key> queue, Key min, Key max ) {
        
        if ( node == null ) {
            return;
        }
        
        int comparacaoMenor = min.compareTo( node.key );
        int comparacaoMaior = max.compareTo( node.key );
        
        if ( comparacaoMenor < 0 ) {
            keys( node.left, queue, min, max );
        }
        if ( comparacaoMenor <= 0 && comparacaoMaior >= 0 ) {
            queue.enqueue( node.key );
        }
        if ( comparacaoMaior > 0 ) {
            keys( node.right, queue, min, max );
        }
        
    }
    
    public int quantity( Key min, Key max ) {
        
        if ( min.compareTo( max ) > 0 ) {
            return 0;
        }
        
        if ( contains( max ) ) {
            return rank( max ) - rank( min ) + 1;
        } else {
            return rank( max ) - rank( min );
        }
        
    }
    
    private boolean verify() {
        
        if ( !isBST() ) {
            System.out.println( "Não possui ordem simétrica" );
        }
        
        if ( !isSizeConsistent() ) {
            System.out.println( "Contagens das subárvores não está consistente" );
        }
        
        if ( !isRankConsistent() ) {
            System.out.println( "Ranques não consistentes" );
        }
        
        if ( !is23() ) {
            System.out.println( "Não é uma árvore 2-3" );
        }
        
        if ( !isBalanced() ) {
            System.out.println( "Não está balanceada" );
        }
        
        return isBST() && 
               isSizeConsistent() && 
               isRankConsistent() && 
               is23() && 
               isBalanced();
        
    }
    
    private boolean isBST() {
        return isBST( root, null, null );
    }
    
    private boolean isBST( Tree.Node<Key, Value> node, Key min, Key max ) {
        
        if ( node == null ) {
            return true;
        }
        
        if ( min != null && node.key.compareTo( min ) <= 0 ) {
            return false;
        }
        
        if ( max != null && node.key.compareTo( max ) >= 0 ) {
            return false;
        }
        
        return isBST( node.left, min, node.key ) && isBST( node.right, node.key, max );
        
    }
    
    private boolean isSizeConsistent() {
        return isSizeConsistent( root );
    }
    
    private boolean isSizeConsistent( Tree.Node<Key, Value> no ) {
        
        if ( no == null ) {
            return true;
        }
        
        if ( ( (Node<Key, Value>) no ).n != nodeSize( no.left ) + nodeSize( no.right ) + 1 ) {
            return false;
        }
        
        return isSizeConsistent( no.left ) && isSizeConsistent( no.right );
        
    }
    
    private boolean isRankConsistent() {
        
        for ( int i = 0; i < nodeSize(); i++ ) {
            if ( i != rank(select( i ) ) ) {
                return false;
            }
        }
        
        Queue<Key> fila = keys();
        while ( fila.isEmpty()) {
            Key key = fila.dequeue();
            if ( key.compareTo(select(rank( key ) ) ) != 0 ) {
                return false;
            }
        }
        
        return true;
        
    }
    
    private boolean is23() {
        return is23( root );
    }
    
    private boolean is23( Tree.Node<Key, Value> node ) {
        
        if ( node == null ) {
            return true;
        }
        
        if ( isRed( node.right ) ) {
            return false;
        }
        
        if ( node != root && isRed( node ) && isRed( node.left ) ) {
            return false;
        }
        
        return is23( node.left ) && is23( node.right );
        
    }
    
    private boolean isBalanced() {
        
        int black = 0;
        Tree.Node<Key, Value> current = root;
        
        while ( current != null ) {
            
            if ( !isRed( current ) ) {
                black++;
            }
            
            current = current.left;
            
        }
        
        return isBalanced( root, black );
        
    }
    
    private boolean isBalanced( Tree.Node<Key, Value> no, int black ) {
        
        if ( no == null ) {
            return black == 0;
        }
        
        if ( !isRed( no ) ) {
            black--;
        }
        
        return isBalanced( no.left, black ) && isBalanced( no.right, black );
        
    }

    @Override
    public void clear() {
        root = (Node<Key, Value>) clear( root );
        size = 0;
    }

    /*
     * Método privado para remoção de todos os itens de forma recursiva.
     */
    private Tree.Node<Key, Value> clear( Tree.Node<Key, Value> node ) {

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
    
    @Override
    public Tree.Node<Key, Value> getRoot() {
        return root;
    }
    
    @Override
    public Iterator<Tree.Entry<Key, Value>> iterator() {
        return TreeTraversals.traverse( this, TraversalTypes.IN_ORDER ).iterator();
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if ( !isEmpty() ) {
            
            for ( Tree.Entry<Key, Value> e : TreeTraversals.traverse( this, TraversalTypes.IN_ORDER ) ) {
                
                if ( e.getKey().equals( root.key ) ) {
                    sb.append( e ).append( " <- root\n" );
                } else {
                    sb.append( e ).append( "\n" );
                }
                
            }
            
        } else {
            sb.append( "empty tree!\n" );
        }
        
        return sb.toString();
        
    }
    
}
