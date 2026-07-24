package aesd.algorithms.dp;

/**
 * Um resolvedor do problema da Subcadeia Comum Máxima (Longest Common
 * Substring Problem) que usa programação dinâmica.
 *
 * Diferente da Subsequência Comum Máxima (LongestCommonSubsequenceProblem),
 * aqui os caracteres da subcadeia resultante precisam ser contíguos em
 * ambas as strings originais.
 *
 * @author Prof. Dr. David Buzatto
 */
public class LongestCommonSubstringProblem {

    // tabela pd
    private int c[][];

    private String string1;
    private String string2;

    // solução
    private String solution;

    /**
     * Cria uma instância do resolvedor do problema da subcadeia comum máxima
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
         * c[i][j] = 0,                    se i = 0 ou j = 0
         * c[i][j] = c[i-1][j-1] + 1,      se i e j > 0 e string1[i] = string2[j]
         * c[i][j] = 0,                    se i e j > 0 e string1[i] != string2[j]
         *
         * diferente da subsequência comum máxima, aqui não há herança do
         * máximo entre c[i-1][j] e c[i][j-1] em caso de incompatibilidade:
         * qualquer incompatibilidade quebra a contiguidade, zerando a
         * contagem. a resposta é o maior valor de toda a tabela (e não,
         * necessariamente, c[n][m]).
         */

        c = new int[string1.length()+1][string2.length()+1];

        // tamanho da maior subcadeia comum encontrada até agora
        int maxLength = 0;

        // posição, em string1, logo após o fim dessa subcadeia
        int maxEndIndex = 0;

        for ( int i = 1; i < c.length; i++ ) {
            for ( int j = 1; j < c[i].length; j++ ) {
                if ( string1.charAt( i-1 ) == string2.charAt( j-1 ) ) {

                    c[i][j] = c[i-1][j-1] + 1;

                    if ( c[i][j] > maxLength ) {
                        maxLength = c[i][j];
                        maxEndIndex = i;
                    }

                } else {
                    c[i][j] = 0;
                }
            }
        }

        solution = string1.substring( maxEndIndex - maxLength, maxEndIndex );

    }

    public String getSolution() {
        return solution;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder( "          " );

        for ( char c : string2.toCharArray() ) {
            sb.append( c ).append( "   " );
        }

        for ( int i = 0; i < c.length; i++ ) {
            sb.append( "\n" );
            if ( i > 0 ) {
                sb.append( string1.charAt( i-1 ) );
            } else {
                sb.append( " " );
            }
            for ( int j = 0; j < c[i].length; j++ ) {
                sb.append( String.format( "%4d", c[i][j] ) ).append( " " );
            }
        }

        return sb.toString();

    }

}
