/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.algorithms.stack;

import aesd.ds.exceptions.EmptyStackException;
import aesd.ds.implementations.linear.LinkedStack;
import aesd.ds.interfaces.Stack;

/**
 * Algoritmos básicos utilizando pilhas.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class StackBasicAlgorithms {
    
    /**
     * Testa a validade de uma expressão balanceada composta exclusivamente de 
     * pares de parênteses.
     * 
     * @param expression A expressão a ser testada.
     * @return verdadeiro caso a expressão seja válida, falso caso contrário.
     */
    public static boolean isBalancedParentheses( String expression ) {
        
        Stack<Character> stack = new LinkedStack<>();
        
        for ( char c : expression.toCharArray() ) {
            
            switch ( c ) {
                
                case '(':
                    stack.push( c );
                    break;
                    
                case ')':
                    if ( !stack.isEmpty() ) {
                        stack.pop();
                    } else {
                        return false;
                    }
                    break;
                    
                default:
                    return false;
                    
            }
            
        }
        
        return stack.isEmpty();
        
    }
    
    /**
     * Testa a validade de uma expressão balanceada composta exclusivamente de 
     * pares de parênteses e pares de colchetes.
     * 
     * @param expression A expressão a ser testada.
     * @return verdadeiro caso a expressão seja válida, falso caso contrário.
     */
    public static boolean isBalancedParenthesesAndBrackets( String expression ) {
        
        Stack<Character> stack = new LinkedStack<>();
        
        for ( char c : expression.toCharArray() ) {
            
            switch ( c ) {
                
                case '(':
                case '[':
                    stack.push( c );
                    break;
                    
                case ')':
                case ']':
                    if ( !stack.isEmpty() ) {
                        char t = stack.peek();
                        if ( ( c == ')' && t == '(' ) ||
                             ( c == ']' && t == '[' ) ) {
                            stack.pop();
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                    
                default:
                    return false;
                    
            }
            
        }
        
        return stack.isEmpty();
        
    }
    
    /**
     * Testa a validade de uma expressão balanceada composta exclusivamente de 
     * pares de parênteses, pares de colchetes e pares de chaves.
     * 
     * @param expression A expressão a ser testada.
     * @return verdadeiro caso a expressão seja válida, falso caso contrário.
     */
    public static boolean isBalancedParenthesesAndBracketsAndBraces( String expression ) {
        
        Stack<Character> stack = new LinkedStack<>();
        
        for ( char c : expression.toCharArray() ) {
            
            switch ( c ) {
                
                case '(':
                case '[':
                case '{':
                    stack.push( c );
                    break;
                    
                case ')':
                case ']':
                case '}':
                    if ( !stack.isEmpty() ) {
                        char t = stack.peek();
                        if ( ( c == ')' && t == '(' ) ||
                             ( c == ']' && t == '[' ) ||
                             ( c == '}' && t == '{' ) ) {
                            stack.pop();
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                    break;
                    
                default:
                    return false;
                    
            }
            
        }
        
        return stack.isEmpty();
        
    }
    
    /**
     * Calcula o resultado de uma expressão pós-fixada composta de inteiros
     * em que os operandos e operadores estão separados por espaços.
     * 
     * @param expression Expressão a ser avaliada.
     * @return O resultado da expressão avaliada.
     * @throws NumberFormatException Se a expressão está mal formada.
     */
    public static int evalueatePostfixExpression( String expression ) 
            throws NumberFormatException {
        
        Stack<Integer> stack = new LinkedStack<>();
        int op1;
        int op2;
        
        try {
            
            for ( String token : expression.split( "\\s+" ) ) {

                switch ( token ) {

                    case "+":
                        op2 = stack.pop();
                        op1 = stack.pop();
                        stack.push( op1 + op2 );
                        break;

                    case "-":
                        op2 = stack.pop();
                        op1 = stack.pop();
                        stack.push( op1 - op2 );
                        break;

                    case "*":
                        op2 = stack.pop();
                        op1 = stack.pop();
                        stack.push( op1 * op2 );
                        break;

                    case "/":
                        op2 = stack.pop();
                        op1 = stack.pop();
                        stack.push( op1 / op2 );
                        break;

                    default:
                        stack.push( Integer.parseInt( token ) );
                        break;

                }

            }

            return stack.pop();
        
        } catch ( NumberFormatException | EmptyStackException exc ) {
            throw new NumberFormatException( "malformed expression: " + expression );
        }
        
    }
    
}
