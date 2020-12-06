/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Graph;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 * Realiza a busca em largura para computar o menor caminho entre o vértice
 * fonte e todos os outros vértices do grafo.
 * 
 * s é o vértice fonte (source).
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class BreadthFirstSearch {

    // maior valor possível do tipo inteiro
    private static final int INFINITY = Integer.MAX_VALUE;

    // marked[v] = há um caminho s-v?
    // ou v foi visitado?
    private boolean[] marked;

    // edgeTo[v] = última aresta no menor caminho s-v
    private int[] edgeTo;

    // distTo[v] = número de arestas no menor caminho s-v
    private int[] distTo;

    // vértice fonte
    private final int source;

    // o grafo
    private final Graph graph;

    /**
     * Computa o menor caminho entre o vértice fonte s e todos os outros
     * vértices do grafo.
     *
     * @param graph o grafo
     * @param source o vértice fonte
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public BreadthFirstSearch( Graph graph, int source ) throws IllegalArgumentException {
        
        validateVertex( source );
        
        marked = new boolean[graph.getNumberOfVertices()];
        distTo = new int[graph.getNumberOfVertices()];
        edgeTo = new int[graph.getNumberOfVertices()];
        
        this.source = source;
        this.graph = graph;
        
        bfs( graph, source );
        
    }

    // implementação da busca em largura para uma fonte
    private void bfs( Graph graph, int source ) {
        
        Queue<Integer> q = new LinkedQueue<>();
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            distTo[v] = INFINITY;
        }
        distTo[source] = 0;
        marked[source] = true;
        q.enqueue( source );

        while ( !q.isEmpty() ) {
            
            int v = q.dequeue();
            
            for ( int w : graph.adj( v ) ) {
                if ( !marked[w] ) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue( w );
                }
            }
            
        }
        
    }

    /**
     * Há um caminho entre o vértice fonte e v?
     *
     * @param v o vértice
     * @return verdadeiro se houver um caminho entre s-v, falso caso contrário
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public boolean hasPathTo( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return marked[v];
    }

    /**
     * Retorna a quantidade de arestas do menor caminho entre o vértice fonte e
     * o vértice passado.
     *
     * @param v o vértice
     * @return a quantidade de aretas no caminho
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public int distTo( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return distTo[v];
    }

    /**
     * Retorna o menor caminho entre o vértice fonte e o vértice passado ou 
     * null caso não exista tal caminho.
     *
     * @param v o vértice
     * @return a sequência de vértices do menor caminho como um iterável
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public Iterable<Integer> pathTo( int v ) {
        
        validateVertex( v );
        
        if ( !hasPathTo( v ) ) {
            return null;
        }
        
        Stack<Integer> path = new ResizingArrayStack<>();
        int current;
        
        for ( current = v; distTo[current] != 0; current = edgeTo[current] ) {
            path.push( current );
        }
        path.push( current );
        
        return path;
        
    }

    private void validateVertex( int v ) throws IllegalArgumentException {
        int V = marked.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append( "Breadth-First Search (source: vertex" ).append( source ).append( ")\n" );
        sb.append( "v\tmarked[v]\tedgeTo[v]\tdistTo[v]\n" );
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            sb.append( String.format( "%d\t%s\t\t%s\t\t%s\n",
                    v,
                    marked[v] ? "T" : "F",
                    edgeTo[v] == -1 ? "-" : edgeTo[v],
                    distTo[v] == -1 ? "-" : distTo[v] ) );
        }

        return sb.toString();

    }

}
