/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.ds.implementations.working;

import aesd.esdc4.algorithms.graphb.BFS;
import aesd.esdc4.algorithms.graphb.ConnectedComponents;
import aesd.esdc4.algorithms.graphb.DFS;
import aesd.esdc4.algorithms.graphb.GraphBasicAlgorithms;
import aesd.esdc4.ds.implementations.DoublyLinkedList;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.interfaces.Stack;

/**
 * Implementação de um grafo utilizando lista de adjacências.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BasicGraph {
    
    private int v;
    private int e;
    private List<Integer>[] adj;
    
    /**
     * Constrói um grafo com v vértices.
     * 
     * @param v Quantidade de vértices.
     */
    @SuppressWarnings( "unchecked" )
    public BasicGraph( int v ) {
        
        this.v = v;
        
        // cria o array da lista de adjacências
        adj = (List<Integer>[]) new List[v];
        
        // inicializa cada posição com uma lista vazia
        for ( int i = 0; i < v; i++ ) {
            adj[i] = new DoublyLinkedList<>();
        }
        
    }
    
    /**
     * Adicionar uma aresta v-w.
     * 
     * @param v Uma extremidade da aresta.
     * @param w Outra extremidade da aresta.
     */
    public void adicionarAresta( int v, int w ) {
        
        // como é um grafo, a aresta é de ida e volta, ou seja,
        // v-w e w-v. em laços, haverá dois laços iguais para cada vértice
        // com laço
        adj[v].add( w );
        adj[w].add( v );
        /*adj[v].inserirFim( w );
        adj[w].inserirFim( v );*/
        
        e += 1;
        
    }
    
    /**
     * Retorna o conjunto de vértices adjacentes a v.
     * 
     * @param v Vértique que se deseja obter os adjacentes.
     * @return Vértices adjacentes a v.
     */
    public Iterable<Integer> adj( int v ) {
        return adj[v];
    }
    
    public int v() {
        return v;
    }
    
    public int e() {
        return e;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        int cont;
        
        sb.append( "Vértices: " ).append( v() ).append( "\n" );
        sb.append( "Arestas: " ).append( e() ).append( "\n" );
        
        for ( int v = 0; v < v(); v++ ) {
            
            cont = 0;
            sb.append( "v: " ).append( v ).append( " -> { " );
            
            for ( int w : adj[v] ) {
                if ( cont == adj[v].getSize() - 1 ) {
                    sb.append( w ).append( " " );
                } else {
                    sb.append( w ).append( ", " );
                }
                cont++;
            }
            
            sb.append( "}\n" );
            
        }
        
        return sb.toString();
        
    }
    
    public static void main( String[] args ) {
        
        BasicGraph g = new BasicGraph( 13 );        
        g.adicionarAresta( 0, 5 );
        g.adicionarAresta( 4, 3 );
        g.adicionarAresta( 0, 1 );
        g.adicionarAresta( 9, 12 );
        g.adicionarAresta( 6, 4 );
        g.adicionarAresta( 5, 4 );
        g.adicionarAresta( 0, 2 );
        g.adicionarAresta( 11, 12 );
        g.adicionarAresta( 9, 10 );
        g.adicionarAresta( 0, 6 );
        g.adicionarAresta( 7, 8 );
        g.adicionarAresta( 9, 11 );
        g.adicionarAresta( 5, 3 );
        
        System.out.println( g );
        
        // utilizando os algoritmos
        System.out.println( "Grau do vértice 0: " + GraphBasicAlgorithms.grau( g, 0 ) );
        System.out.println( "Grau máximo: " + GraphBasicAlgorithms.grauMaximo( g ) );
        System.out.println( "Grau médio: " + GraphBasicAlgorithms.grauMedio( g ) );
        System.out.println( "Quantidade de laços: " + GraphBasicAlgorithms.quantidadeLacos( g ) );
        
        DFS dfs = new DFS( g, 0 );
        System.out.println( dfs );
        
        for ( int w = 0; w < g.v(); w++ ) {
            System.out.printf( "Caminho de 0 a %d: ", w );
            Stack<Integer> p = dfs.caminhoAte( w );
            while ( !p.isEmpty()) {
                System.out.print( p.pop() + " " );
            }
            System.out.println();
        }
        System.out.println();
        
        
        BFS bfs = new BFS( g, 0 );
        System.out.println( bfs );
        
        for ( int w = 0; w < g.v(); w++ ) {
            System.out.printf( "Caminho de 0 a %d: ", w );
            Stack<Integer> p = bfs.caminhoAte( w );
            while ( !p.isEmpty()) {
                System.out.print( p.pop() + " " );
            }
            System.out.println();
        }
        System.out.println();
        
        ConnectedComponents cc = new ConnectedComponents( g );
        System.out.println( "Quantidade de componentes conexos: " + cc.getQuantidade() );
        
    }
    
}
