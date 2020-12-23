/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.graph.edgeweighted.tests;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.nonlinear.pq.MinPriorityQueue;
import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedGraph;
import aesd.esdc4.ds.implementations.nonlinear.uf.UF;
import aesd.esdc4.ds.interfaces.Queue;

/**
 * Cópia a classe KruskalMST com várias escritas no console para traçagem.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class KruskalMSTTrace {

    // peso da MST
    private double weight;
    
    // arestas na MST
    private Queue<Edge> mst;

    /**
     * Comoputa uma árvore geradora mínima (ou floresta) de um grafo ponderado.
     *
     * @param graph o grafo ponderado
     */
    public KruskalMSTTrace( EdgeWeightedGraph graph ) {
        
        mst = new LinkedQueue<>();
        
        // se passar um array de arestas a construção da fila de prioridades
        // se torna mais eficiente
        MinPriorityQueue<Edge> pq = new MinPriorityQueue<>();
        for ( Edge e : graph.edges() ) {
            pq.insert( e );
        }

        // executa o algoritmo guloso
        // union-find ponderado com compressão de caminhos
        UF uf = new UF( graph.getNumberOfVertices() );
        
        while ( !pq.isEmpty() && mst.getSize() < graph.getNumberOfVertices() - 1 ) {
            
            Edge e = pq.delete();
            int v = e.either();
            int w = e.other( v );
            
            // v-w não criam ciclo
            if ( uf.find( v ) != uf.find( w ) ) {
                
                // une os componentes de v e w
                uf.union( v, w );
                
                // insere a aresta na MST
                mst.enqueue( e );
                
                weight += e.weight();
                
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
