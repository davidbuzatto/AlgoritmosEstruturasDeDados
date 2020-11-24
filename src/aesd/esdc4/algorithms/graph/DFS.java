/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.graph;

import aesd.esdc4.ds.implementations.ResizingArrayStack;
import aesd.esdc4.ds.implementations.working.Graph;
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
    private Graph<Tipo> g;

    public DFS( Graph<Tipo> g, Tipo f ) {
        
        this.g = g;
        this.fonte = f;
        
        marcado = new TreeMap<>();
        arestaAte = new TreeMap<>();
        
        for ( Tipo v : g.getVertices() ) {
            marcado.put( v, false );
            arestaAte.put( v, null );
        }
        
        dfs( g, f );
        
    }

    private void dfs( Graph<Tipo> g, Tipo v ) {
        
        marcado.put( v, true );
        
        for ( Tipo w : g.getAdjacentes( v ) ) {
            
            if ( !marcado.get( w ) ) {
                dfs( g, w );
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
        
        for ( Tipo v : g.getVertices() ) {
            sb.append( String.format( "%d\t%s\t\t%s\n",
                    v, 
                    marcado.get( v ) ? "T" : "F", 
                    arestaAte.get( v ) == null ? "-" : arestaAte.get( v ) ) );
        }
        
        return sb.toString();
        
    }
    
}
