/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.algorithms.digraph;

import aesd.ds.implementations.linear.LinkedQueue;
import aesd.ds.implementations.linear.ResizingArrayStack;
import aesd.ds.implementations.nonlinear.graph.Digraph;
import aesd.ds.implementations.nonlinear.graph.Edge;
import aesd.ds.implementations.nonlinear.graph.EdgeWeightedDigraph;
import aesd.ds.interfaces.Queue;
import aesd.ds.interfaces.Stack;

/**
 * Determina a ordem de profundidade de um digrafo.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DepthFirstOrder {

    // marked[v] = v foi marcado na dfs?
    private boolean[] marked;
    
    // pre[v] = número de v na pré-ordem
    private int[] pre;
    
    // post[v] = número de v na pós-ordem
    private int[] post;
    
    // vértices em pré-ordem
    private Queue<Integer> preorder;
    
    // vértices em pós-ordem
    private Queue<Integer> postorder;
    
    // contador para a numeração em pré-ordem
    private int preCounter;
    
    // contador para a numeração em pós-ordem
    private int postCounter;

    /**
     * Determina a ordem de profundidade de um digrafo.
     *
     * @param digraph o digrafo
     */
    public DepthFirstOrder( Digraph digraph ) {
        
        pre = new int[digraph.getNumberOfVertices()];
        post = new int[digraph.getNumberOfVertices()];
        postorder = new LinkedQueue<>();
        preorder = new LinkedQueue<>();
        marked = new boolean[digraph.getNumberOfVertices()];
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( digraph, v );
            }
        }

    }

    /**
     * Determina a ordem de profundidade de um digrafo ponderado.
     *
     * @param digraph o digrafo ponderado
     */
    public DepthFirstOrder( EdgeWeightedDigraph digraph ) {
        
        pre = new int[digraph.getNumberOfVertices()];
        post = new int[digraph.getNumberOfVertices()];
        postorder = new LinkedQueue<>();
        preorder = new LinkedQueue<>();
        marked = new boolean[digraph.getNumberOfVertices()];
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            if ( !marked[v] ) {
                dfs( digraph, v );
            }
        }
        
    }

    // executa a busca em profundidade no digrafo a partir do vértice v e
    // computa pré-ordem/pó-ordem
    private void dfs( Digraph digraph, int v ) {
        
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue( v );
        
        for ( int w : digraph.adj( v ) ) {
            if ( !marked[w] ) {
                dfs( digraph, w );
            }
        }
        
        postorder.enqueue( v );
        post[v] = postCounter++;
        
    }

    // executa a busca em profundidade no digrafo ponderado a partir do vértice
    // v e computa pré-ordem/pó-ordem
    private void dfs( EdgeWeightedDigraph G, int v ) {
        
        marked[v] = true;
        pre[v] = preCounter++;
        preorder.enqueue( v );
        
        for ( Edge e : G.adj( v ) ) {
            int w = e.to();
            if ( !marked[w] ) {
                dfs( G, w );
            }
        }
        
        postorder.enqueue( v );
        post[v] = postCounter++;
        
    }

    /**
     * Retorna o número de pré-ordem do vértice v.
     *
     * @param v o vértice
     * @return o número de pré-ordem do vértice v
     * @throws IllegalArgumentException caso o vértice seja inválido
     */
    public int pre( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return pre[v];
    }

    /**
     * Retorna o número de pó-ordem do vértice v.
     *
     * @param v o vértice
     * @return o número de pós-ordem do vértice v
     * @throws IllegalArgumentException caso o vértice seja inválido
     */
    public int post( int v ) throws IllegalArgumentException {
        validateVertex( v );
        return post[v];
    }

    /**
     * Retorna os vértices em pré-ordem.
     *
     * @return os vértices em pré-ordem como um interável
     */
    public Iterable<Integer> pre() {
        return preorder;
    }
    
    /**
     * Retorna os vértices em pós-ordem.
     *
     * @return os vértices em pós-ordem como um interável
     */
    public Iterable<Integer> post() {
        return postorder;
    }

    /**
     * Rertorna os vértices em pós-ordem inversa
     *
     * @return os vértices em pós-ordem inversa como um interável
     */
    public Iterable<Integer> reversePost() {
        
        Stack<Integer> reverse = new ResizingArrayStack<>();
        
        for ( int v : postorder ) {
            reverse.push( v );
        }
        
        return reverse;
        
    }

    private void validateVertex( int v ) {
        int length = marked.length;
        if ( v < 0 || v >= length ) {
            throw new IllegalArgumentException( "vertex " + v + " is not between 0 and " + ( length - 1 ) );
        }
    }

}
