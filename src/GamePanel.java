import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    static final Button start = new Button("Start"), save = new Button("Save"), new_game = new Button("New Game"), quit = new Button("Quit"), pausee = new Button("Pause"), play = new Button("Play");
    static final int Width = 600;
    static final int Height = 600;
    static final int UNIT_Size = 35;
    static final int Game_Unit = (Width*Height)/ UNIT_Size;
    static  final int delay = 170;          //Change this parameter for controlling speed of the Snake
    final int x [] = new int[50];          //Snake position on X axis
    final int y[] = new int[50];          //Snake position on Y axis
    int bodyParts = 5;                   //Body Parts of Snake
    int fruitX, fruitY;                 //Position of Food
    Image food;                        //Food Image
    char direction = 'R';             //Direction
    boolean running, pause = false;
    int Score = 0;                  //Score
    Timer timer;
    final static Image logo = new ImageIcon("logo.png").getImage();

    GamePanel(){
        setPreferredSize(new Dimension(950, Height));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        setBackground(new Color(255,255,255));
        setLayout(null);

        //Buttons action listener
        start.addActionListener(this);
        new_game.addActionListener(this);
        save.addActionListener(this);
        quit.addActionListener(this);
        pausee.addActionListener(this);
        play.addActionListener(this);

        //Buuttons Positions
        start.setBounds(700, 100,120,70);
        pausee.setBounds(700, 100,120,70);
        play.setBounds(700, 100,120,70);
        new_game.setBounds(700, 200,120,70);
        save.setBounds(700, 300,120,70);
        quit.setBounds(700,400,120,70);

        new_game.setFont(new Font("Georgia", Font.PLAIN, 16));
        new_game.setVisible(false);
        pausee.setVisible(false);
        play.setVisible(false);

        add(start);
        add(save);
        add(new_game);
        add(quit);
        add(pausee);
        add(play);
    }

    //Start Game Method
    public void startGame(){
        newfruit();
        running  = true;
        timer = new Timer(delay, this);
        timer.start();
        start.setVisible(false);
        pausee.setVisible(true);
        new_game.setVisible(true);
        quit.setVisible(true);
    }

    //New_Game, when new game button or N will be pressed
    public void New_game(){
        timer.stop();
        running = false;
        Score = 0;
        bodyParts = 5;
        Arrays.fill(x, 0);
        Arrays.fill(y, 0);
        direction = 'R';
        startGame();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;
        //Rows Drawing Screen
        for(int i=0; i<Width/UNIT_Size+1; i++) {
            for (int j = 0; j < Height/UNIT_Size; j++) {
                if ((i+j) % 2 == 0)
                    gd.setColor(new Color(170, 215, 81));
                else
                    gd.setColor(new Color(162, 209, 73));

                gd.fillRect(i * UNIT_Size, j * UNIT_Size, UNIT_Size, UNIT_Size);

            }
        }

        //Line between Game Play stage & Button Panel
        gd.setColor(Color.red);
        gd.setStroke(new BasicStroke(5));
        gd.drawLine(Width+26,0,Width+26,Height);

        //Snake Drawing Logic
            for (int i = 0; i < bodyParts; i++) {
                if (i == 0 ){
                    gd.setColor(new Color(248, 55, 55));
                    gd.fillRect(x[i], y[i], UNIT_Size - 3, UNIT_Size - 3);
                    gd.setColor(Color.white);
                    gd.setStroke(new BasicStroke(3));
                    gd.drawOval(x[i] + 4, y[i] + 8, 8, 8);
                    gd.drawOval(x[i] + 20, y[i] + 8, 8, 8);
                    gd.setStroke(new BasicStroke(2));
                    gd.drawLine(x[i] + 4, y[i] + 25, x[i] + 28, y[i] + 25);
                } else {
                    gd.setColor(new Color(255, 153, 2));
                    gd.fillRect(x[i], y[i], UNIT_Size - 3, UNIT_Size - 3);

            }
        }

        //New Fruit
        gd.drawImage(food, fruitX, fruitY, null);

        //Score Updating
        gd.setColor(new Color(93, 1, 1));
        gd.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
        gd.drawString("Score:" + Score, 660 ,40);

        gd.drawImage(logo, 880,0,null);

        if(!running && new_game.isVisible()){
            Gameover(g);
        }
    }

    //Moving Snake Method
    public  void move(){
        for(int i=bodyParts; i>0; i--){
            x[i]  = x[i-1];
            y[i] = y[i-1];
        }
        switch(direction){
                case 'R':
                    x[0] = x[0] + UNIT_Size;
                    break;
                case 'L':
                    x[0] = x[0] - UNIT_Size;
                    break;
                case 'U':
                    y[0] = y[0] - UNIT_Size;
                    break;
                case 'D':
                    y[0] = y[0] + UNIT_Size;
                    break;
        }

    }

    //New Fruit Method
    public void newfruit() {
        final String[] Food_Images = new String[]{"src/img/ic_orange.png", "src/img/ic_fruit.png", "src/img/ic_cherry.png",
                "src/img/ic_berry.png", "src/img/ic_coconut_.png", "src/img/ic_peach.png", "src/img/ic_watermelon.png", "src/img/ic_orange.png",
                "src/img/ic_pomegranate.png"};
        Random random = new Random();
        fruitX = random.nextInt(Width / UNIT_Size) * UNIT_Size;
        fruitY = random.nextInt(Height / UNIT_Size) * UNIT_Size;
        food = new ImageIcon(Food_Images[random.nextInt(9)]).getImage().getScaledInstance(35,35,5);

    }

    //Check if snakes eat fruit or not
    public void checkFruit(){
        if(x[0]==fruitX && y[0] ==fruitY){
            Audio clicked = new Audio("src/sounds/eat.wav");
            clicked.audio.start();
            newfruit();
            bodyParts++;
            Score+=1;
        }

    }
    //Checking Game over conditions
    public void checkCollision(){
        for(int i=bodyParts; i>0; i--) {
            //If Body Touches itself
            if (x[0] == x[i] && y[0] == y[i])
                running = false;

            //Collision with sides
            if (x[0] >= Width - 35)        //Right Border
                running = false;
            if (x[0] < 0)              //Left Border
                running = false;
            if (y[0] < 0)              //Up Border
                running = false;
            if (y[0] >= Height)
                running = false;    //Down Border

            if (!running) {
                Audio clicked = new Audio("src/sounds/die.wav");
                clicked.audio.start();
                timer.stop();
            }
        }
    }

    //Game Over Message
    public void Gameover(Graphics g){
        g.setColor(new Color(157, 6, 6));
        g.setFont(new Font("Georgia", Font.BOLD, 60));
        FontMetrics fontMetrics  = getFontMetrics(g.getFont());
        g.drawString("Game Over", (Width-fontMetrics.stringWidth("Game Over"))/2, (int) ((Height-fontMetrics.stringWidth("Game Over"))/1.5));
    }
    //Play Button Logic
    void play(){
        play.setVisible(false);
        pausee.setVisible(true);
        timer.start();
    }
    //Pause Button Logic
    void pause() {
        play.setVisible(true);
        pausee.setVisible(false);
        timer.stop();
    }

    void audio(){
        Audio clicked = new Audio("src/sounds/click.wav");
        clicked.audio.start();
    }

    //Action Performed Method
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            checkCollision();
            checkFruit();
        }
        repaint();

        if(e.getSource() == start)
            startGame();

        if(e.getSource() == new_game){
            New_game();
            repaint();
        }

        if (e.getSource() == pausee)
        pause();
    
    if (e.getSource() == play)
        play();
    
    if (e.getSource() == quit) {
        quit();
        int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin keluar dari game ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Tombol "Quit" ditekan, kembali ke halaman GameOption
            backToGameOption();
        }
    }
    
    if (e.getSource() == save) {
        save();
    
        // Meminta pengguna untuk memasukkan nama
        String playerName = JOptionPane.showInputDialog("Masukkan nama Anda:");
    
        // Pastikan nama tidak kosong dan pengguna tidak membatalkan dialog
        if (playerName != null && !playerName.isEmpty()) {
            if (!isPlayerNameExists(playerName)) {
            // Simpan skor ke database
            DatabaseManager dbManager = new DatabaseManager();
            dbManager.saveScore(playerName, Score);
            JOptionPane.showMessageDialog(this, "Skor Anda telah disimpan!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
        } else {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Nama yang Anda masukkan sudah ada. Apakah Anda yakin ingin mengubah skor?",
                "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
    
            if (confirm == JOptionPane.YES_OPTION) {
                DatabaseManager dbManager = new DatabaseManager();

                int currentScore = dbManager.getCurrentScore(playerName);
                dbManager.updateScore(playerName, Score);
                JOptionPane.showMessageDialog(this, "Skor Anda telah diupdate!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Skor tidak diubah.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            }
    }
}
}
}
    
private boolean isPlayerNameExists(String playerName) {
    try {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/gameular";
        String username = "root"; // Ubah sesuai dengan pengaturan MySQL Anda
        String password = ""; // Kosongkan jika tidak ada password

        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();

        // Check if the player name already exists in the database
        String query = "SELECT COUNT(*) FROM skor_tertinggi WHERE Nama = '" + playerName + "'";
        ResultSet resultSet = statement.executeQuery(query);
        resultSet.next();

        int count = resultSet.getInt(1);

        resultSet.close();
        statement.close();
        connection.close();

        return count > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        // Handle the exception (e.g., show an error message)
        return false;
    }
}
    private void quit() {
    }
private void backToGameOption() {
    // Tutup GamePanel
    JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    currentFrame.dispose();

    // Buka GameOption
    JFrame gameOptionFrame = new GameOption();
    gameOptionFrame.setVisible(true);
    
    }

    private void save() {
    }

    //KeyBoard Control
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){

            switch (e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(direction!='R')
                        direction = 'L';
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L')
                        direction = 'R';
                    break;

                case KeyEvent.VK_UP:
                    if(direction!='D')
                        direction = 'U';
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction!='U')
                        direction = 'D';
                    break;
                case KeyEvent.VK_N:
                    if(!start.isVisible()) {
                    audio();
                        New_game();
                    }

                    break;
                case KeyEvent.VK_ENTER:
                    if(!new_game.isVisible()) {
                        audio();
                        startGame();
                    }
                    break;
                case KeyEvent.VK_P:
                    if(pausee.isVisible()){
                        audio();
                        pause();
                    }
                    else{
                        audio();
                        play();
                    }
                    break;
            }
        }
    }

}