package aesd.algorithms.substrings;

import java.math.BigInteger;
import java.util.Random;

/**
 * Implementação do algoritmo de Rabin-Karp para busca de substrings.
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 
 * 4. ed. Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RabinKarp {

    // o padrão (necessário apenas para a versão Las Vegas)
    private String pat;
    
    // hash do padrão
    private long patHash;
    
    // comprimento do padrão
    private int m;
    
    // um primo grande, mas pequeno o bastante para evitar overflow
    private long q;
    
    // a raiz/base
    private int R;
    
    // R^(M-1) % Q
    private long RM;
    
    public RabinKarp( char[] pattern, int R ) {
        this.pat = String.valueOf( pattern );
        this.R = R;
        throw new UnsupportedOperationException( "Operation not supported yet" );
    }
    
    public RabinKarp( String pat ) {
        
        // salva o padrão (necessário apenas para a versão Las Vegas)
        this.pat = pat;
        
        R = 256;
        m = pat.length();
        q = longRandomPrime();

        // pré-computa R^(M-1) % Q para usar na remoção do dígito inicial
        RM = 1;
        
        for ( int i = 1; i <= m - 1; i++ ) {
            RM = ( R * RM ) % q;
        }
        
        patHash = hash( pat, m );
        
    }

    // cálculo do hash para a key[0..m-1].
    private long hash( String key, int m ) {
        
        long h = 0;
        
        for ( int j = 0; j < m; j++ ) {
            h = ( R * h + key.charAt( j ) ) % q;
        }
        
        return h;
        
    }

    // versão Las Vegas: pat[] casa com txt[i..i-m+1]?
    private boolean check( String txt, int i ) {
        
        for ( int j = 0; j < m; j++ ) {
            if ( pat.charAt( j ) != txt.charAt( i + j ) ) {
                return false;
            }
        }
        
        return true;
        
    }

    // versçai Monte Carlo: sempre retorna verdadeiro
    // private boolean check( int i ) {
    //    return true;
    //}
    
    public int search( String txt ) {
        
        int n = txt.length();
        
        if ( n < m ) {
            return n;
        }
        
        long txtHash = hash( txt, m );

        // verifica o casamento no offset 0
        if ( ( patHash == txtHash ) && check( txt, 0 ) ) {
            return 0;
        }

        // verifica o casamento do hash. se houve casamento, executa o casamento exato
        for ( int i = m; i < n; i++ ) {
            
            // remove o dígito inicial, insere o dígito final e verifica o casamento
            txtHash = ( txtHash + q - RM * txt.charAt( i - m ) % q ) % q;
            txtHash = ( txtHash * R + txt.charAt( i ) ) % q;

            // casamento
            int offset = i - m + 1;
            
            if ( ( patHash == txtHash ) && check( txt, offset ) ) {
                return offset;
            }
            
        }

        // não houve casamento
        return n;
        
    }

    // um número primo inteiro randômico de 31 bits
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime( 31, new Random() );
        return prime.longValue();
    }

}
