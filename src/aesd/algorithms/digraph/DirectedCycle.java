/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.algorithms.digraph;

import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Digraph;
import aesd.ds.interfaces.Stack;

/**
 * Determina se um digrafo possui algum ciclo e caso tenha o armazena.
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
     * @param digraph the undirected graph
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
