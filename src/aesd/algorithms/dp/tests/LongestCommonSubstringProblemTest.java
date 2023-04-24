package aesd.algorithms.dp.tests;

import aesd.algorithms.dp.LongestCommonSubstringProblem;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class LongestCommonSubstringProblemTest {
    
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
        
        LongestCommonSubstringProblem lcsp = new LongestCommonSubstringProblem( "abcb", "bdcab" );
        System.out.println( lcsp );
        System.out.println( "LCS: " + lcsp.getSolution() );
        
    }
    
    private static void test02() {
        
        LongestCommonSubstringProblem lcsp = new LongestCommonSubstringProblem( "testeabc", "abcteste" );
        System.out.println( lcsp );
        System.out.println( "LCS: " + lcsp.getSolution() );
        
    }
    
    private static void test03() {
        
        LongestCommonSubstringProblem lcsp = new LongestCommonSubstringProblem( "XMJYAUZ", "MZJAWXU" );
        System.out.println( lcsp );
        System.out.println( "LCS: " + lcsp.getSolution() );
        
    }
    
    private static void test04() {
        
        LongestCommonSubstringProblem lcsp = new LongestCommonSubstringProblem( "abcbdab", "bdcaba" );
        System.out.println( lcsp );
        System.out.println( "LCS: " + lcsp.getSolution() );
        
    }
    
}
