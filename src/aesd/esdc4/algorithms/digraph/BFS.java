/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.digraph;

import aesd.esdc4.algorithms.graph.Paths;
import aesd.esdc4.ds.implementations.LinkedQueue;
import aesd.esdc4.ds.implementations.ResizingArrayStack;
import aesd.esdc4.ds.implementations.working.Digraph;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;
import java.util.Map;
import java.util.TreeMap;

/**
 * Algoritmo de busca em largura.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BFS<Tipo extends Comparable<Tipo>> extends Paths<Tipo> {

    private Map<Tipo, Boolean> marcado;
    private Map<Tipo, Tipo> arestaAte;
    private Map<Tipo, Integer> distanciaAte;
    private Digraph<Tipo> g;
    
    public BFS( Digraph<Tipo> g, Tipo f ) {
        
        this.g = g;
        this.fonte = f;
        
        marcado = new TreeMap<>();
        arestaAte = new TreeMap<>();
        distanciaAte = new TreeMap<>();
        
        for ( Tipo v : g.getVertices() ) {
            marcado.put( v, false );
            arestaAte.put( v, null );
            distanciaAte.put( v, -1 );
        }
        
        bfs( g, f );
        
    }

    private void bfs( Digraph<Tipo> g, Tipo f ) {
        
        Queue<Tipo> fila = new LinkedQueue<>();
        fila.enqueue( f );
        marcado.put( f, true );
        distanciaAte.put( f, 0 );
        
        while ( !fila.isEmpty()) {
            
            Tipo v = fila.dequeue();
            
            for ( Tipo w : g.getAdjacentes( v ) ) {
                
                if ( !marcado.get( w ) ) {
                    fila.enqueue( w );
                    marcado.put( w, true );
                    arestaAte.put( w, v );
                    distanciaAte.put( w, distanciaAte.get( v ) + 1 );
                }
                
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

    public Map<Tipo, Integer> getDistanciaAte() {
        return distanciaAte;
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( "Busca em Largura (fonte: vértice " ).append( fonte ).append( ")\n" );
        sb.append( "v\tmarcado[v]\tarestaAte[v]\tdistanciaAte[v]\n" );
        
        for ( Tipo v : g.getVertices() ) {
            sb.append( String.format( "%d\t%s\t\t%s\t\t%s\n", 
                    v, 
                    marcado.get( v ) ? "T" : "F", 
                    arestaAte.get( v ) == null ? "-" : arestaAte.get( v ),  
                    distanciaAte.get( v ) == -1 ? "-" : distanciaAte.get( v ) ) );
        }
        
        return sb.toString();
        
    }
    
}
