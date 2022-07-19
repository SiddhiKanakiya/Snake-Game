import javax.swing.*;

public class GameFrame extends JFrame{

    GameFrame(){
//   Constructors
//        Below are attributes from parent class JFame
//        Frame provides a frame
        this.add(new GamePanel());
        this.setTitle("Snakes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

}
