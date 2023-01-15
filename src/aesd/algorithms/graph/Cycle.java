package aesd.algorithms.graph;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Graph;
import aesd.ds.interfaces.Stack;

/**
 * Determina se um grafo possui algum ciclo e caso tenha o armazena.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class Cycle {

    // marked[v] = v foi visitado?
    private boolean[] marked;
    
    // edgeTo[v] = última aresta no caminho
    private int[] edgeTo;
    
    // ciclo, caso exista
    private Stack<Integer> cycle;

    /**
     * Determina se um grafo possui ciclo e, caso tenha, encontra o mesmo.
     *
     * @param graph the undirected graph
     */
    public Cycle( Graph graph ) {
        
        if ( hasSelfLoop( graph ) ) {
            return;
        }
        
        if ( hasParallelEdges( graph ) ) {
            return;
        }
        
        marked = new boolean[graph.getNumberOfVertices()];
        edgeTo = new int[graph.getNumberOfVertices()];
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( graph, -1, v );
            }
        }
        
    }

    // esse grafo tem loop?
    // efeito colateral: inicializa o ciclo para ser o loop
    private boolean hasSelfLoop( Graph graph ) {
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            for ( int w : graph.adj( v ) ) {
                if ( v == w ) {
                    cycle = new ResizingArrayStack<>();
                    cycle.push( v );
                    cycle.push( v );
                    return true;
                }
            }
        }
        
        return false;
        
    }

    // esse grafo tem arestas paralelas?
    // efeito colateral: inicializa o ciclo para ser as arestas paralelas
    private boolean hasParallelEdges( Graph graph ) {
        
        marked = new boolean[graph.getNumberOfVertices()];

        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {

            // procura por arestas paralelas que entram em v
            for ( int w : graph.adj( v ) ) {
                if ( marked[w] ) {
                    cycle = new ResizingArrayStack<>();
                    cycle.push( v );
                    cycle.push( w );
                    cycle.push( v );
                    return true;
                }
                marked[w] = true;
            }

            // reinicia, setando false em marked[v] para todos os v's
            for ( int w : graph.adj( v ) ) {
                marked[w] = false;
            }
            
        }
        
        return false;
        
    }

    /**
     * retorna verdadeiro se o grafo possui ciclo.
     *
     * @return verdadeiro se o grado possui ciclo, falso caso contrário
     */
    public boolean hasCycle() {
        return cycle != null;
    }

    /**
     * Retorna o ciclo do grafo.
     *
     * @return um ciclo do grafo caso exista, como iterável, ou null caso não
     * haja ciclo.
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    // busca em profundidade
    private void dfs( Graph graph, int u, int v ) {
        
        marked[v] = true;
        
        for ( int w : graph.adj( v ) ) {

            // para se já encontrou um ciclo
            if ( cycle != null ) {
                return;
            }

            if ( !marked[w] ) {
                
                edgeTo[w] = v;
                dfs( graph, v, w );
                
                // verifica se há algum ciclo, desconsiderando o reverso da
                // aresta que leva a v
            } else if ( w != u ) {
                
                cycle = new ResizingArrayStack<>();
                
                for ( int current = v; current != w; current = edgeTo[current] ) {
                    cycle.push( current );
                }
                
                cycle.push( w );
                cycle.push( v );
                
            }
            
        }
        
    }

}
