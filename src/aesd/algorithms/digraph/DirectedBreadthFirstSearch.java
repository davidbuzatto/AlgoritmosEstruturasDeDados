package aesd.algorithms.digraph;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Digraph;
import aesd.ds.interfaces.Queue;
import aesd.ds.interfaces.Stack;

/**
 * Realiza a busca em largura para computar o menor caminho entre o vértice
 * fonte e todos os outros vértices do digrafo.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DirectedBreadthFirstSearch {

    // maior valor possível do tipo inteiro
    private static final int INFINITY = Integer.MAX_VALUE;

    // marked[v] = há um caminho s-v?
    // ou v foi visitado?
    private boolean[] marked;

    // edgeTo[v] = última aresta no menor caminho s-v
    private int[] edgeTo;

    // distTo[v] = número de arestas no menor caminho s-v
    private int[] distTo;

    // vértice fonte
    private final int source;

    // o grafo
    private final Digraph digraph;
    
    /**
     * Computa o menor caminho entre o vértice fonte s e todos os outros
     * vértices do digrafo.
     *
     * @param digraph o grafo
     * @param source o vértice fonte
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public DirectedBreadthFirstSearch( Digraph digraph, int source ) throws IllegalArgumentException {
        
        validateVertex( source );
        
        marked = new boolean[digraph.getNumberOfVertices()];
        distTo = new int[digraph.getNumberOfVertices()];
        edgeTo = new int[digraph.getNumberOfVertices()];
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            distTo[v] = INFINITY;
        }
        
        this.source = source;
        this.digraph = digraph;
        
        bfs( digraph, source );
        
    }

    // implementação da busca em largura para uma fonte
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

    /**
     * Há um caminho direcionado entre o vértice fonte e v?
     *
     * @param v o vértice
     * @return verdadeiro se houver um caminho direcionado entre s->v, falso
     * caso contrário
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public boolean hasPathTo( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return marked[v];
    }

    /**
     * Retorna a quantidade de arestas do menor caminho direcionado entre o
     * vértice fonte e o vértice passado.
     *
     * @param v o vértice
     * @return a quantidade de aretas no caminho direcionado
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public int distTo( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return distTo[v];
    }

    /**
     * Retorna o menor caminho direcionado entre o vértice fonte e o vértice
     * passado ou  null caso não exista tal caminho.
     *
     * @param v o vértice
     * @return a sequência de vértices do menor caminho como um iterável
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public Iterable<Integer> pathTo( int v ) throws IllegalArgumentException {
        
        validateVertex( v );

        if ( !hasPathTo( v ) ) {
            return null;
        }
        
        Stack<Integer> path = new ResizingArrayStack<>();
        int current;
        
        for ( current = v; distTo[current] != 0; current = edgeTo[current] ) {
            path.push( current );
        }
        path.push( current );
        
        return path;
        
    }
    
    private void validateVertex( int v ) {
        int length = marked.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }
    
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append( "Breadth-First Search (source: vertex" ).append( source ).append( ")\n" );
        sb.append( "v\tmarked[v]\tedgeTo[v]\tdistTo[v]\n" );
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            sb.append( String.format( "%d\t%s\t\t%s\t\t%s\n",
                    v,
                    marked[v] ? "T" : "F",
                    edgeTo[v] == -1 ? "-" : edgeTo[v],
                    distTo[v] == -1 ? "-" : distTo[v] ) );
        }

        return sb.toString();

    }

}
