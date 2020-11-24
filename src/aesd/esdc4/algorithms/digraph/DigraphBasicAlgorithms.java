/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.ds.implementations.working.Digraph;

/**
 * Classe com alguns algoritmos básicos para digrafos.
 *
 * @author Prof. Dr. David Buzatto
 */
public class DigraphBasicAlgorithms {

    /**
     * Calcula o grau de saída de um vértice de um digrafo.
     * 
     * @param dg Digraph
     * @param v Vértice
     * @return Grau de saída vértice do grafo.
     */
    public static <Tipo> int grauSaida( Digraph<Tipo> dg, Tipo v ) {
        
        int grauEntrada = 0;
        
        for ( Tipo w : dg.getAdjacentes( v ) ) {
            grauEntrada++;
        }
        
        return grauEntrada;
        
    }
    
    /**
     * Calcula o grau de saída de um vértice de um digrafo.
     * 
     * @param dg Digraph
     * @param v Vértice
     * @return Grau de saída vértice do grafo.
     */
    public static <Tipo> int grauEntrada( Digraph<Tipo> dg, Tipo v ) {
        
        int grauSaida = 0;
        
        for ( Tipo w : dg.getVertices() ) {
            
            // se não é o nó de origem
            if ( !w.equals( v ) ) {
                
                for ( Tipo k : dg.getAdjacentes( w ) ) {
                    if ( k.equals( v ) ) {
                        grauSaida++;
                    }
                }
                
            }
            
        }
        
        return grauSaida;
        
    }

    /**
     * Calcula o maior grau de saída do digrafo.
     * 
     * @param dg Digraph
     * @return O maior grau de saída do grafo.
     */
    public static <Tipo> int grauMaximoSaida( Digraph<Tipo> dg ) {
        
        int max = 0;
        int grauSaida;
        
        for ( Tipo v : dg.getVertices() ) {
            grauSaida = grauSaida( dg, v );
            if ( grauSaida > max ) {
                max = grauSaida;
            }
        }
        
        return max;
        
    }
    
    /**
     * Calcula o maior grau de entrada do digrafo.
     * 
     * @param dg Digraph
     * @return O maior grau de entrada do grafo.
     */
    public static <Tipo> int grauMaximoEntrada( Digraph<Tipo> dg ) {
        
        int max = 0;
        int grauEntrada;
        
        for ( Tipo v : dg.getVertices() ) {
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
     * @param dg Digraph
     * @return Grau médio do grafo.
     */
    public static double grauMedio( Digraph dg ) {
        return dg.getQuantidadeArestas() / (double) dg.getQuantidadeVertices();
    }

    /**
     * Calcula a quantidade de laços dentro de um grafo.
     * 
     * @param dg Digraph
     * @return Quantidade de laços.
     */
    public static <Tipo> int quantidadeLacos( Digraph<Tipo> dg ) {
        
        int cont = 0;
        
        for ( Tipo v : dg.getVertices() ) {
            for ( Tipo w : dg.getAdjacentes( v ) ) {
                if ( v.equals( w ) ) {
                    cont++;
                }
            }
        }
        
        return cont;
        
    }

}
