package aesd.algorithms.graph.edgeweighted;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.ds.implementations.nonlinear.pq.MinPriorityQueue;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedGraph;
import aesd.ds.implementations.nonlinear.uf.UF;
import aesd.ds.implementations.nonlinear.uf.WeightedQuickUnionPathCompressionUF;
import aesd.ds.interfaces.Queue;

/**
 * Implementação do algoritmo de Kruskal para computação de árvore geradora
 * mínima -Minimum Spanning Tree (MST)- em grafos ponderados.
 *
 * Diferente da abordagem de Prim (que cresce uma única árvore vértice a
 * vértice), Kruskal processa as arestas globalmente em ordem crescente de
 * peso e aceita cada uma que não forma ciclo com as já aceitas — union-find
 * é o que permite testar "v e w já estão no mesmo componente?" em tempo
 * quase constante. Também respeita a propriedade do corte, mas construindo
 * a MST como uma floresta que vai se fundindo, em vez de expandir a partir
 * de uma única raiz. Complexidade O(E log E), dominada pela ordenação
 * (aqui, pela fila de prioridades).
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class KruskalMST {

    // peso da MST
    private double weight;
    
    // arestas na MST
    private Queue<Edge> mst;

    /**
     * Computa uma árvore geradora mínima (ou floresta) de um grafo ponderado.
     *
     * @param graph o grafo ponderado
     */
    public KruskalMST( EdgeWeightedGraph graph ) {
        
        mst = new LinkedQueue<>();
        
        // se passar um array de arestas a construção da fila de prioridades
        // se torna mais eficiente
        MinPriorityQueue<Edge> pq = new MinPriorityQueue<>();
        for ( Edge e : graph.edges() ) {
            pq.insert( e );
        }

        // executa o algoritmo guloso
        // union-find ponderado com compressão de caminhos
        UF uf = new WeightedQuickUnionPathCompressionUF( graph.getNumberOfVertices() );
        
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
     * Retorna as arestas da árvore/floresta geradora mínima.
     *
     * @return as arestas da árvore/floresta geradora mínima como um
     * iterável
     */
    public Iterable<Edge> edges() {
        return mst;
    }

    /**
     * Retorna a soma dos pesos de todas as arestas da árvore/floresta geradora
     * mínima.
     *
     * @return a soma dos pesos de todas as arestas da árvore/floresta geradora
     * mínima.
     */
    public double weight() {
        return weight;
    }

}
