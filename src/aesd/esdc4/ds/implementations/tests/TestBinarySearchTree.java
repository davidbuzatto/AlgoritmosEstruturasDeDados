/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.tests;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.ds.implementations.BinarySearchTree;
import aesd.esdc4.ds.implementations.ResizingArrayList;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.utils.Utils;
import aesd.esdc4.ds.interfaces.BinaryTree;

/**
 * Teste de uso da árvore de busca binária fundamental.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestBinarySearchTree {
    
    public static void main( String[] args ) {
        
        BinarySearchTree<Integer, String> abb = new BinarySearchTree<>();
        
        abb.put( 6, "João" );
        System.out.println( abb );
        abb.put( 8, "Maria" );
        System.out.println( abb );
        abb.put( 7, "David" );
        System.out.println( abb );
        abb.put( 4, "Fernanda" );
        System.out.println( abb );
        abb.put( 5, "Aurora" );
        System.out.println( abb );
        abb.put( 9, "Marcelo" );
        System.out.println( abb );
        abb.put( 3, "Ronaldinho" );
        System.out.println( abb );
        abb.put( 9, "Matilda" );
        System.out.println( abb );
        abb.put( 3, null );
        System.out.println( abb );
        
        System.out.println( "Dados da árvore através do iterador:" );
        for ( BinaryTree.Entry<Integer, String> e : abb ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "----- Percursos -----" );
        System.out.print( "Pré-Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse(TraversalTypes.PREORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse(TraversalTypes.INORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse(TraversalTypes.POSTORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível: " );
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse( TraversalTypes.LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pré-Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse(TraversalTypes.INVERSE_PREORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse(TraversalTypes.INVERSE_INORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse(TraversalTypes.INVERSE_POSTORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse( TraversalTypes.INVERSE_LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<BinaryTree.Entry<Integer, String>> elementos = new ResizingArrayList<>();
        for ( BinaryTree.Entry<Integer, String> e : abb.traverse(TraversalTypes.INORDER ) ) {
            elementos.add( e );
        }
        elementos.add( new BinaryTree.Entry<>( 15, "Snoopy" ) );
        elementos.add( new BinaryTree.Entry<>( 19, "Papai Noel" ) );
        elementos.add( new BinaryTree.Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( BinaryTree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na árvore? => %s\n", e.getKey(),
                    abb.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( abb );
        for ( BinaryTree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            abb.delete( e.getKey() );
            System.out.println( abb );
        }
        
    }
    
}
