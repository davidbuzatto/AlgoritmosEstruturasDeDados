package aesd.algorithms.backtracking;

/**
 * Um resolvedor de labirintos que usa backtracking.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LabyrinthSolver {
    
    private boolean[][] labyrinth;
    private char[][] output;
    
    private boolean hasSolution;
    
    public LabyrinthSolver( boolean[][] labyrinth, int sourceLine, int sourceColumn, int targetLine, int targetColumn ) {
        
        this.labyrinth = labyrinth;
        
        if ( !validPosition( sourceLine, sourceColumn ) ) {
            throw new IllegalArgumentException( "Invalid source position." );
        }
        
        if ( !validPosition( targetLine, targetColumn ) ) {
            throw new IllegalArgumentException( "Invalid target position." );
        }
        
        output = new char[labyrinth.length][labyrinth[0].length];
        for ( int i = 0; i < output.length; i++ ) {
            for ( int j = 0; j < output[i].length; j++ ) {
                if ( labyrinth[i][j] == true ) {
                    output[i][j] = 'x';
                } else {
                    output[i][j] = ' ';
                }
            }
        }
        
        hasSolution = solve( sourceLine, sourceColumn, targetLine, targetColumn );
        output[sourceLine][sourceColumn] = 's';
        
    }
    
    private boolean solve( int sourceLine, int sourceColumn, int targetLine, int targetColumn ) {
        
        if ( validPosition( sourceLine, sourceColumn ) ) {
            
            labyrinth[sourceLine][sourceColumn] = true;
            
            //System.out.printf( "(%d, %d) ", sourceLine, sourceColumn );
            
            if ( sourceLine == targetLine && sourceColumn == targetColumn ) {
                output[sourceLine][sourceColumn] = 't';
                return true;
            }
            
            // direita
            if ( solve( sourceLine, sourceColumn + 1, targetLine, targetColumn ) ) {
                output[sourceLine][sourceColumn] = 'd';
                return true;
            }
            
            // baixo
            if ( solve( sourceLine + 1, sourceColumn, targetLine, targetColumn ) ) {
                output[sourceLine][sourceColumn] = 'b';
                return true;
            }

            // esquerda
            if ( solve( sourceLine, sourceColumn - 1, targetLine, targetColumn ) ) {
                output[sourceLine][sourceColumn] = 'e';
                return true;
            }

            // cima
            if ( solve( sourceLine - 1, sourceColumn, targetLine, targetColumn ) ) {
                output[sourceLine][sourceColumn] = 'c';
                return true;
            }
            
        }
        
        return false;
        
    }
    
    private boolean validPosition( int line, int column ) {
        return line >= 0 &&
               line < labyrinth.length &&
               column >= 0 &&
               column < labyrinth[line].length &&
               !labyrinth[line][column];
    }

    public boolean[][] getLabyrinth() {
        return labyrinth;
    }

    public char[][] getOutput() {
        return output;
    }

    public boolean hasSolution() {
        return hasSolution;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        for ( int i = 0; i < output.length; i++ ) {
            if ( i != 0 ) {
                sb.append( "\n" );
            }
            for ( int j = 0; j < output[i].length; j++ ) {
                sb.append( output[i][j] );
            }
        }
        
        return sb.toString();
        
    }
    
}
