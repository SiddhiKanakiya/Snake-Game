import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class GamePanel extends JPanel implements ActionListener{
    static final int screenWidth = 600;
    static final int screenHeight = 600;
    static final int unitSize = 25;
    static final int gameUnits = screenWidth*screenHeight/unitSize;
    //            Higher number of delay slower is the game
    static final int delay = 75;
//Co-ordinates of snake x holdy x coord and y coorf
    final int[] x = new int[gameUnits];
    final int[] y = new int[gameUnits];
//    Initial size of the snake
    int bodyParts = 6;

    int applesEaten = 0;
//    coordinates of apple
    int appleX ;
    int appleY ;
//Direction of movement in snake is right;
    char direction = 'R';
    boolean running = false;

    Timer timer;
    Random random;

     GamePanel(){
     //Constructor

         random = new Random();
//      Size of the panel
         this.setPreferredSize(new Dimension(screenWidth,screenHeight));
         this.setBackground(Color.BLACK);
         this.setFocusable(true);
         this.addKeyListener(new myKeyAdapter());
         startGame();
}

    public void startGame(){
         newApple();
         running = true;
         timer = new Timer(delay,this);
         timer.start();

    }

    public void paintComponent(Graphics g){
         super.paintComponent(g);
         draw(g);

    }
    public void draw(Graphics g){
//  add grid to the frame easier visualization
        if(running) {
            for (int i = 0; i < screenHeight / unitSize; i++) {
//          vertical line
            g.drawLine(i * unitSize, 0, i * unitSize, 600);
//          Horizontal line
            g.drawLine(0, i * unitSize, 600, i * unitSize);
            }

//          Apple drawing
        g.setColor(Color.RED);
        g.fillOval(appleX, appleY, unitSize, unitSize);

//         Snake drawing
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
                else {
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], y[i], unitSize, unitSize);
                }
            }

            g.setColor(Color.RED);
            g.setFont(new Font("ink free", Font.BOLD, 25));
//        Helps lining text in center of screen
            FontMetrics metric = getFontMetrics(g.getFont());
            g.drawString("Score: "+ applesEaten, 25, 25);
        }

        else {
            gameOver(g);
        }
    }

    public void newApple(){
//Generate the coordinates of new apple for the snake

        appleX= random.nextInt((int)(screenWidth/unitSize))*unitSize;
        appleY= random.nextInt((int)(screenHeight/unitSize))*unitSize;

    }

    public void move(){
//Movement of the snake
        for(int i=bodyParts;i>0;i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

            switch(direction){
                case 'R':x[0]=x[0]+unitSize;
                        break;
                case'L':x[0]=x[0]-unitSize;
                        break;
                case'U':y[0]=y[0]-unitSize;
                        break;
                case'D':y[0]=y[0]+unitSize;
                        break;
            }




    }

    public void checkApple(){
//Check if snake as eaten the fruit/apple/token

        if((x[0]==appleX) && (y[0]==appleY )){
            bodyParts++;
            applesEaten++;
            newApple();

        }
    }

    public void checkCollision(){
//         TO check if the snake as collided

        for(int i=bodyParts;i>0;i--){
//            if head collides with body
            if(x[0]==x[i] && y[0]==y[i]) running = false;
//            if head touches left border
             if(x[0]<0 || x[0]>screenWidth) running=false;
             if(y[0]<0 || y[0]>screenHeight) running = false;
        }

        if(running==false) timer.stop();

//
    }

    public void replay(Graphics g){
        g.setColor(Color.RED);
        g.setFont(new Font("ink free", Font.BOLD, 45));
//        Helps lining text in center of screen
        FontMetrics metric2 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (screenWidth - metric2.stringWidth("Score: " + applesEaten)) / 2, (screenHeight / 2) + 75);

    }

    public void gameOver(Graphics g) {



            g.setColor(Color.RED);
            g.setFont(new Font("ink free", Font.BOLD, 75));
//        Helps lining text in center of screen
            FontMetrics metric1 = getFontMetrics(g.getFont());
            g.drawString("Game Over", (screenWidth - metric1.stringWidth("Game Over")) / 2, screenHeight / 2);

            g.setColor(Color.RED);
            g.setFont(new Font("ink free", Font.BOLD, 45));
//        Helps lining text in center of screen
            FontMetrics metric2 = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (screenWidth - metric1.stringWidth("Score: " + applesEaten)) / 2, (screenHeight / 2) + 75);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
         if(running){
             move();
             checkApple();
             checkCollision();
         }
         repaint();

         }

    public class myKeyAdapter extends KeyAdapter{
    @Override
    public void keyPressed (KeyEvent e){
//helps control the snake

        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if(direction!='R'){
                    direction='L';
                    break;
                }
            case KeyEvent.VK_RIGHT:
                if(direction!='L'){
                    direction='R';
                    break;
                }
            case KeyEvent.VK_UP:
                if(direction!='D'){
                    direction='U';
                    break;
                }
            case KeyEvent.VK_DOWN:
                if(direction!='U'){
                    direction='D';
                    break;
                }
        }
        move();
    }

    }
}
