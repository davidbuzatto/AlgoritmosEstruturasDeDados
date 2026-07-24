package aesd.ds.implementations.nonlinear.graph;

import aesd.ds.implementations.linear.Bag;
import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.interfaces.Stack;

/**
 * Implementação de um digrafo (grafo direcionado) com listas de adjacências.
 *
 * Como a lista de adjacências adj[v] só registra as arestas que saem de v
 * (arestas de saída), não há como obter o grau de entrada de um vértice
 * apenas percorrendo as listas de um único vértice; por isso existe o array
 * indegree, atualizado à parte a cada chamada de addEdge. A representação por
 * lista de adjacências mantém o mesmo contraste espaço/tempo em relação à
 * representação por matriz (ver AdjMatrixDigraph): a matriz usa O(V^2) de
 * espaço mas testa a existência de uma aresta em O(1), enquanto a lista usa
 * espaço proporcional a O(V + E) e é mais barata para percorrer os vizinhos
 * de um vértice.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Digraph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // listas de adjacências - adj[v] = vértices adjacentes à v
    private Bag<Integer>[] adj;
    
    // grau de entrada dos vértices - indegree[v] = grau de entrada do vértice v
    private int[] indegree;

    /**
     * Cria um digrafo com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    @SuppressWarnings( "unchecked" )
    public Digraph( int vertices ) throws IllegalArgumentException {
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "number of vertices must be nonnegative" );
        }
        
        this.vertices = vertices;
        this.edges = 0;
        
        indegree = new int[vertices];
        
        adj = new Bag[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new Bag<>();
        }
        
    }

    /**
     * Cria um digrafo que é a cópia profunda do digrafo passado como parâmetro.
     *
     * @param digraph O digrafo que será copiado
     * @throws IllegalArgumentException se o digrafo passado for null
     */
    @SuppressWarnings( "unchecked" )
    public Digraph( Digraph digraph ) throws IllegalArgumentException {
        
        if ( digraph == null ) {
            throw new IllegalArgumentException( "argument is null" );
        }

        this.vertices = digraph.getNumberOfVertices();
        this.edges = digraph.getNumberOfEdges();

        // atualiza os graus de entrada
        indegree = new int[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            this.indegree[v] = digraph.indegree( v );
        }

        // atualiza as listas de adjacências
        adj = new Bag[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new Bag<>();
        }

        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {

            // inverte as listas de adjacências para ficarem iguais às originais -
            // como simplesmente iterar a Bag original e ir adicionando na nova
            // lista inverteria a ordem de inserção, os elementos são primeiro
            // empilhados e depois desempilhados (duas inversões), preservando
            // a ordem original
            Stack<Integer> reverse = new ResizingArrayStack<>();
            
            for ( int w : digraph.adj[v] ) {
                reverse.push( w );
            }
            
            for ( int w : reverse ) {
                adj[v].add( w );
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
    
    private void validateVertex( int v ) throws IllegalArgumentException {
        
        if ( v < 0 || v >= vertices ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( vertices - 1 ) );
        }
        
    }

    /**
     * Adiciona uma aresta direcionada v->w à esse digrafo.
     *
     * Diferente do grafo não direcionado, a aresta é adicionada apenas em
     * adj[v] (não em adj[w]), já que ela representa apenas o sentido v->w.
     * Por isso o grau de entrada de w não pode ser deduzido das listas de
     * adjacências e precisa ser controlado à parte, sendo incrementado
     * manualmente em indegree[w].
     *
     * @param v o vértice de calda/origem
     * @param w O vértice de cabeça/destino
     * @throws IllegalArgumentException se os vértices forem inválidos
     */
    public void addEdge( int v, int w ) throws IllegalArgumentException {
        
        validateVertex( v );
        validateVertex( w );
        
        adj[v].add( w );
        indegree[w]++;
        
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
     * Retorna a quantidade de arestas direcionadas que saem do vértice v, ou
     * seja, o grau de saída do vértice v.
     *
     * @param v o vértice
     * @return o grau de saída do vértice v
     * @throws IllegalArgumentException se for um vértice inválido
     */
    public int outdegree( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return adj[v].getSize();
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
        return indegree[v];
    }

    /**
     * Retorna o digrafo inverso do digrafo atual.
     *
     * O digrafo inverso tem todas as arestas do digrafo original com o
     * sentido trocado, e é útil em diversos algoritmos, como o de
     * Kosaraju-Sharir para encontrar componentes fortemente conexos. A
     * construção percorre todos os vértices e todas as arestas uma única
     * vez, custando O(V + E).
     *
     * @return o digrafo inverso
     */
    public Digraph reverse() {
        
        Digraph reverse = new Digraph( vertices );
        
        for ( int v = 0; v < vertices; v++ ) {
            for ( int w : adj( v ) ) {
                reverse.addEdge( w, v );
            }
        }
        
        return reverse;
        
    }

    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append( vertices ).append( " vertices, " ).append( edges ).append(" edges \n");
        
        for ( int v = 0; v < vertices; v++ ) {
            s.append( String.format( "%d: ", v ) );
            for ( int w : adj[v] ) {
                s.append( String.format( "%d ", w ) );
            }
            s.append( "\n" );
        }
        
        return s.toString();
        
    }

}
