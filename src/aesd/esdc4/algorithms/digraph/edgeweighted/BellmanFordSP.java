/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 * Computa a árvore de menor caminho a partir do vértice fonte (source) para
 * todos os outros vértices do digrafo ponderado.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BellmanFordSP {

    // para resolver problemas com precisão de ponto flutuante
    private static final double EPSILON = 1E-14;

    // distTo[v] = distância do menor caminho s->v
    private double[] distTo;
    
    // edgeTo[v] = última aresta no menor caminho s->v
    private Edge[] edgeTo;
    
    // onQueue[v] = v está na fila?
    private boolean[] onQueue;
    
    // filad e vértices à relaxar
    private Queue<Integer> queue;
    
    // quantidade de chamadas ao método relax()
    private int cost;
    
    // ciclo negativo ou null caso não exista
    private Iterable<Edge> cycle;

    /**
     * Computa a árvore de menor caminho a partir do vértice fonte (source) para
     * todos os outros vértices do digrafo ponderado.
     *
     * @param digraph o digrafo ponderado acíclico
     * @param source o vértice fonte
     * @throws IllegalArgumentException se o peso de alguma aresta for negativo
     * @throws IllegalArgumentException se o vértice fonte for inválido
     */
    public BellmanFordSP( EdgeWeightedDigraph digraph, int source ) {
        
        distTo = new double[digraph.getNumberOfVertices()];
        edgeTo = new Edge[digraph.getNumberOfVertices()];
        onQueue = new boolean[digraph.getNumberOfVertices()];
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[source] = 0.0;

        // algoritmo de Bellman-Ford
        queue = new LinkedQueue<>();
        queue.enqueue( source );
        onQueue[source] = true;
        
        while ( !queue.isEmpty() && !hasNegativeCycle() ) {
            int v = queue.dequeue();
            onQueue[v] = false;
            relax( digraph, v );
        }
        
    }

    // relaxamento do vértice v e inserção de outros pontos de fim (endpoints)
    // na fila se algo mudar
    private void relax( EdgeWeightedDigraph digraph ,int v ) {
        
        for ( Edge e : digraph.adj( v ) ) {
            
            int w = e.to();
            
            if ( distTo[w] > distTo[v] + e.weight() + EPSILON ) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if ( !onQueue[w] ) {
                    queue.enqueue( w );
                    onQueue[w] = true;
                }
            }
            
            if ( ++cost % digraph.getNumberOfVertices() == 0 ) {
                findNegativeCycle();
                if ( hasNegativeCycle() ) {
                    return;  // encontrou um ciclo negativo
                }
            }
            
        }
        
    }

    /**
     * Há um ciclo negativo alcançável a partir do vértice fonte source?
     *
     * @return verdadeiro se hovuer um ciclo negativo alcançável a partir do 
     * vértice fonte, falso caso contrário
     */
    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    /**
     * Retorna o ciclo negativo alcançável a partir do vértice fonte ou null
     * caso não exista.
     *
     * @return o ciclo negativo alcançável a partir do vértice fonte como um
     * iterável ou null caso não exista.
     */
    public Iterable<Edge> negativeCycle() {
        return cycle;
    }

    // encontra um ciclo no grafo predecessor
    private void findNegativeCycle() {
        
        int V = edgeTo.length;
        
        EdgeWeightedDigraph spt = new EdgeWeightedDigraph( V );
        
        for ( int v = 0; v < V; v++ ) {
            if ( edgeTo[v] != null ) {
                spt.addEdge( edgeTo[v].from(), edgeTo[v].to(), edgeTo[v].weight() );
            }
        }

        EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle( spt );
        cycle = finder.cycle();
        
    }

    /**
     * Retorna o comprimento do menor caminho do vértice fonte ao vértice v.
     *
     * @param v o vértice de destino
     * @return o comprimento do menor caminho do vértice fonte ao vértice v ou
     * Double.POSITIVE_INFINITY} caso não exista
     * @throws UnsupportedOperationException se houver um ciclo negativo
     * alcançável a partir do vértice fonte.
     * @throws IllegalArgumentException caso o vértice seja inválido
     */
    public double distTo( int v ) throws IllegalArgumentException {
        
        validateVertex( v );
        
        if ( hasNegativeCycle() ) {
            throw new UnsupportedOperationException( "Negative cost cycle exists" );
        }
        
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
     * @throws UnsupportedOperationException se houver um ciclo de custo negativo
     * alcançável a partir do vértice fonte
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public Iterable<Edge> pathTo( int v ) throws IllegalArgumentException {
        
        validateVertex( v );
        
        if ( hasNegativeCycle() ) {
            throw new UnsupportedOperationException( "Negative cost cycle exists" );
        }
        
        if ( !hasPathTo( v ) ) {
            return null;
        }
        
        Stack<Edge> path = new ResizingArrayStack<Edge>();
        
        for ( Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()] ) {
            path.push( e );
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
