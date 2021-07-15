/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.algorithms.digraph;

import aesd.ds.implementations.nonlinear.graph.Digraph;

/**
 * Implementação de algoritmos básicos para digrafos, principalmente para
 * extração de propriedades.
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
     * @param digraph o digrafo
     * @param v Vértice
     * @return Grau de saída vértice do grafo.
     */
    public static int outDegree( Digraph digraph, int v ) {
        
        int outDegree = 0;
        
        for ( int w : digraph.adj( v ) ) {
            outDegree++;
        }
        
        return outDegree;
        
    }
    
    /**
     * Calcula o grau de saída de um vértice de um digrafo.
     * 
     * @param digraph o digrafo
     * @param v Vértice
     * @return Grau de saída vértice do grafo.
     */
    public static int inDegree( Digraph digraph, int v ) {
        
        int inDegree = 0;
        
        for ( int w = 0; w < digraph.getNumberOfVertices(); w++ ) {
            
            // se não é o nó de origem
            if ( w != v ) {
                
                for ( int k : digraph.adj( w ) ) {
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
     * @param digraph o digrafo
     * @return O maior grau de saída do grafo.
     */
    public static int maxOutDegree( Digraph digraph ) {
        
        int max = 0;
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            int outDegree = outDegree( digraph, v );
            if ( outDegree > max ) {
                max = outDegree;
            }
        }
        
        return max;
        
    }
    
    /**
     * Calcula o maior grau de entrada do digrafo.
     * 
     * @param digraph o digrafo
     * @return O maior grau de entrada do grafo.
     */
    public static int maxInDegree( Digraph digraph ) {
        
        int max = 0;
        int inDegree;
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            inDegree = inDegree( digraph, v );
            if ( inDegree > max ) {
                max = inDegree;
            }
        }
        
        return max;
        
    }

    /**
     * Calcula o grauSaida médio do grafo.
     * 
     * @param digraph o digrafo
     * @return Grau médio do grafo.
     */
    public static double mediumDegree( Digraph digraph ) {
        return digraph.getNumberOfEdges() / (double) digraph.getNumberOfVertices();
    }

    /**
     * Calcula a quantidade de laços dentro de um grafo.
     * 
     * @paramdigraphdg o digrafo
     * @return Quantidade de laços.
     */
    public static int loopQuantity( Digraph digraph ) {
        
        int count = 0;
        
        for ( int v = 0; v < digraph.getNumberOfVertices(); v++ ) {
            for ( int w : digraph.adj( v ) ) {
                if ( v == w ) {
                    count++;
                }
            }
        }
        
        return count;
        
    }

}
