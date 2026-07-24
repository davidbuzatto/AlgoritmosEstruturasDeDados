package aesd.sorting.external;

/**
 * Uma área de registros temporária em memória. Serve de região de transferência
 * dos registros que estão sendo ordenados no arquivo fonte. Tem funcionamento
 * análogo à uma lista.
 * 
 * Implementação baseada na obra: ZIVIANI, N. Projeto de Algoritmos com
 * Implementações em Java e C++. São Paulo: Cengage, 2006. 644 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Area {

    // os campos next/previous de Cell têm dupla função, dependendo se a
    // célula está livre ou ocupada: enquanto livre, next encadeia a lista
    // de células disponíveis (a partir de availableCells, montada no
    // construtor); quando ocupada por addRegister(), esses mesmos campos
    // passam a encadear a lista dupla ordenada de registros (a partir de
    // first/last)
    private static class Cell {
        Register register;
        int next;
        int previous;
    }

    private Cell registers[];

    // índice da primeira célula livre (topo da lista de células disponíveis)
    private int availableCells;

    // índices do primeiro e do último registro da lista ordenada ocupada
    private int first;
    private int last;

    private int numOccupiedCells;

    /**
     * Cria uma área vazia de tamanho 1000.
     */
    public Area() {
        this( 1000 );
    }

    /**
     * Cria uma área vazia com tamanho especificado.
     * 
     * @param size Tamanho da área.
     */
    public Area( int size ) {
        
        registers = new Cell[size];
        
        numOccupiedCells = 0;
        first = -1;
        last = -1;
        availableCells = 0;
        
        for ( int i = 0; i < registers.length; i++ ) {
            registers[i] = new Cell();
            registers[i].previous = -1;
            registers[i].next = i + 1;
        }
        
    }

    /**
     * Insere um registro na área, mantendo-a ordenada por meio de uma
     * varredura linear O(n) que localiza a posição correta na lista
     * ligada. É essa ordenação mantida a cada inserção que permite a
     * removeFirst()/removeLast() devolverem sempre o menor/maior registro
     * em O(1).
     *
     * @param register O registro a ser inserido.
     * @throws Exception se a área já estiver cheia.
     */
    public void addRegister( Register register ) throws Exception {
        
        if ( numOccupiedCells == registers.length ) {
            throw new Exception( "Error: insertion attempt in full area" );
        }
        
        int avaiable = availableCells;
        
        availableCells = registers[availableCells].next;
        registers[avaiable].register = register;
        numOccupiedCells++;
        
        // inserção do primeiro register
        if ( numOccupiedCells == 1 ) {
            first = avaiable;
            last = first;
            registers[first].next = -1;
            registers[first].previous = -1;
            return;
        }
        
        int pos = first;
        
        // inserção realizada na primeira posição
        if ( register.compareTo( registers[pos].register ) < 0 ) {
            registers[avaiable].previous = -1;
            registers[avaiable].next = pos;
            registers[pos].previous = avaiable;
            first = avaiable;
            return;
        }
        
        int insertionIndex = registers[pos].next;
        
        while ( insertionIndex != -1
                && registers[insertionIndex].register.compareTo( register ) < 0 ) {
            pos = insertionIndex;
            insertionIndex = registers[pos].next;
        }
        
        // inserção realizada na última posição
        if ( insertionIndex == -1 ) {
            registers[avaiable].previous = pos;
            registers[avaiable].next = -1;
            registers[pos].next = avaiable;
            last = avaiable;
            return;
        }
        
        // inserção realizada no meio
        registers[avaiable].previous = pos;
        registers[avaiable].next = registers[pos].next;
        registers[pos].next = avaiable;
        pos = registers[avaiable].next;
        registers[pos].previous = avaiable;
        
    }

    /**
     * Remove e retorna o menor registro da área (o primeiro da lista
     * ordenada), em O(1).
     *
     * @return O menor registro da área.
     * @throws Exception se a área estiver vazia.
     */
    public Register removeFirst() throws Exception {
        
        if ( numOccupiedCells == 0 ) {
            throw new Exception( "Error: empty area" );
        }
        
        Register register = registers[first].register;
        int next = registers[first].next;
        
        registers[first].next = availableCells;
        availableCells = first;
        first = next;
        
        if ( ( first >= 0 ) && ( first < registers.length ) ) {
            registers[first].previous = -1;
        }
        
        numOccupiedCells--;
        return register;
        
    }

    /**
     * Remove e retorna o maior registro da área (o último da lista
     * ordenada), em O(1).
     *
     * @return O maior registro da área.
     * @throws Exception se a área estiver vazia.
     */
    public Register removeLast() throws Exception {
        
        if ( numOccupiedCells == 0 ) {
            throw new Exception( "Error: empty area" );
        }
        
        Register register = registers[last].register;
        int previous = registers[last].previous;
        
        registers[last].next = availableCells;
        availableCells = last;
        last = previous;
        
        if ( ( last >= 0 ) && ( last < registers.length ) ) {
            registers[last].next = -1;
        }
        
        numOccupiedCells--;
        return register;
        
    }

    
    /**
     * Retorna a quantidade de registros atualmente ocupando a área.
     *
     * @return A quantidade de células ocupadas.
     */
    public int getNumOccupiedCells() {
        return numOccupiedCells;
    }
    
    @Override
    public String toString() {
        
        int pos;
        
        if ( numOccupiedCells == 0 ) {
            return "Erro: Area vazia";
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append( "** LIST **" ).append( "\n" );
        sb.append( "Occupied Cells = " ).append( numOccupiedCells ).append( "\n" );
        pos = first;
        
        while ( pos != -1 ) {
            sb.append( registers[pos].register ).append( "\n" );
            pos = registers[pos].next;
        }
        
        return sb.toString();
        
    }
    
}
