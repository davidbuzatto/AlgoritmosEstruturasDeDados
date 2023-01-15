package aesd.ds.implementations.nonlinear.uf;

/**
 * Implementação do tipo de dados union-find (disjoint-sets) com busca rápida.
 * 
 * Ordem de crescimento das operações:
 *     Union -> O(n).
 *     Find -> O(1).
 * 
 * Implementação baseada na obra: SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed.
 * Boston: Pearson Education, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class QuickFindUF extends UF {

    // identificadores dos componentes
    // id[i] = identificador do componente i
    private int[] id;

    /**
     * Cria uma estrutura union-find com n elementos, sendo que, inicialmente, 
     * cada elemento faz parte de seu próprio conjunto.
     *
     * @param n quantidade de elementos
     * @throws IllegalArgumentException se n for menor que zero
     */
    public QuickFindUF( int n ) throws IllegalArgumentException {
        
        if ( n < 0 ) {
            throw new IllegalArgumentException( "Number of elements must be nonnegative" );
        }
        
        id = new int[n];
        count = n;
        
        for ( int i = 0; i < n; i++ ) {
            id[i] = i;
        }
        
    }

    /**
     * Retorna o componente que p faz parte.
     *
     * @param p um elemento
     * @return o componente que contém p
     * @throws IllegalArgumentException se p for inválido
     */
    @Override
    public int find( int p ) throws IllegalArgumentException {
        validate( p, id );
        return id[p];
    }

    /**
     * Une o conjunto que contém p com o conjunto que contém q.
     *
     * @param p um elemento
     * @param q outro elemento
     * @throws IllegalArgumentException caso p ou q sejam inválidos
     */
    @Override
    public void union( int p, int q ) throws IllegalArgumentException {
        
        // em qual componente?
        int pID = find( p );
        int qID = find( q );
        
        // mesmo componente? não faz nada
        if ( pID == qID ) {
            return;
        }
        
        // componentes distintos
        // insere componente p no componente q
        for ( int i = 0; i < id.length; i++ ) {
            if ( id[i] == pID ) {
                id[i] = qID;
            }
        }
        
        count--;
        
    }
    
}
