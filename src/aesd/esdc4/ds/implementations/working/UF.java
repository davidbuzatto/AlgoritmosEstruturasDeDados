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
public class UF {

    private int[] parent;  // parent[i] = parent of i
    private byte[] rank;   // rank[i] = rank of subtree rooted at i (never more than 31)
    private int count;     // number of components

    /**
     * Initializes an empty union-find data structure with {@code n} elements
     * {@code 0} through {@code n-1}. Initially, each elements is in its own
     * set.
     *
     * @param n the number of elements
     * @throws IllegalArgumentException if {@code n < 0}
     */
    public UF( int n ) {
        if ( n < 0 ) {
            throw new IllegalArgumentException();
        }
        count = n;
        parent = new int[n];
        rank = new byte[n];
        for ( int i = 0; i < n; i++ ) {
            parent[i] = i;
            rank[i] = 0;
        }
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
        while ( p != parent[p] ) {
            parent[p] = parent[parent[p]];    // path compression by halving
            p = parent[p];
        }
        return p;
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
        return find( p ) == find( q );
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
        int rootP = find( p );
        int rootQ = find( q );
        if ( rootP == rootQ ) {
            return;
        }

        // make root of smaller rank point to root of larger rank
        if ( rank[rootP] < rank[rootQ] ) {
            parent[rootP] = rootQ;
        } else if ( rank[rootP] > rank[rootQ] ) {
            parent[rootQ] = rootP;
        } else {
            parent[rootQ] = rootP;
            rank[rootP]++;
        }
        count--;
    }

    // validate that p is a valid index
    private void validate( int p ) {
        int n = parent.length;
        if ( p < 0 || p >= n ) {
            throw new IllegalArgumentException( "index " + p + " is not between 0 and " + ( n - 1 ) );
        }
    }

}
