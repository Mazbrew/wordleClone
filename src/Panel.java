import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Panel extends JPanel implements KeyListener{
    private char userWords[][] = new char[6][5];

    private int userCharX=0;
    private int userCharY=0;

    public Panel(){
        super();
        this.setBounds(0,0,310,370);
        this.setVisible(true);

        this.setFocusable(true);
        this.addKeyListener(this);

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                userWords[i][j]=' ';
            }
        }
    }

    protected void paintComponent(Graphics g){
        g.setColor(new Color(96,96,96));
        g.fillRect(0, 0, this.getSize().width, this.getSize().height);
        paintBoard(g);
    }

    private void paintBoard(Graphics g){
        g.setColor(Color.white);
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform affinetransform = new AffineTransform(); 
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 50);

        int textwidth = (int)(font.getStringBounds(String.valueOf(userWords[0][0]), frc).getWidth());
        int textheight = (int)(font.getStringBounds(String.valueOf(userWords[0][0]), frc).getHeight());

        g2d.setFont(font);
        
        // Draw a string such that its base line is at x, y
        
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                g.drawRect((j+1)*10+j*50,(i+1)*10+i*50, 50, 50);
                
                if(userWords[i][j]!=' '){
                    g2d.drawString(String.valueOf(userWords[i][j]), (j+1)*10+j*50+50/2-textwidth/2,(i+1)*10+i*50+50-textheight/6);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z' || e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z'){
            if(userWords[userCharY][userCharX]==' '){
                userWords[userCharY][userCharX] = Character.toUpperCase(e.getKeyChar());
            }
            if(userCharX !=4){  
                userCharX++;
            }
            System.out.println(userCharX);
            
            repaint();
        }

        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
