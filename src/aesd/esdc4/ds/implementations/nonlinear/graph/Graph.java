/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.graph;

import aesd.esdc4.ds.implementations.linear.ResizingArrayList;
import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.interfaces.Stack;

/**
 * Implementação de um grafo não direcionado com lista de adjacências.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Graph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // lista de adjacências
    private List<Integer>[] adj;

    /**
     * Cria um grafo com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    @SuppressWarnings( "unchecked" )
    public Graph( int vertices ) {
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "Number of vertices must be nonnegative" );
        }
        
        this.vertices = vertices;
        this.edges = 0;
        
        adj = new ResizingArrayList[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new ResizingArrayList<>();
        }
        
    }

    /**
     * Cria um grafo que é a cópia profunda do grafo passado como parâmetro.
     *
     * @param graph O grafo que será copiado
     * @throws IllegalArgumentException se o grafo passado for null
     */
    @SuppressWarnings( "unchecked" )
    public Graph( Graph graph ) {
        
        this.vertices = graph.getNumberOfVertices();
        this.edges = graph.getNumberOfEdges();
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "Number of vertices must be nonnegative" );
        }

        // atualiza a lista de adjacências
        adj = new ResizingArrayList[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new ResizingArrayList<>();
        }

        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            
            // inverte a lista de adjacências para ficar igual à original
            Stack<Integer> reverse = new ResizingArrayStack<Integer>();
            
            for ( int w : graph.adj[v] ) {
                reverse.push( w );
            }
            
            for ( int w : reverse ) {
                adj[v].add( w );
            }
            
        }
        
    }

    /**
     * Retorna a quantidade vértices desse grafo.
     *
     * @return o número de vértices do grafo
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

    private void validateVertex( int v ) {
        
        if ( v < 0 || v >= vertices ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( vertices - 1 ) );
        }
        
    }

    /**
     * Adiciona uma aresta não direcionada v-w à esse grafo.
     *
     * @param v um dos vértices
     * @param w o outro vértice
     * @throws IllegalArgumentException se os vértices forem inválidos
     */
    public void addEdge( int v, int w ) {
        
        validateVertex( v );
        validateVertex( w );
        
        adj[v].add( w );
        adj[w].add( v );
        
        edges++;
        
    }

    /**
     * Retorna os vértices adjacentes à v.
     *
     * @param v o vértice
     * @return os vértices adjacentes ao vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public Iterable<Integer> adj( int v ) {
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
    public int degree( int v ) {
        validateVertex( v );
        return adj[v].getSize();
    }
    
    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append( vertices ).append( " vertices, " ).append( edges ).append(" edges \n");
        
        for ( int v = 0; v < vertices; v++ ) {
            s.append( v ).append(": ");
            for ( int w : adj[v] ) {
                s.append( w ).append( " " );
            }
            s.append( "\n" );
        }
        
        return s.toString();
        
    }

}
