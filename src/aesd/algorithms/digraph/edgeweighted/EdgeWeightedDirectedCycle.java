package aesd.algorithms.digraph.edgeweighted;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;
import aesd.ds.interfaces.Stack;

/**
 * Determina se um digrafo ponderado possui um ciclo direcionado.
 *
 * Mesma técnica de {@link aesd.algorithms.digraph.DirectedCycle}, adaptada
 * para arestas ponderadas: rastreia onStack[] para distinguir uma aresta que
 * fecha um ciclo (aponta para um vértice ainda na pilha de recursão) de uma
 * que apenas cruza para um ramo já concluído da DFS. Detectar a ausência de
 * ciclo aqui é o que viabiliza tanto a ordenação topológica de um DAG quanto
 * a detecção de ciclos negativos usada por {@link BellmanFordSP} e
 * {@link FloydWarshall} (aplicada sobre o grafo de arestas predecessoras).
 * Complexidade O(V + E).
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class EdgeWeightedDirectedCycle {

    // marked[v] = o vértice v foi visitado?
    private boolean[] marked;
    
    // edgeTo[v] = aresta anterior no caminho até v
    private Edge[] edgeTo;
    
    // onStack[v] = o vértice v está na fila?
    private boolean[] onStack;
    
    // o ciclo direcionado, ou null caso não exista
    private Stack<Edge> cycle;

    /**
     * Determina se um digrafo ponderado possui um ciclo direcionado e, caso possua,
     * encontra tal ciclo.
     *
     * @param digrafo o digrafo ponderado
     */
    public EdgeWeightedDirectedCycle( EdgeWeightedDigraph digrafo ) {
        
        marked = new boolean[digrafo.getNumberOfVertices()];
        onStack = new boolean[digrafo.getNumberOfVertices()];
        edgeTo = new Edge[digrafo.getNumberOfVertices()];
        
        for ( int v = 0; v < digrafo.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( digrafo, v );
            }
        }
        
    }

    // computa a ordenação topológica ou encontra um ciclo direcionado
    private void dfs( EdgeWeightedDigraph G, int v ) {
        
        onStack[v] = true;
        marked[v] = true;
        
        for ( Edge e : G.adj( v ) ) {
            
            int w = e.to();

            // se encontrou um ciclo, para
            if ( cycle != null ) {
                return;
            } // encontrou um novo vértice, invoca recursivamente
            else if ( !marked[w] ) {
                edgeTo[w] = e;
                dfs( G, w );
            } // calcula o ciclo direcionado
            else if ( onStack[w] ) {
                
                cycle = new ResizingArrayStack<>();

                Edge f = e;
                while ( f.from() != w ) {
                    cycle.push( f );
                    f = edgeTo[f.from()];
                }
                cycle.push( f );

                return;
                
            }
            
        }

        onStack[v] = false;
        
    }

    /**
     * O digrafo ponderado tem um ciclo direcionado?
     *
     * @return verdadeiro caso o digrafo ponderado possua um ciclo direcionado,
     * falso caso contrário
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Retorna um ciclo direcionado do digrafo ponderado caso exista ou null
     * caso contrário.
     *
     * @return um ciclo direcionado do digrafo ponderado caso exista ou null
     * caso contrário
     */
    public Iterable<Edge> cycle() {
        return cycle;
    }

}
