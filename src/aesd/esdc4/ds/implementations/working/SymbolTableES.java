/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.ds.implementations.working;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Implementação de uma tabela de símbolos usando encadeamento simples.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class SymbolTableES<TipoChave extends Comparable<TipoChave>, TipoValor> implements Iterable<TipoValor> {
    
    /*
     * Classe privada que define os nós da estrutura encadeada da tabela de 
     * símbolos.
     */
    private class No<TipoChave, TipoValor> {
        TipoChave chave;
        TipoValor valor;
        No<TipoChave, TipoValor> anterior;
    }
    
    // marca o primeiro nó da estrutura
    private No<TipoChave, TipoValor> primeiro;
    
    // tamanho da fila
    private int tamanho;
    
    /**
     * Constrói uma tabela de símbolos vazia.
     */
    public SymbolTableES() {
        primeiro = null;
        tamanho = 0;
    }
    
    /**
     * Insere um novo valor na tabela de símbolos.
     * 
     * @param chave chave usada para armazenar o valor.
     * @param valor valor a ser armazenado.
     */
    public void inserir( TipoChave chave, TipoValor valor ) {
        
        if ( estaVazia() ) {
            
            No<TipoChave, TipoValor> novoNo = new No<>();
            novoNo.chave = chave;
            novoNo.valor = valor;
            novoNo.anterior = null;
            
            primeiro = novoNo;
            
            tamanho++;
            
        } else {
            
            boolean encontrou = false;
            No<TipoChave, TipoValor> atual = primeiro;
            
            while ( atual != null ) {
                
                // se já existir um elemento com a chave, atualiza
                if ( atual.chave.equals( chave ) ) {
                    atual.valor = valor;
                    encontrou = true;
                    break;
                }
                
                atual = atual.anterior;
                
            }
            
            // se não encontrou, insere à frente do primeiro 
            // mudando sua posição
            if ( !encontrou ) {
                
                No<TipoChave, TipoValor> novoNo = new No<>();
                novoNo.chave = chave;
                novoNo.valor = valor;
                novoNo.anterior = primeiro;
                
                primeiro = novoNo;
                
                tamanho++;
                
            }
            
        }
        
    }
    
    /**
     * Obtém o valor contido na tabela de símbolos que está associado à chave
     * passada.
     * 
     * @param chave cahve a ser utilizada na pesquisa.
     * @return valor encontrado na posição. Retorna null caso não encontre.
     */
    public TipoValor obter( TipoChave chave ) {
        
        No<TipoChave, TipoValor> atual = primeiro;

        while ( atual != null ) {
            if ( atual.chave.equals( chave ) ) {
                return atual.valor;
            }
            atual = atual.anterior;
        }

        return null;
        
    }
    
    /**
     * Remove o elemento associado à chave, inclusive a chave da tabela de 
     * símbolos.
     * 
     * @param chave chave a ser utilizada na pesquisa.
     */
    public void remover( TipoChave chave ) {

        if ( !estaVazia() ) {
            
            if ( tamanho == 1 && primeiro.chave.equals( chave ) ) {

                primeiro = null;
                tamanho--;

            } else if ( primeiro.chave.equals( chave ) ) {

                // temp é o elemento com a chave
                No<TipoChave, TipoValor> temp = primeiro;
                primeiro = temp.anterior;
                temp.anterior = null;

                tamanho--;

            } else {

                No<TipoChave, TipoValor> atual = primeiro;

                while ( atual != null ) {

                    // para antes de chegar no elemento com a chave
                    if ( atual.anterior.chave.equals( chave ) ) {

                        // temp é o elemento com a chave
                        No<TipoChave, TipoValor> temp = atual.anterior;
                        atual.anterior = temp.anterior;
                        temp.anterior = null;

                        tamanho--;

                        break;

                    }

                    atual = atual.anterior;

                }

            }
            
        }
        
    }
    
    /**
     * Verifica se a tabela de símbolos contém um elemento indexado com a chave
     * especificada.
     * 
     * @param chave chave a ser utilizada na busca.
     * @return true caso a tabela contenha o elemento especificado, false caso
     * contrário.
     */
    public boolean contem( TipoChave chave ) {
        
        for ( TipoChave ch : chaves() ) {
            if ( ch.equals( chave ) ) {
                return true;
            }
        }
        
        return false;
        
    }
    
    /**
     * Esvazia a a tabela de símbolos.
     */
    public void esvaziar() {
        
        for ( TipoChave chave : chaves() ) {
            remover( chave );
        }
        
    }
    
    /**
     * Verifica se a tabela de símbolos está vazia.
     * 
     * @return true se a tabela de símbolos estiver vazia, false caso contrário.
     */
    public boolean estaVazia() {
        return primeiro == null;
    }
    
    /**
     * Obtém o tamanho da tabela de símbolos (quantidade de elementos).
     * 
     * @return O tamanho da tabela de símbolos.
     */
    public int getTamanho() {
        return tamanho;
    }
    
    /**
     * Obtém uma coleção iterável que contém as chaves da tabela de símbolos.
     * 
     * @return Coleção com as chaves da tabela de símbolos.
     */
    public Iterable<TipoChave> chaves() {
        
        List<TipoChave> lista = new ArrayList<>();
        No<TipoChave, TipoValor> atual = primeiro;
        
        while ( atual != null ) {
            lista.add( atual.chave );
            atual = atual.anterior;
        }
        
        return lista;
        
    }
    
    /**
     * Cria uma representação em String da tabela de símbolos.
     * Esta representação apresenta os elementos no sentido primeiro -> último.
     */
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        if ( !estaVazia() ) {
            
            // percorrendo a estrutura encadeada
            No<TipoChave, TipoValor> atual = primeiro;

            while ( atual != null ) {
                
                if ( atual == primeiro ) {
                    sb.append( String.format( "[%s] - ", atual.chave ) )
                            .append( atual.valor ).append( " <- primeiro\n" );
                } else {
                    sb.append( String.format( "[%s] - ", atual.chave ) )
                            .append( atual.valor ).append( "\n" );
                }
                
                atual = atual.anterior;

            }
            
        } else {
            sb.append( "tabela de símbolos vazia!\n" );
        }
        
        return sb.toString();
        
    }
    
    /**
     * Cria um iterador para a tabela de símbolos, permitindo iterar por todos
     * os elementos da mesma, além de poder usar em um for each (for melhorado).
     * Este iterador percorre a tabela de símbolos no sentido primeiro -> último.
     */
    @Override
    public Iterator<TipoValor> iterator() {
        
        return new Iterator<TipoValor>() {
            
            private No<TipoChave, TipoValor> atual = primeiro;
            
            @Override
            public boolean hasNext() {
                return atual != null;
            }

            @Override
            public TipoValor next() {
                TipoValor item = atual.valor;
                atual = atual.anterior;
                return item;
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException( "Não suportado." );
            }
            
        };
        
    }
    public static void main( String[] args ) {
        
        SymbolTableES<String, String> tabelaSimbolos = new SymbolTableES<>();
        
        tabelaSimbolos.inserir( "A", "TipoValor 1" );
        System.out.println( tabelaSimbolos );
        tabelaSimbolos.inserir( "X", "TipoValor 2" );
        System.out.println( tabelaSimbolos );
        tabelaSimbolos.inserir( "C", "TipoValor 3" );
        System.out.println( tabelaSimbolos );
        tabelaSimbolos.inserir( "D", "TipoValor 4" );
        System.out.println( tabelaSimbolos );
        tabelaSimbolos.inserir( "A", "TipoValor 5" );
        System.out.println( tabelaSimbolos );
        tabelaSimbolos.inserir( "Y", "TipoValor 6" );
        System.out.println( tabelaSimbolos );
        
        // chaves
        for ( String c : tabelaSimbolos.chaves() ) {
            System.out.println( "Chave: " + c );
        }
        System.out.println();
        
        // valores usando o iterador
        for ( String v : tabelaSimbolos ) {
            System.out.println( "Item: " + v );
        }
        System.out.println();
        
        // removendo de forma aleatória
        List<String> chaves = (List<String>) tabelaSimbolos.chaves();
        Collections.shuffle( chaves );
        for ( String c : chaves ) {
            System.out.printf( "Removeu o elemento [%s].\n", c );
            tabelaSimbolos.remover( c );
            System.out.println( tabelaSimbolos );
        }
        
    }
    
}
