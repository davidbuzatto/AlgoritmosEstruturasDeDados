/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.working;

/**
 *
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Edge implements Comparable<Edge> {

    private final int v;
    private final int w;
    private final double weight;

    /**
     * Initializes an edge between vertices {@code v} and {@code w} of the given
     * {@code weight}.
     *
     * @param v one vertex
     * @param w the other vertex
     * @param weight the weight of this edge
     * @throws IllegalArgumentException if either {@code v} or {@code w} is a
     * negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public Edge( int v, int w, double weight ) {
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

    /**
     * Returns the weight of this edge.
     *
     * @return the weight of this edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns either endpoint of this edge.
     *
     * @return either endpoint of this edge
     */
    public int either() {
        return v;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    /**
     * Returns the endpoint of this edge that is different from the given
     * vertex.
     *
     * @param vertex one endpoint of this edge
     * @return the other endpoint of this edge
     * @throws IllegalArgumentException if the vertex is not one of the
     * endpoints of this edge
     */
    public int other( int vertex ) {
        if ( vertex == v ) {
            return w;
        } else if ( vertex == w ) {
            return v;
        } else {
            throw new IllegalArgumentException( "Illegal endpoint" );
        }
    }

    /**
     * Compares two edges by weight. Note that {@code compareTo()} is not
     * consistent with {@code equals()}, which uses the reference equality
     * implementation inherited from {@code Object}.
     *
     * @param that the other edge
     * @return a negative integer, zero, or positive integer depending on
     * whether the weight of this is less than, equal to, or greater than the
     * argument edge
     */
    @Override
    public int compareTo( Edge that ) {
        return Double.compare( this.weight, that.weight );
    }

    /**
     * Returns a string representation of this edge.
     *
     * @return a string representation of this edge
     */
    public String toString() {
        return String.format( "%d-%d %.5f", v, w, weight );
    }

}
