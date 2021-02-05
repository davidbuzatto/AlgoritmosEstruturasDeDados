/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.nonlinear.graph;

import aesd.esdi3.ds.implementations.linear.Bag;
import aesd.esdi3.ds.implementations.linear.ResizingArrayList;
import aesd.esdi3.ds.implementations.linear.ResizingArrayStack;
import aesd.esdi3.ds.interfaces.List;
import aesd.esdi3.ds.interfaces.Stack;

/**
 * Implementação de um digrafo (grafo direcionado) ponderado com lista de
 * adjacências.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EdgeWeightedDigraph {

    // quantidade de vértices
    private final int vertices;
    
    // quantidade de arestas
    private int edges;
    
    // listas de adjacências - adj[v] = vértices adjacentes à v
    private Bag<Edge>[] adj;
    
    // grau de entrada dos vértices - indegree[v] = grau de entrada do vértice v
    private int[] indegree;

    /**
     * Cria um digrafo ponderado com uma quantidade específica de vértices.
     *
     * @param vertices quantidade de vértices
     * @throws IllegalArgumentException se a quantidade de vértices for menor
     * que zero
     */
    @SuppressWarnings( "unchecked" )
    public EdgeWeightedDigraph( int vertices ) throws IllegalArgumentException {
        
        if ( vertices < 0 ) {
            throw new IllegalArgumentException( "Number of vertices in a Digraph must be nonnegative" );
        }
        
        this.vertices = vertices;
        this.edges = 0;
        this.indegree = new int[vertices];
        
        adj = new Bag[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new Bag<>();
        }
        
    }

    /**
     * Cria um digrafo ponderado que é a cópia profunda do digrafo ponderado 
     * passado como parâmetro.
     *
     * @param digraph O digrafo que será copiado
     * @throws IllegalArgumentException se o digrafo passado for null
     */
    @SuppressWarnings( "unchecked" )
    public EdgeWeightedDigraph( EdgeWeightedDigraph digraph ) throws IllegalArgumentException {
        
        if ( digraph == null ) {
            throw new IllegalArgumentException( "argument is null" );
        }
        
        this.vertices = digraph.getNumberOfVertices();
        this.edges = digraph.getNumberOfEdges();
        
        // atualiza os graus de entrada
        this.indegree = new int[vertices];
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            this.indegree[v] = digraph.indegree( v );
        }
        
        // atualiza as listas de adjacências
        adj = new Bag[vertices];
        for ( int v = 0; v < vertices; v++ ) {
            adj[v] = new Bag<>();
        }
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            
            // inverte as listas de adjacências para ficarem iguais às originais
            Stack<Edge> reverse = new ResizingArrayStack<>();
            
            for ( Edge e : digraph.adj[v] ) {
                reverse.push( e );
            }
            
            for ( Edge e : reverse ) {
                adj[v].add( e );
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
     * Retorna a quantidade arestas desse grafo.
     *
     * @return o número de arestas do grafo
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
     * @param v o vértice de calda/origem
     * @param w O vértice de cabeça/destino
     * @param weight O peso da aresta
     * @throws IllegalArgumentException se os vértices forem inválidos
     */
    public void addEdge( int v, int w, double weight ) throws IllegalArgumentException {
        
        validateVertex( v );
        validateVertex( w );
        
        adj[v].add( new Edge( v, w, weight ) );
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
    public Iterable<Edge> adj( int v ) throws IllegalArgumentException {
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
     * Retorna todas as arestas do digrafo ponderado.
     *
     * @return todas as aretas como um iterável.
     */
    public Iterable<Edge> edges() {
        List<Edge> list = new ResizingArrayList<>();
        for ( int v = 0; v < vertices; v++ ) {
            for ( Edge e : adj( v ) ) {
                list.add( e );
            }
        }
        return list;
    }

    @Override
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append( vertices + " " + edges + "\n" );
        
        for ( int v = 0; v < vertices; v++ ) {
            s.append( v + ": " );
            for ( Edge e : adj[v] ) {
                s.append( e + "  " );
            }
            s.append( "\n" );
        }
        
        return s.toString();
        
    }

}
