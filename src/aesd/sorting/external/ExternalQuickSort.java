package aesd.sorting.external;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * QuickSort externo.
 * 
 * AJUSTAR ABAIXO
 * 
 * In-place? Sim
 * Estável? Não
 *
 * Complexidade:
 *       Pior caso: O(n^2)
 *      Caso médio: O(n log n)
 *     Melhor caso: O(n log n)
 * 
 * Implementação baseada na obra: ZIVIANI, N. Projeto de Algoritmos com
 * Implementações em Java e C++. São Paulo: Cengage, 2006. 644 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class ExternalQuickSort {

    public static void sort( String fileName, int numberOfRegisters ) throws IOException, Exception {
        sort( fileName, 3, numberOfRegisters );
    }
    
    public static void sort( String fileName, int areaSize, int numberOfRegisters ) throws IOException, Exception {
        ExternalQuickSort externalQS = new ExternalQuickSort( fileName, 3 );
        externalQS.sort( 1, numberOfRegisters );
        externalQS.closeFiles();
    }
    
    /*
     * Legenda:
     *     ls => rs: read superior
     *     li => ri: read inferior
     *     ws => ws: write superior
     *     wi => wi: write inferior
     */
    private static class PartitionLimits {
        int i;
        int j;
    }
    
    private RandomAccessFile rInfFile;
    private RandomAccessFile wInfFile;
    private RandomAccessFile rwSupFile;
    
    private boolean readSuperior;
    private Register lastRead;
    private Area area;
    private int areaSize;
    
    private ExternalQuickSort( String fileName, int areaSize ) throws FileNotFoundException {
        rInfFile = new RandomAccessFile( fileName, "rws" );
        wInfFile = new RandomAccessFile( fileName, "rws" );
        rwSupFile = new RandomAccessFile( fileName, "rws" );
        this.areaSize = areaSize;
    }

    private void sort( int left, int right ) throws Exception {
        
        if ( right - left < 1 ) {
            return;
        }
        
        PartitionLimits p = partition( left, right );
        
        // ordena primeiro o menor subarquivo
        if ( p.i - left < right - p.j ) {
            sort( left, p.i );
            sort( p.j, right );
        } else {
            sort( p.j, right );
            sort( left, p.i );
        }
        
    }
    
    private PartitionLimits partition( int left, int right ) throws Exception {
        
        // ponteiros
        int rs = right;
        int ri = left;
        int ws = right;
        int wi = left;
        int areaNumber = 0;
        
        // registros para marcação
        Register inferiorLimit = new Register( Integer.MIN_VALUE );
        Register superiorLimit = new Register( Integer.MAX_VALUE );
        
        PartitionLimits p = new PartitionLimits();
        p.i = left - 1;
        p.j = right + 1;
        
        // a leitura deve ser a partir do fim?
        readSuperior = true;
        area = new Area( areaSize );
        
        // posiciona os file-pointers
        rInfFile.seek( ( ri - 1 ) * Register.getSize() );
        wInfFile.seek( ( wi - 1 ) * Register.getSize() );
        
        while ( ri <= rs ) {
            
            if ( areaNumber < areaSize - 1 ) {
                
                if ( readSuperior ) {
                    readSuperior( rs );
                    rs--;
                } else {
                    readInferior();
                    ri++;
                }
                
                areaNumber = addArea();
                
            } else {
                
                if ( rs == ws ) {
                    readSuperior( rs );
                    rs--;
                } else if ( ri == wi ) {
                    readInferior();
                    ri++;
                } else if ( readSuperior ) {
                    readSuperior( rs );
                    rs--;
                } else {
                    readInferior();
                    ri++ ;
                }
                
                if ( lastRead.compareTo( superiorLimit ) > 0 ) {
                    p.j = ws;
                    writeSuperior( ws );
                    ws--;
                } else if ( lastRead.compareTo( inferiorLimit ) < 0 ) {
                    p.i = wi;
                    writeInferior();
                    wi++;
                } else {
                    addArea();
                    if ( wi - left < right - ws ) {
                        areaNumber = removeFirst();
                        inferiorLimit = lastRead;
                        writeInferior();
                        wi++;
                    } else {
                        areaNumber = removeLast();
                        superiorLimit = lastRead;
                        writeSuperior( ws );
                        ws--;
                    }
                }
                
            }
            
        }
        
        while ( wi <= ws ) {
            removeFirst();
            writeInferior();
            wi++;
        }
        
        return p;
        
    }

    private void readSuperior( int rs ) throws IOException {
        rwSupFile.seek( ( rs - 1 ) * Register.getSize() );
        lastRead = Register.read( rwSupFile );
        readSuperior = false;
    }
    
    private void readInferior() throws IOException {
        lastRead = Register.read( rInfFile );
        readSuperior = true;
    }
    
    private void writeSuperior( int ws ) throws Exception {
        rwSupFile.seek( ( ws - 1 ) * Register.getSize() );
        Register.write( rwSupFile, lastRead );
    }
    
    private void writeInferior() throws IOException {
        Register.write( wInfFile, lastRead );
    }

    private int removeLast() throws Exception {
        lastRead = (Register) area.removeLast();
        return area.getNumOccupiedCells();
    }

    private int removeFirst() throws Exception {
        lastRead = (Register) area.removeFirst();
        return area.getNumOccupiedCells();
    }
    
    private int addArea() throws Exception {
        area.addRegister( lastRead );
        return area.getNumOccupiedCells();
    }
    
    private void closeFiles() throws Exception {
        wInfFile.close();
        rInfFile.close();
        rwSupFile.close();
    }
    
}
