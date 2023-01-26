package aesd.algorithms.dp;

/**
 * Um resolvedor do problema da Subcadeia Comum Máxima (Longest Common
 * Substring Problem).
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LongestCommonSubstringProblem {
    
    // tabela pd
    private int c[][];
    
    // caminhos após o processsamento
    // d: diagonal
    // l: left
    // u: up
    private String paths[][];
    
    private String string1;
    private String string2;
    
    // solução
    private String solution;
    
    /**
     * Cria uma instância do resolvedor do pronlema da subcadeia comum máxima
     * e resolve o problema para os dados passados.
     * 
     * @param string1 Uma string que será comparada
     * @param string2 A outra string que será comparada
     */
    public LongestCommonSubstringProblem( String string1, String string2 ) {
        
        this.string1 = string1;
        this.string2 = string2;
        
        solution = "";
        solve( string1, string2 );
        
    }
    
    private void solve( String string1, String string2 ) {
        
        /*
         * c[i][j] = 0,                                           se i = 0 ou j = 0
         * c[i][j] = c[i-1][j-1] + 1,                             se i e j > 0 e string1[i] = string2[j]
         * c[i][j] = max( z[k-1][d], z[k-1][d-w[k-1]] + v[k-1]],  se i e j > 0 e string1[i] != string2[j]
         */
        
        c = new int[string1.length()+1][string2.length()+1];
        paths = new String[string1.length()+1][string2.length()+1];
        
        for ( int i = 1; i < c.length; i++ ) {
            for ( int j = 1; j < c[i].length; j++ ) {
                if ( string1.charAt( i-1 ) == string2.charAt( j-1 ) ) {
                    c[i][j] = c[i-1][j-1] + 1;
                    paths[i][j] = "d ";
                } else {
                    
                    int max;
                    
                    if ( c[i-1][j] < c[i][j-1] ) {
                        max = c[i][j-1];
                        paths[i][j] = "l ";
                    } else if ( c[i-1][j] > c[i][j-1] ) {
                        max = c[i-1][j];
                        paths[i][j] = "u ";
                    } else {
                        max = c[i-1][j];
                        paths[i][j] = "lu";
                    }
                    
                    c[i][j] = max;
                    
                }
            }
        }
        
        processPaths();
        
    }

    private void processPaths() {
        
        int i = paths.length - 1;
        int j = paths[0].length - 1;
        
        while ( true ) {
            
            String curr = paths[i][j];
            
            if ( curr.contains( "d" ) ) {
                solution += string1.charAt( i-1 );
                i--;
                j--;
            } else if ( curr.contains( "l" ) ) {
                j--;
            } else {
                i--;
            }
            
            if ( i == 0 || j == 0 ) {
                break;
            }
            
        }
        
        solution = new StringBuilder( solution ).reverse().toString();
        
    }
    
    public String getSolution() {
        return solution;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder( "             " );
        
        for ( char c : string2.toCharArray() ) {
            sb.append( c ).append( "        " );
        }
        
        for ( int i = 0; i < c.length; i++ ) {
            sb.append( "\n" );
            if ( i > 0 ) {
                sb.append( string1.charAt( i-1 ) );
            } else {
                sb.append( " " );
            }
            for ( int j = 0; j < c[i].length; j++ ) {
                sb.append( String.format( "%4d(%s)", c[i][j], paths[i][j] != null ? paths[i][j] : "  " ) ).append( " " );
            }
        }
        
        return sb.toString();
        
    }
    
}
