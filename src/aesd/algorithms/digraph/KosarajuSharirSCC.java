package aesd.algorithms.digraph;

import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Calcula os componentes fortes do digrafo (componentes fortemente conexos).
 *
 * Algoritmo de Kosaraju-Sharir: duas passadas de DFS. Primeiro computa-se a
 * pós-ordem reversa do digrafo com as arestas invertidas (digraph.reverse());
 * em seguida, percorre-se o digrafo original na ordem em que os vértices
 * aparecem nessa pós-ordem reversa, iniciando uma nova DFS a cada vértice
 * ainda não marcado. Cada DFS dessa segunda passada visita exatamente um
 * componente fortemente conexo — a garantia vem do fato de que, ao processar
 * os vértices nessa ordem específica, nunca se alcança, a partir de um
 * componente já concluído, um vértice de um componente ainda não iniciado.
 * Complexidade O(V + E).
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class KosarajuSharirSCC {

    // marked[v] = o vértice v foi visitado?
    private boolean[] marked;
    
    // id[v] = identificador do componente forte que contém v
    private int[] id;
    
    // quantidade dos componentes fortemente conexos do grafo processado
    private int count;

    /**
     * Calcula os componentes fortes do digrafo (componentes fortemente conexos).
     *
     * @param digraph o digrafo
     */
    public KosarajuSharirSCC( Digraph digraph ) {

        // computa a pós-ordem reversa do reverso do grafo
        DepthFirstOrder dfs = new DepthFirstOrder( digraph.reverse() );

        // executa a DFS no digrafo, usando a pós-ordeme reversa para guiar o
        // cálculo
        marked = new boolean[digraph.getNumberOfVertices()];
        id = new int[digraph.getNumberOfVertices()];
        
        for ( int v : dfs.reversePost() ) {
            if ( !marked[v] ) {
                dfs( digraph, v );
                count++;
            }
        }

    }

    // dfs no digrafo
    private void dfs( Digraph digraph, int v ) {
        
        marked[v] = true;
        id[v] = count;
        
        for ( int w : digraph.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( digraph, w );
            }
        }
        
    }

    /**
     * Retorna a quantidade de componentes fortes.
     *
     * @return a quantidade de componentes fortes
     */
    public int count() {
        return count;
    }

    /**
     * Os vértices v e w estão no mesmo componente forte?
     *
     * @param v um vértice
     * @param w outro vértice
     * @return verdadeiro se v e w estiverem no mesmo componente forte,
     * falso caso contrário
     * @throws IllegalArgumentException se o vértice v ou o vértice w forem
     * inválidos
     */
    public boolean stronglyConnected( int v, int w ) throws IllegalArgumentException {
        validateVertex( v );
        validateVertex( w );
        return id[v] == id[w];
    }

    /**
     * Retorna o identificador do componente forte que contém o vértice v.
     *
     * @param v o vértice
     * @return o identificador do componente forte que contém o vértice v
     * @throws IllegalArgumentException se o vértice for inválido
     */
    public int id( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return id[v];
    }

    private void validateVertex( int v ) throws IllegalArgumentException {
        int length = marked.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }

}
