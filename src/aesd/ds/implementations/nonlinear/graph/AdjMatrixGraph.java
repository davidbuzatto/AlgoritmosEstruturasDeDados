package aesd.ds.implementations.nonlinear.graph;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;

/**
 * Implementação de um grafo não direcionado com matriz de adjacências.
 *
 * Em contraste com a versão por lista de adjacências (ver Graph), a matriz
 * usa espaço fixo O(V^2), independentemente da quantidade de arestas, e
 * testa a existência de uma aresta específica em O(1). Em compensação,
 * obter os vizinhos de um vértice (adj(v)) ou seu grau (degree(v)) custa
 * O(V), pois é preciso varrer a linha inteira da matriz, em vez de O(grau(v))
 * como na representação por lista.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class AdjMatrixGraph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // matriz de adjacências
    private boolean[][] adj;

    /**
     * Cria um grafo com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    public AdjMatrixGraph( int vertices ) throws IllegalArgumentException {
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "number of vertices must be nonnegative" );
        }
        
        this.vertices = vertices;
        this.edges = 0;
        
        adj = new boolean[vertices][vertices];
        
    }

    /**
     * Cria um grafo que é a cópia profunda do grafo passado como parâmetro.
     *
     * @param graph O grafo que será copiado
     * @throws IllegalArgumentException se o grafo passado for null
     */
    @SuppressWarnings( "unchecked" )
    public AdjMatrixGraph( AdjMatrixGraph graph ) throws IllegalArgumentException {

        if ( graph == null ) {
            throw new IllegalArgumentException( "argument is null" );
        }

        this.vertices = graph.getNumberOfVertices();
        this.edges = graph.getNumberOfEdges();
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "Number of vertices must be nonnegative" );
        }

        adj = new boolean[vertices][vertices];

        for ( int v = 0; v < vertices; v++ ) {
            for ( int w = 0; w < vertices; w++ ) {
                adj[v][w] = graph.adj[v][w];
            }
        }
        
    }

    /**
     * Retorna a quantidade vértices desse grafo.
     *
     * @return o número de vértices do grafo
     */
    public int getNumberOfVertices() {
        return vertices;
    }

    /**
     * Retorna a quantidade arestas desse grafo.
     *
     * @return o número de arestas do grafo
     */
    public int getNumberOfEdges() {
        return edges;
    }

    private void validateVertex( int v ) throws IllegalArgumentException {
        
        if ( v < 0 || v >= vertices ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( vertices - 1 ) );
        }
        
    }

    /**
     * Adiciona uma aresta não direcionada v-w à esse grafo.
     *
     * O teste if ( !adj[v][w] ) existe porque a matriz de adjacências só
     * representa presença ou ausência de aresta entre dois vértices, não
     * conseguindo representar arestas paralelas (diferente de Graph, que usa
     * lista de adjacências); por isso edges só é incrementado quando a
     * aresta ainda não existia.
     *
     * @param v um dos vértices
     * @param w o outro vértice
     * @throws IllegalArgumentException se os vértices forem inválidos
     */
    public void addEdge( int v, int w ) throws IllegalArgumentException {

        validateVertex( v );
        validateVertex( w );

        if ( !adj[v][w] ) {
            
            adj[v][w] = true;
            adj[w][v] = true;

            edges++;
            
        }
        
    }

    /**
     * Retorna os vértices adjacentes à v.
     *
     * Percorre a linha inteira da matriz correspondente a v (custo O(V)) para
     * montar a lista de vizinhos.
     *
     * @param v o vértice
     * @return os vértices adjacentes ao vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public Iterable<Integer> adj( int v ) throws IllegalArgumentException {
        
        validateVertex( v );
        List<Integer> adjOfV = new ResizingArrayList<>();
        
        int w = 0;
        for ( boolean b : adj[v] ) {
            if ( b ) {
                adjOfV.add( w );
            }
            w++;
        }
        
        return adjOfV;
        
    }

    /**
     * Retorna o grau do vértice v.
     *
     * Assim como adj(v), percorre a linha inteira da matriz, tendo o mesmo
     * custo O(V).
     *
     * @param v o vértice
     * @return o grau do vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public int degree( int v ) throws IllegalArgumentException {
        
        validateVertex( v );
        
        int degree = 0;
        for ( boolean b : adj[v] ) {
            if ( b ) {
                degree++;
            }
        }
        
        return degree;
        
    }
    
    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append( vertices ).append( " vertices, " ).append( edges ).append(" edges \n");
        
        for ( int v = 0; v < vertices; v++ ) {
            s.append( v ).append(": ");
            for ( int w : adj( v ) ) {
                s.append( w ).append( " " );
            }
            s.append( "\n" );
        }
        
        return s.toString();
        
    }

}
