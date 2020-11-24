/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.tree;

import aesd.esdc4.ds.implementations.LinkedQueue;
import aesd.esdc4.ds.implementations.ResizingArrayStack;
import aesd.esdc4.ds.implementations.working.AVLTree;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação dos percursos das árvores AVLs.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class AVLTreeTraversals {
    
    /**
     * Retorna uma lista contendo os ítens da árvore na ordem do percurso
     * especificado.
     * 
     * @param <Tipo> Tipo de dados da árvore.
     * @param aavl Árvore que será percorrida.
     * @param tipo Tipo do percurso a ser executado.
     * @return Lista de elementos na ordem do percurso executado.
     */
    public static <Tipo extends Comparable<Tipo>> Iterable<Tipo> percorrer( AVLTree<Tipo> aavl, TraversalTypes tipo ) {
        
        List<Tipo> elementos = new ArrayList<>();
        
        switch ( tipo ) {
            case PRE_ORDEM:
                preOrdem( aavl.getRaiz(), elementos );
                break;
            case EM_ORDEM:
                emOrdem( aavl.getRaiz(), elementos );
                break;
            case POS_ORDEM:
                posOrdem( aavl.getRaiz(), elementos );
                break;
            case EM_NIVEL:
                emNivel( aavl.getRaiz(), elementos );
                break;
            case PRE_ORDEM_INVERSO:
                preOrdemInverso( aavl.getRaiz(), elementos );
                break;
            case EM_ORDEM_INVERSO:
                emOrdemInverso( aavl.getRaiz(), elementos );
                break;
            case POS_ORDEM_INVERSO:
                posOrdemInverso( aavl.getRaiz(), elementos );
                break;
            case EM_NIVEL_INVERSO:
                emNivelInverso( aavl.getRaiz(), elementos );
                break;
        }
        
        
        return elementos;
        
    }
    
    /*
     * Métodos privados para os percursos.
     */
    private static <Tipo extends Comparable<Tipo>> void preOrdem( AVLTree<Tipo>.No<Tipo> no, List<Tipo> elementos ) {
        
        if ( no != null ) {
            elementos.add( no.valor );
            preOrdem( no.esquerda, elementos );
            preOrdem( no.direita, elementos );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void emOrdem( AVLTree<Tipo>.No<Tipo> no, List<Tipo> elementos ) {
        
        if ( no != null ) {
            emOrdem( no.esquerda, elementos );
            elementos.add( no.valor );
            emOrdem( no.direita, elementos );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void posOrdem( AVLTree<Tipo>.No<Tipo> no, List<Tipo> elementos ) {
        
        if ( no != null ) {
            posOrdem( no.esquerda, elementos );
            posOrdem( no.direita, elementos );
            elementos.add( no.valor );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void emNivel( AVLTree<Tipo>.No<Tipo> no, List<Tipo> elementos ) {
        
        if ( no != null ) {
            
            Queue<AVLTree<Tipo>.No<Tipo>> fila = new LinkedQueue<>();
            fila.enqueue( no );

            while ( !fila.isEmpty() ) {

                AVLTree<Tipo>.No<Tipo> atual = fila.dequeue();
                elementos.add( atual.valor );

                if ( atual.esquerda != null ) {
                    fila.enqueue( atual.esquerda );
                }

                if ( atual.direita != null ) {
                    fila.enqueue( atual.direita );
                }

            }
            
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void preOrdemInverso( AVLTree<Tipo>.No<Tipo> no, List<Tipo> elementos ) {
        
        if ( no != null ) {
            elementos.add( no.valor );
            preOrdemInverso( no.direita, elementos );
            preOrdemInverso( no.esquerda, elementos );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void emOrdemInverso( AVLTree<Tipo>.No<Tipo> no, List<Tipo> elementos ) {
        
        if ( no != null ) {
            emOrdemInverso( no.direita, elementos );
            elementos.add( no.valor );
            emOrdemInverso( no.esquerda, elementos );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void posOrdemInverso( AVLTree<Tipo>.No<Tipo> no, List<Tipo> elementos ) {
        
        if ( no != null ) {
            posOrdemInverso( no.direita, elementos );
            posOrdemInverso( no.esquerda, elementos );
            elementos.add( no.valor );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void emNivelInverso( AVLTree<Tipo>.No<Tipo> no, List<Tipo> elementos ) {
        
        if ( no != null ) {
            
            Queue<AVLTree<Tipo>.No<Tipo>> fila = new LinkedQueue<>();
            Stack<AVLTree<Tipo>.No<Tipo>> pilha = new ResizingArrayStack<>();
            fila.enqueue( no );

            while ( !fila.isEmpty() ) {

                AVLTree<Tipo>.No<Tipo> atual = fila.dequeue();
                pilha.push( atual );

                if ( atual.esquerda != null ) {
                    fila.enqueue( atual.esquerda );
                }

                if ( atual.direita != null ) {
                    fila.enqueue( atual.direita );
                }

            }

            while ( !pilha.isEmpty() ) {
                elementos.add( pilha.pop().valor );
            }
        
        }
        
    }
    
}
