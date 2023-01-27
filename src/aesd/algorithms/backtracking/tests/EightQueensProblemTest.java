package aesd.algorithms.backtracking.tests;

import aesd.algorithms.backtracking.EightQueensProblem;

/**
 * Teste do resolvedor do problema das oito rainhas (Eight Queens Problem).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class EightQueensProblemTest {
    
    public static void main( String[] args ) {
        test01();
        System.out.println();
        test02();
    }
    
    private static void test01() {
        EightQueensProblem eqp = new EightQueensProblem( 4 );
        System.out.println( "Count: " + eqp.getCount() );
    }
    
    private static void test02() {
        EightQueensProblem eqp = new EightQueensProblem( 8 );
        System.out.println( "Count: " + eqp.getCount() );
    }
}
