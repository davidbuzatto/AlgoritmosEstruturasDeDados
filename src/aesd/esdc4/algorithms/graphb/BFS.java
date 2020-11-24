/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aesd.esdc4.algorithms.graphb;

import aesd.esdc4.algorithms.graph.Paths;
import aesd.esdc4.ds.implementations.LinkedQueue;
import aesd.esdc4.ds.implementations.ResizingArrayStack;
import aesd.esdc4.ds.implementations.working.BasicGraph;
import aesd.esdc4.ds.interfaces.Queue;
import aesd.esdc4.ds.interfaces.Stack;

/**
 * Algoritmo de busca em largura.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BFS extends Paths<Integer> {

    private boolean[] marcado;
    private int[] arestaAte;
    private int[] distanciaAte;
    private BasicGraph g;
    
    public BFS( BasicGraph g, int f ) {
        
        this.g = g;
        this.fonte = f;
        
        marcado = new boolean[g.v()];
        arestaAte = new int[g.v()];
        distanciaAte = new int[g.v()];
        
        for ( int i = 0; i < g.v(); i++ ) {
            arestaAte[i] = -1;
            distanciaAte[i] = -1;
        }
        
        bfs( g, f );
        
    }

    private void bfs( BasicGraph g, int f ) {
        
        Queue<Integer> fila = new LinkedQueue<>();
        fila.enqueue( f );
        marcado[f] = true;
        distanciaAte[f] = 0;
        
        while ( !fila.isEmpty()) {
            
            int v = fila.dequeue();
            
            for ( int w : g.adj( v ) ) {
                
                if ( !marcado[w] ) {
                    fila.enqueue(w );
                    marcado[w] = true;
                    arestaAte[w] = v;
                    distanciaAte[w] = distanciaAte[v] + 1;
                }
                
            }
            
        }
        
    }

    @Override
    public Stack<Integer> caminhoAte( Integer w ) {
        
        Stack<Integer> p = new ResizingArrayStack<>();
        
        if ( arestaAte[w] != -1 ) {    
            
            int atual = w;
            p.push( atual );

            while ( atual != -1 ) {
                atual = arestaAte[atual];
                if ( atual != -1 ) {
                    p.push( atual );
                }
            }
            
        }
        
        return p;
        
    }
    
    @Override
    public boolean existeCaminhoAte( Integer w ) {
        return arestaAte[w] != -1;
    }
    
    public boolean[] getMarcado() {
        return marcado.clone();
    }

    public int[] getArestaAte() {
        return arestaAte.clone();
    }

    public int[] getDistanciaAte() {
        return distanciaAte.clone();
    }

    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        sb.append( "Busca em Largura (fonte: vértice " ).append( fonte ).append( ")\n" );
        sb.append( "v\tmarcado[v]\tarestaAte[v]\tdistanciaAte[v]\n" );
        for ( int v = 0; v < g.v(); v++ ) {
            sb.append( String.format( "%d\t%s\t\t%s\t\t%s\n", 
                    v, 
                    marcado[v] ? "T" : "F", 
                    arestaAte[v] == -1 ? "-" : arestaAte[v],  
                    distanciaAte[v] == -1 ? "-" : distanciaAte[v] ) );
        }
        
        return sb.toString();
        
    }
    
}
