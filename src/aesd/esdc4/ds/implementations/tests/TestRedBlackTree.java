/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.tests;

import aesd.esdc4.algorithms.tree.TraversalTypes;
import aesd.esdc4.ds.implementations.RedBlackTree;
import aesd.esdc4.ds.interfaces.List;
import aesd.esdc4.ds.utils.Utils;
import aesd.esdc4.ds.interfaces.BinaryTree;

/**
 * Teste de uso da árvore vermelho-preto.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestRedBlackTree {
    
    public static void main( String[] args ) {
        
        RedBlackTree<Integer, String> avp = new RedBlackTree<>();
        
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
        for ( BinaryTree.Entry<Integer, String> e : avp ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "----- Percursos -----" );
        System.out.print( "Pré-Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : avp.traverse( TraversalTypes.PRE_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : avp.traverse( TraversalTypes.IN_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem: " );
        for ( BinaryTree.Entry<Integer, String> e : avp.traverse( TraversalTypes.POST_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível: " );
        for ( BinaryTree.Entry<Integer, String> e : avp.traverse( TraversalTypes.LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pré-Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : avp.traverse( TraversalTypes.INVERSE_PRE_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : avp.traverse( TraversalTypes.INVERSE_IN_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : avp.traverse( TraversalTypes.INVERSE_POST_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível Inverso: " );
        for ( BinaryTree.Entry<Integer, String> e : avp.traverse( TraversalTypes.INVERSE_LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<BinaryTree.Entry<Integer, String>> elementos = (List<BinaryTree.Entry<Integer, String>>) avp.traverse( TraversalTypes.IN_ORDER );
        elementos.add(new BinaryTree.Entry<>( 15, "Snoopy" ) );
        elementos.add(new BinaryTree.Entry<>( 19, "Papai Noel" ) );
        elementos.add(new BinaryTree.Entry<>( -4, "Garfield" ) );
        Utils.shuffle( elementos );
        for ( BinaryTree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "%4d está na árvore? => %s\n", e.getKey(),
                    avp.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( avp );
        for ( BinaryTree.Entry<Integer, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %d...\n", e.getKey() );
            avp.delete( e.getKey() );
            System.out.println( avp );
        }
        
    }
    
}
