package aesd.algorithms.dp;

import aesd.ds.implementations.nonlinear.symtable.RedBlackTree;
import aesd.ds.interfaces.SymbolTable;

/**
 * Métodos estáticos para o cálculo dos termos da série de Fibonnaci.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Fibonacci {
    
    // armazenamento dos termos da série gerados no algoritmo top-down
    private static SymbolTable<Integer, Long> fLookup;
   
    /**
     * Versão recursiva.
     * 
     * @param n Termo da série de Fibonnaci.
     * @return O valor do termo da série.
     */
    public static long recursiveFibonacci( int n ) {
        
        if ( n < 0 ) {
            throw new IllegalArgumentException( "n must be greater than or equal to 0" );
        }
        
        if ( n == 0 || n == 1 ) {
            return 1;
        } else {
            return recursiveFibonacci( n - 2 ) + recursiveFibonacci( n - 1 );
        }
        
    }
    
    /**
     * Versão com programação dinâmica bottom-up.
     * Armazenamento dos resultados do subproblema localmente.
     * 
     * @param n Termo da série de Fibonnaci.
     * @return O valor do termo da série.
     */
    public static long DPBottomUpFibonacci( int n ) {
        
        if ( n < 0 ) {
            throw new IllegalArgumentException( "n must be greater than or equal to 0" );
        }
        
        long[] f = new long[n+1];
        
        f[0] = 1;
        
        if ( n > 0 ) {
            f[1] = 1;
        }
        
        for ( int i = 2; i <= n; i++ ) {
            f[i] = f[i-2] + f[i-1];
        }
        
        return f[n];
        
    }
    
    /**
     * Versão com programação dinâmica top-down.
     * Armazenamento dos resultados do subproblema usando uma tabela de símbolos
     * e evitando recálculos.
     * 
     * @param n Termo da série de Fibonnaci.
     * @return O valor do termo da série.
     */
    public static long DPTopDownFibonacci( int n ) {
        
        if ( n < 0 ) {
            throw new IllegalArgumentException( "n must be greater than or equal to 0" );
        }
        
        if ( fLookup == null ) {
            fLookup = new RedBlackTree<>();
            fLookup.put( 0, 1L );
            fLookup.put( 1, 1L );
        }
        
        for ( int i = fLookup.getSize(); i <= n; i++ ) {
            fLookup.put( i, fLookup.get( i-2 ) + fLookup.get( i-1 ) );
        }
        
        return fLookup.get( n );
        
    }
    
}
