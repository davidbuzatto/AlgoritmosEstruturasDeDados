/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph.edgeweighted;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.AdjMatrixEdgeWeightedDigraph;
import aesd.esdc4.ds.implementations.nonlinear.graph.Edge;
import aesd.esdc4.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FloydWarshall {

    private boolean hasNegativeCycle;  // is there a negative cycle?
    private double[][] distTo;         // distTo[v][w] = length of shortest v->w path
    private Edge[][] edgeTo;   // edgeTo[v][w] = last edge on shortest v->w path

    /**
     * Computes a shortest paths tree from each vertex to to every other vertex
     * in the edge-weighted digraph {@code G}. If no such shortest path exists
     * for some pair of vertices, it computes a negative cycle.
     *
     * @param G the edge-weighted digraph
     */
    public FloydWarshall( AdjMatrixEdgeWeightedDigraph G ) {
        int V = G.getNumberOfVertices();
        distTo = new double[V][V];
        edgeTo = new Edge[V][V];

        // initialize distances to infinity
        for ( int v = 0; v < V; v++ ) {
            for ( int w = 0; w < V; w++ ) {
                distTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }

        // initialize distances using edge-weighted digraph's
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            for ( Edge e : G.adj( v ) ) {
                distTo[e.from()][e.to()] = e.weight();
                edgeTo[e.from()][e.to()] = e;
            }
            // in case of self-loops
            if ( distTo[v][v] >= 0.0 ) {
                distTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        // Floyd-Warshall updates
        for ( int i = 0; i < V; i++ ) {
            // compute shortest paths using only 0, 1, ..., i as intermediate vertices
            for ( int v = 0; v < V; v++ ) {
                if ( edgeTo[v][i] == null ) {
                    continue;  // optimization
                }
                for ( int w = 0; w < V; w++ ) {
                    if ( distTo[v][w] > distTo[v][i] + distTo[i][w] ) {
                        distTo[v][w] = distTo[v][i] + distTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                // check for negative cycle
                if ( distTo[v][v] < 0.0 ) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }
        assert check( G );
    }

    /**
     * Is there a negative cycle?
     *
     * @return {@code true} if there is a negative cycle, and {@code false}
     * otherwise
     */
    public boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    /**
     * Returns a negative cycle, or {@code null} if there is no such cycle.
     *
     * @return a negative cycle as an iterable of edges, or {@code null} if
     * there is no such cycle
     */
    public Iterable<Edge> negativeCycle() {
        for ( int v = 0; v < distTo.length; v++ ) {
            // negative cycle in v's predecessor graph
            if ( distTo[v][v] < 0.0 ) {
                int V = edgeTo.length;
                EdgeWeightedDigraph spt = new EdgeWeightedDigraph( V );
                for ( int w = 0; w < V; w++ ) {
                    if ( edgeTo[v][w] != null ) {
                        spt.addEdge( edgeTo[v][w].from(), edgeTo[v][w].to(), edgeTo[v][w].weight() );
                    }
                }
                EdgeWeightedDirectedCycle finder = new EdgeWeightedDirectedCycle( spt );
                assert finder.hasCycle();
                return finder.cycle();
            }
        }
        return null;
    }

    /**
     * Is there a path from the vertex {@code s} to vertex {@code t}?
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return {@code true} if there is a path from vertex {@code s} to vertex
     * {@code t}, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     * @throws IllegalArgumentException unless {@code 0 <= t < V}
     */
    public boolean hasPath( int s, int t ) {
        validateVertex( s );
        validateVertex( t );
        return distTo[s][t] < Double.POSITIVE_INFINITY;
    }

    /**
     * Returns the length of a shortest path from vertex {@code s} to vertex
     * {@code t}.
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return the length of a shortest path from vertex {@code s} to vertex
     * {@code t}; {@code Double.POSITIVE_INFINITY} if no such path
     * @throws UnsupportedOperationException if there is a negative cost cycle
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public double dist( int s, int t ) {
        validateVertex( s );
        validateVertex( t );
        if ( hasNegativeCycle() ) {
            throw new UnsupportedOperationException( "Negative cost cycle exists" );
        }
        return distTo[s][t];
    }

    /**
     * Returns a shortest path from vertex {@code s} to vertex {@code t}.
     *
     * @param s the source vertex
     * @param t the destination vertex
     * @return a shortest path from vertex {@code s} to vertex {@code t} as an
     * iterable of edges, and {@code null} if no such path
     * @throws UnsupportedOperationException if there is a negative cost cycle
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> path( int s, int t ) {
        validateVertex( s );
        validateVertex( t );
        if ( hasNegativeCycle() ) {
            throw new UnsupportedOperationException( "Negative cost cycle exists" );
        }
        if ( !hasPath( s, t ) ) {
            return null;
        }
        Stack<Edge> path = new ResizingArrayStack<>();
        for ( Edge e = edgeTo[s][t]; e != null; e = edgeTo[s][e.from()] ) {
            path.push( e );
        }
        return path;
    }

    // check optimality conditions
    private boolean check( AdjMatrixEdgeWeightedDigraph G ) {

        // no negative cycle
        if ( !hasNegativeCycle() ) {
            for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
                for ( Edge e : G.adj( v ) ) {
                    int w = e.to();
                    for ( int i = 0; i < G.getNumberOfVertices(); i++ ) {
                        if ( distTo[i][w] > distTo[i][v] + e.weight() ) {
                            System.err.println( "edge " + e + " is eligible" );
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = distTo.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

}
