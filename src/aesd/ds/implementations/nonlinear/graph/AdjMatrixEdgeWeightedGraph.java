package aesd.ds.implementations.nonlinear.graph;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementação de um grafo não direcionado ponderado com matriz de
 * adjacências.
 *
 * Em contraste com a versão por lista de adjacências (ver EdgeWeightedGraph),
 * a matriz usa espaço fixo O(V^2), independentemente da quantidade de
 * arestas, e testa a existência de uma aresta específica em O(1). Em
 * compensação, obter os vizinhos de um vértice ou seu grau custa O(V), pois
 * é preciso varrer a linha inteira da matriz, em vez de O(grau) como na
 * representação por lista.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class AdjMatrixEdgeWeightedGraph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // matriz de adjacências
    private Edge[][] adj;

    /**
     * Cria um grafo ponderado com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    public AdjMatrixEdgeWeightedGraph( int vertices ) throws IllegalArgumentException {
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "number of vertices must be nonnegative" );
        }
        
        this.vertices = vertices;
        this.edges = 0;
        this.adj = new Edge[vertices][vertices];

    }

    /**
     * Cria um grafo ponderado que é a cópia profunda do grafo passado como
     * parâmetro.
     *
     * @param graph O grafo que será copiado
     * @throws IllegalArgumentException se o grafo passado for null
     */
    public AdjMatrixEdgeWeightedGraph( AdjMatrixEdgeWeightedGraph graph ) throws IllegalArgumentException {

        if ( graph == null ) {
            throw new IllegalArgumentException( "argument is null" );
        }

        this.vertices = graph.getNumberOfVertices();
        this.edges = graph.getNumberOfEdges();

        adj = new Edge[vertices][vertices];

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
     * A matriz não permite arestas paralelas: a célula só é preenchida se
     * ainda estiver nula. Para um laço (v == w), adj[v][w] e adj[w][v] são a
     * mesma célula da matriz, atribuída uma única vez.
     *
     * @param v um dos vértices
     * @param w o outro vértice
     * @param weight O peso da aresta
     * @throws IllegalArgumentException se os vértices forem inválidos
     */
    public void addEdge( int v, int w, double weight ) throws IllegalArgumentException {
        
        validateVertex( v );
        validateVertex( w );
        
        Edge e = new Edge( v, w, weight );
        
        if ( adj[v][w] == null ) {
            
            adj[v][w] = e;
            adj[w][v] = e;
            
            edges++;
            
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
     * Retorna o grau do vértice v.
     *
     * @param v o vértice
     * @return o grau do vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public int degree( int v ) throws IllegalArgumentException {

        validateVertex( v );

        int degree = 0;

        for ( int w = 0; w < vertices; w++ ) {
            if ( adj[v][w] != null ) {
                degree++;
            }
        }

        return degree;

    }

    /**
     * Retorna todas as arestas do grafo ponderado.
     *
     * A lógica selfLoops % 2 == 0 foi herdada da versão por lista de
     * adjacências (EdgeWeightedGraph), mas aqui continua correta por um
     * motivo ligeiramente diferente: lá, um laço ocupa duas posições na
     * mesma Bag; aqui, um laço ocupa uma única célula da matriz e por isso
     * aparece uma única vez ao iterar adj(v) - o contador selfLoops nunca
     * passa de 1, e a condição == 0 é sempre satisfeita nessa única
     * ocorrência.
     *
     * @return todas as aretas como um iterável.
     */
    public Iterable<Edge> edges() {

        List<Edge> list = new ResizingArrayList<>();

        for ( int v = 0; v < vertices; v++ ) {

            int selfLoops = 0;
            for ( Edge e : adj( v ) ) {

                if ( e.other( v ) > v ) {

                    list.add( e );

                    // adiciona apenas uma cópia dos loops
                } else if ( e.other( v ) == v ) {
                    if ( selfLoops % 2 == 0 ) {
                        list.add( e );
                    }
                    selfLoops++;
                }

            }

        }

        return list;

    }

    // como a matriz guarda null nas posições em que não há aresta, este
    // iterador precisa varrer w a partir da posição atual até encontrar uma
    // célula não nula (ou esgotar a linha), diferente da versão por lista de
    // adjacências, em que basta iterar diretamente sobre a Bag
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
