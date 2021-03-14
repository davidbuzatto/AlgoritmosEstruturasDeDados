/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.nonlinear.symtable.tests;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.ds.implementations.nonlinear.symtable.AVLTree;
import aesd.esdc4.ds.implementations.linear.ResizingArrayList;
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
        
        AVLTree<Character, String> aavl = new AVLTree<>();
        
        aavl.put( 'H', "Helena" );
        System.out.println( aavl );
        aavl.put( 'I', "Ingrid" );
        System.out.println( aavl );
        aavl.put( 'J', "João" );
        System.out.println( aavl );
        aavl.put( 'B', "Brendo" );
        System.out.println( aavl );
        aavl.put( 'A', "Aurora" );
        System.out.println( aavl );
        aavl.put( 'E', "Ernesto" );
        System.out.println( aavl );
        aavl.put( 'C', "Camila" );
        System.out.println( aavl );
        aavl.put( 'F', "Fernanda" );
        System.out.println( aavl );
        aavl.put( 'D', "David" );
        System.out.println( aavl );
        aavl.put( 'G', "Giovana" );
        System.out.println( aavl );
        aavl.put( 'K', "Karen" );
        System.out.println( aavl );
        aavl.put( 'L', "Leonardo" );
        System.out.println( aavl );
        
        System.out.println( "Dados da árvore através do iterador:" );
        for ( BinaryTree.Entry<Character, String> e : aavl ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "----- Percursos -----" );
        System.out.print( "Pré-Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse(TraversalTypes.PREORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse(TraversalTypes.INORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse(TraversalTypes.POSTORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível: " );
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse( TraversalTypes.LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pré-Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse(TraversalTypes.INVERSE_PREORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse(TraversalTypes.INVERSE_INORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse(TraversalTypes.INVERSE_POSTORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse( TraversalTypes.INVERSE_LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<BinaryTree.Entry<Character, String>> elementos = new ResizingArrayList<>();
        for ( BinaryTree.Entry<Character, String> e : aavl.traverse(TraversalTypes.INORDER ) ) {
            elementos.add( e );
        }
        elementos.add(new BinaryTree.Entry<>( 'S', "Snoopy" ) );
        elementos.add(new BinaryTree.Entry<>( 'P', "Papai Noel" ) );
        elementos.add(new BinaryTree.Entry<>( 'M', "Mario" ) );
        Utils.shuffle( elementos );
        for ( BinaryTree.Entry<Character, String> e : elementos ) {
            System.out.printf( "%c está na árvore? => %s\n", e.getKey(),
                    aavl.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( aavl );
        for ( BinaryTree.Entry<Character, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %c...\n", e.getKey() );
            aavl.delete( e.getKey() );
            System.out.println( aavl );
        }
        
    }
    
}
