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
    private boolean lose = false;
    private boolean win = false;

    private int userCharX=0;
    private int userCharY=0;

    public Words words = new Words();

    private Frame frame;

    private String checkInput;

    private char[] gameWord;

    private final int headerOffset=50;

    public Panel(Frame frame) throws Exception{
        super();
        this.setBounds(0,0,310,420);
        this.setVisible(true);

        this.setFocusable(true);
        this.addKeyListener(this);

        this.frame = frame;
        this.frame.add(this);
        this.requestFocus();

        gameWord = words.generateWord();

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                userWords[i][j]=' ';
            }
        }
    }

    private void gameReset(){
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                userWords[i][j]=' ';
            }
        }

        gameWord = words.generateWord();

        userCharX =0;
        userCharY =0;

        lose= false;
        win = false;

        repaint();
    }

    protected void paintComponent(Graphics g){
        if(lose){
            paintLoseHeader(g);
        }else if(win){
            paintWinHeader(g);
        }else{
            paintHeader(g);
        }
        
        paintBoard(g);
    }

    private void paintWinHeader(Graphics g){
        String headerText= new String();

        for(int i=0;i<5;i++){
            headerText+= gameWord[i];
        }

        g.setColor(new Color(119, 221, 119));
        g.fillRect(0, 0, this.getSize().width, 50);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform affinetransform = new AffineTransform(); 
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 50);

        int textwidth = (int)(font.getStringBounds(headerText, frc).getWidth());

        g2d.setFont(font);

        g.setColor(new Color(96,96,96));
        g2d.drawString(headerText, this.getSize().width/2-textwidth/2, headerOffset-headerOffset/6);
    }

    private void paintLoseHeader(Graphics g){
        String headerText= new String();

        for(int i=0;i<5;i++){
            headerText+= gameWord[i];
        }

        g.setColor(new Color(255, 105, 97));
        g.fillRect(0, 0, this.getSize().width, 50);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform affinetransform = new AffineTransform(); 
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 50);

        int textwidth = (int)(font.getStringBounds(headerText, frc).getWidth());

        g2d.setFont(font);

        g.setColor(new Color(96,96,96));
        g2d.drawString(headerText, this.getSize().width/2-textwidth/2, headerOffset-headerOffset/6);
    }

    private void paintHeader(Graphics g){
        String headerText = "WORDLE";
        g.setColor(new Color(240,234,214));
        g.fillRect(0, 0, this.getSize().width, 50);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform affinetransform = new AffineTransform(); 
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 50);

        int textwidth = (int)(font.getStringBounds(headerText, frc).getWidth());

        g2d.setFont(font);

        g.setColor(new Color(96,96,96));
        g2d.drawString(headerText, this.getSize().width/2-textwidth/2, headerOffset-headerOffset/6);
    }

    private void paintBoard(Graphics g){
        g.setColor(new Color(96,96,96));
        g.fillRect(0, 50, this.getSize().width, this.getSize().height-headerOffset);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform affinetransform = new AffineTransform(); 
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 50);

        int textwidth = (int)(font.getStringBounds(String.valueOf(userWords[0][0]), frc).getWidth());
        int textheight = (int)(font.getStringBounds(String.valueOf(userWords[0][0]), frc).getHeight());

        g2d.setFont(font);

        //draw green and grey tiles
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(userWords[i][j]!=' '){
                    if(i<userCharY){
                        g.setColor(new Color(45,45,45));
                        g.fillRect((j+1)*10+j*50,(i+1)*10+i*50+headerOffset, 50, 50);

                        if(userWords[i][j]== gameWord[j]){
                            g.setColor(new Color(43,83,41));
                            g.fillRect((j+1)*10+j*50,(i+1)*10+i*50+headerOffset, 50, 50);
                        }
                    }
                }
            }
        }

        //draw yellow tiles
        int inword=0;
        int accounted = 0;

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                inword=0;
                accounted =0;
                if(i<userCharY){
                    //checks for instances of the letter within the word
                    for(int z=0;z<5;z++){
                        if(userWords[i][j]==gameWord[z]){
                            inword++;
                        }
                        

                        if(userWords[i][j]==userWords[i][z] && userWords[i][z]==gameWord[z]){
                            accounted++;
                        }
                    }

                    //checks if the letter has already be accounted for
                    for(int z=0;z<5;z++){
                        if(userWords[i][j]==userWords[i][z] && userWords[i][z]!=gameWord[z] && inword!=accounted){
                            g.setColor(new Color(255,211,0));
                            g.fillRect((z+1)*10+z*50,(i+1)*10+i*50+headerOffset, 50, 50);
                            accounted++;
                        }
                    }
                }
            }
        }

        //draw the words and rectangles
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                g.setColor(Color.white);
                g2d.drawString(String.valueOf(userWords[i][j]), (j+1)*10+j*50+50/2-textwidth/2,(i+1)*10+i*50+50-textheight/6+headerOffset);
                g.setColor(Color.white);
                g.drawRect((j+1)*10+j*50,(i+1)*10+i*50+headerOffset, 50, 50);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // key logic
        if((e.getKeyChar() >= 'A' && e.getKeyChar() <= 'Z' || e.getKeyChar() >= 'a' && e.getKeyChar() <= 'z') && (!win && !lose)){
            if(userWords[userCharY][userCharX]==' '){
                userWords[userCharY][userCharX] = Character.toUpperCase(e.getKeyChar());
            }
            if(userCharX !=4){  
                userCharX++;
            }
            repaint();
        }

        // backspace logic
        if((e.getKeyCode() == KeyEvent.VK_BACK_SPACE) && (!win && !lose)){
            if(userWords[userCharY][userCharX]==' ' && userCharX!=0){
                userCharX--;
            }
            if(userCharX !=0){
                userWords[userCharY][userCharX--] = ' ';
            }
            else if(userCharX ==0){
                userWords[userCharY][userCharX] = ' ';
            }
            repaint();

            if(userWords[userCharY][userCharX]!=' ' && userCharX!=4){
                userCharX++;
            }
        }

        // enter logic
        int checkWin=0;
        if((e.getKeyCode() == KeyEvent.VK_ENTER) && (!win && !lose)){
            if(userCharY <= 5){
                if(userWords[userCharY][4] != ' '){
                    checkInput=new String();
                    for(int i=0;i<5;i++){
                        checkInput += userWords[userCharY][i];
                    }

                    if(words.checkExist(checkInput.toLowerCase())==false){
                        frame.shake();
                    }else{
                        userCharX = 0;
                        for(int i=0;i<5;i++){
                            if(userWords[userCharY][i]==gameWord[i]){
                                checkWin++;
                            }
                        }
                        if(checkWin==5){
                            win=true;
                        }
                        
                        userCharY ++;
                    }  


                    if(userWords[5][4] != ' '){
                        lose = true;
                    }
                    repaint();
                }
            }
        }

        if(e.getKeyCode() == KeyEvent.VK_F5){
            gameReset();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
}
