/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.graph;

import aesd.esdc4.ds.implementations.linear.LinkedQueue;
import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Graph;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BreadthFirstSearch {

    private static final int INFINITY = Integer.MAX_VALUE;
    private boolean[] marked;  // marked[v] = is there an s-v path
    private int[] edgeTo;      // edgeTo[v] = previous edge on shortest s-v path
    private int[] distTo;      // distTo[v] = number of edges shortest s-v path
    private final int s;         // source vertex
    private final Graph g;

    /**
     * Computes the shortest path between the source vertex {@code s}
     * and every other vertex in the graph {@code G}.
     * @param G the graph
     * @param s the source vertex
     * @throws IllegalArgumentException unless {@code 0 <= s < V}
     */
    public BreadthFirstSearch(Graph G, int s) {
        marked = new boolean[G.getNumberOfVertices()];
        distTo = new int[G.getNumberOfVertices()];
        edgeTo = new int[G.getNumberOfVertices()];
        this.s = s;
        this.g = G;
        validateVertex(s);
        bfs(G, s);
    }


    // breadth-first search from a single source
    private void bfs(Graph G, int s) {
        Queue<Integer> q = new LinkedQueue<>();
        for (int v = 0; v < G.getNumberOfVertices(); v++)
            distTo[v] = INFINITY;
        distTo[s] = 0;
        marked[s] = true;
        q.enqueue(s);

        while (!q.isEmpty()) {
            int v = q.dequeue();
            for (int w : G.adj(v)) {
                if (!marked[w]) {
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    marked[w] = true;
                    q.enqueue(w);
                }
            }
        }
    }

    /**
     * Is there a path between the source vertex {@code s} (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return {@code true} if there is a path, and {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public boolean hasPathTo(int v) {
        validateVertex(v);
        return marked[v];
    }

    /**
     * Returns the number of edges in a shortest path between the source vertex {@code s}
     * (or sources) and vertex {@code v}?
     * @param v the vertex
     * @return the number of edges in a shortest path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public int distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    /**
     * Returns a shortest path between the source vertex {@code s} (or sources)
     * and {@code v}, or {@code null} if no such path.
     * @param  v the vertex
     * @return the sequence of vertices on a shortest path, as an Iterable
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Integer> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new ResizingArrayStack<>();
        int x;
        for (x = v; distTo[x] != 0; x = edgeTo[x])
            path.push(x);
        path.push(x);
        return path;
    }


    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = marked.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( "Busca em Largura (fonte: vértice " ).append( s ).append( ")\n" );
        sb.append( "v\tmarcado[v]\tarestaAte[v]\tdistanciaAte[v]\n" );
        for ( int v = 0; v < g.getNumberOfVertices(); v++ ) {
            sb.append( String.format( "%d\t%s\t\t%s\t\t%s\n", 
                    v, 
                    marked[v] ? "T" : "F", 
                    edgeTo[v] == -1 ? "-" : edgeTo[v],  
                    distTo[v] == -1 ? "-" : distTo[v] ) );
        }
        
        return sb.toString();
        
    }
    
}
