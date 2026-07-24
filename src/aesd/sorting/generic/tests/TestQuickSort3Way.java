package aesd.sorting.generic.tests;

import aesd.sorting.generic.QuickSort3Way;
import aesd.sorting.utils.SortingUtils;
import java.util.Arrays;

/**
 * Teste de uso do algoritmo de ordenação Quick Sort 3-Way (versão genérica).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestQuickSort3Way {

    public static void main( String[] args ) {

        // array com bastante repetição de valores, para evidenciar a
        // vantagem do particionamento em 3 vias
        Integer[] array = { 5, 3, 5, 1, 9, 3, 5, 1, 9, 3 };

        // embaralha antes de ordenar, já que esta implementação sempre usa
        // o primeiro elemento como pivô (ver Javadoc de QuickSort3Way)
        SortingUtils.shuffle( array );

        System.out.println( "Antes de ordenar: " + Arrays.toString( array ) );
        QuickSort3Way.sort( array );
        System.out.println( "Depois de ordenar:" + Arrays.toString( array ) );

    }

}
