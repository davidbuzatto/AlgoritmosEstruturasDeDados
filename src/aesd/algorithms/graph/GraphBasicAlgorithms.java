/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.algorithms.graph;

import aesd.ds.implementations.nonlinear.graph.Graph;

/**
 * Implementação de algoritmos básicos para grafos, principalmente para extração
 * de propriedades.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class GraphBasicAlgorithms {

    /**
     * Calcula o grau de um vértice de um grafo.
     * 
     * @param graph o grafo
     * @param v Vértice
     * @return Grau do vértice do grafo.
     */
    public static int degree( Graph graph, int v ) {
        
        int degree = 0;
        
        for ( int w : graph.adj( v ) ) {
            degree++;
        }
        
        return degree;
        
    }

    /**
     * Calcula o maior grau do grafo.
     * 
     * @param graph o grafo
     * @return O maior grau do grafo.
     */
    public static int maxDegree( Graph graph ) {
        
        int max = 0;
        int grau;
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            grau = degree( graph, v );
            if ( grau > max ) {
                max = grau;
            }
        }
        
        return max;
        
    }

    /**
     * Calcula o grau médio do grafo.
     * 
     * @param graph o grafo
     * @return Grau médio do grafo.
     */
    public static double mediumDegree( Graph graph ) {
        return 2.0 * ( graph.getNumberOfEdges() / (double) graph.getNumberOfVertices() );
    }

    /**
     * Calcula a quantidade de laços dentro de um grafo.
     * 
     * @param graph o grafo
     * @return Quantidade de laços.
     */
    public static int loopQuantity( Graph graph ) {
        
        int count = 0;
        
        for ( int v = 0; v < graph.getNumberOfVertices(); v++ ) {
            for ( int w : graph.adj( v ) ) {
                if ( v == w ) {
                    count++;
                }
            }
        }
        
        // para laços, 2 iguais (implementação das listas de adjacências)
        return count / 2;
        
    }

}
