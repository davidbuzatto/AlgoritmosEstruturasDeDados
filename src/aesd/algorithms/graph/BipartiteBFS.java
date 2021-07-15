/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.algorithms.graph;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Graph;
import aesd.ds.interfaces.Queue;
import aesd.ds.interfaces.Stack;

/**
 * Determina se um grafo é bipartido ou se possui um ciclo ímpar. Essa
 * implementação usa busca em largura.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class BipartiteBFS {

    private static final boolean WHITE = false;
    private static final boolean BLACK = true;

    // o grafo é bipartido?
    private boolean isBipartite;

    // color[v] distingue os vértices de cada partição
    private boolean[] color;

    // marked[v] = true se e semente se v foi visitado na BFS
    private boolean[] marked;

    // edgeTo[v] = última aresta no caminho até v
    private int[] edgeTo;

    // ciclo ímpar
    private Queue<Integer> cycle;

    /**
     * Determina se um grafo é bipartido e encontra a bipartição ou o ciclo
     * ímpar.
     *
     * @param graph o grafo
     */
    public BipartiteBFS( Graph graph ) {
        
        isBipartite = true;
        color = new boolean[graph.getNumberOfVertices()];
        marked = new boolean[graph.getNumberOfVertices()];
        edgeTo = new int[graph.getNumberOfVertices()];

        for ( int v = 0; v < graph.getNumberOfVertices() && isBipartite; v++ ) {
            if ( !marked[v] ) {
                bfs( graph, v );
            }
        }
        
    }

    private void bfs( Graph graph, int s ) {
        
        Queue<Integer> q = new LinkedQueue<>();
        color[s] = WHITE;
        marked[s] = true;
        q.enqueue( s );

        while ( !q.isEmpty() ) {
            
            int v = q.dequeue();
            
            for ( int w : graph.adj( v ) ) {
                
                // encontrou um vértice sem cor
                if ( !marked[w] ) {
                    
                    marked[w] = true;
                    edgeTo[w] = v;
                    color[w] = !color[v];
                    q.enqueue( w );
                    
                } else if ( color[w] == color[v] ) {
                    
                    isBipartite = false;

                    /*
                     * para formar um ciclo ímpar, considere os caminhos
                     * s-v e s-w and faça com que x seja o vértice mais próximo
                     * a v e w e que seja comum aos dois caminhos. sendo assim,
                     * (caminho w-x) + (caminho x-v) + (aresta v-w) é um ciclo
                     * ímpar.
                     *
                     * Obs: distTo[v] == distTo[w];
                     */
                    cycle = new LinkedQueue<>();
                    Stack<Integer> stack = new ResizingArrayStack<>();
                    
                    int x = v;
                    int y = w;
                    
                    while ( x != y ) {
                        stack.push( x );
                        cycle.enqueue( y );
                        x = edgeTo[x];
                        y = edgeTo[y];
                    }
                    
                    stack.push( x );
                    
                    while ( !stack.isEmpty() ) {
                        cycle.enqueue( stack.pop() );
                    }
                    cycle.enqueue( w );
                    
                    return;
                    
                }
                
            }
            
        }
        
    }

    /**
     * Retorna verdadeiro se o grafo é bipartido.
     *
     * @return verdadeiro se for bipartido, falso caso contrário
     */
    public boolean isBipartite() {
        return isBipartite;
    }

    /**
     * Retorna o lado/cor da bipartição em que o vértice v está contido.
     *
     * @param v o vértice.
     * @return o lado da bipartição que o vértice v está contido. dois vértices
     * estão no mesmo lado da bipartição se e somente se eles têm a mesma cor.
     * @throws IllegalArgumentException se o vértice for inválido
     * @throws UnsupportedOperationException caso esse método seja invocado em
     * um grafo não bipartido.
     */
    public boolean color( int v ) throws IllegalArgumentException, UnsupportedOperationException {
        
        validateVertex( v );
        
        if ( !isBipartite ) {
            throw new UnsupportedOperationException( "Graph is not bipartite" );
        }
        
        return color[v];
        
    }

    /**
     * Retorna o ciclo ímpar caso o grafo não sej bipartido ou null caso
     * contrário.
     *
     * @return o ciclo ímpar caso o grafo não seja bipartido, null caso
     * contrário.
     */
    public Iterable<Integer> oddCycle() {
        return cycle;
    }

    private void validateVertex( int v ) throws IllegalArgumentException {
        int length = marked.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }

}
