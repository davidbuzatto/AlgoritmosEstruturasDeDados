/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Digraph;
import aesd.esdc4.ds.implementations.working.DirectedEdge;
import aesd.esdc4.ds.implementations.working.EdgeWeightedDigraph;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DepthFirstOrder {

    private boolean[] marked;          // marked[v] = has v been marked in dfs?
    private int[] pre;                 // pre[v]    = preorder  number of v
    private int[] post;                // post[v]   = postorder number of v
    private Queue<Integer> preorder;   // vertices in preorder
    private Queue<Integer> postorder;  // vertices in postorder
    private int preCounter;            // counter or preorder numbering
    private int postCounter;           // counter for postorder numbering

    /**
     * Determines a depth-first order for the digraph {@code G}.
     *
     * @param G the digraph
     */
    public DepthFirstOrder( Digraph G ) {
        pre = new int[G.getNumberOfVertices()];
        post = new int[G.getNumberOfVertices()];
        postorder = new LinkedQueue<>();
        preorder = new LinkedQueue<>();
        marked = new boolean[G.getNumberOfVertices()];
        for ( int v = 0; v < G.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( G, v );
            }
        }

    }

    /**
     * Determines a depth-first order for the edge-weighted digraph {@code G}.
     *
     * @param G the edge-weighted digraph
     */
    public DepthFirstOrder( EdgeWeightedDigraph G ) {
        pre = new int[G.V()];
        post = new int[G.V()];
        postorder = new LinkedQueue<>();
        preorder = new LinkedQueue<>();
        marked = new boolean[G.V()];
        for ( int v = 0; v < G.V(); v++ ) {
            if ( !marked[v] ) {
                dfs( G, v );
            }
        }
    }

    // run DFS in digraph G from vertex v and compute preorder/postorder
    private void dfs( Digraph G, int v ) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue( v );
        for ( int w : G.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( G, w );
            }
        }
        postorder.enqueue( v );
        post[v] = postCounter++;
    }

    // run DFS in edge-weighted digraph G from vertex v and compute preorder/postorder
    private void dfs( EdgeWeightedDigraph G, int v ) {
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue( v );
        for ( DirectedEdge e : G.adj( v ) ) {
            int w = e.to();
            if ( !marked[w] ) {
                dfs( G, w );
            }
        }
        postorder.enqueue( v );
        post[v] = postCounter++;
    }

    /**
     * Returns the preorder number of vertex {@code v}.
     *
     * @param v the vertex
     * @return the preorder number of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int pre( int v ) {
        validateVertex( v );
        return pre[v];
    }

    /**
     * Returns the postorder number of vertex {@code v}.
     *
     * @param v the vertex
     * @return the postorder number of vertex {@code v}
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int post( int v ) {
        validateVertex( v );
        return post[v];
    }

    /**
     * Returns the vertices in postorder.
     *
     * @return the vertices in postorder, as an iterable of vertices
     */
    public Iterable<Integer> post() {
        return postorder;
    }

    /**
     * Returns the vertices in preorder.
     *
     * @return the vertices in preorder, as an iterable of vertices
     */
    public Iterable<Integer> pre() {
        return preorder;
    }

    /**
     * Returns the vertices in reverse postorder.
     *
     * @return the vertices in reverse postorder, as an iterable of vertices
     */
    public Iterable<Integer> reversePost() {
        Stack<Integer> reverse = new ResizingArrayStack<>();
        for ( int v : postorder ) {
            reverse.push( v );
        }
        return reverse;
    }

    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex( int v ) {
        int V = marked.length;
        if ( v < 0 || v >= V ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( V - 1 ) );
        }
    }

}
