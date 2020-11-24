/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.graphb;

import aesd.esdc4.ds.implementations.working.BasicGraph;

/**
 * Classe com alguns algoritmos básicos para grafos.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GraphBasicAlgorithms {

    /**
     * Calcula o grau de um vértice de um grafo.
     * 
     * @param g BasicGraph
     * @param v Vértice
     * @return Grau do vértice do grafo.
     */
    public static int grau( BasicGraph g, int v ) {
        
        int grau = 0;
        
        for ( int w : g.adj( v ) ) {
            grau++;
        }
        
        return grau;
        
    }

    /**
     * Calcula o maior grau do grafo.
     * 
     * @param g BasicGraph
     * @return O maior grau do grafo.
     */
    public static int grauMaximo( BasicGraph g ) {
        
        int max = 0;
        int grau;
        
        for ( int v = 0; v < g.v(); v++ ) {
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
     * @param g BasicGraph
     * @return Grau médio do grafo.
     */
    public static double grauMedio( BasicGraph g ) {
        return 2.0 * g.e() / (double) g.v();
    }

    /**
     * Calcula a quantidade de laços dentro de um grafo.
     * 
     * @param g BasicGraph
     * @return Quantidade de laços.
     */
    public static int quantidadeLacos( BasicGraph g ) {
        
        int cont = 0;
        
        for ( int v = 0; v < g.v(); v++ ) {
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
