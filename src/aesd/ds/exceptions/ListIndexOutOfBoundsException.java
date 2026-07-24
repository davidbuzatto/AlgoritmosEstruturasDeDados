package aesd.ds.exceptions;

/**
 * Exceção para indicar um índice inválido de uma lista.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ListIndexOutOfBoundsException extends RuntimeException {

    /**
     * Cria a exceção sem uma mensagem específica.
     */
    public ListIndexOutOfBoundsException() {
    }

    /**
     * Cria a exceção com uma mensagem customizada. Esta é a única exceção
     * do pacote que possui construtor com mensagem própria, as demais
     * utilizam apenas o construtor padrão implícito.
     *
     * @param message Mensagem que descreve o motivo da exceção.
     */
    public ListIndexOutOfBoundsException( String message ) {
        super( message );
    }

}
