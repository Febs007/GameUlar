//Main Frame
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class GameFrame extends JFrame{
    final  static Image icon = new ImageIcon("logo.png").getImage();
    GameFrame(){
        add(new GamePanel());
        setTitle("Game Ular imut");
        setSize(950,700);
        setIconImage(icon);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
