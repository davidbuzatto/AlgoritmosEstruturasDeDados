/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.ds.implementations;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.algorithms.tree.TreeTraversals;
import aesd.esdc4.ds.exceptions.KeyNotFoundException;
import java.util.Iterator;
import aesd.esdc4.ds.interfaces.Tree;

/**
 * Implementação de uma árvore binária de busca fundamental (Binary Search Tree).
 * 
 * É importante frisar que essa árvore não aceita valores duplicados.
 * Caso tente-se inserir um valor que já existe, ele será ignorado.
 * 
 * Algumas modificações de acesso foram feitas na classe, permitindo que alguns
 * detalhes internos dela sejam acessíveis externamente. Essa mudança
 * teve como objetivo permitir que os detalhes estruturais da árvore possam ser
 * usados pela classe com os algoritmos de percursos (TreeTraversals).
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 * @param <Key> Tipo das chaves que serão armazenadas na árvore.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na árvore.
 */
public class BSTree<Key extends Comparable<Key>, Value> implements Tree<Key, Value> {

    // raiz da árvore
    protected Node<Key, Value> root;
    
    // tamanho da árvore (quantidade de pares chave/valor)
    protected int size;
    
    /**
     * Constrói uma árvore binária de busca vazia.
     */
    public BSTree() {
        root = null;   // redundante, apenas para mostrar o que acontece
    }
    
    @Override
    public void put( Key key, Value value ) {
        
        /*
         * Algoritmo iterativo.
         */
        /*Node<Key, Value> newNode = new Node<>();
        newNode.key = key;
        newNode.value = value;
        newNode.left = null;    // redundante
        newNode.right = null;   // idem...

        if ( isEmpty() ) {
            root = newNode;
        } else {

            boolean found = false;
            Node<Key, Value> temp = root;
            int comp = 0;
 
            while ( !found ) {

                comp = key.compareTo( temp.key );

                if ( comp < 0 ) {
 
                    if ( temp.left == null ) {
                        temp.left = newNode;
                        found = true;
                    } else {
                        temp = temp.left;
                    }
 
                } else if ( comp > 0 ) {
 
                    if ( temp.right == null ) {
                        temp.right = newNode;
                        found = true;
                    } else {
                        temp = temp.right;
                    }
 
                } else { // igual, não insere
                    break;
                }
                
            }
        
            size++;

        }*/

        /*
         * Algoritmo recursivo.
         */
        root = put( root, key, value );
        
    }
    
    /*
     * Método privado para a inserção recursiva.
     */
    private Node<Key, Value> put( Node<Key, Value> node, Key key, Value value ) {
        
        if ( node == null ) {

            node = new Node<>();
            node.key = key;
            node.value = value;
            node.left = null;
            node.right = null;
            
            size++;

        } else {
            
            int comp = key.compareTo( node.key );
            
            if ( comp < 0 ) {
                node.left = put( node.left, key, value );
            } else if ( comp > 0 ) {
                node.right = put( node.right, key, value );
            }
            
        }

        return node;

    }
    
    @Override
    public Value get( Key key ) throws KeyNotFoundException {
        
        /*
         * Algoritmo iterativo.
         */
        /*if ( !isEmpty() ) {

            Node<Key, Value> temp = root;
            int comp = 0;

            while ( true ) {
                
                comp = key.compareTo( temp.key );
                
                if ( comp == 0 ) {
                    return temp.value;
                } else if ( comp < 0 ) {

                    if ( temp.left == null ) {
                        break;
                    } else {
                        temp = temp.left;
                    }

                } else { // comparacao > 0

                    if ( temp.right == null ) {
                        break;
                    } else {
                        temp = temp.right;
                    }

                }

            }

        }
        
        throw new KeyNotFoundException( key + " not found!" );*/
        
        /*
         * Algoritmo recursivo.
         */
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
                return get( node.left, key );
            } else { // comparacao > 0
                return get( node.right, key );
            }
            
        }

        throw new KeyNotFoundException( key + " not found!" );

    }
    
    @Override
    public void delete( Key key ) {

        /*
         * Algoritmo iterativo.
         */
        /*if ( !isEmpty()) {

            Node<Key, Value> current = root;
            Node<Key, Value> previous = null;
            char path = '\0';
            int comp = 0;

            while ( true ) {
                
                comp = key.compareTo( current.key );
                
                if ( comp == 0 ) {
                    
                    // o nó não tem filhos
                    if ( current.left == current.right ) {

                        // está na raiz
                        if ( previous == null ) {
                            root = null;
                        } else {
                            if ( path == 'e' ) {
                                previous.left = null;
                            } else if ( path == 'd' ) {
                                previous.right = null;
                            }
                        }

                    // o nó a ser removido não tem filho à esquerda, só à direita
                    // a primeira condição garante que se os dois nós não são o mesmo,
                    // um deles pode ser null.
                    } else if ( current.left == null ) {
                        
                        // está na raiz
                        if ( previous == null ) {
                            root = current.right;
                        } else {
                            if ( path == 'e' ) {
                                previous.left = current.right;
                            } else if ( path == 'd' ) {
                                previous.right = current.right;
                            }
                        }
                        
                        current.right = null;

                    // o nó a ser removido não tem filho à direita, só à esquerda
                    // a primeira condição garante que se os dois nós não são o mesmo,
                    // um deles pode ser null.
                    } else if ( current.right == null ) {

                        // está na raiz
                        if ( previous == null ) {
                            root = current.left;
                        } else {
                            if ( path == 'e' ) {
                                previous.left = current.left;
                            } else if ( path == 'd' ) {
                                previous.right = current.left;
                            }
                        }
                        
                        current.left = null;

                    // o nó a ser removido tem filhos em ambos os lados
                    } else {

                        // busca pelo menor nó, onde a subárvore esquerda
                        // será inserida
                        Node<Key, Value> min = current.right;

                        while ( min.left != null ) {
                            min = min.left;
                        }

                        // reaponta a subárvore esquerda do nó removido
                        // no menor item encontrado
                        min.left = current.left;
                        
                        // está na raiz
                        if ( previous == null ) {
                            root = current.right;
                        } else {
                            if ( path == 'e' ) {
                                previous.left = current.right;
                            } else if ( path == 'd' ) {
                                previous.right = current.right;
                            }
                        }
                        
                        current.left = null;
                        current.right = null;

                    }
                    
                    size--;
                    break;
                    
                } else if ( comp < 0 ) {

                    if ( current.left == null ) {
                        break;
                    } else {
                        previous = current;
                        path = 'e';
                        current = current.left;
                    }

                } else { // comparacao > 0

                    if ( current.right == null ) {
                        break;
                    } else {
                        previous = current;
                        path = 'd';
                        current = current.right;
                    }

                }

            }

        }*/

        /*
         * Algoritmo recursivo.
         */
        root = delete( root, key );

    }
    
    /*
     * Método privado para a remoção recursiva (Hibbard Deletion).
     */
    private Node<Key, Value> delete( Node<Key, Value> node, Key key ) {
        
        if ( node != null ) {
            
            Node<Key, Value> temp;
            int comp = key.compareTo( node.key );

            if ( comp == 0 ) {
                
                size--;
                
                // o nó não tem filhos
                if ( node.left == node.right ) {

                    return null;

                // o nó a ser removido não tem filho à esquerda, só à direita
                // a primeira condição garante que se os dois nós não são o mesmo,
                // um deles pode ser null.
                } else if ( node.left == null ) {

                    temp = node.right;
                    node.right = null;
                    return temp;

                // o nó a ser removido não tem filho à direita, só à esquerda
                // a primeira condição garante que se os dois nós não são o mesmo,
                // um deles pode ser null.
                } else if ( node.right == null ) {

                    temp = node.left;
                    node.left = null;
                    return temp;

                // o nó a ser removido tem filhos em ambos os lados
                } else {

                    // busca pelo menor nó, onde a subárvore esquerda
                    // será inserida
                    temp = node.right;
                    Node<Key, Value> min = temp;

                    while ( min.left != null ) {
                        min = min.left;
                    }

                    // reaponta a subárvore esquerda do nó removido
                    // no menor item encontrado
                    min.left = node.left;

                    node.left = null;
                    node.right = null;

                    return temp;

                }

            } else if ( comp < 0 ) {
                node.left = delete( node.left, key );
            } else { // comparacao > 0
                node.right = delete( node.right, key );
            }
            
        }

        return node;

    }
    
    @Override
    public boolean contains( Key key ) {
        
        /*
         * Algoritmo iterativo.
         */
        /*boolean found = false;
        
        if ( !isEmpty() ) {

            Node<Key, Value> temp = root;
            int comp = 0;

            while ( !found ) {
                
                comp = key.compareTo( temp.key );
                
                if ( comp == 0 ) {
                    found = true;
                    break;
                } else if ( comp < 0 ) {

                    if ( temp.left == null ) {
                        found = false;
                        break;
                    } else {
                        temp = temp.left;
                    }

                } else { // comparacao > 0

                    if ( temp.right == null ) {
                        found = false;
                        break;
                    } else {
                        temp = temp.right;
                    }

                }

            }

        }
        
        return found;*/
        
        /*
         * Algoritmo recursivo.
         */
        return contains( root, key );
        
    }

    /*
     * Método privado para a consulta recursiva.
     */
    private boolean contains( Node<Key, Value> node, Key key ) {
        
        if ( node != null ) {
            
            int comp = key.compareTo( node.key );
            
            if ( comp == 0 ) {
                return true;
            } else if ( comp < 0 ) {
                return contains( node.left, key );
            } else { // comparacao > 0
                return contains( node.right, key );
            }
            
        }

        return false;

    }
    
    @Override
    public void clear() {
        root = clear( root );
        size = 0;
    }
    
    /*
     * Método privado para remoção de todos os itens de forma recursiva.
     */
    private Node<Key, Value> clear( Node<Key, Value> node ) {

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
    public Node<Key, Value> getRoot() {
        return root;
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
