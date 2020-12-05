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
public class QuickFindUF {

    private int[] id;    // id[i] = component identifier of i
    private int count;   // number of components

    /**
     * Initializes an empty union-find data structure with {@code n} elements
     * {@code 0} through {@code n-1}. Initially, each elements is in its own
     * set.
     *
     * @param n the number of elements
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public QuickFindUF( int n ) {
        count = n;
        id = new int[n];
        for ( int i = 0; i < n; i++ ) {
            id[i] = i;
        }
    }

    /**
     * Returns the number of sets.
     *
     * @return the number of sets (between {@code 1} and {@code n})
     */
    public int count() {
        return count;
    }

    /**
     * Returns the canonical element of the set containing element {@code p}.
     *
     * @param p an element
     * @return the canonical element of the set containing {@code p}
     * @throws IllegalArgumentException unless {@code 0 <= p < n}
     */
    public int find( int p ) {
        validate( p );
        return id[p];
    }

    // validate that p is a valid index
    private void validate( int p ) {
        int n = id.length;
        if ( p < 0 || p >= n ) {
            throw new IllegalArgumentException( "index " + p + " is not between 0 and " + ( n - 1 ) );
        }
    }

    /**
     * Returns true if the two elements are in the same set.
     *
     * @param p one element
     * @param q the other element
     * @return {@code true} if {@code p} and {@code q} are in the same set;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless both {@code 0 <= p < n} and
     * {@code 0 <= q < n}
     * @deprecated Replace with two calls to {@link #find(int)}.
     */
    @Deprecated
    public boolean connected( int p, int q ) {
        validate( p );
        validate( q );
        return id[p] == id[q];
    }

    /**
     * Merges the set containing element {@code p} with the the set containing
     * element {@code q}.
     *
     * @param p one element
     * @param q the other element
     * @throws IllegalArgumentException unless both {@code 0 <= p < n} and
     * {@code 0 <= q < n}
     */
    public void union( int p, int q ) {
        validate( p );
        validate( q );
        int pID = id[p];   // needed for correctness
        int qID = id[q];   // to reduce the number of array accesses

        // p and q are already in the same component
        if ( pID == qID ) {
            return;
        }

        for ( int i = 0; i < id.length; i++ ) {
            if ( id[i] == pID ) {
                id[i] = qID;
            }
        }
        count--;
    }

}
