package aesd.algorithms.backtracking;

import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;

/**
 * Um resolvedor de quadrados latinos que usa backtracking.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LatinSquareProblem {
    
    private char[][] latinSquare;
    private char[] symbols;
    private List<Integer> linearPositions;
    
    private int lines;
    private int columns;
    
    private boolean hasSolution;
    
    public LatinSquareProblem( char[][] latinSquare, char[] symbols ) {
        
        this.latinSquare = latinSquare;
        this.symbols = symbols;
        
        lines = latinSquare.length;
        columns = latinSquare[0].length;
        
        checkArguments();
        generateLinearPositions();
        
        // inicia na primeira posição
        solve( 0 );
        hasSolution = isSolved();
        
    }
    
    private boolean solve( int linearPosition ) {
        
        // a posição linear deve ser processada?
        if ( linearPosition < linearPositions.getSize() ) {
            
            // calcula em qual linha e coluna o algoritmo vai processar
            int line   = linearPositions.get( linearPosition ) / lines;
            int column = linearPositions.get( linearPosition ) % columns;
            int errors = 0 ;
            
            // testa cada uma das possibilidades
            for ( char c : symbols ) {

                // c é um candidato na linha e na coluna?
                if ( isLineCandidate( c, line ) && isColumnCandidate( c, column ) ) {

                    // assume-se que c é a solução no momento
                    latinSquare[line][column] = c;
                    
                    // vai processar a próxima
                    // se retornar true, quer dizer que encaixou pelo menos uma
                    // alternativa
                    if ( !solve( linearPosition + 1 ) ) {
                        // se não encontrou nenhuma alternativa, o símbolo atual
                        // está errado
                        latinSquare[line][column] = ' ';
                    }

                } else {
                    errors++;
                }

            }
            
            // caso a quantidade de erros seja igual ao tamanho
            // do array de símbolos, ou seja, nenhum símbolo serviu
            // quer dizer que a escolha anterior foi errada, então retorna falso
            if ( errors == symbols.length ) {
                return false;
            }
            
            // foi encontrado pelo menos um símbolo correto
            return true;
        
        }
        
        // chegou na última posição
        return true;
        
    }
    
    /**
     * Gera as posições lineares possíveis, ou seja, todas as que não estão
     * sendo ocupadas.
     */
    private void generateLinearPositions() {
        
        linearPositions = new ResizingArrayList<>();
        
        int linearPosition = 0;
        int k = 0;
        
        for ( int i = 0; i < latinSquare.length; i++ ) {
            for ( int j = 0; j < latinSquare[i].length; j++ ) {
                if ( latinSquare[i][j] == ' ' ) {
                    linearPositions.add( linearPosition );
                }
                linearPosition++;
            }
        }
        
    }
    
    /**
     * Verifica se foi resolvido.
     * 
     * @return se foi ou não resolvido
     */
    private boolean isSolved() {
        
        for ( int i = 0; i < latinSquare.length; i++ ) {
            for ( int j = 0; j < latinSquare[i].length; j++ ) {
                if ( latinSquare[i][j] == ' ' ) {
                    return false;
                }
            }
        }
        
        return true;
        
    }
    
    private void checkArguments() throws IllegalArgumentException {
        
        for ( int i = 0; i < lines; i++ ) {
            if ( lines != latinSquare[i].length ) {
                throw new IllegalArgumentException( "latinSquare must have the same amount of lines and columns" );
            }
        }
        
        if ( symbols.length != lines ) {
            throw new IllegalArgumentException( "symbols length must be equal to " + lines );
        }
        
    }
    
    /**
     * A letra pode ser inserida nessa linha?
     * 
     * @param letter a letra a ser testada,
     * @param line a linha em questão
     * @return se a letra pode ou não ser inserida nessa linha
     */
    private boolean isLineCandidate( char letter, int line ) {
        for ( int j = 0; j < latinSquare[line].length; j++ ) {
            if ( latinSquare[line][j] == letter ) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * A letra pode ser inserida nessa coluna?
     * 
     * @param letter a letra a ser testada,
     * @param column a coluna em questão
     * @return se a letra pode ou não ser inserida nessa coluna
     */
    private boolean isColumnCandidate( char letter, int column ) {
        for ( int i = 0; i < latinSquare.length; i++ ) {
            if ( latinSquare[i][column] == letter ) {
                return false;
            }
        }
        return true;
    }

    public boolean hasSolution() {
        return hasSolution;
    }
    
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        for ( int i = 0; i < latinSquare.length; i++ ) {
            if ( i != 0 ) {
                sb.append( "\n" );
            }
            for ( int j = 0; j < latinSquare[i].length; j++ ) {
                sb.append( latinSquare[i][j] ).append( " " );
            }
        }
        
        return sb.toString();
        
    }
    
    public String toString( String id ) {
        
        StringBuilder sb = new StringBuilder();
        
        for ( int i = 0; i < latinSquare.length; i++ ) {
            if ( i != 0 ) {
                sb.append( "\n" );
            }
            sb.append( id );
            for ( int j = 0; j < latinSquare[i].length; j++ ) {
                sb.append( latinSquare[i][j] ).append( " " );
            }
        }
        
        return sb.toString();
        
    }
    
}
