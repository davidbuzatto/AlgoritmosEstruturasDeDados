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
 * Baseado no código de:
 *     WEISS, M. A. Data Structures and Algorithm Analysis in Java. 3. ed. 
 *     Pearson Education: New Jersey, 2012. 614 p.
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
        root = put( root, key, value );
    }
    
    private Node<Key, Value> put( Node<Key, Value> node, Key key, Value value ) {
        
        if ( node == null ) {
            
            node = new Node<>();
            node.key = key;
            node.value = value;
            node.left = null;
            node.right = null;
            node.height = 0;
            
            size++;
            
        }

        int comp = key.compareTo( node.key );

        if ( comp < 0 ) {
            node.left = put( (Node<Key, Value>) node.left, key, value );
        } else if ( comp > 0 ) {
            node.right = put( (Node<Key, Value>) node.right, key, value );
        }

        // não faz nada para duplicatas
        
        // balanceia a árvore
        return balancear( node );
        
    }
    
    @Override
    public Value get( Key key ) throws KeyNotFoundException {
        return get( root, key );
    }

    /*
     * Método privado para a consulta recursiva.
     */
    private Value get( Node<Key, Value> node, Key key ) throws KeyNotFoundException {
        
        if ( node != null ) {
            
            int comp = key.compareTo( node.key );
            
            if ( comp == 0 ) {
                return node.value;
            } else if ( comp < 0 ) {
                return get( (Node<Key, Value>) node.left, key );
            } else { // comparacao > 0
                return get( (Node<Key, Value>) node.right, key );
            }
            
        }

        throw new KeyNotFoundException( key + " not found!" );

    }
    
    @Override
    public boolean contains( Key key ) {
        return contains( root, key );
    }
    
    private boolean contains( Node<Key, Value> node, Key key ) {
        
        while ( node != null ) {
            
            int comparacao = key.compareTo( node.key );

            if ( comparacao == 0 ) {
                return true;
            } else if ( comparacao < 0 ) {
                return contains( (Node<Key, Value>) node.left, key );
            } else { // comparacao > 0
                return contains( (Node<Key, Value>) node.left, key );
            }
        }

        return false;
        
    }
    
    @Override
    public void delete( Key key ) {
        root = (Node<Key, Value>) delete( root, key );
    }

    /**
     * Método privado para remoção recursiva na subárvore.
     *
     * @param node raiz da subárvore.
     * @param key elemento que será removido.
     * @return a nova raiz da subárvore.
     */
    private Node<Key, Value> delete( Node<Key, Value> node, Key key ) {
        
        if ( node == null ) {
            return node;   // não encontrado
        }
        
        int comp = key.compareTo( node.key );

        if ( comp < 0 ) {
            node.left = delete( (Node<Key, Value>) node.left, key );
        } else if ( comp > 0 ) {
            node.right = delete( (Node<Key, Value>) node.right, key );
        } else if ( node.left != null && node.right != null ) { // dois filhos
            node.key = encontrarMinimo( (Node<Key, Value>) node.right ).key;
            node.right = delete( (Node<Key, Value>) node.right, node.key );
        } else {
            node = ( (Node<Key, Value>) node.left != null ) ? (Node<Key, Value>) node.left : (Node<Key, Value>) node.right;
        }
        
        return balancear( node );
        
    }

    /**
     * Encontra o menor item da árvore.
     *
     * @return o menor item ou null caso a árvore esteja vazia.
     */
    public Node<Key, Value> encontrarMinimo() {
        
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
    private Node<Key, Value> encontrarMinimo( Node<Key, Value> node ) {
        
        if ( node == null ) {
            return node;
        }

        while ( node.left != null ) {
            node = (Node<Key, Value>) node.left;
        }
        
        return node;
        
    }

    /**
     * Encontra o maior item da árvore.
     *
     * @return o maior item ou null caso a árvore esteja vazia.
     */
    public Node<Key, Value> encontrarMaximo() {
        
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
    private Node<Key, Value> encontrarMaximo( Node<Key, Value> node ) {
        
        if ( node == null ) {
            return node;
        }

        while ( node.right != null ) {
            node = (Node<Key, Value>) node.right;
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
    private Node<Key, Value> clear( Node<Key, Value> node ) {

        if ( node != null ) {
            node.left = clear( (Node<Key, Value>) node.left );
            node.right = clear( (Node<Key, Value>) node.right );
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
    public Node<Key, Value> getRoot() {
        return root;
    }
    
    // Assume que no está balanceado ou está sendo balanceado
    private Node<Key, Value> balancear( Node<Key, Value> node ) {
        
        if ( node == null ) {
            return node;
        }

        if ( height( (Node<Key, Value>) node.left ) - height( (Node<Key, Value>) node.right ) > DESBALANCEAMENTO_PERMITIDO ) {
            if ( height( (Node<Key, Value>) (Node<Key, Value>) node.left.left ) >= height( (Node<Key, Value>) node.left.right ) ) {
                node = rotacionarComFilhoEsquerdo( node );
            } else {
                node = rotacionarDuploComFilhoEsquerdo( node );
            }
        } else if ( height( (Node<Key, Value>) node.right ) - height( (Node<Key, Value>) node.left ) > DESBALANCEAMENTO_PERMITIDO ) {
            if ( height( (Node<Key, Value>) node.right.right ) >= height( (Node<Key, Value>) node.right.left ) ) {
                node = rotacionarComFilhoDireito( node );
            } else {
                node = rotacionarDuploComFilhoDireito( node );
            }
        }

        node.height = Math.max( height( (Node<Key, Value>) node.left ), height( (Node<Key, Value>) node.right ) ) + 1;
        
        return node;
        
    }

    public void verificarBalanceamento() {
        verificarBalanceamento( root );
    }

    private int verificarBalanceamento( Node<Key, Value> node ) {
        
        if ( node == null ) {
            return -1;
        }

        if ( node != null ) {
            
            int alturaEsquerda = verificarBalanceamento( (Node<Key, Value>) node.left );
            int alturaDireita = verificarBalanceamento( (Node<Key, Value>) node.right );
            
            if ( Math.abs( height( (Node<Key, Value>) node.left ) - height( (Node<Key, Value>) node.right ) ) > 1 || 
                    height( (Node<Key, Value>) node.left ) != alturaEsquerda || 
                    height( (Node<Key, Value>) node.right ) != alturaDireita ) {
                System.out.println( "Desbalanceamento encontrado!" );
            }
            
        }

        return height( node );
        
    }

    /**
     * Retorna a altura de um no ou -1 caso no seja nulo.
     */
    private int height( Node<Key, Value> node ) {
        return node == null ? -1 : node.height;
    }

    /**
     * Rotação EE/LL
     * Rotacionada um nó com filho à esquerda. Para as árvores AVL, essa é a 
     * rotação simples do caso 1. Atualiza as alturas e retorna a nova raiz.
     */
    private Node<Key, Value> rotacionarComFilhoEsquerdo( Node<Key, Value> a ) {
        Node<Key, Value> b = (Node<Key, Value>) a.left;
        a.left = b.right;
        b.right = a;
        a.height = Math.max( height( (Node<Key, Value>) a.left ), height( (Node<Key, Value>) a.right ) ) + 1;
        b.height = Math.max( height( (Node<Key, Value>) b.left ), a.height ) + 1;
        return b;
    }

    /**
     * Rotação DD/RR
     * Rotacionada um nó com filho à direita. Para as árvores AVL, essa é a 
     * rotação simples do caso 4. Atualiza as alturas e retorna a nova raiz.
     */
    private Node<Key, Value> rotacionarComFilhoDireito( Node<Key, Value> a ) {
        Node<Key, Value> b = (Node<Key, Value>) a.right;
        a.right = b.left;
        b.left = a;
        a.height = Math.max( height( (Node<Key, Value>) a.left ), height( (Node<Key, Value>) a.right ) ) + 1;
        b.height = Math.max( height( (Node<Key, Value>) b.right ), a.height ) + 1;
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
    private Node<Key, Value> rotacionarDuploComFilhoEsquerdo( Node<Key, Value> a ) {
        a.left = rotacionarComFilhoDireito( (Node<Key, Value>) a.left );
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
    private Node<Key, Value> rotacionarDuploComFilhoDireito( Node<Key, Value> a ) {
        a.right = rotacionarComFilhoEsquerdo( (Node<Key, Value>) a.right );
        return rotacionarComFilhoDireito( a );
    }

    @Override
    public Iterator<Tree.Entry<Key, Value>> iterator() {
        return TreeTraversals.traverse( this, TraversalTypes.IN_ORDER ).iterator();
    }
    
    /**
     * Cria uma representação em String da árvore.
     * Esta representação apresenta os elementos na ordem do percurso em ordem.
     */
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
