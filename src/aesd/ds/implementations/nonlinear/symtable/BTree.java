/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.symtable;

import java.util.Iterator;
import aesd.ds.interfaces.SymbolTable;

/**
 * Implementação de uma árvore b (B-Tree).
 *
 * Uma árvore b generaliza a árvore binária de busca permitindo que cada nó
 * tenha vários filhos (não apenas dois), o que reduz a altura da árvore e,
 * consequentemente, a quantidade de acessos a disco necessários para
 * localizar uma chave — por isso é a estrutura clássica de índices de
 * bancos de dados e sistemas de arquivos, cenários em que cada acesso a um
 * nó é uma operação de E/S cara.
 *
 * M (constante privada) é a quantidade máxima de filhos por nó; todo nó,
 * exceto a raiz, deve ter entre M/2 e M filhos — manter essa ocupação
 * mínima é o que garante que a árvore permaneça balanceada.
 *
 * Cada nó (BNode) guarda um array de Entry, mas o significado de uma Entry
 * muda de acordo com o nível do nó:
 *     - em um nó externo (folha, ht == 0), a Entry representa um par
 *       chave/valor de fato armazenado na árvore;
 *     - em um nó interno (ht > 0), a Entry funciona como separador/
 *       roteador: sua chave é a menor chave presente na subárvore apontada
 *       por next, usada para decidir por qual filho descer durante a busca.
 *
 * A variável height (e o parâmetro ht usado nos métodos recursivos) conta
 * quantos níveis internos faltam percorrer até alcançar os nós externos;
 * ht chega a 0 exatamente quando o método está operando sobre um nó
 * externo.
 *
 * Esta implementação didática cobre apenas inserção e busca; delete(),
 * contains(), clear(), getKeys() e iterator() lançam
 * UnsupportedOperationException de propósito, não por estarem incompletos.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms.
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 *
 * @param <Key> Tipo das chaves que serão armazenadas na árvore.
 * @param <Value> Tipo dos valores associados às chaves armazenadas na árvore.
 *
 * @author Prof. Dr. David Buzatto
 */
public class BTree<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    // máximo de filhos por nó = M-1
    // precisa ser par e maior que 2
    private static final int M = 4;

    /*
     * Classe interna estática que define os nós da árvore b.
     */
    @SuppressWarnings( "unchecked" )
    private static final class BNode<Key extends Comparable<Key>, Value> {
        
        // quantidade de filhos
        private int m;
        
        // array de filhos
        private Entry<Key, Value>[] children = (Entry<Key, Value>[]) new Entry[M];

        // cria um nó com m filhos
        private BNode( int m ) {
            this.m = m;
        }
        
    }

    /*
     * Classe interna estática que define as entradas dos nós da árvore b.
     * Nós internos usam apenas key e next (chave e próximo): key é a menor
     * chave da subárvore apontada por next, funcionando como separador.
     * Nós externos usam apenas key e value (chave e valor).
     */
    private static final class Entry<Key extends Comparable<Key>, Value> {
        
        private Key key;
        private Value value;
        private BNode next;
        
        public Entry( Key key, Value value, BNode<Key, Value> next ) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
        
    }
    
    // raiz da árvore
    private BNode<Key, Value> root;
    
    // altura da árvore
    private int height;
    
    // quantidade de pares chave-valor contidos na árvore
    private int n;

    // true se a última chamada a insert() adicionou uma chave nova
    // (não apenas atualizou o valor de uma chave já existente)
    private boolean added;

    /**
     * Constrói uma Árvore b vazia.
     */
    public BTree() {
        root = new BNode<>( 0 );
    }

    @Override
    public boolean isEmpty() {
        return getSize() == 0;
    }

    @Override
    public int getSize() {
        return n;
    }
    
    public int getHeight() {
        return height;
    }

    @Override
    public Value get(Key key) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument to get() is null" );
        }
        
        return search( root, key, height );
        
    }

    @SuppressWarnings( "unchecked" )
    private Value search( BNode<Key, Value> x, Key key, int ht ) {
        
        Entry<Key, Value>[] children = x.children;

        // nó externo
        if ( ht == 0 ) {
            for ( int j = 0; j < x.m; j++ ) {
                if ( eq( key, children[j].key ) ) {
                    return children[j].value;
                }
            }
        } else { // nó interno
            // a chave de cada entrada interna é a menor chave de sua
            // subárvore, então basta achar o primeiro filho cuja chave é
            // maior que key (ou o último filho, se nenhum for) para saber
            // que key, se existir, está na subárvore do filho anterior
            for ( int j = 0; j < x.m; j++ ) {
                if ( j+1 == x.m || less( key, (Key) children[j+1].key ) ) {
                    return (Value) search( children[j].next, key, ht-1 );
                }
            }
        }
        
        return null;
        
    }

    @Override
    public void put( Key key, Value val ) {
        
        if ( key == null ) {
            throw new IllegalArgumentException( "argument key to put() is null" );
        }

        added = false;
        BNode<Key, Value> u = insert( root, key, val, height );

        if ( added ) {
            n++;
        }

        if ( u == null ) {
            return;
        }

        // precisa dividir a raiz
        BNode<Key, Value> t = new BNode<>( 2 );
        t.children[0] = new Entry<>( root.children[0].key, null, root );
        t.children[1] = new Entry<>( u.children[0].key, null, u );
        
        root = t;
        height++;
        
    }

    @SuppressWarnings( "unchecked" )
    private BNode<Key, Value> insert( BNode<Key, Value> h, Key key, Value val, int ht ) {
        
        int j;
        Entry<Key, Value> t = new Entry<>( key, val, null );

        // nó externo
        if ( ht == 0 ) {
            for ( j = 0; j < h.m; j++ ) {
                if ( eq( key, h.children[j].key ) ) {
                    // chave já existe: apenas atualiza o valor associado,
                    // sem crescer o nó nem disparar split
                    h.children[j].value = val;
                    return null;
                }
                if ( less( key, h.children[j].key ) ) {
                    break;
                }
            }
            added = true;
        } else { // nó interno
            for ( j = 0; j < h.m; j++ ) {
                if ( ( j+1 == h.m ) || less( key, h.children[j+1].key ) ) {

                    // desce recursivamente pela subárvore correta; se a
                    // inserção lá embaixo causar um split, u é o novo nó
                    // irmão à direita que precisa ser inserido neste nível
                    BNode<Key, Value> u = insert( h.children[j++].next, key, val, ht-1 );

                    if ( u == null ) {
                        return null;
                    }

                    // a chave roteadora da nova entrada é a menor chave do
                    // nó dividido (u), mantendo a invariante de que toda
                    // entrada interna aponta para sua subárvore usando a
                    // menor chave dela como separador
                    t.key = u.children[0].key;
                    t.value = null;
                    t.next = u;

                    break;

                }
            }
        }

        for ( int i = h.m; i > j; i-- ) {
            h.children[i] = h.children[i-1];
        }

        h.children[j] = t;
        h.m++;

        // se o nó ainda não atingiu a capacidade máxima M, a inserção
        // termina aqui; caso contrário, ele precisa ser dividido, e o novo
        // nó irmão retornado propaga o split para o nível acima (put(), ou
        // a chamada recursiva de insert() no nível pai)
        if ( h.m < M ) {
            return null;
        } else {
            return split(h);
        }

    }

    // divide o nó em dois: a metade da esquerda continua em h, e a metade
    // da direita é devolvida como um novo nó irmão, que caberá ao chamador
    // (insert() ou put()) inserir no nível acima como uma nova entrada
    // roteadora
    private BNode<Key, Value> split( BNode<Key, Value> h ) {

        BNode<Key, Value> t = new BNode<>( M / 2 );
        h.m = M / 2;

        for ( int j = 0; j < M/2; j++ ) {
            t.children[j] = h.children[M/2+j];
        }

        return t;

    }

    @Override
    public String toString() {
        return toString( root, height, "" ) + "\n";
    }

    @SuppressWarnings( "unchecked" )
    private String toString( BNode<Key, Value> h, int ht, String indent ) {
        
        StringBuilder s = new StringBuilder();
        Entry<Key, Value>[] children = h.children;

        if ( ht == 0 ) {
            for (int j = 0; j < h.m; j++) {
                s.append( indent )
                        .append( children[j].key )
                        .append( " " )
                        .append( children[j].value )
                        .append("\n");
            }
        } else {
            for (int j = 0; j < h.m; j++) {
                if ( j > 0 ) {
                    s.append( indent )
                            .append( "(" )
                            .append( children[j].key )
                            .append( ")\n" );
                }
                s.append( toString( children[j].next, ht-1, indent + "     " ) );
            }
        }
        
        return s.toString();
        
    }


    private boolean less( Key k1, Key k2 ) {
        return k1.compareTo(k2) < 0;
    }

    private boolean eq( Key k1, Key k2 ) {
        return k1.compareTo(k2) == 0;
    }

    /**
     * Não implementado nesta árvore b: escopo desta implementação didática
     * é apenas inserção e busca.
     *
     * @param key Chave usada na busca.
     * @throws UnsupportedOperationException sempre, de propósito.
     */
    @Override
    public void delete( Key key ) throws IllegalArgumentException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    /**
     * Não implementado nesta árvore b: escopo desta implementação didática
     * é apenas inserção e busca.
     *
     * @param key Chave usada na busca.
     * @return nunca retorna.
     * @throws UnsupportedOperationException sempre, de propósito.
     */
    @Override
    public boolean contains( Key key ) throws IllegalArgumentException {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    /**
     * Não implementado nesta árvore b: escopo desta implementação didática
     * é apenas inserção e busca.
     *
     * @throws UnsupportedOperationException sempre, de propósito.
     */
    @Override
    public void clear() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    /**
     * Não implementado nesta árvore b: escopo desta implementação didática
     * é apenas inserção e busca.
     *
     * @return nunca retorna.
     * @throws UnsupportedOperationException sempre, de propósito.
     */
    @Override
    public Iterable<Key> getKeys() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }

    /**
     * Não implementado nesta árvore b: escopo desta implementação didática
     * é apenas inserção e busca.
     *
     * @return nunca retorna.
     * @throws UnsupportedOperationException sempre, de propósito.
     */
    @Override
    public Iterator<SymbolTable.Entry<Key, Value>> iterator() {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
    
}
