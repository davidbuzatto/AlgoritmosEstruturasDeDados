/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.graph;

import aesd.esdc4.ds.interfaces.Stack;

/**
 * Classe abstrata de caminhos.
 * 
 * @author Prof. Dr. David Buzatto
 */
public abstract class Paths<Tipo> {
    
    // fonte do caminho
    protected Tipo fonte;
    
    /**
     * Caminho da fonte até w.
     * 
     * @param w Alvo do caminho.
     * @return Uma pilha com os elementos do caminho da fonte até w.
     */
    public abstract Stack<Tipo> caminhoAte( Tipo w );
    
    /**
     * Retorna se existe ou não caminho da fonte até w.
     * 
     * @param w Alvo do caminho.
     * @return True se existir caminho, false caso contrário.
     */
    public abstract boolean existeCaminhoAte( Tipo w );

    public Tipo getFonte() {
        return fonte;
    }

    public void setFonte( Tipo fonte ) {
        this.fonte = fonte;
    }
    
}
