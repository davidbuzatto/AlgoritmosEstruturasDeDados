/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.tests;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.algorithms.tree.TreeTraversals;
import aesd.esdc4.ds.implementations.BSTree;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.interfaces.Tree;
import aesd.esdc4.ds.utils.Utils;

/**
 * Teste de uso da árvore de busca binária fundamental.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestBSTree {
    
    public static void main( String[] args ) {
        
        Tree<Integer, String> abb = new BSTree<>();
        
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
        
        System.out.println( "Dados da árvore através do iterador:" );
        for ( Tree.Entry<Integer, String> e : abb ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "----- Percursos -----" );
        System.out.print( "Pré-Ordem: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( abb, TraversalTypes.PRE_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( abb, TraversalTypes.IN_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( abb, TraversalTypes.POST_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( abb, TraversalTypes.LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pré-Ordem Inverso: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( abb, TraversalTypes.INVERSE_PRE_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem Inverso: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( abb, TraversalTypes.INVERSE_IN_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem Inverso: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( abb, TraversalTypes.INVERSE_POST_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível Inverso: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( abb, TraversalTypes.INVERSE_LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<Tree.Entry<Integer, String>> elementos = (List<Tree.Entry<Integer, String>>) TreeTraversals.traverse( abb, TraversalTypes.IN_ORDER );
        elementos.add( new Tree.Entry<>( 15, "Snoopy" ) );
        elementos.add( new Tree.Entry<>( 19, "Papai Noel" ) );
        elementos.add( new Tree.Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( Tree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na árvore? => %s\n", e.getKey(),
                    abb.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( abb );
        for ( Tree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            abb.delete( e.getKey() );
            System.out.println( abb );
        }
        
    }
    
}
