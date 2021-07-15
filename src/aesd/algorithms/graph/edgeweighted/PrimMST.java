/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.algorithms.graph.edgeweighted;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedGraph;
import aesd.ds.implementations.nonlinear.pq.IndexedMinPriorityQueue;
import aesd.ds.interfaces.Queue;

/**
 * Implementação do algoritmo de Prim para computação de árvore geradora
 * mínima -Minimum Spanning Tree (MST)- em grafos ponderados.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class PrimMST {

    // edgeTo[v] = menor aresta do vértice da árvore para o vértice que não está
    // na árvore
    private Edge[] edgeTo;
    
    // distTo[v] = peso da menor aresta
    private double[] distTo;
    
    // marked[v] = true se v está na árvore, falso caso contrário
    private boolean[] marked;
    
    // fila de de prioridades indexada
    private IndexedMinPriorityQueue<Double> pq;

    /**
     * Comoputa uma árvore geradora mínima (ou floresta) de um grafo ponderado.
     *
     * @param graph o grafo ponderado
     */
    public PrimMST( EdgeWeightedGraph graph ) {
        
        edgeTo = new Edge[graph.getNumberOfVertices()];
        distTo = new double[graph.getNumberOfVertices()];
        marked = new boolean[graph.getNumberOfVertices()];
        pq = new IndexedMinPriorityQueue<>( graph.getNumberOfVertices() );
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }

        // executa o algoritmo de Prim em todos os vértices para que se
        // obténha uma floresta geradora mínima (minimum spanning forest)
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                prim( graph, v );
            }
        }
        
    }

    // implementação do algoritmo de Prim iniciando no vértice source
    private void prim( EdgeWeightedGraph graph, int source ) {
        
        distTo[source] = 0.0;
        pq.insert( source, distTo[source] );
        
        while ( !pq.isEmpty() ) {
            int v = pq.delete();
            scan( graph, v );
        }
        
    }

    // escaneia o vértice v
    private void scan( EdgeWeightedGraph graph, int v ) {
        
        marked[v] = true;
        
        for ( Edge e : graph.adj( v ) ) {
            
            int w = e.other( v );
            if ( marked[w] ) {
                continue;         // v-w é uma aresta obsoleta
            }
            
            if ( e.weight() < distTo[w] ) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if ( pq.contains( w ) ) {
                    pq.decreaseKey( w, distTo[w] );
                } else {
                    pq.insert( w, distTo[w] );
                }
            }
            
        }
        
    }

    /**
     * Retorna as aretas da árvore/floresta geradora mínima.
     *
     * @return as arestas da árvore/floresta geradora mínima como um
     * iterável
     */
    public Iterable<Edge> edges() {
        
        Queue<Edge> mst = new LinkedQueue<>();
        
        for ( int v = 0; v < edgeTo.length; v++ ) {
            Edge e = edgeTo[v];
            if ( e != null ) {
                mst.enqueue( e );
            }
        }
        
        return mst;
        
    }

    /**
     * Retorna a soma dos pesos de todas as areas da árvore/floresta geradora
     * mínima.
     *
     * @return a soma dos pesos de todas as areas da árvore/floresta geradora
     * mínima.
     */
    public double weight() {
        
        double weight = 0.0;
        
        for ( Edge e : edges() ) {
            weight += e.weight();
        }
        
        return weight;
        
    }

}
