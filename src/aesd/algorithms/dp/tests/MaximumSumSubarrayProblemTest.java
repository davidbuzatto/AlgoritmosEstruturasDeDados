package aesd.algorithms.dp.tests;

import aesd.algorithms.dp.MaximumSumSubarrayProblem;

/**
 * Teste da implementação do algoritmo de Kadane para um resolvedor do problema
 * do subarranjo máximo de maior soma (Maximum Sum Subarray Problem).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class MaximumSumSubarrayProblemTest {
    
    public static void main( String[] args ) {
        test01();
        System.out.println();
        test02();
        System.out.println();
        test03();
        System.out.println();
        test04();
    }
    
    private static void test01() {
        
        int[] sequence = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        
        MaximumSumSubarrayProblem mssp = new MaximumSumSubarrayProblem( sequence );
        System.out.println( mssp );
        System.out.println( "Solution: " + mssp.getSolution() );
        
    }
    
    private static void test02() {
        
        int[] sequence = new int[]{5, 5, 5, 5, 5, 5, 5, 5};
        
        MaximumSumSubarrayProblem mssp = new MaximumSumSubarrayProblem( sequence );
        System.out.println( mssp );
        System.out.println( "Solution: " + mssp.getSolution() );
        
    }
    
    private static void test03() {
        
        int[] sequence = new int[]{1, 2, 3, -10, 1, 2, 3, -5, 1, 2, 3};
        
        MaximumSumSubarrayProblem mssp = new MaximumSumSubarrayProblem( sequence );
        System.out.println( mssp );
        System.out.println( "Solution: " + mssp.getSolution() );
        
    }
    
    private static void test04() {
        
        int[] sequence = new int[]{4, -5, 4, -3, 4, 4, -4, 4, -5};
        
        MaximumSumSubarrayProblem mssp = new MaximumSumSubarrayProblem( sequence );
        System.out.println( mssp );
        System.out.println( "Solution: " + mssp.getSolution() );
        
    }
    
}
