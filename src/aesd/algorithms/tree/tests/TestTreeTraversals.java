package aesd.algorithms.tree.tests;

import aesd.algorithms.tree.TraversalTypes;
import aesd.algorithms.tree.TreeTraversals;
import aesd.ds.interfaces.BinaryTree;

/**
 * Teste de uso dos percursos de árvores binárias (TreeTraversals).
 *
 * @author Prof. Dr. David Buzatto
 */
public class TestTreeTraversals {

    public static void main( String[] args ) {

        //          4
        //        /   \
        //       2     6
        //      / \   / \
        //     1   3 5   7
        BinaryTree.Node<Integer, String> n1 = node( 1 );
        BinaryTree.Node<Integer, String> n3 = node( 3 );
        BinaryTree.Node<Integer, String> n5 = node( 5 );
        BinaryTree.Node<Integer, String> n7 = node( 7 );

        BinaryTree.Node<Integer, String> n2 = node( 2 );
        n2.left = n1;
        n2.right = n3;

        BinaryTree.Node<Integer, String> n6 = node( 6 );
        n6.left = n5;
        n6.right = n7;

        BinaryTree.Node<Integer, String> root = node( 4 );
        root.left = n2;
        root.right = n6;

        print( "Pré-ordem", root, TraversalTypes.PREORDER );
        print( "Em ordem", root, TraversalTypes.INORDER );
        print( "Pós-ordem", root, TraversalTypes.POSTORDER );
        print( "Em nível", root, TraversalTypes.LEVEL_ORDER );
        print( "Em ordem inversa", root, TraversalTypes.INVERSE_INORDER );

    }

    private static BinaryTree.Node<Integer, String> node( int key ) {
        BinaryTree.Node<Integer, String> n = new BinaryTree.Node<>();
        n.key = key;
        n.value = "valor" + key;
        return n;
    }

    private static void print( String label, BinaryTree.Node<Integer, String> root, TraversalTypes type ) {

        System.out.print( label + ": " );

        for ( BinaryTree.Entry<Integer, String> entry : TreeTraversals.traverse( root, type ) ) {
            System.out.print( entry.getKey() + " " );
        }

        System.out.println();

    }

}
