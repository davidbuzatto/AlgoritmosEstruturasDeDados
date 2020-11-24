/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.ds.implementations.working;

import aesd.esdc4.ds.exceptions.EmptyHeapException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implementação de um heap mínimo (min-heap).
 * 
 * Critério de ordenação:
 *     - Min-Heap:
 *         * Elemento pai sempre menor ou igual aos filhos
 *     – Chaves armazenadas nos nós, neste caso, posições de uma lista.
 *
 * Construção:
 *     – Árvores binárias (até dois filhos)
 *     – Completa: elementos sem filhos apenas no último nível
 *      (e anterior, quando o último nível não está completo)
 *
 * Árvore binária completa:
 *     – Armazenamento direto em array!
 *     - Raiz na posição 1
 *     - Último elemento na posição tamanho - 1
 *     – Manipulação dos índices:
 *          * Pai: posição do filho / 2
 *          * Filho esquerda: posição do pai * 2
 *          * Filho direita: posicao do pai * 2 + 1
 *
 *     - Para raiz na posição 0, a geração das posições seria
 *          * Pai: (posição do filho - 1) / 2
 *          * Filho esquerda: posição do pai * 2 + 1
 *          * Filho direita: posicao do pai * 2 + 2
 *
 * Elemento violando a condição heap mínimo
 *     – Valor menor que o pai
 *        * O elemento precisa "subir" na árvore
 *        * Bottom-Up heapify (swim => flutuar)
 *     – Valor maior que os filhos (um ou dois)
 *        * O elemento precisa "descer" na árvore
 *        * Top-Down heapify (sink => afundar)
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MinHeap<Tipo extends Comparable<Tipo>> implements Iterable<Tipo> {
    
    private List<Tipo> dados;
    
    /**
     * Constrói o heap mínimo.
     */
    public MinHeap() {
        
        dados = new ArrayList<>();
        
        // primeira posição recebe um null
        // ela não será utilizada
        dados.add( null );
        
    }
    
    /**
     * Insere um elemento no heap mínimo.
     * 
     * @param valor Elemento a ser inserido.
     */
    public void inserir( Tipo valor ) {
        
        // insere no fim da lista
        dados.add( valor );
        
        // flutua o último elemento
        flutuar( dados.size() - 1 );
        
    }
    
    /**
     * Remove o maior elemento do heap mínimo.
     * 
     * @return O maior elemento.
     */
    public Tipo remover() throws EmptyHeapException {
        
        if ( !estaVazia() ) {
            
            // salva o elemento que está sempre na raiz
            Tipo temp = dados.get( 1 );
            
            // troca a raiz com o último
            trocar( 1, dados.size() - 1 );
            
            // remove o último elemento (a raiz está nessa posição agora)
            dados.remove( dados.size() - 1 );
            
            // afunda a nova raiz
            afundar( 1 );
            
            return temp;
            
        } else {
            throw new EmptyHeapException();
        }
        
    }
    
    /**
     * Retorna o menor elemento do heap mínimo sem removê-lo.
     * 
     * @return O menor elemento.
     */
    public Tipo consultar() throws EmptyHeapException {
        
        if ( !estaVazia() ) {
            return dados.get( 1 );
        } else {
            throw new EmptyHeapException();
        }
        
    }
    
    /*
     * Algoritmo para organização do heap mínimo.
     *
     * Flutua o nó k para a posição correta (baixo para cima)
     * se necessário (se for menor que o seu pai).
     */
    private void flutuar( int k ) {

        // se o nó k não é a raiz (nó 1) e
        // seu pai (k/2) for maior que ele
        while ( k > 1 && dados.get( k/2 ).compareTo( dados.get( k ) ) > 0 ) {

            // troca o pai pelo filho
            trocar( k/2, k );

            // indica que o nó que será processado
            // na próxima iteração é o pai do nó k atual
            k = k/2;

        }

    }

    /*
     * Algoritmo para organização do heap mínimo.
     *
     * Afunda o nó k para a posição correta (cima para baixo)
     * se necessário (se for maior que algum de seus filhos).
     */
    private void afundar( int k  ) {

        // posição do filho
        int j;
        
        // última posição válida
        int n = dados.size() - 1;

        // se o filho está numa posição válida (dentro do limite)
        while ( 2*k <= n ) {

            // filho da esquerda
            j = 2*k;

            // se j está dentro do limite
            // e o valor da posição j é maior que
            // o valor do seu irmão
            if ( j < n && dados.get( j ).compareTo( dados.get( j+1 ) ) > 0 ) {

                // muda para o irmão (filho da direita)
                // pois o filho à esquerda é maior que o pai
                // e maior que o irmão (o irmão pode ir para o lugar
                // do pai se for menor que ele e o min-heap se mantém)
                j++;

            }

            // se o valor do pai for menor ou igual
            // ao valor do filho da direita (que faltou ser testado).
            // está ok, pois atende à regra do min-heap e termina
            // o loop
            if ( dados.get( k ).compareTo( dados.get( j ) ) <= 0 ) {
                break;
            }

            // caso contrário (se o nó k for maior que o nó j),
            // troca o pai pelo filho da direita (nó j)
            trocar( k, j );

            // indica o novo pai como o filho da direita
            // para dar continuidade ao processo de afundamento
            k = j;

        }

    }
    
    /**
     * Troca dois elementos da lista que contém os elementos do heap mínimo
     * 
     * @param p1 A posição de um elemento.
     * @param p2 A posição do outro elemento.
     */
    private void trocar( int p1, int p2 ) {
        
        Tipo temp = dados.get( p1 );
        
        dados.set( p1, dados.get( p2 ) );
        dados.set( p2, temp );
        
    }
    
    /**
     * Esvazia o heap mínimo.
     */
    public void esvaziar() {
        
        while ( !estaVazia() ) {
            remover();
        }
        
    }
    
    /**
     * Verifica se o heap mínimo está vazio.
     * 
     * @return true se o heap mínimo estiver vazio, false caso contrário.
     */
    public boolean estaVazia() {
        
        // sempre haverá um elemento na lista, pois é o null que não será usado
        return dados.size() == 1;
        
    }
    
    /**
     * Obtém o tamanho do heap mínimo (quantidade de elementos).
     * 
     * @return O tamanho do heap mínimo.
     */
    public int getTamanho() {
        
        // desconsidera o primeiro elemento que é null
        return dados.size() - 1;
        
    }
    
    /**
     * Cria uma representação em String do heap mínimo.
     * Esta representação apresenta os elementos usando o percurso em nível.
     */
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if ( !estaVazia() ) {
            
            boolean primeiro = true;
            boolean segundo = false;
            
            for ( Tipo item : dados ) {
                
                if ( primeiro ) {
                    primeiro = false;
                    segundo = true;
                } else {
                    
                    sb.append( item );
                    
                    if ( segundo ) {
                        segundo = false;
                        sb.append( " <- mínimo" );
                    }
                    
                    sb.append( "\n" );
                    
                }
                
            }
            
        } else {
            sb.append( "fila vazia!\n" );
        }
        
        return sb.toString();
        
    }
    
    /**
     * Cria um iterador para o heap mínimo, permitindo iterar por todos os elementos
     * do mesmo, além de poder usar em um for each (for melhorado).
     * Este iterador percorre o heap mínimo em nível.
     */
    @Override
    public Iterator<Tipo> iterator() {
        
        return new Iterator<Tipo>() {
            
            private int atual = 1;
            
            @Override
            public boolean hasNext() {
                return atual != dados.size();
            }

            @Override
            public Tipo next() {
                Tipo item = dados.get( atual++ );
                return item;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException( "Não suportado." );
            }
            
        };
        
    }
    
    /**
     * Testes do heap mínimo.
     * 
     * @param args
     */
    public static void main( String[] args ) {
        
        MinHeap<Integer> hm = new MinHeap<>();
        
        hm.inserir( 10 );
        System.out.println( hm );
        hm.inserir( 5 );
        System.out.println( hm );
        hm.inserir( -2 );
        System.out.println( hm );
        hm.inserir( 3 );
        System.out.println( hm );
        hm.inserir( 7 );
        System.out.println( hm );
        hm.inserir( 15 );
        System.out.println( hm );
        hm.inserir( 5 );
        System.out.println( hm );
        
        // usando o iterador
        for ( int v : hm ) {
            System.out.println( "Item: " + v );
        }
        System.out.println();
        
        System.out.println( "Removeu: " + hm.remover() );
        System.out.println( hm );
        System.out.println( "Removeu: " + hm.remover() );
        System.out.println( hm );
        System.out.println( "Removeu: " + hm.remover() );
        System.out.println( hm );
        System.out.println( "Removeu: " + hm.remover() );
        System.out.println( hm );
        System.out.println( "Removeu: " + hm.remover() );
        System.out.println( hm );
        System.out.println( "Removeu: " + hm.remover() );
        System.out.println( hm );
        System.out.println( "Removeu: " + hm.remover() );
        System.out.println( hm );
        //System.out.println( "Removeu: " + hm.remover() ); // <- heap mínimo vazio!
        
    }
    
}
