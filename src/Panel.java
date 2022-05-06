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

    private int keyboard[] = new int[26];

    private int userCharX=0;
    private int userCharY=0;

    public Words words = new Words();

    private Frame frame;

    private String checkInput;

    private char[] gameWord;

    private final int headerOffset=50;
    private final int headboardOffset= 420;

    public Panel(Frame frame) throws Exception{
        super();
        this.setBounds(0,0,310,520);
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

        for(int i=0;i<26;i++){
            keyboard[i]=-1;
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

        for(int i=0;i<26;i++){
            keyboard[i]=-1;
        }

        repaint();
    }

    protected void paintComponent(Graphics g){
        paintHeader(g);
        paintBoard(g);
        paintKeyboard(g);
    }

    private void paintHeader(Graphics g){
        String headerText= new String();

        if(lose){
            g.setColor(new Color(255, 105, 97));
            for(int i=0;i<5;i++){
                headerText+= gameWord[i];
            }
            
        }else if(win){
            g.setColor(new Color(119,221,119));
            for(int i=0;i<5;i++){
                headerText+= gameWord[i];
            }
        }else{
            g.setColor(new Color(240,234,214));
            headerText = "WORDLE";
        }

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
                        keyboard[(int)(userWords[i][j])-65] = 0;

                        if(userWords[i][j]== gameWord[j]){
                            keyboard[(int)(userWords[i][j])-65] = 2;

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
                            if(keyboard[(int)(userWords[i][j])-65]!=2){
                                keyboard[(int)(userWords[i][j])-65]=1;
                            }
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

    private void paintKeyboard(Graphics g){
        g.setColor(new Color(240,234,214));
        g.fillRect(0, 420, 310, this.getSize().height-headboardOffset);

        Graphics2D g2d = (Graphics2D) g;
        AffineTransform affinetransform = new AffineTransform(); 
        FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 20);

        int textwidth = (int)(font.getStringBounds(String.valueOf(userWords[0][0]), frc).getWidth());
        int textheight = (int)(font.getStringBounds(String.valueOf(userWords[0][0]), frc).getHeight());

        g2d.setFont(font);

        //keyboard drawing logic, tedious but idk what else i can do
        for(int i=0;i<3;i++){
            for(int j=0;j<10;j++){
                //first row
                if(i==0 && j==0){
                    if(keyboard[16]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[16]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[16]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[16]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("Q", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==1){
                    if(keyboard[22]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[22]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[22]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[22]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("W", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==2){
                    if(keyboard[4]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[4]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[4]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[4]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("E", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==3){
                    if(keyboard[17]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[17]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[17]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[17]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("R", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==4){
                    if(keyboard[19]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[19]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[19]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[19]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("T", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==5){
                    if(keyboard[24]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[24]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[24]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[24]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("Y", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==6){
                    if(keyboard[20]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[20]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[20]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[20]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("U", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==7){
                    if(keyboard[8]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[8]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[8]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[8]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("I", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==8){
                    if(keyboard[14]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[14]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[14]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[14]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("O", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==0 && j==9){
                    if(keyboard[15]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[15]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[15]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[15]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("P", (j+1)*10+j*20+20/2-textwidth/2,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20,(i+1)*10+i*20+headboardOffset, 20, 20);
                }

                //second row
                if(i==1 && j==0){
                    if(keyboard[0]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[0]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[0]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[0]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("A", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==1 && j==1){
                    if(keyboard[18]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[18]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[18]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[18]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("S", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==1 && j==2){
                    if(keyboard[3]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[3]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[3]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[3]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("D", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==1 && j==3){
                    if(keyboard[5]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[5]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[5]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[5]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("F", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==1 && j==4){
                    if(keyboard[6]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[6]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[6]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[6]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("G", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==1 && j==5){
                    if(keyboard[7]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[7]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[7]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[7]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("H", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==1 && j==6){
                    if(keyboard[9]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[9]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[9]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[9]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("J", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==1 && j==7){
                    if(keyboard[10]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[10]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[10]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[10]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("K", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==1 && j==8){
                    if(keyboard[11]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[11]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[11]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[11]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("L", (j+1)*10+j*20+20/2-textwidth/2+10,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+10,(i+1)*10+i*20+headboardOffset, 20, 20);
                }

                //third row
                if(i==2 && j==0){
                    if(keyboard[25]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[25]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[25]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[25]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("Z", (j+1)*10+j*20+20/2-textwidth/2+40,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==2 && j==1){
                    if(keyboard[23]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[23]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[23]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[23]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("X", (j+1)*10+j*20+20/2-textwidth/2+40,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==2 && j==2){
                    if(keyboard[2]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[2]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[2]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[2]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("C", (j+1)*10+j*20+20/2-textwidth/2+40,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==2 && j==3){
                    if(keyboard[21]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[21]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[21]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[21]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("V", (j+1)*10+j*20+20/2-textwidth/2+40,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==2 && j==4){
                    if(keyboard[1]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[1]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[1]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[1]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("B", (j+1)*10+j*20+20/2-textwidth/2+40,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==2 && j==5){
                    if(keyboard[13]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[13]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[13]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[13]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("N", (j+1)*10+j*20+20/2-textwidth/2+40,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                if(i==2 && j==6){
                    if(keyboard[12]==0){
                        g.setColor(new Color(45,45,45));
                    }else if(keyboard[12]==1){
                        g.setColor(new Color(255,211,0));
                    }else if(keyboard[12]==2){
                        g.setColor(new Color(43,83,41));
                    }else if(keyboard[12]==-1){
                        g.setColor(new Color(240,234,214));
                    }
                    g.fillRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);

                    g.setColor(new Color(96,96,96));
                    g2d.drawString("M", (j+1)*10+j*20+20/2-textwidth/2+40,(i+1)*10+i*20+20-textheight/6+headboardOffset);
                    g.drawRect((j+1)*10+j*20+40,(i+1)*10+i*20+headboardOffset, 20, 20);
                }
                
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
        if((e.getKeyCode() == KeyEvent.VK_ENTER)){
            if(win||lose){
                gameReset();
            }
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

                        if(userCharY==5 && words.checkExist(checkInput.toLowerCase())==true && checkWin != 5){
                            lose=true;
                        }
                        
                        userCharY ++;
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
