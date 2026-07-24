package aesd.algorithms.digraph;

import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Computa o fecho transitivo de um digrafo.
 *
 * O fecho transitivo responde, para qualquer par (v, w), se existe algum
 * caminho direcionado de v até w. Essa implementação usa a abordagem mais
 * direta: executa uma DFS completa a partir de cada um dos V vértices e
 * guarda o resultado (quais vértices cada DFS alcançou), respondendo cada
 * consulta reachable(v, w) em tempo O(1). O custo fica em O(V(V + E)) de
 * pré-processamento e espaço O(V^2) — vantajoso quando muitas consultas
 * serão feitas sobre o mesmo digrafo fixo.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class TransitiveClosure {

    // tc[v] = alcançável a partir de v
    private DirectedDepthFirstSearch[] tc;

    /**
     * Computa o fecho transitivo de um digrafo.
     *
     * @param digraph o digrafo
     */
    public TransitiveClosure( Digraph digraph ) {
        
        tc = new DirectedDepthFirstSearch[digraph.getNumberOfVertices()];
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            tc[v] = new DirectedDepthFirstSearch( digraph, v );
        }
        
    }

    /**
     * Há um caminho direcionado do vértice v ao vértice w no digrafo?
     *
     * @param v o vértice fonte
     * @param w o vértice de destino
     * @return verdadeiro se houver um caminho direcionado entre v e w, falso
     * caso contrário
     * @throws IllegalArgumentException se o vértice v ou o vértice w forem
     * inválidos
     */
    public boolean reachable( int v, int w ) throws IllegalArgumentException {
        validateVertex( v );
        validateVertex( w );
        return tc[v].isMarked( w );
    }

    private void validateVertex( int v ) {
        int length = tc.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }

}
