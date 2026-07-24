package aesd.ds.implementations.nonlinear.graph;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de um digrafo (grafo direcionado) com matriz de adjacências.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AdjMatrixDigraph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // matriz de adjacências
    private Edge[][] adj;

    /**
     * Cria um digrafo com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    public AdjMatrixDigraph( int vertices ) throws IllegalArgumentException {
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "number of vertices must be nonnegative" );
        }
        
        this.vertices = vertices;
        this.edges = 0;
        this.adj = new Edge[vertices][vertices];

    }

    /**
     * Cria um digrafo que é a cópia profunda do digrafo passado como parâmetro.
     *
     * @param digraph O digrafo que será copiado
     * @throws IllegalArgumentException se o digrafo passado for null
     */
    public AdjMatrixDigraph( AdjMatrixDigraph digraph ) throws IllegalArgumentException {

        if ( digraph == null ) {
            throw new IllegalArgumentException( "argument is null" );
        }

        this.vertices = digraph.getNumberOfVertices();
        this.edges = digraph.getNumberOfEdges();

        adj = new Edge[vertices][vertices];

        for ( int v = 0; v < vertices; v++ ) {
            for ( int w = 0; w < vertices; w++ ) {
                adj[v][w] = digraph.adj[v][w];
            }
        }

    }

    /**
     * Retorna a quantidade vértices desse digrafo.
     *
     * @return o número de vértices do digrafo
     */
    public int getNumberOfVertices() {
        return vertices;
    }

    /**
     * Retorna a quantidade arestas desse digrafo.
     *
     * @return o número de arestas do digrafo
     */
    public int getNumberOfEdges() {
        return edges;
    }

    private void validateVertex( int v ) {
        
        if ( v < 0 || v >= vertices ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( vertices - 1 ) );
        }
        
    }
    
    /**
     * Adiciona uma aresta direcionada v->w à esse digrafo.
     *
     * @param v o vértice de calda/origem
     * @param w O vértice de cabeça/destino
     * @throws IllegalArgumentException se os vértices forem inválidos
     */
    public void addEdge( int v, int w ) throws IllegalArgumentException {
        
        validateVertex( v );
        validateVertex( w );
        
        if ( adj[v][w] == null ) {
            edges++;
            adj[v][w] = new Edge( v, w, 0 );
        }
    }

    /**
     * Retorna os vértices adjacentes à v.
     *
     * @param v o vértice
     * @return os vértices adjacentes ao vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public Iterable<Edge> adj( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return new AdjIterator( v );
    }

    /**
     * Retorna a quantidade de arestas direcionadas que saem do vértice v, ou
     * seja, o grau de saída do vértice v.
     *
     * @param v o vértice
     * @return o grau de saída do vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public int outdegree( int v ) throws IllegalArgumentException {

        validateVertex( v );

        int outdegree = 0;

        for ( int w = 0; w < vertices; w++ ) {
            if ( adj[v][w] != null ) {
                outdegree++;
            }
        }

        return outdegree;

    }

    /**
     * Retorna a quantidade de arestas direcionadas que entram do vértice v, ou
     * seja, o grau de entrada do vértice v.
     *
     * @param v o vértice
     * @return o grau de entrada do vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public int indegree( int v ) throws IllegalArgumentException {

        validateVertex( v );

        int indegree = 0;

        for ( int w = 0; w < vertices; w++ ) {
            if ( adj[w][v] != null ) {
                indegree++;
            }
        }

        return indegree;

    }

    /**
     * Retorna o digrafo inverso do digrafo atual.
     *
     * @return o digrafo inverso
     */
    public AdjMatrixDigraph reverse() {

        AdjMatrixDigraph reverse = new AdjMatrixDigraph( vertices );

        for ( int v = 0; v < vertices; v++ ) {
            for ( int w = 0; w < vertices; w++ ) {
                if ( adj[v][w] != null ) {
                    reverse.addEdge( w, v );
                }
            }
        }

        return reverse;

    }

    private class AdjIterator implements Iterator<Edge>, Iterable<Edge> {

        private int v;
        private int w = 0;

        public AdjIterator( int v ) {
            this.v = v;
        }

        @Override
        public Iterator<Edge> iterator() {
            return this;
        }

        @Override
        public boolean hasNext() {
            while ( w < vertices ) {
                if ( adj[v][w] != null ) {
                    return true;
                }
                w++;
            }
            return false;
        }

        @Override
        public Edge next() {
            if ( !hasNext() ) {
                throw new NoSuchElementException();
            }
            return adj[v][w++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    
    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append( vertices ).append( " " ).append( edges ).append( "\n" );
        
        for ( int v = 0; v < vertices; v++ ) {
            s.append( v ).append( ": " );
            for ( Edge e : adj( v ) ) {
                s.append( e ).append( "  " );
            }
            s.append( "\n" );
        }
        
        return s.toString();
        
    }

}
