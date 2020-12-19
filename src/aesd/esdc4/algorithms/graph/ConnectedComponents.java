/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph;

import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedGraph;
import aesd.esdc4.ds.implementations.nonlinear.graph.Graph;

/**
 * Computa os componentes conexos de um grafo ou de um grafo ponderado.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class ConnectedComponents {

    // marked[v] = o vértice v foi visitado?
    private boolean[] marked;

    // id[v] = identificador do componente conexto que contém v
    private int[] id;

    // size[id] = quantidade de vértices no componente conexo identificado por id
    private int[] size;

    // quantidade de componentes conexos.
    private int count;

    /**
     * Computa os componentes conexos do grafo.
     *
     * @param graph o grafo
     */
    public ConnectedComponents( Graph graph ) {
        
        marked = new boolean[graph.getNumberOfVertices()];
        id = new int[graph.getNumberOfVertices()];
        size = new int[graph.getNumberOfVertices()];
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( graph, v );
                count++;
            }
        }
        
    }

    /**
     * Computa os componentes conexos do grafo ponderado.
     *
     * @param graph o grafo ponderado
     */
    public ConnectedComponents( EdgeWeightedGraph graph ) {
        
        marked = new boolean[graph.getNumberOfVertices()];
        id = new int[graph.getNumberOfVertices()];
        size = new int[graph.getNumberOfVertices()];
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( graph, v );
                count++;
            }
        }
        
    }

    // implementação da busca em profundidade a partir de v para um grafo
    private void dfs( Graph graph, int v ) {
        
        marked[v] = true;
        id[v] = count;
        size[count]++;
        
        for ( int w : graph.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( graph, w );
            }
        }
        
    }

    // implementação da busca em profundidade a partir de v para um grafo
    // ponderado
    private void dfs( EdgeWeightedGraph graph, int v ) {
        
        marked[v] = true;
        id[v] = count;
        size[count]++;
        
        for ( Edge e : graph.adj( v ) ) {
            int w = e.other( v );
            if ( !marked[w] ) {
                dfs( graph, w );
            }
        }
        
    }

    /**
     * Retorna o identificador do componente conexo que contém v.
     *
     * @param v o vértice
     * @return o identificador do componente conexo que contém v
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public int id( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return id[v];
    }

    /**
     * Returna a quantidade de vértices no componente conexo que contém o 
     * vértice v.
     *
     * @param v o vértice
     * @return a quantidade de vértices contidos no componente conexo em que v
     * está contido
     * @throws IllegalArgumentException se o vértice for inválido.
     */
    public int size( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return size[id[v]];
    }

    /**
     * Retorna a quantidade de componentes conexos do grafo.
     *
     * @return o número de componentex conexos
     */
    public int count() {
        return count;
    }

    /**
     * Verifica se há um caminho entre v e w, ou seja, se ambos estão conectados.
     *
     * @param v um vértice
     * @param w outro vértice
     * @return verdadeiro se v estiver conectado a w, falso caso contrário
     * @throws IllegalArgumentException se qualquer um dos vértices for inválido
     */
    public boolean connected( int v, int w ) throws IllegalArgumentException {
        
        validateVertex( v );
        validateVertex( w );
        
        return id( v ) == id( w );
        
    }

    private void validateVertex( int v ) throws IllegalArgumentException {
        int V = marked.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

}
