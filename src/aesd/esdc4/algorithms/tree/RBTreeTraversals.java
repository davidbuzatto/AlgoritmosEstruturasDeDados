/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.tree;

import aesd.esdc4.ds.implementations.LinkedQueue;
import aesd.esdc4.ds.implementations.ResizingArrayStack;
import aesd.esdc4.ds.implementations.working.RBTree;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação dos percursos das árvores vermelho-preto.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RBTreeTraversals {
    
    /**
     * Retorna uma lista contendo os ítens da árvore na ordem do percurso
     * especificado.
     * 
     * @param <Tipo> Tipo do valor.
     * @param avp Árvore que será percorrida.
     * @param tipo Tipo do percurso a ser executado.
     * @return Lista de elementos com os nós na ordem do percurso executado.
     */
    public static <Tipo extends Comparable<Tipo>> Iterable<RBTree<Tipo>.No<Tipo>> percorrer( 
            RBTree<Tipo> avp, TraversalTypes tipo ) {
        
        List<RBTree<Tipo>.No<Tipo>> elementos = new ArrayList<>();
        
        switch ( tipo ) {
            case PRE_ORDEM:
                preOrdem( avp.getRaiz(), elementos );
                break;
            case EM_ORDEM:
                emOrdem( avp.getRaiz(), elementos );
                break;
            case POS_ORDEM:
                posOrdem( avp.getRaiz(), elementos );
                break;
            case EM_NIVEL:
                emNivel( avp.getRaiz(), elementos );
                break;
            case PRE_ORDEM_INVERSO:
                preOrdemInverso( avp.getRaiz(), elementos );
                break;
            case EM_ORDEM_INVERSO:
                emOrdemInverso( avp.getRaiz(), elementos );
                break;
            case POS_ORDEM_INVERSO:
                posOrdemInverso( avp.getRaiz(), elementos );
                break;
            case EM_NIVEL_INVERSO:
                emNivelInverso( avp.getRaiz(), elementos );
                break;
        }
        
        return elementos;
        
    }
    
    /*
     * Métodos privados para os percursos.
     */
    private static <Tipo extends Comparable<Tipo>> void preOrdem( 
            RBTree<Tipo>.No<Tipo> no, 
            List<RBTree<Tipo>.No<Tipo>> elementos ) {
        
        if ( no != null ) {
            elementos.add( no );
            preOrdem( no.esquerda, elementos );
            preOrdem( no.direita, elementos );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void emOrdem( 
            RBTree<Tipo>.No<Tipo> no, 
            List<RBTree<Tipo>.No<Tipo>> elementos ) {
        
        if ( no != null ) {
            emOrdem( no.esquerda, elementos );
            elementos.add( no );
            emOrdem( no.direita, elementos );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void posOrdem( 
            RBTree<Tipo>.No<Tipo> no, 
            List<RBTree<Tipo>.No<Tipo>> elementos ) {
        
        if ( no != null ) {
            posOrdem( no.esquerda, elementos );
            posOrdem( no.direita, elementos );
            elementos.add( no );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void emNivel( 
            RBTree<Tipo>.No<Tipo> no, 
            List<RBTree<Tipo>.No<Tipo>> elementos ) {
        
        if ( no != null ) {
            
            Queue<RBTree<Tipo>.No<Tipo>> fila = new LinkedQueue<>();
            fila.enqueue( no );

            while ( !fila.isEmpty() ) {

                RBTree<Tipo>.No<Tipo> atual = fila.dequeue();
                elementos.add( atual );

                if ( atual.esquerda != null ) {
                    fila.enqueue( atual.esquerda );
                }

                if ( atual.direita != null ) {
                    fila.enqueue( atual.direita );
                }

            }
            
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void preOrdemInverso( 
            RBTree<Tipo>.No<Tipo> no, 
            List<RBTree<Tipo>.No<Tipo>> elementos ) {
        
        if ( no != null ) {
            elementos.add( no );
            preOrdemInverso( no.direita, elementos );
            preOrdemInverso( no.esquerda, elementos );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void emOrdemInverso( 
            RBTree<Tipo>.No<Tipo> no, 
            List<RBTree<Tipo>.No<Tipo>> elementos ) {
        
        if ( no != null ) {
            emOrdemInverso( no.direita, elementos );
            elementos.add( no );
            emOrdemInverso( no.esquerda, elementos );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void posOrdemInverso( 
            RBTree<Tipo>.No<Tipo> no, 
            List<RBTree<Tipo>.No<Tipo>> elementos ) {
        
        if ( no != null ) {
            posOrdemInverso( no.direita, elementos );
            posOrdemInverso( no.esquerda, elementos );
            elementos.add( no );
        }
        
    }
    
    private static <Tipo extends Comparable<Tipo>> void emNivelInverso( 
            RBTree<Tipo>.No<Tipo> no, 
            List<RBTree<Tipo>.No<Tipo>> elementos ) {
        
        if ( no != null ) {
            
            Queue<RBTree<Tipo>.No<Tipo>> fila = new LinkedQueue<>();
            Stack<RBTree<Tipo>.No<Tipo>> pilha = new ResizingArrayStack<>();
            fila.enqueue( no );

            while ( !fila.isEmpty() ) {

                RBTree<Tipo>.No<Tipo> atual = fila.dequeue();
                pilha.push( atual );

                if ( atual.esquerda != null ) {
                    fila.enqueue( atual.esquerda );
                }

                if ( atual.direita != null ) {
                    fila.enqueue( atual.direita );
                }

            }

            while ( !pilha.isEmpty() ) {
                elementos.add( pilha.pop() );
            }
        
        }
        
    }
    
}
