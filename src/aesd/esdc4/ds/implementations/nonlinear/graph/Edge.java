/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.graph;

/**
 * Implementação de um aresta direcionada com peso.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Edge implements Comparable<Edge> {

    // vértice de calda/origem
    private final int v;
    
    // vértice cabeça/destino
    private final int w;
    
    // peso
    private final double weight;

    /**
     * Cria uma aresta entre v e w, com um peso.
     *
     * @param v vértice de calda/origem
     * @param w vértice de cabeça/destino
     * @param weight o peso da atesta
     * @throws IllegalArgumentException se v ou w forem inválidos
     * @throws IllegalArgumentException se o peso não for um número
     */
    public Edge( int v, int w, double weight ) throws IllegalArgumentException {
        
        if ( v < 0 ) {
            throw new IllegalArgumentException( "vertex index must be a nonnegative integer" );
        }
        
        if ( w < 0 ) {
            throw new IllegalArgumentException( "vertex index must be a nonnegative integer" );
        }
        
        if ( Double.isNaN( weight ) ) {
            throw new IllegalArgumentException( "Weight is NaN" );
        }
        
        this.v = v;
        this.w = w;
        this.weight = weight;
        
    }

    public double weight() {
        return weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }
    
    /**
     * Retorna qualquer um dos pontos finais desta aresta.
     *
     * @return qualquer ponto da aresta
     */
    public int either() {
        return v;
    }

    /**
     * Retorna o ponto final desta aresta diferente do vértice passado.
     *
     * @param vertex um dos pontos finais na aresta.
     * @return o outro ponto final
     * @throws IllegalArgumentException se o vértice passado não for um dos pontos
     * finais da aresta.
     */
    public int other( int vertex ) throws IllegalArgumentException {
        if ( vertex == v ) {
            return w;
        } else if ( vertex == w ) {
            return v;
        } else {
            throw new IllegalArgumentException( "Illegal endpoint" );
        }
    }

    @Override
    public int compareTo( Edge that ) {
        return Double.compare( this.weight, that.weight );
    }

    @Override
    public String toString() {
        return String.format( "%d-%d %.5f", v, w, weight );
    }

}
