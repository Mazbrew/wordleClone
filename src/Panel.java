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

    public Words words = new Words();

    private Frame frame;

    public Panel(Frame frame) throws Exception{
        super();
        this.setBounds(0,0,310,370);
        this.setVisible(true);

        this.setFocusable(true);
        this.addKeyListener(this);

        this.frame = frame;
        this.frame.add(this);
        this.requestFocus();

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                userWords[i][j]=' ';
            }
        }
    }

    public void gameOver(){
        System.out.println("Game Over");
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

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(userWords[i][j]!=' '){
                    if(userCharY>i){
                        g.setColor(new Color(45,45,45));
                        g.fillRect((j+1)*10+j*50,(i+1)*10+i*50, 50, 50);
                    }
                    g.setColor(Color.white);
                    g2d.drawString(String.valueOf(userWords[i][j]), (j+1)*10+j*50+50/2-textwidth/2,(i+1)*10+i*50+50-textheight/6);
                }
                g.drawRect((j+1)*10+j*50,(i+1)*10+i*50, 50, 50);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // key logic
        if(e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z' || e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z'){
            if(userWords[userCharY][userCharX]==' '){
                userWords[userCharY][userCharX] = Character.toUpperCase(e.getKeyChar());
            }
            if(userCharX !=4){  
                userCharX++;
            }
            repaint();
        }

        // backspace logic
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            if(userCharX !=0){
                userWords[userCharY][userCharX--] = ' ';
            }
            else if(userCharX ==0){
                userWords[userCharY][userCharX] = ' ';
            }
            repaint();
        }

        // enter logic
        String checkInput = new String();
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            if(userCharY <= 5){
                if(userWords[userCharY][4] != ' '){
                    for(int i=0;i<5;i++){
                        checkInput += userWords[userCharY][i];
                        System.out.println(checkInput);
                    }

                    if(words.checkExist(checkInput.toLowerCase())==false){
                        frame.shake();
                    }else{
                        userCharX = 0;
                        userCharY ++;
                    }  

                    if(userWords[5][4] != ' '){
                        gameOver();
                    }
                    repaint();
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
