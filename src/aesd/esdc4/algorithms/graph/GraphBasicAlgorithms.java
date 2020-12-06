/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.graph;

import aesd.esdc4.ds.implementations.nonlinear.graph.Graph;

/**
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
     * @param g o grafo
     * @param v Vértice
     * @return Grau do vértice do grafo.
     */
    public static int grau( Graph g, int v ) {
        
        int grau = 0;
        
        for ( int w : g.adj( v ) ) {
            grau++;
        }
        
        return grau;
        
    }

    /**
     * Calcula o maior grau do grafo.
     * 
     * @param g o grafo
     * @return O maior grau do grafo.
     */
    public static int grauMaximo( Graph g ) {
        
        int max = 0;
        int grau;
        
        for ( int v = 0; v < g.getNumberOfVertices(); v++ ) {
            grau = grau( g, v );
            if ( grau > max ) {
                max = grau;
            }
        }
        
        return max;
        
    }

    /**
     * Calcula o grau médio do grafo.
     * 
     * @param g o grafo
     * @return Grau médio do grafo.
     */
    public static double grauMedio( Graph g ) {
        return 2.0 * g.getNumberOfEdges() / (double) g.getNumberOfVertices();
    }

    /**
     * Calcula a quantidade de laços dentro de um grafo.
     * 
     * @param g o grafo
     * @return Quantidade de laços.
     */
    public static int quantidadeLacos( Graph g ) {
        
        int cont = 0;
        
        for ( int v = 0; v < g.getNumberOfVertices(); v++ ) {
            for ( int w : g.adj( v ) ) {
                if ( v == w ) {
                    cont++;
                }
            }
        }
        
        // para laços, 2 iguais (implementação)
        return cont / 2;
        
    }

}
