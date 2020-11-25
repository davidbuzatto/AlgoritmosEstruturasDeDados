/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.tests;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.algorithms.tree.TreeTraversals;
import aesd.esdc4.ds.implementations.RBTree;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.interfaces.Tree;
import aesd.esdc4.ds.utils.Utils;

/**
 * Teste de uso da árvore de busca binária fundamental.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestRBTree {
    
    public static void main( String[] args ) {
        
        Tree<Integer, String> avp = new RBTree<>();
        
        avp.put( 6, "João" );
        System.out.println( avp );
        avp.put( 8, "Maria" );
        System.out.println( avp );
        avp.put( 7, "David" );
        System.out.println( avp );
        avp.put( 4, "Fernanda" );
        System.out.println( avp );
        avp.put( 5, "Aurora" );
        System.out.println( avp );
        avp.put( 9, "Marcelo" );
        System.out.println( avp );
        avp.put( 3, "Ronaldinho" );
        System.out.println( avp );
        
        System.out.println( "Dados da árvore através do iterador:" );
        for ( Tree.Entry<Integer, String> e : avp ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "----- Percursos -----" );
        System.out.print( "Pré-Ordem: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( avp, TraversalTypes.PRE_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( avp, TraversalTypes.IN_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( avp, TraversalTypes.POST_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( avp, TraversalTypes.LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pré-Ordem Inverso: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( avp, TraversalTypes.INVERSE_PRE_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem Inverso: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( avp, TraversalTypes.INVERSE_IN_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem Inverso: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( avp, TraversalTypes.INVERSE_POST_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível Inverso: " );
        for ( Tree.Entry<Integer, String> e : TreeTraversals.traverse( avp, TraversalTypes.INVERSE_LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<Tree.Entry<Integer, String>> elementos = (List<Tree.Entry<Integer, String>>) TreeTraversals.traverse( avp, TraversalTypes.IN_ORDER );
        elementos.add( new Tree.Entry<>( 15, "Snoopy" ) );
        elementos.add( new Tree.Entry<>( 19, "Papai Noel" ) );
        elementos.add( new Tree.Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( Tree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na árvore? => %s\n", e.getKey(),
                    avp.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( avp );
        for ( Tree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            avp.delete( e.getKey() );
            System.out.println( avp );
        }
        
    }
    
}
