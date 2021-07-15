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
 * Determina se um grafo é bipartido ou se possui um ciclo ímpar. Essa
 * implementação usa busca em profundidade.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class BipartiteDFS {

    // o grafo é bipartido?
    private boolean isBipartite;

    // color[v] distingue os vértices de cada partição
    private boolean[] color;

    // marked[v] = true se e semente se v foi visitado na DFS
    private boolean[] marked;

    // edgeTo[v] = última aresta no caminho até v
    private int[] edgeTo;

    // ciclo ímpar
    private Stack<Integer> cycle;

    /**
     * Determina se um grafo é bipartido e encontra a bipartição ou o ciclo
     * ímpar.
     *
     * @param graph o grafo
     */
    public BipartiteDFS( Graph graph ) {
        
        isBipartite = true;
        color = new boolean[graph.getNumberOfVertices()];
        marked = new boolean[graph.getNumberOfVertices()];
        edgeTo = new int[graph.getNumberOfVertices()];

        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( graph, v );
            }
        }
        
    }

    private void dfs( Graph graph, int v ) {
        
        marked[v] = true;
        
        for ( int w : graph.adj( v ) ) {

            // se encontrou um ciclo ímpar
            if ( cycle != null ) {
                return;
            }

            // encontrou um vértice sem cor
            if ( !marked[w] ) {
                
                edgeTo[w] = v;
                color[w] = !color[v];
                dfs( graph, w );
                
                // se v e w tem a mesma cor, cria um ciclo ímpar e o encontra
            } else if ( color[w] == color[v] ) {
                
                isBipartite = false;
                cycle = new ResizingArrayStack<>();
                
                // não é necessário a não ser que se deseje inserir o vértice
                // inicial duas vezes para ter o ciclo dentro da pilha
                cycle.push( w );  
                
                for ( int x = v; x != w; x = edgeTo[x] ) {
                    cycle.push( x );
                }
                cycle.push( w );
                
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
            throw new UnsupportedOperationException( "graph is not bipartite" );
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
