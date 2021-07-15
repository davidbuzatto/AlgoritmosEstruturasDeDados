/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.ds.implementations.nonlinear.symtable.tests;

import aesd.algorithms.tree.TraversalTypes;
import aesd.ds.implementations.nonlinear.symtable.BinarySearchTree;
import aesd.ds.implementations.linear.ResizingArrayList;
import aesd.ds.interfaces.List;
import aesd.ds.utils.Utils;
import aesd.ds.interfaces.BinaryTree;

/**
 * Teste de uso da árvore de busca binária fundamental.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestBinarySearchTree {
    
    public static void main( String[] args ) {
        
        BinarySearchTree<Character, String> abb = new BinarySearchTree<>();
        
        abb.put( 'H', "Helena" );
        System.out.println( abb );
        abb.put( 'I', "Ingrid" );
        System.out.println( abb );
        abb.put( 'J', "João" );
        System.out.println( abb );
        abb.put( 'B', "Brendo" );
        System.out.println( abb );
        abb.put( 'A', "Aurora" );
        System.out.println( abb );
        abb.put( 'E', "Ernesto" );
        System.out.println( abb );
        abb.put( 'C', "Camila" );
        System.out.println( abb );
        abb.put( 'F', "Fernanda" );
        System.out.println( abb );
        abb.put( 'D', "David" );
        System.out.println( abb );
        abb.put( 'G', "Giovana" );
        System.out.println( abb );
        abb.put( 'K', "Karen" );
        System.out.println( abb );
        abb.put( 'L', "Leonardo" );
        System.out.println( abb );
        
        System.out.println( "Dados da árvore através do iterador:" );
        for ( BinaryTree.Entry<Character, String> e : abb ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "----- Percursos -----" );
        System.out.print( "Pré-Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : abb.traverse(TraversalTypes.PREORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : abb.traverse(TraversalTypes.INORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : abb.traverse(TraversalTypes.POSTORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível: " );
        for ( BinaryTree.Entry<Character, String> e : abb.traverse( TraversalTypes.LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pré-Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : abb.traverse(TraversalTypes.INVERSE_PREORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : abb.traverse(TraversalTypes.INVERSE_INORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : abb.traverse(TraversalTypes.INVERSE_POSTORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : abb.traverse( TraversalTypes.INVERSE_LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<BinaryTree.Entry<Character, String>> elementos = new ResizingArrayList<>();
        for ( BinaryTree.Entry<Character, String> e : abb.traverse(TraversalTypes.INORDER ) ) {
            elementos.add( e );
        }
        elementos.add(new BinaryTree.Entry<>( 'S', "Snoopy" ) );
        elementos.add(new BinaryTree.Entry<>( 'P', "Papai Noel" ) );
        elementos.add(new BinaryTree.Entry<>( 'M', "Mario" ) );
        Utils.shuffle( elementos );
        for ( BinaryTree.Entry<Character, String> e : elementos ) {
            System.out.printf( "%c está na árvore? => %s\n", e.getKey(),
                    abb.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( abb );
        for ( BinaryTree.Entry<Character, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %c...\n", e.getKey() );
            abb.delete( e.getKey() );
            System.out.println( abb );
        }
        
    }
    
}
