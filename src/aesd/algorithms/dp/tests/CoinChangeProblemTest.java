package aesd.algorithms.dp.tests;

import aesd.algorithms.dp.CoinChangeProblem;

/**
 * Testes do resolvedor do problema do troco (Coin Change Problem).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class CoinChangeProblemTest {
    
    public static void main( String[] args ) {
        test01();
        System.out.println();
        test02();
        System.out.println();
        test03();
        System.out.println();
        test04();
        System.out.println();
        test05();
    }
    
    private static void test01() {
        
        int v = 10;
        int[] d = new int[]{1, 5};
        
        CoinChangeProblem ccp = new CoinChangeProblem( v, d );
        System.out.println( ccp );
        System.out.println( "Solution: " + ccp.getSolution() );
        
    }
    
    private static void test02() {
        
        int v = 50;
        int[] d = new int[]{1, 10, 25};
        
        CoinChangeProblem ccp = new CoinChangeProblem( v, d );
        System.out.println( ccp );
        System.out.println( "Solution: " + ccp.getSolution() );
        
    }
    
    private static void test03() {
        
        int v = 10;
        int[] d = new int[]{1};
        
        CoinChangeProblem ccp = new CoinChangeProblem( v, d );
        System.out.println( ccp );
        System.out.println( "Solution: " + ccp.getSolution() );
        
    }
    
    private static void test04() {
        
        int v = 10;
        int[] d = new int[]{1, 2};
        
        CoinChangeProblem ccp = new CoinChangeProblem( v, d );
        System.out.println( ccp );
        System.out.println( "Solution: " + ccp.getSolution() );
        
    }
    
    private static void test05() {
        
        int v = 7;
        int[] d = new int[]{1, 3, 4, 5};
        
        CoinChangeProblem ccp = new CoinChangeProblem( v, d );
        System.out.println( ccp );
        System.out.println( "Solution: " + ccp.getSolution() );
        
    }
    
}
