package aesd.algorithms.digraph.edgeweighted;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.AdjMatrixEdgeWeightedDigraph;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;
import aesd.ds.interfaces.Stack;

/**
 * Calcula a árvore de menores caminhos para cada vértice até qualquer outro
 * vértice em um digrafo ponderado.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FloydWarshall {

    // há um ciclo negativo?
    private boolean hasNegativeCycle;
    
    // distTo[v][w] = distância do menor caminho entre v-w
    private double[][] distTo;
    
    // edgeTo[v][w] = última aresta no menor caminho v->w
    private Edge[][] edgeTo;

    /**
     * Calcula a árvore de menores caminhos para cada vértice até qualquer outro
     * vértice em um digrafo ponderado. Se não houver uma menor caminho para algum
     * par de vértices, computa um ciclo negativo.
     *
     * @param digraph o digrafo ponderado
     */
    public FloydWarshall( AdjMatrixEdgeWeightedDigraph digraph ) {
        
        int V = digraph.getNumberOfVertices();
        
        distTo = new double[V][V];
        edgeTo = new Edge[V][V];

        // inicializa as distâncias para infinito
        for ( int v = 0; v < V; v++ ) {
            for ( int w = 0; w < V; w++ ) {
                distTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }

        // inicializa as distâncias usando o digrafo ponderado
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            for ( Edge e : digraph.adj( v ) ) {
                distTo[e.from()][e.to()] = e.weight();
                edgeTo[e.from()][e.to()] = e;
            }
            // tratando loops
            if ( distTo[v][v] >= 0.0 ) {
                distTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        // atualizações Floyd-Warshall
        for ( int i = 0; i < V; i++ ) {
            
            // comptua os menos caminhos usando somente 0, 1, ..., i como
            // vértices intermediários
            for ( int v = 0; v < V; v++ ) {
                if ( edgeTo[v][i] == null ) {
                    continue;  // otimização
                }
                for ( int w = 0; w < V; w++ ) {
                    if ( distTo[v][w] > distTo[v][i] + distTo[i][w] ) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                // verificação de ciclo negativo
                if ( distTo[v][v] < 0.0 ) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }
        
    }

    /**
     * Há um ciclo negativo?
     *
     * @return verdadeiro se houver um ciclo negativo, falso caso contrário
     */
    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    /**
     * Retorna um ciclo negativo ou nulll caso não haja.
     *
     * @return um ciclo negativo ou null caso não haja.
     */
    public Iterable<Edge> negativeCycle() {
        
        for ( int v = 0; v < distTo.length; v++ ) {
            
            // ciclo negativo no grafo predecessor de v
            if ( distTo[v][v] < 0.0 ) {
                
                int V = edgeTo.length;
                
                EdgeWeightedDigraph spt = new EdgeWeightedDigraph( V );
                
                for ( int w = 0; w < V; w++ ) {
                    if ( edgeTo[v][w] != null ) {
                        spt.addEdge( edgeTo[v][w].from(), edgeTo[v][w].to(), edgeTo[v][w].weight() );
                    }
                }
                
                EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle( spt );
                
                
                return finder.cycle();
                
            }
            
        }
        
        return null;
        
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
        
        return distTo[source][target] < Double.POSITIVE_INFINITY;
        
    }

    /**
     * Retorna o comprimento menor caminho entre o vértice source e o vértice
     * target.
     *
     * @param source o vértice fonte
     * @param target o vértice de destino
     * @return o comprimento do menor caminho entre os vértices source e target
     * ou Double.POSITIVE_INFINITY caso não exista
     * @throws UnsupportedOperationException se houver um ciclo negativo
     * @throws IllegalArgumentException se o vértice source ou o vértice target
     * forem inválidos
     */
    public double dist( int source, int target )
            throws UnsupportedOperationException, IllegalArgumentException {
        
        validateVertex( source );
        validateVertex( target );
        
        if ( hasNegativeCycle() ) {
            throw new UnsupportedOperationException( "Negative cost cycle exists" );
        }
        
        return distTo[source][target];
        
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
    public Iterable<Edge> path( int source, int target )
            throws UnsupportedOperationException, IllegalArgumentException {
        
        validateVertex( source );
        validateVertex( target );
        
        if ( hasNegativeCycle() ) {
            throw new UnsupportedOperationException( "Negative cost cycle exists" );
        }
        
        if ( !hasPath( source, target ) ) {
            return null;
        }
        
        Stack<Edge> path = new ResizingArrayStack<>();
        for ( Edge e = edgeTo[source][target]; e != null; e = edgeTo[source][e.from()] ) {
            path.push( e );
        }
        
        return path;
        
    }

    private void validateVertex( int v ) throws IllegalArgumentException {
        int length = distTo.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }

}
