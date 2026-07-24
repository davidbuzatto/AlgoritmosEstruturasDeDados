package aesd.algorithms.backtracking;

/**
 * Um resolvedor de labirintos que usa backtracking.
 *
 * A partir da posição de origem, tenta se mover em uma ordem fixa de
 * direções (direita, baixo, esquerda, cima); cada posição visitada é
 * marcada como parede (labyrinth[][] = true) para nunca ser revisitada,
 * o que evita loops infinitos e faz o papel do "desfazer" do backtracking
 * clássico (não é preciso desmarcar ao retroceder, pois uma posição já
 * visitada nunca leva a uma solução nova). Se uma direção não leva ao
 * alvo, a chamada recursiva retorna falso e a próxima direção é testada;
 * se nenhuma leva ao alvo, a posição não faz parte de nenhum caminho
 * possível. O caminho encontrado (se houver) fica registrado em output[][]
 * com a direção tomada em cada célula. Complexidade O(linhas * colunas) no
 * pior caso, já que cada posição é visitada no máximo uma vez.
 *
 * @author Prof. Dr. David Buzatto
 */
public class LabyrinthSolver {

    private boolean[][] labyrinth;
    private char[][] output;

    private boolean hasSolution;

    /**
     * Cria uma instância do resolvedor de labirintos e resolve o problema
     * para os dados passados.
     *
     * @param labyrinth O array de duas dimensões do labirinto (true = parede,
     * false = livre).
     * @param sourceLine A linha da posição de origem.
     * @param sourceColumn A coluna da posição de origem.
     * @param targetLine A linha da posição de destino.
     * @param targetColumn A coluna da posição de destino.
     */
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
    
    // tenta alcançar o destino a partir da posição atual, testando as
    // direções em ordem fixa (direita, baixo, esquerda, cima) via backtracking
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
    
    // a posição está dentro dos limites do labirinto e livre (não é parede
    // nem já foi visitada)?
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
