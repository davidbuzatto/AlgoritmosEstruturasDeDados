/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.graph;

import aesd.esdc4.ds.implementations.working.Graph;

/**
 * Classe com alguns algoritmos básicos para grafos.
 *
 * @author Prof. Dr. David Buzatto
 */
public class GraphBasicAlgorithms {

    /**
     * Calcula o grau de um vértice de um grafo.
     * 
     * @param g Graph
     * @param v Vértice
     * @return Grau do vértice do grafo.
     */
    public static <Tipo> int grau( Graph<Tipo> g, Tipo v ) {
        
        int grau = 0;
        
        for ( Tipo w : g.getAdjacentes( v ) ) {
            grau++;
        }
        
        return grau;
        
    }

    /**
     * Calcula o maior grau do grafo.
     * 
     * @param g Graph
     * @return O maior grau do grafo.
     */
    public static <Tipo> int grauMaximo( Graph<Tipo> g ) {
        
        int max = 0;
        int grau;
        
        for ( Tipo v : g.getVertices() ) {
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
     * @param g Graph
     * @return Grau médio do grafo.
     */
    public static double grauMedio( Graph g ) {
        return 2.0 * g.getQuantidadeArestas() / (double) g.getQuantidadeVertices();
    }

    /**
     * Calcula a quantidade de laços dentro de um grafo.
     * 
     * @param g Graph
     * @return Quantidade de laços.
     */
    public static <Tipo> int quantidadeLacos( Graph<Tipo> g ) {
        
        int cont = 0;
        
        for ( Tipo v : g.getVertices() ) {
            for ( Tipo w : g.getAdjacentes( v ) ) {
                if ( v.equals( w ) ) {
                    cont++;
                }
            }
        }
        
        // para laços, 2 iguais (implementação)
        return cont / 2;
        
    }

}
