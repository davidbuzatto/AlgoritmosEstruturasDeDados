/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Digraph;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DirectedBreadthFirstSearch {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s->v path?
    private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
    private int[] distTo;      // distTo[v] = length of shortest s->v path

    /**
     * Computes the shortest path from {@code s} and every other vertex in graph
     * {@code G}.
     *
     * @param G the digraph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public DirectedBreadthFirstSearch( Digraph G, int s ) {
        marked = new boolean[G.getNumberOfVertices()];
        distTo = new int[G.getNumberOfVertices()];
        edgeTo = new int[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            distTo[v] = INFINITY;
        }
        validateVertex( s );
        bfs( G, s );
    }

    /**
     * Computes the shortest path from any one of the source vertices in
     * {@code sources} to every other vertex in graph {@code G}.
     *
     * @param G the digraph
     * @param sources the source vertices
     * @throws IllegalArgumentException if {@code sources} is {@code null}
     * @throws IllegalArgumentException unless each vertex {@code v} in
     * {@code sources} satisfies {@code 0 <= v < V}
     */
    public DirectedBreadthFirstSearch( Digraph G, Iterable<Integer> sources ) {
        marked = new boolean[G.getNumberOfVertices()];
        distTo = new int[G.getNumberOfVertices()];
        edgeTo = new int[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            distTo[v] = INFINITY;
        }
        validateVertices( sources );
        bfs( G, sources );
    }

    // BFS from single source
    private void bfs( Digraph G, int s ) {
        Queue<Integer> q = new LinkedQueue<>();
        marked[s] = true;
        distTo[s] = 0;
        q.enqueue( s );
        while ( !q.isEmpty() ) {
            int v = q.dequeue();
            for ( int w : G.adj( v ) ) {
                if ( !marked[w] ) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue( w );
                }
            }
        }
    }

    // BFS from multiple sources
    private void bfs( Digraph G, Iterable<Integer> sources ) {
        Queue<Integer> q = new LinkedQueue<>();
        for ( int s : sources ) {
            marked[s] = true;
            distTo[s] = 0;
            q.enqueue( s );
        }
        while ( !q.isEmpty() ) {
            int v = q.dequeue();
            for ( int w : G.adj( v ) ) {
                if ( !marked[w] ) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue( w );
                }
            }
        }
    }

    /**
     * Is there a directed path from the source {@code s} (or sources) to vertex
     * {@code v}?
     *
     * @param v the vertex
     * @return {@code true} if there is a directed path, {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo( int v ) {
        validateVertex( v );
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path from the source {@code s}
     * (or sources) to vertex {@code v}?
     *
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo( int v ) {
        validateVertex( v );
        return distTo[v];
    }

    /**
     * Returns a shortest path from {@code s} (or sources) to {@code v}, or
     * {@code null} if no such path.
     *
     * @param v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo( int v ) {
        validateVertex( v );

        if ( !hasPathTo( v ) ) {
            return null;
        }
        Stack<Integer> path = new ResizingArrayStack<>();
        int x;
        for ( x = v; distTo[x] != 0; x = edgeTo[x] ) {
            path.push( x );
        }
        path.push( x );
        return path;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = marked.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertices( Iterable<Integer> vertices ) {
        if ( vertices == null ) {
            throw new IllegalArgumentException( "argument is null" );
        }
        for ( Integer v : vertices ) {
            if ( v == null ) {
                throw new IllegalArgumentException( "vertex is null" );
            }
            validateVertex( v );
        }
    }

}
