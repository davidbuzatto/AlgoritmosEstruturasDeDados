package aesd.sorting.external;

/**
 * Uma área de registros.
 * 
 * Implementação baseada na obra: ZIVIANI, N. Projeto de Algoritmos com
 * Implementações em Java e C++. São Paulo: Cengage, 2006. 644 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Area {

    private static class Cell {
        Register register;
        int next;
        int previous;
    }
    
    private Cell registers[];
    
    private int availableCells;
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
