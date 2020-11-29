/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.tests;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.ds.implementations.AVLTree;
import aesd.esdc4.ds.implementations.ResizingArrayList;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.utils.Utils;
import aesd.esdc4.ds.interfaces.BinaryTree;

/**
 * Teste de uso da árvore AVL.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestAVLTree {
    
    public static void main( String[] args ) {
        
        AVLTree<Integer, String> aavl = new AVLTree<>();
        
        aavl.put( 6, "João" );
        System.out.println( aavl );
        aavl.put( 8, "Maria" );
        System.out.println( aavl );
        aavl.put( 7, "David" );
        System.out.println( aavl );
        aavl.put( 4, "Fernanda" );
        System.out.println( aavl );
        aavl.put( 5, "Aurora" );
        System.out.println( aavl );
        aavl.put( 9, "Marcelo" );
        System.out.println( aavl );
        aavl.put( 3, "Ronaldinho" );
        System.out.println( aavl );
        aavl.put( 9, "Matilda" );
        System.out.println( aavl );
        aavl.put( 3, null );
        System.out.println( aavl );
        aavl.put( 3, "Ronaldinho" );
        System.out.println( aavl );
        
        System.out.println( "Dados da árvore através do iterador:" );
        for ( BinaryTree.Entry<Integer, String> e : aavl ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "----- Percursos -----" );
        System.out.print( "Pré-Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.PRE_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.IN_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.POST_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível: " );
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pré-Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.INVERSE_PRE_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.INVERSE_IN_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.INVERSE_POST_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.INVERSE_LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<BinaryTree.Entry<Integer, String>> elementos = new ResizingArrayList<>();
        for ( BinaryTree.Entry<Integer, String> e : aavl.traverse( TraversalTypes.IN_ORDER ) ) {
            elementos.add( e );
        }
        elementos.add(new BinaryTree.Entry<>( 15, "Snoopy" ) );
        elementos.add(new BinaryTree.Entry<>( 19, "Papai Noel" ) );
        elementos.add(new BinaryTree.Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( BinaryTree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na árvore? => %s\n", e.getKey(),
                    aavl.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( aavl );
        for ( BinaryTree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            aavl.delete( e.getKey() );
            System.out.println( aavl );
        }
        
    }
    
}
