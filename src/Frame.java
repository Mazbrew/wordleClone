import java.awt.Dimension;

import java.awt.*;
import javax.swing.JFrame;


public class Frame extends JFrame {
    public Frame(){
        super();
        this.setResizable(false);
        this.setTitle("Wordle Clone");
        this.getContentPane().setPreferredSize(new Dimension(310,370));
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-310/2,Toolkit.getDefaultToolkit().getScreenSize().height/2-370/2);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void shake(){
        this.setLocation(this.getLocation().x+10, this.getLocation().y+10);
        this.setLocation(this.getLocation().x-20, this.getLocation().y-20);
        this.setLocation(this.getLocation().x+20, this.getLocation().y+20);
        this.setLocation(this.getLocation().x-20, this.getLocation().y-20);
        this.setLocation(this.getLocation().x+20, this.getLocation().y+20);
        this.setLocation(this.getLocation().x-10, this.getLocation().y-10);
    }
}
