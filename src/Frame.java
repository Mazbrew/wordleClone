import java.awt.Dimension;

import java.awt.*;
import javax.swing.JFrame;


public class Frame extends JFrame {
    public Frame(){
        super();
        this.setResizable(false);
        this.setTitle("MAZDLE");
        this.getContentPane().setPreferredSize(new Dimension(310,520));
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-310/2,Toolkit.getDefaultToolkit().getScreenSize().height/2-520/2);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void shake(){
        int shake = 10;
        int originalX = this.getLocation().x;
        int originalY = this.getLocation().y;

        this.setLocation((int)(this.getLocation().x+shake), (int)(this.getLocation().y+shake));
        try{
        Thread.sleep(40);
        }catch(Exception e){
            System.out.println("waiting failed");
        }
        this.setLocation((int)(this.getLocation().x-shake), (int)(this.getLocation().y-shake));
        try{
            Thread.sleep(40);
        }catch(Exception e){
            System.out.println("waiting failed");
        }
        this.setLocation(originalX,originalY);
    }
}
