package aesd.algorithms.backtracking.tests;

import aesd.algorithms.backtracking.LabyrinthSolver;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Teste do resolvedor de labirintos com GUI.
 * 
 * @author Prof. Dr. David Buzatto
 */
public class LabyrinthSolverTestGUI {
    
    public static void main( String[] args ) {
        test02();
        test01();
    }
    
    private static void test01() {
        
        boolean[][] labyrinth = new boolean[][]{
            { false, false, false, false,  true, false },
            { false,  true,  true, false, false, false },
            { false, false, false, false,  true, false },
            {  true, false,  true,  true,  true,  true },
            { false, false, false, false, false, false }
        };
        
        LabyrinthSolver ls = new LabyrinthSolver( labyrinth, 0, 0, 4, 5 );
        new Janela( ls ).setVisible( true );
        
    }
    
    private static void test02() {
        
        boolean[][] labyrinth = new boolean[][]{
            { false, false, false,  true, false, false, false, false, false, false, false, false, false, false, false, false },
            { false, false,  true,  true, false, false,  true, false, false, false,  true,  true,  true,  true,  true,  true },
            { false, false, false,  true, false, false,  true, false, false, false, false, false, false, false, false, false },
            { false,  true,  true,  true, false, false,  true, false, false, false, false, false,  true,  true,  true, false },
            { false, false,  true, false,  true, false,  true, false, false, false,  true, false,  true, false, false, false },
            { false, false,  true, false,  true, false,  true, false,  true, false,  true, false,  true, false,  true,  true },
            { false, false,  true, false,  true, false,  true, false,  true, false,  true, false,  true, false, false, false },
            { false,  true,  true, false,  true,  true, false, false,  true, false,  true, false,  true,  true,  true, false },
            { false, false, false, false, false,  true, false, false,  true, false,  true, false,  true, false, false, false },
            { false, false, false, false, false,  true, false, false,  true, false,  true, false,  true, false,  true,  true },
            { false, false,  true,  true,  true,  true, false, false,  true, false,  true, false,  true, false, false, false },
            { false, false, false, false, false, false, false, false,  true, false, false, false, false, false, false, false },
        };
        
        LabyrinthSolver ls = new LabyrinthSolver( labyrinth, 0, 0, 4, 5 );
        new Janela( ls ).setVisible( true );
        
    }
    
    private static class Janela extends JFrame {
        
        final int CELL_SIDE = 40;
        final Color PATH_COLOR = new Color( 209, 231, 246 );
        final Color ARROW_COLOR = new Color( 35, 107, 136 );
        final Color SOURCE_COLOR = new Color( 35, 107, 136 );
        final Color TARGET_COLOR = new Color( 0, 176, 80 );
        
        final BasicStroke GRID_STROKE = new BasicStroke( 1 );
        final BasicStroke ARROW_STROKE = new BasicStroke( 3 );
        
        LabyrinthSolver ls;
        
        public Janela( LabyrinthSolver ls ) {
            
            this.ls = ls;
            
            setTitle( "Labyrinth Solver" );
            setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            setSize( ( ls.getLabyrinth()[0].length + 2 ) * CELL_SIDE, ( ls.getLabyrinth().length + 3 ) * CELL_SIDE );
            setLocationRelativeTo( null );
            
            add( new JPanel() {
                @Override
                protected void paintComponent( Graphics g ) {
                    
                    super.paintComponent( g );
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

                    char[][] output = ls.getOutput();
                    
                    g2d.setStroke( ARROW_STROKE );
                    for ( int i = 0; i < output.length; i++ ) {
                        
                        int y = CELL_SIDE + ( i * CELL_SIDE );
                        
                        for ( int j = 0; j < output[i].length; j++ ) {
                            
                            int x = CELL_SIDE + ( j * CELL_SIDE );
                            
                            if ( output[i][j] == 'x' ) {
                                g2d.setColor( Color.BLACK );
                                g2d.fillRect( x, y, CELL_SIDE, CELL_SIDE );
                            } else if ( output[i][j] != ' ' ) {
                                
                                g2d.setColor( PATH_COLOR );
                                g2d.fillRect( x, y, CELL_SIDE, CELL_SIDE );
                                
                                int r = -1;
                                
                                switch ( output[i][j] ) {
                                    case 's':
                                        g2d.setColor( SOURCE_COLOR );
                                        g2d.fillRect( x, y, CELL_SIDE, CELL_SIDE );
                                        break;
                                    case 'd':
                                        r = 0;
                                        break;
                                    case 'b':
                                        r = 90;
                                        break;
                                    case 'e':
                                        r = 180;
                                        break;
                                    case 'c':
                                        r = 270;
                                        break;
                                    case 't':
                                        g2d.setColor( TARGET_COLOR );
                                        g2d.fillRect( x, y, CELL_SIDE, CELL_SIDE );
                                        break;
                                }
                                
                                if ( r != - 1 ) {
                                    
                                    Graphics2D g2da = (Graphics2D) g2d.create();
                                    
                                    g2da.setColor( ARROW_COLOR );
                                    
                                    int arrowTip = x + CELL_SIDE / 2 + (int) ( CELL_SIDE * 0.3 );
                                    int xArrow = x + CELL_SIDE / 2;
                                    int yArrow = y + CELL_SIDE / 2;
                                    
                                    g2da.rotate( Math.toRadians( r ), xArrow, yArrow );
                                    
                                    g2da.drawLine( xArrow, 
                                                  yArrow, 
                                                  arrowTip,
                                                  yArrow );
                                    g2da.drawLine( arrowTip, 
                                                  yArrow, 
                                                  arrowTip - 5,
                                                  yArrow - 5 );
                                    g2da.drawLine( arrowTip, 
                                                  yArrow, 
                                                  arrowTip - 5,
                                                  yArrow + 5 );
                                    
                                    g2da.dispose();
                                    
                                }
                            }
                            
                        }
                    }
                    
                    g2d.setStroke( GRID_STROKE );
                    g2d.setColor( Color.BLACK );
                    for ( int i = 0; i <= output.length; i++ ) {
                        g2d.drawLine( CELL_SIDE, 
                                    CELL_SIDE + ( i * CELL_SIDE ),
                                    CELL_SIDE + CELL_SIDE * output[0].length,
                                    CELL_SIDE + ( i * CELL_SIDE ) );
                    }
                    
                    for ( int i = 0; i <= output[0].length; i++ ) {
                        g2d.drawLine( CELL_SIDE + ( i * CELL_SIDE ),
                                    CELL_SIDE,
                                    CELL_SIDE + ( i * CELL_SIDE ),
                                    CELL_SIDE + CELL_SIDE * output.length );
                    }

                    g2d.dispose();

                }
                
            }, BorderLayout.CENTER );
            
        }
        
    }
    
}
