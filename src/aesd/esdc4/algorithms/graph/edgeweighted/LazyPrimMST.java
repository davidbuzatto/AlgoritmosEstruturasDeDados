/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.nonlinear.pq.MinPriorityQueue;
import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedGraph;
import aesd.esdc4.ds.interfaces.Queue;

/**
 * Implementação do algoritmo de Prim (lazy) para computação de árvore geradora
 * mínima -Minimum Spanning Tree (MST)- em grafos ponderados.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LazyPrimMST {

    // peso total da MST
    private double weight;
    
    // arestas na MST
    private Queue<Edge> mst;
    
    // marked[v] = true se e somente se v está na árvore
    private boolean[] marked;
    
    // arestas com um vértice na árvore
    private MinPriorityQueue<Edge> pq;

    /**
     * Comoputa uma árvore geradora mínima (ou floresta) de um grafo ponderado.
     *
     * @param graph o grafo ponderado
     */
    public LazyPrimMST( EdgeWeightedGraph graph ) {
        
        mst = new LinkedQueue<>();
        pq = new MinPriorityQueue<>();
        marked = new boolean[graph.getNumberOfVertices()];
        
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
        
        scan( graph, source );
        
        // melhor parar quando a MST tiver a quantidade de arestas igual à
        // quantidade de vértices - 1
        while ( !pq.isEmpty() ) {
            
            // menor aresta na fila de prioridades
            Edge e = pq.delete();
            
            // obtém os dois vértices da aresta
            int v = e.either();
            int w = e.other( v );
            
            if ( marked[v] && marked[w] ) {
                continue;      // forma preguiçosa (lazy), tanto v quanto w
                               // já foram escaneados
            }
            
            // insere a aresta na MST
            mst.enqueue( e );
            weight += e.weight();
            
            // v se torna parte da árvore
            if ( !marked[v] ) {
                scan( graph, v );
            }
            
            // w se torna parte da árvore
            if ( !marked[w] ) {
                scan( graph, w );
            }
            
        }
        
    }

    // todos os vértices que incidem em v são inseridos na fila de prioridades
    // se o outro vértice da aresta ainda não foi escaneado
    private void scan( EdgeWeightedGraph graph, int v ) {
        
        marked[v] = true;
        
        for ( Edge e : graph.adj( v ) ) {
            if ( !marked[e.other( v )] ) {
                pq.insert( e );
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
        return weight;
    }
    
}
