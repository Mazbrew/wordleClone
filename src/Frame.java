import java.awt.Dimension;

import java.awt.*;
import javax.swing.JFrame;


public class Frame extends JFrame {
    public Frame(Panel panel){
        super();
        this.add(panel);
        this.setResizable(false);
        this.setTitle("Wordle Clone");
        this.getContentPane().setPreferredSize(new Dimension(panel.getSize().width,panel.getSize().height));
        this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width/2-panel.getSize().width/2,Toolkit.getDefaultToolkit().getScreenSize().height/2-panel.getSize().height/2);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    
    }
}
