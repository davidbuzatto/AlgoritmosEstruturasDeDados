package aesd.algorithms.digraph;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Digraph;
import aesd.ds.interfaces.Stack;

/**
 * Determina se um digrafo possui algum ciclo e caso tenha o armazena.
 *
 * Diferente da versão para grafos não direcionados ({@link Cycle}), aqui não
 * basta comparar com o vértice pai: como as arestas têm sentido, é preciso
 * rastrear explicitamente quais vértices estão na pilha de recursão atual
 * (onStack[]). Encontrar uma aresta v-w em que w já está marcado só indica
 * um ciclo se w ainda estiver na pilha (onStack[w] == true) — ou seja, se v
 * é alcançável a partir de w pelo caminho atualmente em andamento; se w já
 * foi totalmente processado e removido da pilha, a aresta apenas cruza para
 * um ramo já concluído da DFS, não fecha ciclo algum. Complexidade O(V + E).
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class DirectedCycle {

    // marked[v] = v foi visitado?
    private boolean[] marked;
    
    // edgeTo[v] = última aresta no caminho
    private int[] edgeTo;
    
    // onStack[v] = o vértice v está na fila?
    private boolean[] onStack;
    
    // ciclo direcionado, caso exista
    private Stack<Integer> cycle;

    /**
     * Determina se um digrafo possui ciclo e, caso tenha, encontra o mesmo.
     *
     * @param digraph o digrafo
     */
    public DirectedCycle( Digraph digraph ) {
        
        marked = new boolean[digraph.getNumberOfVertices()];
        onStack = new boolean[digraph.getNumberOfVertices()];
        edgeTo = new int[digraph.getNumberOfVertices()];
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] && cycle == null ) {
                dfs( digraph, v );
            }
        }
        
    }

    // busca em profundidade para encontrar o ciclo direcionado, caso exista
    private void dfs( Digraph digraph, int v ) {
        
        onStack[v] = true;
        marked[v] = true;
        
        for ( int w : digraph.adj( v ) ) {

            // se encontrou um ciclo, para
            if ( cycle != null ) {
                return;
            } // encontrou um novo vértice, invoca recursivamente
            else if ( !marked[w] ) {
                edgeTo[w] = v;
                dfs( digraph, w );
            } // calcula o ciclo direcionado
            else if ( onStack[w] ) {
                
                cycle = new ResizingArrayStack<>();
                
                for ( int x = v; x != w; x = edgeTo[x] ) {
                    cycle.push( x );
                }
                
                cycle.push( w );
                cycle.push( v );
                
            }
            
        }
        
        onStack[v] = false;
        
    }

    /**
     * O digrafo tem um ciclo direcionado?
     *
     * @return verdadeiro caso o digrafo possua um ciclo direcionado,
     * falso caso contrário
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Retorna um ciclo direcionado do digrafo caso exista ou null caso
     * contrário.
     *
     * @return um ciclo direcionado do digrafo caso exista ou null
     * caso contrário
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

}
