package aesd.sorting.external;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Quick Sort externo.
 * 
 * O Quick Sort externo foi proposto por Maria Carolina Monard em 1980 em sua
 * tese de doutorado intitulada "Projeto e Análise de Algoritmos de
 * Classificação Externa baseados na Estratégia de Quicksort".
 * 
 * Ordena um arquivo A de n registros in situ, utilizando arquivos de acesso
 * randômico e tem como base o paradigma de divisão e conquista, assim como
 * sua contraparte interna. O processo de particionamento utiliza uma área
 * de armazenamento na memória interna (T).
 * 
 * In-place? Sim
 * Estável? Não
 *
 * Complexidade:
 *       Pior caso: O(n^2)
 *      Caso médio: O(n lg n)
 *     Melhor caso: O(n)
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
    
    private static class PartitionLimits {
        int i;
        int j;
    }
    
    // ponteiros para o arquivo
    private RandomAccessFile riFile;   // read inferior
    private RandomAccessFile wiFile;   // write inferior
    private RandomAccessFile rsFile;   // read superior
    private RandomAccessFile wsFile;   // write superior
    
    private boolean readSuperior;
    private Register lastRead;
    private Area area;
    private int areaSize;
    
    private ExternalQuickSort( String fileName, int areaSize ) throws FileNotFoundException {
        riFile = new RandomAccessFile( fileName, "rws" );
        wiFile = new RandomAccessFile( fileName, "rws" );
        rsFile = new RandomAccessFile( fileName, "rws" );
        wsFile = new RandomAccessFile( fileName, "rws" );
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
        int ri = left;     // read inferior
        int rs = right;    // read superior
        int wi = left;     // write inferior
        int ws = right;    // write superior
        
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
        riFile.seek( ( ri - 1 ) * Register.getSize() );
        wiFile.seek( ( wi - 1 ) * Register.getSize() );
        
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
        rsFile.seek( ( rs - 1 ) * Register.getSize() );
        lastRead = Register.read( rsFile );
        readSuperior = false;
    }
    
    private void readInferior() throws IOException {
        lastRead = Register.read( riFile );
        readSuperior = true;
    }
    
    private void writeSuperior( int ws ) throws Exception {
        wsFile.seek( ( ws - 1 ) * Register.getSize() );
        Register.write( wsFile, lastRead );
    }
    
    private void writeInferior() throws IOException {
        Register.write( wiFile, lastRead );
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
        wiFile.close();
        riFile.close();
        rsFile.close();
        wsFile.close();
    }
    
}
