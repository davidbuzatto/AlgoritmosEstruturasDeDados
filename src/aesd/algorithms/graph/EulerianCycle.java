package aesd.algorithms.graph;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Graph;
import aesd.ds.interfaces.Queue;
import aesd.ds.interfaces.Stack;

/**
 * Computa um ciclo Euleriano do grafo caso exista.
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 *
 * @author Prof. Dr. David Buzatto
 */
public class EulerianCycle {

    // ciclo Euleriano
    private Stack<Integer> cycle;

    // uma aresta não direcionada com um campo para indicar se a aresta já foi
    // visitada
    private static class Edge {

        private final int v;
        private final int w;
        private boolean isUsed;

        public Edge( int v, int w ) {
            this.v = v;
            this.w = w;
            isUsed = false;
        }

        // retorna o outro vértice da aresta
        public int other( int vertex ) throws IllegalArgumentException {
            if ( vertex == v ) {
                return w;
            } else if ( vertex == w ) {
                return v;
            } else {
                throw new IllegalArgumentException( "Illegal endpoint" );
            }
        }
        
    }

    /**
     * Computa um ciclo Euleriano do grafo caso exista.
     *
     * @param graph o grafo
     */
    @SuppressWarnings( "unchecked" )
    public EulerianCycle( Graph graph ) {

        // precisa ter no mínimo uma aresta
        if ( graph.getNumberOfEdges() == 0 ) {
            return;
        }

        // condição necessária: todos os vértices precisam ter grau par
        // esse teste é necessário, senão será encontrado um caminho Euleriano
        // não um ciclo
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            if ( graph.degree( v ) % 2 != 0 ) {
                return;
            }
        }

        // cria uma visualização local das listas de adjacências
        // para iterar um vértice por vez, o tipo de dados Edge é usado para
        // evitar que sejam exploradas ambas as cópias da aresta v-w.
        Queue<Edge>[] adj = new Queue[graph.getNumberOfVertices()];
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            adj[v] = new LinkedQueue<>();
        }

        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            
            int selfLoops = 0;
            
            for ( int w : graph.adj( v ) ) {
                
                // tratando loops
                if ( v == w ) {
                    if ( selfLoops % 2 == 0 ) {
                        Edge e = new Edge( v, w );
                        adj[v].enqueue( e );
                        adj[w].enqueue( e );
                    }
                    selfLoops++;
                } else if ( v < w ) {
                    Edge e = new Edge( v, w );
                    adj[v].enqueue( e );
                    adj[w].enqueue( e );
                }
                
            }
            
        }

        // cria a pilha com um vértice não isolado
        int s = nonIsolatedVertex( graph );
        Stack<Integer> stack = new ResizingArrayStack<>();
        stack.push( s );

        // procura de forma gulosa todos as arestas no estilo de uma busca em
        // profundidade iterativa
        cycle = new ResizingArrayStack<>();
        
        while ( !stack.isEmpty() ) {
            
            int v = stack.pop();
            
            while ( !adj[v].isEmpty() ) {
                
                Edge edge = adj[v].dequeue();
                if ( edge.isUsed ) {
                    continue;
                }
                edge.isUsed = true;
                
                stack.push( v );
                v = edge.other( v );
                
            }
            
            // empilha o vértice que não tem mais arestas saindo
            cycle.push( v );
            
        }        

        // verifica se todas as arestas foram usadas
        if ( cycle.getSize() != graph.getNumberOfEdges() + 1 ) {
            cycle = null;
        }
        
    }

    /**
     * Retorna a sequência de vértices do ciclo Euleriano
     *
     * @return a sequência de vértices do ciclo Euleriano ou null caso não
     * exista.
     */
    public Iterable<Integer> cycle() {
        return cycle;
    }

    /**
     * Retorna se o grafo possui um ciclo Euleriano.
     *
     * @return verdadeiro caso o grafo possua um ciclo Euleriano, falso caso
     * contrário
     */
    public boolean hasEulerianCycle() {
        return cycle != null;
    }

    // retorna qualquer vértice não isolado ou -1 caso não exista nenhum
    private static int nonIsolatedVertex( Graph graph ) {
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            if ( graph.degree( v ) > 0 ) {
                return v;
            }
        }
        
        return -1;
        
    }

}
