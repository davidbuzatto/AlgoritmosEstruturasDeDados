package aesd.algorithms.dp.tests;

import aesd.algorithms.dp.KnapsackProblem;

/**
 * Teste do resolvedor do Problema da Mochila (Knapsack Problem).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class KnapsackProblemTest {
    
    public static void main( String[] args ) {
        test01();
        System.out.println();
        test02();
    }
    
    private static void test01() {
        
        int[] w = new int[]{ 2, 1, 6, 5 };
        int[] v = new int[]{ 10, 7, 25, 24 };
        
        KnapsackProblem kp = new KnapsackProblem( 7, w, v );
        System.out.println( kp );
        System.out.println( "Solution: " + kp.getSolution() );
        
    }
    
    private static void test02() {
        
        int c = 10;
        int[] w = new int[]{ 8, 1, 5, 4 };
        int[] v = new int[]{ 500, 1000, 300,  210 };
        
        KnapsackProblem kp = new KnapsackProblem( c, w, v );
        System.out.println( kp );
        System.out.println( "Solution: " + kp.getSolution() );
        
    }
    
}
