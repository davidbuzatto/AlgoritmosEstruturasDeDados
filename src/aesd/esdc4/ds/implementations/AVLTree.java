/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.algorithms.tree.TreeTraversals;
import aesd.esdc4.ds.exceptions.KeyNotFoundException;
import aesd.esdc4.ds.interfaces.Tree;
import java.util.Iterator;

/**
 * Implementação de uma árvore AVL.
 * 
 * Implementação baseada na obra: WEISS, M. A. Data Structures and Algorithm
 * Analysis in Java. 3. ed. Pearson Education: New Jersey, 2012. 614 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AVLTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    /*
     * Classe interna estática que define os nós da árvore AVL.
     * Ela e seus membros são públicos para poder expor a estrutura dos nós.
     * Normalmente deveria ser privada.
     */
    public static class Node<Key extends Comparable<Key>, Value> extends Tree.Node<Key, Value> {
        
        public int height;
        
        @Override
        public String toString() {
            return key + " -> " + value + " (" + height + ")";
        }
        
    }
    
    // raiz da árvore
    protected Node<Key, Value> root;
    
    // tamanho da árvore (quantidade de pares chave/valor)
    protected int size;
    
    // valor máximo na diferença de alturas de duas subárvores
    private static final int DESBALANCEAMENTO_PERMITIDO = 1;
    
    /**
     * Constrói uma Árvore AVL vazia.
     */
    public AVLTree() {
        root = null;
    }
    
    @Override
    public void put( Key key, Value value ) {
        root = (Node<Key, Value>) put( root, key, value );
    }
    
    private Tree.Node<Key, Value> put( Tree.Node<Key, Value> node, Key key, Value value ) {
        
        if ( node == null ) {
            
            Node<Key, Value> avlNode = new Node<>();
            avlNode.key = key;
            avlNode.value = value;
            avlNode.left = null;
            avlNode.right = null;
            avlNode.height = 0;
            
            node = avlNode;
            
            size++;
            
        }

        int comp = key.compareTo( node.key );

        if ( comp < 0 ) {
            node.left = put( node.left, key, value );
        } else if ( comp > 0 ) {
            node.right = put( node.right, key, value );
        }

        // não faz nada para duplicatas
        
        // balanceia a árvore
        return balancear( node );
        
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
        
        root = (Node<Key, Value>) delete( root, key );
        size--;
        
    }

    /**
     * Método privado para remoção recursiva na subárvore.
     *
     * @param node raiz da subárvore.
     * @param key elemento que será removido.
     * @return a nova raiz da subárvore.
     */
    private Tree.Node<Key, Value> delete( Tree.Node<Key, Value> node, Key key ) {
        
        if ( node == null ) {
            return node;   // não encontrado
        }
        
        int comp = key.compareTo( node.key );

        if ( comp < 0 ) {
            node.left = delete( node.left, key );
        } else if ( comp > 0 ) {
            node.right = delete( node.right, key );
        } else if ( node.left != null && node.right != null ) { // dois filhos
            node.key = encontrarMinimo( node.right ).key;
            node.right = delete( node.right, node.key );
        } else {
            node = ( node.left != null ) ? node.left : node.right;
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

    /**
     * Encontra o menor item da árvore.
     *
     * @return o menor item ou null caso a árvore esteja vazia.
     */
    public Tree.Node<Key, Value> encontrarMinimo() {
        
        if ( isEmpty() ) {
            return null;
        }
        
        return encontrarMinimo( root );
        
    }
    
    /**
     * Método privado para encontrar o menor item de uma subárvore.
     *
     * @param node raiz da subárvore
     * @return o nó que contém o menor item.
     */
    private Tree.Node<Key, Value> encontrarMinimo( Tree.Node<Key, Value> node ) {
        
        if ( node == null ) {
            return node;
        }

        while ( node.left != null ) {
            node = node.left;
        }
        
        return node;
        
    }

    /**
     * Encontra o maior item da árvore.
     *
     * @return o maior item ou null caso a árvore esteja vazia.
     */
    public Tree.Node<Key, Value> encontrarMaximo() {
        
        if ( isEmpty() ) {
            return null;
        }
        
        return encontrarMaximo( root );
        
    }
    
    /**
     * Método privado para encontrar o maior item de uma subárvore.
     *
     * @param node raiz da subárvore
     * @return o nó que contém o maior item.
     */
    private Tree.Node<Key, Value> encontrarMaximo( Tree.Node<Key, Value> node ) {
        
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
    
    // Assume que no está balanceado ou está sendo balanceado
    private Tree.Node<Key, Value> balancear( Tree.Node<Key, Value> node ) {
        
        if ( node == null ) {
            return node;
        }

        if ( height( node.left ) - height( node.right ) > DESBALANCEAMENTO_PERMITIDO ) {
            if ( height( node.left.left ) >= height( node.left.right ) ) {
                node = rotacionarComFilhoEsquerdo( node );
            } else {
                node = rotacionarDuploComFilhoEsquerdo( node );
            }
        } else if ( height( node.right ) - height( node.left ) > DESBALANCEAMENTO_PERMITIDO ) {
            if ( height( node.right.right ) >= height( node.right.left ) ) {
                node = rotacionarComFilhoDireito( node );
            } else {
                node = rotacionarDuploComFilhoDireito( node );
            }
        }

        ( (Node<Key, Value>) node ).height = Math.max( height( node.left ), height( node.right ) ) + 1;
        
        return node;
        
    }

    public void verificarBalanceamento() {
        verificarBalanceamento( root );
    }

    private int verificarBalanceamento( Tree.Node<Key, Value> node ) {
        
        if ( node == null ) {
            return -1;
        }

        if ( node != null ) {
            
            int alturaEsquerda = verificarBalanceamento( node.left );
            int alturaDireita = verificarBalanceamento( node.right );
            
            if ( Math.abs( height( node.left ) - height( node.right ) ) > 1 || 
                    height( node.left ) != alturaEsquerda || 
                    height( node.right ) != alturaDireita ) {
                System.out.println( "Desbalanceamento encontrado!" );
            }
            
        }

        return height( node );
        
    }

    /**
     * Retorna a altura de um no ou -1 caso no seja nulo.
     */
    private int height( Tree.Node<Key, Value> node ) {
        return node == null ? -1 : ( (Node<Key, Value>) node ).height;
    }

    /**
     * Rotação EE/LL
     * Rotacionada um nó com filho à esquerda. Para as árvores AVL, essa é a 
     * rotação simples do caso 1. Atualiza as alturas e retorna a nova raiz.
     */
    private Tree.Node<Key, Value> rotacionarComFilhoEsquerdo( Tree.Node<Key, Value> a ) {
        
        Tree.Node<Key, Value> b = a.left;
        a.left = b.right;
        b.right = a;
        
        Node<Key, Value> aAvl = (Node<Key, Value>) a;
        Node<Key, Value> bAvl = (Node<Key, Value>) b;
        
        aAvl.height = Math.max( height( a.left ), height( a.right ) ) + 1;
        bAvl.height = Math.max( height( b.left ), aAvl.height ) + 1;
        
        return b;
        
    }

    /**
     * Rotação DD/RR
     * Rotacionada um nó com filho à direita. Para as árvores AVL, essa é a 
     * rotação simples do caso 4. Atualiza as alturas e retorna a nova raiz.
     */
    private Tree.Node<Key, Value> rotacionarComFilhoDireito( Tree.Node<Key, Value> a ) {
        
        Tree.Node<Key, Value> b = a.right;
        a.right = b.left;
        b.left = a;
        
        Node<Key, Value> aAvl = (Node<Key, Value>) a;
        Node<Key, Value> bAvl = (Node<Key, Value>) b;
        
        aAvl.height = Math.max( height( a.left ), height( a.right ) ) + 1;
        bAvl.height = Math.max( height( b.right ), aAvl.height ) + 1;
        
        return b;
        
    }

    /**
     * Rotação ED/LR
     * Realiza uma rotação dupla:
     *     1 - filho da esquerda com seu filho da direita;
     *     2 - nó a com seu novo filho à esquerda.
     * 
     * Para as árvores AVL, essa é a rotação dupla do caso 2.
     * Atualiza as alturas e retorna a nova raiz.
     */
    private Tree.Node<Key, Value> rotacionarDuploComFilhoEsquerdo( Tree.Node<Key, Value> a ) {
        a.left = rotacionarComFilhoDireito( a.left );
        return rotacionarComFilhoEsquerdo( a );
    }

    /**
     * Rotação DE/RL
     * Realiza uma rotação dupla:
     *     1 - filho da direita com seu filho da esquerda;
     *     2 - nó a com seu novo filho à direita.
     * 
     * Para as árvores AVL, essa é a rotação dupla do caso 3.
     * Atualiza as alturas e retorna a nova raiz.
     */
    private Tree.Node<Key, Value> rotacionarDuploComFilhoDireito( Tree.Node<Key, Value> a ) {
        a.right = rotacionarComFilhoEsquerdo( a.right );
        return rotacionarComFilhoDireito( a );
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
