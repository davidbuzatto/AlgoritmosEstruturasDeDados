/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.algorithms.graph;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Graph;
import aesd.ds.interfaces.Stack;

/**
 * Realiza a busca em profundidade para computar os caminhos entre o vértice
 * fonte e todos os outros vértices do grafo.
 *
 * s é o vértice fonte (source).
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class DepthFirstSearch {

    // marked[v] = há um caminho s-v?
    // ou v foi visitado?
    private boolean[] marked;

    // edgeTo[v] = última aresta no caminho s-v
    private int[] edgeTo;

    // vértice fonte
    private final int source;

    // o grafo
    private final Graph graph;

    /**
     * Computa o caminho entre o vértice fonte s e todos os outros vértices do
     * grafo.
     *
     * @param graph o grafo
     * @param source o vértice fonte
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public DepthFirstSearch( Graph graph, int source )  throws IllegalArgumentException {
        
        validateVertex( source );
        
        this.source = source;
        this.graph = graph;
        
        edgeTo = new int[graph.getNumberOfVertices()];
        marked = new boolean[graph.getNumberOfVertices()];
        
        dfs( graph, source );
        
    }

    // implementação da busca em profundidade a partir de v
    private void dfs( Graph graph, int v ) {
        
        marked[v] = true;
        
        for ( int w : graph.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( graph, w );
                edgeTo[w] = v;
            }
        }
        
    }

    /**
     * Há um caminho entre o vértice fonte e v?
     *
     * @param v o vértice
     * @return verdadeiro se houver um caminho, falso caso contrário
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public boolean hasPathTo( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return marked[v];
    }

    /**
     * Retorna o caminho entre o vértice fonte e o vértice passado ou null caso
     * não exista tal caminho.
     *
     * @param v o vértice
     * @return a sequência de vértices do caminho como um iterável
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public Iterable<Integer> pathTo( int v ) throws IllegalArgumentException {
        
        validateVertex( v );
        
        if ( !hasPathTo( v ) ) {
            return null;
        }
        
        Stack<Integer> path = new ResizingArrayStack<>();
        
        for ( int current = v; current != source; current = edgeTo[current] ) {
            path.push( current );
        }
        path.push( source );
        
        return path;
        
        
    }

    private void validateVertex( int v ) throws IllegalArgumentException {
        int length = marked.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append( "Depth-First Search (source: vertex " ).append( source ).append( ")\n" );
        sb.append( "v\tmarked[v]\tedgeTo[v]\n" );
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            sb.append( String.format( "%d\t%s\t\t%s\n",
                    v,
                    marked[v] ? "T" : "F",
                    edgeTo[v] == -1 ? "-" : edgeTo[v] ) );
        }

        return sb.toString();

    }

}
