/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdi3.algorithms.stack.tests;

import static aesd.esdi3.algorithms.stack.StackBasicAlgorithms.evalueatePostfixExpression;
import static aesd.esdi3.algorithms.stack.StackBasicAlgorithms.isBalancedParentheses;
import static aesd.esdi3.algorithms.stack.StackBasicAlgorithms.isBalancedParenthesesAndBrackets;
import static aesd.esdi3.algorithms.stack.StackBasicAlgorithms.isBalancedParenthesesAndBracketsAndBraces;

/**
 * Teste de uso dos algoritmos básicos utilizando pilhas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class TestStackBasicAlgorithms {
    
    public static void main( String[] args ) {
        
        String[] testesParenteses = {
            "()",
            "(())",
            "(()())",
            "(()",
            "())"
        };
        
        String[] testesParentesesColchetes = {
            "()[]",
            "(())[[]]",
            "([()]()[])",
            "([())()[])",
            "(()]",
            "[())",
            "(())[",
            "(())]"
        };
        
        String[] testesParentesesColchetesChaves = {
            "()[]{}",
            "(()){}[[]]",
            "({[()]}{}()[])",
            "([())()[])",
            "(()]",
            "[({})]",
            "(())}",
            "{(())"
        };
        
        for ( String teste : testesParenteses ) {
            System.out.printf( "%s: %s\n", teste,
                    isBalancedParentheses( teste ) ? 
                            " é válida!" : " não é válida..." );
        }
        System.out.println();
        
        for ( String teste : testesParentesesColchetes ) {
            System.out.printf( "%s: %s\n", teste,
                    isBalancedParenthesesAndBrackets( teste ) ? 
                            " é válida!" : " não é válida..." );
        }
        System.out.println();
        
        for ( String teste : testesParentesesColchetesChaves ) {
            System.out.printf( "%s: %s\n", teste,
                    isBalancedParenthesesAndBracketsAndBraces( teste ) ? 
                            " é válida!" : " não é válida..." );
        }
        System.out.println();
        
        System.out.println( evalueatePostfixExpression( "5 6 + 5 / 5 10 - +" ) );
        
    }
    
}
