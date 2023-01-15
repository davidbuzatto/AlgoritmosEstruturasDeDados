package aesd.algorithms.graph.edgeweighted;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedGraph;
import aesd.ds.implementations.nonlinear.uf.UF;
import aesd.ds.implementations.nonlinear.uf.WeightedQuickUnionPathCompressionUF;
import aesd.ds.interfaces.List;

/**
 * Implementação do algoritmo de Boruvka para computação de árvore geradora
 * mínima -Minimum Spanning Tree (MST)- em grafos ponderados.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BoruvkaMST {

    // peso da MST
    private double weight;
    
    // arestas da MST
    private List<Edge> mst;

    /**
     * Comoputa uma árvore geradora mínima (ou floresta) de um grafo ponderado.
     *
     * @param graph o grafo ponderado
     */
    public BoruvkaMST( EdgeWeightedGraph graph ) {
        
        mst = new ResizingArrayList<>();
        UF uf = new WeightedQuickUnionPathCompressionUF( graph.getNumberOfVertices() );

        // para V = quantidade de vértices
        // repete no máximo lg V vezes o até possuir V-1 arestas
        for ( int t = 1; t < graph.getNumberOfVertices() && mst.getSize()< graph.getNumberOfVertices() - 1; t = t + t ) {

            // para cada árvore na floresta, busca pela aresta mais próxima
            // se o os pesos das arestas são iguais, desempata escolhendo a primeira aresta
            // encontrada no grafo (graph.edges()).
            Edge[] closest = new Edge[graph.getNumberOfVertices()];
            
            for ( Edge e : graph.edges() ) {
                
                int v = e.either();
                int w = e.other( v );
                int i = uf.find( v );
                int j = uf.find( w );
                
                if ( i == j ) {
                    continue;   // mesma árvore
                }
                
                if ( closest[i] == null || less( e, closest[i] ) ) {
                    closest[i] = e;
                }
                
                if ( closest[j] == null || less( e, closest[j] ) ) {
                    closest[j] = e;
                }
                
            }

            // aidicona as novas arestas encontradas à MST
            for ( int i = 0; i < graph.getNumberOfVertices(); i++ ) {
                
                Edge e = closest[i];
                
                if ( e != null ) {
                    
                    int v = e.either(), w = e.other( v );
                    
                    // previne adicioanr a mesma aresta duas vezes
                    if ( uf.find( v ) != uf.find( w ) ) {
                        mst.add( e );
                        weight += e.weight();
                        uf.union( v, w );
                    }
                    
                }
                
            }
            
        }
        
    }

    // o peso da aresta é estritamente menor que o da aresta f?
    private static boolean less( Edge e, Edge f ) {
        return e.compareTo( f ) < 0;
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
