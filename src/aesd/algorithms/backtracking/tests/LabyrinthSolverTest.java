package aesd.algorithms.backtracking.tests;

import aesd.algorithms.backtracking.LabyrinthSolver;

/**
 * Teste do resolvedor de labirintos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LabyrinthSolverTest {
    
    public static void main( String[] args ) {
        test01();
        System.out.println();
        test02();
    }
    
    public static void test01() {
        
        boolean[][] labyrinth = new boolean[][]{
            { false, false, false, false,  true, false },
            { false,  true,  true, false, false, false },
            { false, false, false, false,  true, false },
            {  true, false,  true,  true,  true, true },
            { false, false, false, false, false, false }
        };
        
        LabyrinthSolver ls = new LabyrinthSolver( labyrinth, 0, 0, 4, 5 );
        if ( ls.hasSolution() ) {
            System.out.println( ls );
        }
        
    }
    
    public static void test02() {
        
        boolean[][] labyrinth = new boolean[][]{
            { false, false, false,  true, false, false, false, false, false, false, false, false, false, false, false, false },
            { false, false,  true,  true, false, false,  true, false, false, false,  true,  true,  true,  true,  true,  true },
            { false, false, false,  true, false, false,  true, false, false, false, false, false, false, false, false, false },
            { false,  true,  true,  true, false, false,  true, false, false, false, false, false,  true,  true,  true, false },
            { false, false,  true, false,  true, false,  true, false, false, false,  true, false,  true, false, false, false },
            { false, false,  true, false,  true, false,  true, false,  true, false,  true, false,  true, false,  true,  true },
            { false, false,  true, false,  true, false,  true, false,  true, false,  true, false,  true, false, false, false },
            { false,  true,  true, false,  true,  true, false, false,  true, false,  true, false,  true,  true,  true, false },
            { false, false, false, false, false,  true, false, false,  true, false,  true, false,  true, false, false, false },
            { false, false, false, false, false,  true, false, false,  true, false,  true, false,  true, false,  true,  true },
            { false, false,  true,  true,  true,  true, false, false,  true, false,  true, false,  true, false, false, false },
            { false, false, false, false, false, false, false, false,  true, false, false, false, false, false, false, false },
        };
        
        LabyrinthSolver ls = new LabyrinthSolver( labyrinth, 0, 0, 4, 5 );
        if ( ls.hasSolution() ) {
            System.out.println( ls );
        }
        
    }
    
}
