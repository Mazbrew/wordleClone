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
    private char userWords[][] ;

    public Panel(){
        super();
        this.setBounds(0,0,310,370);
        this.setVisible(true);

        userWords= new char[][]{
        {'T','E','S','T','S'},
        {'T','E','S','T','S'},
        {'T','E','S','T','S'},
        {'T','E','S','T','S'},
        {'T','E','S','T','S'},
        {'T','E','S','T','S'}};

        this.addKeyListener(this);
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
                g2d.drawString(String.valueOf(userWords[i][j]), (j+1)*10+j*50+50/2-textwidth/2,(i+1)*10+i*50+50/2-textheight/2);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        
    }
}
