package aesd.algorithms.digraph.edgeweighted;

import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;

/**
 * Calcula a árvore de menores caminhos para cada vértice até qualquer outro
 * vértice em um digrafo ponderado.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class DirectedDijkstraAllPairsSP {

    private DirectedDijkstraSP[] all;

    /**
     * Calcula a árvore de menores caminhos para cada vértice até qualquer outro
     * vértice em um digrafo ponderado.
     *
     * @param digraph o digrafo ponderado
     * @throws IllegalArgumentException se o peso de alguma aresta for negativo
     * @throws IllegalArgumentException se o vértice fonte for inválido
     */
    public DirectedDijkstraAllPairsSP( EdgeWeightedDigraph digraph )
            throws IllegalArgumentException {
        
        all = new DirectedDijkstraSP[digraph.getNumberOfVertices()];
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            all[v] = new DirectedDijkstraSP( digraph, v );
        }
        
    }

    /**
     * Retorna o menor caminho entre o vértice source e o vértice
     * target.
     *
     * @param source o vértice fonte
     * @param target o vértice de destino
     * @return o menor caminho entre os vértices source e target ou null caso
     * não exista
     * @throws UnsupportedOperationException se houver um ciclo negativo
     * @throws IllegalArgumentException se o vértice source ou o vértice target
     * forem inválidos
     */
    public Iterable<Edge> path( int source, int target ) {
        
        validateVertex( source );
        validateVertex( target );
        
        return all[source].pathTo( target );
        
    }

    /**
     * Há um caminho de source até target?
     *
     * @param source o vértice fonte
     * @param target o vértice de destino
     * @return verdadeiro caso haja um caminho entre os vértices source e target
     * falso caso contrário
     * @throws IllegalArgumentException se o vértice source ou o vértice target
     * forem inválidos
     */
    public boolean hasPath( int source, int target ) throws IllegalArgumentException {
        
        validateVertex( source );
        validateVertex( target );
        
        return dist( source, target ) < Double.POSITIVE_INFINITY;
        
    }

    /**
     * Retorna o comprimento menor caminho entre o vértice source e o vértice
     * target.
     *
     * @param source o vértice fonte
     * @param target o vértice de destino
     * @return o comprimento do menor caminho entre os vértices source e target
     * ou Double.POSITIVE_INFINITY caso não exista
     * @throws IllegalArgumentException se o vértice source ou o vértice target
     * forem inválidos
     */
    public double dist( int source, int target ) {
        
        validateVertex( source );
        validateVertex( target );
        
        return all[source].distTo( target );
        
    }

    private void validateVertex( int v ) {
        int length = all.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }
    
}
