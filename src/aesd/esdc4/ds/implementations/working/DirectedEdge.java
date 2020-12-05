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
public class DirectedEdge {

    private final int v;
    private final int w;
    private final double weight;

    /**
     * Initializes a directed edge from vertex {@code v} to vertex {@code w}
     * with the given {@code weight}.
     *
     * @param v the tail vertex
     * @param w the head vertex
     * @param weight the weight of the directed edge
     * @throws IllegalArgumentException if either {@code v} or {@code w} is a
     * negative integer
     * @throws IllegalArgumentException if {@code weight} is {@code NaN}
     */
    public DirectedEdge( int v, int w, double weight ) {
        if ( v < 0 ) {
            throw new IllegalArgumentException( "Vertex names must be nonnegative integers" );
        }
        if ( w < 0 ) {
            throw new IllegalArgumentException( "Vertex names must be nonnegative integers" );
        }
        if ( Double.isNaN( weight ) ) {
            throw new IllegalArgumentException( "Weight is NaN" );
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /**
     * Returns the tail vertex of the directed edge.
     *
     * @return the tail vertex of the directed edge
     */
    public int from() {
        return v;
    }

    /**
     * Returns the head vertex of the directed edge.
     *
     * @return the head vertex of the directed edge
     */
    public int to() {
        return w;
    }

    /**
     * Returns the weight of the directed edge.
     *
     * @return the weight of the directed edge
     */
    public double weight() {
        return weight;
    }

    /**
     * Returns a string representation of the directed edge.
     *
     * @return a string representation of the directed edge
     */
    public String toString() {
        return v + "->" + w + " " + String.format( "%5.2f", weight );
    }

}
