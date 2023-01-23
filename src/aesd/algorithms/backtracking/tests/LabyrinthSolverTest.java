package aesd.algorithms.backtracking.tests;

import aesd.algorithms.backtracking.LabyrinthSolver;

/**
 * Teste do resolvedor de labirintos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LabyrinthSolverTest {
    
    public static void main( String[] args ) {
        
        int[][] labyrinth = new int[][]{
            { 0, 0, 0, 0, 1, 0 },
            { 0, 1, 1, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0 },
            { 1, 0, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0 }
        };
        
        LabyrinthSolver ls = new LabyrinthSolver( labyrinth, 0, 0, 4, 5 );
        if ( ls.hasSolution() ) {
            System.out.println( ls );
        }
        
    }
    
}
