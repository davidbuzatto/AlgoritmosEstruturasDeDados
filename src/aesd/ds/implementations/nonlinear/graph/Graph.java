package aesd.ds.implementations.nonlinear.graph;

import aesd.ds.implementations.linear.Bag;
import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.interfaces.Stack;

/**
 * Implementação de um grafo não direcionado com listas de adjacências.
 *
 * A ideia central da representação por listas de adjacências é manter, para
 * cada vértice v, uma lista (aqui, uma Bag) apenas com os vértices que são
 * adjacentes à v. Isso resulta em espaço proporcional a O(V + E), inserção de
 * uma aresta em tempo O(1) e percurso pelos vizinhos de um vértice em tempo
 * O(grau(v)). Isso contrasta com a representação por matriz de adjacências
 * (ver AdjMatrixGraph), que gasta O(V^2) de espaço mas testa a existência de
 * uma aresta em O(1); a lista de adjacências, por sua vez, usa espaço
 * proporcional à quantidade de arestas, porém percorrer os vizinhos de um
 * vértice é mais barato.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Graph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // listas de adjacências
    private Bag<Integer>[] adj;

    /**
     * Cria um grafo com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    @SuppressWarnings( "unchecked" )
    public Graph( int vertices ) throws IllegalArgumentException {
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "number of vertices must be nonnegative" );
        }
        
        this.vertices = vertices;
        this.edges = 0;
        
        adj = new Bag[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new Bag<>();
        }
        
    }

    /**
     * Cria um grafo que é a cópia profunda do grafo passado como parâmetro.
     *
     * @param graph O grafo que será copiado
     * @throws IllegalArgumentException se o grafo passado for null
     */
    @SuppressWarnings( "unchecked" )
    public Graph( Graph graph ) throws IllegalArgumentException {
        
        if ( graph == null ) {
            throw new IllegalArgumentException( "argument is null" );
        }
        
        this.vertices = graph.getNumberOfVertices();
        this.edges = graph.getNumberOfEdges();

        // atualiza as listas de adjacências
        adj = new Bag[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new Bag<>();
        }

        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {

            // inverte as listas de adjacências para ficarem iguais às originais -
            // como simplesmente iterar a Bag original e ir adicionando na nova
            // lista inverteria a ordem de inserção, os elementos são primeiro
            // empilhados e depois desempilhados (duas inversões), preservando
            // a ordem original
            Stack<Integer> reverse = new ResizingArrayStack<>();
            
            for ( int w : graph.adj[v] ) {
                reverse.push( w );
            }
            
            for ( int w : reverse ) {
                adj[v].add( w );
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
     * Arestas paralelas e laços (v == w) são aceitos sem qualquer verificação,
     * já que a lista de adjacências naturalmente suporta multigrafos. Isso é
     * diferente de AdjMatrixGraph, cuja matriz de adjacências descarta
     * duplicatas.
     *
     * @param v um dos vértices
     * @param w o outro vértice
     * @throws IllegalArgumentException se os vértices forem inválidos
     */
    public void addEdge( int v, int w ) throws IllegalArgumentException {
        
        validateVertex( v );
        validateVertex( w );
        
        adj[v].add( w );
        adj[w].add( v );
        
        edges++;
        
    }

    /**
     * Retorna os vértices adjacentes à v.
     *
     * @param v o vértice
     * @return os vértices adjacentes ao vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public Iterable<Integer> adj( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return adj[v];
    }

    /**
     * Retorna o grau do vértice v.
     *
     * @param v o vértice
     * @return o grau do vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public int degree( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return adj[v].getSize();
    }
    
    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append( vertices ).append( " vertices, " ).append( edges ).append(" edges \n");
        
        for ( int v = 0; v < vertices; v++ ) {
            s.append( v ).append(": ");
            for ( int w : adj[v] ) {
                s.append( w ).append( " " );
            }
            s.append( "\n" );
        }
        
        return s.toString();
        
    }

}
