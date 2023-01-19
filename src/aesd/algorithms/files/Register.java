package aesd.algorithms.files;

/**
 * Um registro para testes de escrita em arquivos de acesso randômico.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class Register {
    
    // dados do registro: 12 bytes
    public static final int SIZE = 12;
    
    private int n1;     // 4 bytes
    private double n2;  // 8 bytes

    public Register( int n1, double n2 ) {
        this.n1 = n1;
        this.n2 = n2;
    }
    
    public int getN1() {
        return n1;
    }

    public void setN1( int n1 ) {
        this.n1 = n1;
    }

    public double getN2() {
        return n2;
    }

    public void setN2( double n2 ) {
        this.n2 = n2;
    }

    @Override
    public String toString() {
        return "Register = " + n1 + " " + n2;
    }
    
}
