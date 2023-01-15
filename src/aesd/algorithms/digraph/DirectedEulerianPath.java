package aesd.algorithms.digraph;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Digraph;
import aesd.ds.interfaces.Stack;
import java.util.Iterator;

/**
 * Computa um caminho Euleriano do digrafo caso exista.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DirectedEulerianPath {

    // caminho Euleriano
    private Stack<Integer> path;

    /**
     * Computa um caminho Euleriano do digrafo caso exista.
     *
     * @param digraph o grafo
     */
    @SuppressWarnings( "unchecked" )
    public DirectedEulerianPath( Digraph digraph ) {

        // procura por um vértice para iniciar um provável caminho Euleriano:
        // um vértice com grau de saída maior que o grau de entrada se existir
        // (outdegree(v) > indegree(v));
        // caso contrário, um vértice com grau de saída maior que zero
        // (outdegree(v) > 0).
        int deficit = 0;
        int s = nonIsolatedVertex( digraph );
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            if ( digraph.outdegree( v ) > digraph.indegree( v ) ) {
                deficit += ( digraph.outdegree( v ) - digraph.indegree( v ) );
                s = v;
            }
        }

        // digrafo não têm um caminho Euleriano
        // essa condição é necessária para corretude
        if ( deficit > 1 ) {
            return;
        }

        // caso especial em que o grafo não possui nenhuma aresta.
        // nesse caso o grafo possui um caminho Euleriano degenerado.
        if ( s == -1 ) {
            s = 0;
        }

        // cria uma visualização local das listas de adjacências
        // para iterar um vértice por vez
        Iterator<Integer>[] adj = new Iterator[digraph.getNumberOfVertices()];
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            adj[v] = digraph.adj( v ).iterator();
        }

        // cria a pilha com um vértice não isolado
        Stack<Integer> stack = new ResizingArrayStack<>();
        stack.push( s );
        
        // procura de forma gulosa todos as arestas no estilo de uma busca em
        // profundidade iterativa
        path = new ResizingArrayStack<>();
        
        while ( !stack.isEmpty() ) {
            
            int v = stack.pop();
            
            while ( adj[v].hasNext() ) {
                stack.push( v );
                v = adj[v].next();
            }
            
            // empilha o vértice que não tem mais arestas saindo
            path.push( v );
            
        }

        // verifica se todas as arestas foram usadas
        if ( path.getSize()!= digraph.getNumberOfEdges() + 1 ) {
            path = null;
        }
        
    }

    /**
     * Retorna a sequência de vértices do caminho Euleriano
     *
     * @return a sequência de vértices do caminho Euleriano ou null caso não
     * exista.
     */
    public Iterable<Integer> path() {
        return path;
    }

    /**
     * Retorna se o digrafo possui um caminho Euleriano.
     *
     * @return verdadeiro caso o digrafo possua um caminho Euleriano, falso caso
     * contrário
     */
    public boolean hasEulerianPath() {
        return path != null;
    }

    // retorna qualquer vértice não isolado ou -1 caso não exista nenhum
    private static int nonIsolatedVertex( Digraph digraph ) {
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            if ( digraph.outdegree( v ) > 0 ) {
                return v;
            }
        }
        
        return -1;
        
    }

}
