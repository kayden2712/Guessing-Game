import javax.swing.*;
import java.awt.event.ActionListener;

public class gameFrame extends JFrame {
    gameFrame(){
        gamePanel panel = new gamePanel();
        this.add(new gamePanel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
