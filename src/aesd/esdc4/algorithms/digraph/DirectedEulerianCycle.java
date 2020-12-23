/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.linear.ResizingArrayStack;
import aesd.esdc4.ds.implementations.nonlinear.graph.Digraph;
import aesd.esdc4.ds.interfaces.Stack;
import java.util.Iterator;

/**
 * Computa um ciclo Euleriano do digrafo caso exista.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DirectedEulerianCycle {

    // ciclo Euleriano
    private Stack<Integer> cycle;

    /**
     * Computa um ciclo Euleriano do digrafo caso exista.
     *
     * @param digraph o digrafo
     */
    @SuppressWarnings( "unchecked" )
    public DirectedEulerianCycle( Digraph digraph ) {

        // precisa ter no mínimo uma aresta
        if ( digraph.getNumberOfEdges() == 0 ) {
            return;
        }

        // condição necessária: graus de entrada e saída de v precisam ser
        // iguais (indegree(v) = outdegree(v)) para cada vértice v
        // esse teste é necessário, senão será encontrado um caminho Euleriano
        // não um ciclo
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            if ( digraph.outdegree( v ) != digraph.indegree( v ) ) {
                return;
            }
        }

        // cria uma visualização local das listas de adjacências
        // para iterar um vértice por vez
        Iterator<Integer>[] adj = new Iterator[digraph.getNumberOfVertices()];
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            adj[v] = digraph.adj( v ).iterator();
        }

        // cria a pilha com um vértice não isolado
        int s = nonIsolatedVertex( digraph );
        Stack<Integer> stack = new ResizingArrayStack<>();
        stack.push( s );

        // procura de forma gulosa todos as arestas no estilo de uma busca em
        // profundidade iterativa
        cycle = new ResizingArrayStack<>();
        
        while ( !stack.isEmpty() ) {
            
            int v = stack.pop();
            
            while ( adj[v].hasNext() ) {
                stack.push( v );
                v = adj[v].next();
            }
            
            // empilha o vértice que não tem mais arestas saindo
            cycle.push( v );
            
        }

        // verifica se todas as arestas foram usadas
        if ( cycle.getSize()!= digraph.getNumberOfEdges() + 1 ) {
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
     * Retorna se o digrafo possui um ciclo Euleriano.
     *
     * @return verdadeiro caso o digrafo possua um ciclo Euleriano, falso caso
     * contrário
     */
    public boolean hasEulerianCycle() {
        return cycle != null;
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
