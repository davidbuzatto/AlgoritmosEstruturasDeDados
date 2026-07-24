package aesd.algorithms.files;

import java.io.Serializable;

/**
 * Uma classe que pode ter seus objetos serializados.
 *
 * Implementar Serializable (uma interface marcadora, sem métodos) é o único
 * requisito para que ObjectOutputStream/ObjectInputStream saibam converter
 * objetos desta classe de/para bytes automaticamente, via reflexão. O campo
 * serialVersionUID identifica a versão do formato serializado: se os campos
 * da classe mudarem de forma incompatível, alterar esse valor evita que um
 * objeto serializado com uma versão antiga seja deserializado
 * incorretamente com a versão nova.
 *
 * @author Prof. Dr. David Buzatto
 */
public class SerializableClass implements Serializable {
    
    // indica a versão do objeto serializável
    private static final long serialVersionUID = 0L;
    
    private int number;
    private String string;

    public SerializableClass( int number, String string ) {
        this.number = number;
        this.string = string;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber( int number ) {
        this.number = number;
    }

    public String getString() {
        return string;
    }

    public void setString( String string ) {
        this.string = string;
    }

    @Override
    public String toString() {
        return "SerializableClass = " + number + " " + string;
    }
    
}
