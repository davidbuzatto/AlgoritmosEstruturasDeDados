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
     * @param dg o digrafo
     * @param v Vértice
     * @return Grau de saída vértice do grafo.
     */
    public static int outDegree( Digraph dg, int v ) {
        
        int outDegree = 0;
        
        for ( int w : dg.adj( v ) ) {
            outDegree++;
        }
        
        return outDegree;
        
    }
    
    /**
     * Calcula o grau de saída de um vértice de um digrafo.
     * 
     * @param dg o digrafo
     * @param v Vértice
     * @return Grau de saída vértice do grafo.
     */
    public static int inDegree( Digraph dg, int v ) {
        
        int inDegree = 0;
        
        for ( int w = 0; w < dg.getNumberOfVertices(); w++ ) {
            
            // se não é o nó de origem
            if ( w != v ) {
                
                for ( int k : dg.adj( w ) ) {
                    if ( k == v ) {
                        inDegree++;
                    }
                }
                
            }
            
        }
        
        return inDegree;
        
    }

    /**
     * Calcula o maior grau de saída do digrafo.
     * 
     * @param dg o digrafo
     * @return O maior grau de saída do grafo.
     */
    public static int maxOutDegree( Digraph dg ) {
        
        int max = 0;
        
        for ( int v = 0; v < dg.getNumberOfVertices(); v++ ) {
            int outDegree = outDegree( dg, v );
            if ( outDegree > max ) {
                max = outDegree;
            }
        }
        
        return max;
        
    }
    
    /**
     * Calcula o maior grau de entrada do digrafo.
     * 
     * @param dg o digrafo
     * @return O maior grau de entrada do grafo.
     */
    public static int maxInDegree( Digraph dg ) {
        
        int max = 0;
        int inDegree;
        
        for ( int v = 0; v < dg.getNumberOfVertices(); v++ ) {
            inDegree = inDegree( dg, v );
            if ( inDegree > max ) {
                max = inDegree;
            }
        }
        
        return max;
        
    }

    /**
     * Calcula o grauSaida médio do grafo.
     * 
     * @param dg o digrafo
     * @return Grau médio do grafo.
     */
    public static double mediumDegree( Digraph dg ) {
        return dg.getNumberOfEdges()/ (double) dg.getNumberOfVertices();
    }

    /**
     * Calcula a quantidade de laços dentro de um grafo.
     * 
     * @param dg o digrafo
     * @return Quantidade de laços.
     */
    public static int loopQuantity( Digraph dg ) {
        
        int count = 0;
        
        for ( int v = 0; v < dg.getNumberOfVertices(); v++ ) {
            for ( int w : dg.adj( v ) ) {
                if ( v == w ) {
                    count++;
                }
            }
        }
        
        return count;
        
    }

}
