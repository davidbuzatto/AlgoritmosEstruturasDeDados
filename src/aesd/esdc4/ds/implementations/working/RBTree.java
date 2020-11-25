/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aesd.esdc4.ds.implementations.working;

import aesd.esdc4.ds.implementations.LinkedQueue;
import aesd.esdc4.ds.interfaces.Queue;
import java.util.NoSuchElementException;

/**
 * Implementação de uma árvore vermelho e preto paro valor/valor.
 * 
 * Baseado no código de:
 *     SEDGEWICK, R.; WAYNE, K. Algorithms. 4. ed. 
 *     Pearson Education: New Jersey, 2011. 955 p.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class RBTree<Tipo extends Comparable<Tipo>> {

    /*
     * Classe interna que define os nós da árvore.
     * É pública para poder acessar a estrutura dos nós externamente
     * no simulador e nos percursos. Deveria ser privada.
     */
    public class No<Tipo> {
        
        public Tipo valor;
        public No<Tipo> esquerda;
        public No<Tipo> direita;
        public CorNo cor;
        public int n;

        @Override
        public String toString() {
            return valor + " (" + ( cor == CorNo.VERMELHO ? "V" : "P" ) + ")";
        }
        
    }
    
    /*
     * Enumeração para especificação da cor do nó.
     */
    public enum CorNo {
        VERMELHO,
        PRETO
    }

    // raiz da árvore
    private No<Tipo> raiz;

    
    
    /*
     * ***********************************************************************
     * Métodos utilitários para os nós. Não estão inseridos na classe nó
     * pois verificam também se o nó passado por parâmetro é null para retornar
     * ***********************************************************************
     */
    
    /**
     * Verifica se um nó é vermelho.
     * 
     * @param no O nó a ser verificado
     * @return true caso o nó seja vermelho, false caso seja preto ou null.
     */
    private boolean isVermelho( No<Tipo> no ) {
        if ( no == null ) {
            return false;
        }
        return no.cor == CorNo.VERMELHO;
    }

    /**
     * Retorna o tamanho do nó.
     * 
     * @param no O nó a ser verificado.
     * @return O número do nó na sub-árvore enraizada nó ou 0 caso seja null.
     */
    // number of node in subtree rooted at x; 0 if x is null
    private int tamanho( No<Tipo> no ) {
        if ( no == null ) {
            return 0;
        }
        return no.n;
    }

    
    
    /**
     * ***********************************************************************
     * Métodos para obtenção do tamanho.
     * ***********************************************************************
     */
    
    /**
     * 
     * @return return o número do par valor-valor ness árvore.
     */
    // 
    public int tamanho() {
        return tamanho( raiz );
    }

    /**
     * Verifica se a árvore está vazia.
     * 
     * @return true caso esteja vazia, false caso contrário.
     */
    public boolean estaVazia() {
        return raiz == null;
    }

    
    
    /**
     * ***********************************************************************
     * Métodos de busca padrão.
     * ***********************************************************************
     */
    
    /**
     * Verifica se existe um valor associado a umo valor.
     * 
     * @param valor valor a ser consultada
     * @return true caso haja um valor associado à valor, false caso contrário.
     */
    public boolean contem( Tipo valor ) {
        return contem( raiz, valor ) != null;
    }

    /**
     * Verifica se existe um valor associado a umo valor em uma subárvore enraizada em um nó.
     * 
     * @param no nó de origem da consulta
     * @param valor valor a ser consultada
     * @return true caso haja um valor associado à valor, false caso contrário.
     */
    private Tipo contem( No<Tipo> no, Tipo valor ) {
        
        while ( no != null ) {
            
            int comparacao = valor.compareTo( no.valor );
            
            if ( comparacao < 0 ) {
                no = no.esquerda;
            } else if ( comparacao > 0 ) {
                no = no.direita;
            } else {
                return no.valor;
            }
            
        }
        
        return null;
        
    }

    
    
    /**
     * ***********************************************************************
     * Inserção vermelho-preto
     * ***********************************************************************
     */
    
    /**
     * Insere um valor. Caso o valor já exista, não faz nada.
     * 
     * @param valor valor para inserção
     */
    public void inserir( Tipo valor ) {
        
        raiz = inserir( raiz, valor );
        raiz.cor = CorNo.PRETO;
        
        //assert verificar();
        
    }

    /**
     * Insere um valor em uma subárvore enraizada em um nó.
     * Caso o valor já exista, não faz nada.
     * 
     * @param no nó de origem da inserção
     * @param valor valor para inserção
     */
    private No<Tipo> inserir( No<Tipo> no, Tipo valor ) {
        
        if ( no == null ) {
            
            No<Tipo> novoNo = new No<>();
            novoNo.valor = valor;
            novoNo.cor = CorNo.VERMELHO;
            novoNo.n = 1;
            
            return novoNo;
            
        }

        int comparacao = valor.compareTo( no.valor );
        boolean reorganizarNos = true;
        if ( comparacao < 0 ) {
            no.esquerda = inserir( no.esquerda, valor );
        } else if ( comparacao > 0 ) {
            no.direita = inserir( no.direita, valor );
        } else { // não faz nada
            reorganizarNos = false;
        }

        if ( reorganizarNos ) {
            
            // consertando os links inclinados à direita
            if ( isVermelho( no.direita ) && !isVermelho( no.esquerda ) ) {
                no = rotacionarParaEsquerda( no );
            }
            if ( isVermelho( no.esquerda ) && isVermelho( no.esquerda.esquerda ) ) {
                no = rotacionarParaDireita( no );
            }
            if ( isVermelho( no.esquerda ) && isVermelho( no.direita ) ) {
                trocarCor( no );
            }

            no.n = tamanho( no.esquerda ) + tamanho( no.direita ) + 1;
            
        }

        return no;
        
    }

    /**
     * ***********************************************************************
     * Remoção vermelho-preto
     * ***********************************************************************
     */
    
    /**
     * Remove o nó com o menor valor.
     */
    public void removerMinimo() {
        
        if ( estaVazia() ) {
            throw new NoSuchElementException( "Não há nó mínimo - BST underflow" );
        }

        // se ambos os filhos da raiz forem pretos, configura a raiz como vermelho
        if ( !isVermelho( raiz.esquerda ) && !isVermelho( raiz.direita ) ) {
            raiz.cor = CorNo.VERMELHO;
        }

        raiz = removerMinimo( raiz );
        if ( !estaVazia() ) {
            raiz.cor = CorNo.PRETO;
        }
        
        //assert verificar();
        
    }

    /**
     * Remove o nó com o menor valor da subárvore enraizada em um nó.
     */
    private No<Tipo> removerMinimo( No<Tipo> no ) {
        
        if ( no.esquerda == null ) {
            return null;
        }

        if ( !isVermelho( no.esquerda ) && !isVermelho( no.esquerda.esquerda ) ) {
            no = moverVermelhoEsquerda( no );
        }

        no.esquerda = removerMinimo( no.esquerda );
        
        return balancear( no );
        
    }

    /**
     * Remove o nó com o maior valor.
     */
    public void removerMaximo() {
        
        if ( estaVazia() ) {
            throw new NoSuchElementException( "Não há nó máximo - BST underflow" );
        }

        // se ambos os filhos da raiz forem pretos, configura a raiz como vermelho
        if ( !isVermelho( raiz.esquerda ) && !isVermelho( raiz.direita ) ) {
            raiz.cor = CorNo.VERMELHO;
        }

        raiz = removerMaximo( raiz );
        if ( !estaVazia() ) {
            raiz.cor = CorNo.PRETO;
        }
        
        //assert verificar();
        
    }

    /**
     * Remove o nó com o maior valor da subárvore enraizada em um nó.
     */
    private No<Tipo> removerMaximo( No<Tipo> no ) {
        
        if ( isVermelho( no.esquerda ) ) {
            no = rotacionarParaDireita( no );
        }

        if ( no.direita == null ) {
            return null;
        }

        if ( !isVermelho( no.direita ) && !isVermelho( no.direita.esquerda ) ) {
            no = moverVermelhoDireita( no );
        }

        no.direita = removerMaximo( no.direita );

        return balancear( no );
        
    }

    /**
     * Remove um valor. Caso o valor não exista, não faz nada.
     * 
     * @param valor valor a ser removido.
     */
    public void remover( Tipo valor ) {
        
        if ( !contem( valor ) ) {
            return;
        }

        // se ambos os filhos da raiz forem pretos, configura a raiz como vermelho
        if ( !isVermelho( raiz.esquerda ) && !isVermelho( raiz.direita ) ) {
            raiz.cor = CorNo.VERMELHO;
        }

        raiz = remover( raiz, valor );
        if ( !estaVazia() ) {
            raiz.cor = CorNo.PRETO;
        }
        
        //assert verificar();
        
    }

    /**
     * Remove um valor passado de uma subárvore enraizada em um nó. 
     * Caso o valor não exista, não faz nada.
     * 
     * @param valor valor a ser removido.
     */
    private No<Tipo> remover( No<Tipo> no, Tipo valor ) {
        
        //assert contem( h, valor );

        if ( valor.compareTo( no.valor ) < 0 ) {
            
            if ( !isVermelho( no.esquerda ) && !isVermelho( no.esquerda.esquerda ) ) {
                no = moverVermelhoEsquerda( no );
            }
            
            no.esquerda = remover( no.esquerda, valor );
            
        } else {
            
            if ( isVermelho( no.esquerda ) ) {
                no = rotacionarParaDireita( no );
            }
            
            if ( valor.compareTo( no.valor ) == 0 && ( no.direita == null ) ) {
                return null;
            }
            
            if ( !isVermelho( no.direita ) && !isVermelho( no.direita.esquerda ) ) {
                no = moverVermelhoDireita( no );
            }
            
            if ( valor.compareTo( no.valor ) == 0 ) {
                no.valor = minimo( no.direita ).valor;
                no.direita = removerMinimo( no.direita );
            } else {
                no.direita = remover( no.direita, valor );
            }
            
        }
        
        return balancear( no );
        
    }

    
    
    /**
     * ***********************************************************************
     * Métodos utilitários da árvore vermelho-preto
     * ***********************************************************************
     */
    
    /**
     * Faz com que o link inclinado à esquerda se incline para a direita.
     * 
     * @param no nó de origem
     * @return a nova raiz da subárvore.
     */
    private No<Tipo> rotacionarParaDireita( No<Tipo> no ) {
        
        //assert ( no != null ) && isVermelho( no.esquerda );
        
        No<Tipo> novaRaiz = no.esquerda;
        no.esquerda = novaRaiz.direita;
        
        novaRaiz.direita = no;
        novaRaiz.cor = novaRaiz.direita.cor;
        novaRaiz.direita.cor = CorNo.VERMELHO;
        novaRaiz.n = no.n;
        
        no.n = tamanho( no.esquerda ) + tamanho( no.direita ) + 1;
        
        return novaRaiz;
        
    }

    /**
     * Faz com que o link inclinado à direita se incline para a esquerda.
     * 
     * @param no nó de origem
     * @return a nova raiz da subárvore.
     */
    private No<Tipo> rotacionarParaEsquerda( No<Tipo> no ) {
        
        //assert ( h != null ) && isVermelho( h.direita );
        
        No<Tipo> novaRaiz = no.direita;
        no.direita = novaRaiz.esquerda;
        
        novaRaiz.esquerda = no;
        novaRaiz.cor = novaRaiz.esquerda.cor;
        novaRaiz.esquerda.cor = CorNo.VERMELHO;
        novaRaiz.n = no.n;
        
        no.n = tamanho( no.esquerda ) + tamanho( no.direita ) + 1;
        
        return novaRaiz;
        
    }

    /**
     * Inverte a cor de um nó e de seus filhos.
     * 
     * @param no nó a ser alterado.
     */
    private void trocarCor( No<Tipo> no ) {
        
        // condição de existência: no precisa ter cor oposta a seus filhos
        //assert ( no != null ) && ( no.esquerda != null ) && ( no.direita != null );
        //assert ( !isVermelho( no ) && isVermelho( no.esquerda ) && isVermelho( no.direita ) )
        //        || ( isVermelho( no ) && !isVermelho( no.esquerda ) && !isVermelho( no.direita ) );
        
        no.cor = no.cor == CorNo.VERMELHO ? CorNo.PRETO : CorNo.VERMELHO;
        no.esquerda.cor = no.esquerda.cor == CorNo.VERMELHO ? CorNo.PRETO : CorNo.VERMELHO;
        no.direita.cor = no.direita.cor == CorNo.VERMELHO ? CorNo.PRETO : CorNo.VERMELHO;
        
    }

    /**
     * Assumindo que o nó é vermelho e ambos os seus filhos são pretos, faz
     * com que no.esquerda ou um de seus filhos seja vermelho.
     * 
     * @param no nó a ser movido.
     * @return o nó modificado.
     */
    private No<Tipo> moverVermelhoEsquerda( No<Tipo> no ) {
        
        //assert ( no != null );
        //assert isVermelho( no ) && !isVermelho( no.esquerda ) && !isVermelho( no.esquerda.esquerda );

        trocarCor( no );
        
        if ( isVermelho( no.direita.esquerda ) ) {
            no.direita = rotacionarParaDireita( no.direita );
            no = rotacionarParaEsquerda( no );
            //trocarCor( no );
        }
        
        return no;
        
    }

    /**
     * Assumindo que o nó é vermelho e ambos os seus filhos são pretos, faz
     * com que no.direita ou um de seus filhos seja vermelho.
     * 
     * @param no nó a ser movido.
     * @return o nó modificado.
     */
    private No<Tipo> moverVermelhoDireita( No<Tipo> no ) {
        
        //assert ( h != null );
        //assert isVermelho( h ) && !isVermelho( h.direita ) && !isVermelho( h.direita.esquerda );
        
        trocarCor( no );
        
        if ( isVermelho( no.esquerda.esquerda ) ) {
            no = rotacionarParaDireita( no );
            //trocarCor( no );
        }
        
        return no;
        
    }

    /**
     * Recupera a condição de existência (invariante) para a árvore vermelho-preto.
     * 
     * @param no nó de origem.
     * @return o nó modificado.
     */
    private No<Tipo> balancear( No<Tipo> no ) {
        
        //assert ( no != null );

        if ( isVermelho( no.direita ) ) {
            no = rotacionarParaEsquerda( no );
        }
        
        if ( isVermelho( no.esquerda ) && isVermelho( no.esquerda.esquerda ) ) {
            no = rotacionarParaDireita( no );
        }
        
        if ( isVermelho( no.esquerda ) && isVermelho( no.direita ) ) {
            trocarCor( no );
        }

        no.n = tamanho( no.esquerda ) + tamanho( no.direita ) + 1;
        
        return no;
        
    }

    
    
    /**
     * ***********************************************************************
     * Métodos utilitários
     * ***********************************************************************
     */
    
    /**
     * Altura da árvore.
     * @return Retorna a altura da árvore, 0 se vazia.
     */
    public int altura() {
        return altura( raiz );
    }

    private int altura( No<Tipo> x ) {
        
        if ( x == null ) {
            return 0;
        }
        
        return 1 + Math.max( altura( x.esquerda ), altura( x.direita ) );
        
    }

    /**
     * ***********************************************************************
     * Métodos para a tabela de símbolos ordenada
     * ***********************************************************************
     */
    
    /**
     * Retorna o menor valor.
     * 
     * @return o menor valor, null caso o valor não exista.
     */
    public Tipo minimo() {
        
        if ( estaVazia() ) {
            return null;
        }
        
        return minimo( raiz ).valor;
        
    }

    /**
     * Retorna o menor valor em uma subárvore enraizada em um nó.
     * 
     * @param no nó de origem.
     * @return o menor valor, null caso o valor não exista.
     */
    private No<Tipo> minimo( No<Tipo> no ) {
        
        //assert x != null;
        
        if ( no.esquerda == null ) {
            return no;
        } else {
            return minimo( no.esquerda );
        }
        
    }

    /**
     * Retorna o maior valor.
     * 
     * @return o maior valor, null caso o valor não exista.
     */
    public Tipo maximo() {
        
        if ( estaVazia() ) {
            return null;
        }
        
        return maximo( raiz ).valor;
        
    }

    /**
     * Retorna o maior valor em uma subárvore enraizada em um nó.
     * 
     * @param no nó de origem.
     * @return o maior valor, null caso o valor não exista.
     */
    private No<Tipo> maximo( No<Tipo> no ) {
        
        //assert no != null;
        
        if ( no.direita == null ) {
            return no;
        } else {
            return maximo( no.direita );
        }
        
    }

    /**
     * Retorna o maior valor menor ou igual a umo valor.
     * 
     * @param valor valor a ser consultada
     * @return o maior valor menor ou igual a umo valor, null caso o valor não exista.
     */
    public Tipo chao( Tipo valor ) {
        
        No<Tipo> x = chao( raiz, valor );
        
        if ( x == null ) {
            return null;
        } else {
            return x.valor;
        }
        
    }

    /**
     * Retorna o maior valor menor ou igual a umo valor em uma subárvore enraizada em um nó.
     * 
     * @param no nó de origem
     * @param valor valor a ser consultada
     * @return o maior valor menor ou igual a umo valor, null caso o valor não exista.
     */
    private No<Tipo> chao( No<Tipo> no, Tipo valor ) {
        
        if ( no == null ) {
            return null;
        }
        
        int comparacao = valor.compareTo( no.valor );
        
        if ( comparacao == 0 ) {
            return no;
        }
        if ( comparacao < 0 ) {
            return chao( no.esquerda, valor );
        }
        
        No<Tipo> t = chao( no.direita, valor );
        if ( t != null ) {
            return t;
        } else {
            return no;
        }
        
    }

    /**
     * Retorna o menor valor maior ou igual a umo valor.
     * 
     * @param valor valor a ser consultada
     * @return o menor valor maior ou igual a umo valor, null caso o valor não exista.
     */
    public Tipo teto( Tipo valor ) {
        
        No<Tipo> x = teto( raiz, valor );
        
        if ( x == null ) {
            return null;
        } else {
            return x.valor;
        }
        
    }

    /**
     * Retorna o menor valor maior ou igual a umo valor em uma subárvore enraizada em um nó.
     * 
     * @param no nó de origem
     * @param valor valor a ser consultada
     * @return o menor valor maior ou igual a umo valor, null caso o valor não exista.
     */
    private No<Tipo> teto( No<Tipo> x, Tipo valor ) {
        
        if ( x == null ) {
            return null;
        }
        
        int comparacao = valor.compareTo( x.valor );
        
        if ( comparacao == 0 ) {
            return x;
        }
        if ( comparacao > 0 ) {
            return teto( x.direita, valor );
        }
        
        No<Tipo> t = teto( x.esquerda, valor );
        if ( t != null ) {
            return t;
        } else {
            return x;
        }
        
    }

    /**
     * Retorna o valor que tem o determinado ranque.
     * 
     * @param k ranque a ser verificado
     * @return o valor com o ranque passado, null caso o ranque não exista
     */
    public Tipo selecionar( int k ) {
        
        if ( k < 0 || k >= tamanho() ) {
            return null;
        }
        
        No<Tipo> x = selecionar( raiz, k );
        return x.valor;
        
    }

    /**
     * Retorna o valor que tem o determinado ranque em uma subárvore enraizada em um nó
     * 
     * @param no nó de origem
     * @param k ranque a ser verificado
     * @return o valor com o ranque passado, null caso o ranque não exista
     */
    private No<Tipo> selecionar( No<Tipo> no, int k ) {
        
        //assert no != null;
        //assert k >= 0 && k < tamanho( no );
        
        int t = tamanho( no.esquerda );
        
        if ( t > k ) {
            return selecionar( no.esquerda, k );
        } else if ( t < k ) {
            return selecionar( no.direita, k - t - 1 );
        } else {
            return no;
        }
        
    }

    /**
     * Obtém o ranque de umo valor.
     * 
     * @param valor valor a ser consultada
     * @return o ranque do valor, 0 caso não exista
     */
    public int ranque( Tipo valor ) {
        return ranque( raiz, valor );
    }

    /**
     * Obtém o ranque de umo valor em uma subárvore enraizada em um nó.
     * 
     * @param no nó de origem
     * @param valor valor a ser consultada
     * @return o ranque do valor, 0 caso não exista
     */
    private int ranque( No<Tipo> no, Tipo valor ) {
        
        if ( no == null ) {
            return 0;
        }
        
        int comparacao = valor.compareTo( no.valor );
        
        if ( comparacao < 0 ) {
            return ranque( no.esquerda, valor );
        } else if ( comparacao > 0 ) {
            return 1 + tamanho( no.esquerda ) + ranque( no.direita, valor );
        } else {
            return tamanho( no.esquerda );
        }
        
    }

    
    
    /**
     * *********************************************************************
     * Contagem e busca em intervalos.
     * *********************************************************************
     */
    
    /**
     * Obtém todos os valores na forma de um iterável.
     * 
     * @return todos os valores.
     */
    public Queue<Tipo> valores() {
        return valores( minimo(), maximo() );
    }

    /**
     * Obtém todos os valores de um intervalo na forma de um iterável.
     * 
     * @param menor valor de ínicio
     * @param maior valor de fim
     * @return todos os valores no intervalo.
     */
    public Queue<Tipo> valores( Tipo menor, Tipo maior ) {
        
        Queue<Tipo> fila = new LinkedQueue<Tipo>();
        
        if ( estaVazia() || menor.compareTo( maior ) > 0 ) {
            return fila;
        }
        
        valores( raiz, fila, menor, maior );
        
        return fila;
        
    }

    /**
     * Obtém todos os valores de um intervalo na forma de um iterável em uma subárvore
     * enraizada em um nó.
     * 
     * @param no nó de origem
     * @param menor valor de ínicio
     * @param maior valor de fim
     * @return todos os valores no intervalo.
     */
    private void valores( No<Tipo> no, Queue<Tipo> fila, Tipo menor, Tipo maior ) {
        
        if ( no == null ) {
            return;
        }
        
        int comparacaoMenor = menor.compareTo( no.valor );
        int comparacaoMaior = maior.compareTo( no.valor );
        
        if ( comparacaoMenor < 0 ) {
            valores( no.esquerda, fila, menor, maior );
        }
        if ( comparacaoMenor <= 0 && comparacaoMaior >= 0 ) {
            fila.enqueue( no.valor );
        }
        if ( comparacaoMaior > 0 ) {
            valores( no.direita, fila, menor, maior );
        }
        
    }

    /**
     * Obtém a quantidade de valores entre duos valores.
     * 
     * @param menor valor de início
     * @param maior valor de fim
     * @return a quantidade de valores entre duos valores
     */
    public int tamanho( Tipo menor, Tipo maior ) {
        
        if ( menor.compareTo( maior ) > 0 ) {
            return 0;
        }
        
        if ( contem( maior ) ) {
            return ranque( maior ) - ranque( menor ) + 1;
        } else {
            return ranque( maior ) - ranque( menor );
        }
        
    }

    /**
     * Verifica a integridade da árvore vermelho-preto
     * 
     * @return true caso a árvore este íntegra, false caso contrário.
     */
    private boolean verificar() {
        
        if ( !isABB() ) {
            System.out.println( "Não possui ordem simétrica" );
        }
        
        if ( !isTamanhoConsistente() ) {
            System.out.println( "Contagens das subárvores não está consistente" );
        }
        
        if ( !isRanqueConsistente() ) {
            System.out.println( "Ranques não consistentes" );
        }
        
        if ( !is23() ) {
            System.out.println( "Não é uma árvore 2-3" );
        }
        
        if ( !isBalanceada() ) {
            System.out.println( "Não está balanceada" );
        }
        
        return isABB() && 
               isTamanhoConsistente() && 
               isRanqueConsistente() && 
               is23() && 
               isBalanceada();
        
    }

    /**
     * Verifica se é uma árvore binária de busca (menores à esquerda, maiores à direita)
     * 
     * @return true caso seja uma ABB, false caso contrário.
     */
    private boolean isABB() {
        return isABB( raiz, null, null );
    }

    /**
     * Verifica se a subárvore enraizada em um nó tem os valores em ordem entre duas 
     * valores.
     * Credito: Bob Dondero's
     * 
     * @param no no de origem
     * @param min valor mínima
     * @param max valor máxima
     * @return true caso seja uma ABB entre os valores, false caso contrário.
     */
    private boolean isABB( No<Tipo> no, Tipo min, Tipo max ) {
        
        if ( no == null ) {
            return true;
        }
        
        if ( min != null && no.valor.compareTo( min ) <= 0 ) {
            return false;
        }
        
        if ( max != null && no.valor.compareTo( max ) >= 0 ) {
            return false;
        }
        
        return isABB( no.esquerda, min, no.valor ) && isABB( no.direita, no.valor, max );
        
    }

    /**
     * Verifica se as contagens estão certas.
     * 
     * @return true caso sim, false caso contrário
     */
    private boolean isTamanhoConsistente() {
        return isTamanhoConsistente( raiz );
    }

    /**
     * Verifica se as contagens estão certas em uma subárvore enraizada em um nó.
     * 
     * @return true caso sim, false caso contrário
     */
    private boolean isTamanhoConsistente( No<Tipo> no ) {
        
        if ( no == null ) {
            return true;
        }
        
        if ( no.n != tamanho( no.esquerda ) + tamanho( no.direita ) + 1 ) {
            return false;
        }
        
        return isTamanhoConsistente( no.esquerda ) && isTamanhoConsistente( no.direita );
        
    }

    /**
     * Verifica se os ranques são consistentes.
     * 
     * @return true caso sim, false caso contrário
     */
    private boolean isRanqueConsistente() {
        
        for ( int i = 0; i < tamanho(); i++ ) {
            if ( i != ranque( selecionar( i ) ) ) {
                return false;
            }
        }
        
        Queue<Tipo> fila = valores();
        while ( fila.isEmpty()) {
            Tipo valor = fila.dequeue();
            if ( valor.compareTo( selecionar( ranque( valor ) ) ) != 0 ) {
                return false;
            }
        }
        
        return true;
        
    }

    /**
     * Verifica se esta árvore não tem links vermelhos à direita e se tem no máximo
     * um link vermelho seguido à esquerda um uma linha de qualquer caminho.
     * 
     * @return true caso sim, false caso contrário
     */
    private boolean is23() {
        return is23( raiz );
    }

    /**
     * Verifica se uma subárvore enraizada em um nó não tem links vermelhos à 
     * direita e se tem no máximo um link vermelho seguido à esquerda um uma 
     * linha de qualquer caminho.
     * 
     * @return true caso sim, false caso contrário
     */
    private boolean is23( No<Tipo> no ) {
        
        if ( no == null ) {
            return true;
        }
        
        if ( isVermelho( no.direita ) ) {
            return false;
        }
        
        if ( no != raiz && isVermelho( no ) && isVermelho( no.esquerda ) ) {
            return false;
        }
        
        return is23( no.esquerda ) && is23( no.direita );
        
    }

    /**
     * Verifica se todos os caminhos da raiz às folhas tem a mesma quantidade
     * de links pretos.
     * 
     * @return true caso sim, false caso contrário
     */
    private boolean isBalanceada() {
        
        // quantidade de links pretos em um caminho da raiz até uma folha.
        
        int black = 0;
        No<Tipo> atual = raiz;
        
        while ( atual != null ) {
            
            if ( !isVermelho( atual ) ) {
                black++;
            }
            
            atual = atual.esquerda;
            
        }
        
        return isBalanceada( raiz, black );
        
    }

    /**
     * Verifica se todos os caminhos a partir de um nó até uma folha folhas tem
     * a mesma quantidade de links pretos.
     * 
     * @return true caso sim, false caso contrário
     */
    private boolean isBalanceada( No<Tipo> no, int black ) {
        
        if ( no == null ) {
            return black == 0;
        }
        
        if ( !isVermelho( no ) ) {
            black--;
        }
        
        return isBalanceada( no.esquerda, black ) && isBalanceada( no.direita, black );
        
    }

    /**
     * Esvazia a árvore.
     */
    public void esvaziar() {
        raiz = desalocar( raiz );
    }

    /*
     * Método privado para remoção de todos os itens de forma recursiva.
     */
    private No<Tipo> desalocar( No<Tipo> no ) {

        if ( no != null ) {
            no.esquerda = desalocar( no.esquerda );
            no.direita = desalocar( no.direita );
        }

        return null;

    }
    
    /**
     * Cria uma representação em String da árvore.
     * Esta representação apresenta os elementos na ordem do percurso em ordem.
     */
    @Override
    public String toString() {
        
        StringBuilder sb = new StringBuilder();
        
        /*if ( !estaVazia() ) {
            
            for ( No<Tipo> no : RBTreeTraversals.percorrer( this, TraversalTypes.IN_ORDER ) ) {
                
                if ( no == raiz ) {
                    sb.append( no ).append( " <- raiz\n" );
                } else {
                    sb.append( no ).append( "\n" );
                }
                
            }
            
        } else {
            sb.append( "árvore vazia!\n" );
        }*/
        
        return sb.toString();
        
    }
    
    /**
     * Método para obter o nó da raiz.
     * Não deveria existir, mas é necessário para o simulador.
     * 
     * @return Nó com a raiz da árvore.
     */
    public No<Tipo> getRaiz() {
        return raiz;
    }
    
}
