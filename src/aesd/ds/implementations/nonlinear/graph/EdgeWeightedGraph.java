package aesd.ds.implementations.nonlinear.graph;

import aesd.ds.implementations.linear.Bag;
import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.interfaces.List;
import aesd.ds.interfaces.Stack;

/**
 * Implementação de um grafo não direcionado ponderado com listas de
 * adjacências.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EdgeWeightedGraph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // listas de adjacências - adj[v] = vértices adjacentes à v
    private Bag<Edge>[] adj;

    /**
     * Cria um grafo ponderado com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    @SuppressWarnings( "unchecked" )
    public EdgeWeightedGraph( int vertices ) throws IllegalArgumentException {
        
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
     * Cria um grafo ponderado que é a cópia profunda do grafo ponderado 
     * passado como parâmetro.
     *
     * @param graph O grafo que será copiado
     * @throws IllegalArgumentException se o grafo passado for null
     */
    @SuppressWarnings( "unchecked" )
    public EdgeWeightedGraph( EdgeWeightedGraph graph ) throws IllegalArgumentException {
        
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
            Stack<Edge> reverse = new ResizingArrayStack<>();
            
            for ( Edge e : graph.adj[v] ) {
                reverse.push( e );
            }
            
            for ( Edge e : reverse ) {
                adj[v].add( e );
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
     * Adiciona uma aresta não direcionada v-w, com peso, à esse grafo ponderado.
     *
     * Para um laço (v == w), adj[v].add(e) e adj[w].add(e) se referem à mesma
     * Bag, de modo que a mesma Edge acaba sendo adicionada duas vezes nessa
     * única lista. É esse o motivo direto da lógica selfLoops % 2 == 0
     * empregada em edges() para não listar o laço em duplicidade.
     *
     * @param v um dos vértices
     * @param w o outro vértice
     * @param weight o peso da aresta
     * @throws IllegalArgumentException se os vértices forem inválidos
     */
    public void addEdge( int v, int w, double weight ) throws IllegalArgumentException {
        
        validateVertex( v );
        validateVertex( w );
        
        Edge e = new Edge( v, w, weight );
        
        adj[v].add( e );
        adj[w].add( e );
        
        edges++;
        
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

    /**
     * Retorna todas as arestas do grafo ponderado.
     *
     * Como cada aresta v-w aparece tanto em adj[v] quanto em adj[w], o teste
     * e.other(v) > v garante que cada aresta seja listada uma única vez, a
     * partir do vértice de menor índice entre os dois. O contador selfLoops
     * trata o caso especial dos laços, que ocupam duas posições na mesma
     * Bag (ver addEdge).
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

    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append( vertices ).append( " " ).append( edges ).append( "\n" );
        
        for ( int v = 0; v < vertices; v++ ) {
            s.append( v ).append( ": " );
            for ( Edge e : adj[v] ) {
                s.append( e ).append( "  " );
            }
            s.append( "\n" );
        }
        
        return s.toString();
        
    }

}
