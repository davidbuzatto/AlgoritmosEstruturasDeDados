/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedGraph;
import aesd.esdc4.ds.implementations.nonlinear.pq.IndexedMinPriorityQueue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 * Implementação do algoritmo de menor caminho de Dijksta para grafos
 * ponderados.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DijkstraSP {

    // distTo[v] = distância do menor caminho entre source-v
    private double[] distTo;
    
    // edgeTo[v] = última aresta no menor caminho entre source-v
    private Edge[] edgeTo;
    
    // fila de prioridades indexada dos vértices
    private IndexedMinPriorityQueue<Double> pq;

    /**
     * Computa a árvore de menor caminho a partir do vértice fonte (source) para
     * todos os outros vértices do grafo ponderado.
     *
     * @param graph o grafo ponderado
     * @param source o vértice fonte
     * @throws IllegalArgumentException se o peso de alguma aresta for negativo
     * @throws IllegalArgumentException se o vértice fonte for inválido
     */
    public DijkstraSP( EdgeWeightedGraph graph, int source ) throws IllegalArgumentException {
        
        for ( Edge e : graph.edges() ) {
            if ( e.weight() < 0 ) {
                throw new IllegalArgumentException( "edge " + e + " has negative weight" );
            }
        }

        distTo = new double[graph.getNumberOfVertices()];
        edgeTo = new Edge[graph.getNumberOfVertices()];

        validateVertex( source );

        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0.0;

        // relaxamento dos vértices na ordem de distância ao vértice fonte
        pq = new IndexedMinPriorityQueue<>( graph.getNumberOfVertices() );
        pq.insert( source, distTo[source] );
        
        while ( !pq.isEmpty() ) {
            int v = pq.delete();
            for ( Edge e : graph.adj( v ) ) {
                relax( e, v );
            }
        }
        
    }

    // relaxamento da aresta e atualização da fila de prioridades se foi
    // alterada
    private void relax( Edge e, int v ) {
        
        int w = e.other( v );
        
        if ( distTo[w] > distTo[v] + e.weight() ) {
            
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            
            if ( pq.contains( w ) ) {
                pq.decreaseKey( w, distTo[w] );
            } else {
                pq.insert( w, distTo[w] );
            }
            
        }
        
    }

    /**
     * Retorna o comprimento/tamanho do menor caminho entre o vértice fonte e o
     * vértice de destino.
     *
     * @param v o vértice de destino
     * @return o comprimento/tamanho do menor caminho entre o vértice fonte e o 
     * vértice de destino ou Dougle.POSITIVE_INFINITY se não hover caminho entre
     * eles.
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public double distTo( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return distTo[v];
    }

    /**
     * Retorna verdadeiro se houver um caminho entre o vértice fonte e o vértice
     * de destino.
     *
     * @param v o vértice de destino
     * @return verdadeiro se houver um caminho entre o vértice fonte e o vértice
     * de destino, falso caso contrário
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public boolean hasPathTo( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    /**
     * Retorna o menor caminho entre o vértice fonte e o vértice de destino.
     *
     * @param v o vértice de destino
     * @return o menor caminho entre o vértice fonte e o vértice de destino como
     * um iterável, ou null caso o caminho não exista
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public Iterable<Edge> pathTo( int v ) throws IllegalArgumentException {
        
        validateVertex( v );
        
        if ( !hasPathTo( v ) ) {
            return null;
        }
        
        Stack<Edge> path = new ResizingArrayStack<>();
        int current = v;
        
        for ( Edge e = edgeTo[v]; e != null; e = edgeTo[current] ) {
            path.push( e );
            current = e.other( current );
        }
        
        return path;
        
    }

    private void validateVertex( int v ) {
        int length = distTo.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }

}
