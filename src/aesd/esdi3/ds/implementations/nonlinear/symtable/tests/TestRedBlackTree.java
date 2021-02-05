/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.ds.implementations.nonlinear.symtable.tests;

import aesd.esdi3.algorithms.tree.TraversalTypes;
import aesd.esdi3.ds.implementations.nonlinear.symtable.RedBlackTree;
import aesd.esdi3.ds.implementations.linear.ResizingArrayList;
import aesd.esdi3.ds.interfaces.List;
import aesd.esdi3.ds.utils.Utils;
import aesd.esdi3.ds.interfaces.BinaryTree;

/**
 * Teste de uso da árvore vermelho-preto.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestRedBlackTree {
    
    public static void main( String[] args ) {
        
        RedBlackTree<Character, String> avp = new RedBlackTree<>();
        
        avp.put( 'H', "Helena" );
        System.out.println( avp );
        avp.put( 'I', "Ingrid" );
        System.out.println( avp );
        avp.put( 'J', "João" );
        System.out.println( avp );
        avp.put( 'B', "Brendo" );
        System.out.println( avp );
        avp.put( 'A', "Aurora" );
        System.out.println( avp );
        avp.put( 'E', "Ernesto" );
        System.out.println( avp );
        avp.put( 'C', "Camila" );
        System.out.println( avp );
        avp.put( 'F', "Fernanda" );
        System.out.println( avp );
        avp.put( 'D', "David" );
        System.out.println( avp );
        avp.put( 'G', "Giovana" );
        System.out.println( avp );
        avp.put( 'K', "Karen" );
        System.out.println( avp );
        avp.put( 'L', "Leonardo" );
        System.out.println( avp );
        
        System.out.println( "Dados da árvore através do iterador:" );
        for ( BinaryTree.Entry<Character, String> e : avp ) {
            System.out.print( e.getKey() );
            System.out.print( " " );
        }
        
        System.out.println( "\n" );
        
        System.out.println( "----- Percursos -----" );
        System.out.print( "Pré-Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : avp.traverse(TraversalTypes.PREORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : avp.traverse(TraversalTypes.INORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem: " );
        for ( BinaryTree.Entry<Character, String> e : avp.traverse(TraversalTypes.POSTORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível: " );
        for ( BinaryTree.Entry<Character, String> e : avp.traverse( TraversalTypes.LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pré-Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : avp.traverse(TraversalTypes.INVERSE_PREORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : avp.traverse(TraversalTypes.INVERSE_INORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Pós-Ordem Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : avp.traverse(TraversalTypes.INVERSE_POSTORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        System.out.print( "Em Nível Inverso: " );
        for ( BinaryTree.Entry<Character, String> e : avp.traverse( TraversalTypes.INVERSE_LEVEL_ORDER ) ) {
            System.out.print( "(" + e.getKey() + ") " );
        }
        System.out.println();
        
        // consultas
        System.out.println( "\n----- Consultas -----" );
        List<BinaryTree.Entry<Character, String>> elementos = new ResizingArrayList<>();
        for ( BinaryTree.Entry<Character, String> e : avp.traverse(TraversalTypes.INORDER ) ) {
            elementos.add( e );
        }
        elementos.add(new BinaryTree.Entry<>( 'S', "Snoopy" ) );
        elementos.add(new BinaryTree.Entry<>( 'P', "Papai Noel" ) );
        elementos.add(new BinaryTree.Entry<>( 'M', "Mario" ) );
        Utils.shuffle( elementos );
        for ( BinaryTree.Entry<Character, String> e : elementos ) {
            System.out.printf( "%c está na árvore? => %s\n", e.getKey(),
                    avp.contains( e.getKey() ) ? "SIM" : "NÃO" );
        }
        
        System.out.println( "\n----- Remoção -----" );
        System.out.println( avp );
        for ( BinaryTree.Entry<Character, String> e : elementos ) {
            System.out.printf( "Removendo o par chave/valor com chave %c...\n", e.getKey() );
            avp.delete( e.getKey() );
            System.out.println( avp );
        }
        
    }
    
}
