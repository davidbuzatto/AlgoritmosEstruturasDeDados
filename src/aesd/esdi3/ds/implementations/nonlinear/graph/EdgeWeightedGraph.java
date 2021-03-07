/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.nonlinear.graph;

import aesd.esdi3.ds.implementations.linear.Bag;
import aesd.esdi3.ds.implementations.linear.ResizingArrayList;
import aesd.esdi3.ds.implementations.linear.ResizingArrayStack;
import aesd.esdi3.ds.interfaces.List;
import aesd.esdi3.ds.interfaces.Stack;

/**
 * Implementação de um grafo não direcionado com listas de adjacências.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EdgeWeightedGraph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // listas de adjacências - adj[v] = vértices adjacentes à v
    private Bag<Edge>[] adj;

    /**
     * Cria um grafo ponderado com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    @SuppressWarnings( "unchecked" )
    public EdgeWeightedGraph( int vertices ) throws IllegalArgumentException {
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "number of vertices must be nonnegative" );
        }
        
        this.vertices = vertices;
        this.edges = 0;
        
        adj = new Bag[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new Bag<>();
        }
        
    }

    /**
     * Cria um grafo ponderado que é a cópia profunda do grafo ponderado 
     * passado como parâmetro.
     *
     * @param graph O grafo que será copiado
     * @throws IllegalArgumentException se o grafo passado for null
     */
    @SuppressWarnings( "unchecked" )
    public EdgeWeightedGraph( EdgeWeightedGraph graph ) throws IllegalArgumentException {
        
        if ( graph == null ) {
            throw new IllegalArgumentException( "argument is null" );
        }
        
        this.vertices = graph.getNumberOfVertices();
        this.edges = graph.getNumberOfEdges();
        
        // atualiza as listas de adjacências
        adj = new Bag[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new Bag<>();
        }
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            
            // inverte as listas de adjacências para ficarem iguais às originais
            Stack<Edge> reverse = new ResizingArrayStack<>();
            
            for ( Edge e : graph.adj[v] ) {
                reverse.push( e );
            }
            
            for ( Edge e : reverse ) {
                adj[v].add( e );
            }
            
        }
        
    }

    /**
     * Retorna a quantidade vértices desse digrafo.
     *
     * @return o número de vértices do digrafo
     */
    public int getNumberOfVertices() {
        return vertices;
    }

    /**
     * Retorna a quantidade arestas desse grafo.
     *
     * @return o número de arestas do grafo
     */
    public int getNumberOfEdges() {
        return edges;
    }
    
    private void validateVertex( int v ) throws IllegalArgumentException {
        
        if ( v < 0 || v >= vertices ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( vertices - 1 ) );
        }
        
    }

    /**
     * Adds the undirected edge {@code e} to this edge-weighted graph.
     *
     * @param v o vértice de calda/origem
     * @param w O vértice de cabeça/destino
     * @param weight O peso da aresta
     * @throws IllegalArgumentException unless both endpoints are between
     * {@code 0} and {@code V-1}
     */
    public void addEdge( int v, int w, double weight ) throws IllegalArgumentException {
        
        validateVertex( v );
        validateVertex( w );
        
        Edge e = new Edge( v, w, weight );
        
        adj[v].add( e );
        adj[w].add( e );
        
        edges++;
        
    }

    /**
     * Retorna os vértices adjacentes à v.
     *
     * @param v o vértice
     * @return os vértices adjacentes ao vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public Iterable<Edge> adj( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return adj[v];
    }

    /**
     * Retorna o grau do vértice v.
     *
     * @param v o vértice
     * @return o grafu do vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public int degree( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return adj[v].getSize();
    }

    /**
     * Retorna todas as arestas do grafo ponderado.
     *
     * @return todas as aretas como um iterável.
     */
    public Iterable<Edge> edges() {
        
        List<Edge> list = new ResizingArrayList<>();
        
        for ( int v = 0; v < vertices; v++ ) {
            
            int selfLoops = 0;
            for ( Edge e : adj( v ) ) {
                
                if ( e.other( v ) > v ) {
                    
                    list.add( e );
                    
                    // adiciona apenas uma cópia dos loops
                } else if ( e.other( v ) == v ) {
                    if ( selfLoops % 2 == 0 ) {
                        list.add( e );
                    }
                    selfLoops++;
                }
                
            }
            
        }
        
        return list;
        
    }

    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append( vertices ).append( " " ).append( edges ).append( "\n" );
        
        for ( int v = 0; v < vertices; v++ ) {
            s.append( v ).append( ": " );
            for ( Edge e : adj[v] ) {
                s.append( e ).append( "  " );
            }
            s.append( "\n" );
        }
        
        return s.toString();
        
    }

}
