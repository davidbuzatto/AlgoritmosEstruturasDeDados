/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Digraph;
import aesd.esdc4.ds.interfaces.Stack;

/**
 * Realiza a busca em profundidade para computar os caminhos entre o vértice
 * fonte e todos os outros vértices do digrafo.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DirectedDepthFirstSearch {

    // marked[v] = há um caminho s-v?
    // ou v foi visitado?
    private boolean[] marked;

    // edgeTo[v] = última aresta no caminho s-v
    private int[] edgeTo;

    // vértice fonte
    private final int source;

    // o digrafo
    private final Digraph digraph;

    /**
     * Computa o caminho entre o vértice fonte s e todos os outros vértices do
     * digrafo.
     *
     * @param digraph o grafo
     * @param source o vértice fonte
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public DirectedDepthFirstSearch( Digraph digraph, int source ) throws IllegalArgumentException {
        
        validateVertex( source );
        
        this.source = source;
        this.digraph = digraph;
        
        marked = new boolean[digraph.getNumberOfVertices()];
        edgeTo = new int[digraph.getNumberOfVertices()];
        
        dfs( digraph, source );
        
    }

    // implementação da busca em profundidade a partir de v
    private void dfs( Digraph digraph, int v ) {
        
        marked[v] = true;
        
        for ( int w : digraph.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( digraph, w );
                edgeTo[w] = v;
            }
        }
        
    }

    /**
     * Há um caminho direcionado entre o vértice fonte e v?
     *
     * @param v o vértice
     * @return verdadeiro se houver um caminho direcionado, falso caso contrário
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public boolean hasPathTo( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return marked[v];
    }

    /**
     * Retorna o caminho direcionado entre o vértice fonte e o vértice passado
     * ou null caso não exista tal caminho.
     *
     * @param v o vértice
     * @return a sequência de vértices do caminho direcionado como um iterável
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public Iterable<Integer> pathTo( int v ) throws IllegalArgumentException {
        
        validateVertex( v );
        
        if ( !hasPathTo( v ) ) {
            return null;
        }
        
        Stack<Integer> path = new ResizingArrayStack<>();
        
        for ( int x = v; x != source; x = edgeTo[x] ) {
            path.push( x );
        }
        path.push( source );
        
        return path;
        
    }

    private void validateVertex( int v ) {
        int length = marked.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }
    
    public boolean isMarked( int w ) {
        return marked[w];
    }
    
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append( "Depth-First Search (source: vertex " ).append( source ).append( ")\n" );
        sb.append( "v\tmarked[v]\tedgeTo[v]\n" );
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            sb.append( String.format( "%d\t%s\t\t%s\n",
                    v,
                    marked[v] ? "T" : "F",
                    edgeTo[v] == -1 ? "-" : edgeTo[v] ) );
        }

        return sb.toString();

    }

}
