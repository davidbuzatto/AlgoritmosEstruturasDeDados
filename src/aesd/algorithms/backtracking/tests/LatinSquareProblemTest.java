package aesd.algorithms.backtracking.tests;

import aesd.algorithms.backtracking.LatinSquareProblem;

/**
 * Teste do resolvedor de quadrados latinos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LatinSquareProblemTest {
    
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
        
        char[][] latinSquare = new char[][]{
            { 'B', ' ', ' ', 'D' },
            { ' ', 'D', 'B', ' ' },
            { ' ', 'C', 'D', ' ' },
            { 'D', ' ', ' ', 'C' },
        };
        
        char[] symbols = new char[]{
            'A', 'B', 'C', 'D'
        };
        
        LatinSquareProblem lsp = new LatinSquareProblem( latinSquare, symbols );
        System.out.println( lsp );
        System.out.println( "Solvable? " + lsp.hasSolution() );
        
    }
    
    private static void test02() {
        
        char[][] latinSquare = new char[][]{
            { ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ' },
        };
        
        char[] symbols = new char[]{
            'A', 'B', 'C', 'D'
        };
        
        LatinSquareProblem lsp = new LatinSquareProblem( latinSquare, symbols );
        System.out.println( lsp );
        System.out.println( "Solvable? " + lsp.hasSolution() );
        
    }
    
    private static void test03() {
        
        char[][] latinSquare = new char[][]{
            { '1', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', '2', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', '3', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', '4', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', '5', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', '6', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', '7' },
        };
        
        char[] symbols = new char[]{
            '1', '2', '3', '4', '5', '6', '7'
        };
        
        LatinSquareProblem lsp = new LatinSquareProblem( latinSquare, symbols );
        System.out.println( lsp );
        System.out.println( "Solvable? " + lsp.hasSolution() );
        
    }
    
    private static void test04() {
        
        char[][] latinSquare = new char[][]{
            { '1', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', '2', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', '3', '3', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
            { ' ', ' ', ' ', ' ', '5', ' ', ' ' },
            { ' ', ' ', ' ', ' ', ' ', '6', ' ' },
            { ' ', ' ', ' ', ' ', ' ', ' ', '7' },
        };
        
        char[] symbols = new char[]{
            '1', '2', '3', '4', '5', '6', '7'
        };
        
        LatinSquareProblem lsp = new LatinSquareProblem( latinSquare, symbols );
        System.out.println( lsp );
        System.out.println( "Solvable? " + lsp.hasSolution() );
        
    }
    
}
