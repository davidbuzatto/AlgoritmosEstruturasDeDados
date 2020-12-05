/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.nonlinear.graph.Digraph;

/**
 * Classe com alguns algoritmos básicos para digrafos.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DigraphBasicAlgorithms {

    /**
     * Calcula o grau de saída de um vértice de um digrafo.
     * 
     * @param dg Digrafo
     * @param v Vértice
     * @return Grau de saída vértice do grafo.
     */
    public static int grauSaida( Digraph dg, int v ) {
        
        int grauSaida = 0;
        
        for ( int w : dg.adj( v ) ) {
            grauSaida++;
        }
        
        return grauSaida;
        
    }
    
    /**
     * Calcula o grau de saída de um vértice de um digrafo.
     * 
     * @param dg Digrafo
     * @param v Vértice
     * @return Grau de saída vértice do grafo.
     */
    public static int grauEntrada( Digraph dg, int v ) {
        
        int grauEntrada = 0;
        
        for ( int w = 0; w < dg.getNumberOfVertices(); w++ ) {
            
            // se não é o nó de origem
            if ( w != v ) {
                
                for ( int k : dg.adj( w ) ) {
                    if ( k == v ) {
                        grauEntrada++;
                    }
                }
                
            }
            
        }
        
        return grauEntrada;
        
    }

    /**
     * Calcula o maior grau de saída do digrafo.
     * 
     * @param dg Digrafo
     * @return O maior grau de saída do grafo.
     */
    public static int grauMaximoSaida( Digraph dg ) {
        
        int max = 0;
        
        for ( int v = 0; v < dg.getNumberOfVertices(); v++ ) {
            int grauSaida = grauSaida( dg, v );
            if ( grauSaida > max ) {
                max = grauSaida;
            }
        }
        
        return max;
        
    }
    
    /**
     * Calcula o maior grau de entrada do digrafo.
     * 
     * @param dg Digrafo
     * @return O maior grau de entrada do grafo.
     */
    public static int grauMaximoEntrada( Digraph dg ) {
        
        int max = 0;
        int grauEntrada;
        
        for ( int v = 0; v < dg.getNumberOfVertices(); v++ ) {
            grauEntrada = grauEntrada( dg, v );
            if ( grauEntrada > max ) {
                max = grauEntrada;
            }
        }
        
        return max;
        
    }

    /**
     * Calcula o grauSaida médio do grafo.
     * 
     * @param dg Digrafo
     * @return Grau médio do grafo.
     */
    public static double grauMedio( Digraph dg ) {
        return dg.getNumberOfEdges()/ (double) dg.getNumberOfVertices();
    }

    /**
     * Calcula a quantidade de laços dentro de um grafo.
     * 
     * @param dg Digrafo
     * @return Quantidade de laços.
     */
    public static int quantidadeLacos( Digraph dg ) {
        
        int cont = 0;
        
        for ( int v = 0; v < dg.getNumberOfVertices(); v++ ) {
            for ( int w : dg.adj( v ) ) {
                if ( v == w ) {
                    cont++;
                }
            }
        }
        
        return cont;
        
    }

}
