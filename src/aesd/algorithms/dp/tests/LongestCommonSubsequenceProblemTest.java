package aesd.algorithms.dp.tests;

import aesd.algorithms.dp.LongestCommonSubsequenceProblem;

/**
 *
 * @author Prof. Dr. David Buzatto
 */
public class LongestCommonSubsequenceProblemTest {

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

        LongestCommonSubsequenceProblem lcsp = new LongestCommonSubsequenceProblem( "abcb", "bdcab" );
        System.out.println( lcsp );
        System.out.println( "LCS: " + lcsp.getSolution() );

    }

    private static void test02() {

        LongestCommonSubsequenceProblem lcsp = new LongestCommonSubsequenceProblem( "testeabc", "abcteste" );
        System.out.println( lcsp );
        System.out.println( "LCS: " + lcsp.getSolution() );

    }

    private static void test03() {

        LongestCommonSubsequenceProblem lcsp = new LongestCommonSubsequenceProblem( "XMJYAUZ", "MZJAWXU" );
        System.out.println( lcsp );
        System.out.println( "LCS: " + lcsp.getSolution() );

    }

    private static void test04() {

        LongestCommonSubsequenceProblem lcsp = new LongestCommonSubsequenceProblem( "abcbdab", "bdcaba" );
        System.out.println( lcsp );
        System.out.println( "LCS: " + lcsp.getSolution() );

    }

}
