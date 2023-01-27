package aesd.algorithms.dp.tests;

import static aesd.algorithms.dp.FibonacciProblem.DPBottomUpFibonacci;
import static aesd.algorithms.dp.FibonacciProblem.DPTopDownFibonacci;
import static aesd.algorithms.dp.FibonacciProblem.recursiveFibonacci;

/**
 * Testes do cálculo dos termos da série de Fibonacci.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class FibonacciProblemTest {
    
    public static void main( String[] args ) {
        
        int n = 0;
        int times = 0;
        
        long t = 0;
        
        long rF = 0;
        long buF = 0;
        long tdF = 0;
        
        // fácil
        n = 40;
        times = 1;
        
        // crítico
        //n = 91;
        //times = 100000;
        
        t = System.nanoTime();
        for ( int i = 0; i <= n; i++ ) {
            rF = recursiveFibonacci( i );
        }
        t = nanoSecsToMilliSecs( System.nanoTime() - t );
        System.out.printf( "Recursive: fib(%d) = %,d (%dms)\n", n, rF, t );
        
        t = System.nanoTime();
        for ( int k = 0; k < times; k++ ) {
            for ( int i = 0; i <= n; i++ ) {
                buF = DPBottomUpFibonacci( i );
            }
        }
        t = nanoSecsToMilliSecs( System.nanoTime() - t );
        System.out.printf( "Bottom-Up: fib(%d) = %,d (%dms)\n", n, buF, t );
        
        t = System.nanoTime();
        for ( int k = 0; k < times; k++ ) {
            for ( int i = 0; i <= n; i++ ) {
                tdF = DPTopDownFibonacci( i );
            }
        }
        t = nanoSecsToMilliSecs( System.nanoTime() - t );
        System.out.printf( " Top-Down: fib(%d) = %,d (%dms)\n", n, buF, t );
        
    }
    
    private static long nanoSecsToMilliSecs( long nano ) {
        return nano / 1000000;
    }
    
}
