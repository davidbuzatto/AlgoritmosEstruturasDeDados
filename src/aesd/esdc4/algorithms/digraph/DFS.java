/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.algorithms.graph.Paths;
import aesd.esdc4.ds.implementations.ResizingArrayStack;
import aesd.esdc4.ds.implementations.working.Digraph;
import aesd.esdc4.ds.interfaces.Stack;
import java.util.Map;
import java.util.TreeMap;

/**
 * Algoritmo de busca em profundidade.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class DFS<Tipo extends Comparable<Tipo>> extends Paths<Tipo> {

    private Map<Tipo, Boolean> marcado;
    private Map<Tipo, Tipo> arestaAte;
    private Digraph<Tipo> dg;

    public DFS( Digraph<Tipo> dg, Tipo f ) {
        
        this.dg = dg;
        this.fonte = f;
        
        marcado = new TreeMap<>();
        arestaAte = new TreeMap<>();
        
        for ( Tipo v : dg.getVertices() ) {
            marcado.put( v, false );
            arestaAte.put( v, null );
        }
        
        dfs( dg, f );
        
    }

    private void dfs( Digraph<Tipo> dg, Tipo v ) {
        
        marcado.put( v, true );
        
        for ( Tipo w : dg.getAdjacentes( v ) ) {
            
            if ( !marcado.get( w ) ) {
                dfs( dg, w );
                arestaAte.put( w, v );
            }
            
        }
        
    }
    
    @Override
    public Stack<Tipo> caminhoAte( Tipo w ) {
        
        Stack<Tipo> p = new ResizingArrayStack<>();
        
        if ( arestaAte.get( w ) != null ) {    
            
            Tipo atual = w;
            p.push( atual );

            while ( atual != null ) {
                atual = arestaAte.get( atual );
                if ( atual != null ) {
                    p.push( atual );
                }
            }
            
        }
        
        return p;
        
    }
    
    @Override
    public boolean existeCaminhoAte( Tipo w ) {
        return arestaAte.get( w ) != null;
    }

    public Map<Tipo, Boolean> getMarcado() {
        return marcado;
    }

    public Map<Tipo, Tipo> getArestaAte() {
        return arestaAte;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( "Busca em Profunidade (fonte: vértice " ).append( fonte ).append( ")\n" );
        sb.append( "v\tmarcado[v]\tarestaAte[v]\n" );
        
        for ( Tipo v : dg.getVertices() ) {
            sb.append( String.format( "%d\t%s\t\t%s\n",
                    v, 
                    marcado.get( v ) ? "T" : "F", 
                    arestaAte.get( v ) == null ? "-" : arestaAte.get( v ) ) );
        }
        
        return sb.toString();
        
    }
    
}
