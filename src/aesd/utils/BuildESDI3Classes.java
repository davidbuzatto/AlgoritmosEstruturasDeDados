/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Esta classe foi criada com o objetivo de copiar classes prontas dos
 * pacotes das disciplinas do curso de Bacharelado em Ciência da Computação
 * para os pacotes das disciplinas do curso de Tecnologia em Sistemas para
 * Internet.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class BuildESDI3Classes {
    
    public static void main( String[] args ) throws IOException {
        
        Path base = Path.of( "src/aesd/esdc4" );
        List<Path> paths = new ArrayList<>();
        collect( base, paths );
        
        for ( Path o : paths ) {
            
            Path t = Path.of( o.toString().replace( "esdc4", "esdi3" ) );
            
            String n = t.toString();
            if ( !n.contains( "descrição.txt" ) ) {
                copy( o, t );
            }
            
        }
        
    }
    
    private static void collect( Path path, List<Path> list ) {
        
        try {
            if ( Files.isDirectory( path ) ) {
                Files.list( path ).forEach( ( Path dirPath ) -> {
                    collect( dirPath, list );
                });
            } else {
                list.add( path );
            }
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
    }
    
    private static void copy( Path o, Path t ) {
        
        String oPrefix = "aesd.esdc4.";
        String tPrefix = "aesd.esdi3.";
        
        try {
            
            if ( Files.exists( t ) ) {
                Files.delete( t );
            }
            
            Files.createDirectories( t.getParent() );
            Files.copy( o, t, StandardCopyOption.REPLACE_EXISTING );
            Scanner s = new Scanner( Files.newBufferedReader( t, StandardCharsets.UTF_8 ) );
            
            StringBuilder d = new StringBuilder();
            
            while ( s.hasNextLine() ) {
                String li = s.nextLine().replace( oPrefix, tPrefix );
                d.append( li ).append( "\n" );
            }
            s.close();
            
            PrintWriter p = new PrintWriter( Files.newBufferedWriter( t, StandardCharsets.UTF_8 ) );
            p.write( d.toString() );
            p.close();
            
        } catch ( IOException exc ) {
            exc.printStackTrace();
        }
        
    }
    
}
